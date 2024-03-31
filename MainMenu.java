import java.util.*;
import java.io.*;

/**
 * MainMenu
 *
 * Holds all the functionality for users to interact with the system.
 * First a welcome message is displayed and then the user logs in or creates an account.
 * When a user logs in to their account the menu gives them different options.
 * The menu allows the user to loop through and perform as many actions as they want, and then when they are done they
 * exit. Once they exit, the menu prompts them if they want to make any edits to their account before they log out, and
 * they can make as many edits as they like. After the user is done editing, a goodbye message is displayed and the
 * program ends.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version March 31, 2024
 *
 */

public class MainMenu {

    // main method
    public static void main(String[] args) {
        // start
        Scanner scanner = new Scanner(System.in);
        boolean stay = true;
        User user = new User();
        int userChoiceFinal = 0;
        int userChoice = 0;
        int userChoice2 = 0;
        System.out.println("Welcome to BoilerTown!");
        System.out.println("Would you like to create an account or log-in?");
        System.out.println("1. Create an account");
        System.out.println("2. Log-in");
        System.out.println("3. Exit");

        do {
            try {
                userChoice = Integer.parseInt(scanner.nextLine());
                switch (userChoice) {
                    case 1: {  // user creates account
                        user = user.createAccount(scanner);
                        break;
                    }
                    case 2: {  // user logs in
                        user = user.logIn(scanner);
                        if (user == null) {
                            stay = false;
                        }
                        break;
                    }
                    case 3: {  // user wants to exit
                        stay = false;
                        break;
                    }
                    default: {
                        System.out.println("Please enter a valid number!");
                        System.out.println("Would you like to create an account or log-in?");
                        System.out.println("1. Create an account");
                        System.out.println("2. Log-in");
                        System.out.println("3. Exit");
                        userChoice = scanner.nextInt();
                    }
                }
            } catch (NumberFormatException e) {
                userChoice = 4;
                System.out.println("Please enter a number!");
                System.out.println("Would you like to create an account or log-in?");
                System.out.println("1. Create an account");
                System.out.println("2. Log-in");
                System.out.println("3. Exit");
            }
        } while (userChoice != 1 && userChoice != 2 && userChoice != 3);

        if (User.checkMoreOneUser()) {
            while (stay) {  // loops while user wants to do things
                do {
                    if (user instanceof Friends) { // user code
                        Friends person = (Friends) user;
                        System.out.println("What would you like to do?");
                        System.out.println("1. View all BoilerTown users" + "\n" + "2. Search BoilerTown users" + "\n" +
                                "3. View/Interact with your friends" + "\n" + "4. Edit Account" + "\n" + "5. Exit");

                        do {
                            try {
                                userChoice2 = Integer.parseInt(scanner.nextLine());
                                switch (userChoice2) {
                                    case 1: {  // searches through all users
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
                                            } catch (IOException e) {
                                                System.out.println("File cannot be found!");
                                            }

                                            System.out.println("List of all BoilerTown Users:");
                                            for (int i = 0; i < users.size(); i++) {
                                                System.out.println(users.get(i));
                                            }

                                            // return the user the user wants to see
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

                                            int userChoice3 = 0;

                                            do {
                                                try {
                                                    System.out.println("What do you want to do?");
                                                    System.out.printf("1. View " + selectedUserName + "'s Profile" + "\n" + "2. Make a " +
                                                            "friend request" + "\n" + "3. Exit");
                                                    userChoice3 = Integer.parseInt(scanner.nextLine());
                                                    switch (userChoice3) {
                                                        case 1: {  // looking up a bio for a user
                                                            person.viewProfile(selectedUserName);
                                                            break;
                                                        }
                                                        case 2: { // make a friend request
                                                            File targetUserFile = new File("User_" + selectedUserName + "_Friends.txt");
                                                            if (targetUserFile.exists()) {
                                                                Friends targetUser = new Friends(); // You might want to load this user's details if necessary
                                                                targetUser.setUsername(selectedUserName); // Set the username of the target user
                                                                // Send the friend request using the method from Friends class
                                                                person.makeFriendRequest(scanner, selectedUserName, user.getUsername());
                                                            } else {
                                                                System.out.println(selectedUserName + " does not exist or has blocked friend requests.");
                                                            }
                                                            break;
                                                        }
                                                        default: {
                                                            System.out.println("Please choose a valid number!");
                                                            userChoice3 = scanner.nextInt();
                                                            scanner.nextLine();
                                                        }
                                                    }
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Please enter a number!");
                                                }
                                            } while (userChoice3 != 1 && userChoice3 != 2);
                                        }
                                        break;
                                    }

                                    case 2: { // view
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
                                                System.out.println("Unable to find file!");
                                            }

                                            // return the user that the user chooses
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
                                            System.out.println("1. View " + selectedUserName + "'s Profile" + "\n" +
                                                    "2. Make a friend request" + "\n" + "3. Exit");

                                            int userChoice3 = 0;
                                            do {
                                                userChoice3 = Integer.parseInt(scanner.nextLine());
                                                try {
                                                    switch (userChoice3) {
                                                        case 1: {  // looking up information of a user
                                                            person.viewProfile(selectedUserName);
                                                            break;
                                                        }
                                                        case 2: { // make a friend request
                                                            File targetUserFile = new File("User_" + selectedUserName + "_Friends.txt");
                                                            if (targetUserFile.exists()) {
                                                                Friends targetUser = new Friends(); // You might want to load this user's details if necessary
                                                                targetUser.setUsername(selectedUserName); // Set the username of the target user
                                                                // Send the friend request using the method from Friends class
                                                                person.makeFriendRequest(scanner, selectedUserName, user.getUsername());
                                                            } else {
                                                                System.out.println(selectedUserName + " does not exist or has blocked friend requests.");
                                                            }
                                                            break;
                                                        }
                                                        case 3: {
                                                            stay = false;
                                                            break;
                                                        }
                                                        default: {
                                                            System.out.println("Please enter a valid number!");
                                                            System.out.println("What do you want to do?");
                                                            System.out.println("1. View " + selectedUserName + "'s Profile" + "\n" +
                                                                    "2. Make a friend request" + "\n" + "3. Exit");
                                                            userChoice3 = scanner.nextInt();
                                                            scanner.nextLine();
                                                        }
                                                    }
                                                } catch (InputMismatchException e) {
                                                    System.out.println("Please enter a number!");
                                                    System.out.println("What do you want to do?");
                                                    System.out.println("1. View " + selectedUserName + "'s Profile" + "\n" +
                                                            "2. Make a friend request" + "\n" + "3. Exit");
                                                    userChoice3 = scanner.nextInt();
                                                    scanner.nextLine();
                                                }
                                            } while (userChoice3 != 1 && userChoice3 != 2 && userChoice3 != 3);
                                        }
                                        break;
                                    }

                                    case 3: {  // View all your friends
                                        if (person.hasFriends(user.getUsername())) {
                                            System.out.printf("%s's friends list:\n", user.getUsername());
                                            person.friendViewer(user.getUsername());
                                            System.out.println("What do you want to do?");
                                            System.out.println("1. Message my friends" + "\n" + "2. View my friend requests" + "\n"
                                                    + "3. Remove a friend" + "\n" + "4. Block a friend" + "\n" + "5. Exit");

                                            int userChoice3 = 0;

                                            do {
                                                try {
                                                    userChoice3 = Integer.parseInt(scanner.nextLine());
                                                    switch (userChoice3) {
                                                        case 1: {  // message your friend
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
                                                            System.out.println("1. message stuff" + "\n" + "2. Exit");

                                                            int userChoice4 = scanner.nextInt();
                                                            scanner.nextLine();
                                                            break;
                                                        }
                                                        case 2: { // view friend request
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
                                                            break;
                                                        }
                                                        case 3: { // Remove a friend
                                                            person.removeUser(scanner, user.getUsername());
                                                            break;
                                                        }
                                                        case 4: { // block a friend
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
                                                            break;
                                                        }
                                                        case 5: {
                                                            stay = false;
                                                            break;
                                                        }
                                                        default: {
                                                            System.out.println("Please enter a valid number!");
                                                            System.out.println("What do you want to do?");
                                                            System.out.println("1. Message my friends" + "\n" + "2. View my friend requests" + "\n"
                                                                    + "3. Remove a friend" + "\n" + "4. Block a friend" + "\n" + "5. Exit");
                                                            userChoice3 = Integer.parseInt(scanner.nextLine());
                                                        }
                                                    }
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Please enter a number!");
                                                }
                                            } while (userChoice3 != 1 && userChoice3 != 2 && userChoice3 != 3 && userChoice3 != 4
                                                    && userChoice3 != 5);
                                        } else {
                                            System.out.println("Sorry! You currently don't have any friends");
                                            stay = false;
                                        }
                                        break;
                                    }
                                    case 4: {
                                        user.editAccount(scanner);
                                        break;
                                    }
                                    case 5: {
                                        stay = false;
                                        break;
                                    }
                                    default: {
                                        System.out.println("Please enter a valid number!");
                                        System.out.println("What would you like to do?");
                                        System.out.println("1. View all BoilerTown users" + "\n" + "2. Search BoilerTown users" + "\n" +
                                                "3. View/Interact with your friends" + "\n" + "4. Edit Account" + "\n" + "5. Exit");
                                        userChoice2 = scanner.nextInt();
                                        scanner.nextLine();
                                    }
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a number!");
                                System.out.println("What would you like to do?");
                                System.out.println("1. View all BoilerTown users" + "\n" + "2. Search BoilerTown users" + "\n" +
                                        "3. View/Interact with your friends" + "\n" + "4. Edit Account" + "\n" + "5. Exit");
                            }
                        } while (userChoice2 != 1 && userChoice2 != 2 && userChoice2 != 3 && userChoice2 != 4 &&
                                userChoice2 != 5);
                    }
                    if (!stay) {
                        do {
                            System.out.println("Do you wish to leave?");
                            System.out.println("1. Yes" + "\n" + "2. No");
                            try {
                                userChoiceFinal = Integer.parseInt(scanner.nextLine());
                                if (userChoiceFinal != 1 && userChoiceFinal != 2) {
                                    System.out.println("Please enter a valid number");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Please enter a number!");
                            }
                        } while (userChoiceFinal != 1 && userChoiceFinal != 2);
                    } else {
                        userChoiceFinal = 2;
                    }
                } while (userChoiceFinal == 2);
            }
        } else {
            System.out.println("Sorry! BoilerTown cannot currently be used due to less than two users existing on"
                    + " the platform.");
        }
        System.out.println("Goodbye! You have been logged out.");
    }
}
