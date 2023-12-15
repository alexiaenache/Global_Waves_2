package app;

import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Admin {
    private static final int MAX = 5;
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp = 0;

    public static List<User> getUsers() {
        return users;
    }

    public static List<Album> getAlbums() {
        return albums;
    }

    public static List<Song> getSongs() {
        return songs;
    }

    public static List<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * Retrieves a list of all playlists from all users.
     * <p>
     * This method gathers playlists from all users and returns a combined list
     * containing playlists from each user.
     *
     * @return A list containing playlists from all users.
     */
    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Retrieves a user object based on the provided username.
     * <p>
     * This method searches through the list of users and returns the user object
     * with the specified username. If no user is found, it returns null.
     *
     * @param username The username of the user to retrieve.
     * @return The user object with the specified username, or null if not found.
     */
    public static User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Resets the state of the Admin class.
     * <p>
     * This method resets the state of the Admin class, clearing the lists of users,
     * songs, podcasts, and albums. It also resets the timestamp to zero.
     */
    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * Adds a new song to the collection of songs.
     * <p>
     * This method adds the provided song to the existing collection of songs.
     * The collection is typically managed by the 'songs' list in the 'Admin' class.
     * It is a convenient way to populate the list of songs with new entries.
     *
     * @param song The song to be added. It should not be null.
     */
    public static void addSong(final Song song) {
        songs.add(song);
    }


    /**
     * Sets the collection of albums in the Admin class.
     * <p>
     * This method replaces the existing collection of albums in the 'Admin' class
     * with the provided list of albums. It is a convenient way to update the list
     * of albums with a new set of entries.
     * <p>
     * Note: This operation completely replaces the current list of albums. If you
     * need to add or remove individual albums, consider using addAlbum() or
     * removeAlbum() methods respectively.
     *
     * @param albums The list of albums to set. It can be an empty list, but it should not be null.
     * @throws NullPointerException If the provided list of albums is null.
     */
    public static void setAlbums(final List<Album> albums) {
        Admin.albums = albums;
    }

    /**
     * Sets the collection of users in the Admin class based on the provided UserInput list.
     * <p>
     * This method replaces the existing collection of users in the 'Admin' class
     * with a new list of users created from the provided UserInput list. It is a
     * convenient way to update the list of users with a set of entries from UserInput objects.
     * <p>
     * Note: This operation completely replaces the current list of users. If you
     * need to add or remove individual users, consider using addUser() or
     * removeUser() methods respectively.
     *
     * @param userInputList The list of UserInput objects from which to create the new users.
     *                      It can be an empty list, but it should not be null.
     * @throws NullPointerException If the provided list of UserInput is null.
     */
    public static void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    /**
     * Sets the collection of songs in the Admin class based on the provided SongInput list.
     * <p>
     * This method replaces the existing collection of songs in the 'Admin' class
     * with a new list of songs created from the provided SongInput list. It is a
     * convenient way to update the list of songs with a set of entries from SongInput objects.
     * <p>
     * Note: This operation completely replaces the current list of songs. If you
     * need to add or remove individual songs, consider using addSong() or
     * removeSong() methods respectively.
     *
     * @param songInputList The list of SongInput objects from which to create the new songs.
     *                      It can be an empty list, but it should not be null.
     * @throws NullPointerException If the provided list of SongInput is null.
     */
    public static void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * Sets the collection of podcasts in the Admin class based on the provided PodcastInput list.
     * <p>
     * This method replaces the existing collection of podcasts in the 'Admin' class
     * with a new list of podcasts created from the provided PodcastInput list. It is a
     * convenient way to update the list of podcasts with a set of entries from
     * PodcastInput objects.
     * <p>
     * Note: This operation completely replaces the current list of podcasts. If you
     * need to add or remove individual podcasts, consider using addPodcast() or
     * removePodcast() methods respectively.
     *
     * @param podcastInputList The list of PodcastInput objects from which to create
     *                         the new podcasts.
     *                         It can be an empty list, but it should not be null.
     * @throws NullPointerException If the provided list of PodcastInput is null.
     */
    public static void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(),
                        episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }


    /**
     * Updates the timestamp and simulates time for all users in the system.
     * <p>
     * This method updates the timestamp with the provided value and simulates time
     * for all users in the system based on the elapsed time since the last timestamp.
     * The elapsed time is calculated as the difference between the new timestamp
     * and the current timestamp. If the elapsed time is zero, no simulation is performed.
     *
     * @param newTimestamp The new timestamp to set.
     */
    public static void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Retrieves a list of the top 5 songs based on the number of likes.
     * <p>
     * This method creates a sorted list of songs in descending order of likes
     * and returns the names of the top 5 songs. If there are fewer than 5 songs,
     * it includes all available songs in the result.
     *
     * @return A list of names of the top 5 songs, sorted by the number of likes.
     */
    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);

        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= MAX) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Retrieves a list of the top 5 playlists based on the number of followers and timestamp.
     * <p>
     * This method creates a sorted list of playlists in descending order of followers.
     * For playlists with the same number of followers, it further sorts them by timestamp
     * in ascending order. The method returns the names of the top 5 playlists. If there are
     * fewer than 5 playlists, it includes all available playlists in the result.
     *
     * @return A list of names of the top 5 playlists, sorted by followers and timestamp.
     */
    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= MAX) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Retrieves a list of the top 5 albums based on the number of likes and album name.
     * <p>
     * This method creates a sorted list of albums in descending order of likes.
     * For albums with the same number of likes, it further sorts them by album name
     * in case-insensitive alphabetical order. The method returns the names of the top 5 albums.
     * If there are fewer than 5 albums, it includes all available albums in the result.
     *
     * @return A list of names of the top 5 albums, sorted by likes and album name.
     */
    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(albums);
        sortedAlbums.sort(
                Comparator.<Album, Integer>comparing(Album::getLikes)
                        .reversed().thenComparing(Album::getName, String.CASE_INSENSITIVE_ORDER));

        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= MAX) {
                break;
            }
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

    /**
     * Retrieves a list of the top 5 artists based on the number of likes and artist username.
     * <p>
     * This method creates a sorted list of artists in descending order of likes.
     * For artists with the same number of likes, it further sorts them by username
     * in case-insensitive alphabetical order. The method returns the usernames of the
     * top 5 artists.
     * If there are fewer than 5 artists, it includes all available artists in the result.
     *
     * @return A list of usernames of the top 5 artists, sorted by likes and username.
     */
    public static List<String> getTop5Artists() {
        List<User> sortedArtists = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals("artist")) {
                sortedArtists.add(user);
            }
        }
        sortedArtists.sort(
                Comparator.<User, Integer>comparing(User::getLikes)
                        .reversed()
                        .thenComparing(User::getUsername, String.CASE_INSENSITIVE_ORDER));

        List<String> topArtists = new ArrayList<>();
        int count = 0;
        for (User artist : sortedArtists) {
            if (count >= MAX) {
                break;
            }
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }

    /**
     * Retrieves a list of usernames for all users in the system, including users, artists
     * and hosts.
     * <p>
     * This method creates a list of usernames for all users in the system, regardless
     * of their type.
     * It includes users, artists, and hosts in the result in this specific order.
     *
     * @return An ArrayList containing usernames for all users in the system.
     */
    public static ArrayList<String> getAllUsers() {
        ArrayList<String> usernames = new ArrayList<>();
        for (User user : users) {
            if (user.getType().equals("user")) {
                usernames.add(user.getUsername());
            }
        }
        for (User user : users) {
            if (user.getType().equals("artist")) {
                usernames.add(user.getUsername());
            }
        }
        for (User user : users) {
            if (user.getType().equals("host")) {
                usernames.add(user.getUsername());
            }
        }
        return usernames;
    }

    /**
     * Checks if it is safe to delete the specified user from the system.
     * <p>
     * This method verifies whether it is safe to delete a user based on their type
     * (artist, host, or user). For artists, it checks if any other user is currently
     * viewing the artist's page or listening to their songs. For hosts, it checks if
     * any other user is currently viewing the host's page or listening to their podcasts.
     * For regular users, it checks if any other user is currently listening to a playlist
     * created by the user to be deleted.
     *
     * @param user The user to be checked for safe deletion.
     * @return True if it is safe to delete the user; false otherwise.
     */
    public static boolean checkSafeDelete(final User user) {

        if (user.getType().equals("artist")) {
            ArrayList<Album> allAlbums = user.getAlbums();
            ArrayList<Song> allSongs = new ArrayList<>();
            for (Album album : allAlbums) {
                allSongs.addAll(album.getSongs());
            }
            for (User u : users) {
                // For each user, check if their current page is the page of the user to be deleted
                // If it is, return false; otherwise, return true.
                if (u.getCurrentPage().getName().equals(user.getUsername()) && !u.getUsername()
                        .equals(user.getUsername())) {
                    return false;
                }
//                For each user, check if they are currently listening to a song by the artist
//                to be deleted. If they are, return false; otherwise, return true.
                if (u.getPlayer().getSource() != null) {
                    AudioFile currentAudio = u.getPlayer().getSource().getAudioFile();
                    for (Song song : allSongs) {
                        if (song.getName().equals(currentAudio.getName()) && song.getArtist()
                                .equals(user.getUsername())) {
                            return false;
                        }
                    }
//                    For each user, check if they are currently listening to an album by the artist
//                    to be deleted. If they are, return false; otherwise, return true.
                    if (allAlbums.contains(currentAudio)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (user.getType().equals("host")) {
            ArrayList<Podcast> allPodcasts = user.getPodcasts();
//            For each user, check if their current page is the page of the user to be deleted
//            If it is, return false; otherwise, return true.
            for (User u : users) {
                if (u.getCurrentPage().getName().equals(user.getUsername()) && !u.getUsername()
                        .equals(user.getUsername())) {
                    return false;
                }
//                For each user, check if they are currently listening to a podcast by the host
//                to be deleted. If they are, return false; otherwise, return true.
                if (u.getPlayer().getSource() != null) {
                    AudioCollection currentAudio = u.getPlayer().getSource().getAudioCollection();
                    if (allPodcasts.contains(currentAudio)) {
                        return false;
                    }
                }

            }
            return true;
        } else if (user.getType().equals("user")) {
//            For each user, check if they are currently listening to a playlist by the user
//            to be deleted. If they are, return false; otherwise, return true.
            for (User u : users) {
                if (u.getPlayer().getSource() != null) {
                    AudioCollection currentAudio = u.getPlayer().getSource().
                            getAudioCollection();
                    if (currentAudio != null && currentAudio.getOwner().
                            equals(user.getUsername())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Removes an artist from the system, including their albums, songs.
     * <p>
     * This method removes an artist from the system, including all their albums, songs,
     * and the impact on playlists and liked songs of other users. It iterates through the
     * songs of the artist and removes them from the system's song list. It also removes
     * the artist's albums, songs from playlists of all users, and liked songs from all users.
     *
     * @param u The artist user to be removed from the system.
     */
    public static void removeArtist(final User u) {
        ArrayList<Album> allAlbums = u.getAlbums();
        ArrayList<Song> allSongs = new ArrayList<>();
//        make a list with all the songs from the artist's albums
        for (Album album : allAlbums) {
            allSongs.addAll(album.getSongs());
        }
//        remove the songs from the system's song list
        for (int i = 0; i < allSongs.size(); i++) {
            for (int j = 0; j < songs.size(); j++) {
                if (allSongs.get(i).getName().equals(songs.get(j).getName())
                        && allSongs.get(i).getArtist().equals(songs.get(j).getArtist())) {
                    songs.remove(j);
                }
            }
        }
//        remove the songs from the playlists of all users
        for (User user : users) {
            ArrayList<Playlist> allPlaylists = user.getPlaylists();
            for (Playlist playlist : allPlaylists) {
                ArrayList<Song> allPlaylistSongs = playlist.getSongs();

                for (int i = 0; i < allPlaylistSongs.size(); i++) {
                    Song song = allPlaylistSongs.get(i);
                    for (int j = 0; j < allSongs.size(); j++) {
                        if (song.getName().equals(allSongs.get(j).getName())
                                && song.getArtist().equals(allSongs.get(j).getArtist())) {
                            allPlaylistSongs.remove(i);
                        }
                    }
                }
            }
//            remove the songs from the liked songs of all users
            Iterator<Song> iterator = user.getLikedSongs().iterator();
            while (iterator.hasNext()) {
                Song song = iterator.next();
                for (int j = 0; j < allSongs.size(); j++) {
                    if (song.getName().equals(allSongs.get(j).getName())
                            && song.getArtist().equals(allSongs.get(j).getArtist())) {
                        iterator.remove();
                    }
                }
            }
        }
//        remove the albums from the system's album list
        for (Album album : allAlbums) {
            albums.remove(album);
        }
        users.remove(u);
    }

    /**
     * Removes a host user from the system, including their podcasts.
     * <p>
     * This method removes a host user from the system, including all their podcasts.
     * It iterates through the podcasts of the host and removes them from the system's podcast list.
     * The host user is then removed from the system's user list.
     *
     * @param u The host user to be removed from the system.
     */
    public static void removeHost(final User u) {
        ArrayList<Podcast> allPodcasts = u.getPodcasts();
//        remove the podcasts from the system's podcast list
        for (Podcast podcast : allPodcasts) {
            podcasts.remove(podcast);
        }
        users.remove(u);
    }

    /**
     * Removes a user from the system, including handling liked songs, followed playlists,
     * and decrements playlist followers.
     * <p>
     * This method removes a user from the system and performs the following actions:
     * 1. Removes the user from the system's user list.
     * 2. Iterates through the user's liked songs and decrements the likes of those songs.
     * 3. Decreases the followers count of playlists followed by the user.
     * 4. Erase all the playlists created by the user
     *
     * @param u The user to be removed from the system.
     */
    public static void removeUser(final User u) {
        users.remove(u);
//        decrement the likes of the songs liked by the user
        for (int i = 0; i < u.getLikedSongs().size(); i++) {
            Song song = u.getLikedSongs().get(i);
            for (int j = 0; j < songs.size(); j++) {
                if (song.getName().equals(songs.get(j).getName())
                        && song.getArtist().equals(songs.get(j).getArtist())) {
                    songs.get(j).dislike();
                }
            }
//            for each user decrement the likes of the songs liked by the user
            for (User user : users) {
                Iterator<Song> iterator = user.getLikedSongs().iterator();
                while (iterator.hasNext()) {
                    Song song1 = iterator.next();
                    if (song1.getName().equals(song.getName())
                            && song1.getArtist().equals(song.getArtist())) {
                        song1.dislike();
                    }
                }
            }
        }
//        erase all the playlists created by the user
        for (User user : users) {
            ArrayList<Playlist> allPlaylists = user.getFollowedPlaylists();
            for (int i = 0; i < allPlaylists.size(); i++) {
                Playlist playlist = allPlaylists.get(i);
                if (playlist.getOwner().equals(u.getUsername())) {
                    allPlaylists.remove(i);
                }
            }
        }
//        decrease the followers count of the playlists followed by the user
        ArrayList<Playlist> allPlaylists = u.getFollowedPlaylists();
        for (Playlist playlist : allPlaylists) {
            playlist.decreaseFollowers();
        }
    }

    /**
     * Checks if it is safe to delete an album from the system, considering users' current playback
     * sources.
     * <p>
     * This method determines whether it is safe to delete an album from the system by checking the
     * following conditions:
     * 1. If any user is currently playing a podcast, it is safe to delete the album.
     * 2. If any user is currently playing a song from the album, it is not safe to delete
     * the album.
     * 3. If any user is currently playing the album itself, it is not safe to delete the album.
     * 4. If any user is currently playing a playlist containing songs from the album,
     * it is not safe to delete the album.
     *
     * @param album The album to be checked for safe deletion.
     * @return {@code true} if it is safe to delete the album, {@code false} otherwise.
     */
    public static boolean checkSafeDeleteAlbum(final Album album) {
        for (User user : users) {
            if (user.getPlayer().getSource() != null) {
//               check if any user is currently playing a podcast
//                if yes, it is safe to delete the album
                String type = user.getPlayer().getSource().getType().toString().toLowerCase();
                if (type.equals("podcast")) {
                    return true;
                }
//                check if any user is currently playing a song from the album
//                if yes, it is not safe to delete the album
                AudioFile currentSong = user.getPlayer().getSource().getAudioFile();
                if (type.equals("song")) {
                    for (Song song : album.getSongs()) {
                        if (song.getName().equals(currentSong.getName())) {
                            return false;
                        }
                    }
                }
//                check if any user is currently playing the album itself
//                if yes, it is not safe to delete the album
                AudioCollection currentAudio = user.getPlayer().getSource().getAudioCollection();
                if (type.equals("album") && album.equals(currentAudio)) {
                    return false;
                }
//                check if any user is currently playing a playlist containing songs from the album
//                if yes, it is not safe to delete the album
                if (type.equals("playlist")) {
                    Playlist playlist = (Playlist) currentAudio;
                    for (Song song : album.getSongs()) {
                        for (Song playlistSong : playlist.getSongs()) {
                            if (song.getName().equals(playlistSong.getName())) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Removes an album owned by an artist, including associated songs, from the system.
     * <p>
     * This method allows an artist to remove one of their albums from the system.
     * The removal includes the following steps:
     * 1. Check if the user is an artist; if not, return an error message.
     * 2. Retrieve the album with the specified name owned by the artist.
     * 3. If the album doesn't exist, return an error message.
     * 4. Check if it is safe to delete the album (considering users' current playback sources);
     * if not, return an error message.
     * 5. Remove all songs from the system that belong to the specified album.
     * 6. Remove songs from users' liked songs playlists if they liked songs from the deleted album.
     * 7. Remove the album from the artist's list of albums and the global list of albums.
     * 8. Return a success message indicating that the album was deleted successfully.
     *
     * @param albumName The name of the album to be removed.
     * @param user      The artist who owns the album and wants to remove it.
     * @return A string message indicating the result of the removal operation.
     */
    public static String removeAlbum(final String albumName, final User user) {
//        check if the user is an artist
        if (!user.getType().equals("artist")) {
            return user.getUsername() + " is not an artist.";
        }
//        retrieve the album with the specified name is owned by the artist
        Album album = user.getAlbum(albumName);
        if (album == null) {
            return user.getUsername() + " doesn't have an album with the given name.";
        }
//        check if it is safe to delete the album
        if (!checkSafeDeleteAlbum(album)) {
            return user.getUsername() + " can't delete this album.";
        }
//        remove all songs from the system that belong to the specified album
        Iterator<Song> iterator = songs.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            if (song.getAlbum().equals(albumName)) {
                iterator.remove();
            }
        }
//        remove songs from users' liked songs playlists if they liked songs from the deleted album
        for (User u : users) {
            Iterator<Song> iterator1 = u.getLikedSongs().iterator();
            while (iterator1.hasNext()) {
                Song song = iterator1.next();
                if (song.getAlbum().equals(albumName)) {
                    iterator1.remove();
                }
            }
        }
//        remove the album from the artist's list of albums and the global list of albums
        user.getAlbums().remove(album);
        albums.remove(album);
        return "Album deleted successfully";
    }
}
