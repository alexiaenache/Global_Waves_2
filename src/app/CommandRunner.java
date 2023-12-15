package app;

import app.audio.Collections.AlbumOutput;
import app.audio.Collections.PlaylistOutput;
import app.audio.Collections.PodcastOutput;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

public class CommandRunner {
    private static ObjectMapper objectMapper = new ObjectMapper();

/**
 * Executes a search based on the provided command input, filters, and user information.
 * Generates a response in the form of a JSON ObjectNode containing search results.
 *
 * @param commandInput The input containing the search command, username, filters, type,
 *                     and timestamp.
 * @return An ObjectNode representing the response, including search results and status
 * information.
 */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            Filters filters = new Filters(commandInput.getFilters());
            String type = commandInput.getType();

            ArrayList<String> results = user.search(filters, type);
            String message = "Search returned " + results.size() + " results";

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);
            objectNode.put("results", objectMapper.valueToTree(results));
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");
        objectNode.put("results", objectMapper.valueToTree(new ArrayList<String>()));
        return objectNode;


    }

/**
 * Executes a select operation based on the provided command input, user information,
 * and selected item number.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the select command, username, item number,
 *                     and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.select(commandInput.getItemNumber(), user.getLastSearchType());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");
        return objectNode;
    }

/**
 * Executes a load operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the load command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.load();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes a play/pause operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the play/pause command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.playPause();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes a repeat operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the repeat command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.repeat();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes a shuffle operation based on the provided command input, user
 * information, and optional seed.
 * Generates a response in the form of a JSON ObjectNode containing status
 * information.
 *
 * @param commandInput The input containing the shuffle command, username,
 *                     timestamp, and optional seed.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            Integer seed = commandInput.getSeed();
            String message = user.shuffle(seed);

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes a forward operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the forward command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.forward();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes a backward operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the backward command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.backward();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes a like operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the like command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.like();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);
            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.getUsername() + " is offline.");
        return objectNode;

    }

/**
 * Executes a next operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the next command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.next();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes a previous operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the prev command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.prev();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes the creation of a playlist based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the createPlaylist command, username, playlist name,
 *                     and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.createPlaylist(commandInput.getPlaylistName(),
                    commandInput.getTimestamp());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes the add/remove operation in a playlist based on the provided
 * command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status
 * information.
 *
 * @param commandInput The input containing the addRemoveInPlaylist command,
 *                     username, playlist ID,
 *                     and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes the switchVisibility operation for a playlist based on the provided
 * command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing status
 * information.
 *
 * @param commandInput The input containing the switchVisibility command,
 *                     username, playlist ID,
 *                     and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes the showPlaylists operation based on the provided command
 * input and user information.
 * Generates a response in the form of a JSON ObjectNode containing the
 * list of playlists.
 *
 * @param commandInput The input containing the showPlaylists command,
 *                     username, and timestamp.
 * @return An ObjectNode representing the response, including the list
 * of playlists.
 */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

/**
 * Executes the follow operation based on the provided command input
 * and user information.
 * Generates a response in the form of a JSON ObjectNode containing
 * status information.
 *
 * @param commandInput The input containing the follow command,
 *                     username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.follow();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", user.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes the status operation based on the provided command input and user information.
 * Generates a response in the form of a JSON ObjectNode containing player statistics.
 *
 * @param commandInput The input containing the status command, username, and timestamp.
 * @return An ObjectNode representing the response, including player statistics.
 */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;

    }

/**
 * Executes the showLikedSongs operation based on the provided command
 * input and user information.
 * Generates a response in the form of a JSON ObjectNode containing the
 * list of liked songs.
 *
 * @param commandInput The input containing the showLikedSongs command,
 *                     username, and timestamp.
 * @return An ObjectNode representing the response, including the list of liked songs.
 */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

/**
 * Executes the getPreferredGenre operation based on the provided command
 * input and user information.
 * Generates a response in the form of a JSON ObjectNode containing the preferred genre.
 *
 * @param commandInput The input containing the getPreferredGenre command,
 *                     username, and timestamp.
 * @return An ObjectNode representing the response, including the preferred genre.
 */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

/**
 * Executes the getTop5Songs operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the top 5 songs.
 *
 * @param commandInput The input containing the getTop5Songs command and timestamp.
 * @return An ObjectNode representing the response, including the top 5 songs.
 */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

/**
 * Executes the getTop5Playlists operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the top 5 playlists.
 *
 * @param commandInput The input containing the getTop5Playlists command and timestamp.
 * @return An ObjectNode representing the response, including the top 5 playlists.
 */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

