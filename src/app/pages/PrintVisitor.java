package app.pages;

import app.Admin;
import app.CommandRunner;
import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Announcement;
import app.user.Event;
import app.user.Merch;
import app.user.User;

import java.util.ArrayList;
import java.util.Comparator;

public class PrintVisitor implements PageVisitor{
    @Override
    public String visit(HomePage page) {
        User user = Admin.getUser(page.getName());
        StringBuilder likedSongsBuilder = new StringBuilder();
        ArrayList<Song> likedSongsSorted = new ArrayList<>(user.getLikedSongs());
        for (Song song : user.getLikedSongs()) {
            System.out.print(song.getName() + " song likes: " + song.getLikes() + ",");;
        }
        System.out.println();
//        ordonez melodiile descrescaror in functie de numarul de likeuri
        Comparator<Song> comparator = (Song s1, Song s2) -> s2.getLikes() - s1.getLikes();
        likedSongsSorted.sort(comparator);
        for (Song song : likedSongsSorted) {
            System.out.print(song.getName() + " song likes: " + song.getLikes() + ",");;
        }
        System.out.println();
        int no = 0;
        for (Song song : likedSongsSorted) {
            if(no >= 5) {
                break;
            }
            likedSongsBuilder.append(song.getName()).append(", ");
            no++;
        }

        StringBuilder followedPlaylistsBuilder = new StringBuilder();
        ArrayList<Playlist> followedPlaylistsSorted = new ArrayList<>(user.getFollowedPlaylists());
//        ordonez playlisturile descrescator in functie de numarul de likeuri
        followedPlaylistsSorted.sort(Comparator.comparingInt(Playlist::getLikes).reversed());
        no = 0;
        for (Playlist playlist : followedPlaylistsSorted) {
            if(no >= 5) {
                break;
            }
            followedPlaylistsBuilder.append(playlist.getName());
            no++;
        }

        String likedSongsString = likedSongsBuilder.toString().replaceAll(", $", "");
        String followedPlaylistsString = followedPlaylistsBuilder.toString().replaceAll(", $", "");

        String message = "Liked songs:\n\t[" + likedSongsString + "]\n\n" +
                "Followed playlists:\n\t[" + followedPlaylistsString + "]";

        return message;
    }
    @Override
    public String visit(ArtistPage page) {
        User artist = Admin.getUser(page.getName());
        ArrayList<Album> albums = artist.getAlbums();
        ArrayList<Event> events = artist.getEvents();
        ArrayList<Merch> merchandise = artist.getMerchandise();

        StringBuilder albumsBuilder = new StringBuilder();
        for (Album album : albums) {
            albumsBuilder.append(album.getName()).append(", ");
        }
        StringBuilder eventsBuilder = new StringBuilder();
        if(events != null) {
            for (Event event : events) {
                eventsBuilder.append(event.getName()).append(" - ").append(event.getDate()).append(":\n\t").append(event.getDescription()).append(", ");
            }
        }
        StringBuilder merchandiseBuilder = new StringBuilder();
        for (Merch merch : merchandise) {
            merchandiseBuilder.append(merch.getName()).append(" - ").append(merch.getPrice()).append(":\n\t").append(merch.getDescription()).append(", ");
        }
        String albumsString = albumsBuilder.toString().replaceAll(", $", "");
        String eventsString = eventsBuilder.toString().replaceAll(", $", "");
        String merchandiseString = merchandiseBuilder.toString().replaceAll(", $", "");
        String message = "Albums:\n\t[" + albumsString + "]\n\n" +
                "Merch:\n\t[" + merchandiseString + "]" + "\n\n" +
                "Events:\n\t[" + eventsString + "]";
        return message;
    }

    @Override
    public String visit(HostPage hostPage) {
        User host = Admin.getUser(hostPage.getName());
        ArrayList<Podcast> podcasts = host.getPodcasts();
        StringBuilder podcastsBuilder = new StringBuilder();
        for (Podcast podcast : podcasts) {
            podcastsBuilder.append(podcast.getName()).append(":\n\t[");
            for (int i = 0; i < podcast.getEpisodes().size(); i++) {
                Episode episode = podcast.getEpisodes().get(i);
                podcastsBuilder.append(episode.getName()).append(" - ").append(episode.getDescription());
                if(i != podcast.getEpisodes().size() - 1) {
                    podcastsBuilder.append(", ");
                }
            }
            podcastsBuilder.append("]\n, ");
        }
        ArrayList<Announcement> announcements = host.getAnnouncements();
        StringBuilder announcementsBuilder = new StringBuilder();
        for (Announcement announcement : announcements) {
            announcementsBuilder.append(announcement.getName()).append(":\n\t").append(announcement.getDescription()).append(", ");
        }
        String podcastsString = podcastsBuilder.toString().replaceAll(", $", "");
        String announcementsString = announcementsBuilder.toString().replaceAll(", $", "");
        String message = "Podcasts:\n\t[" + podcastsString + "]\n\n" +
                "Announcements:\n\t[" + announcementsString + "\n]";
        return message;
    }

    public String visit(LikedContent page) {
        User user = Admin.getUser(page.getName());
        StringBuilder likedSongsBuilder = new StringBuilder();
//        ArrayList<Song> likedSongsSorted = user.getLikedSongs();
////        ordonez melodiile descrescaror in functie de numarul de likeuri
//        likedSongsSorted.sort(Comparator.comparingInt(Song::getLikes).reversed());
        for (Song song : user.getLikedSongs()) {
            likedSongsBuilder.append(song.getName()).append(" - ").append(song.getArtist()).append(", ");
        }

        StringBuilder followedPlaylistsBuilder = new StringBuilder();
//        ArrayList<Playlist> followedPlaylistsSorted = user.getFollowedPlaylists();
//        ordonez playlisturile descrescator in functie de numarul de likeuri
//        followedPlaylistsSorted.sort(Comparator.comparingInt(Playlist::getLikes).reversed());
        for (Playlist playlist : user.getFollowedPlaylists()) {
            followedPlaylistsBuilder.append(playlist.getName()).append(" - ").append(playlist.getOwner()).append(", ");
        }

        String likedSongsString = likedSongsBuilder.toString().replaceAll(", $", "");
        String followedPlaylistsString = followedPlaylistsBuilder.toString().replaceAll(", $", "");

        String message = "Liked songs:\n\t[" + likedSongsString + "]\n\n" +
                "Followed playlists:\n\t[" + followedPlaylistsString + "]";

        return message;
    }
    @Override
    public String visit(Page page) {
        return null;
    }
}
