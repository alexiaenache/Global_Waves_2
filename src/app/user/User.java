package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.AlbumOutput;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.Podcast;
import app.audio.Collections.PodcastOutput;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.ArtistPage;
import app.pages.HomePage;
import app.pages.HostPage;
import app.pages.LikedContent;
import app.pages.Page;
import app.pages.PrintVisitor;
import app.player.Player;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public final class User extends LibraryEntry {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private ArrayList<Playlist> playlists;
    @Getter
    private ArrayList<Song> likedSongs;
    @Getter
    private ArrayList<Playlist> followedPlaylists;
    @Getter
    private ArrayList<Merch> merchandise = new ArrayList<>();
    @Getter
    private ArrayList<Album> albums;
    @Getter
    private ArrayList<Event> events;
    @Getter
    private ArrayList<Announcement> announcements;
    @Getter
    private ArrayList<Podcast> podcasts = new ArrayList<>();
    @Getter
    private Page currentPage;
    @Getter
    private final Player player;
    private final SearchBar searchBar;
    private boolean lastSearched;
    private String lastSearchType;
    private boolean connectionStatus = true;
    private String type = "user";

    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    public String getLastSearchType() {
        return lastSearchType;
    }

    public String getType() {
        return type;
    }

    /**
     * Sets the type of the user.
     *
     * @param type The new type for the user.
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Checks the connection status of the user.
     *
     * @return True if the user is currently connected, false otherwise.
     */
    public boolean isConnectionStatus() {
        return connectionStatus;
    }

    /**
     * Switches the connection status of the user (log in or log out).
     */
    public void switchConnection() {
        connectionStatus = !connectionStatus;
        if (connectionStatus) {
            player.logIn();
        } else {
            player.logOut();
        }
    }


    public User(final String username, final int age, final String city) {
        super(username);
        this.username = username;
        this.age = age;
        this.city = city;
        currentPage = new HomePage(username);
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
    }

    public User(final String username, final int age, final String city, final String type) {
        super(username);
        this.username = username;
        this.type = type;
        this.age = age;
        this.city = city;
        playlists = new ArrayList<>();
        likedSongs = new ArrayList<>();
        followedPlaylists = new ArrayList<>();
        player = new Player();
        searchBar = new SearchBar(username);
        lastSearched = false;
        currentPage = new HomePage(username);
    }

    /**
     * Returns a LibraryEntry representation of the user.
     *
     * @return A LibraryEntry representing the user.
     */
    public LibraryEntry asLibraryEntry() {
        return new LibraryEntry(username) {
            @Override
            public boolean matchesName(final String name) {
                return getName().toLowerCase()
                        .startsWith(name.toLowerCase());
            }
        };
    }

    /**
     * Searches for items based on the provided filters and type.
     *
     * @param filters The filters to apply during the search.
     * @param type1    The type of items to search for.
     * @return A list of search results.
     */
    public ArrayList<String> search(final Filters filters, final String type1) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        lastSearchType = type1;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type1);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    /**
     * Selects an item based on its number and type.
     *
     * @param itemNumber The number of the item to select.
     * @param type1       The type of the selected item.
     * @return A message indicating the result of the selection.
     */
    public String select(final int itemNumber, final String type1) {
        if (!lastSearched) {
            return "Please conduct a search before making a selection.";
        }

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null) {
            return "The selected ID is too high.";
        }
        if (type1.equals("song") || type1.equals("podcast") || type1.equals("album")
                || type1.equals("playlist")) {
            return "Successfully selected %s.".formatted(selected.getName());
        } else {
            if (type1.equals("artist")) {
                currentPage = new ArtistPage(selected.getName());
            } else if (type1.equals("host")) {
                currentPage = new HostPage(selected.getName());
            }
            return "Successfully selected %s".formatted(selected.getName()) + "'s page.";
        }
    }

    /**
     * Loads the selected source for playback.
     *
     * @return A message indicating the result of the load operation.
     */
    public String load() {
        if (searchBar.getLastSelected() == null) {
            return "Please select a source before attempting to load.";
        }

        if (!searchBar.getLastSearchType().equals("song") && ((AudioCollection) searchBar
                .getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    /**
     * Pauses or resumes playback of the current audio file.
     *
     * @return A message indicating the result of the pause or resume operation.
     */
    public String playPause() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to pause or resume playback.";
        }

        player.pause();

        if (player.getPaused()) {
            return "Playback paused successfully.";
        } else {
            return "Playback resumed successfully.";
        }
    }

    /**
     * Sets the repeat mode for playback.
     *
     * @return A message indicating the updated repeat mode.
     */
    public String repeat() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before setting the repeat status.";
        }

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch (repeatMode) {
            case NO_REPEAT -> repeatStatus = "no repeat";
            case REPEAT_ONCE -> repeatStatus = "repeat once";
            case REPEAT_ALL -> repeatStatus = "repeat all";
            case REPEAT_INFINITE -> repeatStatus = "repeat infinite";
            case REPEAT_CURRENT_SONG -> repeatStatus = "repeat current song";
            default -> {
            }
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    /**
     * Activates or deactivates the shuffle function for the current playlist or album.
     *
     * @param seed The seed for shuffling.
     * @return A message indicating the result of the shuffle operation.
     */
    public String shuffle(final Integer seed) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before using the shuffle function.";
        }

        if (!player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "The loaded source is not a playlist or an album.";
        }

        player.shuffle(seed);

        if (player.getShuffle()) {
            return "Shuffle function activated successfully.";
        }
        return "Shuffle function deactivated successfully.";
    }

    /**
     * Skips to the next episode in the loaded podcast.
     *
     * @return A message indicating the result of the skip operation.
     */
    public String forward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before attempting to forward.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipNext();

        return "Skipped forward successfully.";
    }

    /**
     * Rewinds to the previous episode in the loaded podcast.
     *
     * @return A message indicating the result of the rewind operation.
     */
    public String backward() {
        if (player.getCurrentAudioFile() == null) {
            return "Please select a source before rewinding.";
        }

        if (!player.getType().equals("podcast")) {
            return "The loaded source is not a podcast.";
        }

        player.skipPrev();

        return "Rewound successfully.";
    }

    /**
     * Registers a like or unlike for the currently loaded song, playlist, or album.
     *
     * @return A message indicating the result of the like operation.
     */
    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist")
                && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();
        boolean isLiked = false;
        for (Song likedSong : likedSongs) {
            if (likedSong.getName().equals(song.getName())) {
                isLiked = true;
                break;
            }
        }

        if (isLiked) {
            likedSongs.remove(song);
            song.dislike();
            for (Song s : Admin.getSongs()) {
                if (s.getName().equals(song.getName()) && !song.equals(s)) {
                    s.dislike();
                }
            }
            for (User user : Admin.getUsers()) {
                for (Song currentSong : user.getLikedSongs()) {
                    if (song.getName().equals(currentSong.getName())
                            && !song.equals(currentSong)) {
                        currentSong.dislike();
                    }
                }
            }
            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        for (Song s : Admin.getSongs()) {
            if (s.getName().equals(song.getName()) && !song.equals(s)) {
                s.like();
            }
        }
        for (User user : Admin.getUsers()) {
            for (Song currentSong : user.getLikedSongs()) {
                if (song.getName().equals(currentSong.getName())
                        && !song.equals(currentSong)) {
                    currentSong.like();
                }
            }
        }
        return "Like registered successfully.";
    }

    /**
     * Skips to the next track in the loaded playlist or album.
     *
     * @return A message indicating the result of the skip operation.
     */
    public String next() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        player.next();

        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before skipping to the next track.";
        }

        return "Skipped to next track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Returns to the previous track in the loaded playlist or album.
     *
     * @return A message indicating the result of the return operation.
     */
    public String prev() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before returning to the previous track.";
        }

        player.prev();

        return "Returned to previous track successfully. The current track is %s."
                .formatted(player.getCurrentAudioFile().getName());
    }

    /**
     * Creates a new playlist with the specified name and timestamp.
     *
     * @param name      The name of the new playlist.
     * @param timestamp The timestamp for the new playlist.
     * @return A message indicating the result of the playlist creation.
     */
    public String createPlaylist(final String name, final int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name))) {
            return "A playlist with the same name already exists.";
        }

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    /**
     * Adds or removes the currently loaded song from a playlist.
     *
     * @param id The ID of the playlist.
     * @return A message indicating the result of the add or remove operation.
     */
    public String addRemoveInPlaylist(final int id) {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before adding to or removing from the playlist.";
        }

        if (player.getType().equals("podcast")) {
            return "The loaded source is not a song.";
        }

        if (id > playlists.size()) {
            return "The specified playlist does not exist.";
        }

        Playlist playlist = playlists.get(id - 1);

        if (playlist.containsSong((Song) player.getCurrentAudioFile())) {
            playlist.removeSong((Song) player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song) player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    /**
     * Switches the visibility status of a playlist.
     *
     * @param playlistId The ID of the playlist.
     * @return A message indicating the result of the visibility status change.
     */
    public String switchPlaylistVisibility(final Integer playlistId) {
        if (playlistId > playlists.size()) {
            return "The specified playlist ID is too high.";
        }

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    /**
     * Returns a list of PlaylistOutput objects representing the user's playlists.
     *
     * @return A list of PlaylistOutput objects.
     */
    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    /**
     * Follows or unfollows the currently selected playlist.
     *
     * @return A message indicating the result of the follow or unfollow operation.
     */
    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null) {
            return "Please select a source before following or unfollowing.";
        }

        if (!type.equals("playlist")) {
            return "The selected source is not a playlist.";
        }

        Playlist playlist = (Playlist) selection;

        if (playlist.getOwner().equals(username)) {
            return "You cannot follow or unfollow your own playlist.";
        }

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    /**
     * Returns the player statistics, including play count, skip count, and duration.
     *
     * @return PlayerStats object containing the player statistics.
     */
    public PlayerStats getPlayerStats() {
        if (isConnectionStatus()) {
            return player.getStats();
        }
        return player.getStats();
    }

    /**
     * Returns a list of the user's preferred songs.
     *
     * @return A list of preferred songs.
     */
    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

    /**
     * Returns the user's preferred genre based on liked songs.
     *
     * @return A message indicating the user's preferred genre.
     */
    public String getPreferredGenre() {
        String[] genres = {"pop", "rock", "rap"};
        int[] counts = new int[genres.length];
        int mostLikedIndex = -1;
        int mostLikedCount = 0;

        for (Song song : likedSongs) {
            for (int i = 0; i < genres.length; i++) {
                if (song.getGenre().equals(genres[i])) {
                    counts[i]++;
                    if (counts[i] > mostLikedCount) {
                        mostLikedCount = counts[i];
                        mostLikedIndex = i;
                    }
                    break;
                }
            }
        }

        String preferredGenre = mostLikedIndex != -1 ? genres[mostLikedIndex] : "unknown";
        return "This user's preferred genre is %s.".formatted(preferredGenre);
    }

    /**
     * Simulates the passage of time in the player.
     *
     * @param time The amount of time to simulate, in seconds.
     */
    public void simulateTime(final int time) {
        if (this.isConnectionStatus()) {
            player.simulatePlayer(time);
        }
    }

    /**
     * Adds a new album with the specified details and songs to the user's collection.
     *
     * @param name        The name of the new album.
     * @param timestamp   The timestamp for the new album.
     * @param description The description of the new album.
     * @param releaseYear The release year of the new album.
     * @param songInputs  The list of SongInput objects representing songs on the album.
     * @return A message indicating the result of the album addition.
     */
    public String addAlbum(final String name, final int timestamp, final String description,
                           final int releaseYear, final ArrayList<SongInput> songInputs) {
        if (albums == null) {
            albums = new ArrayList<>();
        }
        if (Admin.getAlbums() == null) {
            Admin.setAlbums(new ArrayList<>());
        }
        if (albums.stream().anyMatch(album -> album.getName().equals(name))) {
            return getUsername() + " has another album with the same name.";
        }
        ArrayList<Song> songs = new ArrayList<>();
        for (SongInput songInput : songInputs) {
            Song newSong = new Song(songInput.getName(), songInput.getDuration(),
                    songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),
                    songInput.getGenre(), songInput.getReleaseYear(), songInput.getArtist());
            if (songs.stream().noneMatch(song -> song.getName().equals(newSong.getName()))) {
                songs.add(newSong);
            } else {
                return username + " has the same song at least twice in this album.";
            }
            if (Admin.getSongs().stream().noneMatch(song -> song.getName()
                    .equals(songInput.getName()))) {
                Song s = new Song(songInput.getName(), songInput.getDuration(),
                        songInput.getAlbum(), songInput.getTags(),
                        songInput.getLyrics(), songInput.getGenre(), songInput.getReleaseYear(),
                        songInput.getArtist());
                Admin.addSong(s);
            }
        }
        Album album = new Album(name, username, timestamp, description, releaseYear, songs);
        albums.add(album);
        Admin.getAlbums().add(album);
        return getUsername() + " has added new album successfully.";

    }

    /**
     * Returns a list of AlbumOutput objects representing the user's albums.
     *
     * @return A list of AlbumOutput objects.
     */
    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutputs = new ArrayList<>();
        if (albums != null) {
            for (Album album : albums) {
                albumOutputs.add(new AlbumOutput(album));
            }
        }

        return albumOutputs;
    }

    /**
     * Prints information about the current page the user is viewing.
     *
     * @return A message containing information about the current page.
     */
    public String printCurrentPage() {
        PrintVisitor p = new PrintVisitor();
        String message = currentPage.accept(p);
        return message;
    }

    /**
     * Adds a new event with the specified details to the user's events.
     *
     * @param name        The name of the new event.
     * @param description The description of the new event.
     * @param date        The date of the new event.
     * @param timestamp   The timestamp for the new event.
     * @param artistName  The name of the artist associated with the event.
     * @return A message indicating the result of the event addition.
     */
    public String addEvent(final String name, final String description, final String date,
                           final int timestamp, final String artistName) {
        if (events != null && events.stream().anyMatch(event -> event.getName().equals(name))) {
            return username + " has another event with the same name.";
        }
        if (events == null) {
            events = new ArrayList<>();
        }
        if (DateValidator.isValidDate(date)) {

            events.add(new Event(name, description, date, timestamp, artistName));
            return username + " has added new event successfully.";
        }
        return "Event for " + username + " does not have a valid date.";

    }

    /**
     * Removes an event with the specified name from the user's events.
     *
     * @param name The name of the event to be removed.
     * @return A message indicating the result of the event removal.
     */
    public String removeEvent(final String name) {
        if (events == null || events.stream().noneMatch(event -> event.getName().equals(name))) {
            return username + " doesn't have an event with the given name.";
        }
        events.removeIf(event -> event.getName().equals(name));
        return username + " deleted the event successfully.";
    }

    /**
     * Adds a new announcement with the specified details to the user's announcements.
     *
     * @param name        The name of the new announcement.
     * @param description The description of the new announcement.
     * @return A message indicating the result of the announcement addition.
     */
    public String addAnnouncement(final String name, final String description) {
        if (announcements == null) {
            announcements = new ArrayList<>();
        }
        if (announcements.stream().anyMatch(announcement -> announcement.getName().equals(name))) {
            return username + " has already added an announcement with this name.";
        }
        announcements.add(new Announcement(name, description));
        return username + " has successfully added new announcement.";
    }

    /**
     * Removes an announcement with the specified name from the user's announcements.
     *
     * @param name The name of the announcement to be removed.
     * @return A message indicating the result of the announcement removal.
     */
    public String removeAnnouncement(final String name) {
        if (announcements == null || announcements.stream()
                .noneMatch(announcement -> announcement.getName().equals(name))) {
            return username + " has no announcement with the given name.";
        }
        announcements.removeIf(announcement -> announcement.getName().equals(name));
        return username + " has successfully deleted the announcement.";
    }

    /**
     * Adds new merchandise with the specified details to the user's merchandise collection.
     *
     * @param name        The name of the new merchandise.
     * @param description The description of the new merchandise.
     * @param price       The price of the new merchandise.
     * @return A message indicating the result of the merchandise addition.
     */
    public String addMerch(final String name, final String description, final int price) {
        String message = "";
        if (!getType().equals("artist")) {
            message = username + " is not an artist.";
        } else if (price < 0) {
            message = "Price for merchandise can not be negative.";
        } else if (merchandise != null && merchandise.stream()
                .anyMatch(merch -> merch.getName().equals(name))) {
            message = username + " has merchandise with the same name.";
        } else {
            merchandise.add(new Merch(name, description, price));
            message = username + " has added new merchandise successfully.";
        }
        return message;
    }

    /**
     * Adds a new podcast with the specified name and episodes to the user's collection.
     *
     * @param name          The name of the new podcast.
     * @param episodeInputs The list of EpisodeInput objects representing episodes in the podcast.
     * @return A message indicating the result of the podcast addition.
     */
    public String addPodcast(final String name, final List<EpisodeInput> episodeInputs) {
        if (Admin.getPodcasts() == null) {
            Admin.setPodcasts(new ArrayList<>());
        }
        if (podcasts.stream().anyMatch(podcast -> podcast.getName().equals(name))) {
            return username + " has another podcast with the same name.";
        }
        ArrayList<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            Episode newEpisode = new Episode(episodeInput.getName(),
                    episodeInput.getDuration(), episodeInput.getDescription());
            if (episodes.stream().noneMatch(episode -> episode.getName()
                    .equals(newEpisode.getName()))) {
                episodes.add(newEpisode);
            } else {
                return username + " has the same episode at least twice in this podcast.";
            }
        }
        Podcast podcast = new Podcast(name, username, episodes);
        podcasts.add(podcast);
        Admin.getPodcasts().add(podcast);
        return username + " has added new podcast successfully.";
    }

    /**
     * Returns a list of PodcastOutput objects representing the user's podcasts.
     *
     * @return A list of PodcastOutput objects.
     */
    public ArrayList<PodcastOutput> showPodcasts() {
        ArrayList<PodcastOutput> podcastOutputs = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            podcastOutputs.add(new PodcastOutput(podcast));
        }

        return podcastOutputs;
    }

    /**
     * Returns the total number of likes received by the user for their albums.
     *
     * @return The total number of likes.
     */
    public Integer getLikes() {
        Integer likes = 0;
        if (albums == null) {
            return likes;
        }
        for (Album album : albums) {
            likes += album.getLikes();
        }
        return likes;
    }

    /**
     * Returns the album with the specified name from the user's collection.
     *
     * @param albumName The name of the album to retrieve.
     * @return The Album object or null if not found.
     */
    public Album getAlbum(final String albumName) {
        for (Album album : albums) {
            if (album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }

    /**
     * Checks if it is safe to remove a podcast with the specified name.
     *
     * @param name The name of the podcast to check.
     * @return True if safe to remove, false otherwise.
     */
    public boolean checkSafeRemovePodcast(final String name) {
        for (User user : Admin.getUsers()) {
            if (user.getPlayer().getSource() == null) {
                continue;
            }
            AudioCollection currentAudioCollection = user.getPlayer().
                    getSource().getAudioCollection();
            if (currentAudioCollection != null && currentAudioCollection
                    .getName().equals(name) && currentAudioCollection.getOwner()
                    .equals(username)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Changes the current page to the specified next page.
     *
     * @param nextPage The name of the next page.
     * @return A message indicating the result of the page change.
     */
    public String changePage(final String nextPage) {
        if (nextPage.equals("Home")) {
            currentPage = new HomePage(username);
        } else if (nextPage.equals("LikedContent")) {
            currentPage = new LikedContent(username);
        } else {
            return username + "is trying to access a non-existent page.";
        }
        return username + " accessed " + nextPage + " successfully.";
    }

    /**
     * Removes a podcast with the specified name from the user's collection.
     *
     * @param name The name of the podcast to be removed.
     * @return A message indicating the result of the podcast removal.
     */
    public String removePodcast(final String name) {
        if (podcasts == null || podcasts.stream().noneMatch(podcast -> podcast.getName()
                .equals(name))) {
            return username + " doesn't have a podcast with the given name.";
        }
        if (checkSafeRemovePodcast(name)) {
            podcasts.removeIf(podcast -> podcast.getName().equals(name));
            Admin.getPodcasts().removeIf(podcast -> podcast.getName().equals(name));
            return username + " deleted the podcast successfully.";
        } else {
            return username + " can't delete this podcast.";
        }
    }
}
