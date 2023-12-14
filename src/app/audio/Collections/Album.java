package app.audio.Collections;

import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import app.utils.Enums;
import fileio.input.SongInput;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public final class Album extends AudioCollection {
    private final ArrayList<Song> songs;
    private Enums.Visibility visibility;
    private String description;
    private int releaseYear;
    private Integer followers;
    private int timestamp;

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Album(String name, String owner, int timestamp , String description , int releaseYear, ArrayList<Song> songs) {
        super(name, owner);
        this.songs = songs;
        this.description = description;
        this.releaseYear = releaseYear;
        this.timestamp = timestamp;
    }

    public boolean containsSong(Song song) {
        return songs.contains(song);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }
    public void removeSong(int index) {
        songs.remove(index);
    }

    public void switchVisibility() {
        if (visibility == Enums.Visibility.PUBLIC) {
            visibility = Enums.Visibility.PRIVATE;
        } else {
            visibility = Enums.Visibility.PUBLIC;
        }
    }

    public void increaseFollowers() {
        followers++;
    }

    public void decreaseFollowers() {
        followers--;
    }

    @Override
    public int getNumberOfTracks() {
        return songs.size();
    }

    @Override
    public AudioFile getTrackByIndex(int index) {
        return songs.get(index);
    }

    @Override
    public boolean isVisibleToUser(String user) {
        return this.getVisibility() == Enums.Visibility.PUBLIC ||
                (this.getVisibility() == Enums.Visibility.PRIVATE && this.getOwner().equals(user));
    }

    @Override
    public boolean matchesFollowers(String followers) {
        return filterByFollowersCount(this.getFollowers(), followers);
    }

    private static boolean filterByFollowersCount(int count, String query) {
        if (query.startsWith("<")) {
            return count < Integer.parseInt(query.substring(1));
        } else if (query.startsWith(">")) {
            return count > Integer.parseInt(query.substring(1));
        } else {
            return count == Integer.parseInt(query);
        }
    }

    public int getLikes() {
        int likes = 0;
        for(Song song : songs) {
            likes += song.getLikes();
        }
        return likes;
    }
}
