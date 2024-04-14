import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Clients {

    public static void main(String[] args) {
        // start
        boolean stay = true;
        boolean newAccount = false;
        User user = new User();
        int userChoiceFinal = 0;
        int userChoice = 0;
        int userChoice2 = 0;

        try {

            JOptionPane.showMessageDialog(null, "Welcome to BoilerTown!",
                    "Database Searcher", JOptionPane.INFORMATION_MESSAGE);

            Socket socket = new Socket("localhost", 4242);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            do {
                try {
                    userChoice = Integer.parseInt(JOptionPane.showInputDialog(null, "Would you " +
                                    "like to create an account " +
                                    "or log-in?" + "\n" + "1. Create an account" + "\n" + "2. Log-in" + "\n" + "3. Exit",
                            "Database Searcher", JOptionPane.QUESTION_MESSAGE));
                    if (userChoice != 1 && userChoice != 2 && userChoice != 3) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number!",
                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String userChoiceSend = String.valueOf(userChoice);
                        writer.write(userChoiceSend);
                        writer.println();
                        writer.flush();
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a number!",
                            "Database Searcher", JOptionPane.ERROR_MESSAGE);
                }
            } while (userChoice != 1 && userChoice != 2 && userChoice != 3);

            if (userChoice != 3) {
                do {
                    try {
                        switch (userChoice) {
                            case 1: {  // user creates account
                                String firstName, lastName, email, bio, username, password;
                                boolean isValid;

                                userChoice = Integer.parseInt(reader.readLine());

                                // First Name
                                do {
                                    firstName = JOptionPane.showInputDialog(null, "Enter first " +
                                            "name: ", "Database Searcher", JOptionPane.QUESTION_MESSAGE);
                                    writer.write(firstName);
                                    writer.println();
                                    writer.flush();

                                    isValid = Boolean.parseBoolean(reader.readLine());
                                    if (!isValid) {
                                        JOptionPane.showMessageDialog(null, "Invalid first " +
                                                        "name. Please try again.",
                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (!isValid);

                                // Last Name
                                do {
                                    lastName = JOptionPane.showInputDialog(null, "Enter last " +
                                            "name: ", "Database Searcher", JOptionPane.QUESTION_MESSAGE);
                                    writer.write(lastName);
                                    writer.println();
                                    writer.flush();

                                    isValid = Boolean.parseBoolean(reader.readLine());
                                    if (!isValid) {
                                        JOptionPane.showMessageDialog(null, "Invalid last " +
                                                        "name. Please try again.",
                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (!isValid);

                                // Email
                                do {
                                    email = JOptionPane.showInputDialog(null, "Enter email: ",
                                            "Database Searcher", JOptionPane.QUESTION_MESSAGE);
                                    writer.write(email);
                                    writer.println();
                                    writer.flush();

                                    isValid = Boolean.parseBoolean(reader.readLine());
                                    if (!isValid) {
                                        JOptionPane.showMessageDialog(null, "Invalid email." +
                                                        " Please try again.",
                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (!isValid);

                                // Bio
                                do {
                                    bio = JOptionPane.showInputDialog(null, "Enter bio " +
                                                    "(20 to 40 characters): ", "Database Searcher",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.write(bio);
                                    writer.println();
                                    writer.flush();

                                    isValid = Boolean.parseBoolean(reader.readLine());
                                    if (!isValid)
                                        JOptionPane.showMessageDialog(null, "Invalid bio. " +
                                                        "Please ensure it is between 20 to 40 characters.",
                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                } while (!isValid);

                                // Username
                                do {
                                    username = JOptionPane.showInputDialog(null, "Enter " +
                                                    "username: ", "Database Searcher",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.write(username);
                                    writer.println();
                                    writer.flush();

                                    isValid = Boolean.parseBoolean(reader.readLine());
                                    if (!isValid) {
                                        JOptionPane.showMessageDialog(null, "Invalid " +
                                                        "username. Please try again.", "Database Searcher",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (!isValid);

                                // Password
                                do {
                                    password = JOptionPane.showInputDialog(null, "Enter " +
                                                    "password: ", "Database Searcher",
                                            JOptionPane.QUESTION_MESSAGE);
                                    writer.write(password);
                                    writer.println();
                                    writer.flush();

                                    isValid = Boolean.parseBoolean(reader.readLine());
                                    if (!isValid) {
                                        JOptionPane.showMessageDialog(null, "Invalid " +
                                                        "password. Please ensure it is at least 4 characters long.",
                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                    }
                                } while (!isValid);
                                JOptionPane.showMessageDialog(null, "Account created " +
                                                "successfully!", "Database Searcher",
                                        JOptionPane.INFORMATION_MESSAGE);
                                writer.write("SUCCESS");
                                writer.println();
                                writer.flush();
                                break;
                            }
                            case 2: {  // User logs in
                                boolean allUsers = Boolean.parseBoolean(reader.readLine());
                                boolean checker = false;
                                if (allUsers) {
                                    do {
                                        String username = JOptionPane.showInputDialog(null,
                                                "Enter your username: ", "Database Searcher",
                                                JOptionPane.QUESTION_MESSAGE);
                                        writer.write(username);
                                        writer.println();
                                        writer.flush();
                                        String returner = reader.readLine();
                                        if (returner.equals("YES")) {
                                            String password = JOptionPane.showInputDialog(null,
                                                    "Enter your password: ", "Database Searcher",
                                                    JOptionPane.QUESTION_MESSAGE);
                                            writer.write(password);
                                            writer.println();
                                            writer.flush();

                                            checker = Boolean.parseBoolean(reader.readLine());
                                            if (checker) {
                                                writer.write("YES");
                                                writer.println();
                                                writer.flush();
                                                JOptionPane.showMessageDialog(null, "Welcome " +
                                                        "back " + reader.readLine() + "!", "Database " +
                                                        "Searcher", JOptionPane.INFORMATION_MESSAGE);
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Invalid " +
                                                        "username or password. Please try again.", "Database " +
                                                        "Searcher", JOptionPane.ERROR_MESSAGE);
                                                writer.write("NO");
                                                writer.println();
                                                writer.flush();
                                            }
                                        }
                                    } while(!checker);
                                } else {
                                    JOptionPane.showMessageDialog(null, "User database " +
                                            "not found. Please exit program and create an account", "Database " +
                                            "Searcher", JOptionPane.ERROR_MESSAGE);
                                    stay = false; // 'stay' controls whether to continue in the main loop
                                }
                                writer.write("SUCCESS");
                                writer.println();
                                writer.flush();
                                break;
                            }
                            default: {
                                JOptionPane.showMessageDialog(null, "Please enter a " +
                                        "valid number!", "Database " +
                                        "Searcher", JOptionPane.ERROR_MESSAGE);
                                userChoice = Integer.parseInt(JOptionPane.showInputDialog(null,
                                        "Would you like to create an account or log-in?" + "\n" + "1. " +
                                                "Create an account" + "\n" + "2. Log-in" + "\n" + "3. Exit",
                                        "Database Searcher", JOptionPane.QUESTION_MESSAGE));
                                writer.write(String.valueOf(userChoice));
                                writer.println();
                                writer.flush();
                            }
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Please enter a number!",
                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                        userChoice = Integer.parseInt(JOptionPane.showInputDialog(null,
                                "Would you like to create an account or log-in?" + "\n" + "1. Create " +
                                        "an account" + "\n" + "2. Log-in" + "\n" + "3. Exit",
                                "Database Searcher", JOptionPane.QUESTION_MESSAGE));
                        writer.write(String.valueOf(userChoice));
                        writer.println();
                        writer.flush();
                    }
                } while (userChoice != 1 && userChoice != 2 && userChoice != 3);

                boolean multiple = Boolean.parseBoolean(reader.readLine());
                writer.write(String.valueOf(multiple));
                writer.println();
                writer.flush();
                if (!multiple) {
                    JOptionPane.showMessageDialog(null, "Sorry! BoilerTown cannot currently " +
                                    "be used due to less than two users existing on the platform.",
                            "Database Searcher", JOptionPane.INFORMATION_MESSAGE);
                    stay = false;
                }

                newAccount = Boolean.parseBoolean(reader.readLine());
                writer.write(String.valueOf(newAccount));
                writer.println();
                writer.flush();
                if (!newAccount) {
                    while (stay) {  // loops while user wants to do things
                        do {
                            boolean userFriends = Boolean.parseBoolean(reader.readLine());
                            if (userFriends) { // user code
                                do {
                                    try {
                                        userChoice2 = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                "What would you like to do?" + "\n" + "1. View all " +
                                                        "BoilerTown users " + "\n" + "2. Search BoilerTown users" +
                                                        "\n" + "3. View/Interact with your friends" + "\n" + "4. Edit Account" +
                                                        "\n" + "5. Exit", "Database Searcher",
                                                JOptionPane.QUESTION_MESSAGE));
                                        writer.write(String.valueOf(userChoice2));
                                        writer.println();
                                        writer.flush();
                                        switch (userChoice2) {
                                            case 1: {  // searches through all users
                                                boolean allUsers = Boolean.parseBoolean(reader.readLine());
                                                writer.write(String.valueOf(allUsers));
                                                writer.println();
                                                writer.flush();
                                                if (!allUsers) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "ERROR! no users have been created yet!",
                                                            "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                                } else {  // there are users to look at
                                                    String usersConvert = reader.readLine();
                                                    usersConvert = usersConvert.substring(1, usersConvert.length() - 1);
                                                    String[] users = usersConvert.split(", ");

                                                    if (usersConvert.isEmpty()) {
                                                        JOptionPane.showMessageDialog(null,
                                                                "File cannot be found!",
                                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                                    }

                                                    boolean cont;
                                                    String selectedUserName;
                                                    do {
                                                        selectedUserName = (String) JOptionPane.showInputDialog(
                                                                null,
                                                                "List of all BoilerTown Users:",
                                                                "Database Searcher", JOptionPane.PLAIN_MESSAGE,
                                                                null, users, null);
                                                        writer.write(selectedUserName);
                                                        writer.println();
                                                        writer.flush();

                                                        cont = Boolean.parseBoolean(reader.readLine());
                                                        if (!cont) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "You can't enter your own username! Please" +
                                                                            " put another username.",
                                                                    "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } while (!cont);

                                                    int userChoice3 = 0;

                                                    do {
                                                        try {
                                                            userChoice3 = Integer.parseInt(JOptionPane.
                                                                    showInputDialog(null,
                                                                            "What do you want to do?" + "\n" +
                                                                                    "1. View " + selectedUserName + "'s " +
                                                                                    "Profile" + "\n" + "2. Make a friend" +
                                                                                    " request" + "\n" + "3. Exit",
                                                                            "Database Searcher",
                                                                            JOptionPane.QUESTION_MESSAGE));
                                                            writer.write(String.valueOf(userChoice3));
                                                            writer.println();
                                                            writer.flush();
                                                            switch (userChoice3) {
                                                                case 1: {  // looking up a bio for a user
                                                                    boolean userExists = Boolean.parseBoolean(reader.readLine());
                                                                    writer.write("SUCCESS");
                                                                    writer.println();
                                                                    writer.flush();

                                                                    String firstName = "";
                                                                    String lastName = "";
                                                                    String bio = "";
                                                                    String username = "";
                                                                    if (userExists) {
                                                                        firstName = reader.readLine();
                                                                        writer.write(firstName);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        lastName = reader.readLine();
                                                                        writer.write(lastName);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        bio = reader.readLine();
                                                                        writer.write(bio);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        username = reader.readLine();
                                                                        writer.write(username);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        JOptionPane.showMessageDialog(null,
                                                                                selectedUserName + "'s Profile" +
                                                                                        "\n" + "Name: " + firstName +
                                                                                        " " + lastName + "\n" +
                                                                                        "Username: " + username + "\n" +
                                                                                        "Bio: " + bio,
                                                                                "Database Searcher",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,
                                                                                "The profile file for" +
                                                                                        selectedUserName + " does not exist.",
                                                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                                                    }

                                                                    break;
                                                                }
                                                                case 2: { // make a friend request
                                                                    boolean targetFile = Boolean.parseBoolean(reader.readLine());
                                                                    if (targetFile) {
                                                                        JOptionPane.showInputDialog(null,
                                                                                "What would you like to say to " +
                                                                                        selectedUserName + "?",
                                                                                "Database Searcher",
                                                                                JOptionPane.QUESTION_MESSAGE);
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,
                                                                                " does not exist or has blocked " +
                                                                                        "friend requests.",
                                                                                "Database Searcher",
                                                                                JOptionPane.ERROR_MESSAGE);
                                                                    }
                                                                    break;
                                                                }
                                                                default: {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Please choose a valid number!",
                                                                            "Database Searcher",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                                    userChoice3 = Integer.parseInt(JOptionPane.
                                                                            showInputDialog(null,
                                                                                    "What do you want to do?" + "\n" +
                                                                                            "1. View " + selectedUserName + "'s " +
                                                                                            "Profile" + "\n" + "2. Make a friend" +
                                                                                            "request" + "\n" + "3. Exit",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.QUESTION_MESSAGE));
                                                                    writer.write(String.valueOf(userChoice3));
                                                                    writer.println();
                                                                    writer.flush();
                                                                }
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Please enter a number!",
                                                                    "Database Searcher",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                            userChoice3 = Integer.parseInt(JOptionPane.
                                                                    showInputDialog(null,
                                                                            "What do you want to do?" + "\n" +
                                                                                    "1. View " + selectedUserName + "'s " +
                                                                                    "Profile" + "\n" + "2. Make a friend" +
                                                                                    "request" + "\n" + "3. Exit",
                                                                            "Database Searcher",
                                                                            JOptionPane.QUESTION_MESSAGE));
                                                            writer.write(userChoice3);
                                                            writer.println();
                                                            writer.flush();
                                                        }
                                                    } while (userChoice3 != 1 && userChoice3 != 2);
                                                }
                                                break;
                                            }

                                            case 2: { // view
                                                boolean finder = Boolean.parseBoolean(reader.readLine());
                                                if (!finder) {
                                                    JOptionPane.showMessageDialog(null,
                                                            "ERROR! no users have been created yet!",
                                                            "Database Searcher",
                                                            JOptionPane.ERROR_MESSAGE);
                                                } else {  // there are users to look at
                                                    // return the user that the user chooses
                                                    String selectedUserName;
                                                    boolean validUsername = true;
                                                    do {
                                                        selectedUserName = JOptionPane.
                                                                showInputDialog(null,
                                                                        "Please enter the name of the user " +
                                                                                "you want to look into:",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                        writer.write(selectedUserName);
                                                        writer.println();
                                                        writer.flush();

                                                        validUsername = Boolean.parseBoolean(reader.readLine());
                                                        writer.write(String.valueOf(validUsername));
                                                        writer.println();
                                                        writer.flush();
                                                        String differentiate = reader.readLine();
                                                        if (differentiate.equals("ERROR! The username you entered is " +
                                                                "not a current user!")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "ERROR! The user you entered is not " +
                                                                            "a current user!",
                                                                    "Database Searcher",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                        if (differentiate.equals("You can't enter your own username! " +
                                                                "Please put another username.")) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "You can't enter your own username!" +
                                                                            "Please put another username.",
                                                                    "Database Searcher",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    } while (!validUsername);

                                                    int userChoice3 = 0;
                                                    do {
                                                        userChoice3 = Integer.parseInt(JOptionPane.
                                                                showInputDialog(null,
                                                                        "What do you want to do?" + "\n" +
                                                                                "1. View " + selectedUserName + "'s " +
                                                                                "Profile" + "\n" + "2. Make a friend" +
                                                                                "request" + "\n" + "3. Exit",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE));
                                                        writer.write(String.valueOf(userChoice3));
                                                        writer.println();
                                                        writer.flush();
                                                        try {
                                                            switch (userChoice3) {
                                                                case 1: {  // looking up information of a user
                                                                    boolean userExists = Boolean.parseBoolean(reader.readLine());
                                                                    writer.write("SUCCESS");
                                                                    writer.println();
                                                                    writer.flush();

                                                                    String firstName = "";
                                                                    String lastName = "";
                                                                    String bio = "";
                                                                    String username = "";
                                                                    if (userExists) {
                                                                        firstName = reader.readLine();
                                                                        writer.write(firstName);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        lastName = reader.readLine();
                                                                        writer.write(lastName);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        bio = reader.readLine();
                                                                        writer.write(bio);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        username = reader.readLine();
                                                                        writer.write(username);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        JOptionPane.showMessageDialog(null,
                                                                                selectedUserName + "'s Profile" +
                                                                                        "\n" + "Name: " + firstName +
                                                                                        " " + lastName + "\n" +
                                                                                        "Username: " + username + "\n" +
                                                                                        "Bio: " + bio,
                                                                                "Database Searcher",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,
                                                                                "The profile file for" +
                                                                                        selectedUserName + " does not exist.",
                                                                                "Database Searcher", JOptionPane.ERROR_MESSAGE);
                                                                    }
                                                                    break;
                                                                }
                                                                case 2: { // make a friend request
                                                                    boolean target = Boolean.parseBoolean(reader.readLine());
                                                                    if (target) {
                                                                        String message = JOptionPane.
                                                                                showInputDialog(null,
                                                                                        "What would you like" +
                                                                                                "to say to " +
                                                                                                selectedUserName,
                                                                                        "Database Searcher",
                                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                        writer.write(message);
                                                                        writer.println();
                                                                        writer.flush();
                                                                        String confirm = reader.readLine();
                                                                        if (confirm.equals("confirm")) {
                                                                            JOptionPane.showMessageDialog(null,
                                                                                    "Request Sent!",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                                        }

                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null,
                                                                                selectedUserName + " does not" +
                                                                                        "exist or has blocked friend" +
                                                                                        "requests.",
                                                                                "Database Searcher",
                                                                                JOptionPane.ERROR_MESSAGE);
                                                                    }
                                                                    break;
                                                                }
                                                                case 3: {
                                                                    stay = false;
                                                                    break;
                                                                }
                                                                default: {
                                                                    userChoice3 = Integer.parseInt(JOptionPane.
                                                                            showInputDialog(null,
                                                                                    "What do you want to do?" + "\n" +
                                                                                            "1. View " + selectedUserName + "'s " +
                                                                                            "Profile" + "\n" + "2. Make a friend" +
                                                                                            "request" + "\n" + "3. Exit",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.QUESTION_MESSAGE));
                                                                    writer.write(String.valueOf(userChoice3));
                                                                    writer.println();
                                                                    writer.flush();
                                                                }
                                                            }
                                                        } catch (InputMismatchException e) {
                                                            userChoice3 = Integer.parseInt(JOptionPane.
                                                                    showInputDialog(null,
                                                                            "What do you want to do?" + "\n" +
                                                                                    "1. View " + selectedUserName + "'s " +
                                                                                    "Profile" + "\n" + "2. Make a friend" +
                                                                                    "request" + "\n" + "3. Exit",
                                                                            "Database Searcher",
                                                                            JOptionPane.QUESTION_MESSAGE));
                                                            writer.write(String.valueOf(userChoice3));
                                                            writer.println();
                                                            writer.flush();
                                                        }
                                                    } while (userChoice3 != 1 && userChoice3 != 2 && userChoice3 != 3);
                                                }
                                                break;
                                            }

                                            case 3: {  // View all your friends

                                                int userChoice3 = 0;

                                                do {
                                                    try {
                                                        userChoice3 = Integer.parseInt(JOptionPane.
                                                                showInputDialog(null,
                                                                        "What do you want to do?" + "\n" +
                                                                                "1. Message my friends" + "\n" +
                                                                                "2. View my friend requests" +
                                                                                "\n" + "3. Remove a friend" + "\n" +
                                                                                "4. Block a friend" + "\n" + "5. Exit",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE));
                                                        writer.write(String.valueOf(userChoice3));
                                                        writer.println();
                                                        writer.flush();

                                                        switch (userChoice3) {
                                                            case 1: {  // message your friend
                                                                String nameOfUser = reader.readLine();
                                                                writer.write(nameOfUser);
                                                                writer.println();
                                                                writer.flush();

                                                                boolean friend = Boolean.parseBoolean(reader.readLine());
                                                                if (friend) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            nameOfUser + "'s friends list:",
                                                                            "Database Searcher",
                                                                            JOptionPane.INFORMATION_MESSAGE);

                                                                    boolean validUsername = false;
                                                                    String selectedUserName;
                                                                    do {
                                                                        // message mechanics implemented here and only here
                                                                        selectedUserName = JOptionPane.
                                                                                showInputDialog(null,
                                                                                        "Who do you want to " +
                                                                                                "message?",
                                                                                        "Database Searcher",
                                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                        writer.write(selectedUserName);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        boolean friends = Boolean.parseBoolean(reader.readLine());
                                                                        if (!friends) {
                                                                            JOptionPane.showMessageDialog(null,
                                                                                    "You can only select" +
                                                                                            "usernames that you are" +
                                                                                            "friends with.",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.ERROR_MESSAGE);
                                                                        }
                                                                    } while (!validUsername);

                                                                    int userChoice4 = Integer.parseInt(JOptionPane.
                                                                            showInputDialog(null,
                                                                                    "What do you want to do?" +
                                                                                            "\n" + "1. Message stuff" +
                                                                                            "\n" + "2. Delete a message" +
                                                                                            "\n" + "3. Exit",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.QUESTION_MESSAGE));
                                                                    writer.write(userChoice4);
                                                                    writer.println();
                                                                    writer.flush();

                                                                    if (userChoice4 == 1) {
                                                                        //Enter message
                                                                        String message = JOptionPane.
                                                                                showInputDialog(null,
                                                                                        "Please enter your message:",
                                                                                        "Database Searcher",
                                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                        writer.write(message);
                                                                        writer.println();
                                                                        writer.flush();
                                                                        boolean fill = Boolean.parseBoolean(reader.readLine());
                                                                        if (fill) {
                                                                            JOptionPane.showMessageDialog(null,
                                                                                    "Successfully sent!",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null,
                                                                                    "Failed send...",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.ERROR_MESSAGE);
                                                                        }
                                                                    } else if (userChoice4 == 2) {
                                                                        String reeds = reader.readLine();
                                                                        if (reeds.equals("No")) {
                                                                            JOptionPane.showMessageDialog(null,
                                                                                    "It seems that you don't" +
                                                                                            "have a chat with your" +
                                                                                            "friend.",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                                        }

                                                                        String delete = JOptionPane.showInputDialog(null,
                                                                                "Please enter which" +
                                                                                        "message you want" +
                                                                                        "to delete (Enter" +
                                                                                        "the whole message)",
                                                                                "Database Searcher",
                                                                                JOptionPane.QUESTION_MESSAGE);
                                                                        writer.write(delete);
                                                                        writer.println();
                                                                        writer.flush();

                                                                        boolean deleter = Boolean.parseBoolean(reader.readLine());
                                                                        if (deleter) {
                                                                            JOptionPane.showMessageDialog(null,
                                                                                    "Successfully deleted!",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(null,
                                                                                    "Failed delete...Please" +
                                                                                            "check if you had this" +
                                                                                            "message with your friend.",
                                                                                    "Database Searcher",
                                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                                        }
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Sorry! You currently don't have" +
                                                                                    " any friends.",
                                                                            "Database Searcher",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                }
                                                                break;
                                                            }
                                                            case 2: { // view friend request
                                                                boolean client = Boolean.parseBoolean(reader.readLine());

                                                                if (!client) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "You have no friend requests at" +
                                                                                    " this time.",
                                                                            "Database Searcher",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                } else {
                                                                    String action = JOptionPane.showInputDialog(null,
                                                                            "Do you want to approve or decline" +
                                                                                    "a friend request? (approve/decline)",
                                                                            "Database Searcher",
                                                                            JOptionPane.QUESTION_MESSAGE);

                                                                    if (!action.equals("approve") &&
                                                                            !action.equals("decline")) {
                                                                        System.out.println("Invalid action.");
                                                                    }
                                                                    writer.write(action);
                                                                    writer.println();
                                                                    writer.flush();

                                                                    String friendUsername = JOptionPane.showInputDialog(null,
                                                                            "Enter the username of the friend" +
                                                                                    "request:",
                                                                            "Database Searcher",
                                                                            JOptionPane.QUESTION_MESSAGE);
                                                                    writer.write(friendUsername);
                                                                    writer.println();
                                                                    writer.flush();
                                                                    // needs validation

                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Friend requested " + action,
                                                                            "Database Searcher",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                }
                                                                break;
                                                            }
                                                            case 3: { // Remove a friend
                                                                boolean hasfriends = Boolean.parseBoolean(reader.readLine());
                                                                if (hasfriends) {
                                                                    String removeUsername = JOptionPane.showInputDialog(null,
                                                                            "Enter the username of the friend" +
                                                                                    "you wish to remove:",
                                                                            "Database Searcher",
                                                                            JOptionPane.QUESTION_MESSAGE);
                                                                    writer.write(removeUsername);
                                                                    writer.println();
                                                                    writer.flush();
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Sorry! You currently don't have" +
                                                                                    "any friends.",
                                                                            "Database Searcher",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                }
                                                                break;
                                                            }
                                                            case 4: { // block a friend
                                                                boolean friendstwo = Boolean.parseBoolean(reader.readLine());
                                                                if (friendstwo) {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            user.getUsername() + "'s friends" +
                                                                                    "list:",
                                                                            "Database Searcher",
                                                                            JOptionPane.INFORMATION_MESSAGE);

                                                                    String selectedUserName = JOptionPane.showInputDialog(null,
                                                                            "Who do you want to block?",
                                                                            "Database Searcher",
                                                                            JOptionPane.QUESTION_MESSAGE);
                                                                    writer.write(selectedUserName);
                                                                    writer.println();
                                                                    writer.flush();

                                                                    boolean three = Boolean.parseBoolean(reader.readLine());
                                                                    // Validate that the selected username is a friend
                                                                    if (!three) {
                                                                        JOptionPane.showMessageDialog(null,
                                                                                "You can only select" +
                                                                                        "usernames that you are" +
                                                                                        " friends with.",
                                                                                "Database Searcher",
                                                                                JOptionPane.INFORMATION_MESSAGE);
                                                                    }
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Sorry! You currently don't have" +
                                                                                    " any friends.",
                                                                            "Database Searcher",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                }
                                                                break;
                                                            }
                                                            case 5: {
                                                                stay = false;
                                                                break;
                                                            }
                                                            default: {
                                                                userChoice3 = Integer.parseInt(JOptionPane.
                                                                        showInputDialog(null,
                                                                                "What do you want to do?" + "\n" +
                                                                                        "1. Message my friends" + "\n" +
                                                                                        "2. View my friend requests" +
                                                                                        "\n" + "3. Remove a friend" + "\n" +
                                                                                        "4. Block a friend" + "\n" + "5. Exit",
                                                                                "Database Searcher",
                                                                                JOptionPane.QUESTION_MESSAGE));
                                                            }
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Please enter a number!",
                                                                "Database Searcher",
                                                                JOptionPane.ERROR_MESSAGE);
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
                                                    editOption = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                            "What do you want to edit about your account?" +
                                                                    "\n" + "1. First name\n2. Last name\n3. Bio\n4. " +
                                                                    "Email\n5. Password",
                                                            "Database Searcher",
                                                            JOptionPane.QUESTION_MESSAGE));
                                                    writer.write(String.valueOf(editOption));
                                                    writer.println();
                                                    writer.flush();

                                                    boolean isValid;
                                                    switch (editOption) {
                                                        case 1:
                                                            do {
                                                                editValue = JOptionPane.showInputDialog(null,
                                                                        "Enter new first name:",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                writer.write(editValue);
                                                                writer.println();
                                                                writer.flush();

                                                                isValid = Boolean.parseBoolean(reader.readLine());
                                                                if (!isValid)
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Invalid first name." +
                                                                                    "Please try again.",
                                                                            "Database Searcher",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                            } while (!isValid);
                                                            user.setFirstName(editValue);
                                                            break;
                                                        case 2:
                                                            do {
                                                                editValue = JOptionPane.showInputDialog(null,
                                                                        "Enter new last name:",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                writer.write(editValue);
                                                                writer.println();
                                                                writer.flush();

                                                                isValid = Boolean.parseBoolean(reader.readLine());
                                                                if (!isValid)
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Invalid last name." +
                                                                                    "Please try again.",
                                                                            "Database Searcher",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                            } while (!isValid);
                                                            user.setLastName(editValue);
                                                            break;
                                                        case 3:
                                                            do {
                                                                editValue = JOptionPane.showInputDialog(null,
                                                                        "Enter new bio:",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                writer.write(editValue);
                                                                writer.println();
                                                                writer.flush();

                                                                isValid = Boolean.parseBoolean(reader.readLine());
                                                                if (!isValid)
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Invalid bio. Please ensure it " +
                                                                                    "is between 20 to 40 characters." +
                                                                                    "Please try again.",
                                                                            "Database Searcher",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                            } while (!isValid);
                                                            user.setBio(editValue);
                                                            break;
                                                        case 4:
                                                            do {
                                                                editValue = JOptionPane.showInputDialog(null,
                                                                        "Enter new email:",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                writer.write(editValue);
                                                                writer.println();
                                                                writer.flush();

                                                                isValid = Boolean.parseBoolean(reader.readLine());
                                                                if (!isValid)
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Invalid email." +
                                                                                    "Please try again.",
                                                                            "Database Searcher",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                            } while (!isValid);
                                                            user.setEmail(editValue);
                                                            break;
                                                        case 5:
                                                            do {
                                                                editValue = JOptionPane.showInputDialog(null,
                                                                        "Enter new password:",
                                                                        "Database Searcher",
                                                                        JOptionPane.QUESTION_MESSAGE);
                                                                writer.write(editValue);
                                                                writer.println();
                                                                writer.flush();

                                                                isValid = Boolean.parseBoolean(reader.readLine());
                                                                if (!isValid)
                                                                    JOptionPane.showMessageDialog(null,
                                                                            "Invalid password. Please ensure " +
                                                                                    "it is at least 4 characters long." +
                                                                                    "Please try again.",
                                                                            "Database Searcher",
                                                                            JOptionPane.ERROR_MESSAGE);
                                                            } while (!isValid);
                                                            user.setPassword(editValue);
                                                            break;
                                                        default:
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Invalid option, please choose again." +
                                                                            "Please try again.",
                                                                    "Database Searcher",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                            continue;
                                                    }

                                                    // Assuming there's a method to update the user's information in the file after changes
                                                    JOptionPane.showMessageDialog(null,
                                                            "You changes have been made!",
                                                            "Database Searcher",
                                                            JOptionPane.INFORMATION_MESSAGE);
                                                    continueEditing = false;
                                                }
                                                break;
                                            }
                                            case 5: {
                                                stay = false;
                                                break;
                                            }
                                            default: {
                                                userChoice2 = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                        "What would you like to do?" + "\n" + "1. View all " +
                                                                "BoilerTown users " + "\n" + "2. Search BoilerTown users" +
                                                                "\n" + "3. View/Interact with your friends" + "\n" + "4. Edit Account" +
                                                                "\n" + "5. Exit", "Database Searcher",
                                                        JOptionPane.QUESTION_MESSAGE));
                                                writer.write(String.valueOf(userChoice2));
                                                writer.println();
                                                writer.flush();
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
                                    userChoiceFinal = JOptionPane.showConfirmDialog(null,
                                            "Do you wish to leave?",
                                            "Database Searcher", JOptionPane.YES_NO_OPTION);
                                    writer.write(userChoiceFinal);
                                    writer.println();
                                    writer.flush();
                                    if (userChoiceFinal != JOptionPane.YES_OPTION && userChoiceFinal !=
                                            JOptionPane.NO_OPTION) {
                                        JOptionPane.showMessageDialog(null,
                                                "Please enter a valid number!",
                                                "Database Searcher",
                                                JOptionPane.INFORMATION_MESSAGE);
                                    }
                                } while (userChoiceFinal != JOptionPane.YES_OPTION && userChoiceFinal !=
                                        JOptionPane.NO_OPTION);
                            } else {
                                userChoiceFinal = JOptionPane.NO_OPTION;
                            }
                        } while (userChoiceFinal == JOptionPane.NO_OPTION);
                    }
                } else { // if there aren't enough users, the program will close
                    JOptionPane.showMessageDialog(null,
                            "Thank you for making an account! Please log back in to access app features!",
                            "Database Searcher",
                            JOptionPane.INFORMATION_MESSAGE);
                }

                JOptionPane.showMessageDialog(null,
                        "Goodbye! You have been logged out.",
                        "Database Searcher",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Goodbye! You have been logged out.",
                        "Database Searcher",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            System.out.print("");
        } catch (NullPointerException e) {
            System.out.print("");
        }
    }

}
