import java.util.*;
import java.io.*;

/**
 * Project 4 - MainMenu
 * <p>
 * Holds all the functionality for users to interact with the system. First a welcome
 * messages is displayed and then the user logs in or creates an account.
 * Then depending on what user type the user is the menu gives them different options. The menu allows the user
 * to loop through and perform as many actions as they want, and then when they are done they exit. Once they exit,
 * the menu prompts them if they want to make any edits to their account before they log out, and they can make as
 * many edits as they like or delete their account. After the user is done editing, a goodbye message is displayed
 * and the program ends.
 */

public class MainMenu {

    // main method
    public static void main(String[] args) {
        // start
        Scanner scanner = new Scanner(System.in);
        boolean stay = true;
        User user = new User();
        System.out.println("Welcome to BoilerTown!");
        System.out.println("Would you like to create an account or log-in?");
        System.out.println("1. Create an account");
        System.out.println("2. Log-in");
        System.out.println("3. Exit");
        int userChoice = scanner.nextInt();
        scanner.nextLine();
        if (userChoice == 1) {  // user creates account
            user = user.createAccount(scanner);
        } else if (userChoice == 2) {  // user logs in
            user = user.logIn(scanner);
        } else {  // user wants to exit
            stay = false;
        }

        File allUsersFile1 = new File("All_User_Info.txt");
        ArrayList<String> users1 = new ArrayList<>();
        if (allUsersFile1.exists()) { // there are users to look at
            String fileLine;
            try (BufferedReader br = new BufferedReader(new FileReader(allUsersFile1))) {
                fileLine = br.readLine();
                while (fileLine != null && !fileLine.equals("\n") && !fileLine.equals("")) {
                    users1.add(fileLine);
                    fileLine = br.readLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (users1.size() < 2) {
                stay = false;
            }
        } else {
            stay = false;
        }

        while (stay) {  // loops while user wants to do things
            if (user instanceof Friends) { // customer code
                Friends person = (Friends) user;
                int customerChoice;
                System.out.println("What would you like to do?");
                System.out.println("""
                        1. View all BoilerTown users
                        2. Search BoilerTown users
                        3. View/Interact with your friends
                        4. Exit""");
                customerChoice = scanner.nextInt();
                scanner.nextLine();

                if (customerChoice == 1) {  // looking at all the users
                    File allUsersFile = new File("All_User_Info.txt");
                    if (!allUsersFile.exists()) {
                        System.out.println("ERROR! no users have been created yet!");
                    } else {  // there are users to look at
                        ArrayList<String> users = new ArrayList<>();
                        String fileLine;
                        try (BufferedReader br = new BufferedReader(new FileReader(allUsersFile))) {
                            fileLine = br.readLine();
                            while (fileLine != null && !fileLine.equals("\n") && !fileLine.equals("")) {
                                users.add(fileLine);
                                fileLine = br.readLine();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // printing out the stores the seller has
                        System.out.println("List of all BoilerTown Users:");
                        for (int i = 0; i < users.size(); i++) {
                            System.out.println(users.get(i));
                        }

                        // get the store the user wants to change
                        String selectedUserName;
                        boolean validUsername = true;
                        do {
                            System.out.println("Please enter the name of the user you want to look into:");

                            selectedUserName = scanner.nextLine();

                            if (!users.contains(selectedUserName)) {
                                validUsername = false;
                                System.out.println("ERROR! The username you entered is not a current user!");
                            } else if (user.getUsername().equals(selectedUserName)) {
                                validUsername = false;
                                System.out.println("You can't enter your own username! Please put another username.\n");
                            } else {
                                validUsername = true;
                            }
                        } while (!validUsername); // username is now valid

                        System.out.println("What do you want to do?");
                        System.out.printf("""
                                1. View %s's Profile
                                2. Make a friend request
                                3. Exit
                                """, selectedUserName);

                        customerChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (customerChoice == 1) {  // looking up a bio for a user
                            person.viewProfile(selectedUserName);
                        }
                        if (customerChoice == 2) { // make a friend request
                            File targetUserFile = new File("User_" + selectedUserName + "_Friends.txt");
                            if (targetUserFile.exists()) {
                                Friends targetUser = new Friends(); // You might want to load this user's details if necessary
                                targetUser.setUsername(selectedUserName); // Set the username of the target user
                                // Send the friend request using the method from Friends class
                                person.makeFriendRequest(scanner, selectedUserName, user.getUsername());
                            } else {
                                System.out.println(selectedUserName + " does not exist or has blocked friend requests.");
                            }
                        }
                    }
                } else if (customerChoice == 2) { // view
                    File allUsersFile = new File("All_User_Info.txt");
                    if (!allUsersFile.exists()) {
                        System.out.println("ERROR! no users have been created yet!");
                    } else {  // there are users to look at
                        ArrayList<String> users = new ArrayList<>();
                        String fileLine;
                        try (BufferedReader br = new BufferedReader(new FileReader(allUsersFile))) {
                            fileLine = br.readLine();
                            while (fileLine != null && !fileLine.equals("\n") && !fileLine.equals("")) {
                                users.add(fileLine);
                                fileLine = br.readLine();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        // get the store the user wants to change
                        String selectedUserName;
                        boolean validUsername = true;
                        do {
                            System.out.println("Please enter the name of the user you want to look into:");

                            selectedUserName = scanner.nextLine();

                            if (!users.contains(selectedUserName)) {
                                validUsername = false;
                                System.out.println("ERROR! The username you entered is not a current user!");
                            } else if (user.getUsername().equals(selectedUserName)) {
                                validUsername = false;
                                System.out.println("You can't enter your own username! Please put another username.");
                            } else {
                                validUsername = true;
                            }
                        } while (!validUsername);

                        System.out.println("What do you want to do?");
                        System.out.printf("""
                                1. View %s's Profile
                                2. Make a friend request
                                3. Exit
                                """, selectedUserName);

                        customerChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (customerChoice == 1) {  // looking up a bio for a user
                            person.viewProfile(selectedUserName);
                        }
                        if (customerChoice == 2) { // make a friend request
                            File targetUserFile = new File("User_" + selectedUserName + "_Friends.txt");
                            if (targetUserFile.exists()) {
                                Friends targetUser = new Friends(); // You might want to load this user's details if necessary
                                targetUser.setUsername(selectedUserName); // Set the username of the target user
                                // Send the friend request using the method from Friends class
                                person.makeFriendRequest(scanner, selectedUserName, user.getUsername());
                            } else {
                                System.out.println(selectedUserName + " does not exist or has blocked friend requests.");
                            }
                        }
                    }
                } else if (customerChoice == 3) {  // View all your friends
                    System.out.printf("%s's friends list:\n", user.getUsername());
                    person.friendViewer(user.getUsername());
                    System.out.println("What do you want to do?");
                    System.out.println("""
                            1. Message my friends
                            2. View my friend requests
                            3. Remove a friend
                            4. Block a friend
                            5. Exit""");

                    customerChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (customerChoice == 1) {  // message your friend
                        System.out.printf("%s's friends list:\n", user.getUsername());
                        ArrayList<String> friendUsernames = person.friendViewer(user.getUsername());
                        boolean validUsername = false;
                        do {
                            System.out.println("Who do u want to message?"); // message mechanics implemented here and only here
                            String selectedUserName = scanner.nextLine();
                            if (friendUsernames.contains(selectedUserName)) {
                                validUsername = true;
                            } else {
                                System.out.println("You can only select usernames that you are friends with.");
                            }
                        } while (!validUsername);
                        System.out.println("What do you want to do?");
                        System.out.println("""
                                        1. message stuff
                                        2. Exit""");

                        customerChoice = scanner.nextInt();
                        scanner.nextLine();
                    } else if (customerChoice == 2) { // view friend request
                        boolean hasFriendRequests = false;
                        boolean approve = false;
                        File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
                        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                if (line.startsWith("Friend request from:")) {
                                    System.out.println(line);
                                    hasFriendRequests = true; // Set flag to true if a friend request line is found
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!hasFriendRequests) {
                            System.out.println("You have no friend requests at this time.");
                        } else {
                            System.out.println("Do you want to approve or decline a friend request? (approve/decline)");
                            String action = scanner.nextLine().trim();
                            if (!action.equals("approve") && !action.equals("decline")) {
                                System.out.println("Invalid action.");
                            }
                            System.out.println("Enter the username of the friend request:");
                            String friendUsername = scanner.nextLine(); // needs validation
                            if ("approve".equals(action)) {
                                approve = true;
                                person.updateFriendRequestStatus(friendUsername, user.getUsername(),
                                        approve);
                            }
                            if ("decline".equals(action)) {
                                person.updateFriendRequestStatus(friendUsername, user.getUsername(),
                                        approve);
                            }
                            System.out.printf("friend requested %sd\n", action);
                        }
                    } else if (customerChoice == 3) { // Remove a friend
                        person.removeUser(scanner, user.getUsername());
                    } else if (customerChoice == 4) { // block a friend
                        System.out.printf("%s's friends list:\n", user.getUsername());
                        ArrayList<String> friendUsernames = person.friendViewer(user.getUsername());
                        System.out.println("Who do you want to block?");
                        String selectedUserName = scanner.nextLine().trim();
                        // Validate that the selected username is a friend
                        if (friendUsernames.contains(selectedUserName)) {
                            person.blockUser(selectedUserName, user.getUsername());
                        } else {
                            System.out.println("You can only select usernames that you are friends with.");
                        }
                    }
                }
            }

            System.out.println("Do you want to continue?");
            System.out.println("""
                    1. Yes
                    2. No""");

            userChoice = scanner.nextInt();
            scanner.nextLine();

            if (userChoice == 2) {
                stay = false;
            }

            if (!stay) {
                System.out.println("Do you want to make any edits to your account info before you logout?");
                System.out.println("""
                        1. Yes
                        2. No""");

                userChoice = scanner.nextInt();
                scanner.nextLine();

                if (userChoice == 1) {
                    System.out.println("What kind of edits do you want to make to your account?");
                    System.out.println("""
                            1. edit your info
                            2. delete your account
                            3. Exit""");

                    userChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (userChoice == 1) {
                        user.editAccount(scanner);
                    } else if (userChoice == 2) {
                        user.deleteAccount(scanner);
                    }
                }
            }
        }
        if (users1.size() < 2) {
            System.out.println("Sorry! BoilerTown cannot currently be used due to less than two users existing on"
                    + " the platform." );
        }
        System.out.println("Goodbye! You have been logged out.");
    }
}
