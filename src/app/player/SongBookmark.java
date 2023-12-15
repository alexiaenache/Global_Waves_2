package app.player;


import app.utils.Enums;
import lombok.Getter;

@Getter
public class SongBookmark {
    private final String name;
    private final int timestamp;
    private final boolean paused;
    private final Enums.RepeatMode repeat;

    public SongBookmark(final String name, final int timestamp, final boolean paused,
                        final Enums.RepeatMode repeat) {
        this.name = name;
        this.timestamp = timestamp;
        this.paused = paused;
        this.repeat = repeat;
    }

    /**
     * Returns a string representation of the SongBookmark.
     *
     * @return A string containing the name, timestamp, paused status,
     * and repeat mode of the SongBookmark.
     */
    @Override
    public String toString() {
        return "SongBookmark{"
                + "name='" + name + '\''
                + ", timestamp=" + timestamp
                + '}';
    }
}
