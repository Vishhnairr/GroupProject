import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Servers {

    public static void main(String[] args) {
        // start
        //Scanner scanner = new Scanner(System.in);
        boolean stay = true;
        boolean newAccount = false;
        User user = new User();
        int userChoiceFinal = 0;
        String userChoice = null;
        int userChoice2 = 0;

        try {

            ServerSocket serverSocket = new ServerSocket(4242);
            Socket socket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            do {
                userChoice = reader.readLine();

                try {
                    switch (userChoice) {
                        case "1": {  // user creates account
                            String firstName, lastName, email, bio, username, password;
                            boolean isValid;

                            // First Name
                            do {
                                firstName = reader.readLine();
                                isValid = user.checkFirstName(firstName);
                            } while (!isValid);

                            // Last Name
                            do {
                                lastName = reader.readLine();
                                isValid = user.checkLastName(lastName);
                            } while (!isValid);

                            // Email
                            do {
                                email = reader.readLine();
                                isValid = user.checkEmail(email);
                            } while (!isValid);

                            // Bio
                            do {
                                bio = reader.readLine();
                                isValid = user.checkBio(bio);
                            } while (!isValid);

                            // Username
                            do {
                                username = reader.readLine();
                                isValid = user.checkUsername(username);
                            } while (!isValid);

                            // Password
                            do {
                                password = reader.readLine();
                                isValid = user.checkPassword(password);
                            } while (!isValid);

                            user = user.createAccount(firstName, lastName, email, bio, username, password);
                            newAccount = true;
                            break;
                        }
                        case "2": {  // User logs in
                            File allUsersFile = new File("All_User_Info.txt");
                            if (allUsersFile.exists()) {
                                User loggedInUser = null;
                                boolean isLoggedIn = false;
                                while (!isLoggedIn) {
                                    String username = reader.readLine();

                                    String password = reader.readLine();

                                    loggedInUser = user.logIn(username, password);  // Assuming 'user' is an instance capable of logging in
                                    writer.write(String.valueOf(loggedInUser));
                                    writer.println();
                                    writer.flush();
                                    if (loggedInUser != null) {
                                        isLoggedIn = true;
                                        user = loggedInUser;
                                    }
                                }
                            } else {
                                stay = false; // 'stay' controls whether to continue in the main loop
                            }
                            break;
                        }
                        case "3": {  // user wants to exit
                            stay = false;
                            break;
                        }
                        default: {
                            userChoice = reader.readLine();
                        }
                    }
                } catch (NumberFormatException e) {
                    userChoice = reader.readLine();
                }
            } while (!userChoice.equals("1") && !userChoice.equals("2") && !userChoice.equals("3"));
            if (!User.checkMoreOneUser()) {
                stay = false;
            }

            if (!newAccount) {

                while (stay) {  // loops while user wants to do things
                    do {
                        if (user instanceof Friends) { // user code
                            Friends person = (Friends) user;

                            do {
                                try {
                                    userChoice2 = Integer.parseInt(reader.readLine());
                                    switch (userChoice2) {
                                        case 1: {  // searches through all users
                                            File allUsersFile = new File("All_User_Info.txt");
                                            if (!allUsersFile.exists()) {
                                                System.out.println("ERROR! no users have been created yet!");
                                            } else {  // there are users to look at
                                                ArrayList<String> users = new ArrayList<>();
                                                String[] usersFinal = null;
                                                String fileLine;
                                                try (BufferedReader br = new BufferedReader
                                                        (new FileReader(allUsersFile))) {
                                                    fileLine = br.readLine();
                                                    while (fileLine != null && !fileLine.equals("\n") &&
                                                            !fileLine.equals("")) {
                                                        users.add(fileLine + ",");
                                                        fileLine = br.readLine();
                                                    }
                                                    usersFinal = new String[users.size()];
                                                    users.toArray(usersFinal);
                                                    writer.write(Arrays.toString(usersFinal));
                                                    writer.println();
                                                    writer.flush();
                                                } catch (IOException e) {
                                                    users = null;
                                                    writer.write(String.valueOf(users));
                                                    writer.println();
                                                    writer.flush();
                                                }

                                                String selectedUserName = reader.readLine();

                                                int userChoice3 = 0;

                                                do {
                                                    try {
                                                        userChoice3 = Integer.parseInt(reader.readLine());
                                                        switch (userChoice3) {
                                                            case 1: {  // looking up a bio for a user
                                                                person.viewProfile(selectedUserName);
                                                                break;
                                                            }
                                                            case 2: { // make a friend request
                                                                File targetUserFile = new File("User_"
                                                                        + selectedUserName + "_Friends.txt");
                                                                writer.write(String.valueOf(targetUserFile.exists()));
                                                                writer.println();
                                                                writer.flush();
                                                                if (targetUserFile.exists()) {
                                                                    Friends targetUser = new Friends(); // You might
                                                                    // want to load this user's details if necessary
                                                                    targetUser.setUsername(selectedUserName); // Set
                                                                    // the username of the target user
                                                                    // Send the friend request using the method
                                                                    // from Friends class
                                                                    String message = reader.readLine();
                                                                    person.makeFriendRequest(message, selectedUserName,
                                                                            user.getUsername());
                                                                }
                                                                break;
                                                            }
                                                            default: {
                                                                userChoice3 = Integer.parseInt(reader.readLine());
                                                            }
                                                        }
                                                    } catch (NumberFormatException e) {
                                                        userChoice3 = Integer.parseInt(reader.readLine());
                                                    }
                                                } while (userChoice3 != 1 && userChoice3 != 2);
                                            }
                                            break;
                                        }

                                        case 2: { // view
                                            File allUsersFile = new File("All_User_Info.txt");
                                            boolean allUsersExists = allUsersFile.exists();
                                            writer.write(String.valueOf(allUsersExists));
                                            writer.println();
                                            writer.flush();
                                            if (allUsersFile.exists()) {  // there are users to look at
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
                                                    selectedUserName = reader.readLine();

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
                                                    writer.write(String.valueOf(validUsername));
                                                    writer.println();
                                                    writer.flush();
                                                } while (!validUsername);

                                                int userChoice3 = 0;
                                                do {
                                                    userChoice3 = Integer.parseInt(reader.readLine());
                                                    try {
                                                        switch (userChoice3) {
                                                            case 1: {  // looking up information of a user
                                                                person.viewProfile(selectedUserName);
                                                                break;
                                                            }
                                                            case 2: { // make a friend request
                                                                File targetUserFile = new File("User_" +
                                                                        selectedUserName + "_Friends.txt");
                                                                boolean targetUserExists = targetUserFile.exists();
                                                                writer.write(String.valueOf(targetUserExists));
                                                                writer.println();
                                                                writer.flush();
                                                                if (targetUserFile.exists()) {
                                                                    Friends targetUser = new Friends(); // You might
                                                                    // want to load this user's details if necessary
                                                                    targetUser.setUsername(selectedUserName); // Set
                                                                    // the username of the target user
                                                                    // Send the friend request using the method from
                                                                    // Friends class
                                                                    String message = reader.readLine();
                                                                    person.makeFriendRequest(message, selectedUserName,
                                                                            user.getUsername());
                                                                }
                                                                break;
                                                            }
                                                            case 3: {
                                                                stay = false;
                                                                break;
                                                            }
                                                            default: {
                                                                userChoice3 = Integer.parseInt(reader.readLine());
                                                            }
                                                        }
                                                    } catch (InputMismatchException e) {
                                                        userChoice3 = Integer.parseInt(reader.readLine());
                                                    }
                                                } while (userChoice3 != 1 && userChoice3 != 2 && userChoice3 != 3);
                                            }
                                            break;
                                        }

                                        case 3: {  // View all your friends
                                            int userChoice3 = Integer.parseInt(reader.readLine());

                                            do {
                                                try {
                                                    userChoice3 = Integer.parseInt(reader.readLine());
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
                                                                    selectedUserName = reader.readLine();
                                                                    writer.write(String.valueOf(friendUsernames.contains(selectedUserName)));
                                                                    writer.println();
                                                                    writer.flush();
                                                                    if (friendUsernames.contains(selectedUserName)) {
                                                                        validUsername = true;
                                                                    }
                                                                } while (!validUsername);

                                                                int userChoice4 = Integer.parseInt(reader.readLine());

                                                                if (userChoice4 == 1) {
                                                                    //Enter message
                                                                    String message = reader.readLine();
                                                                    MessageList send = new MessageList(message, user,
                                                                            selectedUserName);
                                                                    boolean fill = send.sendMessage();
                                                                    writer.write(String.valueOf(fill));
                                                                    writer.println();
                                                                    writer.flush();
                                                                } else if (userChoice4 == 2) {
                                                                    File file = new File(user.getUsername()
                                                                            + "_" + selectedUserName + ".txt");
                                                                    String except = "";
                                                                    try {
                                                                        FileReader fr = new FileReader(file);
                                                                        BufferedReader bfr = new BufferedReader(fr);
                                                                        String line = bfr.readLine();
                                                                        while (line != null) {
                                                                            System.out.println(line);
                                                                            line = bfr.readLine();
                                                                        }
                                                                    } catch (Exception e) {
                                                                        except = "No";
                                                                    }
                                                                    writer.write(except);
                                                                    writer.println();
                                                                    writer.flush();

                                                                    String delete = reader.readLine();
                                                                    MessageList deleteIt = new MessageList(delete,
                                                                            user, selectedUserName);
                                                                    boolean deleted = deleteIt.deleteMessage(delete);
                                                                    writer.write(String.valueOf(deleted));
                                                                    writer.println();
                                                                    writer.flush();
                                                                }
                                                            }
                                                            break;
                                                        }
                                                        case 2: { // view friend request
                                                            boolean clientele = true;
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
                                                                clientele = false;
                                                                writer.write(String.valueOf(clientele));
                                                                writer.println();
                                                                writer.flush();
                                                            } else {
                                                                writer.write(String.valueOf(clientele));
                                                                writer.println();
                                                                writer.flush();

                                                                String action = reader.readLine();
                                                                String friendUsername = reader.readLine();

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
                                                            }
                                                            break;
                                                        }
                                                        case 3: { // Remove a friend
                                                            boolean hasfriends = false;
                                                            if (person.hasFriends(user.getUsername())) {
                                                                hasfriends = true;
                                                                writer.write(String.valueOf(hasfriends));
                                                                writer.println();
                                                                writer.flush();
                                                                person.friendViewer(user.getUsername());

                                                                String removeUsername = reader.readLine();
                                                                person.removeUser(user.getUsername(), removeUsername);
                                                            } else {
                                                                writer.write(String.valueOf(hasfriends));
                                                                writer.println();
                                                                writer.flush();
                                                            }
                                                            break;
                                                        }
                                                        case 4: { // block a friend
                                                            boolean hasfriends = false;
                                                            if (person.hasFriends(user.getUsername())) {
                                                                hasfriends = true;
                                                                writer.write(String.valueOf(hasfriends));
                                                                writer.println();
                                                                writer.flush();
                                                                ArrayList<String> friendUsernames =
                                                                        person.friendViewer(user.getUsername());
                                                                String selectedUserName = reader.readLine();
                                                                boolean two = false;
                                                                // Validate that the selected username is a friend
                                                                if (friendUsernames.contains(selectedUserName)) {
                                                                    two = true;
                                                                    writer.write(String.valueOf(two));
                                                                    writer.println();
                                                                    writer.flush();
                                                                    person.blockUser(selectedUserName,
                                                                            user.getUsername());
                                                                } else {
                                                                    two = false;
                                                                    writer.write(String.valueOf(two));
                                                                    writer.println();
                                                                    writer.flush();

                                                                }
                                                            } else {
                                                                writer.write(String.valueOf(hasfriends));
                                                                writer.println();
                                                                writer.flush();
                                                            }
                                                            break;
                                                        }
                                                        case 5: {
                                                            stay = false;
                                                            break;
                                                        }
                                                        default: {
                                                            userChoice3 = Integer.parseInt(reader.readLine());
                                                        }
                                                    }
                                                } catch (NumberFormatException e) {
                                                    //System.out.println("Please enter a number!");
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
                                                editOption = Integer.parseInt(reader.readLine());

                                                boolean isValid;
                                                switch (editOption) {
                                                    case 1:
                                                        do {
                                                            editValue = reader.readLine();
                                                            isValid = user.checkFirstName(editValue);
                                                            writer.write(String.valueOf(isValid));
                                                            writer.println();
                                                            writer.flush();
                                                        } while (!isValid);
                                                        user.setFirstName(editValue);
                                                        break;
                                                    case 2:
                                                        do {
                                                            editValue = reader.readLine();
                                                            isValid = user.checkFirstName(editValue);
                                                            writer.write(String.valueOf(isValid));
                                                            writer.println();
                                                            writer.flush();
                                                        } while (!isValid);
                                                        user.setLastName(editValue);
                                                        break;
                                                    case 3:
                                                        do {
                                                            editValue = reader.readLine();
                                                            isValid = user.checkFirstName(editValue);
                                                            writer.write(String.valueOf(isValid));
                                                            writer.println();
                                                            writer.flush();
                                                        } while (!isValid);
                                                        user.setBio(editValue);
                                                        break;
                                                    case 4:
                                                        do {
                                                            editValue = reader.readLine();
                                                            isValid = user.checkFirstName(editValue);
                                                            writer.write(String.valueOf(isValid));
                                                            writer.println();
                                                            writer.flush();
                                                        } while (!isValid);
                                                        user.setEmail(editValue);
                                                        break;
                                                    case 5:
                                                        do {
                                                            editValue = reader.readLine();
                                                            isValid = user.checkFirstName(editValue);
                                                            writer.write(String.valueOf(isValid));
                                                            writer.println();
                                                            writer.flush();
                                                        } while (!isValid);
                                                        user.setPassword(editValue);
                                                        break;
                                                    default:
                                                        continue;
                                                }

                                                // Assuming there's a method to update the user's information in the file after changes
                                                user.editAccount(editOption, editValue); // Updates the user information based on editOption and editValue
                                                continueEditing = false;
                                            }
                                            break;
                                        }
                                        case 5: {
                                            stay = false;
                                            break;
                                        }
                                        default: {
                                            userChoice2 = Integer.parseInt(reader.readLine());
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
                                userChoiceFinal = Integer.parseInt(reader.readLine());
                            } while (userChoiceFinal != 1 && userChoiceFinal != 2);
                        } else {
                            userChoiceFinal = 2;
                        }
                    } while (userChoiceFinal == 2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

