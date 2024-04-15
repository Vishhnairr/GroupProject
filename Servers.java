import java.net.ServerSocket;
import java.io.*;
import java.net.*;
import java.util.*;
public class Servers {
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
        try{
            Servers servers = new Servers(8787);
            servers.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClientHandler extends Thread implements Runnable {
        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.output = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                do {
                    String firstChose = input.readLine();

                    if (firstChose.equals("Log in")) {

                        while (true) {
                            String username = input.readLine();
                            String password = input.readLine();

                            User user = new User(username, password).logIn();

                            if (user == null) {
                                output.println("null");

                            } else {
                                output.println("Log in Successfully!");

                                while (true) {
                                    String secondChose = input.readLine();

                                    if (secondChose.equals("Set your account")) {
                                        while (true) {
                                            String thirdChose = input.readLine();

                                            if (thirdChose.equals("Change your username")) {
                                                String newUsername = input.readLine();

                                                if (!user.setUsername(newUsername)) {
                                                    output.println("Fail to change username. " +
                                                            "Your new username is not unique or valid!");
                                                } else {
                                                    output.println("Change Your username successfully!");
                                                }

                                            } else if (thirdChose.equals("Change your password")) {
                                                String newPassword = input.readLine();

                                                if (!user.setPassword(newPassword)) {
                                                    output.println("Fail to change password. Your new password should not be shorter than 4 characters!");

                                                } else {
                                                    output.println("Change your password successfully!");
                                                }
                                            } else if (secondChose.equals("Change your first name")) {
                                                String newFirstName = input.readLine();

                                                if (!user.setFirstName(newFirstName)) {
                                                    output.println("Fail to change first name. Your first name should have no space!");
                                                } else {
                                                    output.println("Change your first name successfully!");
                                                }
                                            } else if (secondChose.equals("Change your last name")) {
                                                String newLastName = input.readLine();

                                                if (!user.setLastName(newLastName)) {
                                                    output.println("Fail to change last name. Your last name should have no space!");
                                                } else {
                                                    output.println("Change your last name successfully!");
                                                }
                                            } else if (secondChose.equals("Change your email")) {
                                                String newEmail = input.readLine();

                                                if (!user.setEmail(newEmail)) {
                                                    output.println("Fail to change email. Your email should be the form: ___@___.___ with no spaces!");
                                                } else {
                                                    output.println("Change your email successfully!");
                                                }
                                            } else if (secondChose.equals("Edit your bio")) {
                                                String newBio = input.readLine();

                                                if (!user.setBio(newBio)) {
                                                    output.println("Fail to change bio. Your bio should not be greater than 50 characters!");
                                                } else {
                                                    output.println("Change your bio successfully!");
                                                }
                                            } else if (secondChose.equals("Set your profile view")) {
                                                String newProfileView = input.readLine();
                                                boolean set = Boolean.parseBoolean(newProfileView);

                                                if (!user.setProfileView(set)) {
                                                    output.println("Fail to set profile view.");
                                                } else {
                                                    output.println("Set your profile view successfully!");
                                                }
                                            } else if (secondChose.equals("Set your message receive")) {
                                                String newMessageReceive = input.readLine();
                                                boolean set = Boolean.parseBoolean(newMessageReceive);

                                                if (!user.setMessageCheck(set)) {
                                                    output.println("Fail to set message receive.");
                                                } else {
                                                    output.println("Set your message receive successfully!");
                                                }
                                            }
                                        }
                                    } else if (secondChose.equals("View All users")) {
                                        String[] allUsersArray = user.viewAllUsers();
                                        String usersCount = String.valueOf(allUsersArray.length);
                                        output.println(usersCount);

                                        for (int i = 0; i < allUsersArray.length; i++) {
                                            output.println(allUsersArray[i]);
                                        }

                                        while (true) {
                                            String thirdChose = input.readLine();

                                            if (thirdChose.equals("View profile")) {
                                                String userSelected = input.readLine();
                                                Friends friends = new Friends(user, userSelected);
                                                String userSelectedFile = friends.viewProfile();
                                                if (userSelectedFile == null) {
                                                    output.println("0");
                                                } else {
                                                    String[] fileSplits = userSelectedFile.split("\n");

                                                    int fileCount = fileSplits.length;
                                                    String count = String.valueOf(fileCount);
                                                    output.println(count);

                                                    for (int j = 0; j < fileCount; j++) {
                                                        output.println(fileSplits[j]);
                                                    }
                                                }
                                            } else if (thirdChose.equals("Make a friend request")) {
                                                String userSelected = input.readLine();
                                                Friends friends = new Friends(user, userSelected);

                                                if (!friends.makeFriendRequest()) {
                                                    output.println("Fail to make a friend. This user might already be your friend or blocked.");
                                                } else {
                                                    output.println("Make a friend request successfully!");
                                                }
                                            } else if (thirdChose.equals("Block a user")) {
                                                String userSelected = input.readLine();
                                                Friends friends = new Friends(user, userSelected);

                                                if (!friends.blockUser()) {
                                                    output.println("Fail to block a user. This user might already be blocked.");
                                                } else {
                                                    output.println("Block a user successfully!");
                                                }
                                            } else if (thirdChose.equals("Send a message")) {
                                                String userSelected = input.readLine();
                                                String content = input.readLine();
                                                MessageList messageList = new MessageList(content, user, userSelected);

                                                if (!messageList.sendMessage()) {
                                                    output.println("Fail to send a message. May be is because you are not this user's friend.");
                                                } else {
                                                    output.println("Send a message successfully!");
                                                }
                                            } else if (thirdChose.equals("View message history")) {
                                                String userSelected = input.readLine();
                                                String content = "content";
                                                MessageList messageList = new MessageList(content, user, userSelected);

                                                ArrayList<String> messages = messageList.viewMessageHistory();
                                                int messageCount = messages.size();
                                                String count = String.valueOf(messageCount);
                                                output.println(count);

                                                for (int m = 0; m < messageCount; m++) {
                                                    output.println(messages.get(m));
                                                }

                                            }
                                        }
                                    } else if (secondChose.equals("Search a user")) {
                                        String searchedUser = input.readLine();

                                        if (!user.searchUser(searchedUser)) {
                                            output.println("No user searched. Please check the username is valid or not.");
                                        } else {
                                            output.println("User searched!");

                                            while (true) {
                                                String thirdChose = input.readLine();

                                                if (thirdChose.equals("View profile")) {
                                                    Friends friends = new Friends(user, searchedUser);
                                                    String userSelectedFile = friends.viewProfile();

                                                    if (userSelectedFile == null) {
                                                        output.println("0");
                                                    } else {
                                                        String[] fileSplits = userSelectedFile.split("\n");

                                                        int fileCount = fileSplits.length;
                                                        String count = String.valueOf(fileCount);
                                                        output.println(count);

                                                        for (int j = 0; j < fileCount; j++) {
                                                            output.println(fileSplits[j]);
                                                        }
                                                    }

                                                } else if (thirdChose.equals("Make a friend request")) {
                                                    Friends friends = new Friends(user, searchedUser);

                                                    if (!friends.makeFriendRequest()) {
                                                        output.println("Fail to make a friend. This user might already be your friend or blocked.");
                                                    } else {
                                                        output.println("Make a friend request successfully!");
                                                    }
                                                } else if (thirdChose.equals("Block a user")) {
                                                    Friends friends = new Friends(user, searchedUser);

                                                    if (!friends.blockUser()) {
                                                        output.println("Fail to block a user. This user might already be blocked.");
                                                    } else {
                                                        output.println("Block a user successfully!");
                                                    }
                                                } else if (thirdChose.equals("Send a message")) {
                                                    String content = input.readLine();
                                                    MessageList messageList = new MessageList(content, user, searchedUser);

                                                    if (!messageList.sendMessage()) {
                                                        output.println("Fail to send a message. May be is because you are not this user's friend.");
                                                    } else {
                                                        output.println("Send a message successfully!");
                                                    }
                                                } else if (thirdChose.equals("View message history")) {
                                                    String content = "content";
                                                    MessageList messageList = new MessageList(content, user, searchedUser);

                                                    ArrayList<String> messages = messageList.viewMessageHistory();
                                                    int messageCount = messages.size();
                                                    String count = String.valueOf(messageCount);
                                                    output.println(count);

                                                    for (int m = 0; m < messageCount; m++) {
                                                        output.println(messages.get(m));
                                                    }
                                                } else if (thirdChose.equals("Exit")) {
                                                    break;
                                                }
                                            }
                                        }
                                    } else if (secondChose.equals("View your profile")) {
                                        String files = user.viewFile();
                                        String[] fileSplits = files.split("\n");
                                        int fileCount = fileSplits.length;
                                        String count = String.valueOf(fileCount);
                                        output.println(count);

                                        for (int i = 0; i < fileCount; i++) {
                                            output.println(fileSplits[i]);
                                        }
                                    } else if (secondChose.equals("View your friend requests")) {
                                        String[] requests = user.viewFriendsRequest();
                                        int requestsCount = requests.length;
                                        String count = String.valueOf(requestsCount);
                                        output.println(count);

                                        for (int i = 0; i < requestsCount; i++) {
                                            output.println(requests[i]);
                                        }

                                        while (true) {
                                            String thirdChose = input.readLine();

                                            if (thirdChose.equals("Add a friend")) {
                                                String addFriend = input.readLine();

                                                Friends friends = new Friends(user, addFriend);

                                                if (!friends.addFriend()) {
                                                    output.println("Fail to add a friend");
                                                } else {
                                                    output.println("Add a friend successfully!");
                                                }
                                            } else {
                                                break;
                                            }
                                        }
                                    } else if (secondChose.equals("View your friends")) {
                                        String[] friends = user.viewFriends();
                                        int friendsCount = friends.length;
                                        String count = String.valueOf(friendsCount);
                                        output.println(count);

                                        for (int i = 0; i < friendsCount; i++) {
                                            output.println(friends[i]);
                                        }

                                        while (true) {
                                            String thirdChose = input.readLine();

                                            if (thirdChose.equals("Remove a friend")) {
                                                String removedFriend = input.readLine();

                                                Friends friend = new Friends(user, removedFriend);

                                                if (!friend.removeFriend()) {
                                                    output.println("Fail to remove.");
                                                } else {
                                                    output.println("Remove a friend successfully!");
                                                }
                                            } else if (thirdChose.equals("Block a user")) {
                                                String blockedFriend = input.readLine();

                                                Friends friend = new Friends(user, blockedFriend);

                                                if (!friend.blockUser()) {
                                                    output.println("Fail to block.");
                                                } else {
                                                    output.println("Block a user successfully!");
                                                }
                                            }
                                        }
                                    } else if (secondChose.equals("View your blocks")) {
                                        String[] blocks = user.viewBlocks();
                                        System.out.println(blocks);
                                        int blocksCount = blocks.length;
                                        String count = String.valueOf(blocksCount);
                                        output.println(count);

                                        for (int i = 0; i < blocksCount; i++) {
                                            output.println(blocks[i]);
                                        }

                                        while (true) {
                                            String thirdChose = input.readLine();

                                            if (thirdChose.equals("Remove a blocked user")) {
                                                String removedBlocked = input.readLine();

                                                Friends friend = new Friends(user, removedBlocked);

                                                if (!friend.removeBlock()) {
                                                    output.println("Fail to remove.");
                                                } else {
                                                    output.println("Remove a blocked successfully!");
                                                }
                                            }
                                        }
                                    }
                                }

                            }
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

                        User user = new User(username,
                                password,
                                firstName,
                                lastName,
                                email,
                                bio,
                                Boolean.parseBoolean(profileView),
                                Boolean.parseBoolean(messageReceive));

                        if (!user.createAccount()) {
                            output.println("Error! Your account already exist! Or one of your enter is invalid!");
                        } else {
                            output.println("Sign up successfully!");
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
