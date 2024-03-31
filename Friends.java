// imports
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Project 4 - Friends
 *
 * This class extends the User class and allows for a user to make, accept, and decline
 * friend requests, block other users, view their friends list, and remove friends from
 * the list. The friends list is stored as a file associated with the user and is updated
 * every time they perform one of the above actions.
 *
 */

public class Friends extends User {

    private String friendUsername;

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


    /**
     * Sends a friend request to another user.
     *
     * @param scanner        The Scanner object for user input.
     * @param friendUsername The username of the user recieving the friend request.
     * @param username       The username of the user sending the friend request.
     */

    public void makeFriendRequest(Scanner scanner, String friendUsername, String username) {
        System.out.printf("What would you like to say to %s?\n", friendUsername);
        String message = scanner.nextLine();

        File friendsFile = new File("User_" + friendUsername + "_Friends.txt");
        File userFile = new File("User_" + username + "_Friends.txt");

        // Check if the target user has blocked the sender or does not exist
        if (username.equals(friendUsername)) {
            System.out.print("You can not send a friend request to yourself! Please put another user's name.\n");
        } else {
            // Write the friend request to the targeted user's friend file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(friendsFile, true))) {
                bw.write("Friend request from: " + username + ", " + message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Record the sent friend request in the sender's friend file
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
                bw.write("You sent a friend request to: " + friendUsername + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.printf("Friend request to user %s has been sent successfully!\n", friendUsername);
        }

    }

    /**
     * Blocks a user, preventing them from sending friend requests.
     *
     * @param usernameToBlock  The username of the user to block.
     * @param blockingUsername The username of the user performing the block.
     */
    public void blockUser(String usernameToBlock, String blockingUsername) {
        File blockedUserFile = new File("User_" + usernameToBlock + "_Friends.txt"); // vishnairr
        File blockingUserFile = new File("User_" + blockingUsername + "_Friends.txt"); // naclls
        // Update the blocked user's file with a message that they've been blocked
        List<String> blockedlines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(blockedUserFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(blockingUsername + " is your friend!")) {
                    line = "You have been blocked by: " + blockingUsername; // Replace the line
                }
                blockedlines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the blocked user's file.");
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(blockedUserFile, false))) {
            for (String line : blockedlines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the blocked user's file.");
            e.printStackTrace();
        }


        // Update the blocking user's file, replacing the friend line with a blocked message
        List<String> blockinglines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(blockingUserFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(usernameToBlock + " is your friend!")) {
                    line = "You blocked: " + usernameToBlock; // Replace the line
                }
                blockinglines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the blocking user's file.");
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(blockingUserFile, false))) {
            for (String line : blockinglines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the blocking user's file.");
            e.printStackTrace();
        }

        System.out.printf("User %s has been successfully blocked.\n", usernameToBlock);
    }


    public void handleFriendRequest(Scanner scanner, User user) {
        System.out.println("Do you want to approve or decline a friend request? (approve/decline)");
        String action = scanner.nextLine().trim();

        if (!action.equals("approve") && !action.equals("decline")) {
            System.out.println("Invalid action.");
            return;
        }

        System.out.println("Enter the username of the friend request:");
        String friendUsername = scanner.nextLine();


        File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
        File friendsFile = new File("User_" + friendUsername + "_Friends.txt");


        if (action.equals("approve")) {
            updateFriendRequestStatus(friendUsername, user.getUsername(), true);
        } else if (action.equals("decline")) {
            updateFriendRequestStatus(friendUsername, user.getUsername(),false);
        }
    }

