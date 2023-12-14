package app.audio.Collections;

import app.utils.Enums;

import java.util.ArrayList;

public class AlbumOutput {
    private final String name;
    private final ArrayList<String> songs;
//    private final String visibility = "public";
//    private final int followers = 0;
//    private final int timestamp;


    public AlbumOutput(Album album) {
        this.name = album.getName();
        this.songs = new ArrayList<>();
        for (int i = 0; i < album.getSongs().size(); i++) {
            songs.add(album.getSongs().get(i).getName());
        }
//        this.visibility = album.getVisibility() == Enums.Visibility.PRIVATE ? "private" : "public";
//        this.followers = album.getFollowers();
//        this.timestamp = album.getTimestamp();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSongs() {
        return songs;
    }
}
