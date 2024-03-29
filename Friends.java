// imports
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 - Member
 *
 * This class extends the User class and allows for a Customer
 * to make appointment requests, view all their approved appointments, cancel appointment requests, reschedule already
 * approved appointments to a different time on the same day. Every customer has an appointments file that store's
 * all their appointment info for each appointment/appointment request they have. This file is updated both when
 * a customer makes/edits an appointment request or reschedules an appointment request, or when a seller approves
 * or declines an appointment request or reschedules an appointment for the customer.
 *
 */

public class Friends extends User {


    // customizable constructor
    public Friends(String firstName, String lastName, String email, String bio, String username,
                  String password) {
        super(firstName, lastName, email, username, bio, password);
        // read file to get appointment information and store it.

    }

    // blank constructor
    public Friends() {
        super();
    }

    
    
    public static void updateFriendStatus(String username, Scanner scanner) {
        File userFriendsFile = new File("User_" + username + "_Friends.txt");
        List<String> userFileContent = new ArrayList<>();
        String action;
        String friendUsername;

        // Read the current user file content
        try (BufferedReader br = new BufferedReader(new FileReader(userFriendsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                userFileContent.add(line);
                if (line.startsWith("Friend request from:")) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Request action from the user
        System.out.println("Do you want to approve or decline a friend request? (approve/decline)");
        action = scanner.nextLine().trim();
        if (!action.equals("approve") && !action.equals("decline")) {
            System.out.println("Invalid action.");
            return;
        }

        // Get the username from the friend request
        System.out.println("Enter the username of the friend request:");
        friendUsername = scanner.nextLine();

        File friendFriendsFile = new File("User_" + friendUsername + "_Friends.txt");
        List<String> friendFileContent = new ArrayList<>();

        // Read friend's current file content if exists
        if (friendFriendsFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(friendFriendsFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    friendFileContent.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Process approve/decline action
        if (action.equals("approve")) {
            // Update both users' files to reflect the new friend status
            userFileContent.removeIf(line -> line.contains("Friend request from: " + friendUsername));
            userFileContent.add(friendUsername + " is now your friend!");

            friendFileContent.removeIf(line -> line.contains(username));
            friendFileContent.add(username + " is now your friend!");
        } else if (action.equals("decline")) {
            // Optionally, handle the decline case
            // This can involve notifying the requester their request was declined
            friendFileContent.add(username + " declined your friend request.");
        }

        // Write the updated content back to both files
        try (BufferedWriter bwUser = new BufferedWriter(new FileWriter(userFriendsFile, false));
             BufferedWriter bwFriend = new BufferedWriter(new FileWriter(friendFriendsFile, false))) {
            for (String line : userFileContent) {
                bwUser.write(line + "\n");
            }
            for (String line : friendFileContent) {
                bwFriend.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Your main method logic here
        Scanner scanner = new Scanner(System.in);
        // Assume username is already obtained from the login process
        String username = "exampleUsername"; // Placeholder username
        updateFriendStatus(username, scanner);
    }
}