    public void updateFriendRequestStatus(String friendUsername, String username, boolean approve) {
        File sentRequestFile = new File("User_" + friendUsername + "_Friends.txt");
        File acceptingRequestFile = new File("User_" + username + "_Friends.txt");
        List<String> sentlines = new ArrayList<>();
        List<String> acceptlines = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(acceptingRequestFile))) {
            while ((line = br.readLine()) != null) {
                if (line.contains("Friend request from: " + friendUsername)) {
                    if (approve) {
                        line = friendUsername + " is your friend!";
                    } else {
                        line = "You declined " + friendUsername + "'s friend request.";
                    }
                }
                acceptlines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(acceptingRequestFile, false))) {
            for (String l : acceptlines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(sentRequestFile))) {
            while ((line = br.readLine()) != null) {
                if (line.contains("You sent a friend request to: " + username)) {
                    if (approve) {
                        line = username + " is your friend!";
                    } else {
                        line = username + " declined your friend request.";
                    }
                }
                sentlines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(sentRequestFile, false))) {
            for (String l : sentlines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(Scanner scanner, String username) {
        File userFile = new File("User_" + username + "_Friends.txt");
        List<String> lines = new ArrayList<>();
        List<String> friendUsernames = new ArrayList<>();

        // Read the existing friends and display them to the user
        try (BufferedReader reader = new BufferedReader(new FileReader(userFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
                if (line.endsWith("is your friend!")) {
                    friendUsernames.add(line.substring(0, line.indexOf(" is your friend!")));
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user's file.");
            e.printStackTrace();
            return;
        }

        // Ask the user which friend they would like to remove
        System.out.println("Enter the username of the friend you wish to remove:");
        String friendToRemove = scanner.nextLine().trim();

        // Validate the entered username
        if (!friendUsernames.contains(friendToRemove)) {
            System.out.println("The entered username is not in your friends list.");
            return;
        }
        List<String> friendLines = new ArrayList<>();
        File friendFile = new File("User_" + friendToRemove + "_Friends.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(friendFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                friendLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the user's file.");
            e.printStackTrace();
            return;
        }
        // Remove the friend from the list
        lines.removeIf(line -> line.equals(friendToRemove + " is your friend!"));

        // Rewrite the file without the removed friend
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(userFile, false))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the user's file.");
            e.printStackTrace();
        }

        friendLines.removeIf(line -> line.equals(username + " is your friend!"));

        // Rewrite the file without the removed friend
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(friendFile, false))) {
            for (String line : friendLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the user's file.");
            e.printStackTrace();
        }

        System.out.printf("User %s has been successfully removed from your friends list.\n", friendToRemove);
    }
    public ArrayList<String> friendViewer(String username) {
        ArrayList<String> friendUsernames = new ArrayList<>();
        File f = new File("User_" + username + "_Friends.txt");

        if (f.exists()) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = bfr.readLine()) != null) {
                    // Check if the line indicates a confirmed friendship
                    if (line.endsWith("is your friend!")) {
                        System.out.println(line);
                        // Extract the friend's username from the line and add it to the list
                        String friendUsername = line.substring(0, line.indexOf(" is your friend!"));
                        friendUsernames.add(friendUsername);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("User file doesn't exist.");
            }
        } else {
            System.out.println("The friends file does not exist.");
        }

        return friendUsernames;
    }

    /**
     * Views and prints the profile of the selected user.
     *
     * @param selectedUserName The username of the user whose profile is to be viewed.
     */

    public void viewProfile(String selectedUserName) {
        File f = new File("User_" + selectedUserName + ".txt");
        if (f.exists()) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                String line;
                int lineNumber = 0;
                String firstName = "";
                String lastName = "";
                String bio = "";
                String username = "";
                while ((line = bfr.readLine()) != null) {
                    lineNumber++;
                    if (lineNumber == 1) { // Store the 4th line (bio)
                        firstName = line;
                    }
                    if (lineNumber == 2) { // Store the 4th line (bio)
                        lastName = line;
                    }
                    // Store the 4th line (bio)
                    if (lineNumber == 4) {
                        bio = line;
                    }
                    // Store the 5th line (username)
                    if (lineNumber == 5) {
                        username = line;
                    }
                }
                System.out.printf("%s's Profile\n", username);
                System.out.println("Name: " + firstName + " " + lastName);
                System.out.println("Username: " + username);
                System.out.println("Bio: " + bio);
            } catch (IOException e) {
                System.out.println("An error occurred while trying to read the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("The profile file for " + selectedUserName + " does not exist.");
        }
    }
    public boolean hasFriends(String username) {
        boolean friends = true;
        ArrayList<String> friendUsernames = new ArrayList<>();
        File f = new File("User_" + username + "_Friends.txt");

        if (f.exists()) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = bfr.readLine()) != null) {
                    // Check if the line indicates a confirmed friendship
                    if (line.endsWith("is your friend!")) {
                        // Extract the friend's username from the line and add it to the list
                        String friendUsername = line.substring(0, line.indexOf(" is your friend!"));
                        friendUsernames.add(friendUsername);
                    }
                }
                if (friendUsernames.isEmpty()) {
                    friends = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("User file doesn't exist.");
                friends = false;
            }
        } else {
            System.out.println("The friends file does not exist.");
            friends = false;
        }
        return friends;
    }
}







