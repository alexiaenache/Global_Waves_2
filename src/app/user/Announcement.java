package app.user;

public class Announcement {
    private String name;
    private String description;

    private String hostName;

    public Announcement(String name, String description,  String hostName) {
        this.name = name;
        this.description = description;
        this.hostName = hostName;
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


    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
