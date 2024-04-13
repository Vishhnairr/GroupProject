// imports here

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

public class User implements UserList {
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

    private boolean profileView; // if the user's profile is public or private

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
        this.profileView = false;
        this.messageCheck = false;
    }

    // creates custom constructor
    public User(String firstName, String lastName, String email, String bio, String username,
                String password, boolean profile, boolean message) {
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
        this.profileView = profile;
        this.messageCheck = message;
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

    public boolean getProfileView() {
        return this.profileView;
    }

    public boolean getMessageCheck() {
        return  this.messageCheck;
    }

    // sets first name
    public boolean setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty() || firstName.contains(" ")) {
            return false;
        }
        this.firstName = firstName;

        this.setAccountFile();
        return true;
    }

    // sets last name
    public boolean setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty() || lastName.contains(" ")) {
            System.out.println("ERROR! Please enter your last name correctly, without spaces!");
            return false;
        }
        this.lastName = lastName;

        this.setAccountFile();
        return true;
    }

    // sets username
    public boolean setUsername(String username) throws IOException {
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
        } catch (IOException e) {
            return false;
        }

        File newFile = new File("User_" + username + ".txt");
        boolean check = this.userFile.renameTo(newFile);
        this.username = username;
        this.setAccountFile();

        return check;
    }

    // sets email
    public boolean setEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@") || email.contains(" ") ||
                email.charAt(email.length() - 4) != '.') {
            return false;
        }
        this.email = email;

        this.setAccountFile();
        return true;
    }

    // sets bio
    public boolean setBio(String bio) {
        if (bio.length() > 50) {
            return false;
        } else {
            this.bio = bio;

            this.setAccountFile();
            return true;
        }
    }

    // sets password
    public boolean setPassword(String password) {
        if (password.length() < 4) {
            return false;
        } else {
            this.password = password;

            this.setAccountFile();
            return true;
        }
    }

    public boolean setProfileView(boolean check) {
        this.profileView = check;

        this.setAccountFile();
        return true;
    }

    public boolean setMessageCheck(boolean check) {
        this.messageCheck = check;

        this.setAccountFile();
        return true;
    }
    @Override
    public boolean setAccountFile() {
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
            pw.println("Profile view: " + profileView);
            pw.println("Message only: " + messageCheck);
            pw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public void setFriend(File file) {
        this.friends = file;
    }

    public void setBlock(File file) {
        this.block = file;
    }

    public void setFriendRequest(File file) {
        this.friendRequest = file;
    }
    // verifies if account exists for User
    public boolean checkAccountExists(String username) throws IOException {
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
            if (allUserNames.get(i).equals(username)) {
                return true;
            }
        }
        return false;
    }



    // creates new account based on info from scanner in main method
    public boolean createAccount() {
        // writing account file
        this.userFile = new File("User_" + this.username + ".txt");
        this.friends = new File("User_" + this.username + "_Friends.txt");
        this.block = new File("User_" + this.username + "_Block.txt");
        this.friendRequest = new File("User_" + this.username + "_FriendRequest.txt");

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
            pw.println("Profile view: " + profileView);
            pw.println("Message only: " + messageCheck);
            pw.close();
        } catch (IOException e) {
            return false;
        }

        try {
            File allUsersFile = new File("All_User_Info.txt");
            FileOutputStream fosAll = new FileOutputStream(allUsersFile, true);
            PrintWriter pwAll = new PrintWriter(fosAll);
            pwAll.println(username);
            pwAll.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
    // verifies login based on info from scanner in main method
    public User logIn(String username, String password) {
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
                userData.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return null;
        }


        if (userData.get(0).equals(username) && userData.get(1).equals(password)) {
            User user = new User(userData.get(0), userData.get(1), userData.get(2),
                    userData.get(3), userData.get(4), userData.get(5),
                    Boolean.parseBoolean(userData.get(6)), Boolean.parseBoolean(userData.get(7)));

            user.setAccountFile();
            user.setFriend(new File("User_" + user.getUsername() + "_Friends.txt"));
            user.setBlock(new File("User_" + user.getUsername() + "_Block.txt"));
            user.setFriendRequest(new File("User_" + user.getUsername() + "_FriendRequest.txt"));
            return user;
        }

        return null;
    }

    // formats User's information in their own file
    public String toString() {
        return username + "\n"
                + firstName + "\n"
                + lastName + "\n"
                + email + "\n"
                + bio;
    }

    public String[] viewFriends() {
        ArrayList<String> friends = new ArrayList<>();
        File friendFile = this.getFriends();

        try {
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

    public String[] viewBlocks() {
        ArrayList<String> blocks = new ArrayList<>();
        File blockFile = this.getBlock();

        try {
            FileReader fr = new FileReader(blockFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                blocks.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return null;
        }

        String[] block = blocks.toArray(new String[blocks.size()]);
        return block;
    }

    public String viewFile() {
        ArrayList<String> profile = new ArrayList<>();

        try {
            File profileFile = this.getUserFile();
            FileReader fr = new FileReader(profileFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                profile.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return null;
        }

        String userProfile = "";

        for (int i = 0; i < profile.size(); i++) {
            userProfile += profile.get(i) + "\n";
        }

        return userProfile;
    }

    public String[] viewAllUsers() {
        ArrayList<String> allUsernames = new ArrayList<>();

        try {
            File allUserFile = new File("All_User_Info.txt");
            FileReader fr = new FileReader(allUserFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                allUsernames.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return null;
        }

        String[] allUsers = allUsernames.toArray(new String[allUsernames.size()]);

        return allUsers;
    }
}
