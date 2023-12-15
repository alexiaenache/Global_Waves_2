package app.user;

public final class Event {
    private String name;
    private String description;
    private String date;
    private int timestamp;
    private String artistName;

    public Event(final String name, final String description, final String date,
                 final int timestamp, final String artistName) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.timestamp = timestamp;
        this.artistName = artistName;
    }

    public String getName() {
        return name;
    }
    /**
     * Sets the name of the event.
     *
     * @param name The new name for the event.
     */
    public void setName(final String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description The new name for the event.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }


    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of the event.
     *
     * @param timestamp The new name for the event.
     */
    public void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
}
