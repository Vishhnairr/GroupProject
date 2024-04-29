// imports

import java.io.*;
import java.util.ArrayList;

/**
 * Friends
 *
 * This class extends the User class and allows for a user to make, accept, and decline
 * friend requests, block other users, view their friends list, and remove friends from
 * the list. The friends list is stored as a file associated with the user and is updated
 * every time they perform one of the above actions.
 *
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version March 31, 2024
 *
 */

public class Friends implements FriendList{

    private User user; //The user
    private String friendUsername; // The user we want to process

    // customizable constructor
    public Friends(User user, String friendUsername) {
        this.user = user;
        this.friendUsername = friendUsername;
    }

    // blank constructor
    public Friends() {
        this.user = null;
        this.friendUsername = null;
    }


//    /**
//     * Sends a friend request to another user.
//     *
//     * @param friendUsername The username of the user recieving the friend request.
//     * @param username       The username of the user sending the friend request.
//     */

    public synchronized boolean verifyUser() {

        ArrayList<String> allUsers = new ArrayList<>();

        synchronized (Friends.class) {
            File allUserNames = new File("All_User_Info.txt");
            try {
                FileReader fr = new FileReader(allUserNames);
                BufferedReader bfr =  new BufferedReader(fr);
                String line = bfr.readLine();

                while (line != null) {
                    allUsers.add(line);
                    line = bfr.readLine();
                }
                bfr.close();
            } catch (Exception e) {
                return false;
            }
        }


        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).equals(friendUsername)) {
                return true;
            }
        }
        return false;
    }
    public synchronized boolean makeFriendRequest() {
        // Verify the user exists in the system
        if (!this.verifyUser()) {
            return false;
        }

        // Check for existing friend requests to this user
        try {
            File friendRequestFile = new File("User_" + this.friendUsername + "_FriendRequest.txt");
            if (!friendRequestFile.exists()) {
                friendRequestFile.createNewFile();
            } else {
                // If the file exists, read through it to see if there's already a request from this user
                FileReader fr = new FileReader(friendRequestFile);
                BufferedReader bfr = new BufferedReader(fr);
                String line;
                while ((line = bfr.readLine()) != null) {
                    if (line.equals(this.user.getUsername())) {
                        bfr.close();
                        return false; // Request already exists, do nothing
                    }
                }
                bfr.close();
            }
        } catch (IOException e) {
            return false;
        }

        // If no existing request, proceed to add a new friend request
        try {
            FileOutputStream fos = new FileOutputStream("User_" + this.friendUsername + "_FriendRequest.txt", true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this.user.getUsername());
            pw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }


    public synchronized boolean rejectFriendRequest() {
        if (!this.verifyUser()) {
            return false;
        }

        ArrayList<String> requests = new ArrayList<>();
        try {
            File friendRequest = new File("User_" + this.user.getUsername() + "_FriendRequest.txt");
            if (!friendRequest.exists()) {
                friendRequest.createNewFile();
            }

            FileReader fr = new FileReader(friendRequest);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                requests.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        System.out.println(requests);
        requests.remove(this.friendUsername);
        System.out.println(requests);

        try {
            File friendRequest = new File("User_" + this.user.getUsername() + "_FriendRequest.txt");
            if (!friendRequest.exists()) {
                friendRequest.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(friendRequest, false);
            PrintWriter pw = new PrintWriter(fos);

            pw.print("");

            for (int i = 0; i < requests.size(); i++) {
                pw.println(requests.get(i));
            }

            pw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public synchronized boolean addFriend() {
        ArrayList<String> requests = new ArrayList<>();
        File friendRequestFile = new File("User_" + this.user.getUsername() + "_FriendRequest.txt");
        try {
            FileReader fr = new FileReader(friendRequestFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                requests.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        System.out.println(requests);
        System.out.println(friendUsername);

        int check = 0;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).equals(this.friendUsername)) {
                check = 1;
            }
        }
        if (check == 0) {
            System.out.println("2");
            return false;
        }

        requests.remove(this.friendUsername);

        try {
            FileOutputStream fos = new FileOutputStream(friendRequestFile, false);
            PrintWriter pw = new PrintWriter(fos);
            for (int j = 0; j < requests.size(); j++) {
                pw.println(requests.get(j));
            }
            pw.close();
        } catch (Exception e) {
            System.out.println("3");
            return false;
        }

        try {
            File friendFile = new File("User_" + this.user.getUsername() + "_Friends.txt");
            FileOutputStream fos = new FileOutputStream(friendFile, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this.friendUsername);
            pw.close();
        } catch (Exception e) {
            System.out.println("4");
            return false;
        }

        try {
            File friendsFile = new File("User_" + this.friendUsername + "_Friends.txt");
            FileOutputStream fos = new FileOutputStream(friendsFile, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this.user.getUsername());
            pw.close();
        } catch (Exception e) {
            System.out.println("5");
            return false;
        }

        return true;
    }

    public synchronized boolean removeFriend() {
        ArrayList<String> friends = new ArrayList<>();

        try {
            File friendFile = new File("User_" + this.user.getUsername() + "_Friends.txt");
            FileReader fr = new FileReader(friendFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                friends.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        int check = 0;
        for (int i = 0; i < friends.size(); i++) {
            if (friends.get(i).equals(friendUsername)) {
                check = 1;
            }
        }

        if (check == 0) {
            return false;
        }

        friends.remove(friendUsername);

        try {
            File friendFile = new File("User_" + this.user.getUsername() + "_Friends.txt");
            FileOutputStream fos = new FileOutputStream(friendFile, false);
            PrintWriter pw = new PrintWriter(fos);

            for (int j = 0; j < friends.size(); j++) {
                pw.println(friends.get(j));
            }
            pw.close();
        } catch (Exception e) {
            return false;
        }


        ArrayList<String> frinedsFriend = new ArrayList<>();

        try {
            File friendsFile = new File("User_" + this.friendUsername + "_Friends.txt");
            FileReader fr = new FileReader(friendsFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                frinedsFriend.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        frinedsFriend.remove(this.user.getUsername());
        try {
            File friendsFile = new File("User_" + this.friendUsername + "_Friends.txt");
            FileOutputStream fos = new FileOutputStream(friendsFile, false);
            PrintWriter pw = new PrintWriter(fos);

            for (int k = 0; k < frinedsFriend.size(); k++) {
                pw.println(frinedsFriend.get(k));
            }
            pw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }
    //    /**
//     * Blocks a user, preventing them from sending friend requests.
//     *
//     * @param usernameToBlock  The username of the user to block.
//     * @param blockingUsername The username of the user performing the block.
//     */
    public synchronized boolean blockUser() {
        if (!this.verifyUser()) {
            return false;  // Verify that the friendUsername exists in the system
        }

        // Remove any friend requests received from the target user
        if (!removeFriendRequestFromUser(this.user.getUsername(), this.friendUsername)) {
            return false;  // If the operation fails, return false
        }

        // Remove any friend requests sent to the target user
        if (!removeFriendRequestFromUser(this.friendUsername, this.user.getUsername())) {
            return false;  // If the operation fails, return false
        }

        // Remove the target user from the friends list, if present
        this.removeFriend();

        // Add the target user to the block list
        try {
            File blockFile = new File("User_" + this.user.getUsername() + "_Block.txt");
            if (!blockFile.exists()) {
                blockFile.createNewFile();  // Create the block file if it doesn't exist
            }
            FileWriter fw = new FileWriter(blockFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(this.friendUsername);  // Write the target username to the block file
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public synchronized boolean removeFriendRequestFromUser(String ownerUsername, String targetUsername) {
        ArrayList<String> requests = new ArrayList<>();
        try {
            File requestFile = new File("User_" + ownerUsername + "_FriendRequest.txt");
            if (!requestFile.exists()) {
                return true;  // If the file does not exist, there are no requests to delete
            }

            // Read all requests
            FileReader fr = new FileReader(requestFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line;
            while ((line = bfr.readLine()) != null) {
                if (!line.equals(targetUsername)) {  // Keep requests that are not from the target user
                    requests.add(line);
                }
            }
            bfr.close();

            // Rewrite the request file
            FileWriter fw = new FileWriter(requestFile, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (String request : requests) {
                bw.write(request);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }



    public synchronized boolean removeBlock() {
        ArrayList<String> blocks = new ArrayList<>();

        try {
            File blockFile = new File("User_" + this.user.getUsername() + "_Block.txt");
            FileReader fr = new FileReader(blockFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                blocks.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        int check = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).equals(this.friendUsername)) {
                check = 1;
            }
        }

        if (check == 0) {
            return false;
        }

        blocks.remove(this.friendUsername);

        try {
            File blockFile = new File("User_" + this.user.getUsername() + "_Block.txt");
            FileOutputStream fos = new FileOutputStream(blockFile, false);
            PrintWriter pw = new PrintWriter(fos);

            pw.print("");

            for (int j = 0; j < blocks.size(); j++) {
                pw.println(blocks.get(j));
            }

            pw.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public synchronized String viewProfile() {
        if (!this.verifyUser()) {
            return null;
        }

        ArrayList<String> friendProfile = new ArrayList<>();
        try {
            File friendFile = new File("User_" + this.friendUsername + ".txt");
            FileReader fr = new FileReader(friendFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                friendProfile.add(line.substring(line.indexOf(": ") + 1).trim());
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return null;
        }

        User friend = new User(friendProfile.get(0), friendProfile.get(1),
                friendProfile.get(2), friendProfile.get(3), friendProfile.get(4),
                friendProfile.get(5), Boolean.parseBoolean(friendProfile.get(6)),
                Boolean.parseBoolean(friendProfile.get(7)));

        ArrayList<String> friendFriends = new ArrayList<>();

        if (friend.getProfileView()) {
            return friend.toString();
        } else {
            try {
                File friendsFriendFile = new File("User_" + this.friendUsername + "_Friends.txt");
                FileReader fr = new FileReader(friendsFriendFile);
                BufferedReader bfr = new BufferedReader(fr);
                String line = bfr.readLine();

                while (line != null) {
                    friendFriends.add(line);
                    line = bfr.readLine();
                }
            } catch (Exception e) {
                return null;
            }

            int check = 0;
            for (int i = 0; i < friendFriends.size(); i++) {
                if (friendFriends.get(i).equals(this.user.getUsername())) {
                    check = 1;
                }
            }

            if (check == 0) {
                return null;
            }

            return friend.toString();
        }

    }

}
