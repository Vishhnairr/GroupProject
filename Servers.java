
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Servers
 *
 * This class allow multiple users to connect. And creat a nested class called clientHandle to handle every client.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 26, 2024
 *
 */

public class Servers implements ServerList {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients = new ArrayList<>();

    public Servers(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        System.out.println("Server start!");

        while (true) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("New client: " + client);

                ClientHandler clientHandler = new ClientHandler(client);
                clients.add(clientHandler);
                clientHandler.start();

            } catch (Exception e) {
                return;
            }
        }
    }


    public static void main(String[] args) {
        try {
            Servers servers = new Servers(8787);
            servers.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ClientHandler
     *
     * This class handle one client and allow server to deal with many clients
     *
     * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
     *
     * @version April 26, 2024
     *
     */
    class ClientHandler extends Thread implements Runnable, ClientHandlerList {
        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.input = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                this.output = new PrintWriter(
                        clientSocket.getOutputStream(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                boolean breakCheck = true;
                do {
                    String firstChose = input.readLine(); //receive {1}
                    System.out.println(firstChose);

                    if (firstChose.equals("Log in")) {

                        while (true) { //Log In
                            String username = input.readLine();  //receive {2}
                            System.out.println(username);
                            String password = input.readLine();  //receive {3}
                            System.out.println(password);
                            User user = new User(username, password).logIn();

                            if (user == null) {
                                output.println("null"); //pass user (1)
                            } else {
                                output.println("Log in Successfully!"); // pass user (1)
                                System.out.println("Log in Successfully!");

                                while (true) { //Log In Successfully
                                    String secondChose = input.readLine();  //receive {4}
                                    System.out.println(secondChose);

                                    if (secondChose.equals("View All users")) {
                                        while (true) {
                                            String[] allUsersArray = user.viewAllUsers();
                                            String usersCount = String.valueOf(allUsersArray.length - 1);
                                            output.println(usersCount); // pass length of users (2)
                                            System.out.println(usersCount);
                                            for (int i = 0; i < allUsersArray.length; i++) {
                                                if (allUsersArray[i].equals(username)) continue;
                                                output.println(allUsersArray[i]); //pass users (3)
                                                System.out.println(username);
                                                System.out.println(allUsersArray[i]);
                                            }
                                            String userSelected = input.readLine(); //receive userSelected {5}

                                            if (!userSelected.equals("Click on this if you want to exit")) {
                                                while (true) { //Interactions in All User after select a user
                                                    String thirdChose = input.readLine(); //receive thirdChose {6}

                                                    if (thirdChose.equals("View profile")) {
                                                        Friends friends = new Friends(user, userSelected);
                                                        String userSelectedFile = friends.viewProfile();

                                                        if (userSelectedFile == null) {
                                                            output.println("0"); // pass length of profile (4)
                                                        } else {
                                                            String[] fileSplits = userSelectedFile.split("\n");

                                                            int fileCount = fileSplits.length;
                                                            String count = String.valueOf(fileCount);
                                                            output.println(count); // pass length of profile (4)

                                                            for (int j = 0; j < fileCount; j++) {
                                                                output.println(fileSplits[j]); //pass file (5)
                                                            }
                                                        }
                                                    } else if (thirdChose.equals("Make a friend request")) {
                                                        Friends friends = new Friends(user, userSelected);

                                                        if (!friends.makeFriendRequest()) {
                                                            output.println("Fail to make a friend. " +
                                                                    "This user might " +
                                                                    "already be your friend or blocked.");
                                                            //pass result of making a friend request (4)
                                                        } else {
                                                            output.println("Make a friend request successfully!");
                                                            //pass result of making a friend request (4)
                                                        }

                                                    } else if (thirdChose.equals("Block a user")) {
                                                        Friends friends = new Friends(user, userSelected);

                                                        if (!friends.blockUser()) {
                                                            output.println("Fail to block a user. This user might " +
                                                                    "already be blocked.");
                                                            //pass result of blocking a user (4)
                                                        } else {
                                                            output.println("Block a user successfully!");
                                                            //pass result of blocking a user (4)
                                                        }

                                                    } else if (thirdChose.equals("Send a message")) {
                                                        String content = input.readLine(); //receive content {7}
                                                        MessageList messageList = new Message(
                                                                content, user, userSelected);

                                                        if (!messageList.sendMessage()) {
                                                            output.println("Fail to send a " +
                                                                    "message. May be is because " +
                                                                    "you are not this user's friend.");
                                                            //pass result of sending a message (4)
                                                        } else {
                                                            output.println("Send a message successfully!");
                                                            //pass result of sending a message (4)
                                                        }

                                                    } else if (thirdChose.equals("View message history")) {
                                                        String content = "content";
                                                        MessageList messageList = new Message(
                                                                content, user, userSelected);

                                                        ArrayList<String> messages = messageList.viewMessageHistory();
                                                        int messageCount = messages.size();
                                                        String count = String.valueOf(messageCount);
                                                        output.println(count); //pass the length of messages (4)

                                                        for (int m = 0; m < messageCount; m++) {
                                                            output.println(messages.get(m)); //pass messages (5)
                                                        }
                                                        String result = input.readLine();
                                                        if (result.equals("History window was closed")) {
                                                            continue;
                                                        } else {
                                                            // Assuming the client sends back the number of
                                                            // messages followed by each message
                                                            int newMessageCount = Integer.parseInt(input.readLine());
                                                            ArrayList<String> newMessages = new ArrayList<>();

                                                            for (int i = 0; i < newMessageCount; i++) {
                                                                newMessages.add(input.readLine());
                                                            }

                                                            // Rewrite these messages to the history files
                                                            if (messageList.rewriteMessageHistory(newMessages)) {
                                                                output.println("Updated successfully");
                                                            } else {
                                                                output.println("Update failed");
                                                            }
                                                        }
                                                    } else {
                                                        break;
                                                    }
                                                }
                                            } else {
                                                break;
                                            }
                                        }

                                    } else if (secondChose.equals("Search a user")) {
                                        String searchedUser = input.readLine(); //receive the searchedUser {5}
                                        if (searchedUser.equals(username) || !user.searchUser(searchedUser)) {
                                            output.println("No user searched. Please " +
                                                    "check the username is valid or not.");
                                            //pass the result of searching a user (2)
                                        } else {
                                            output.println("User searched!"); //pass the result of searching a user (2)

                                            while (true) { //Interactions in search user
                                                String thirdChose = input.readLine(); //receive thirdChose {6}
                                                if (thirdChose.equals("View profile")) {
                                                    Friends friends = new Friends(user, searchedUser);
                                                    String userSelectedFile = friends.viewProfile();

                                                    if (userSelectedFile == null) {
                                                        output.println("0"); // pass length of profile (3)
                                                    } else {
                                                        String[] fileSplits = userSelectedFile.split("\n");

                                                        int fileCount = fileSplits.length;
                                                        String count = String.valueOf(fileCount);
                                                        output.println(count); // pass length of profile (3)

                                                        for (int j = 0; j < fileCount; j++) {
                                                            output.println(fileSplits[j]); //pass file (4)
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Make a friend request")) {
                                                    Friends friends = new Friends(user, searchedUser);

                                                    if (!friends.makeFriendRequest()) {
                                                        output.println("Fail to make a friend. This user might " +
                                                                "already be your friend or blocked.");
                                                        //pass result of making a friend request (3)
                                                    } else {
                                                        output.println("Make a friend request successfully!");
                                                        //pass result of making a friend request (3)
                                                    }
                                                } else if (thirdChose.equals("Block a user")) {
                                                    Friends friends = new Friends(user, searchedUser);

                                                    if (!friends.blockUser()) {
                                                        output.println("Fail to block a user. " +
                                                                "This user might already be blocked.");
                                                        //pass result of blocking a user (3)
                                                    } else {
                                                        output.println("Block a user successfully!");
                                                        //pass result of blocking a user (3)
                                                    }
                                                } else if (thirdChose.equals("Send a message")) {
                                                    String content = input.readLine(); //receive content {7}
                                                    MessageList messageList = new Message(content, user, searchedUser);

                                                    if (!messageList.sendMessage()) {
                                                        output.println("Fail to send a message. " +
                                                                "May be is because you are not this user's friend.");
                                                        //pass result of sending a message (3)
                                                    } else {
                                                        output.println("Send a message successfully!");
                                                        //pass result of sending a message (3)
                                                    }
                                                } else if (thirdChose.equals("View message history")) {
                                                    String content = "content";
                                                    MessageList messageList = new Message(content, user, searchedUser);

                                                    ArrayList<String> messages = messageList.viewMessageHistory();
                                                    int messageCount = messages.size();
                                                    String count = String.valueOf(messageCount);
                                                    output.println(count); //pass the length of messages (4)

                                                    for (int m = 0; m < messageCount; m++) {
                                                        output.println(messages.get(m)); //pass messages (5)
                                                    }
                                                    String result = input.readLine();
                                                    if (result.equals("History window was closed")) {
                                                        continue;
                                                    } else {
                                                        // Assuming the client sends back the number of messages
                                                        // followed by each message
                                                        int newMessageCount = Integer.parseInt(input.readLine());
                                                        ArrayList<String> newMessages = new ArrayList<>();

                                                        for (int i = 0; i < newMessageCount; i++) {
                                                            newMessages.add(input.readLine());
                                                        }

                                                        // Rewrite these messages to the history files
                                                        if (messageList.rewriteMessageHistory(newMessages)) {
                                                            output.println("Updated successfully");
                                                        } else {
                                                            output.println("Update failed");
                                                        }
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    } else if (secondChose.equals("View your profile")) {
                                        String files = user.viewFile();
                                        String[] fileSplits = files.split("\n");
                                        int fileCount = fileSplits.length;
                                        String count = String.valueOf(fileCount);
                                        output.println(count); //pass the length of file (2)

                                        for (int i = 0; i < fileCount; i++) {
                                            output.println(fileSplits[i]); //pass file (3)
                                        }
                                        String message = input.readLine();
                                        if (message.equals("profile_saved_and_closed")) {
                                            List<String> profileData = new ArrayList<>();
                                            for (int i = 0; i < fileCount; i++) {
                                                profileData.add(input.readLine());
                                            }
                                            // Handle saving the profile data
                                            user.updateProfile(profileData.toArray(new String[0]));
                                        } else if (message.equals("profile_edit_cancelled")) {
                                            // Handle profile editing cancellation
                                        }
                                    } else if (secondChose.equals("View your friends")) {
                                        while (true) {
                                            String[] friends = user.viewFriends();
                                            int friendsCount = friends.length;
                                            String count = String.valueOf(friendsCount);
                                            output.println(count); //pass the length of friends (2)

                                            for (int i = 0; i < friendsCount; i++) {
                                                output.println(friends[i]); //pass friends (3)
                                            }
                                            String pickedFriend = input.readLine(); //receive pickedFriend {5}
                                            System.out.println(pickedFriend);

                                            if (pickedFriend.equals("Click on this if you want to exit")) {
                                                break;
                                            } else {
                                                String thirdChose = input.readLine(); //receive thirdChose {6}
                                                System.out.println(thirdChose);

                                                if (thirdChose.equals("View Friend's Profile")) {
                                                    Friends friendsTrue = new Friends(user, pickedFriend);
                                                    String friendFile = friendsTrue.viewProfile();

                                                    if (friendFile == null) {
                                                        output.println("0"); // pass length of profile (4)
                                                    } else {
                                                        String[] fileSplits = friendFile.split("\n");

                                                        int fileCount = fileSplits.length;
                                                        String countFriend = String.valueOf(fileCount);
                                                        output.println(countFriend); // pass length of profile (4)

                                                        for (int j = 0; j < fileCount; j++) {
                                                            output.println(fileSplits[j]); //pass file (5)
                                                        }
                                                    }
                                                } else if (thirdChose.equals("View Message History")) {
                                                    String content = "content";
                                                    MessageList messageList = new Message(content, user, pickedFriend);

                                                    ArrayList<String> messages = messageList.viewMessageHistory();
                                                    int messageCount = messages.size();
                                                    String count1 = String.valueOf(messageCount);
                                                    output.println(count1); //pass the length of messages (4)

                                                    for (int m = 0; m < messageCount; m++) {
                                                        output.println(messages.get(m)); //pass messages (5)
                                                    }
                                                    String result = input.readLine();
                                                    if (result.equals("History window was closed")) {
                                                        continue;
                                                    } else {
                                                        // Assuming the client sends back the number of messages
                                                        // followed by each message
                                                        int newMessageCount = Integer.parseInt(input.readLine());
                                                        ArrayList<String> newMessages = new ArrayList<>();

                                                        for (int i = 0; i < newMessageCount; i++) {
                                                            newMessages.add(input.readLine());
                                                        }

                                                        // Rewrite these messages to the history files
                                                        if (messageList.rewriteMessageHistory(newMessages)) {
                                                            output.println("Updated successfully");
                                                        } else {
                                                            output.println("Update failed");
                                                        }
                                                    }
                                                } else if (thirdChose.equals("Send a Message")) {
                                                    String content = input.readLine(); //receive content {7}
                                                    MessageList messageList = new Message(content, user, pickedFriend);

                                                    if (!messageList.sendMessage()) {
                                                        output.println("Fail to send a message. " +
                                                                "May be is because you are not this user's friend.");
                                                        //pass result of sending a message (4)
                                                    } else {
                                                        output.println("Send a message successfully!");
                                                        //pass result of sending a message (4)
                                                    }
                                                } else if (thirdChose.equals("Remove a Friend")) {
                                                    Friends friend = new Friends(user, pickedFriend);

                                                    if (!friend.removeFriend()) {
                                                        output.println("Fail to remove.");
                                                        //pass the result of removing a friend (4)
                                                    } else {
                                                        output.println("Remove a friend successfully!");
                                                        //pass the result of removing a friend (4)
                                                    }
                                                } else if (thirdChose.equals("Block a Friend")) {
                                                    Friends friend = new Friends(user, pickedFriend);

                                                    if (!friend.blockUser()) {
                                                        output.println("Fail to block.");
                                                        //pass the result of blocking a friend (4)
                                                    } else {
                                                        output.println("Block a friend successfully!");
                                                        //pass the result of blocking a friend (4)
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    } else if (secondChose.equals("View your blocks")) {
                                        while (true) {
                                            String[] blocks = user.viewBlocks();
                                            int blocksCount = blocks.length;
                                            String count = String.valueOf(blocksCount);
                                            output.println(count); //pass the length of blocks (2)

                                            for (int i = 0; i < blocksCount; i++) {
                                                output.println(blocks[i]); //pass blocks (3)
                                            }
                                            String pickedBlock = input.readLine(); //receive pickedBlock {5}

                                            if (pickedBlock.equals("Click on this if you want to exit")) {
                                                break;
                                            } else {
                                                String thirdChose = input.readLine(); //receive thirdChose {6}

                                                if (thirdChose.equals("Remove Block")) {
                                                    Friends friend = new Friends(user, pickedBlock);

                                                    if (!friend.removeBlock()) {
                                                        output.println("Fail to remove.");
                                                        //pass the result of removing block (4)
                                                    } else {
                                                        output.println("Remove a blocked successfully!");
                                                        //pass the result of removing block (4)
                                                    }
                                                } else {
                                                    break;
                                                }
                                            }
                                        }
                                    } else if (secondChose.equals("View your friend requests")) {
                                        while (true) {
                                            String[] requests = user.viewFriendsRequest();
                                            int requestsCount = requests.length;
                                            String count = String.valueOf(requestsCount);
                                            output.println(count); //pass the length of requests (2)

                                            for (int i = 0; i < requestsCount; i++) {
                                                output.println(requests[i]); //pass requests (3)
                                            }
                                            String pickedRequest = input.readLine(); //receive pickedRequest {5}

                                            if (pickedRequest.equals("Click on this if you want to exit")) {
                                                break;
                                            } else {
                                                String thirdChose = input.readLine(); //receive thirdChose {6}

                                                if (thirdChose.equals("Accept request")) {
                                                    Friends friends = new Friends(user, pickedRequest);

                                                    if (!friends.addFriend()) {
                                                        output.println("Fail to add a friend");
                                                        //pass the result of accepting request (4)
                                                    } else {
                                                        output.println("Add a friend successfully!");
                                                        //pass the result of accepting request (4)
                                                    }
                                                } else if (thirdChose.equals("Reject request")) {
                                                    Friends friends = new Friends(user, pickedRequest);

                                                    if (!friends.rejectFriendRequest()) {
                                                        output.println("Fail to reject");
                                                        //pass the result of rejecting request (4)
                                                    } else {
                                                        output.println("Reject " +
                                                                "a friend request successfully!");
                                                        //pass the result of rejecting request (4)
                                                    }
                                                }
                                            }
                                        }

                                    } else {
                                        break;
                                    }
                                }
                            }
                            break;
                        }

                    } else if (firstChose.equals("Sign up")) {
                        String username = input.readLine();
                        String password = input.readLine();
                        String firstName = input.readLine();
                        String lastName = input.readLine();
                        String email = input.readLine();
                        String bio = input.readLine();
                        String profileView = input.readLine();
                        String messageReceive = input.readLine();

                        if (!profileView.equalsIgnoreCase("true") &&
                                !profileView.equalsIgnoreCase("false") &&
                                (!messageReceive.equalsIgnoreCase("true") &&
                                        !messageReceive.equalsIgnoreCase("false"))) {
                            output.println("Error! Your account already exists! Or one of your inputs is invalid!");
                        } else {
                            User user = new User(username,
                                    password,
                                    firstName,
                                    lastName,
                                    email,
                                    bio,
                                    Boolean.parseBoolean(profileView),
                                    Boolean.parseBoolean(messageReceive));

                            if (!user.createAccount()) {
                                output.println("Error! Your account already exists! Or one of your inputs is invalid!");
                            } else {
                                output.println("Sign up successfully!");
                            }
                        }
                    } else {
                        break;
                    }

                } while (true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
