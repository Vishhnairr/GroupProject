import java.util.*;
import java.io.*;

/**
 * MainMenu
 * <p>
 * Holds all the functionality for users to interact with the system.
 * First a welcome message is displayed and then the user logs in or creates an account.
 * When a user logs in to their account the menu gives them different options.
 * The menu allows the user to loop through and perform as many actions as they want, and then when they are done they
 * exit. Once they exit, the menu prompts them if they want to make any edits to their account before they log out, and
 * they can make as many edits as they like. After the user is done editing, a goodbye message is displayed and the
 * program ends.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 * @version March 31, 2024
 */

public class MainMenu {

    // main method
    public static void main(String[] args) {
        // start
        Scanner scanner = new Scanner(System.in);
        boolean stay = true;
        boolean newAccount = false;
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
                        String firstName, lastName, email, bio, username, password;
                        boolean isValid;

                        // First Name
                        do {
                            System.out.println("Enter first name: ");
                            firstName = scanner.nextLine();
                            isValid = user.checkFirstName(firstName);
                            if (!isValid) System.out.println("Invalid first name. Please try again.");
                        } while (!isValid);

                        // Last Name
                        do {
                            System.out.println("Enter last name: ");
                            lastName = scanner.nextLine();
                            isValid = user.checkLastName(lastName);
                            if (!isValid) System.out.println("Invalid last name. Please try again.");
                        } while (!isValid);

                        // Email
                        do {
                            System.out.println("Enter email: ");
                            email = scanner.nextLine();
                            isValid = user.checkEmail(email);
                            if (!isValid) System.out.println("Invalid email. Please try again.");
                        } while (!isValid);

                        // Bio
                        do {
                            System.out.println("Enter bio (20 to 40 characters): ");
                            bio = scanner.nextLine();
                            isValid = user.checkBio(bio);
                            if (!isValid) System.out.println("Invalid bio. Please ensure it is between 20 to 40 characters.");
                        } while (!isValid);

                        // Username
                        do {
                            System.out.println("Enter username: ");
                            username = scanner.nextLine();
                            isValid = user.checkUsername(username);
                            if (!isValid) System.out.println("Invalid username. Please try again.");
                        } while (!isValid);

                        // Password
                        do {
                            System.out.println("Enter password: ");
                            password = scanner.nextLine();
                            isValid = user.checkPassword(password);
                            if (!isValid) System.out.println("Invalid password. Please ensure it is at least 4 characters long.");
                        } while (!isValid);

                        user = user.createAccount(firstName, lastName, email, bio, username, password);
                        newAccount = true;
                        System.out.println("Account created successfully!");
                        break;
                    }
                    case 2: {  // User logs in
                        File allUsersFile = new File("All_User_Info.txt");
                        if (allUsersFile.exists()) {
                            User loggedInUser = null;
                            boolean isLoggedIn = false;
                            while (!isLoggedIn) {
                                System.out.println("Enter your username: ");
                                String username = scanner.nextLine();

                                System.out.println("Enter your password: ");
                                String password = scanner.nextLine();

                                loggedInUser = user.logIn(username, password);  // Assuming 'user' is an instance capable of logging in
                                if (loggedInUser != null) {
                                    isLoggedIn = true;
                                    user = loggedInUser;
                                    System.out.println("Welcome back, " + user.getUsername() + "!");
                                } else {
                                    System.out.println("Invalid username or password. Please try again.");
                                }
                            }
                        } else {
                            System.out.println("User database not found. Please exit program and create an account");
                            stay = false; // 'stay' controls whether to continue in the main loop
                        }
                        break;
                    }
                    case 3: {  // user wants to exit
                        System.out.println("hello");
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

        if (!newAccount) {
            if (User.checkMoreOneUser()) {
                while (stay) {  // loops while user wants to do things
                    do {
                        if (user instanceof Friends) { // user code
                            Friends person = (Friends) user;

                            do {
                                try {
                                    System.out.println("What would you like to do?");
                                    System.out.println("1. View all BoilerTown users" + "\n" +
                                            "2. Search BoilerTown users" + "\n" +
                                            "3. View/Interact with your friends" + "\n" +
                                            "4. Edit Account" + "\n" + "5. Exit");
                                    userChoice2 = Integer.parseInt(scanner.nextLine());
                                    switch (userChoice2) {
                                        case 1: {  // searches through all users
                                            File allUsersFile = new File("All_User_Info.txt");
                                            if (!allUsersFile.exists()) {
                                                System.out.println("ERROR! no users have been created yet!");
                                            } else {  // there are users to look at
                                                ArrayList<String> users = new ArrayList<>();
                                                String fileLine;
                                                try (BufferedReader br = new BufferedReader
                                                        (new FileReader(allUsersFile))) {
                                                    fileLine = br.readLine();
                                                    while (fileLine != null && !fileLine.equals("\n") &&
                                                            !fileLine.equals("")) {
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
                                                    System.out.println("Please enter the name of the user you " +
                                                            "want to look into:");

                                                    selectedUserName = scanner.nextLine();

                                                    if (!users.contains(selectedUserName)) {
                                                        validUsername = false;
                                                        System.out.println("ERROR! The username you entered is " +
                                                                "not a current user!");
                                                    } else if (user.getUsername().equals(selectedUserName)) {
                                                        validUsername = false;
                                                        System.out.println("You can't enter your own username! " +
                                                                "Please put another username.");
                                                    } else {
                                                        validUsername = true;
                                                    }
                                                } while (!validUsername); // username is now valid

                                                int userChoice3 = 0;

                                                do {
                                                    try {
                                                        System.out.println("What do you want to do?");
                                                        System.out.println("1. View " + selectedUserName +
                                                                "'s Profile" + "\n" + "2. Make a " +
                                                                "friend request" + "\n" + "3. Exit");
                                                        userChoice3 = Integer.parseInt(scanner.nextLine());
                                                        switch (userChoice3) {
                                                            case 1: {  // looking up a bio for a user
                                                                person.viewProfile(selectedUserName);
                                                                break;
                                                            }
                                                            case 2: { // make a friend request
                                                                File targetUserFile = new File("User_"
                                                                        + selectedUserName + "_Friends.txt");
                                                                if (targetUserFile.exists()) {
                                                                    Friends targetUser = new Friends(); // You might
                                                                    // want to load this user's details if necessary
                                                                    targetUser.setUsername(selectedUserName); // Set
                                                                    // the username of the target user
                                                                    // Send the friend request using the method
                                                                    // from Friends class
                                                                    person.makeFriendRequest(scanner, selectedUserName,
                                                                            user.getUsername());
                                                                } else {
                                                                    System.out.println(selectedUserName +
                                                                            " does not exist or has blocked " +
                                                                            "friend requests.");
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
                                                try (BufferedReader br = new BufferedReader
                                                        (new FileReader(allUsersFile))) {
                                                    fileLine = br.readLine();
                                                    while (fileLine != null && !fileLine.equals("\n") &&
                                                            !fileLine.equals("")) {
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
                                                    System.out.println("Please enter the name of the user " +
                                                            "you want to look into:");

                                                    selectedUserName = scanner.nextLine();

                                                    if (!users.contains(selectedUserName)) {
                                                        validUsername = false;
                                                        System.out.println("ERROR! The username you entered is " +
                                                                "not a current user!");
                                                    } else if (user.getUsername().equals(selectedUserName)) {
                                                        validUsername = false;
                                                        System.out.println("You can't enter your own username! " +
                                                                "Please put another username.");
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
                                                                File targetUserFile = new File("User_" +
                                                                        selectedUserName + "_Friends.txt");
                                                                if (targetUserFile.exists()) {
                                                                    Friends targetUser = new Friends(); // You might
                                                                    // want to load this user's details if necessary
                                                                    targetUser.setUsername(selectedUserName); // Set
                                                                    // the username of the target user
                                                                    // Send the friend request using the method from
                                                                    // Friends class
                                                                    person.makeFriendRequest(scanner, selectedUserName,
                                                                            user.getUsername());
                                                                } else {
                                                                    System.out.println(selectedUserName + " does not " +
                                                                            "exist or has blocked friend requests.");
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
                                                                System.out.println("1. View " + selectedUserName +
                                                                        "'s Profile" + "\n" +
                                                                        "2. Make a friend request" + "\n" + "3. Exit");
                                                                userChoice3 = scanner.nextInt();
                                                                scanner.nextLine();
                                                            }
                                                        }
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("Please enter a number!");
                                                        System.out.println("What do you want to do?");
                                                        System.out.println("1. View " + selectedUserName +
                                                                "'s Profile" + "\n" +
                                                                "2. Make a friend request" + "\n" + "3. Exit");
                                                        userChoice3 = scanner.nextInt();
                                                        scanner.nextLine();
                                                    }
                                                } while (userChoice3 != 1 && userChoice3 != 2 && userChoice3 != 3);
                                            }
                                            break;
                                        }

                                        case 3: {  // View all your friends
                                            System.out.printf("%s's friends list:\n", user.getUsername());
                                            person.friendViewer(user.getUsername());
                                            System.out.println("What do you want to do?");
                                            System.out.println("1. Message my friends" + "\n" +
                                                    "2. View my friend requests" + "\n"
                                                    + "3. Remove a friend" + "\n" + "4. Block a friend" +
                                                    "\n" + "5. Exit");

                                            int userChoice3 = 0;

                                            do {
                                                try {
                                                    userChoice3 = Integer.parseInt(scanner.nextLine());
                                                    switch (userChoice3) {
                                                        case 1: {  // message your friend
                                                            if (person.hasFriends(user.getUsername())) {
                                                                System.out.printf("%s's friends list:\n",
                                                                        user.getUsername());
                                                                ArrayList<String> friendUsernames =
                                                                        person.friendViewer(user.getUsername());
                                                                boolean validUsername = false;
                                                                String selectedUserName;
                                                                do {
                                                                    System.out.println("Who do u want to message?");
                                                                    // message mechanics implemented here and only here
                                                                    selectedUserName = scanner.nextLine();
                                                                    if (friendUsernames.contains(selectedUserName)) {
                                                                        validUsername = true;
                                                                    } else {
                                                                        System.out.println("You can only select " +
                                                                                "usernames that you are friends with.");
                                                                    }
                                                                } while (!validUsername);
                                                                System.out.println("What do you want to do?");
                                                                System.out.println("1. Message stuff" + "\n" +
                                                                        "2. Delete a message" +
                                                                        "\n" + "3. Exit");

                                                                int userChoice4 = scanner.nextInt();
                                                                scanner.nextLine();

                                                                if (userChoice4 == 1) {
                                                                    System.out.println("Please enter your message:");
                                                                    //Enter message
                                                                    String message = scanner.nextLine();
                                                                    MessageList send = new MessageList(message, user,
                                                                            selectedUserName);
                                                                    if (send.sendMessage()) {
                                                                        System.out.println("Successfully send!");
                                                                    } else {
                                                                        System.out.println("Failed send...");
                                                                    }
                                                                } else if (userChoice4 == 2) {
                                                                    File file = new File(user.getUsername()
                                                                            + "_" + selectedUserName + ".txt");
//
                                                                    try {
                                                                        FileReader fr = new FileReader(file);
                                                                        BufferedReader bfr = new BufferedReader(fr);
                                                                        String line = bfr.readLine();
                                                                        while (line != null) {
                                                                            System.out.println(line);
                                                                            line = bfr.readLine();
                                                                        }
                                                                    } catch (Exception e) {
                                                                        System.out.println("It seems that you don't " +
                                                                                "have a chat with your friend.");
                                                                    }

                                                                    System.out.println("Please enter which message " +
                                                                            "you want " +
                                                                            "to delete (Enter the whole message):");
                                                                    String delete = scanner.nextLine();
                                                                    MessageList deleteIt = new MessageList(delete,
                                                                            user, selectedUserName);
                                                                    if (deleteIt.deleteMessage(delete)) {
                                                                        System.out.println("Successfully delete!");
                                                                    } else {
                                                                        System.out.println("Failed delete...Please" +
                                                                                " check if " +
                                                                                "you had this message with your " +
                                                                                "friend.");
                                                                    }
                                                                }
                                                            } else {
                                                                System.out.println("Sorry! You currently don't " +
                                                                        "have any friends");
                                                            }
                                                            break;
                                                        }
                                                        case 2: { // view friend request
                                                            boolean hasFriendRequests = false;
                                                            boolean approve = false;
                                                            File userFile = new File("User_" +
                                                                    user.getUsername() + "_Friends.txt");
                                                            try (BufferedReader br = new BufferedReader
                                                                    (new FileReader(userFile))) {
                                                                String line;
                                                                while ((line = br.readLine()) != null) {
                                                                    if (line.startsWith("Friend request from:")) {
                                                                        System.out.println(line);
                                                                        hasFriendRequests = true; // Set flag to
                                                                        // true if a friend request line is found
                                                                    }
                                                                }
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                            if (!hasFriendRequests) {
                                                                System.out.println("You have no friend requests at " +
                                                                        "this time.");
                                                            } else {
                                                                System.out.println("Do you want to approve or " +
                                                                        "decline a friend request? (approve/decline)");
                                                                String action = scanner.nextLine().trim();
                                                                if (!action.equals("approve") &&
                                                                        !action.equals("decline")) {
                                                                    System.out.println("Invalid action.");
                                                                }
                                                                System.out.println("Enter the username of the " +
                                                                        "friend request:");
                                                                String friendUsername = scanner.nextLine();
                                                                // needs validation
                                                                if ("approve".equals(action)) {
                                                                    approve = true;
                                                                    person.updateFriendRequestStatus
                                                                            (friendUsername, user.getUsername(),
                                                                                    approve);
                                                                }
                                                                if ("decline".equals(action)) {
                                                                    person.updateFriendRequestStatus
                                                                            (friendUsername, user.getUsername(),
                                                                                    approve);
                                                                }
                                                                System.out.printf("friend requested %sd\n", action);
                                                            }
                                                            break;
                                                        }
                                                        case 3: { // Remove a friend
                                                            if (person.hasFriends(user.getUsername())) {
                                                                person.removeUser(scanner, user.getUsername());
                                                            } else {
                                                                System.out.println
                                                                        ("Sorry! You currently don't have any friends");
                                                            }
                                                            break;
                                                        }
                                                        case 4: { // block a friend
                                                            if (person.hasFriends(user.getUsername())) {
                                                                System.out.printf("%s's friends list:\n",
                                                                        user.getUsername());
                                                                ArrayList<String> friendUsernames =
                                                                        person.friendViewer(user.getUsername());
                                                                System.out.println("Who do you want to block?");
                                                                String selectedUserName = scanner.nextLine().trim();
                                                                // Validate that the selected username is a friend
                                                                if (friendUsernames.contains(selectedUserName)) {
                                                                    person.blockUser(selectedUserName,
                                                                            user.getUsername());
                                                                } else {
                                                                    System.out.println("You can only select " +
                                                                            "usernames that you are friends with.");
                                                                }
                                                            } else {
                                                                System.out.println("Sorry! You currently " +
                                                                        "don't have any friends");
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
                                                            System.out.println("1. Message my friends" + "\n" +
                                                                    "2. View my friend requests" + "\n"
                                                                    + "3. Remove a friend" + "\n" +
                                                                    "4. Block a friend" + "\n" + "5. Exit");
                                                            userChoice3 = Integer.parseInt(scanner.nextLine());
                                                        }
                                                    }
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Please enter a number!");
                                                }
                                            } while (userChoice3 != 1 && userChoice3 != 2 && userChoice3 != 3
                                                    && userChoice3 != 4
                                                    && userChoice3 != 5);
                                            break;
                                        }
                                        case 4: {
                                            boolean continueEditing = true;
                                            int editOption;
                                            String editValue;
                                            while (continueEditing) {
                                                System.out.println("What do you want to edit about your account?");
                                                System.out.println("1. First name\n2. Last name\n3. Bio\n4. Email\n5. Password");
                                                editOption = Integer.parseInt(scanner.nextLine());

                                                boolean isValid;
                                                switch (editOption) {
                                                    case 1:
                                                        do {
                                                            System.out.println("Enter new first name:");
                                                            editValue = scanner.nextLine();
                                                            isValid = user.checkFirstName(editValue);
                                                            if (!isValid) System.out.println("Invalid first name. Please try again.");
                                                        } while (!isValid);
                                                        user.setFirstName(editValue);
                                                        break;
                                                    case 2:
                                                        do {
                                                            System.out.println("Enter new last name:");
                                                            editValue = scanner.nextLine();
                                                            isValid = user.checkLastName(editValue);
                                                            if (!isValid) System.out.println("Invalid last name. Please try again.");
                                                        } while (!isValid);
                                                        user.setLastName(editValue);
                                                        break;
                                                    case 3:
                                                        do {
                                                            System.out.println("Enter new bio:");
                                                            editValue = scanner.nextLine();
                                                            isValid = user.checkBio(editValue);
                                                            if (!isValid) System.out.println("Invalid bio. Please ensure it is between 20 to 40 characters.");
                                                        } while (!isValid);
                                                        user.setBio(editValue);
                                                        break;
                                                    case 4:
                                                        do {
                                                            System.out.println("Enter new email:");
                                                            editValue = scanner.nextLine();
                                                            isValid = user.checkEmail(editValue);
                                                            if (!isValid) System.out.println("Invalid email. Please try again.");
                                                        } while (!isValid);
                                                        user.setEmail(editValue);
                                                        break;
                                                    case 5:
                                                        do {
                                                            System.out.println("Enter new password:");
                                                            editValue = scanner.nextLine();
                                                            isValid = user.checkPassword(editValue);
                                                            if (!isValid) System.out.println("Invalid password. Please ensure it is at least 4 characters long.");
                                                        } while (!isValid);
                                                        user.setPassword(editValue);
                                                        break;
                                                    default:
                                                        System.out.println("Invalid option, please choose again.");
                                                        continue;
                                                }

                                                // Assuming there's a method to update the user's information in the file after changes
                                                user.editAccount(editOption, editValue); // Updates the user information based on editOption and editValue
                                                System.out.println("Your changes have been made!");
                                                continueEditing = false;
                                            }
                                            break;
                                        }
                                        case 5: {
                                            stay = false;
                                            break;
                                        }
                                        default: {
                                            System.out.println("Please enter a valid number!");
                                            System.out.println("What would you like to do?");
                                            System.out.println("1. View all BoilerTown users" + "\n" +
                                                    "2. Search BoilerTown users" + "\n" +
                                                    "3. View/Interact with your friends" + "\n"
                                                    + "4. Edit Account" + "\n" + "5. Exit");
                                            userChoice2 = scanner.nextInt();
                                            scanner.nextLine();
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    System.out.println("Please enter a number!");
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
            } else { // if there aren't enough users, the program will close
                System.out.println("Sorry! BoilerTown cannot currently be used due to less than two users existing on"
                        + " the platform.");
            }
        } else {
            System.out.println("Thank you for making an account! Please log back in to access app features!");
        }

        System.out.println("Goodbye! You have been logged out.");
    }
}
