package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Playlist extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private Integer followers;
    private int timestamp;

    public Playlist(final String name, final String owner) {
        this(name, owner, 0);
    }

    public Playlist(final String name, final String owner, final int timestamp) {
        super(name, owner);
        this.songs = new ArrayList<>();
        this.visibility = Enums.Visibility.PUBLIC;
        this.followers = 0;
        this.timestamp = timestamp;
    }

    /**
     * Checks if the playlist contains a specific song.
     *
     * @param song The song to check for in the playlist.
     * @return {@code true} if the playlist contains the song; {@code false} otherwise.
     */
    public boolean containsSong(final Song song) {
        return songs.contains(song);
    }

    /**
     * Adds a song to the playlist.
     *
     * @param song The song to be added to the playlist.
     */
    public void addSong(final Song song) {
        songs.add(song);
    }

    /**
     * Removes a specific song from the playlist.
     *
     * @param song The song to be removed from the playlist.
     */
    public void removeSong(final Song song) {
        songs.remove(song);
    }

/**
 * Switches the visibility of the object between public and private.
 * If the current visibility is public, it changes it to private, and vice versa.
 */
    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }

    /**
     * Increases the count of followers for the object.
     */
    public void increaseFollowers() {
        followers++;
    }

    /**
     * Decreases the count of followers for the object.
     */
    public void decreaseFollowers() {
        followers--;
    }

    /**
     * Gets the number of tracks in the collection.
     *
     * @return The number of tracks in the collection.
     */
    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    /**
     * Retrieves the audio file (track) at the specified index.
     *
     * @param index The index of the track to retrieve.
     * @return The audio file (track) at the specified index.
     */
    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

/**
 * Checks if the object is visible to a specific user based on its visibility setting.
 *
 * @param user The username of the user attempting to access the object.
 * @return {@code true} if the object is public or owned by the specified user (private),
 * {@code false} otherwise.
 */
    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                || (this.getVisibility() == Enums.Visibility.PRIVATE
                && this.getOwner().equals(user));
    }

    @Override
    public boolean matchesFollowers(final String followers2) {
        return filterByFollowersCount(this.getFollowers(), followers2);
    }

    /**
     * Filters an entry based on its followers count using a specified query.
     *
     * @param count The followers count of the entry.
     * @param query The query to filter by, supporting comparison operators
     *              (<, >) and equality (=).
     * @return {@code true} if the entry's followers count matches the query;
     * {@code false} otherwise.
     */
    private static boolean filterByFollowersCount(final int count, final String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }

/**
 * Gets the total number of likes for the object based on the likes of associated songs.
 *
 * @return The total number of likes for the object.
 */
    public int getLikes() {
        int likes = 0;
        for (Song song : songs) {
            likes += song.getLikes();

        }
        return likes;
    }
}
