package app.user;

public final class Announcement {
    private String name;
    private String description;


    public Announcement(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the announcement.
     *
     * @param name The new name for the announcement.
     */
    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the announcement.
     *
     * @param description The new description for the announcement.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

}
