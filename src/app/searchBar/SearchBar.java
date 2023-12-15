package app.searchBar;


import app.Admin;
import app.audio.LibraryEntry;
import app.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static app.searchBar.FilterUtils.filterByAlbum;
import static app.searchBar.FilterUtils.filterByArtist;
import static app.searchBar.FilterUtils.filterByDescription;
import static app.searchBar.FilterUtils.filterByFollowers;
import static app.searchBar.FilterUtils.filterByGenre;
import static app.searchBar.FilterUtils.filterByLyrics;
import static app.searchBar.FilterUtils.filterByName;
import static app.searchBar.FilterUtils.filterByOwner;
import static app.searchBar.FilterUtils.filterByPlaylistVisibility;
import static app.searchBar.FilterUtils.filterByReleaseYear;
import static app.searchBar.FilterUtils.filterByTags;

public class SearchBar {
    private List<LibraryEntry> results;
    private final String user;
    private static final Integer MAX_RESULTS = 5;
    @Getter
    private String lastSearchType;

    @Getter
    private LibraryEntry lastSelected;

    public SearchBar(final String user) {
        this.results = new ArrayList<>();
        this.user = user;
    }

    /**
     * Clears the selection by resetting the lastSelected and lastSearchType attributes.
     */
    public void clearSelection() {
        lastSelected = null;
        lastSearchType = null;
    }

    /**
     * Searches for library entries based on the specified filters and type.
     *
     * @param filters The filters to apply during the search.
     * @param type    The type of library entries to search for.
     * @return A list of library entries matching the search criteria.
     */
    public List<LibraryEntry> search(final Filters filters, final String type) {
        List<LibraryEntry> entries;

        switch (type) {
            case "song":
                entries = new ArrayList<>(Admin.getSongs());

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getAlbum() != null) {
                    entries = filterByAlbum(entries, filters.getAlbum());
                }

                if (filters.getTags() != null) {
                    entries = filterByTags(entries, filters.getTags());
                }

                if (filters.getLyrics() != null) {
                    entries = filterByLyrics(entries, filters.getLyrics());
                }

                if (filters.getGenre() != null) {
                    entries = filterByGenre(entries, filters.getGenre());
                }

                if (filters.getReleaseYear() != null) {
                    entries = filterByReleaseYear(entries, filters.getReleaseYear());
                }

                if (filters.getArtist() != null) {
                    entries = filterByArtist(entries, filters.getArtist());
                }

                break;
            case "playlist":
                entries = new ArrayList<>(Admin.getPlaylists());

                entries = filterByPlaylistVisibility(entries, user);

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                if (filters.getFollowers() != null) {
                    entries = filterByFollowers(entries, filters.getFollowers());
                }

                break;
            case "podcast":
                entries = new ArrayList<>(Admin.getPodcasts());

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                break;
            case "artist":
                entries = new ArrayList<>(Admin.getUsers().size());
                for (User user : Admin.getUsers()) {
                    if (user.getType().equals("artist")) {
                        entries.add(user.asLibraryEntry());
                    }
                }
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }
                break;
            case "album":
                entries = new ArrayList<>(Admin.getAlbums());

                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }

                if (filters.getOwner() != null) {
                    entries = filterByOwner(entries, filters.getOwner());
                }

                if (filters.getDescription() != null) {
                    entries = filterByDescription(entries, filters.getDescription());
                }
                break;
            case "host":
                entries = new ArrayList<>(Admin.getUsers().size());
                for (User user : Admin.getUsers()) {
                    if (user.getType().equals("host")) {
                        entries.add(user.asLibraryEntry());
                    }
                }
                if (filters.getName() != null) {
                    entries = filterByName(entries, filters.getName());
                }
                break;
            default:
                entries = new ArrayList<>();
        }

        while (entries.size() > MAX_RESULTS) {
            entries.remove(entries.size() - 1);
        }

        this.results = entries;
        this.lastSearchType = type;
        return this.results;
    }

    /**
     * Selects a library entry from the search results based on the item number.
     *
     * @param itemNumber The item number representing the position of the entry
     *                   in the search results.
     * @return The selected library entry or null if the item number is invalid.
     */
    public LibraryEntry select(final Integer itemNumber) {
        if (this.results.size() < itemNumber) {
            results.clear();

            return null;
        } else {
            lastSelected = this.results.get(itemNumber - 1);
            results.clear();

            return lastSelected;
        }
    }
}
