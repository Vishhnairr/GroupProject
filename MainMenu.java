import java.util.*;
import java.io.*;

/**
 * Project 4 - MainMenu
 * <p>
 * Holds all the functionality for users to interact with the system. First a welcome
 * messages is displayed and then the user logs in or creates an account.
 * Then depending on what user type the user is the menu gives them different options. The menu allows the user
 * to loop through and perform as many actions as they want, and then when they are done they exit. Once they exit,
 * the menu prompts them if they want to make any edits to their account before they log out, and they can make as
 * many edits as they like or delete their account. After the user is done editing, a goodbye message is displayed
 * and the program ends.
 */

public class MainMenu {

    // main method
    public static void main(String[] args) {
        // start
        Scanner scanner = new Scanner(System.in);
        boolean stay = true;
        User user = new User();
        System.out.println("Welcome to BoilerTown!");
        System.out.println("Would you like to create an account or log-in?");
        System.out.println("1. Create an account");
        System.out.println("2. Log-in");
        System.out.println("3. Exit");
        int userChoice = scanner.nextInt();
        scanner.nextLine();
        if (userChoice == 1) {  // user creates account
            user = user.createAccount(scanner);
        } else if (userChoice == 2) {  // user logs in
            user = user.logIn(scanner);
        } else {  // user wants to exit
            stay = false;
        }

        while (stay) {  // loops while user wants to do things
            if (user instanceof Friends) { // customer code
                Friends customer = (Friends) user;
                int customerChoice;
                System.out.println("What would you like to do?");
                System.out.println("""
                        1. View all BoilerTown users
                        2. Search BoilerTown users
                        3. View/Interact with your friends
                        4. Exit""");
                customerChoice = scanner.nextInt();
                scanner.nextLine();

                if (customerChoice == 1) {  // looking at all the users
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
                            e.printStackTrace();
                        }

                        // printing out the stores the seller has
                        System.out.println("List of all BoilerTown Users:");
                        for (int i = 0; i < users.size(); i++) {
                            System.out.println(users.get(i));
                        }

                        // get the store the user wants to change
                        String selectedUserName;
                        do {
                            System.out.println("Please enter the name of the user you want to look into:");

                            selectedUserName = scanner.nextLine();

                            if (!users.contains(selectedUserName)) {
                                System.out.println("ERROR! The username you entered is not a current user!");
                            }
                        } while (!users.contains(selectedUserName)); // store name is now valid

                        System.out.println("What do you want to do?");
                        System.out.printf("""
                                1. View %s's Bio
                                2. Make a friend request
                                3. Block %s
                                4. Exit
                                """, selectedUserName, selectedUserName);

                        customerChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (customerChoice == 1) {  // looking up a bio for a user
                            String line;
                            File f = new File("User_" + selectedUserName + ".txt");
                            if (f.exists()) {
                                try {
                                    BufferedReader bfr = new BufferedReader(new FileReader(f));
                                    int lineNumber = 0;
                                    String bio = "";
                                    String username = "";
                                    while ((line = bfr.readLine()) != null) {
                                        lineNumber++;
                                        // Store the 4th line (bio)
                                        if (lineNumber == 4) {
                                            bio = line;
                                        }
                                        // Store the 5th line (username)
                                        if (lineNumber == 5) {
                                            username = line;
                                        }
                                    }
                                    System.out.println(username);
                                    System.out.println(bio);
                                    bfr.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("User file doesn't exist.");
                            }
                        } else if (customerChoice == 2) { // make a friend request
                            System.out.printf("What would you like to say to %s?\n", selectedUserName);
                            String message = scanner.nextLine();
                            File friendsFile = new File("User_" + selectedUserName + "_Friends.txt");
                            File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
                            if (!friendsFile.exists()) {
                                System.out.printf("%s has blocked you or does not exist.\n", selectedUserName);
                            } else {
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(friendsFile, true))) {
                                    bw.write("Friend request from: " + user.getUsername() + ", " + message + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
                                    bw.write("You sent a friend request to: " + selectedUserName + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.printf("Friend request to user %s has been sent successfully!\n", selectedUserName);
                            }
                        } else if (customerChoice == 3) {  // block a user
                            File friendsFile = new File("User_" + selectedUserName + "_Friends.txt");
                            File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(friendsFile, true))) {
                                bw.write("You have been blocked by: " + user.getUsername() + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {

                                bw.write("You blocked: " + selectedUserName + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.printf("User %s has been successfully blocked.\n", selectedUserName);
                        }
                    }
                } else if (customerChoice == 2) { // view
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
                            e.printStackTrace();
                        }

                        // get the store the user wants to change
                        String selectedUserName;
                        do {
                            System.out.println("Please enter the name of the user you want to look into:");

                            selectedUserName = scanner.nextLine();

                            if (!users.contains(selectedUserName)) {
                                System.out.println("ERROR! The username you entered is not a current user!");
                            }
                            if (users.contains(selectedUserName)) {
                                System.out.println("User Found!");
                            }
                        } while (!users.contains(selectedUserName)); // store name is now valid

                        System.out.println("What do you want to do?");
                        System.out.printf("""
                                1. View %s's Bio
                                2. Make a friend request
                                3. Block %s
                                4. Exit
                                """, selectedUserName, selectedUserName);

                        customerChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (customerChoice == 1) {  // looking up a bio for a user
                            String line;
                            File f = new File("User_" + selectedUserName + ".txt");
                            if (f.exists()) {
                                try {
                                    BufferedReader bfr = new BufferedReader(new FileReader(f));
                                    int lineNumber = 0;
                                    String bio = "";
                                    String username = "";
                                    while ((line = bfr.readLine()) != null) {
                                        lineNumber++;
                                        // Store the 4th line (bio)
                                        if (lineNumber == 4) {
                                            bio = line;
                                        }
                                        // Store the 5th line (username)
                                        if (lineNumber == 5) {
                                            username = line;
                                        }
                                    }
                                    System.out.println(username);
                                    System.out.println(bio);
                                    bfr.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("User file doesn't exist.");
                            }
                        } else if (customerChoice == 2) { // make a friend request
                            System.out.printf("What would you like to say to %s?\n", selectedUserName);
                            String message = scanner.nextLine();
                            File friendsFile = new File("User_" + selectedUserName + "_Friends.txt");
                            File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
                            if (!friendsFile.exists()) {
                                System.out.printf("%s has blocked you or does not exist.\n", selectedUserName);
                            } else {
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(friendsFile, true))) {
                                    bw.write("Friend request from: " + user.getUsername() + ", " + message + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
                                    bw.write("You sent a friend request to: " + selectedUserName + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.printf("Friend request to user %s has been sent successfully!\n", selectedUserName);
                            }
                        } else if (customerChoice == 3) {  // block a user
                            File friendsFile = new File("User_" + selectedUserName + "_Friends.txt");
                            File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(friendsFile, true))) {
                                bw.write("You have been blocked by: " + user.getUsername() + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {

                                bw.write("You blocked: " + selectedUserName + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.printf("User %s has been successfully blocked.\n", selectedUserName);
                        }
                    }
                } else if (customerChoice == 3) {  // View all your friends
                    File allUsersFile = new File("User_" + user.getUsername() + "_Friends.txt");
                    if (!allUsersFile.exists()) {
                        System.out.println("ERROR! you have not added any friends yet!");
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
                            e.printStackTrace();
                        }


                        System.out.println("What do you want to do?");
                        System.out.println("""
                                1. View my friends
                                2. View my friend requests
                                3. Make a friend request
                                4. Remove a friend
                                5. Block a friend
                                6. Exit""");

                        customerChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (customerChoice == 1) {  // view
                            File f = new File("User_" + user.getUsername() + "_Friends.txt");
                            if (f.exists()) {
                                try {
                                    String line;
                                    BufferedReader bfr = new BufferedReader(new FileReader(f));
                                    while ((line = bfr.readLine()) != null) {
                                        // Check if the line indicates a confirmed friendship
                                        if (line.endsWith("is your friend!")) {
                                            System.out.println(line);
                                        }
                                    }
                                    bfr.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("User file doesn't exist.");
                                }
                                System.out.println("Who do u want to talk to?");
                                String selectedUserName = scanner.nextLine();
                                System.out.println("""
                                        1. View my friends
                                        2. View my friend requests
                                        3. Make a friend request
                                        4. Remove a friend
                                        5. Block a friend
                                        6. Exit""");

                                customerChoice = scanner.nextInt();
                                scanner.nextLine();
                            }

                        } else if (customerChoice == 2) { // view friend request
                            File friendsFile = new File("User_" + user.getUsername() + "_Friends.txt");
                            try (BufferedReader br = new BufferedReader(new FileReader(friendsFile))) {
                                String line;
                                while ((line = br.readLine()) != null) {
                                    if (line.startsWith("Friend request from:")) {
                                        System.out.println(line);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Do you want to approve or decline a friend request? (approve/decline)");
                            String action = scanner.nextLine().trim();
                            if (!action.equals("approve") && !action.equals("decline")) {
                                System.out.println("Invalid action.");
                                return;
                            }
                            System.out.println("Enter the username of the friend request:");
                            String friendUsername = scanner.nextLine();
                            if (action.equals("approve")) {
                                File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
                                Friends j = new Friends();
                                j.updateFriendStatus(user.getUsername(), scanner);
                            }
                            if (action.equals("decline")) {
                                File userFile = new File("User_" + user.getUsername() + "_Friends.txt");
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, true))) {
                                    bw.write("You declined" + friendUsername + "'s friend request." + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                File declineFriendFile = new File("User_" + friendUsername + "_Friends.txt");
                                try (BufferedWriter bw = new BufferedWriter(new FileWriter(declineFriendFile, true))) {
                                    bw.write(user.getUsername() + "declined your friend request." + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        } else if (customerChoice == 3) { // make friend request
                            System.out.println("Who do u want to talk to?");
                            String selectedUserName = scanner.nextLine();
                            //check if user exists
                            System.out.println("Enter your message:");
                            String message = scanner.nextLine();
                            File receiverFile = new File("User_" + user.getUsername() + "_Friends.txt");
                            try (BufferedWriter bw = new BufferedWriter(new FileWriter(receiverFile, true))) {
                                bw.write("Friend request from: " + user.getUsername() + ", " + message + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("Friend request sent to " + selectedUserName);
                        } else if (customerChoice == 4) { // remove a friend

                        } else if (customerChoice == 5) { // block a friend
                        }
                    }

                    System.out.println("Do you want to continue?");
                    System.out.println("""
                            1. Yes
                            2. No""");

                    userChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (userChoice == 2) {
                        stay = false;
                    }

                    if (!stay) {
                        System.out.println("Do you want to make any edits to your account info before you logout?");
                        System.out.println("""
                                1. Yes
                                2. No""");

                        userChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (userChoice == 1) {
                            System.out.println("What kind of edits do you want to make to your account?");
                            System.out.println("""
                                    1. edit your info
                                    2. delete your account
                                    3. Exit""");

                            userChoice = scanner.nextInt();
                            scanner.nextLine();

                            if (userChoice == 1) {
                                user.editAccount(scanner);
                            } else if (userChoice == 2) {
                                user.deleteAccount(scanner);
                            }
                        }
                    }
                }

                System.out.println("Goodbye! You have been logged out.");
            }
        }
    }
}
