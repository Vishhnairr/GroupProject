package Model;// imports here

import java.io.*;
import java.util.ArrayList;

/**
 * User
 * <p>
 * This class creates a User with a first name, a last name, an email, a bio, a username
 * a password and a file that holds users. This method is able to check that every input is correct.
 * It also holds the methods of creating a user, logging in to an account, and editing an account if
 * the user desires. There is also a method to check the amount of users on a created file and to
 * confirm there is more than one for the main method to use.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 * @version March 31, 2024
 */

public class User {
    private String firstName; // holds first name of User

    private String lastName; // holds last name of User

    private String email; // holds email of User

    private String bio; // holds bio (description) of User

    private String username; // holds username of User

    private String password; // holds password of User

    private File userFile; // holds file with User's information

    private File friends; // the friend list of user

    private File block; // the block users list of this user

    private File friendRequest; // the file of friends requests

    private boolean messageCheck; // if the user want to receive the message from public or friends only

    // empty constructor
    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.bio = null;
        this.username = null;
        this.password = null;
        this.userFile = null;
        this.friends = null;
        this.block = null;
        this.friendRequest = null;
        this.messageCheck = false;
    }

    // creates custom constructor
    public User(String username, String password, String firstName, String lastName, String email, String bio,
                boolean message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.username = username;
        this.password = password;
        this.userFile = null;
        this.friends = null;
        this.block = null;
        this.friendRequest = null;
        this.messageCheck = message;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // returns first name
    public String getFirstName() {
        return this.firstName;
    }

    // returns username name
    public String getUsername() {
        return this.username;
    }

    // returns last name
    public String getLastName() {
        return this.lastName;
    }

    // returns bio
    public String getBio() {
        return this.bio;
    }

    // returns email
    public String getEmail() {
        return this.email;
    }

    // returns password
    public String getPassword() {
        return this.password;
    }

    // returns the file of user
    public File getUserFile() {
        return this.userFile;
    }

    // returns the friends list of the user
    public File getFriends() {
        return this.friends;
    }

    // returns the blocking users of this user
    public File getBlock() {
        return this.block;
    }

    public File getFriendRequest() {
        return this.friendRequest;
    }


    public boolean getMessageCheck() {
        return  this.messageCheck;
    }

    // sets first name
    public synchronized boolean setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty() || firstName.contains(" ")) {
            return false;
        }
        this.firstName = firstName;

        this.setAccountFile();
        return true;
    }

    // sets last name
    public synchronized boolean setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty() || lastName.contains(" ")) {
            return false;
        }
        this.lastName = lastName;

        this.setAccountFile();
        return true;
    }

    // sets username
    public synchronized boolean setUsername(String username) {
        ArrayList<String> allUserNames = new ArrayList<>();
        try {
            File file = new File("All_User_Info.txt");
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                allUserNames.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }


        for (int i = 0; i < allUserNames.size(); i++) {
            if (allUserNames.get(i).equals(username)) {
                return false;
            }
        }


        if (username == null || username.isEmpty() || username.contains(" ")) {
            return false;
        }
        allUserNames.set(allUserNames.indexOf(this.getUsername()), username);


        try {
            File allUsersFile = new File("All_User_Info.txt");
            FileOutputStream fosAll = new FileOutputStream(allUsersFile, false);
            PrintWriter pwAll = new PrintWriter(fosAll);
            for (int j = 0; j < allUserNames.size(); j++) {
                pwAll.println(allUserNames.get(j));
            }
            pwAll.close();
        } catch (Exception e) {
            return false;
        }


        File newFile = new File("User_" + username + ".txt");
        File userFile = new File("User_" + this.username + ".txt");
        boolean check = userFile.renameTo(newFile);
        this.username = username;
        this.setAccountFile();

        return check;
    }

    // sets email
    public synchronized boolean setEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@") || email.contains(" ") ||
                email.charAt(email.length() - 4) != '.') {
            return false;
        }
        this.email = email;

        this.setAccountFile();
        return true;
    }

    // sets bio
    public synchronized boolean setBio(String bio) {
        if (bio.length() > 50) {
            return false;
        } else {
            this.bio = bio;
            this.setAccountFile();
            return true;
        }
    }

    // sets password
    public synchronized boolean setPassword(String password) {
        if (password.length() < 4) {
            return false;
        } else {
            this.password = password;

            this.setAccountFile();
            return true;
        }
    }

    public synchronized boolean setMessageCheck(boolean check) {
        this.messageCheck = check;

        this.setAccountFile();
        return true;
    }
    public synchronized boolean setAccountFile() {
        try {
            File userFile = new File("User_" + username + ".txt");
            FileOutputStream fos = new FileOutputStream(userFile, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println("User name: " + username);
            pw.println("Password: " + password);
            pw.println("First name: " + firstName);
            pw.println("Last name: " + lastName);
            pw.println("Email: " + email);
            pw.println("Bio: " + bio);
            pw.println("Message only: " + messageCheck);
            pw.close();
        } catch (IOException e) {
            return false;
        }

        this.userFile = new File("User_" + username + ".txt");
        return true;
    }

    public synchronized void setFriend(File file) {
        this.friends = file;
    }

    public synchronized void setBlock(File file) {
        this.block = file;
    }

    public synchronized void setFriendRequest(File file) {
        this.friendRequest = file;
    }
    // verifies if account exists for User
    public synchronized boolean checkAccountExists() throws IOException {
        ArrayList<String> allUserNames = new ArrayList<>();
        File file = new File("All_User_Info.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bfr = new BufferedReader(fr);
        String line = bfr.readLine();

        while (line != null) {
            allUserNames.add(line);
            line = bfr.readLine();
        }
        bfr.close();

        for (int i = 0; i < allUserNames.size(); i++) {
            if (allUserNames.get(i).equals(this.username)) {
                return true;
            }
        }
        return false;
    }


    // creates new account based on info from scanner in main method
    public synchronized boolean createAccount() throws IOException {
        if (this.firstName == null || this.firstName.isEmpty() || this.firstName.contains(" ") ||
                lastName == null || lastName.isEmpty() || lastName.contains(" ") ||
                password.length() < 4 || bio.length() > 50 || email == null || email.isEmpty()) {
            return false;
        }
        ArrayList<String> allUserNames = new ArrayList<>();
        try {
            File file = new File("All_User_Info.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader fr = new FileReader(file);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                allUserNames.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }
        for (int i = 0; i < allUserNames.size(); i++) {
            if (allUserNames.get(i).equals(username)) {
                return false;
            }
        }
        // writing account file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("User_" + username + ".txt", false))) {
            pw.println("User name: " + username);
            pw.println("Password: " + password); // Assume hashPassword is a method to hash passwords
            pw.println("First name: " + firstName);
            pw.println("Last name: " + lastName);
            pw.println("Email: " + email);
            pw.println("Bio: " + bio);
            pw.println("Message only: " + messageCheck);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        this.setFriend(new File("User_" + this.getUsername() + "_Friends.txt"));
        this.setBlock(new File("User_" + this.getUsername() + "_Block.txt"));
        try {
            this.friends.createNewFile();
            this.block.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (PrintWriter pwAll = new PrintWriter(new FileOutputStream("All_User_Info.txt", true))) {
            pwAll.println(username);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        User newUser = new User("luuuuuul", "666666");
        System.out.println(newUser.logIn());
    }
    // verifies login based on info from scanner in main method
    public synchronized User logIn() {
        File allUser = new File("All_User_Info.txt");
        int check = 0;

        try {
            FileReader fr = new FileReader(allUser);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                if (line.equals(username)) {
                    check = 1;
                    break;
                }
                line = bfr.readLine();
            }
            bfr.close();

        } catch (Exception e) {
            return null;
        }

        if (check == 0) {
            return null;
        }

        File userFile = new File("User_" + username + ".txt");
        ArrayList<String> userData = new ArrayList<>();

        try {
            FileReader fr = new FileReader(userFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                userData.add(line.substring(line.indexOf(": ") + 1).trim());
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return null;
        }

        User user = null;
        if (userData.size() == 7) {
            if (userData.get(1).equals(password)) {
                user = new User(userData.get(0), userData.get(1), userData.get(2),
                        userData.get(3), userData.get(4), userData.get(5),
                        Boolean.parseBoolean(userData.get(6)));

                user.setAccountFile();
                user.setFriend(new File("User_" + user.getUsername() + "_Friends.txt"));
                user.setBlock(new File("User_" + user.getUsername() + "_Block.txt"));
                user.setFriendRequest(new File("User_" + user.getUsername() + "_FriendRequest.txt"));
            }
        }

        return user;
    }

    // formats User's information in their own file
    public String toString() {
        return "Username: " + username + "\n"
                + "First name: " + firstName + "\n"
                + "Last name: " + lastName + "\n"
                + "Email: " + email + "\n"
                + "Bio: " + bio + "\n"
                + "Public status: " + messageCheck;
    }

    public synchronized String[] viewFriends() {
        ArrayList<String> friends = new ArrayList<>();

        try {
            File friendFile = new File("User_" + this.username + "_Friends.txt");
            if (!friendFile.exists()) {
                friendFile.createNewFile();
            }
            FileReader fr = new FileReader(friendFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                friends.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return null;
        }

        String[] friend = friends.toArray(new String[friends.size()]);
        return friend;
    }

    public synchronized String[] viewBlocks() {
        ArrayList<String> blocks = new ArrayList<>();

        try {
            File blockFile = new File("User_" + this.username + "_Block.txt");
            if (!blockFile.exists()) {
                blockFile.createNewFile();
            }
            FileReader fr = new FileReader(blockFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                blocks.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            System.out.println("1");
            return null;
        }

        String[] block = blocks.toArray(new String[blocks.size()]);
        System.out.println("2");
        return block;
    }

    public synchronized String viewFile() {
        ArrayList<String> profile = new ArrayList<>();

        try {
            File profileFile = new File("User_" + this.username + ".txt");
            FileReader fr = new FileReader(profileFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                profile.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        String userProfile = "";

        for (int i = 0; i < profile.size(); i++) {
            if (i == profile.size() - 1) {
                userProfile += profile.get(i);
            } else {
                userProfile += profile.get(i) + "\n";
            }
        }

        return userProfile;
    }

    public synchronized String[] viewAllUsers() {
        ArrayList<String> allUsernames = new ArrayList<>();

        try {
            File allUserFile = new File("All_User_Info.txt");
            if (!allUserFile.exists()) {
                allUserFile.createNewFile();
            }
            FileReader fr = new FileReader(allUserFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                allUsernames.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }

        String[] allUsers = allUsernames.toArray(new String[allUsernames.size()]);

        return allUsers;
    }

    public static synchronized User searchUser(String searchName) {
        File allUserFile = new File("All_User_Info.txt");

        try {
            FileReader fr = new FileReader(allUserFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                if (line.equals(searchName)) {
                    File userFile = new File("User_" + searchName + ".txt");
                    ArrayList<String> userData = new ArrayList<>();
                    FileReader userFr = new FileReader(userFile);
                    BufferedReader userBfr = new BufferedReader(userFr);

                    String userLine = userBfr.readLine();
                    while (userLine != null) {
                        userData.add(userLine.substring(userLine.indexOf(": ") + 1).trim());
                        userLine = userBfr.readLine();
                    }
                    userBfr.close();

                    if (userData.size() == 7) {
                        User user = new User(userData.get(0), userData.get(1), userData.get(2),
                                userData.get(3), userData.get(4), userData.get(5),
                                Boolean.parseBoolean(userData.get(6)));
                        return user;
                    }
                    break;
                }
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }
    public boolean isFriend(String otherUsername) {
        File otherUserFriendFile = new File("User_" + otherUsername + "_Friends.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(otherUserFriendFile))) {
            String friend;
            while ((friend = reader.readLine()) != null) {
                if (friend.equals(this.username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read other user's friends file: " + e.getMessage());
        }
        return false;
    }
    public boolean isBlocked(String otherUsername) {
        File otherUserBlockFile = new File("User_" + otherUsername + "_Block.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(otherUserBlockFile))) {
            String blockedUser;
            while ((blockedUser = reader.readLine()) != null) {
                if (blockedUser.equals(this.username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read other user's block file: " + e.getMessage());
        }
        return false;
    }

    public boolean hasFriend(String username) {
        File friendFile = new File("User_" + this.getUsername() + "_Friends.txt");
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(friendFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                if (line.equals(username)) {
                    bfr.close();
                    return true;
                }
            }
            bfr.close();
        } catch (IOException e) {
            System.out.println("Error reading friend file: " + e.getMessage());
        }
        return false;
    }

    public boolean hasBlock(String username) {
        File blockFile = new File("User_" + this.getUsername() + "_Block.txt");
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(blockFile));
            String line;
            while ((line = bfr.readLine()) != null) {
                if (line.equals(username)) {
                    bfr.close();
                    return true;
                }
            }
            bfr.close();
        } catch (IOException e) {
            System.out.println("Error reading block file: " + e.getMessage());
        }
        return false;
    }
    // Method to send a friend request
    public synchronized boolean sendRequest(String username) {
        String friendRequestFileName = "User_" + username + "_FriendRequest.txt";
        File friendRequestFile = new File(friendRequestFileName);

        // Check if the friend request file exists; create it if it does not exist
        if (!friendRequestFile.exists()) {
            try {
                friendRequestFile.createNewFile();
            } catch (IOException e) {
                System.err.println("Failed to create friend request file: " + e.getMessage());
                return false;
            }
        }

        // Check if the request has already been sent
        try (BufferedReader reader = new BufferedReader(new FileReader(friendRequestFile))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(this.getUsername())) {
                    return false;  // Request already sent we do nothing
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read from friend request file: " + e.getMessage());
            return false;
        }

        // Write the requester's username to the friend request file of the target user
        try (FileWriter fw = new FileWriter(friendRequestFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(this.getUsername());  // Assuming getUsername() method exists and gives the current user's username
        } catch (IOException e) {
            System.err.println("Failed to write to friend request file: " + e.getMessage());
            return false;
        }

        return true;  // Return true if the friend request was successfully sent
    }
    public synchronized boolean blockUser(String username) {
        if (this.hasBlock(username)) {
            return false;
        }

        File blockFile = new File("User_" + this.getUsername() + "_Block.txt");
        try {
            if (!blockFile.exists()) {
                blockFile.createNewFile();
            }
            try (FileWriter fw = new FileWriter(blockFile, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(username);
            }
        } catch (IOException e) {
            System.err.println("Failed to write to block file: " + e.getMessage());
            return false;
        }

        if (this.hasFriend(username)) {
            return this.removeFriend(username);
        }

        return true;
    }

    public synchronized boolean removeFriend(String username) {
        if (!removeFriendFromList(this.getUsername(), username)) {
            return true;
        }
        if (!removeFriendFromList(username, this.getUsername())) {
            // Optionally handle re-adding the friend to the first user's list if second removal fails
            return true;
        }
        return true;
    }

    private synchronized boolean removeFriendFromList(String ownerUsername, String friendToRemove) {
        ArrayList<String> friendsList = new ArrayList<>();
        File friendsFile = new File("User_" + ownerUsername + "_Friends.txt");

        // Load current friends from file
        try (BufferedReader reader = new BufferedReader(new FileReader(friendsFile))) {
            String friend;
            while ((friend = reader.readLine()) != null) {
                if (!friend.equals(friendToRemove)) {
                    friendsList.add(friend);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read friends file for user " + ownerUsername + ": " + e.getMessage());
            return false;
        }

        // Write updated friends back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(friendsFile, false))) {
            for (String friend : friendsList) {
                writer.println(friend);
            }
        } catch (IOException e) {
            System.err.println("Failed to write to friends file for user " + ownerUsername + ": " + e.getMessage());
            return false;
        }

        return true;
    }

    public synchronized boolean removeBlock(String username) {
        ArrayList<String> blockList = new ArrayList<>();
        File blockFile = new File("User_" + this.getUsername() + "_Block.txt");

        // Load current blocks from file
        try (BufferedReader reader = new BufferedReader(new FileReader(blockFile))) {
            String blockedUser;
            while ((blockedUser = reader.readLine()) != null) {
                if (!blockedUser.equals(username)) {
                    blockList.add(blockedUser);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read block file: " + e.getMessage());
            return false;
        }

        // Write updated blocks back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(blockFile, false))) {
            for (String blockedUser : blockList) {
                writer.println(blockedUser);
            }
        } catch (IOException e) {
            System.err.println("Failed to write to block file: " + e.getMessage());
            return false;
        }

        return true;
    }

    public synchronized ArrayList<String> viewAllMessageHistory() {
        ArrayList<String> allMessages = new ArrayList<>();
        File dir = new File("messages");
        File[] listOfFiles = dir.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().startsWith(this.username + "_") || file.getName().endsWith("_" + this.username + ".txt")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            allMessages.add(line);
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading from file: " + file.getName());
                    }
                }
            }
        }
        return allMessages;
    }

    public synchronized boolean deleteAllMessages() {
        boolean allDeleted = true;
        File dir = new File("messages");
        File[] listOfFiles = dir.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.getName().startsWith(this.username + "_") || file.getName().endsWith("_" + this.username + ".txt")) {
                    if (!file.delete()) {
                        System.out.println("Failed to delete file: " + file.getName());
                        allDeleted = false;
                    }
                }
            }
        }
        return allDeleted;
    }
    public ArrayList<String> viewFriendRequests() throws IOException {
        ArrayList<String> requests = new ArrayList<>();
        File requestFile = new File("User_" + this.username + "_FriendRequest.txt");
        if (requestFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(requestFile))) {
                String request;
                while ((request = reader.readLine()) != null) {
                    requests.add(request);
                }
            }
        }
        return requests;
    }
    public synchronized boolean acceptFriendRequest(String username) {
        if (!addFriend(username)) return false; // Add friend
        return removeFriendRequest(username);  // Remove from request list
    }

    private synchronized boolean addFriend(String username) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("User_" + this.username + "_Friends.txt", true))) {
            pw.println(username);
            return true;
        } catch (IOException e) {
            System.err.println("Error adding friend: " + e.getMessage());
            return false;
        }
    }
    public synchronized boolean rejectFriendRequest(String username) {
        return removeFriendRequest(username);
    }
    private synchronized boolean removeFriendRequest(String username) {
        File requestFile = new File("User_" + this.username + "_FriendRequest.txt");
        ArrayList<String> currentRequests = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(requestFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(username)) {
                    currentRequests.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading friend request file: " + e.getMessage());
            return false;
        }

        if (!found) return false;  // The username was not in the request list

        try (PrintWriter writer = new PrintWriter(new FileWriter(requestFile, false))) {
            for (String request : currentRequests) {
                writer.println(request);
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error rewriting friend request file: " + e.getMessage());
            return false;
        }
    }


}