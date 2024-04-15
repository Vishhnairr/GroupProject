package Controller;
import Model.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final BufferedReader fromClient;
    private final PrintWriter toClient;
    private boolean loginSuccessful = false;
    private int situationCode = 0;  // 0: normal, 1: login success, 2: exit
    private String userNameFinal;  // only exist if login is successful
    private User thisUser;
    private static ArrayList<ClientHandler> clients;
    private static ConcurrentHashMap<String, ClientHandler> usersOnline;


    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientsList, ConcurrentHashMap<String, ClientHandler> onlineUsers) throws IOException {
        this.client = clientSocket;
        clients = clientsList;
        usersOnline = onlineUsers;
        fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        toClient = new PrintWriter(client.getOutputStream(), true);
    }

    public void run() {
        try {
            // ########################## LOG IN ##########################
            while (situationCode == 0) {

                int command = Integer.parseInt(fromClient.readLine());

                switch (command) {
                    case 1:
                        handleLogin();
                        break;
                    case 2:
                        handleCreateAccount();
                        break;
                    case 3:
                        situationCode = 2;
                        break;
                }
            }
            // ########################## LOG IN ##########################

            // ########################## MAIN MENU ##########################
            while (situationCode == 1) {

                int command = Integer.parseInt(fromClient.readLine());

                switch (command) {
                    case 1:
                        handleProfile();
                        break;
                    case 2:
                        handleSearch();
                        break;
                    case 3:
                        handleFriends();
                        break;
                    case 4:
                        handleBlocks();
                        break;
                    case 5:
                        handleMessage();
                        break;
                    case 6:
                        handleRequests();
                        break;
                    case 7:
                        situationCode = 2;
                        break;
                }
            }
            // ########################## MAIN MENU ##########################
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error in ClientHandler: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void handleLogin() throws IOException {
        while (situationCode == 0) {
            String userName = fromClient.readLine();
            String password = fromClient.readLine();
            User newUser = new User(userName, password);
            newUser = newUser.logIn();
            if (newUser != null && usersOnline.get(userName) == null) {  // Simplified check
                thisUser = newUser;
                toClient.println("SUCCESS");
                loginSuccessful = true;
                userNameFinal = userName;
                usersOnline.put(userName, this);
                situationCode = 1;
            } else if (usersOnline.get(userName) != null) {
                toClient.println("EXIST");
                situationCode = 2;
            } else {
                toClient.println("FAIL");
            }
        }
    }

    private void handleCreateAccount() throws IOException {
        try {
            while (situationCode == 0) {
                String username = fromClient.readLine();
                String password = fromClient.readLine();
                String firstName = fromClient.readLine();
                String lastName = fromClient.readLine();
                String email = fromClient.readLine();
                String bio = fromClient.readLine();
                User newUser = new User(username, password, firstName, lastName, email, bio, true);
                if (newUser.createAccount()) {
                    thisUser = newUser;
                    toClient.println("SUCCESS");
                    loginSuccessful = true;
                    userNameFinal = username;
                    usersOnline.put(username, this);
                    situationCode = 1;
                } else {
                    toClient.println("FAIL");
                }
            }
        } catch (Exception e) {
            toClient.println("Error creating account: " + e.getMessage());
        }
    }

    private void closeConnection() {
        try {
            if (loginSuccessful) {
                usersOnline.remove(userNameFinal);
            }
            clients.remove(this);
            fromClient.close();
            toClient.close();
            client.close();
        } catch (IOException e) {
            System.out.println("Failed to close resources: " + e.getMessage());
        }
    }

    private void handleProfile() {
        String profile = thisUser.toString();
        toClient.println(profile);
        try {
            int command = Integer.parseInt(fromClient.readLine());

            switch (command) {
                case 1:
                    handleChangeBio();
                    break;
                case 2:
                    handleChangeStatus();
                    break;
                case 3:
                    situationCode = 2;
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void handleSearch() throws IOException {
        String username = fromClient.readLine();
        User foundUser = User.searchUser(username);
        if (foundUser != null) {
            toClient.println("SUCCESS");
            String profile = foundUser.toString();
            toClient.println(profile);
            toClient.printf("%b,%b,%b,%b\n", foundUser.hasFriend(userNameFinal),
                    foundUser.getMessageCheck(), foundUser.hasBlock(userNameFinal), thisUser.hasBlock(username));
            int command = Integer.parseInt(fromClient.readLine());
            switch (command) {
                case 1:
                    handleSendRequest(username);
                    break;
                case 2:
                    handleSendMessage(username);
                    break;
                case 3:
                    handleMakeBlock(username);
                    break;
                case 4:
                    break;
            }
        } else {
            toClient.println("FAIL");
        }
    }

    private void handleFriends() throws IOException {
        String[] friends = thisUser.viewFriends(); // Retrieve the list of friend usernames
        if (friends.length == 0) {
            toClient.println("FAIL"); // Inform the client if the user has no friends
        } else {
            toClient.println("SUCCESS");
            for (String friend : friends) {
                toClient.println(friend); // Send each friend's username to the client
            }
            toClient.println("END"); // Signal the end of the list

            // Wait for the client to send the username of the selected friend
            String selectedFriendUsername = fromClient.readLine();
            User selectedFriend = User.searchUser(selectedFriendUsername); // Find the selected friend's User object


            toClient.println("FRIEND_FOUND");
            String profile = selectedFriend.toString();
            toClient.println(profile); // Send the selected friend's profile
            toClient.printf("%b,%b,%b\n",
                    selectedFriend.getMessageCheck(), // Is messaging public
                    thisUser.hasBlock(selectedFriend.getUsername()), // Current user has blocked the friend
                    selectedFriend.hasBlock(thisUser.getUsername())); // Friend has blocked the current user

            int action = Integer.parseInt(fromClient.readLine()); // Receive action command from client
            switch (action) {
                case 1:
                    handleRemoveFriend(selectedFriendUsername);
                    break;
                case 2:
                    handleSendMessage(selectedFriendUsername);
                    break;
                case 3:
                    handleMakeBlock(selectedFriendUsername);
                    break;
                case 4:
                    // Optionally handle returning to main menu or other actions
                    break;
            }
        }
    }

    private void handleRemoveFriend(String username) throws IOException {
        if (thisUser.removeFriend(username)) {
            toClient.println("SUCCESS");
        } else {
            toClient.println("FAIL");
        }
    }



    private void handleBlocks() throws IOException {
        String[] blockedUsers = thisUser.viewBlocks(); // Retrieve the list of blocked usernames
        if (blockedUsers.length == 0) {
            toClient.println("FAIL"); // Inform the client if the user has no blocked users
        } else {
            toClient.println("SUCCESS");
            for (String blockedUser : blockedUsers) {
                toClient.println(blockedUser); // Send each blocked user's username to the client
            }
            toClient.println("END"); // Signal the end of the list

            // Wait for the client to send the username of the selected blocked user
            String selectedBlockedUser = fromClient.readLine();
            User blockedUser = User.searchUser(selectedBlockedUser); // Find the selected blocked user's User object

            if (blockedUser != null) {
                toClient.println("BLOCKED_USER_FOUND");
                String profile = blockedUser.toString();
                toClient.println(profile); // Send the selected blocked user's profile

                int action = Integer.parseInt(fromClient.readLine()); // Receive action command from client
                switch (action) {
                    case 1:
                        handleRemoveBlock(selectedBlockedUser);
                        break;
                    case 2:
                        break;
                }
            } else {
                toClient.println("FAIL");
            }
        }
    }

    private void handleChangeBio() throws IOException {
        String bio = fromClient.readLine();
        if (thisUser.setBio(bio)) {
            toClient.println("SUCCESS");
        } else {
            toClient.println("FAIL");
        }
    }
    private void handleChangeStatus() throws IOException {
        if (thisUser.setMessageCheck(!thisUser.getMessageCheck())) {
            toClient.println("SUCCESS");
        } else {
            toClient.println("FAIL");
        }
    }
    private void handleMessage() throws IOException {
        ArrayList<String> messages = thisUser.viewAllMessageHistory();  // Retrieve the message history
        if (messages.isEmpty()) {
            toClient.println("FAIL");  // Inform the client if there are no messages
        } else {
            toClient.println("SUCCESS");
            for (String message : messages) {
                toClient.println(message);  // Send each message to the client
            }
            toClient.println("END");  // Signal the end of the list

            String command = fromClient.readLine();  // Receive command from client
            if ("DELETE".equals(command)) {
                if (thisUser.deleteAllMessages()) {  // Assuming a method to delete all messages
                    toClient.println("DELETED");
                } else {
                    toClient.println("FAIL_DELETE");
                }
            }
        }
    }


    private void handleRequests() throws IOException {
        // Load the list of friend requests
        ArrayList<String> requests = thisUser.viewFriendRequests();  // Assuming this method returns the list of requests
        if (requests.isEmpty()) {
            toClient.println("FAIL"); // No requests to display
        } else {
            toClient.println("SUCCESS");
            for (int i = 0; i < requests.size(); i++) {
                toClient.println(i + ": " + requests.get(i)); // Send each request with an index
            }
            toClient.println("END"); // End of request list

            // Wait for the client to choose an action on a request
            String line = fromClient.readLine();
            if (line.startsWith("ACCEPT:") || line.startsWith("REJECT:")) {
                int index = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                String requestUsername = requests.get(index);

                if (line.startsWith("ACCEPT")) {
                    if (thisUser.acceptFriendRequest(requestUsername)) {
                        toClient.println("ACCEPTED:" + requestUsername); // Inform the client of acceptance
                    } else {
                        toClient.println("FAIL_ACCEPT");
                    }
                } else if (line.startsWith("REJECT")) {
                    if (thisUser.rejectFriendRequest(requestUsername)) {
                        toClient.println("REJECTED:" + requestUsername); // Inform the client of rejection
                    } else {
                        toClient.println("FAIL_REJECT");
                    }
                }
            }
        }
    }

    private void handleSendRequest(String username) {
        if (thisUser.sendRequest(username)) {
            toClient.println("SUCCESS");
        } else {
            toClient.println("FAIL");
        }
    }
    private void handleSendMessage(String username) throws IOException {
        String aMessage = fromClient.readLine();
        MessageList message = new MessageList(aMessage, thisUser, username);
        if (message.sendMessage()) {
            toClient.println("SUCCESS");
        } else {
            toClient.println("FAIL");
        }
    }
    private void handleMakeBlock(String username) {
        if (thisUser.blockUser(username)) {
            toClient.println("SUCCESS");
        } else {
            toClient.println("FAIL");
        }
    }
    private void handleRemoveBlock(String username) {
        if (thisUser.removeBlock(username)) {
            toClient.println("SUCCESS");
        } else {
            toClient.println("FAIL");
        }
    }
}
