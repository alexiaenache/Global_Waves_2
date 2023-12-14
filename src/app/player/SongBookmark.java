package app.player;


import app.utils.Enums;
import lombok.Getter;

@Getter
public class SongBookmark {
    private final String name;
    private final int timestamp;
    private final boolean paused;
    private final Enums.RepeatMode repeat;

    public SongBookmark(String name, int timestamp, boolean paused, Enums.RepeatMode repeat) {
        this.name = name;
        this.timestamp = timestamp;
        this.paused = paused;
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        return "SongBookmark{" +
                "name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
