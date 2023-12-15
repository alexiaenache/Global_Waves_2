package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.LibraryEntry;
import lombok.Getter;

@Getter
public abstract class AudioCollection extends LibraryEntry {
    private final String owner;

    public AudioCollection(final String name, final String owner) {
        super(name);
        this.owner = owner;
    }

/**
 * Abstract method to retrieve the number of tracks associated with the object.
 *
 * Subclasses implementing this method should provide functionality to determine the total
 * number of tracks associated with the object.
 *
 * @return The number of tracks associated with the object.
 */
    public abstract int getNumberOfTracks();

/**
 * Abstract method to retrieve an audio file based on its index.
 *
 * Subclasses implementing this method should provide functionality to retrieve
 * an audio file based on the specified index.
 *
 * @param index The index of the audio file to retrieve.
 * @return The audio file corresponding to the specified index.
 */
    public abstract AudioFile getTrackByIndex(int index);

    @Override
    public final boolean matchesOwner(final String user) {
        return this.getOwner().equals(user);
    }
}
