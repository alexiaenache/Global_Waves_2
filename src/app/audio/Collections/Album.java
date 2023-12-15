package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Album extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private String description;
    private int releaseYear;
    private int timestamp;

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public String getDescription() {
        return description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Album(final String name, final String owner, final int timestamp,
                 final String description, final int releaseYear, final ArrayList<Song> songs) {
        super(name, owner);
        this.songs = songs;
        this.description = description;
        this.releaseYear = releaseYear;
        this.timestamp = timestamp;
    }

/**
 * Switches the visibility of an object between public and private.
 *
 * This method toggles the visibility of an object between public and private.
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
 * Checks if the object is visible to a specific user.
 *
 * This method determines whether the object is visible to a particular user based on its visibility
 * setting
 * and ownership.
 *
 * @param user The username of the user for whom visibility is being checked.
 * @return {@code true} if the object is public or if it is private and owned by the specified user;
 *         {@code false} otherwise.
 */
    @Override
    public boolean isVisibleToUser(final String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC
                || (this.getVisibility() == Enums.Visibility.PRIVATE
                && this.getOwner().equals(user));
    }

    /**
    * Retrieves the total number of likes for the object.
    *
    * This method calculates and returns the cumulative number of likes for all songs
     * associated with the object.
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
