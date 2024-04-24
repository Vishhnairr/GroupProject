import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
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
        boolean breakCheck = true;

        JOptionPane.showMessageDialog(null, "Welcome to Boiler Town!",
                "Boiler Town", JOptionPane.INFORMATION_MESSAGE);

        do { // program begin
            String[] fistOptions = {"Log in", "Sign up", "Exit"};
            String firstChose = (String) JOptionPane.showInputDialog(null,
                    "What do you want to do today?", "Boiler Town",
                    JOptionPane.PLAIN_MESSAGE, null, fistOptions, fistOptions[0]);

            if (firstChose == null) {
                return;

            } else if (firstChose.equals("Log in")) { //Log in
                while (breakCheck) { //Log in begin - username
                    String username = JOptionPane.showInputDialog("Please enter your username: ");

                    if (username == null) {
                        return;

                    } else if (username.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");

                        String[] options = {"Yes", "No"};
                        int option = JOptionPane.showOptionDialog(
                                null,
                                "Do you still want to log in?",
                                "Boiler Town",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options, options[1]);

                        if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.NO_OPTION) {
                            return;
                        }

                    } else {
                        while (breakCheck) { //Log in password begin
                            String password = JOptionPane.showInputDialog("Please enter your password: ");

                            if (password == null) {
                                return;
                            } else if (password.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");

                                String[] options = {"Yes", "No"};
                                int option = JOptionPane.showOptionDialog(
                                        null,
                                        "Do you still want to log in?",
                                        "Boiler Town",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        options, options[1]);

                                if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.NO_OPTION) {
                                    return;
                                } else {
                                    break;
                                }
                            } else {
                                clients.output.println(firstChose); //pass first chose of log in (1)
                                clients.output.println(username); // pass username (2)
                                clients.output.println(password); // pass password (3)

                                String user = clients.input.readLine(); // receive user {1}

                                if (user.equals("null")) {
                                    JOptionPane.showMessageDialog(null,
                                            "Your username or password is wrong!");

                                    break;

                                } else { //Log in successfully
                                    String[] secondOptions = {"View All users",
                                            "Search a user",
                                            "View your profile",
                                            "View your friends",
                                            "View your blocks",
                                            "View your friend requests",
                                            "Exit"};

                                    while (breakCheck) { //Interactions after Log In
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
                                        } else if (secondChose.equals("View All users")) {
                                            clients.output.println(secondChose); //pass secondChose (4)
                                            ArrayList<String> allUsers = new ArrayList<>();
                                            allUsers.add("Click on this if you want to exit");

                                            String usersCount = clients.input.readLine(); //receive length of users {2}
                                            int count = Integer.parseInt(usersCount);

                                            for (int i = 0; i < count; i++) {
                                                allUsers.add(clients.input.readLine()); // receive users {3}
                                            }

                                            String[] allUser = allUsers.toArray(new String[allUsers.size()]);

                                            while (true) { //interaction in All users
                                                String userSelected = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "Who do you want to interact with?",
                                                        "Boiler Town",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        allUser, allUser[0]);

                                                if (userSelected == null) {
                                                    return;
                                                } else if (userSelected.equals("Click on this if you want to exit")) {
                                                    clients.output.println(userSelected);//pass userSelected (5)
                                                    break;

                                                } else {
                                                    clients.output.println(userSelected);//pass userSelected (5)
                                                    String[] thirdOptions = {"View profile",
                                                            "Make a friend request",
                                                            "Block a user",
                                                            "Send a message",
                                                            "View message history",
                                                            "Exit"};

                                                    while (true) { // interactions if chose a specific user in all users
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
                                                        } else if (thirdChose.equals("View profile")) {
                                                            clients.output.println(thirdChose); // pass thirdChose (6)
                                                            String countFile = clients.input.readLine(); //receive length of file {4}

                                                            if (!countFile.equals("0")) {
                                                                int fileCount = Integer.parseInt(countFile);
                                                                ArrayList<String> fileList = new ArrayList<>();

                                                                for (int k = 0; k < fileCount; k++) {
                                                                    fileList.add(clients.input.readLine()); //receive file {5}
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

                                                            } else { //if no file is passed
                                                                JOptionPane.showMessageDialog(
                                                                        null,
                                                                        "You are not allowed " +
                                                                                "to see this user's profile.");
                                                            }
                                                        } else if (thirdChose.equals("Make a friend request")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String result = clients.input.readLine(); //receive result of making a friend request {4}

                                                            if (result.equals("Make a friend request successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Make a friend request successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }

                                                        } else if (thirdChose.equals("Block a user")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String result = clients.input.readLine(); //receive result of blocking a user {4}

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
                                                                clients.output.println(thirdChose); //pass thirdChose (6)
                                                                clients.output.println(content); //pass the content of message (7)
                                                                String result = clients.input.readLine(); //receive the result of sending message {4}

                                                                if (result.equals("Send a message successfully!")) {
                                                                    JOptionPane.showMessageDialog(null, "Send a message successfully!");
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, result);
                                                                }
                                                            }
                                                        } else if (thirdChose.equals("View message history")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)

                                                            String messageCount = clients.input.readLine(); //receive the count of messages {4}
                                                            int messages = Integer.parseInt(messageCount);

                                                            String history = "";

                                                            for (int m = 0; m < messages; m++) {
                                                                if (m == messages - 1) {
                                                                    history += clients.input.readLine(); //receive messages {5}
                                                                } else {
                                                                    history += clients.input.readLine() + "\n"; //receive messages {5}
                                                                }
                                                            }

                                                            JOptionPane.showMessageDialog(null,
                                                                    history,
                                                                    "Message History",
                                                                    JOptionPane.INFORMATION_MESSAGE);

                                                        } else {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            System.out.println(thirdChose);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (secondChose.equals("Search a user")) {
                                            String userSearched = JOptionPane.showInputDialog("Please enter the username you want to search: ");

                                            if (userSearched == null) {
                                                return;
                                            } else if (userSearched.isEmpty()) {
                                                JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                            } else {
                                                clients.output.println(secondChose); //pass secondChose (4)
                                                clients.output.println(userSearched); //pass userSearched (5)
                                                String result = clients.input.readLine(); //receive result of searching a user {2}

                                                if (!result.equals("User searched!")) {
                                                    JOptionPane.showMessageDialog(null, result);
                                                } else {
                                                    String[] thirdOptions = {"View profile",
                                                            "Make a friend request",
                                                            "Block a user",
                                                            "Send a message",
                                                            "View message history",
                                                            "Exit"};
                                                    while (true) { //Interactions in search user
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
                                                        } else {
                                                            if (thirdChose.equals("View profile")) {
                                                                clients.output.println(thirdChose); // pass thirdChose (6)

                                                                String countFile = clients.input.readLine(); //receive length of file {3}

                                                                if (!countFile.equals("0")) {
                                                                    int fileCount = Integer.parseInt(countFile);
                                                                    ArrayList<String> fileList = new ArrayList<>();

                                                                    for (int k = 0; k < fileCount; k++) {
                                                                        fileList.add(clients.input.readLine()); //receive file {4}
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

                                                                } else { //if no file is passed
                                                                    JOptionPane.showMessageDialog(
                                                                            null,
                                                                            "You are not allowed " +
                                                                                    "to see this user's profile.");
                                                                }
                                                            } else if (thirdChose.equals("Make a friend request")) {
                                                                clients.output.println(thirdChose); //pass thirdChose (6)
                                                                String resultRequest = clients.input.readLine(); //receive result of making a friend request {3}

                                                                if (resultRequest.equals("Make a friend request successfully!")) {
                                                                    JOptionPane.showMessageDialog(null, "Make a friend request successfully!");
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, resultRequest);
                                                                }
                                                            } else if (thirdChose.equals("Block a user")) {
                                                                clients.output.println(thirdChose); //pass thirdChose (6)
                                                                String resultBlock = clients.input.readLine(); //receive result of blocking a user {3}

                                                                if (resultBlock.equals("Block a user successfully!")) {
                                                                    JOptionPane.showMessageDialog(null, "Block a user successfully!");
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, resultBlock);
                                                                }
                                                            } else if (thirdChose.equals("Send a message")) {
                                                                String content = JOptionPane.showInputDialog("Please enter what you want to send: ");

                                                                if (content == null) {
                                                                    return;
                                                                } else if (content.isEmpty()) {
                                                                    JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                                } else {
                                                                    clients.output.println(thirdChose); //pass thirdChose (6)
                                                                    clients.output.println(content); //pass the content of message (7)
                                                                    String resultSend = clients.input.readLine(); //receive the result of sending message {3}

                                                                    if (resultSend.equals("Send a message successfully!")) {
                                                                        JOptionPane.showMessageDialog(null, "Send a message successfully!");
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(null, resultSend);
                                                                    }
                                                                }
                                                            } else if (thirdChose.equals("View message history")) {
                                                                clients.output.println(thirdChose); //pass thirdChose (6)

                                                                String messageCount = clients.input.readLine(); //receive the count of messages {3}
                                                                int messages = Integer.parseInt(messageCount);

                                                                String history = "";

                                                                for (int m = 0; m < messages; m++) {
                                                                    if (m == messages - 1) {
                                                                        history += clients.input.readLine(); //receive messages {4}
                                                                    } else {
                                                                        history += clients.input.readLine() + "\n"; //receive messages {4}
                                                                    }
                                                                }

                                                                JOptionPane.showMessageDialog(null,
                                                                        history,
                                                                        "Message History",
                                                                        JOptionPane.INFORMATION_MESSAGE);
                                                            } else {
                                                                clients.output.println(thirdChose); //pass thirdChose (6)
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (secondChose.equals("View your profile")) {
                                            clients.output.println(secondChose); //pass secondChose (4)
                                            String fileCount = clients.input.readLine(); //receive length of file {2}
                                            int count = Integer.parseInt(fileCount);

                                            String profile = "";

                                            for (int i = 0; i < count; i++) {
                                                if (i == count - 1) {
                                                    profile += clients.input.readLine(); //receive file {3}
                                                } else {
                                                    profile += clients.input.readLine() + "\n"; //receive file {3}
                                                }
                                            }

                                            JOptionPane.showMessageDialog(null,
                                                    profile,
                                                    "Your profile",
                                                    JOptionPane.INFORMATION_MESSAGE);

                                        } else if (secondChose.equals("View your friends")) {
                                            clients.output.println(secondChose); //pass secondChose (4)
                                            String friendsCount = clients.input.readLine(); //receive length of friends {2}
                                            int count = Integer.parseInt(friendsCount);

                                            ArrayList<String> friends = new ArrayList<>();
                                            friends.add("Click on this if you want to exit");

                                            for (int i = 0; i < count; i++) {
                                                friends.add(clients.input.readLine()); //receive friends {3}
                                            }

                                            String[] friend = friends.toArray(new String[friends.size()]);

                                            while (true) { //Interactions in View your Friends
                                                String pickedFriend = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "Who do you want to interact with?",
                                                        "Friends",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        friend, friend[0]);

                                                if (pickedFriend == null) {
                                                    return;
                                                } else if (pickedFriend.equals("Click on this if you want to exit")) {
                                                    clients.output.println(pickedFriend); //pass pickedFriend (5)
                                                    break;
                                                } else {
                                                    clients.output.println(pickedFriend); //pass pickedFriend (5)

                                                    String[] thirdOptions = {"View Friend's Profile",
                                                            "View Message History",
                                                            "Send a Message",
                                                            "Remove a Friend",
                                                            "Block a Friend",
                                                            "Exit"};

                                                    while (true) { //Interactions after chose a specific friend
                                                        String thirdChose = (String) JOptionPane.showInputDialog(
                                                                null,
                                                                "What do you want to do now?",
                                                                "Friends",
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                thirdOptions,
                                                                thirdOptions[0]);

                                                        if (thirdChose == null) {
                                                            return;
                                                        } else if (thirdChose.equals("View Friend's Profile")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String friendFile = clients.input.readLine(); //receive length of file {4}

                                                            if (!friendFile.equals("0")) {
                                                                int fileCount = Integer.parseInt(friendFile);
                                                                ArrayList<String> fileList = new ArrayList<>();

                                                                for (int k = 0; k < fileCount; k++) {
                                                                    fileList.add(clients.input.readLine()); //receive file {5}
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
                                                                        "Friend profile",
                                                                        JOptionPane.INFORMATION_MESSAGE);

                                                            } else { //if no file is passed
                                                                JOptionPane.showMessageDialog(
                                                                        null,
                                                                        "You are not allowed " +
                                                                                "to see this user's profile.");
                                                            }
                                                        } else if (thirdChose.equals("View Message History")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)

                                                            String messageCount = clients.input.readLine(); //receive the count of messages {4}
                                                            int messages = Integer.parseInt(messageCount);

                                                            String history = "";

                                                            for (int m = 0; m < messages; m++) {
                                                                if (m == messages - 1) {
                                                                    history += clients.input.readLine(); //receive messages {5}
                                                                } else {
                                                                    history += clients.input.readLine() + "\n"; //receive messages {5}
                                                                }
                                                            }

                                                            JOptionPane.showMessageDialog(null,
                                                                    history,
                                                                    "Message History",
                                                                    JOptionPane.INFORMATION_MESSAGE);
                                                        } else if (thirdChose.equals("Send a Message")) {
                                                            String content = JOptionPane.showInputDialog("Please enter what you want to send: ");

                                                            if (content == null) {
                                                                return;
                                                            } else if (content.isEmpty()) {
                                                                JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                                                            } else {
                                                                clients.output.println(thirdChose); //pass thirdChose (6)
                                                                clients.output.println(content); //pass the content of message (7)
                                                                String resultSend = clients.input.readLine(); //receive the result of sending message {4}

                                                                if (resultSend.equals("Send a message successfully!")) {
                                                                    JOptionPane.showMessageDialog(null, "Send a message successfully!");
                                                                } else {
                                                                    JOptionPane.showMessageDialog(null, resultSend);
                                                                }
                                                            }
                                                        } else if (thirdChose.equals("Remove a Friend")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String result = clients.input.readLine(); //receive result of removing a friend {4}

                                                            if (result.equals("Remove a friend successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Remove a friend successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        } else if (thirdChose.equals("Block a Friend")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String result = clients.input.readLine(); //receive result of removing a friend {4}

                                                            if (result.equals("Block a friend successfully!")) {
                                                                JOptionPane.showMessageDialog(null, "Block a friend successfully!");
                                                            } else {
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                        } else {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            break;
                                                        }
                                                    }

                                                }
                                            }
                                        } else if (secondChose.equals("View your blocks")) {
                                            clients.output.println(secondChose); //pass secondChose (4)
                                            String blocksCount = clients.input.readLine(); //receive the length of blocks {2}
                                            int count = Integer.parseInt(blocksCount);

                                            ArrayList<String> blocks = new ArrayList<>();
                                            blocks.add("Click on this if you want to exit");

                                            for (int i = 0; i < count; i++) {
                                                blocks.add(clients.input.readLine()); //receive blocks {3}
                                            }

                                            String[] block = blocks.toArray(new String[blocks.size()]);

                                            while (true) { //Interactions in View your blocks
                                                String pickedBlock = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "Who do you want to interact with?",
                                                        "Blocks",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        block, block[0]);

                                                if (pickedBlock == null) {
                                                    return;
                                                } else if (pickedBlock.equals("Click on this if you want to exit")) {
                                                    clients.output.println(pickedBlock); //pass pickedBlock (5)
                                                    break;
                                                } else {
                                                    clients.output.println(pickedBlock); //pass pickedBlock (5)

                                                    String[] thirdOptions = {"Remove Block", "Exit"};

                                                    while (true) { //Interactions in view your blocks after chose a specific block
                                                        String thirdChose = (String) JOptionPane.showInputDialog(
                                                                null,
                                                                "What do you want to do now?",
                                                                "Blocks",
                                                                JOptionPane.PLAIN_MESSAGE,
                                                                null,
                                                                thirdOptions,
                                                                thirdOptions[0]);

                                                        if (thirdChose == null) {
                                                            return;
                                                        } else if (thirdChose.equals("Remove Block")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String result = clients.input.readLine(); // receive the result of removing block {4}

                                                            JOptionPane.showMessageDialog(null, result);
                                                        } else {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (secondChose.equals("View your friend requests")) {
                                            clients.output.println(secondChose); //pass secondChose (4)
                                            String requestsCount = clients.input.readLine(); //receive the length of requests {2}
                                            int count = Integer.parseInt(requestsCount);

                                            ArrayList<String> requests = new ArrayList<>();
                                            requests.add("Click on this if you want to exit");

                                            for (int i = 0; i < count; i++) {
                                                requests.add(clients.input.readLine()); //receive requests {3}
                                            }

                                            String[] request = requests.toArray(new String[requests.size()]);

                                            while (true) { //Interactions in friend request
                                                String pickedRequest = (String) JOptionPane.showInputDialog(
                                                        null,
                                                        "Which request do you want to interact?",
                                                        "Friend Request",
                                                        JOptionPane.PLAIN_MESSAGE,
                                                        null,
                                                        request, request[0]);

                                                if (pickedRequest == null) {
                                                    return;
                                                } else if (pickedRequest.equals("Click on this if you want to exit")) {
                                                    clients.output.println(pickedRequest); //pass pickedRequest (5)
                                                    break;
                                                } else {
                                                    clients.output.println(pickedRequest); //pass pickedRequest (5)
                                                    String[] thirdOptions = {"Accept request", "Reject request", "Exit"};

                                                    while (true) { //Interactions in view friend request after chose a specific request
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
                                                        } else if (thirdChose.equals("Accept request")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String result = clients.input.readLine(); //receive the result of accepting request {4}

                                                            JOptionPane.showMessageDialog(null, result);

                                                        } else if (thirdChose.equals("Reject request")) {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            String result = clients.input.readLine(); //receive the result of accepting request {4}

                                                            JOptionPane.showMessageDialog(null, result);
                                                        } else {
                                                            clients.output.println(thirdChose); //pass thirdChose (6)
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            clients.output.println(secondChose); //pass secondChose (4)
                                            break;
                                        }
                                    }
                                }
                            }
                            break;
                        }
                        break;//Log in password end
                    }
                    break;
                }
                break;//Log in username end
            } else if (firstChose.equals("Sign up")) { //Sign up

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
                                                    JOptionPane.showMessageDialog(null, "You need to log in your account after you signed up.");
                                                } else {
                                                    JOptionPane.showMessageDialog(null, result);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } else { //Other than Log in or Sign Up, directly just end the program
                break;
            }

        } while (true);

        clients.disconnect(); //program end
    }
}
