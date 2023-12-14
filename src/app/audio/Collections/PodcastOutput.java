package app.audio.Collections;

import java.util.ArrayList;

public class PodcastOutput {
    private final String name;
    private final ArrayList<String> episodes;
//    private final String owner;

    public PodcastOutput(Podcast podcast) {
        this.name = podcast.getName();
        this.episodes = new ArrayList<>();
        for (int i = 0; i < podcast.getEpisodes().size(); i++) {
            episodes.add(podcast.getEpisodes().get(i).getName());
        }
//        this.owner = podcast.getOwner();
    }

    public PodcastOutput(String name, ArrayList<String> episodes, String owner) {
        this.name = name;
        this.episodes = episodes;
//        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getEpisodes() {
        return episodes;
    }

//    public String getOwner() {
//        return owner;
//    }
}
