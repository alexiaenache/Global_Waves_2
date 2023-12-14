package app.player;


import lombok.Getter;

@Getter
public class PlaylistBookmark {
    private final String name;
    private final int id;
    private final int timestamp;

    public PlaylistBookmark(String name, int id, int timestamp) {
        this.name = name;
        this.id = id;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PlaylistBookmark{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }
}