/**
 * Executes the switchConnectionStatus operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the switchConnectionStatus command,
 *                     username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (user.getType().equals("user")) {
            user.switchConnection();
            message = user.getUsername() + " has changed status successfully.";
        } else {
            message = user.getUsername() + " is not a normal user.";
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

/**
 * Executes the getOnlineUsers operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the list of online users.
 *
 * @param commandInput The input containing the getOnlineUsers command and timestamp.
 * @return An ObjectNode representing the response, including the list of online users.
 */
    public static ObjectNode getOnlineUsers(final CommandInput commandInput) {
        List<User> users = Admin.getUsers();
        ArrayList<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isConnectionStatus() && user.getType().equals("user")) {
                onlineUsers.add(user.getUsername());
            }
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));

        return objectNode;
    }

/**
 * Executes the addUser operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the addUser command, username, age,
 *                     city, type, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode addUser(final CommandInput commandInput) {
        List<User> users = Admin.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(commandInput.getUsername())) {
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("command", commandInput.getCommand());
                objectNode.put("user", commandInput.getUsername());
                objectNode.put("timestamp", commandInput.getTimestamp());
                objectNode.put("message", "The username " + commandInput.getUsername()
                        + " is already taken.");

                return objectNode;
            }
        }
        Admin.getUsers().add(new User(commandInput.getUsername(), commandInput.getAge(),
                commandInput.getCity(), commandInput.getType()));
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", "The username " + commandInput.getUsername()
                + " has been added successfully.");

        return objectNode;
    }

/**
 * Executes the addAlbum operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the addAlbum command, username, name, timestamp,
 *                     description, release year, and list of songs.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        List<User> users = Admin.getUsers();
        User u = null;
        User artist = null;
        for (User user : users) {
            if (user.getUsername().equals(commandInput.getUsername())) {
                u = user;
                if (user.getType().equals("artist")) {
                    artist = user;
                    ObjectNode objectNode = objectMapper.createObjectNode();
                    String message = user.addAlbum(commandInput.getName(),
                            commandInput.getTimestamp(),
                            commandInput.getDescription(), commandInput.getReleaseYear(),
                            commandInput.getSongs());
                    objectNode.put("command", commandInput.getCommand());
                    objectNode.put("user", commandInput.getUsername());
                    objectNode.put("timestamp", commandInput.getTimestamp());
                    objectNode.put("message", message);

                    return objectNode;
                }
            }
        }
        String m = "";
        if (u == null && artist == null) {
            m = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (u != null && artist == null) {
            m = commandInput.getUsername() + " is not an artist.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", m);

        return objectNode;
    }


/**
 * Executes the showAlbums operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the list of albums.
 *
 * @param commandInput The input containing the showAlbums command, username, and timestamp.
 * @return An ObjectNode representing the response, including the list of albums.
 */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<AlbumOutput> albums = user.showAlbums();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", albums == null
                ? objectMapper.valueToTree(new ArrayList<AlbumOutput>())
                : objectMapper.valueToTree(albums));
        return objectNode;
    }

/**
 * Executes the printCurrentPage operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the printed page information.
 *
 * @param commandInput The input containing the printCurrentPage command, username, and timestamp.
 * @return An ObjectNode representing the response, including the printed page information.
 */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        if (user.isConnectionStatus()) {
            String message = user.printCurrentPage();

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put("user", commandInput.getUsername());
            objectNode.put("command", commandInput.getCommand());
            objectNode.put("timestamp", commandInput.getTimestamp());
            objectNode.put("message", message);

            return objectNode;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", commandInput.getUsername() + " is offline.");

        return objectNode;
    }

/**
 * Executes the addEvent operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the addEvent command, username, name,
 *                     description, date, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            message = user.addEvent(commandInput.getName(), commandInput.getDescription(),
                    commandInput.getDate(), commandInput.getTimestamp(),
                    commandInput.getUsername());
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

/**
 * Executes the removeEvent operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the removeEvent command, username, name, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("artist")) {
            message = commandInput.getUsername() + " is not an artist.";
        } else {
            message = user.removeEvent(commandInput.getName());
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;

    }

/**
 * Executes the addMerch operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the addMerch command, username, name,
 *                     description, price, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user != null) {
            message = user.addMerch(commandInput.getName(), commandInput.getDescription(),
                    commandInput.getPrice());
        } else {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

/**
 * Executes the addAnnouncement operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the addAnnouncement command, username,
 *                     name, description, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            message = user.addAnnouncement(commandInput.getName(), commandInput.getDescription());
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

/**
 * Executes the removeAnnouncement operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the removeAnnouncement command, username,
 *                     name, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            message = user.removeAnnouncement(commandInput.getName());
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }


/**
 * Executes the getAllUsers operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the list of all usernames.
 *
 * @param commandInput The input containing the getAllUsers command and timestamp.
 * @return An ObjectNode representing the response, including the list of all usernames.
 */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        ArrayList<String> usernames = new ArrayList<>(Admin.getAllUsers());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(usernames));

        return objectNode;
    }

