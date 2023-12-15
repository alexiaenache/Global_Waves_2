package app.audio;

import lombok.Getter;

import java.util.ArrayList;

@Getter
public abstract class LibraryEntry {
    private final String name;

    public LibraryEntry(final String name) {
        this.name = name;
    }

/**
 * Checks if the song's name matches the specified name.
 *
 * @param name The name to check against.
 * @return {@code true} if the song's name starts with the specified name
 * (case-insensitive), {@code false} otherwise.
 */
    public boolean matchesName(final String name) {
        return getName().toLowerCase().startsWith(name.toLowerCase());
    }

/**
 * Checks if the song belongs to the specified album.
 *
 * @param album The album to check against.
 * @return {@code true} if the song belongs to the specified album, {@code false} otherwise.
 */
    public boolean matchesAlbum(final String album) {
        return false;
    }

/**
 * Checks if the playlist matches the specified list of tags.
 *
 * @param tags The list of tags to check.
 * @return {@code true} if the playlist matches the tags, {@code false} otherwise.
 */
    public boolean matchesTags(final ArrayList<String> tags) {
        return false;
    }

/**
 * Checks if the playlist matches the specified lyrics.
 *
 * @param lyrics The lyrics to check.
 * @return {@code true} if the playlist matches the lyrics, {@code false} otherwise.
 */
    public boolean matchesLyrics(final String lyrics) {
        return false;
    }

/**
 * Checks if the playlist matches the specified genre.
 *
 * @param genre The genre to check.
 * @return {@code true} if the playlist matches the genre, {@code false} otherwise.
 */
    public boolean matchesGenre(final String genre) {
        return false;
    }

/**
 * Checks if the playlist matches the specified artist.
 *
 * @param artist The artist to check.
 * @return {@code true} if the playlist matches the artist, {@code false} otherwise.
 */
    public boolean matchesArtist(final String artist) {
        return false;
    }

/**
 * Checks if the playlist matches the specified release year.
 *
 * @param releaseYear The release year to check.
 * @return {@code true} if the playlist matches the release year, {@code false} otherwise.
 */
    public boolean matchesReleaseYear(final String releaseYear) {
        return false;
    }

/**
 * Checks if the playlist matches the specified owner (user).
 *
 * @param user The owner (user) to check.
 * @return {@code true} if the playlist matches the owner, {@code false} otherwise.
 */
    public boolean matchesOwner(final String user) {
        return false;
    }

/**
 * Checks if the playlist is visible to the specified user.
 *
 * @param user The user to check visibility for.
 * @return {@code true} if the playlist is visible to the user, {@code false} otherwise.
 */
    public boolean isVisibleToUser(final String user) {
        return false;
    }

/**
 * Checks if the playlist matches the specified number of followers.
 *
 * @param followers The number of followers to check.
 * @return {@code true} if the playlist matches the followers count, {@code false} otherwise.
 */
    public boolean matchesFollowers(final String followers) {
        return false;
    }


/**
 * Checks if the playlist matches the specified description.
 *
 * @param description The description to check.
 * @return {@code true} if the playlist matches the description, {@code false} otherwise.
 */
    public boolean matchesDescription(final String description) {
        return false;
    }
}
