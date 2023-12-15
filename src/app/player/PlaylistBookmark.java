package app.player;


import lombok.Getter;

@Getter
public class PlaylistBookmark {
    private final String name;
    private final int id;
    private final int timestamp;

    public PlaylistBookmark(final String name, final int id, final int timestamp) {
        this.name = name;
        this.id = id;
        this.timestamp = timestamp;
    }

    /**
     * Returns a string representation of the PlaylistBookmark.
     *
     * @return A string containing the name, id, and timestamp of the PlaylistBookmark.
     */
    @Override
    public String toString() {
        return "PlaylistBookmark{"
                + "name='" + name + '\''
                + ", id=" + id
                + ", timestamp=" + timestamp
                + '}';
    }
}