/**
 * Executes the deleteUser operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the deleteUser command, username, and timestamp.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        List<User> users = Admin.getUsers();
        User u = null;
        for (User user : users) {
            if (user.getUsername().equals(commandInput.getUsername())) {
                u = user;
                break;
            }
        }
        String message = "";
        if (u == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            if (Admin.checkSafeDelete(u)) {
                users.remove(u);
                if (u.getType().equals("artist")) {
                    Admin.removeArtist(u);
                } else if (u.getType().equals("host")) {
                    Admin.removeHost(u);
                } else {
                    Admin.removeUser(u);
                }
                message = commandInput.getUsername() + " was successfully deleted.";
            } else {
                message = commandInput.getUsername() + " can't be deleted.";
            }

        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

/**
 * Executes the addPodcast operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the addPodcast command, username, name,
 *                     and list of episodes.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        List<User> users = Admin.getUsers();
        User u = null;
        User host = null;
        for (User user : users) {
            if (user.getUsername().equals(commandInput.getUsername())) {
                u = user;
                if (user.getType().equals("host")) {
                    ObjectNode objectNode = objectMapper.createObjectNode();
                    String message = user.addPodcast(commandInput.getName(),
                            commandInput.getEpisodes());
                    objectNode.put("command", commandInput.getCommand());
                    objectNode.put("user", commandInput.getUsername());
                    objectNode.put("timestamp", commandInput.getTimestamp());
                    objectNode.put("message", message);

                    return objectNode;
                }
            }
        }
        String m = "";
        if (u == null && host == null) {
            m = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (u != null && host == null) {
            m = commandInput.getUsername() + " is not a host.";
        }

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", m);

        return objectNode;
    }

/**
 * Executes the removePodcast operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the removePodcast command, username, and name.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else if (!user.getType().equals("host")) {
            message = commandInput.getUsername() + " is not a host.";
        } else {
            message = user.removePodcast(commandInput.getName());
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

/**
 * Executes the showPodcasts operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the list of podcasts.
 *
 * @param commandInput The input containing the showPodcasts command, username, and timestamp.
 * @return An ObjectNode representing the response, including the list of podcasts.
 */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        ArrayList<PodcastOutput> podcasts = user.showPodcasts();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(podcasts));
        return objectNode;
    }

/**
 * Executes the getTop5Albums operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the top 5 albums.
 *
 * @param commandInput The input containing the getTop5Albums command and timestamp.
 * @return An ObjectNode representing the response, including the top 5 albums.
 */
    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        List<String> topAlbums = Admin.getTop5Albums();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(topAlbums));

        return objectNode;
    }

/**
 * Executes the getTop5Artists operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing the top 5 artists.
 *
 * @param commandInput The input containing the getTop5Artists command and timestamp.
 * @return An ObjectNode representing the response, including the top 5 artists.
 */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        List<String> topArtists = Admin.getTop5Artists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(topArtists));

        return objectNode;
    }

/**
 * Executes the removeAlbum operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the removeAlbum command, username, and album name.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String albumName = commandInput.getName();
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = Admin.removeAlbum(albumName, user);
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

/**
 * Executes the changePage operation based on the provided command input.
 * Generates a response in the form of a JSON ObjectNode containing status information.
 *
 * @param commandInput The input containing the changePage command, username, and next
 *                     page information.
 * @return An ObjectNode representing the response, including the status message.
 */
    public static ObjectNode changePage(final CommandInput commandInput) {
        User user = Admin.getUser(commandInput.getUsername());
        String message = "";
        if (user == null) {
            message = "The username " + commandInput.getUsername() + " doesn't exist.";
        } else {
            message = user.changePage(commandInput.getNextPage());
        }
        ObjectNode objectNode = objectMapper.createObjectNode();

        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);
        return objectNode;
    }

}
