package Controller;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.ArrayList;
public class Client implements Runnable {
    private Socket server;
    private BufferedReader fromServer;
    private PrintWriter toServer;
    private int situationCode = 0;  // 0: normal, 1: login success, 2: exit

    public Client(String serverIp, int serverPort) throws IOException {
        this.server = new Socket(serverIp, serverPort);
        this.fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        this.toServer = new PrintWriter(server.getOutputStream(), true);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);  // Use Scanner to read input from System.in
        try {
            // ########################## LOG IN ##########################
            while (situationCode == 0) {
                String input;
                while (true) {
                    System.out.println("Welcome to BoilerTown!");
                    System.out.println("Would you like to create an account or log-in?");
                    System.out.println("Enter '1': Log-in");
                    System.out.println("Enter '2': Create an account");
                    System.out.println("Enter '3': Exit");
                    input = scanner.nextLine();
                    if ("1".equals(input) || "2".equals(input) || "3".equals(input)){
                        break;
                    } else {
                        System.out.println("Invalid input, please enter '1', '2' or '3'.");
                    }
                }

                int command = Integer.parseInt(input);
                toServer.println(command);

                switch (command) {
                    case 1:
                        handleLogin();
                        break;
                    case 2:
                        handleCreateAccount();
                        break;
                    case 3:
                        situationCode = 2;
                        break;
                }
            }
            // ########################## LOG IN ##########################

            // ########################## MAIN MENU ##########################
            while (situationCode == 1) {
                String input;
                while (true) {
                    System.out.println("Welcome to BoilerTown!");
                    System.out.println("Enter '1': View / Edit your user profiles (Change your public status here)");
                    System.out.println("Enter '2': Search and View (add / message / block) an user");
                    System.out.println("Enter '3': List and View (remove / message / block) your friends");
                    System.out.println("Enter '4': List (Remove) your blocks");
                    System.out.println("Enter '5': View (Delete) new or old message");
                    System.out.println("Enter '6': View (accept or reject) friend request");
                    System.out.println("Enter '7': Exit");
                    input = scanner.nextLine();
                    if ("1".equals(input) || "2".equals(input) || "3".equals(input) || "4".equals(input)
                            || "5".equals(input) || "6".equals(input) || "7".equals(input)){
                        break;
                    } else {
                        System.out.println("Invalid input, please enter '1' to '7'.");
                    }
                }

                int command = Integer.parseInt(input);
                toServer.println(command);

                switch (command) {
                    case 1:
                        handleProfile();
                        break;
                    case 2:
                        handleSearch();
                        break;
                    case 3:
                        handleFriends();
                        break;
                    case 4:
                        handleBlocks();
                        break;
                    case 5:
                        handleMessage();
                        break;
                    case 6:
                        handleRequests();
                        break;
                    case 7:
                        situationCode = 2;
                        break;
                }
            }
            // ########################## MAIN MENU ##########################
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();  // Properly close connections
            scanner.close();  // Close the scanner
        }
    }

    private void handleLogin() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (situationCode == 0) {
            System.out.println("Please enter your user name!");
            String username = scanner.nextLine();
            System.out.println("Please enter your password!");
            String password = scanner.nextLine();

            // Send credentials to server for validation
            toServer.println(username);
            toServer.println(password);

            String response = fromServer.readLine();
            if ("SUCCESS".equals(response)) {
                System.out.println("Login Successful!");
                situationCode = 1;
            } else if ("EXIST".equals(response)) {
                System.out.println("Login failed. Your username has already login.");
                situationCode = 2;
            } else {
                System.out.println("Login failed. Please check your username and password.");
            }
        }
    }

    private void handleCreateAccount() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (situationCode == 0) {
            // Username
            System.out.println("Enter username: (Can't be empty or repeat in existing names)");
            String username = scanner.nextLine();
            toServer.println(username);

            // Password
            System.out.println("Enter password (length >= 4): ");
            String password = scanner.nextLine();
            toServer.println(password);

            // First Name
            System.out.println("Enter first name: (Can't be empty or contains space)");
            String firstName = scanner.nextLine();
            toServer.println(firstName);

            // Last Name
            System.out.println("Enter last name: (Can't be empty or contains space)");
            String lastName = scanner.nextLine();
            toServer.println(lastName);

            // Email
            System.out.println("Enter email: (Should be a normal email format)");
            String email = scanner.nextLine();
            toServer.println(email);

            // Bio
            System.out.println("Enter bio: (length <= 50)");
            String bio = scanner.nextLine();
            toServer.println(bio);

            String response = fromServer.readLine();
            if ("SUCCESS".equals(response)) {
                System.out.println("Account created successfully!");
                situationCode = 1;
            } else {
                System.out.println("Account creation failed. Please check your data and try again.");
            }
        }
    }

    private void closeConnections() {
        try {
            // Proper resource closing order and error handling
            if (toServer != null) {
                toServer.close();
            }
            if (fromServer != null) {
                fromServer.close();
            }
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing connections: " + e.getMessage());
        }
    }

    private void handleProfile() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your profile is below:");
        try {
            for (int i = 0; i < 6; i++) {
                String profile = fromServer.readLine();
                System.out.println(profile);
            }
            System.out.println("If your public status is false, you only accept message from friends");
            String input;
            while (true) {
                System.out.println("Enter '1': Change Bio");
                System.out.println("Enter '2': Change Public status");
                System.out.println("Enter '3': Back to main menu");
                input = scanner.nextLine();
                if ("1".equals(input) || "2".equals(input) || "3".equals(input)) {
                    break;
                } else {
                    System.out.println("Invalid input, please enter '1', '2' or '3'.");
                }
            }

            int command = Integer.parseInt(input);
            toServer.println(command);

            switch (command) {
                case 1:
                    handleChangeBio();
                    break;
                case 2:
                    handleChangeStatus();
                    break;
                case 3:
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSearch() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a user name to search ('luuuuuul' is an existing name)");
        String username = scanner.nextLine();
        toServer.println(username);
        String response = fromServer.readLine();
        if ("SUCCESS".equals(response)) {
            System.out.println("The user is found!");
            System.out.println("His/Her profile is below: ");
            for (int i = 0; i < 6; i++) {
                String profile = fromServer.readLine();
                System.out.println(profile);
            }
            String status = fromServer.readLine();
            boolean[] booleans = new boolean[4];
            String[] parts = status.split(",");
            for (int i = 0; i < 4; i++) {
                booleans[i] = Boolean.parseBoolean(parts[i].trim());
            }
            String input;
            System.out.println(booleans);
            while (true) {
                if (!booleans[0] && !booleans[2]) {
                    System.out.println("Enter '1' to sent friend request");
                } else {
                    System.out.println("He/Her is already your friend or you are blocked");
                }
                if (!booleans[2] && (booleans[1] || booleans[0])) {
                    System.out.println("Enter '2' to sent a message to him/her");
                } else {
                    System.out.println("You can't send a message to him/her, the reason includes:\n" +
                            "1.This is a private user but you are not a friend\n" +
                            "2.You are blocked");
                }
                if (!booleans[3]) {
                    System.out.println("Enter '3' to block this user");
                } else {
                    System.out.println("He/Her is already in the blocklist");
                }
                System.out.println("Enter '4' to come back to main menu");
                input = scanner.nextLine();
                if (("1".equals(input) && (!booleans[0] && !booleans[2]))
                        || ("2".equals(input) && !booleans[2] && (booleans[1] || booleans[0]))
                        || ("3".equals(input) && !booleans[2])
                        || "4".equals(input)) {
                    break;
                } else {
                    System.out.println("Invalid input, please refer to the instruction");
                }
            }

            int command = Integer.parseInt(input);
            toServer.println(command);

            switch (command) {
                case 1:
                    handleSendRequset(username);
                    break;
                case 2:
                    handleSendMessage(username);
                    break;
                case 3:
                    handleMakeBlock(username);
                    break;
                case 4:
                    break;
            }
        } else {
            System.out.println("There is no such a user, you may try 'luuuuuul'");
        }
    }
    private void handleFriends() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String response = fromServer.readLine();
        if ("FAIL".equals(response)) {
            System.out.println("No friends to display.");
        } else {
            System.out.println("Your friends list:");
            ArrayList<String> friends = new ArrayList<>();
            String friend;
            int index = 0;
            while (!(friend = fromServer.readLine()).equals("END")) {
                friends.add(friend);
                System.out.println(index++ + ": " + friend);
            }

            // User selects a friend by index for further actions
            int friendIndex;
            while (true) {
                System.out.println("Select a friend by index for further actions:");
                try {
                    friendIndex = Integer.parseInt(scanner.nextLine());
                    if (friendIndex >= 0 && friendIndex < friends.size()) {
                        break;
                    } else {
                        System.out.println("Invalid friend selection. Out of Scope");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Not a number");
                }
            }
            String selectedFriend = friends.get(friendIndex);
            toServer.println(selectedFriend);  // Send selected friend's username to server

            // Receive friend's details and show options
            System.out.println("The friend you select is found!");
            System.out.println("His/Her profile is below: ");
            for (int i = 0; i < 6; i++) {
                String profile = fromServer.readLine();
                System.out.println(profile);
            }


            String input;
            System.out.println("Choose an action for " + selectedFriend + ":");
            while (true) {
                System.out.println("Enter '1' to remove this friend");
                System.out.println("Enter '2' to send a message to this friend");
                System.out.println("Enter '3' to block this friend");
                System.out.println("Enter '4' to return to the main menu");

                input = scanner.nextLine();
                if (("1".equals(input) || "2".equals(input) || "3".equals(input) || "4".equals(input))) {
                    break;
                } else {
                    System.out.println("Invalid input, please refer to the instructions.");
                }
            }

            int command = Integer.parseInt(input);
            toServer.println(command);  // Send command to server

            switch (command) {
                case 1:
                    handleRemoveFriend(selectedFriend);
                    break;
                case 2:
                    handleSendMessage(selectedFriend);
                    break;
                case 3:
                    handleMakeBlock(selectedFriend);
                    break;
                case 4:
                    break;  // Exit this method
            }
        }
    }



    private void handleBlocks() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String response = fromServer.readLine();
        if ("FAIL".equals(response)) {
            System.out.println("No blocked users to display.");
        } else {
            System.out.println("Your blocked users list:");
            ArrayList<String> blockedUsers = new ArrayList<>();
            String blockedUser;
            int index = 0;
            while (!(blockedUser = fromServer.readLine()).equals("END")) {
                blockedUsers.add(blockedUser);
                System.out.println(index++ + ": " + blockedUser);
            }

            // User selects a blocked user by index for further actions
            int blockedUserIndex;
            while (true) {
                System.out.println("Select a blocked user by index for further actions:");
                try {
                    blockedUserIndex = Integer.parseInt(scanner.nextLine());
                    if (blockedUserIndex >= 0 && blockedUserIndex < blockedUsers.size()) {
                        break;
                    } else {
                        System.out.println("Invalid blocked user selection. Out of Scope");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Not a number");
                }
            }
            String selectedBlockedUser = blockedUsers.get(blockedUserIndex);
            toServer.println(selectedBlockedUser);  // Send selected blocked user's username to server

            // Receive blocked user's details and show options
            System.out.println("The blocked user you select is found!");
            System.out.println("His/Her profile is below: ");
            for (int i = 0; i < 6; i++) {
                String profile = fromServer.readLine();
                System.out.println(profile);
            }

            String input;
            System.out.println("Choose an action for " + selectedBlockedUser + ":");
            while (true) {
                System.out.println("Enter '1' to unblock this user");
                System.out.println("Enter '2' to return to the main menu");

                input = scanner.nextLine();
                if ("1".equals(input) || "2".equals(input)) {
                    break;
                } else {
                    System.out.println("Invalid input, please refer to the instructions.");
                }
            }

            int command = Integer.parseInt(input);
            toServer.println(command);  // Send command to server

            switch (command) {
                case 1:
                    handleRemoveBlock(selectedBlockedUser);  // Assuming handleRemoveBlock is implemented
                    break;
                case 2:
                    break;  // Exit this method
            }
        }
    }

    private void handleChangeBio() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter you new Bio (<= 50 characters)");
        String bio = scanner.nextLine();
        toServer.println(bio);
        String response = fromServer.readLine();
        if ("SUCCESS".equals(response)) {
            System.out.println("Changed successful");
        } else {
            System.out.println("Changed failed. Your Bio length has to <= 50");
        }
    }
    private void handleChangeStatus() throws IOException {
        String response = fromServer.readLine();
        if ("SUCCESS".equals(response)) {
            System.out.println("Changed successful");
        } else {
            System.out.println("Changed failed. Set file failed");
        }

    }
    private void handleMessage() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String response = fromServer.readLine();
        if ("FAIL".equals(response)) {
            System.out.println("No messages to display.");
        } else {
            System.out.println("Your messages:");
            ArrayList<String> messages = new ArrayList<>();
            String message;
            while (!(message = fromServer.readLine()).equals("END")) {
                messages.add(message);
                System.out.println(message);
            }

            String input;
            System.out.println("Enter '1' to delete all messages or '2' to return to the main menu:");
            while (true) {
                input = scanner.nextLine();
                if ("1".equals(input)) {
                    toServer.println("DELETE");  // Send command to server to delete all messages
                    String deleteResponse = fromServer.readLine();
                    if ("DELETED".equals(deleteResponse)) {
                        System.out.println("All messages have been successfully deleted.");
                    } else {
                        System.out.println("Failed to delete messages.");
                    }
                    break;
                } else if ("2".equals(input)) {
                    break;  // Just break out of the loop to return to the main menu
                } else {
                    System.out.println("Invalid input, please enter '1' to delete all messages or '2' to return.");
                }
            }
        }
    }

    private void handleRequests() throws IOException {
        System.out.println("Checking for friend requests...");
        String response = fromServer.readLine();

        if ("FAIL".equals(response)) {
            System.out.println("You have no friend requests at this time.");
            return;
        }

        if ("SUCCESS".equals(response)) {
            System.out.println("You have the following friend requests:");
            ArrayList<String> requests = new ArrayList<>();
            String request;
            while (!(request = fromServer.readLine()).equals("END")) {
                requests.add(request);
                System.out.println(request);
            }

            // Ask user to choose an action on a friend request
            Scanner scanner = new Scanner(System.in);
            int requestIndex;
            while (true) {
                System.out.println("Enter the index of the friend request to respond to (or type '-1' to exit):");
                try {
                    requestIndex = Integer.parseInt(scanner.nextLine());
                    if (requestIndex == -1) {
                        toServer.println("EXIT"); // Notify server to stop the process
                        break;
                    }
                    if (requestIndex >= 0 && requestIndex < requests.size()) {
                        System.out.println("You selected: " + requests.get(requestIndex));
                        System.out.println("Enter 'ACCEPT' to accept the friend request, 'REJECT' to reject it:");
                        String command = scanner.nextLine().trim().toUpperCase();
                        if ("ACCEPT".equals(command) || "REJECT".equals(command)) {
                            toServer.println(command + ":" + requestIndex);
                            String serverResponse = fromServer.readLine();
                            if (serverResponse.startsWith("ACCEPTED")) {
                                System.out.println("Friend request from " + requests.get(requestIndex) + " accepted.");
                            } else if (serverResponse.startsWith("REJECTED")) {
                                System.out.println("Friend request from " + requests.get(requestIndex) + " rejected.");
                            } else {
                                System.out.println("Failed to process your request.");
                            }
                            break;
                        } else {
                            System.out.println("Invalid command. Please enter 'ACCEPT' or 'REJECT'.");
                        }
                    } else {
                        System.out.println("Invalid index. Please try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
        }
    }

    private void handleSendRequset(String username) throws IOException {
        if ("SUCCESS".equals(fromServer.readLine())) {
            System.out.println("You sent an request successfully!");
        } else {
            System.out.println("You have already sent one and it still undecided");
        }
    }
    private void handleSendMessage(String username) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your message to him");
        String message = scanner.nextLine();
        toServer.println(message);
        if ("SUCCESS".equals(fromServer.readLine())) {
            System.out.println("You sent an message successfully!");
        } else {
            System.out.println("Message sent failed");
        }
    }
    private void handleMakeBlock(String username) throws IOException {
        if ("SUCCESS".equals(fromServer.readLine())) {
            System.out.println("You block this user successfully!");
        } else {
            System.out.println("There is some IOE issues");
        }
    }
    private void handleRemoveFriend(String username) throws IOException {
        if ("SUCCESS".equals(fromServer.readLine())) {
            System.out.println("The friend is removed successfully!");
        } else {
            System.out.println("There is some IOE issues");
        }
    }
    private void handleRemoveBlock(String username) throws IOException {
        if ("SUCCESS".equals(fromServer.readLine())) {
            System.out.println("The block is removed successfully!");
        } else {
            System.out.println("There is some IOE issues");
        }
    }
    public static void main(String[] args) {
        try {
            Client connection = new Client("localhost", 9090);
            Thread serverThread = new Thread(connection);
            serverThread.start();
            serverThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
