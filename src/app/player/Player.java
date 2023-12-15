package app.player;

import app.audio.Collections.AudioCollection;
import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Player {
    public static final int MAX = 90;
    private Enums.RepeatMode repeatMode;
    private boolean shuffle;
    private boolean paused;
    @Getter
    private PlayerSource source;
    private PlayerSource lastAudionBeforeLogOut;
    @Getter
    private String type;
    private ArrayList<PodcastBookmark> bookmarks = new ArrayList<>();

    /**
     * Logs out the user, saving the current audio state (song, bookmark) before logging out.
     */
    public void logOut() {
        if (source != null && source.getType().equals(Enums.PlayerSourceType.SONG)) {
            lastAudionBeforeLogOut = new PlayerSource(source.getType(),
                    source.getAudioFile(), new SongBookmark(
                    source.getAudioFile().getName(), source.getRemainedDuration(),
                    paused, repeatMode
            ));
        }
    }

    /**
     * Logs in the user, restoring the audio state saved before logging out.
     */
    public void logIn() {
        if (lastAudionBeforeLogOut != null) {
            source = lastAudionBeforeLogOut;
        }
    }


    public Player() {
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.paused = true;
    }

    /**
     * Stops the player, clearing the current audio source, repeat mode, and shuffle status.
     * If the current type is "podcast," it bookmarks the podcast.
     */
    public void stop() {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }

        repeatMode = Enums.RepeatMode.NO_REPEAT;
        paused = true;
        source = null;
        shuffle = false;
    }

    private void bookmarkPodcast() {
        if (source != null && source.getAudioFile() != null) {
            PodcastBookmark currentBookmark = new PodcastBookmark(source.getAudioCollection()
                    .getName(), source.getIndex(), source.getDuration());
            bookmarks.removeIf(bookmark -> bookmark.getName().equals(currentBookmark.getName()));
            bookmarks.add(currentBookmark);
        }
    }

    /**
     * Creates a new player source based on the specified type and library entry.
     * Also, initializes the bookmarks for podcasts if the type is "podcast."
     *
     * @param type      The type of the source (e.g., "song," "playlist," "podcast," "album").
     * @param entry     The library entry to create the source for.
     * @param bookmarks The list of podcast bookmarks for initialization.
     * @return A new PlayerSource object.
     */
    public static PlayerSource createSource(final String type, final LibraryEntry entry,
                                            final List<PodcastBookmark> bookmarks) {
        if ("song".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.LIBRARY, (AudioFile) entry);
        } else if ("playlist".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.PLAYLIST, (AudioCollection) entry);
        } else if ("podcast".equals(type)) {
            return createPodcastSource((AudioCollection) entry, bookmarks);
        } else if ("album".equals(type)) {
            return new PlayerSource(Enums.PlayerSourceType.ALBUM, (AudioCollection) entry);
        }

        return null;
    }

    /**
     * Creates a podcast source with bookmarks based on the provided collection and
     * bookmark list.
     *
     * @param collection The podcast collection to create the source for.
     * @param bookmarks  The list of podcast bookmarks for initialization.
     * @return A new PlayerSource object for the podcast.
     */
    private static PlayerSource createPodcastSource(final AudioCollection collection,
                                                    final List<PodcastBookmark> bookmarks) {
        for (PodcastBookmark bookmark : bookmarks) {
            if (bookmark.getName().equals(collection.getName())) {
                return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection, bookmark);
            }
        }
        return new PlayerSource(Enums.PlayerSourceType.PODCAST, collection);
    }

    /**
     * Sets the current audio source, type, repeat mode, shuffle status, and paused status
     * based on the specified library entry and type.
     * If the current type is "podcast," it bookmarks the podcast.
     *
     * @param entry The library entry to set as the new source.
     * @param type  The type of the new source (e.g., "song," "playlist," "podcast," "album").
     */
    public void setSource(final LibraryEntry entry, final String type) {
        if ("podcast".equals(this.type)) {
            bookmarkPodcast();
        }
        this.type = type;
        this.source = createSource(type, entry, bookmarks);
        this.repeatMode = Enums.RepeatMode.NO_REPEAT;
        this.shuffle = false;
        this.paused = true;
    }

    /**
     * Pauses or resumes playback.
     */
    public void pause() {
        paused = !paused;
    }

    /**
     * Activates or deactivates the shuffle function. If a seed is provided, it generates
     * a new shuffle order.
     *
     * @param seed The seed for shuffle order generation. Can be null.
     */
    public void shuffle(final Integer seed) {
        if (seed != null) {
            source.generateShuffleOrder(seed);
        }

        if (source.getType() == Enums.PlayerSourceType.PLAYLIST || source.getType()
                == Enums.PlayerSourceType.ALBUM) {
            shuffle = !shuffle;
            if (shuffle) {
                source.updateShuffleIndex();
            }
        }
    }

    /**
     * Toggles between different repeat modes and returns the current repeat mode.
     *
     * @return The current repeat mode after the toggle.
     */
    public Enums.RepeatMode repeat() {
        if (repeatMode == Enums.RepeatMode.NO_REPEAT) {
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                repeatMode = Enums.RepeatMode.REPEAT_ONCE;
            } else {
                repeatMode = Enums.RepeatMode.REPEAT_ALL;
            }
        } else {
            if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
                repeatMode = Enums.RepeatMode.REPEAT_INFINITE;
            } else {
                if (repeatMode == Enums.RepeatMode.REPEAT_ALL) {
                    repeatMode = Enums.RepeatMode.REPEAT_CURRENT_SONG;
                } else {
                    repeatMode = Enums.RepeatMode.NO_REPEAT;
                }
            }
        }

        return repeatMode;
    }

    /**
     * Simulates the player's progress for the specified time, skipping to the next
     * track if needed.
     *
     * @param time The simulated time to progress.
     */
    public void simulatePlayer(final int finalTime) {
        if (!paused) {
            int time = finalTime;
            while (time >= source.getDuration()) {
                time -= source.getDuration();
                next();
                if (paused) {
                    break;
                }
            }
            if (!paused) {
                source.skip(-time);
            }
        }
    }

    /**
     * Skips to the next audio file in the playlist or podcast.
     */
    public void next() {
        paused = source.setNextAudioFile(repeatMode, shuffle);
        if (repeatMode == Enums.RepeatMode.REPEAT_ONCE) {
            repeatMode = Enums.RepeatMode.NO_REPEAT;
        }

        if (source.getDuration() == 0 && paused) {
            stop();
        }
    }

    /**
     * Skips to the previous audio file in the playlist or podcast.
     */
    public void prev() {
        source.setPrevAudioFile(shuffle);
        paused = false;
    }

    /**
     * Skips the specified duration in the podcast (negative for forward skip, positive
     * for backward skip).
     *
     * @param duration The duration to skip.
     */
    private void skip(final int duration) {
        source.skip(duration);
        paused = false;
    }

    /**
     * Skips to the next episode in the podcast (specifically for podcasts).
     */
    public void skipNext() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(-MAX);
        }
    }

    /**
     * Skips to the previous episode in the podcast (specifically for podcasts).
     */
    public void skipPrev() {
        if (source.getType() == Enums.PlayerSourceType.PODCAST) {
            skip(MAX);
        }
    }

    /**
     * Retrieves the current audio file being played.
     *
     * @return The current AudioFile object.
     */
    public AudioFile getCurrentAudioFile() {
        if (source == null) {
            return null;
        }
        return source.getAudioFile();
    }

    /**
     * Checks whether the player is currently paused.
     *
     * @return True if the player is paused, false otherwise.
     */
    public boolean getPaused() {
        return paused;
    }

    /**
     * Checks whether the shuffle function is currently activated.
     *
     * @return True if shuffle is active, false otherwise.
     */
    public boolean getShuffle() {
        return shuffle;
    }

    /**
     * Retrieves the current player statistics, including the filename, duration,
     * repeat mode, shuffle status, and paused status.
     *
     * @return A PlayerStats object representing the current player statistics.
     */
    public PlayerStats getStats() {
        String filename = "";
        int duration = 0;
        if (source != null && source.getAudioFile() != null) {
            filename = source.getAudioFile().getName();
            duration = source.getDuration();
        } else {
            stop();
        }

        return new PlayerStats(filename, duration, repeatMode, shuffle, paused);
    }
}
