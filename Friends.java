// imports
import java.io.*;
import java.util.*;

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

    public boolean verifyUser() {
        File allUserNames = new File("All_User_Info.txt");
        ArrayList<String> allUsers = new ArrayList<>();
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

        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).equals(friendUsername)) {
                return true;
            }
        }
        return false;
    }
    public boolean makeFriendRequest() {
        if (!this.verifyUser()) {
            return false;
        }
        File friendFile = this.user.getFriends();
        try {
            FileReader fr = new FileReader(friendFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                if (line.equals(this.friendUsername)) {
                    return false;
                }
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        File blockFile = this.user.getBlock();
        try {
            FileReader fr = new FileReader(blockFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                if (line.equals(this.friendUsername)) {
                    return false;
                }
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(this.user.getFriendRequest(), true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this.friendUsername);
            pw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean addFriend() {
        ArrayList<String> requests = new ArrayList<>();
        try {
            FileReader fr = new FileReader(this.user.getFriendRequest());
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

        int check = 0;
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).equals(this.friendUsername)) {
                check = 1;
            }
        }
        if (check == 0) {
            return false;
        }

        requests.remove(this.friendUsername);

        try {
            FileOutputStream fos = new FileOutputStream(this.user.getFriendRequest(), false);
            PrintWriter pw = new PrintWriter(fos);
            for (int j = 0; j < requests.size(); j++) {
                pw.println(requests.get(j));
            }
            pw.close();
        } catch (Exception e) {
            return false;
        }

        try {
            FileOutputStream fos = new FileOutputStream(this.user.getFriends(), true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this.friendUsername);
            pw.close();
        } catch (Exception e) {
            return false;
        }

        try {
            File friendsFile = new File("User_" + this.friendUsername + "_Friends.txt");
            FileOutputStream fos = new FileOutputStream(friendsFile, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this.user.getUsername());
            pw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removeFriend() {
        File friendFile = this.user.getFriends();
        ArrayList<String> friends = new ArrayList<>();

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
    public boolean blockUser() {
        if (!this.verifyUser()) {
            return false;
        }

        File blockFile = this.user.getBlock();
        ArrayList<String> blocks = new ArrayList<>();
        try {
            FileReader fr  = new FileReader(blockFile);
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

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).equals(this.friendUsername)) {
                return false;
            }
        }

        this.removeFriend();

        try {
            FileOutputStream fos = new FileOutputStream(blockFile, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(this.friendUsername);
            pw.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean removeBlock() {
        File blockFile = this.user.getBlock();
        ArrayList<String> blocks = new ArrayList<>();

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
            FileOutputStream fos = new FileOutputStream(blockFile, false);
            PrintWriter pw = new PrintWriter(fos);

            for (int j = 0; j < blocks.size(); j++) {
                pw.println(blocks.get(j));
            }

            pw.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public String viewProfile() {
        if (!this.verifyUser()) {
            return null;
        }

        ArrayList<String> friendProfile = new ArrayList<>();
        try {
            File friendFile = new File("User_" + this.friendUsername + "_.txt");
            FileReader fr = new FileReader(friendFile);
            BufferedReader bfr = new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                friendProfile.add(line);
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
