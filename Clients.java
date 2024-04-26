import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.*;

/**
 * Client
 *
 * This class runs the GUI that users will interact with when they use
 * the social platform. It stores the commands and data that the users choose
 * and input to send to the platform's server and displays interfaces
 * based on data received from the server.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 26, 2024
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
        boolean breakCheck = true;
        boolean exit = false;

        JOptionPane.showMessageDialog(null, "Welcome to Boiler Town!",
                "Boiler Town", JOptionPane.INFORMATION_MESSAGE);

        do { // program begin
            GeneralSelectionPane paneOne = new GeneralSelectionPane();
            String[] firstOptions = {"Log in", "Sign up", "Exit"};
            paneOne.setChoices(firstOptions);
            paneOne.setDropDownLabel("What do you want to do today?");
            paneOne.setCb();
            paneOne.setButtonEnter();
            paneOne.createSelectionPane();  // This displays the GUI

            // Busy-wait for the user to make a selection
            while (!paneOne.isButtonHit()) {
                try {
                    Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;  // Exit if the thread is interrupted
                }
            }
            String firstChose = paneOne.getOption();

            // Clean up the frame if still open
            if (paneOne.isOpen()) {
                paneOne.getFrame().dispose();
            }
            if (firstChose == null) {
                return;

            } else if (firstChose.equals("Log in")) { //Log in
                System.out.println("User has chosen to log in");
                while (breakCheck) { //Log in begin - username

                    TextBoxes first = new TextBoxes();
                    first.addTextBoxes("Username");
                    first.addTextBoxPassword("Password");
                    first.setButtonEnter();
                    first.createPane();

                    String responses = "";

                    do {
                        do {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                return;
                            }
                            if (first.getHitButton()) {
                                responses = first.getResponses();
                            }
                        } while (!first.getHitButton());

                        if (responses.equals(";")) {
                            JOptionPane.showMessageDialog(null, "Error! Your enter is empty!");
                            first.emptyTextFields();
                            first.setButtonEnter();
                            first.setHitButton(false);

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
                                first.getFrame().dispose();
                                responses = ";";
                                break;
                            }
                            responses = "";
                        } else if (!responses.equals("exit")) {
                            String username = responses.substring(0, responses.indexOf(";"));
                            String password = responses.substring(responses.indexOf(";") + 1);

                            clients.output.println(firstChose); //pass first chose of log in (1)
                            clients.output.println(username); // pass username (2)
                            clients.output.println(password); // pass password (3)

                            String user = clients.input.readLine(); // receive user {1}

                            if (user.equals("null")) {
                                JOptionPane.showMessageDialog(null,
                                        "Your username or password is wrong!");

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
                                    first.getFrame().dispose();
                                    responses = ";";
                                    break;
                                } else {
                                    first.emptyTextFields();
                                    first.setHitButton(false);
                                }
                                responses = "";
                            }
                        } else {
                            first.getFrame().dispose();
                            responses = ";";
                            break;
                        }
                    } while (responses.isEmpty());

                    if (!responses.equals(";")) { //Log in successfully
                        first.getFrame().dispose();
                        JOptionPane.showMessageDialog(null,
                                "Log In Successful!");
                        String[] secondOptions = {"View All users",
                                "Search a user",
                                "View your profile",
                                "View your friends",
                                "View your blocks",
                                "View your friend requests",
                                "Exit"};

                        while (breakCheck) { //Interactions after Log In
                            GeneralSelectionPane paneTwo = new GeneralSelectionPane();
                            paneTwo.setChoices(secondOptions);
                            paneTwo.setDropDownLabel("What do you want to do now?");
                            paneTwo.setCb();
                            paneTwo.setButtonEnter();
                            paneTwo.createSelectionPane();  // This displays the GUI
                            while (!paneTwo.isButtonHit()) {
                                try {
                                    Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    return;  // Exit if the thread is interrupted
                                }
                            }
                            String secondChose = paneTwo.getOption();

                            // Clean up the frame if still open
                            if (paneTwo.isOpen()) {
                                paneTwo.getFrame().dispose();
                            }

                            if (secondChose == null) {
                                return;
                            } else if (secondChose.equals("View All users")) {
                                System.out.println("User has chosen to " + secondChose);
                                clients.output.println(secondChose); //pass secondChose (4)

                                while (true) { //interaction in All users
                                    ArrayList<String> allUsers = new ArrayList<>();
                                    allUsers.add("Click on this if you want to exit");

                                    String usersCount = clients.input.readLine(); //receive length of users {2}
                                    int count = Integer.parseInt(usersCount);

                                    for (int i = 0; i < count; i++) {
                                        allUsers.add(clients.input.readLine()); // receive users {3}
                                    }

                                    String[] allUser = allUsers.toArray(new String[allUsers.size()]);
                                    GeneralSelectionPane paneThree = new GeneralSelectionPane();
                                    paneThree.setChoices(allUser);
                                    paneThree.setDropDownLabel("What do you want to do now?");
                                    paneThree.setCb();
                                    paneThree.setButtonEnter();
                                    paneThree.createSelectionPane();  // This displays the GUI

                                    // Busy-wait for the user to make a selection
                                    while (!paneThree.isButtonHit()) {
                                        try {
                                            Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            return;  // Exit if the thread is interrupted
                                        }
                                    }
                                    String userSelected = paneThree.getOption();

                                    // Clean up the frame if still open
                                    if (paneThree.isOpen()) {
                                        paneThree.getFrame().dispose();
                                    }

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
                                            GeneralSelectionPane paneFour = new GeneralSelectionPane();
                                            paneFour.setChoices(thirdOptions);
                                            paneFour.setDropDownLabel("What do you want to interact with this user?");
                                            paneFour.setCb();
                                            paneFour.setButtonEnter();
                                            paneFour.createSelectionPane();  // This displays the GUI

                                            // Busy-wait for the user to make a selection
                                            while (!paneFour.isButtonHit()) {
                                                try {
                                                    Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                                } catch (InterruptedException e) {
                                                    Thread.currentThread().interrupt();
                                                    return;  // Exit if the thread is interrupted
                                                }
                                            }
                                            String thirdChose = paneFour.getOption();

                                            // Clean up the frame if still open
                                            if (paneFour.isOpen()) {
                                                paneFour.getFrame().dispose();
                                            }
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
                                                    CountDownLatch latch = new CountDownLatch(1);
                                                    new UserProfileDisplay(file, () -> {
                                                        latch.countDown();
                                                    });
                                                    try {
                                                        latch.await(); // This will block until the count reaches zero
                                                    } catch (InterruptedException e) {
                                                        Thread.currentThread().interrupt();
                                                        System.out.println("Interrupted while waiting for the profile editor to close.");
                                                    }

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
                                                TextBoxes messageBox = new TextBoxes();
                                                String content;
                                                boolean noInput = true;
                                                messageBox.addTextBoxes("Please enter what you want to send: ");
                                                messageBox.setButtonEnter();
                                                messageBox.createPane();

                                                content = "";
                                                do {
                                                    // delay makes loop work
                                                    try {
                                                        Thread.sleep(10);
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    if (messageBox.getHitButton()) {
                                                        content = messageBox.getResponses();
                                                        noInput = false;
                                                    }
                                                } while (noInput);
                                                if (messageBox.getFrame() != null) {
                                                    messageBox.getFrame().dispose();
                                                }

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

                                                String finalHistory = history;
                                                CountDownLatch latch = new CountDownLatch(1);

                                                SwingUtilities.invokeLater(() -> {
                                                    new MessageHistoryEditor(finalHistory, new MessageHistoryEditor.MessageHistoryListener() {
                                                        @Override
                                                        public void onWindowClosed() {
                                                            System.out.println("111");
                                                            clients.output.println("History window was closed");
                                                            latch.countDown(); // Notify the latch that the task is completed
                                                        }

                                                        @Override
                                                        public void onMessagesDeleted(List<String> remainingMessages) {
                                                            System.out.println("!!!");
                                                            if (!finalHistory.isEmpty()) {
                                                                clients.output.println("Delete");
                                                                clients.output.println(remainingMessages.size());
                                                                for (int i = 0; i < remainingMessages.size(); i++) {
                                                                    clients.output.println(remainingMessages.get(i));
                                                                }
                                                                String result = null;
                                                                try {
                                                                    result = clients.input.readLine();
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                JOptionPane.showMessageDialog(null, result);
                                                            }
                                                            latch.countDown(); // Notify the latch that the task is completed
                                                        }
                                                    });
                                                });

// Wait for the message history editor to close
                                                try {
                                                    latch.await(); // This will block until the count reaches zero
                                                } catch (InterruptedException e) {
                                                    Thread.currentThread().interrupt();
                                                    System.out.println("Interrupted while waiting for the message history editor to close.");
                                                }
                                            } else {
                                                clients.output.println(thirdChose); //pass thirdChose (6)
                                                System.out.println(thirdChose);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else if (secondChose.equals("Search a user")) {
                                System.out.println("User has chosen to " + secondChose);

                                TextBoxes second = new TextBoxes();
                                second.addTextBoxes("Please enter the user you want to search (Enter a username)");
                                second.setButtonEnter();
                                second.createPane();

                                String response = "";

                                do {
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                        return;
                                    }
                                    if (second.getHitButton()) {
                                        response = second.getResponses();
                                    }
                                } while (!second.getHitButton());

                                String userSearched = response;

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
                                        second.getFrame().dispose();
                                    } else {
                                        second.getFrame().dispose();
                                        String[] thirdOptions = {"View profile",
                                                "Make a friend request",
                                                "Block a user",
                                                "Send a message",
                                                "View message history",
                                                "Exit"};
                                        while (true) { //Interactions in search user
                                            GeneralSelectionPane paneFive = new GeneralSelectionPane();
                                            paneFive.setChoices(thirdOptions);
                                            paneFive.setDropDownLabel("What do you want to interact with this user?");
                                            paneFive.setCb();
                                            paneFive.setButtonEnter();
                                            paneFive.createSelectionPane();  // This displays the GUI

                                            // Busy-wait for the user to make a selection
                                            while (!paneFive.isButtonHit()) {
                                                try {
                                                    Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                                } catch (InterruptedException e) {
                                                    Thread.currentThread().interrupt();
                                                    return;  // Exit if the thread is interrupted
                                                }
                                            }
                                            String thirdChose = paneFive.getOption();

                                            // Clean up the frame if still open
                                            if (paneFive.isOpen()) {
                                                paneFive.getFrame().dispose();
                                            }

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

                                                        CountDownLatch latch = new CountDownLatch(1);
                                                        new UserProfileDisplay(file, () -> {
                                                            latch.countDown();
                                                        });
                                                        try {
                                                            latch.await(); // This will block until the count reaches zero
                                                        } catch (InterruptedException e) {
                                                            Thread.currentThread().interrupt();
                                                            System.out.println("Interrupted while waiting for the profile editor to close.");
                                                        }

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
                                                    TextBoxes messageBox = new TextBoxes();
                                                    String content;
                                                    boolean noInput = true;
                                                    messageBox.addTextBoxes("Please enter what you want to send: ");
                                                    messageBox.setButtonEnter();
                                                    messageBox.createPane();

                                                    content = "";
                                                    do {
                                                        // delay makes loop work
                                                        try {
                                                            Thread.sleep(10);
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (messageBox.getHitButton()) {
                                                            content = messageBox.getResponses();
                                                            noInput = false;
                                                        }
                                                    } while (noInput);
                                                    if (messageBox.getFrame() != null) {
                                                        messageBox.getFrame().dispose();
                                                    }

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

                                                    String finalHistory = history;
                                                    CountDownLatch latch = new CountDownLatch(1);

                                                    SwingUtilities.invokeLater(() -> {
                                                        new MessageHistoryEditor(finalHistory, new MessageHistoryEditor.MessageHistoryListener() {
                                                            @Override
                                                            public void onWindowClosed() {
                                                                System.out.println("111");
                                                                clients.output.println("History window was closed");
                                                                latch.countDown(); // Notify the latch that the task is completed
                                                            }

                                                            @Override
                                                            public void onMessagesDeleted(List<String> remainingMessages) {
                                                                System.out.println("!!!");
                                                                if (!finalHistory.isEmpty()) {
                                                                    clients.output.println("Delete");
                                                                    clients.output.println(remainingMessages.size());
                                                                    for (int i = 0; i < remainingMessages.size(); i++) {
                                                                        clients.output.println(remainingMessages.get(i));
                                                                    }
                                                                    String result = null;
                                                                    try {
                                                                        result = clients.input.readLine();
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    JOptionPane.showMessageDialog(null, result);
                                                                }
                                                                latch.countDown(); // Notify the latch that the task is completed
                                                            }
                                                        });
                                                    });

// Wait for the message history editor to close
                                                    try {
                                                        latch.await(); // This will block until the count reaches zero
                                                    } catch (InterruptedException e) {
                                                        Thread.currentThread().interrupt();
                                                        System.out.println("Interrupted while waiting for the message history editor to close.");
                                                    }
                                                } else {
                                                    clients.output.println(thirdChose); //pass thirdChose (6)
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (secondChose.equals("View your profile")) {
                                System.out.println("User has chosen to " + secondChose);
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

                                String finalProfile = profile;
                                CountDownLatch latch = new CountDownLatch(1);

                                SwingUtilities.invokeLater(() -> {
                                    EditableUserProfile profileEditor = new EditableUserProfile(finalProfile);
                                    profileEditor.setListener(new EditableUserProfile.UserProfileListener() {
                                        @Override
                                        public void onProfileSave(String[] profileData) {
                                            clients.output.println("profile_saved_and_closed"); // Notify server of save and close
                                            System.out.println("profile_saved_and_closed");
                                            for (String data : profileData) {
                                                clients.output.println(data);
                                            }
                                            profileEditor.closeWindow(); // Close the window
                                            latch.countDown(); // Notify the latch that the task is completed
                                        }

                                        @Override
                                        public void onProfileClose() {
                                            clients.output.println("profile_edit_cancelled"); // Notify server that the profile edit was cancelled
                                            System.out.println("profile_edit_cancelled");
                                            latch.countDown(); // Notify the latch that the task is completed
                                        }
                                    });
                                });

// Wait for the profile editor to close
                                try {
                                    latch.await(); // This will block until the count reaches zero
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    System.out.println("Interrupted while waiting for the profile editor to close.");
                                }



                            } else if (secondChose.equals("View your friends")) {
                                clients.output.println(secondChose); //pass secondChose (4)

                                while (true) { //Interactions in View your Friends
                                    String friendsCount = clients.input.readLine(); //receive length of friends {2}
                                    int count = Integer.parseInt(friendsCount);
                                    ArrayList<String> friends = new ArrayList<>();
                                    friends.add("Click on this if you want to exit");

                                    for (int i = 0; i < count; i++) {
                                        friends.add(clients.input.readLine()); //receive friends {3}
                                    }

                                    String[] friend = friends.toArray(new String[friends.size()]);
                                    GeneralSelectionPane paneSix = new GeneralSelectionPane();
                                    paneSix.setChoices(friend);
                                    paneSix.setDropDownLabel("Which friend would you like to interact with?");
                                    paneSix.setCb();
                                    paneSix.setButtonEnter();
                                    paneSix.createSelectionPane();  // This displays the GUI

                                    // Busy-wait for the user to make a selection
                                    while (!paneSix.isButtonHit()) {
                                        try {
                                            Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            return;  // Exit if the thread is interrupted
                                        }
                                    }
                                    String pickedFriend = paneSix.getOption();

                                    // Clean up the frame if still open
                                    if (paneSix.isOpen()) {
                                        paneSix.getFrame().dispose();
                                    }

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

                                        GeneralSelectionPane paneSeven = new GeneralSelectionPane();
                                        paneSeven.setChoices(thirdOptions);
                                        paneSeven.setDropDownLabel("What do you want to do with your friend?");
                                        paneSeven.setCb();
                                        paneSeven.setButtonEnter();
                                        paneSeven.createSelectionPane();  // This displays the GUI

                                        // Busy-wait for the user to make a selection
                                        while (!paneSeven.isButtonHit()) {
                                            try {
                                                Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                return;  // Exit if the thread is interrupted
                                            }
                                        }
                                        String thirdChose = paneSeven.getOption();

                                        // Clean up the frame if still open
                                        if (paneSeven.isOpen()) {
                                            paneSeven.getFrame().dispose();
                                        }

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

                                                CountDownLatch latch = new CountDownLatch(1);
                                                new UserProfileDisplay(file, () -> {
                                                    latch.countDown();
                                                });
                                                try {
                                                    latch.await(); // This will block until the count reaches zero
                                                } catch (InterruptedException e) {
                                                    Thread.currentThread().interrupt();
                                                    System.out.println("Interrupted while waiting for the profile editor to close.");
                                                }

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

                                            String finalHistory = history;
                                            CountDownLatch latch = new CountDownLatch(1);

                                            SwingUtilities.invokeLater(() -> {
                                                new MessageHistoryEditor(finalHistory, new MessageHistoryEditor.MessageHistoryListener() {
                                                    @Override
                                                    public void onWindowClosed() {
                                                        System.out.println("111");
                                                        clients.output.println("History window was closed");
                                                        latch.countDown(); // Notify the latch that the task is completed
                                                    }

                                                    @Override
                                                    public void onMessagesDeleted(List<String> remainingMessages) {
                                                        System.out.println("!!!");
                                                        if (!finalHistory.isEmpty()) {
                                                            clients.output.println("Delete");
                                                            clients.output.println(remainingMessages.size());
                                                            for (int i = 0; i < remainingMessages.size(); i++) {
                                                                clients.output.println(remainingMessages.get(i));
                                                            }
                                                            String result = null;
                                                            try {
                                                                result = clients.input.readLine();
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                            JOptionPane.showMessageDialog(null, result);
                                                        }
                                                        latch.countDown(); // Notify the latch that the task is completed
                                                    }
                                                });
                                            });

// Wait for the message history editor to close
                                            try {
                                                latch.await(); // This will block until the count reaches zero
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                System.out.println("Interrupted while waiting for the message history editor to close.");
                                            }
                                        } else if (thirdChose.equals("Send a Message")) {
                                            TextBoxes messageBox = new TextBoxes();
                                            String content;
                                            boolean noInput = true;
                                            messageBox.addTextBoxes("Please enter what you want to send: ");
                                            messageBox.setButtonEnter();
                                            messageBox.createPane();

                                            content = "";
                                            do {
                                                // delay makes loop work
                                                try {
                                                    Thread.sleep(10);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                if (messageBox.getHitButton()) {
                                                    content = messageBox.getResponses();
                                                    noInput = false;
                                                }
                                            } while (noInput);
                                            if (messageBox.getFrame() != null) {
                                                messageBox.getFrame().dispose();
                                            }

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
                            } else if (secondChose.equals("View your blocks")) {
                                System.out.println("User has chosen to " + secondChose);
                                clients.output.println(secondChose); //pass secondChose (4)
                                while (true) {
                                    String blocksCount = clients.input.readLine(); //receive the length of blocks {2}
                                    int count = Integer.parseInt(blocksCount);

                                    ArrayList<String> blocks = new ArrayList<>();
                                    blocks.add("Click on this if you want to exit");

                                    for (int i = 0; i < count; i++) {
                                        blocks.add(clients.input.readLine()); //receive blocks {3}
                                    }

                                    String[] block = blocks.toArray(new String[blocks.size()]);

                                    //Interactions in View your blocks
                                    GeneralSelectionPane paneEight = new GeneralSelectionPane();
                                    paneEight.setChoices(block);
                                    paneEight.setDropDownLabel("Who do you want to interact with?");
                                    paneEight.setCb();
                                    paneEight.setButtonEnter();
                                    paneEight.createSelectionPane();  // This displays the GUI

                                    // Busy-wait for the user to make a selection
                                    while (!paneEight.isButtonHit()) {
                                        try {
                                            Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            return;  // Exit if the thread is interrupted
                                        }
                                    }
                                    String pickedBlock = paneEight.getOption();

                                    // Clean up the frame if still open
                                    if (paneEight.isOpen()) {
                                        paneEight.getFrame().dispose();
                                    }

                                    if (pickedBlock == null) {
                                        return;
                                    } else if (pickedBlock.equals("Click on this if you want to exit")) {
                                        clients.output.println(pickedBlock); //pass pickedBlock (5)
                                        break;
                                    } else {
                                        clients.output.println(pickedBlock); //pass pickedBlock (5)

                                        String[] thirdOptions = {"Remove Block", "Exit"};

                                        //Interactions in view your blocks after chose a specific block
                                        GeneralSelectionPane paneNine = new GeneralSelectionPane();
                                        paneNine.setChoices(thirdOptions);
                                        paneNine.setDropDownLabel("What do you want to interact with this user?");
                                        paneNine.setCb();
                                        paneNine.setButtonEnter();
                                        paneNine.createSelectionPane();  // This displays the GUI

                                        // Busy-wait for the user to make a selection
                                        while (!paneNine.isButtonHit()) {
                                            try {
                                                Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                return;  // Exit if the thread is interrupted
                                            }
                                        }
                                        String thirdChose = paneNine.getOption();

                                        // Clean up the frame if still open
                                        if (paneNine.isOpen()) {
                                            paneNine.getFrame().dispose();
                                        }

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
                            } else if (secondChose.equals("View your friend requests")) {
                                System.out.println("User has chosen to " + secondChose);
                                clients.output.println(secondChose); //pass secondChose (4)

                                while (true) { //Interactions in friend request
                                    String requestsCount = clients.input.readLine(); //receive the length of requests {2}
                                    int count = Integer.parseInt(requestsCount);

                                    ArrayList<String> requests = new ArrayList<>();
                                    requests.add("Click on this if you want to exit");

                                    for (int i = 0; i < count; i++) {
                                        requests.add(clients.input.readLine()); //receive requests {3}
                                    }

                                    String[] request = requests.toArray(new String[requests.size()]);
                                    GeneralSelectionPane paneTen = new GeneralSelectionPane();
                                    paneTen.setChoices(request);
                                    paneTen.setDropDownLabel("What do you want to do today?");
                                    paneTen.setCb();
                                    paneTen.setButtonEnter();
                                    paneTen.createSelectionPane();  // This displays the GUI

                                    // Busy-wait for the user to make a selection
                                    while (!paneTen.isButtonHit()) {
                                        try {
                                            Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                        } catch (InterruptedException e) {
                                            Thread.currentThread().interrupt();
                                            return;  // Exit if the thread is interrupted
                                        }
                                    }
                                    String pickedRequest = paneTen.getOption();

                                    // Clean up the frame if still open
                                    if (paneTen.isOpen()) {
                                        paneTen.getFrame().dispose();
                                    }

                                    if (pickedRequest == null) {
                                        return;
                                    } else if (pickedRequest.equals("Click on this if you want to exit")) {
                                        clients.output.println(pickedRequest); //pass pickedRequest (5)
                                        break;
                                    } else {
                                        clients.output.println(pickedRequest); //pass pickedRequest (5)
                                        GeneralSelectionPane paneThree = new GeneralSelectionPane();
                                        String[] thirdOptions = {"Accept request", "Reject request", "Exit"};
                                        paneThree.setChoices(thirdOptions);
                                        paneThree.setDropDownLabel("What do you want to do now?");
                                        paneThree.setCb();
                                        paneThree.setButtonEnter();
                                        paneThree.createSelectionPane();  // This displays the GUI

                                        // Busy-wait for the user to make a selection
                                        while (!paneThree.isButtonHit()) {
                                            try {
                                                Thread.sleep(100);  // Sleep to reduce CPU usage while waiting
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                                return;  // Exit if the thread is interrupted
                                            }
                                        }
                                        String thirdChose = paneThree.getOption();

                                        // Clean up the frame if still open
                                        if (paneThree.isOpen()) {
                                            paneThree.getFrame().dispose();
                                        }

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
                                        }
                                    }
                                }
                            } else {
                                clients.output.println(secondChose); //pass secondChose (4)
                                break;
                            }
                        }
                        break;
                    }
                    break;
                }
                break;//Log in username end

            } else if (firstChose.equals("Sign up")) { //Sign up
                System.out.println("User has chosen to sign up");

                TextBoxes third = new TextBoxes();
                third.addTextBoxes("Username");
                third.addTextBoxes("Password");
                third.addTextBoxes("First Name");
                third.addTextBoxes("Last Name");
                third.addTextBoxes("Email");
                third.addTextBoxes("Bio");
                third.addTextBoxes("Profile View (Enter True or False)");
                third.addTextBoxes("Message Receive (Enter True or False)");
                third.setButtonEnter();
                //third.pictureSignIn();
                third.createPane();

                String responses = "";

                do {
                    do {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                        if (third.getHitButton()) {
                            responses = third.getResponses();
                        }
                    } while (!third.getHitButton());

                    if (!responses.equals(";;;;;;;")) {

                        try {
                            String[] spliter = responses.split(";");
                            String username = spliter[0];
                            String password = spliter[1];
                            String firstName = spliter[2];
                            String lastName = spliter[3];
                            String email = spliter[4];
                            String bio = spliter[5];
                            String profileView = spliter[6];
                            String messageReceive = spliter[7];

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
                                JOptionPane.showMessageDialog(null, "You need to log in your " +
                                        "account after you signed up.");
                                third.getFrame().dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, result);
                                responses = "";
                                third.emptyTextFields();
                                third.setHitButton(false);
                            }
                        } catch (IndexOutOfBoundsException e) {
                            if (responses.equals("exit")) {
                                third.getFrame().dispose();
                                responses = "EXIT";
                            } else {
                                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                                responses = "";

                                String[] options = {"Yes", "No"};
                                int option = JOptionPane.showOptionDialog(
                                        null,
                                        "Do you want to exit Boiler Town?",
                                        "Boiler Town",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        options, options[1]);

                                if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.YES_OPTION) {
                                    third.getFrame().dispose();
                                    responses = "EXIT";
                                } else {
                                    third.emptyTextFields();
                                    third.setHitButton(false);
                                }
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please fill all fields!");
                        responses = "";

                        String[] options = {"Yes", "No"};
                        int option = JOptionPane.showOptionDialog(
                                null,
                                "Do you want to exit Boiler Town?",
                                "Boiler Town",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options, options[1]);

                        if (option == JOptionPane.CLOSED_OPTION || option == JOptionPane.YES_OPTION) {
                            third.getFrame().dispose();
                            responses = "EXIT";
                        } else {
                            third.emptyTextFields();
                            third.setHitButton(false);
                        }
                    }
                } while (responses.isEmpty());
                break;
            } else { //Other than Log in or Sign Up, directly just end the program
                System.out.println("User has chosen to exit");
                JOptionPane.showMessageDialog(null, "Thank you for using Boiler Town!");
                exit = true;
                break;
            }

        } while (true);

        if (!exit) {
            System.out.println("User has chosen to exit");
            JOptionPane.showMessageDialog(null, "Thank you for using Boiler Town!");
        }

        clients.disconnect(); //program end
    }
}
