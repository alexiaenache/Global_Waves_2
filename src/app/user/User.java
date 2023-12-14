package app.user;

import app.Admin;
import app.audio.Collections.*;
import app.audio.Files.AudioFile;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.audio.LibraryEntry;
import app.pages.*;
import app.player.*;
import app.searchBar.Filters;
import app.searchBar.SearchBar;
import app.utils.Enums;
import fileio.input.EpisodeInput;
import fileio.input.SongInput;
import lombok.Getter;
import net.sf.saxon.functions.registry.UseWhen30FunctionSet;

import java.util.ArrayList;
import java.util.List;

public class User extends LibraryEntry{
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
    private SongBookmark lastSongBookmark;
    private PlaylistBookmark lastPlaylistBookmark;
    private PodcastBookmark lastPodcastBookmark;

    public ArrayList<Podcast> getPodcasts() {
        return podcasts;
    }

    public void setPodcasts(ArrayList<Podcast> podcasts) {
        this.podcasts = podcasts;
    }

    public String getLastSearchType() {
        return lastSearchType;
    }

    public void setLastSearchType(String lastSearchType) {
        this.lastSearchType = lastSearchType;
    }

    public SongBookmark getLastSongBookmark() {
        return lastSongBookmark;
    }

    public void setLastSongBookmark(SongBookmark lastSongBookmark) {
        this.lastSongBookmark = lastSongBookmark;
    }

    public PlaylistBookmark getLastPlaylistBookmark() {
        return lastPlaylistBookmark;
    }

    public void setLastPlaylistBookmark(PlaylistBookmark lastPlaylistBookmark) {
        this.lastPlaylistBookmark = lastPlaylistBookmark;
    }

    public PodcastBookmark getLastPodcastBookmark() {
        return lastPodcastBookmark;
    }

    public void setLastPodcastBookmark(PodcastBookmark lastPodcastBookmark) {
        this.lastPodcastBookmark = lastPodcastBookmark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isConnectionStatus() {
        return connectionStatus;
    }

    public void setConnectionStatus(boolean connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
    }

    public void switchConnection() {
        connectionStatus = !connectionStatus;
        if(connectionStatus) {
            player.logIn();
        } else {
            player.logOut();
        }
    }
    public User(String username, int age, String city) {
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
    public User(String username, int age, String city, String type) {
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

    public LibraryEntry asLibraryEntry() {
        return new LibraryEntry(username) {
            @Override
            public boolean matchesName(String name) {
                return getName().toLowerCase().startsWith(name.toLowerCase());
            }
        };
    }

    public ArrayList<String> search(Filters filters, String type) {
        searchBar.clearSelection();
        player.stop();

        lastSearched = true;
        lastSearchType = type;
        ArrayList<String> results = new ArrayList<>();
        List<LibraryEntry> libraryEntries = searchBar.search(filters, type);

        for (LibraryEntry libraryEntry : libraryEntries) {
            results.add(libraryEntry.getName());
        }
        return results;
    }

    public String select(int itemNumber, String type) {
        if (!lastSearched)
            return "Please conduct a search before making a selection.";

        lastSearched = false;

        LibraryEntry selected = searchBar.select(itemNumber);

        if (selected == null)
            return "The selected ID is too high.";
        if(type.equals("song") || type.equals("podcast") || type.equals("album") || type.equals("playlist")) {
            return "Successfully selected %s.".formatted(selected.getName());
        } else {
            if(type.equals("artist")) {
                currentPage = new ArtistPage(selected.getName());
            } else if(type.equals("host")) {
                currentPage = new HostPage(selected.getName());
            }
            return "Successfully selected %s".formatted(selected.getName()) + "'s page.";
        }
    }

    public String load() {
        if (searchBar.getLastSelected() == null)
            return "Please select a source before attempting to load.";

        if (!searchBar.getLastSearchType().equals("song") && ((AudioCollection)searchBar.getLastSelected()).getNumberOfTracks() == 0) {
            return "You can't load an empty audio collection!";
        }

        player.setSource(searchBar.getLastSelected(), searchBar.getLastSearchType());
        searchBar.clearSelection();

        player.pause();

        return "Playback loaded successfully.";
    }

    public String playPause() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to pause or resume playback.";

        player.pause();

        if (player.getPaused())
            return "Playback paused successfully.";
        else
            return "Playback resumed successfully.";
    }

    public String repeat() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before setting the repeat status.";

        Enums.RepeatMode repeatMode = player.repeat();
        String repeatStatus = "";

        switch(repeatMode) {
            case NO_REPEAT -> repeatStatus = "no repeat";
            case REPEAT_ONCE -> repeatStatus = "repeat once";
            case REPEAT_ALL -> repeatStatus = "repeat all";
            case REPEAT_INFINITE -> repeatStatus = "repeat infinite";
            case REPEAT_CURRENT_SONG -> repeatStatus = "repeat current song";
        }

        return "Repeat mode changed to %s.".formatted(repeatStatus);
    }

    public String shuffle(Integer seed) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before using the shuffle function.";

        if (!player.getType().equals("playlist") && !player.getType().equals("album"))
            return "The loaded source is not a playlist or an album.";

        player.shuffle(seed);

        if (player.getShuffle())
            return "Shuffle function activated successfully.";
        return "Shuffle function deactivated successfully.";
    }

