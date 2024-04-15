import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
/**
 * Client
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
public class Clients {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Clients(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Clients clients = new Clients("localhost", 8787);

        JOptionPane.showMessageDialog(null, "Welcome to Boiler Town!",
                "Boiler Town", JOptionPane.INFORMATION_MESSAGE);

        do {
            String[] fistOptions = {"Log in", "Sign up", "Exit"};
            String firstChose = (String) JOptionPane.showInputDialog(null,
                    "What do you want to do today?", "Boiler Town",
                    JOptionPane.PLAIN_MESSAGE, null, fistOptions, fistOptions[0]);

            if (firstChose == null) {
                return;

            } else if (firstChose.equals("Log in")) {

                while (true) {
                    String username = JOptionPane.showInputDialog("Please enter your username: ");

                    if (username == null) {
                        return;

                    } else if (username.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");

                    } else {
                        while (true) {
                            String password = JOptionPane.showInputDialog("Please enter your password: ");

                            if (password == null) {
                                return;

                            } else if (password.isEmpty()) {
                                JOptionPane.showMessageDialog(null,
                                        "Error! Your enter is empty!");

                            } else {
                                clients.output.println(firstChose);
                                clients.output.println(username);
                                clients.output.println(password);

                                String user = clients.input.readLine();

                                if (user.equals("null")) {
                                    JOptionPane.showMessageDialog(null,
                                            "Your username or password is wrong!");

                                } else if (user.equals("Log in Successfully!")) {

                                    String[] secondOptions = {"Set your account",
                                            "View All users",
                                            "Search a user",
                                            "View your profile",
                                            "View your friends",
                                            "View your blocks",
                                            "View your friend requests",
                                            "Exit"};

                                    while (true) {
                                        String secondChose = (String) JOptionPane.showInputDialog(
                                                null,
                                                "What do you want to do now?",
                                                "Boiler Town",
                                                JOptionPane.PLAIN_MESSAGE,
                                                null,
                                                secondOptions,
                                                secondOptions[0]);

                                        if (secondChose == null) {
                                            return;

                                        } else if (secondChose.equals("Set your account")) {
                                            String[] thirdOptions = {"Change your username",
                                                    "Change your password",
                                                    "Change your first name",
                                                    "Change your last name",
                                                    "Change your email",
                                                    "Edit your bio",
                                                    "Set your profile view",
                                                    "Set your message receive",
                                                    "Exit"};

                                            while (true) {
                                                String thirdChose = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "What do you want to do now?",
                                                        "Boiler Town",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        thirdOptions,
                                                        thirdOptions[0]);

                                                if (thirdChose == null) {
                                                    return;

                                                } else if (thirdChose.equals("Change your username")) {

                                                    while (true) {
                                                        String newUsername = JOptionPane.showInputDialog(
                                                                "Please enter your new username: ");

                                                        if (newUsername == null) {
                                                            return;

                                                        } else if (newUsername.isEmpty()) {
                                                            JOptionPane.showMessageDialog(
                                                                    null,
                                                                    "Error! Your enter is empty!");

                                                        } else {
                                                            clients.output.println(secondChose);
                                                            clients.output.println("Change your username");
                                                            clients.output.println(newUsername);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Change Your username successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Change Your username successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        }

                                                        String[] options = {"Yes", "No"};
                                                        int option = JOptionPane.showOptionDialog(
                                                                null,
                                                                "Do you still want to change your username?",
                                                                "Boiler Town",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE,
                                                                null,
                                                                options, options[1]);

                                                        if (option == JOptionPane.CLOSED_OPTION) {
                                                            return;
                                                        } else if (option == JOptionPane.NO_OPTION) {
                                                            break;
                                                        }

                                                    }

                                                } else if (thirdChose.equals("Change your password")) {

                                                    while (true) {
                                                        String newPassword = JOptionPane.showInputDialog("Please enter your new password: ");

                                                        if (newPassword == null) {
                                                            return;
                                                        } else if (newPassword.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                        } else {
                                                            clients.output.println(secondChose);
                                                            clients.output.println("Change your password");
                                                            clients.output.println(newPassword);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Change your password successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Change your password successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        }

                                                        String[] options = {"Yes", "No"};
                                                        int option = JOptionPane.showOptionDialog(
                                                                null,
                                                                "Do you still want to change your password?",
                                                                "Boiler Town",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE,
                                                                null,
                                                                options, options[1]);

                                                        if (option == JOptionPane.CLOSED_OPTION) {
                                                            return;
                                                        } else if (option == JOptionPane.NO_OPTION) {
                                                            break;
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Change your first name")) {

                                                    while (true) {
                                                        String newFirstName = JOptionPane.showInputDialog("Please enter your new first name: ");

                                                        if (newFirstName == null) {
                                                            return;

                                                        } else if (newFirstName.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");

                                                        } else {
                                                            clients.output.println(secondChose);
                                                            clients.output.println("Change your first name");
                                                            clients.output.println(newFirstName);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Change your first name successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Change your first name successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        }

                                                        String[] options = {"Yes", "No"};
                                                        int option = JOptionPane.showOptionDialog(
                                                                null,
                                                                "Do you still want to change your password?",
                                                                "Boiler Town",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE,
                                                                null,
                                                                options, options[1]);

                                                        if (option == JOptionPane.CLOSED_OPTION) {
                                                            return;
                                                        } else if (option == JOptionPane.NO_OPTION) {
                                                            break;
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Change your last name")) {

                                                    while (true) {
                                                        String newLastName = JOptionPane.showInputDialog("Please enter your new last name: ");

                                                        if (newLastName == null) {
                                                            return;
                                                        } else if (newLastName.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                        } else {
                                                            clients.output.println(secondChose);
                                                            clients.output.println("Change your last name");
                                                            clients.output.println(newLastName);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Change your last name successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Change your last name successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        }

                                                        String[] options = {"Yes", "No"};
                                                        int option = JOptionPane.showOptionDialog(
                                                                null,
                                                                "Do you still want to change your password?",
                                                                "Boiler Town",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE,
                                                                null,
                                                                options, options[1]);

                                                        if (option == JOptionPane.CLOSED_OPTION) {
                                                            return;
                                                        } else if (option == JOptionPane.NO_OPTION) {
                                                            break;
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Change your email")) {

                                                    while (true) {
                                                        String newEmail = JOptionPane.showInputDialog("Please enter your new email: ");

                                                        if (newEmail == null) {
                                                            return;

                                                        } else if (newEmail.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");

                                                        } else {
                                                            clients.output.println(secondChose);
                                                            clients.output.println("Change your email");
                                                            clients.output.println(newEmail);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Change your email successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Change your email successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        }

                                                        String[] options = {"Yes", "No"};
                                                        int option = JOptionPane.showOptionDialog(
                                                                null,
                                                                "Do you still want to change your password?",
                                                                "Boiler Town",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE,
                                                                null,
                                                                options, options[1]);

                                                        if (option == JOptionPane.CLOSED_OPTION) {
                                                            return;
                                                        } else if (option == JOptionPane.NO_OPTION) {
                                                            break;
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Edit your bio")) {

                                                    while (true) {
                                                        String newBio = JOptionPane.showInputDialog("Please enter your new bio: ");

                                                        if (newBio == null) {
                                                            return;
                                                        } else if (newBio.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                        } else {
                                                            clients.output.println(secondChose);
                                                            clients.output.println("Edit your bio");
                                                            clients.output.println(newBio);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Change your bio successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Change your bio successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        }

                                                        String[] options = {"Yes", "No"};
                                                        int option = JOptionPane.showOptionDialog(
                                                                null,
                                                                "Do you still want to change your password?",
                                                                "Boiler Town",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE,
                                                                null,
                                                                options, options[1]);

                                                        if (option == JOptionPane.CLOSED_OPTION) {
                                                            return;
                                                        } else if (option == JOptionPane.NO_OPTION) {
                                                            break;
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Set your profile view")) {

                                                    while (true) {
                                                        String newProfileView = JOptionPane.showInputDialog("Please enter your new profile view: ");

                                                        if (newProfileView == null) {
                                                            return;
                                                        } else if (newProfileView.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                        } else if (newProfileView.toLowerCase().equals("true") || newProfileView.toLowerCase().equals("false")){
                                                            clients.output.println(secondChose);
                                                            clients.output.println("Set your profile view");
                                                            clients.output.println(newProfileView);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Set your profile view successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Set your profile view successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "Error! Invalid enter!");
                                                        }

                                                        String[] options = {"Yes", "No"};
                                                        int option = JOptionPane.showOptionDialog(
                                                                null,
                                                                "Do you still want to change your password?",
                                                                "Boiler Town",
                                                                JOptionPane.YES_NO_OPTION,
                                                                JOptionPane.QUESTION_MESSAGE,
                                                                null,
                                                                options, options[1]);

                                                        if (option == JOptionPane.CLOSED_OPTION) {
                                                            return;
                                                        } else if (option == JOptionPane.NO_OPTION) {
                                                            break;
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Set your message receive")) {

                                                    while (true) {
                                                        String newMessageReceive = JOptionPane.showInputDialog("Please enter your new message receive: ");

                                                        if (newMessageReceive == null) {
                                                            return;
                                                        } else if (newMessageReceive.isEmpty()) {
                                                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                        } else if (newMessageReceive.toLowerCase().equals("true") || newMessageReceive.toLowerCase().equals("false")) {
                                                            clients.output.println("Set your message receive");
                                                            clients.output.println(newMessageReceive);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Set your message receive successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Set your message receive successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "Error! Invalid enter!");
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Exit")) {
                                                    break;
                                                }
                                            }
                                        } else if (secondChose.equals("View All users")) {
                                            clients.output.println("View All users");
                                            ArrayList<String> allUsers = new ArrayList<>();

                                            String usersCount = clients.input.readLine();
                                            int count = Integer.parseInt(usersCount);

                                            for (int i = 0; i < count; i++) {
                                                allUsers.add(clients.input.readLine());
                                            }

                                            String[] allUser = allUsers.toArray(new String[allUsers.size()]);

                                            while (true) {
                                                String userSelected = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "Who do you want to interact with?",
                                                        "Boiler Town",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        allUser, allUser[0]);

                                                if (userSelected == null) {
                                                    return;

                                                } else {
                                                    String[] thirdOptions = {"View profile",
                                                            "Make a friend request",
                                                            "Block a user",
                                                            "Send a message",
                                                            "View message history",
                                                            "Exit"};

                                                    while (true) {

                                                        String thirdChose = (String) JOptionPane.showInputDialog(
                                                                null,
                                                                "What do you want to do now?",
                                                                "Boiler Town",
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                thirdOptions,
                                                                thirdOptions[0]);

                                                        if (thirdChose == null) {
                                                            return;
                                                        } else if (thirdChose.equals("View profile")){
                                                            clients.output.println(thirdChose);
                                                            clients.output.println(userSelected);

                                                            String countFile = clients.input.readLine();
                                                            if (!countFile.equals("0")) {
                                                                int fileCount = Integer.parseInt(countFile);
                                                                ArrayList<String> fileList = new ArrayList<>();

                                                                for (int k = 0; k < fileCount; k++) {
                                                                    fileList.add(clients.input.readLine());
                                                                }

                                                                String file = "";

                                                                for (int l = 0; l < fileList.size(); l++) {
                                                                    if (l == fileList.size() - 1) {
                                                                        file += fileList.get(l);
                                                                    } else {
                                                                        file += fileList.get(l) + "\n";
                                                                    }
                                                                }

                                                                JOptionPane.showMessageDialog(null,
                                                                        file,
                                                                        "User profile",
                                                                        JOptionPane.INFORMATION_MESSAGE);

                                                            } else {
                                                                JOptionPane.showMessageDialog(
                                                                        null,
                                                                        "You are not allowed " +
                                                                                "to see this user's profile.");
                                                            }

                                                        } else if (thirdChose.equals("Make a friend request")) {
                                                            clients.output.println(thirdChose);
                                                            clients.output.println(userSelected);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Make a friend request successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Make a friend request successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        } else if (thirdChose.equals("Block a user")) {
                                                            clients.output.println(thirdChose);
                                                            clients.output.println(userSelected);
                                                            String result = clients.input.readLine();

                                                            if (result.equals("Block a user successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Block a user successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        } else if (thirdChose.equals("Send a message")) {
                                                            String content = JOptionPane.showInputDialog("Please enter what you want to send: ");

                                                            if (content == null) {
                                                                return;
                                                            } else if (content.isEmpty()) {
                                                                JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                            } else {
                                                                clients.output.println(thirdChose);
                                                                clients.output.println(userSelected);
                                                                clients.output.println(content);
                                                                String result = clients.input.readLine();

                                                                if (result.equals("Send a message successfully!")) {
                                                                    JOptionPane.showMessageDialog(null, "Send a message successfully!");
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, result);
                                                                }
                                                            }
                                                        } else if (thirdChose.equals("View message history")) {
                                                            clients.output.println(thirdChose);
                                                            clients.output.println(userSelected);

                                                            String messageCount = clients.input.readLine();
                                                            int messages = Integer.parseInt(messageCount);

                                                            String history = "";

                                                            for (int m = 0; m < messages; m++) {
                                                                if (m == messages - 1) {
                                                                    history += clients.input.readLine();
                                                                } else {
                                                                    history += clients.input.readLine() + "\n";
                                                                }
                                                            }

                                                            JOptionPane.showMessageDialog(null,
                                                                    history,
                                                                    "Message History",
                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                        } else if (thirdChose.equals("Exit")) {
                                                            break;
                                                        }
                                                    }
                                                }

                                                String[] options = {"Yes", "No"};
                                                int option = JOptionPane.showOptionDialog(
                                                        null,
                                                        "Do you still want to interact view all users?",
                                                        "Boiler Town",
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE,
                                                        null,
                                                        options, options[1]);

                                                if (option == JOptionPane.CLOSED_OPTION) {
                                                    return;
                                                } else if (option == JOptionPane.NO_OPTION) {
                                                    break;
                                                }
                                            }
                                        } else if (secondChose.equals("Search a user")) {
                                            String userSearched = JOptionPane.showInputDialog("Please enter the username you want to search: ");

                                            if (userSearched == null) {
                                                return;
                                            } else if (userSearched.isEmpty()) {
                                                JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                            } else {
                                                clients.output.println("Search a user");
                                                clients.output.println(userSearched);
                                                String result = clients.input.readLine();

                                                if (result.equals("User searched!")) {
                                                    JOptionPane.showMessageDialog(null, "User searched!");

                                                    while (true) {
                                                        String[] thirdOptions = {"View profile",
                                                                "Make a friend request",
                                                                "Block a user",
                                                                "Send a message",
                                                                "View message history",
                                                                "Exit"};

                                                        String thirdChose = (String) JOptionPane.showInputDialog(
                                                                null,
                                                                "What do you want to interact with this user?",
                                                                "Searched User",
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                thirdOptions,
                                                                thirdOptions[0]);

                                                        if (thirdChose == null) {
                                                            return;
                                                        } else {
                                                            if (thirdChose.equals("View profile")) {
                                                                clients.output.println(thirdChose);

                                                                String countFile = clients.input.readLine();
                                                                if (!countFile.equals("0")) {
                                                                    int fileCount = Integer.parseInt(countFile);
                                                                    ArrayList<String> fileList = new ArrayList<>();

                                                                    for (int k = 0; k < fileCount; k++) {
                                                                        fileList.add(clients.input.readLine());
                                                                    }

                                                                    String file = "";

                                                                    for (int l = 0; l < fileList.size(); l++) {
                                                                        if (l == fileList.size() - 1) {
                                                                            file += fileList.get(l);
                                                                        } else {
                                                                            file += fileList.get(l) + "\n";
                                                                        }
                                                                    }

                                                                    JOptionPane.showMessageDialog(null,
                                                                            file,
                                                                            "User profile",
                                                                            JOptionPane.INFORMATION_MESSAGE);
                                                                } else {
                                                                    JOptionPane.showMessageDialog(
                                                                            null,
                                                                            "You're not allowed " +
                                                                                    "to see this user's profile.");
                                                                }

                                                            } else if (thirdChose.equals("Make a friend request")) {
                                                                clients.output.println(thirdChose);
                                                                String resultMake = clients.input.readLine();

                                                                if (resultMake.equals("Make a friend request successfully!")) {
                                                                    JOptionPane.showMessageDialog(null, "Make a friend request successfully!");
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, result);
                                                                }
                                                            } else if (thirdChose.equals("Block a user")) {
                                                                clients.output.println(thirdChose);
                                                                String resultBlock = clients.input.readLine();

                                                                if (resultBlock.equals("Block a user successfully!")) {
                                                                    JOptionPane.showMessageDialog(null, "Block a user successfully!");
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, result);
                                                                }
                                                            } else if (thirdChose.equals("Send a message")) {
                                                                String content = JOptionPane.showInputDialog("Please enter what you want to send: ");

                                                                if (content == null) {
                                                                    return;
                                                                } else if (content.isEmpty()) {
                                                                    JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                                } else {
                                                                    clients.output.println(thirdChose);
                                                                    clients.output.println(content);
                                                                    String resultSend = clients.input.readLine();

                                                                    if (resultSend.equals("Send a message successfully!")) {
                                                                        JOptionPane.showMessageDialog(null, "Send a message successfully!");
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, result);
                                                                    }
                                                                }
                                                            } else if (thirdChose.equals("View message history")) {
                                                                clients.output.println(thirdChose);

                                                                String messageCount = clients.input.readLine();
                                                                int messages = Integer.parseInt(messageCount);

                                                                String history = "";

                                                                for (int m = 0; m < messages; m++) {
                                                                    if (m == messages - 1) {
                                                                        history += clients.input.readLine();
                                                                    } else {
                                                                        history += clients.input.readLine() + "\n";
                                                                    }
                                                                }

                                                                JOptionPane.showMessageDialog(null,
                                                                        history,
                                                                        "Message History",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                            } else if (thirdChose.equals("Exit")) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(null, result);
                                                }
                                            }
                                        } else if (secondChose.equals("View your profile")) {
                                            clients.output.println("View your profile");
                                            String fileCount = clients.input.readLine();
                                            int count = Integer.parseInt(fileCount);

                                            String profile = "";

                                            for (int i = 0; i < count; i++) {
                                                if (i == count - 1) {
                                                    profile += clients.input.readLine();
                                                } else {
                                                    profile += clients.input.readLine() + "\n";
                                                }
                                            }

                                            JOptionPane.showMessageDialog(null,
                                                    profile,
                                                    "Your profile",
                                                    JOptionPane.INFORMATION_MESSAGE);

                                        } else if (secondChose.equals("View your friend requests")) {
                                            clients.output.println("View your friend requests");
                                            String requestsCount = clients.input.readLine();
                                            int count = Integer.parseInt(requestsCount);

                                            String request = "";

                                            for (int i = 0; i < count; i++) {
                                                if (i == count - 1) {
                                                    request += clients.input.readLine();
                                                } else {
                                                    request += clients.input.readLine() + "\n";
                                                }
                                            }

                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    request,
                                                    "Friend Request",
                                                    JOptionPane.INFORMATION_MESSAGE);

                                            String[] thirdOptions = {"Add a friend", "Exit"};
                                            while (true) {
                                                String thirdChose = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "What do you want to do now?",
                                                        "Friend Request",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        thirdOptions,
                                                        thirdOptions[0]);

                                                if (thirdChose == null) {
                                                    return;
                                                } else if (thirdChose.equals("Add a friend")) {
                                                    clients.output.println(thirdChose);
                                                    String[] requestFriend = request.split("\n");

                                                    String addFriend = (String) JOptionPane.showInputDialog(
                                                            null,
                                                            "Please select who you want to add.",
                                                            "Friend Request",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            requestFriend,
                                                            requestFriend[0]);

                                                    if (addFriend == null) {
                                                        return;
                                                    } else {
                                                        clients.output.println(addFriend);

                                                        String result = clients.input.readLine();

                                                        if (result.equals("Add a friend successfully!")) {
                                                            JOptionPane.showMessageDialog(null, "Add a friend successfully!");
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, result);
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Exit")) {
                                                    break;
                                                }
                                            }
                                        } else if (secondChose.equals("View your friends")) {
                                            clients.output.println("View your friends");
                                            String friendsCount = clients.input.readLine();
                                            int count = Integer.parseInt(friendsCount);

                                            String friends = "";

                                            for (int i = 0; i < count; i++) {
                                                if (i == count - 1) {
                                                    friends += clients.input.readLine();
                                                } else {
                                                    friends += clients.input.readLine() + "\n";
                                                }
                                            }

                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    friends,
                                                    "Friends",
                                                    JOptionPane.INFORMATION_MESSAGE);

                                            String[] thirdOptions = {"Remove a friend", "Block a user", "Exit"};
                                            while (true) {
                                                String thirdChose = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "What do you want to do now",
                                                        "Friends",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        thirdOptions, thirdOptions[0]);

                                                String[] friend = friends.split("\n");

                                                if (thirdChose == null) {
                                                    return;
                                                } else if (thirdChose.equals("Remove a friend")){
                                                    clients.output.println(thirdChose);
                                                    String removedFriend = (String) JOptionPane.showInputDialog(
                                                            null,
                                                            "Please select who you want to remove",
                                                            "Friends",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            friend,
                                                            friend[0]);

                                                    if (removedFriend == null) {
                                                        return;
                                                    } else {
                                                        clients.output.println(removedFriend);
                                                        String result = clients.input.readLine();

                                                        if (result.equals("Remove a friend successfully!")) {
                                                            JOptionPane.showMessageDialog(null, "Remove a friend successfully!");
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, result);
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Block a user")) {
                                                    clients.output.println(thirdChose);

                                                    String blockedFriend = (String) JOptionPane.showInputDialog(
                                                            null,
                                                            "Please select who you want to block",
                                                            "Friends",
                                                            JOptionPane.PLAIN_MESSAGE,
                                                            null,
                                                            friend,
                                                            friend[0]);

                                                    if (blockedFriend == null) {
                                                        return;

                                                    } else {
                                                        clients.output.println(blockedFriend);
                                                        String result = clients.input.readLine();

                                                        if (result.equals("Block a user successfully!")) {
                                                            JOptionPane.showMessageDialog(null, "Block a user successfully!");
                                                        } else {
                                                            JOptionPane.showMessageDialog(null, result);
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Exit")) {
                                                    break;
                                                }
                                            }
                                        } else if (secondChose.equals("View your blocks")) {
                                            clients.output.println("View your blocks");
                                            String blocksCount = clients.input.readLine();
                                            int count = Integer.parseInt(blocksCount);

                                            String blocks = "";

                                            for (int i = 0; i < count; i++) {
                                                if (i == count - 1) {
                                                    blocks += clients.input.readLine();
                                                } else {
                                                    blocks += clients.input.readLine() + "\n";
                                                }
                                            }

                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    blocks,
                                                    "Blocks",
                                                    JOptionPane.INFORMATION_MESSAGE);

                                            String[] thirdOptions = {"Remove a blocked user", "Exit"};
                                            while (true) {
                                                String thirdChose = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "What do you want to do now?",
                                                        "Blocks",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null, thirdOptions, thirdOptions[0]);

                                                if (thirdChose == null) {
                                                    return;
                                                } else if (thirdChose.equals("Remove a blocked user")){
                                                    clients.output.println(thirdChose);
                                                    String result = clients.input.readLine();

                                                    JOptionPane.showMessageDialog(null, result);
                                                }
                                            }
                                        }

                                        String[] options = {"Yes", "No"};
                                        int option = JOptionPane.showOptionDialog(
                                                null,
                                                "Do you still want to stay at Boiler Town?",
                                                "Boiler Town",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                options, options[1]);

                                        if (option == JOptionPane.CLOSED_OPTION) {
                                            return;
                                        } else if (option == JOptionPane.NO_OPTION) {
                                            JOptionPane.showMessageDialog(null,
                                                    "Thank you for using Boiler Town!",
                                                    "Boiler Town",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                            break;
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Fail to log in.");
                                }
                            }

                            break;
                        }
                    }

                    String[] options = {"Yes", "No"};
                    int option = JOptionPane.showOptionDialog(
                            null,
                            "Do you still want to log in?",
                            "Boiler Town",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options, options[1]);

                    if (option == JOptionPane.CLOSED_OPTION) {
                        return;
                    } else if (option == JOptionPane.NO_OPTION) {
                        clients.output.println("break");
                        break;
                    } else {
                        clients.output.println("continue");
                    }
                }
            } else if (firstChose.equals("Sign up")) {

                String username = JOptionPane.showInputDialog("Please enter your username, it should be unique with no spacse");
                if (username == null) {
                    return;

                } else {
                    String password = JOptionPane.showInputDialog("Please enter your password, it should be grater than 4 characters.");

                    if (password == null) {
                        return;

                    } else {
                        String firstName = JOptionPane.showInputDialog("Please enter your first name, it should have no spaces");

                        if (firstName == null) {
                            return;
                        } else {
                            String lastName = JOptionPane.showInputDialog("Please enter your last name, it should have no spaces.");

                            if (lastName == null) {
                                return;
                            } else {
                                String email = JOptionPane.showInputDialog("Please enter your email of the form: ___@___.___ with no spaces.");

                                if (email == null) {
                                    return;
                                } else {
                                    String bio = JOptionPane.showInputDialog("Please enter your bio, it should be shorter than 50 characters.");

                                    if (bio == null) {
                                        return;
                                    } else {
                                        String profileView = JOptionPane.showInputDialog("Please enter \"true\" or \"false\" for your profile view.");

                                        if (profileView == null) {
                                            return;
                                        } else {
                                            String messageReceive = JOptionPane.showInputDialog("Please enter \"true\" or \"false\" for your message receive.");

                                            if (messageReceive == null) {
                                                return;
                                            } else {
                                                clients.output.println(firstChose);
                                                clients.output.println(username);
                                                clients.output.println(password);
                                                clients.output.println(firstName);
                                                clients.output.println(lastName);
                                                clients.output.println(email);
                                                clients.output.println(bio);
                                                clients.output.println(profileView);
                                                clients.output.println(messageReceive);

                                                String result = clients.input.readLine();

                                                if (result.equals("Sign up successfully!")) {
                                                    JOptionPane.showMessageDialog(null, "Sign up successfully!");
                                                } else {
                                                    JOptionPane.showMessageDialog(null, result);
                                                    JOptionPane.showMessageDialog(null, "You need to log in your account after you signed up.");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } else if (firstChose.equals("Exit")) {
                break;
            } else {
                break;
            }

        } while (true);

        clients.disconnect();
    }
}
