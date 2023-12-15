package app.pages;

import app.Admin;
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

public class PrintVisitor implements PageVisitor {
    public static final int MAX = 5;
    /**
     * Visits a HomePage and generates a formatted message based on the
     * user's liked songs and followed playlists.
     *
     * @param page The HomePage instance to visit.
     * @return A String representing the formatted message containing
     * liked songs and followed playlists.
     */
    @Override
    public String visit(final HomePage page) {
        User user = Admin.getUser(page.getName());
        StringBuilder likedSongsBuilder = new StringBuilder();
        ArrayList<Song> likedSongsSorted = new ArrayList<>(user.getLikedSongs());
        Comparator<Song> comparator = (Song s1, Song s2) -> s2.getLikes() - s1.getLikes();
        likedSongsSorted.sort(comparator);
        int no = 0;
        for (Song song : likedSongsSorted) {
            if (no >= MAX) {
                break;
            }
            likedSongsBuilder.append(song.getName()).append(", ");
            no++;
        }

        StringBuilder followedPlaylistsBuilder = new StringBuilder();
        ArrayList<Playlist> followedPlaylistsSorted
                = new ArrayList<>(user.getFollowedPlaylists());
        followedPlaylistsSorted.sort(Comparator.comparingInt(Playlist::getLikes).reversed());
        no = 0;
        for (Playlist playlist : followedPlaylistsSorted) {
            if (no >= MAX) {
                break;
            }
            followedPlaylistsBuilder.append(playlist.getName());
            no++;
        }

        String likedSongsString = likedSongsBuilder.toString()
                .replaceAll(", $", "");
        String followedPlaylistsString = followedPlaylistsBuilder
                .toString().replaceAll(", $", "");

        String message = "Liked songs:\n\t[" + likedSongsString + "]\n\n"
                + "Followed playlists:\n\t[" + followedPlaylistsString + "]";

        return message;
    }

    /**
     * Visits an ArtistPage and generates a formatted message based on the
     * artist's albums, events, and merchandise.
     *
     * @param page The ArtistPage instance to visit.
     * @return A String representing the formatted message containing albums,
     * events, and merchandise of the artist.
     */
    @Override
    public String visit(final ArtistPage page) {
        User artist = Admin.getUser(page.getName());
        ArrayList<Album> albums = artist.getAlbums();
        ArrayList<Event> events = artist.getEvents();
        ArrayList<Merch> merchandise = artist.getMerchandise();

        StringBuilder albumsBuilder = new StringBuilder();
        for (Album album : albums) {
            albumsBuilder.append(album.getName()).append(", ");
        }
        StringBuilder eventsBuilder = new StringBuilder();
        if (events != null) {
            for (Event event : events) {
                eventsBuilder.append(event.getName()).append(" - ")
                        .append(event.getDate()).append(":\n\t")
                        .append(event.getDescription()).append(", ");
            }
        }
        StringBuilder merchandiseBuilder = new StringBuilder();
        for (Merch merch : merchandise) {
            merchandiseBuilder.append(merch.getName()).append(" - ")
                    .append(merch.getPrice()).append(":\n\t")
                    .append(merch.getDescription()).append(", ");
        }
        String albumsString = albumsBuilder.toString().replaceAll(", $", "");
        String eventsString = eventsBuilder.toString().replaceAll(", $", "");
        String merchandiseString = merchandiseBuilder.toString()
                .replaceAll(", $", "");
        String message = "Albums:\n\t[" + albumsString + "]\n\n"
                + "Merch:\n\t[" + merchandiseString + "]" + "\n\n"
                + "Events:\n\t[" + eventsString + "]";
        return message;
    }

    /**
     * Visits a HostPage and generates a formatted message based on the host's
     * podcasts and announcements.
     *
     * @param hostPage The HostPage instance to visit.
     * @return A String representing the formatted message containing podcasts
     * and announcements of the host.
     */
    @Override
    public String visit(final HostPage hostPage) {
        User host = Admin.getUser(hostPage.getName());
        ArrayList<Podcast> podcasts = host.getPodcasts();
        StringBuilder podcastsBuilder = new StringBuilder();
        for (Podcast podcast : podcasts) {
            podcastsBuilder.append(podcast.getName()).append(":\n\t[");
            for (int i = 0; i < podcast.getEpisodes().size(); i++) {
                Episode episode = podcast.getEpisodes().get(i);
                podcastsBuilder.append(episode.getName()).append(" - ")
                        .append(episode.getDescription());
                if (i != podcast.getEpisodes().size() - 1) {
                    podcastsBuilder.append(", ");
                }
            }
            podcastsBuilder.append("]\n, ");
        }
        ArrayList<Announcement> announcements = host.getAnnouncements();
        StringBuilder announcementsBuilder = new StringBuilder();
        for (Announcement announcement : announcements) {
            announcementsBuilder.append(announcement.getName()).append(":\n\t")
                    .append(announcement.getDescription()).append(", ");
        }
        String podcastsString = podcastsBuilder.toString().replaceAll(", $", "");
        String announcementsString = announcementsBuilder.toString().replaceAll(", $", "");
        String message = "Podcasts:\n\t[" + podcastsString + "]\n\n"
                + "Announcements:\n\t[" + announcementsString + "\n]";
        return message;
    }

    /**
     * Visits a LikedContent page and generates a formatted message based on the user's
     * liked songs and followed playlists.
     *
     * @param page The LikedContent instance to visit.
     * @return A String representing the formatted message containing liked songs and
     * followed playlists of the user.
     */
    public String visit(final LikedContent page) {
        User user = Admin.getUser(page.getName());
        StringBuilder likedSongsBuilder = new StringBuilder();
        for (Song song : user.getLikedSongs()) {
            likedSongsBuilder.append(song.getName())
                    .append(" - ").append(song.getArtist()).append(", ");
        }

        StringBuilder followedPlaylistsBuilder = new StringBuilder();

        for (Playlist playlist : user.getFollowedPlaylists()) {
            followedPlaylistsBuilder.append(playlist.getName())
                    .append(" - ").append(playlist.getOwner()).append(", ");
        }

        String likedSongsString = likedSongsBuilder.toString().replaceAll(", $", "");
        String followedPlaylistsString = followedPlaylistsBuilder.toString().replaceAll(", $", "");

        String message = "Liked songs:\n\t[" + likedSongsString + "]\n\n"
                + "Followed playlists:\n\t[" + followedPlaylistsString + "]";

        return message;
    }

    /**
     * Visits a generic Page and returns null as it doesn't perform specific
     * operations for a generic page.
     *
     * @param page The Page instance to visit.
     * @return Always returns null as the method is not implemented for a generic page.
     */
    @Override
    public String visit(final Page page) {
        return null;
    }
}