    public String forward() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before attempting to forward.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipNext();

        return "Skipped forward successfully.";
    }

    public String backward() {
        if (player.getCurrentAudioFile() == null)
            return "Please select a source before rewinding.";

        if (!player.getType().equals("podcast"))
            return "The loaded source is not a podcast.";

        player.skipPrev();

        return "Rewound successfully.";
    }

    public String like() {
        if (player.getCurrentAudioFile() == null) {
            return "Please load a source before liking or unliking.";
        }

        if (!player.getType().equals("song") && !player.getType().equals("playlist") && !player.getType().equals("album")) {
            return "Loaded source is not a song.";
        }

        Song song = (Song) player.getCurrentAudioFile();

        if (likedSongs.contains(song)) {
            likedSongs.remove(song);
            song.dislike();
            for (Song currentSong : Admin.getSongs()) {
                if (currentSong.getName().equals(song.getName())) {
                    currentSong.dislike();
                }
            }

            return "Unlike registered successfully.";
        }

        likedSongs.add(song);
        song.like();
        for (Song currentSong : Admin.getSongs()) {
            if (currentSong.getName().equals(song.getName())) {
                currentSong.like();
            }
        }
        return "Like registered successfully.";
    }

    public String next() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        player.next();

        if (player.getCurrentAudioFile() == null)
            return "Please load a source before skipping to the next track.";

        return "Skipped to next track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }

    public String prev() {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before returning to the previous track.";

        player.prev();

        return "Returned to previous track successfully. The current track is %s.".formatted(player.getCurrentAudioFile().getName());
    }

    public String createPlaylist(String name, int timestamp) {
        if (playlists.stream().anyMatch(playlist -> playlist.getName().equals(name)))
            return "A playlist with the same name already exists.";

        playlists.add(new Playlist(name, username, timestamp));

        return "Playlist created successfully.";
    }

    public String addRemoveInPlaylist(int Id) {
        if (player.getCurrentAudioFile() == null)
            return "Please load a source before adding to or removing from the playlist.";

        if (player.getType().equals("podcast"))
            return "The loaded source is not a song.";

        if (Id > playlists.size())
            return "The specified playlist does not exist.";

        Playlist playlist = playlists.get(Id - 1);

        if (playlist.containsSong((Song)player.getCurrentAudioFile())) {
            playlist.removeSong((Song)player.getCurrentAudioFile());
            return "Successfully removed from playlist.";
        }

        playlist.addSong((Song)player.getCurrentAudioFile());
        return "Successfully added to playlist.";
    }

    public String switchPlaylistVisibility(Integer playlistId) {
        if (playlistId > playlists.size())
            return "The specified playlist ID is too high.";

        Playlist playlist = playlists.get(playlistId - 1);
        playlist.switchVisibility();

        if (playlist.getVisibility() == Enums.Visibility.PUBLIC) {
            return "Visibility status updated successfully to public.";
        }

        return "Visibility status updated successfully to private.";
    }

    public ArrayList<PlaylistOutput> showPlaylists() {
        ArrayList<PlaylistOutput> playlistOutputs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            playlistOutputs.add(new PlaylistOutput(playlist));
        }

        return playlistOutputs;
    }

    public String follow() {
        LibraryEntry selection = searchBar.getLastSelected();
        String type = searchBar.getLastSearchType();

        if (selection == null)
            return "Please select a source before following or unfollowing.";

        if (!type.equals("playlist"))
            return "The selected source is not a playlist.";

        Playlist playlist = (Playlist)selection;

        if (playlist.getOwner().equals(username))
            return "You cannot follow or unfollow your own playlist.";

        if (followedPlaylists.contains(playlist)) {
            followedPlaylists.remove(playlist);
            playlist.decreaseFollowers();

            return "Playlist unfollowed successfully.";
        }

        followedPlaylists.add(playlist);
        playlist.increaseFollowers();


        return "Playlist followed successfully.";
    }

    public PlayerStats getPlayerStats() {
        if(isConnectionStatus()) {
            return player.getStats();
        }
        return player.getStats();
    }

    public ArrayList<String> showPreferredSongs() {
        ArrayList<String> results = new ArrayList<>();
        for (AudioFile audioFile : likedSongs) {
            results.add(audioFile.getName());
        }

        return results;
    }

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

    public void simulateTime(int time) {
        if(this.isConnectionStatus()) {
            player.simulatePlayer(time);
        }
    }
    public String addAlbum(String name, int timestamp, String description, int releaseYear, ArrayList<SongInput> songInputs) {
        if(albums == null) {
            albums = new ArrayList<>();
        }
        if(Admin.getAlbums() == null) {
            Admin.setAlbums(new ArrayList<>());
        }
        if (albums.stream().anyMatch(album -> album.getName().equals(name)))
            return getUsername() + " has another album with the same name.";
        ArrayList<Song> songs = new ArrayList<>();
        for (SongInput songInput : songInputs) {
            Song newSong  = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),songInput.getGenre(), songInput.getReleaseYear(),songInput.getArtist());
            if(songs.stream().noneMatch(song -> song.getName().equals(newSong.getName()))) {
                songs.add(newSong);
            } else {
                return username + " has the same song at least twice in this album.";
            }
            if(Admin.getSongs().stream().noneMatch(song -> song.getName().equals(songInput.getName()))) {
//                List<Song> songsAdmin = Admin.getSongs();
                Song s = new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(), songInput.getTags(),
                        songInput.getLyrics(),songInput.getGenre(), songInput.getReleaseYear(),songInput.getArtist());
                Admin.addSong(s);
            }
        }
        Album album = new Album(name, username, timestamp, description, releaseYear, songs);
        albums.add(album);
        Admin.getAlbums().add(album);
        return getUsername() +  " has added new album successfully.";

    }

    public ArrayList<AlbumOutput> showAlbums() {
        ArrayList<AlbumOutput> albumOutputs = new ArrayList<>();
        if(albums != null) {
            for (Album album : albums) {
                albumOutputs.add(new AlbumOutput(album));
            }
        }

        return albumOutputs;
    }
    public String printCurrentPage() {
        PrintVisitor P = new PrintVisitor();
        String message = currentPage.accept(P);
        return message;
    }
    public String addEvent(String name, String description, String date, int timestamp, String artistName) {
        if(events != null && events.stream().anyMatch(event -> event.getName().equals(name)))
            return username + " has another event with the same name.";
        if(events == null) {
            events = new ArrayList<>();
        }
        if(DateValidator.isValidDate(date)) {

            events.add(new Event(name, description, date, timestamp, artistName));
            return username + " has added new event successfully.";
        }
            return "Event for " + username + " does not have a valid date.";

    }
    public String removeEvent(String name) {
        if(events == null || events.stream().noneMatch(event -> event.getName().equals(name)))
            return username + " doesn't have an event with the given name.";
        events.removeIf(event -> event.getName().equals(name));
        return username + " deleted the event successfully.";
    }
    public String addAnnouncement(String name, String description) {
        if(announcements == null) {
            announcements = new ArrayList<>();
        }
        if(announcements.stream().anyMatch(announcement -> announcement.getName().equals(name)))
            return username + " has already added an announcement with this name.";
        announcements.add(new Announcement(name, description, username));
        return username + " has successfully added new announcement.";
    }
    public String removeAnnouncement(String name) {
        if(announcements == null || announcements.stream().noneMatch(announcement -> announcement.getName().equals(name)))
            return username + " has no announcement with the given name.";
        announcements.removeIf(announcement -> announcement.getName().equals(name));
        return username + " has successfully deleted the announcement.";
    }
    public String addMerch(String name, String description, int price) {
        String message = "";
        if (!getType().equals("artist")) {
            message = username + " is not an artist.";
        } else if(price < 0) {
            message = "Price for merchandise can not be negative.";
        } else if(merchandise != null && merchandise.stream().anyMatch(merch -> merch.getName().equals(name))) {
            message = username + " has merchandise with the same name.";
        } else {
            merchandise.add(new Merch(name, description, price));
            message = username + " has added new merchandise successfully.";
        }
        return message;
    }

    public String addPodcast(String name, String owner, List<EpisodeInput> episodeInputs) {
        if(Admin.getPodcasts() == null) {
            Admin.setPodcasts(new ArrayList<>());
        }
        if (podcasts.stream().anyMatch(podcast -> podcast.getName().equals(name)))
            return username + " has another podcast with the same name.";
        ArrayList<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : episodeInputs) {
            Episode newEpisode  = new Episode(episodeInput.getName(), episodeInput.getDuration(), episodeInput.getDescription());
            if(episodes.stream().noneMatch(episode -> episode.getName().equals(newEpisode.getName()))) {
                episodes.add(newEpisode);
            } else {
                return username + " has the same episode at least twice in this podcast.";
            }
        }
        Podcast podcast = new Podcast(name, username, episodes);
        podcasts.add(podcast);
        System.out.print(Admin.getPodcasts().size());
        Admin.getPodcasts().add(podcast);
        System.out.println(Admin.getPodcasts().size());
        return username + " has added new podcast successfully.";
    }

    public ArrayList<PodcastOutput> showPodcasts() {
        ArrayList<PodcastOutput> podcastOutputs = new ArrayList<>();
        for (Podcast podcast : podcasts) {
            podcastOutputs.add(new PodcastOutput(podcast));
        }

        return podcastOutputs;
    }
