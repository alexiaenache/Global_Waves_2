package app;

import app.audio.Collections.Album;
import app.audio.Collections.AudioCollection;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.pages.HomePage;
import app.user.User;
import app.utils.Enums;
import fileio.input.*;

import java.util.*;

public class Admin {
    private static List<User> users = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp = 0;

    public static void addSong(Song song) {
        songs.add(song);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<Album> getAlbums() {
        return albums;
    }

    public static void setAlbums(List<Album> albums) {
        Admin.albums = albums;
    }

    public static void setUsers(List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }

    public static void setSongs(List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    public static void setPodcasts(List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    public static List<Song> getSongs() {
        return songs;
    }

    public static List<Podcast> getPodcasts() {
        return podcasts;
    }

    public static List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static void updateTimestamp(int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    public static List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);

        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= 5) break;
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    public static List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= 5) break;
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    public static List<String> getTop5Albums() {
        List<Album> sortedAlbums = new ArrayList<>(albums);
        sortedAlbums.sort(
                Comparator.<Album, Integer>comparing(Album::getLikes)
                        .reversed().thenComparing(Album::getName, String.CASE_INSENSITIVE_ORDER));

        List<String> topAlbums = new ArrayList<>();
        int count = 0;
        for (Album album : sortedAlbums) {
            if (count >= 5) break;
            topAlbums.add(album.getName());
            count++;
        }
        return topAlbums;
    }

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
            if (count >= 5) break;
            topArtists.add(artist.getUsername());
            count++;
        }
        return topArtists;
    }

    public static void defaultPage() {
        for (User user : users) {
            user.setCurrentPage(new HomePage(user.getUsername()));
        }
    }


    public static void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        timestamp = 0;
    }

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

    public static boolean checkSafeDelete(User user) {

        if (user.getType().equals("artist")) {
            ArrayList<Album> allAlbums = user.getAlbums();
            ArrayList<Song> allSongs = new ArrayList<>();
            for (Album album : allAlbums) {
                allSongs.addAll(album.getSongs());
            }
            for (User u : users) {
//                pentru fiecare user verific daca currenPage e pagina userului care trebuie sters
//                daca e returnez fals
//                altfel returnez true
                if (u.getCurrentPage().getName().equals(user.getUsername()) && !u.getUsername().equals(user.getUsername())) {
                    return false;
                }
                if (u.getPlayer().getSource() != null) {
                    AudioFile currentAudio = u.getPlayer().getSource().getAudioFile();
//                    System.out.println(currentAudio.getName());
                    for (Song song : allSongs) {
                        if (song.getName().equals(currentAudio.getName()) && song.getArtist().equals(user.getUsername())) {
                            return false;
                        }
                    }
                    if (allAlbums.contains(currentAudio)) {
                        return false;
                    }
                }
//               1. pt fiecare user verific daca are in currentPage pagina userului care trebuie sters
//               2. daca o are returnez fals
            }
            return true;
        } else if (user.getType().equals("host")) {
            ArrayList<Podcast> allPodcasts = user.getPodcasts();
//            ArrayList<Episode> allEpisodes = new ArrayList<>();
//            for (Podcast podcast : allPodcasts) {
//                allEpisodes.addAll(podcast.getEpisodes());
//            }
            for (User u : users) {
                if (u.getCurrentPage().getName().equals(user.getUsername()) && !u.getUsername().equals(user.getUsername())) {
                    return false;
                }
                if (u.getPlayer().getSource() != null) {
                    AudioCollection currentAudio = u.getPlayer().getSource().getAudioCollection();
                    if (allPodcasts.contains(currentAudio)) {
                        return false;
                    }
                }

            }
            return true;
        } else if (user.getType().equals("user")) {

//            pentru fiecare user verific daca asculta un playlist facut de userul pe care vreau sa il sterg
//            daca asculta returnez false
            for (User u : users) {
                if (u.getPlayer().getSource() != null) {
                    AudioCollection currentAudio = u.getPlayer().getSource().getAudioCollection();
                    if (currentAudio != null && currentAudio.getOwner().equals(user.getUsername())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void removeArtist(User u) {
        ArrayList<Album> allAlbums = u.getAlbums();
        ArrayList<Song> allSongs = new ArrayList<>();
        for (Album album : allAlbums) {
            allSongs.addAll(album.getSongs());
        }
//        for (Song song : allSongs) {
//            songs.remove(song);
//        }
        for (int i = 0; i < allSongs.size(); i++) {
            for (int j = 0; j < songs.size(); j++) {
                if (allSongs.get(i).getName().equals(songs.get(j).getName()) && allSongs.get(i).getArtist().equals(songs.get(j).getArtist())) {
                    songs.remove(j);
                }
            }
            System.out.println(allSongs.get(i).getName());
        }
//        1. pt fiecare user
//        2. pt fiecare playlist al userului
//        3. starege melodiile din albumul artistului
        for (User user : users) {
            ArrayList<Playlist> allPlaylists = user.getPlaylists();
            for (Playlist playlist : allPlaylists) {
                ArrayList<Song> allPlaylistSongs = playlist.getSongs();

                for (int i = 0; i < allPlaylistSongs.size(); i++) {
                    Song song = allPlaylistSongs.get(i);
                    for (int j = 0; j < allSongs.size(); j++) {
                        if (song.getName().equals(allSongs.get(j).getName()) && song.getArtist().equals(allSongs.get(j).getArtist())) {
                            allPlaylistSongs.remove(i);
                        }
                    }
                }
            }
            Iterator<Song> iterator = user.getLikedSongs().iterator();
            while (iterator.hasNext()) {
                Song song = iterator.next();
                for (int j = 0; j < allSongs.size(); j++) {
                    if (song.getName().equals(allSongs.get(j).getName()) && song.getArtist().equals(allSongs.get(j).getArtist())) {
                        iterator.remove();
                    }
                }
            }
        }
        for (Album album : allAlbums) {
            albums.remove(album);
        }
        users.remove(u);
    }

    public static void removeHost(User u) {
        ArrayList<Podcast> allPodcasts = u.getPodcasts();
        for (Podcast podcast : allPodcasts) {
            podcasts.remove(podcast);
        }
        users.remove(u);
    }

    public static void removeUser(User u) {
        users.remove(u);
        for (int i = 0; i < u.getLikedSongs().size(); i++) {
            Song song = u.getLikedSongs().get(i);
            for (int j = 0; j < songs.size(); j++) {
                if (song.getName().equals(songs.get(j).getName()) && song.getArtist().equals(songs.get(j).getArtist())) {
                    songs.get(j).dislike();
                }
            }
            for(User user : users) {
                Iterator<Song> iterator = user.getLikedSongs().iterator();
                while (iterator.hasNext()) {
                    Song song1 = iterator.next();
                    if (song1.getName().equals(song.getName()) && song1.getArtist().equals(song.getArtist())) {
                        song1.dislike();
                    }
                }
            }
        }
//        1.pentru fiecare user verific daca arein followed playlists unul dintre playlisturile userului care trebuie sters
//        2.daca il are il steg din lista de followed playlists
        for (User user : users) {
            ArrayList<Playlist> allPlaylists = user.getFollowedPlaylists();
            for (int i = 0; i < allPlaylists.size(); i++) {
                Playlist playlist = allPlaylists.get(i);
                if (playlist.getOwner().equals(u.getUsername())) {
                    allPlaylists.remove(i);
                }
            }
        }
//        1.pentru userul care trebuie sters verific ce playlisturi are la follow si le decrementez numarul de urmaritori cu 1
        ArrayList<Playlist> allPlaylists = u.getFollowedPlaylists();
        for (Playlist playlist : allPlaylists) {
            playlist.decreaseFollowers();
        }
    }

    public static boolean checkSafeDeleteAlbum(Album album) {
        for (User user : users) {
            if (user.getPlayer().getSource() != null) {
                String type = user.getPlayer().getSource().getType().toString().toLowerCase();
                if (type.equals("podcast")) {
                    return true;
                }

                AudioFile currentSong = user.getPlayer().getSource().getAudioFile();
                if (type.equals("song")) {
                    for (Song song : album.getSongs()) {
                        if (song.getName().equals(currentSong.getName())) {
                            return false;
                        }
                    }
                }

                AudioCollection currentAudio = user.getPlayer().getSource().getAudioCollection();
                if (type.equals("album") && album.equals(currentAudio)) {
                    return false;
                }
                if (type.equals("playlist")) {
                    Playlist playlist = (Playlist) currentAudio;
                    for (Song song : album.getSongs()) {
//                        verific daca in acest playlist exista un cantec din albumul pe care vreau sa il sterg
//                        daca exista returnez false
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

    public static String removeAlbum(String albumName, User user) {
        if (!user.getType().equals("artist")) {
            return user.getUsername() + " is not an artist.";
        }
        Album album = user.getAlbum(albumName);
        if (album == null) {
            return user.getUsername() + " doesn't have an album with the given name.";
        }
        if (!checkSafeDeleteAlbum(album)) {
            return user.getUsername() + " can't delete this album.";
        }
        Iterator<Song> iterator = songs.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            if (song.getAlbum().equals(albumName)) {
                iterator.remove();
            }
        }
        for (User u : users) {
            Iterator<Song> iterator1 = u.getLikedSongs().iterator();
            while (iterator1.hasNext()) {
                Song song = iterator1.next();
                if (song.getAlbum().equals(albumName)) {
                    iterator1.remove();
                }
            }
        }
        user.getAlbums().remove(album);
        albums.remove(album);
        return "Album deleted successfully";
    }
}
