package app.user;

public class Event {
    private String name;
    private String description;
    private String date;
    private int timestamp;
    private String artistName;

    public Event(String name, String description, String date, int timestamp, String artistName) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timestamp = timestamp;
        this.artistName = artistName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