//    public String changePage(String page) {
//        if(page.equals("home") || page.equals("LikedContent")) {
//            currentPage = page;
//            return username + " accessed " + page + " successfully.";
//        } else {
//            return username + " is trying to access a non-existent page.";
//        }
//    }
    public Integer getLikes() {
        Integer likes = 0;
        if (albums == null) {
            return likes;
        }
        for(Album album: albums) {
            likes += album.getLikes();
        }
        return likes;
    }

    public Album getAlbum(String albumName) {
        for(Album album: albums) {
            if(album.getName().equals(albumName)) {
                return album;
            }
        }
        return null;
    }
    public boolean checkSafeRemovePodcast(String name) {
//        1.pentru fiecare user verific daca are in play podcastul
//        2.daca il are returnez false
//        3.daca nu il are returnez true
        for(User user: Admin.getUsers()) {
            if(user.getPlayer().getSource() == null) {
                continue;
            }
            AudioCollection currentAudioCollection = user.getPlayer().getSource().getAudioCollection();
            if(currentAudioCollection != null && currentAudioCollection.getName().equals(name) && currentAudioCollection.getOwner().equals(username)) {
                return false;
            }
        }
        return true;
    }
    public String changePage(String nextPage) {
        if (nextPage.equals("Home")) {
            currentPage = new HomePage(username);
        } else if (nextPage.equals("LikedContent")) {
            currentPage = new LikedContent(username);
        } else {
            return username + "is trying to access a non-existent page.";
        }
        return username + " accessed " + nextPage + " successfully.";
    }
    public String removePodcast(String name) {
        if (podcasts == null || podcasts.stream().noneMatch(podcast -> podcast.getName().equals(name)))
            return username + " doesn't have a podcast with the given name.";
        if (checkSafeRemovePodcast(name)) {
            podcasts.removeIf(podcast -> podcast.getName().equals(name));
            Admin.getPodcasts().removeIf(podcast -> podcast.getName().equals(name));
            return username + " deleted the podcast successfully.";
        } else {
            return username + " can't delete this podcast.";
        }
    }
}
