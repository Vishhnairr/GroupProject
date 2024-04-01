// imports here

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * User
 *
 * This class creates a User with a first name, a last name, an email, a bio, a username
 * a password and a file that holds users. This method is able to check that every input is correct.
 * It also holds the methods of creating a user, logging in to an account, and editing an account if
 * the user desires. There is also a method to check the amount of users on a created file and to
 * confirm there is more than one for the main method to use.
 *
 *  @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 *  @version March 31, 2024
 *
 */

public class User implements UserList {
    private String firstName; // holds first name of User

    private String lastName; // holds last name of User

    private String email; // holds email of User

    private String bio; // holds bio (description) of User

    private String username; // holds username of User

    private String password; // holds password of User

    private File userFile; // holds file with User's information

    // empty constructor
    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.bio = null;
        this.username = null;
        this.password = null;
        this.userFile = null;
    }

    // creates custom constructor
    public User(String firstName, String lastName, String email, String bio, String username,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.username = username;
        this.password = password;
        this.userFile = new File("User_" + this.username + ".txt");
    }

    // returns first name
    public String getFirstName() {
        return this.firstName;
    }

    // returns username name
    public String getUsername() {
        return this.username;
    }

    // returns last name
    public String getLastName() {
        return this.lastName;
    }

    // returns bio
    public String getBio() {
        return this.bio;
    }

    // returns email
    public String getEmail() {
        return this.email;
    }

    // returns password
    public String getPassword() {
        return this.password;
    }

    // returns user file
    public File getAccountFile() {
        return this.userFile;
    }

    // sets first name
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // sets last name
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // sets username
    public void setUsername(String username) {
        this.username = username;
    }

    // sets email
    public void setEmail(String email) {
        this.email = email;
    }

    // sets bio
    public void setBio(String bio) {
        this.bio = bio;
    }

    // sets password
    public void setPassword(String password) {
        this.password = password;
    }

    // sets user file
    public void setAccountFile() {
        this.userFile = new File("User_" + this.username + ".txt");
    }

    // verifies first name according to limitations
    public String checkFirstName(Scanner sc) {
        boolean checker = true;
        System.out.println("Please enter your first name:");
        String firstname = sc.nextLine();
        do {
            checker = true;
            if (firstname == null || firstname.isEmpty()) { // checking if first name is empty
                checker = false;
                System.out.println("Your first name was invalid! Please enter your first name:");
                firstname = sc.nextLine();
            } else if (firstname.contains(" ")) { //checking if first name contains space
                checker = false;
                System.out.println("Your first name was invalid! Please enter your first name:");
                firstname = sc.nextLine();
            } else {
                for (int i = 0; i < firstname.length(); i++) { // checking if first name contains other characters (not letters)
                    if (!Character.isLetter(firstname.charAt(i))) {
                        checker = false;
                    }
                }
                if (!checker) {
                    System.out.println("Your first name was invalid! Please enter your first name:");
                    firstname = sc.nextLine();
                }
            }
        } while (!checker);
        return firstname;
    }

    // verifies last name according to limitations
    public String checkLastName(Scanner sc) {
        boolean checker = true;
        System.out.println("Please enter your last name:");
        String lastname = sc.nextLine();
        do {
            checker = true;
            if (lastname == null || lastname.isEmpty()) { // checking if last name is empty
                checker = false;
                System.out.println("The last name you entered was invalid! Please enter your last name:");
                lastname = sc.nextLine();
            } else if (lastname.contains(" ")) { //checking if last name contains space
                checker = false;
                System.out.println("The last name you entered was invalid! Please enter your last name:");
                lastname = sc.nextLine();
            } else {
                for (int i = 0; i < lastname.length(); i++) { // checking if last name contains other characters (not letters)
                    if (!Character.isLetter(lastname.charAt(i))) {
                        checker = false;
                    }
                }
                if (!checker) {
                    System.out.println("The last name you entered was invalid! Please enter your last name:");
                    lastname = sc.nextLine();
                }
            }
        } while (!checker);
        return lastname;
    }

    // verifies email name according to limitations
    public String checkEmail(Scanner sc) {
        boolean checker = true;
        System.out.println("Please enter your email:");
        String email = sc.nextLine();
        do {
            checker = true;
            if (!email.contains("@") || email.contains(" ") || email.charAt(email.length() - 4) != '.') {
                // checking if email doesn't & have "@" or has a space or doesn't have a period 4 spots from end
                checker = false;
                System.out.println("Your email was invalid! Please enter your email:");
                email = sc.nextLine();
            }
        } while (!checker);
        return email;
    }

    // verifies bio according to limitations
    public String checkBio(Scanner sc) {
        boolean checker = true;
        System.out.println("Please enter your bio:");
        String bio = sc.nextLine();
        do {
            checker = true;
            if (bio.length() > 40 || bio.isEmpty()) { // checking if bio is empty or over 40 characters long
                checker = false;
                System.out.println("Please enter your bio:");
                bio = sc.nextLine();
            }
        } while (!checker);
        return bio;
    }

    // verifies username according to limitations
    public String checkUsername(Scanner sc) {
        boolean checker = true;
        File allUsersFile = new File("All_User_Info.txt");
        ArrayList<String> usernameList = new ArrayList<>();
        System.out.println("Please enter your username:");
        String username = sc.nextLine();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(allUsersFile));
            String line = bfr.readLine();
            while (line != null) { // reading all users in file
                usernameList.add(line);
                line = bfr.readLine();
            }
            do {
                checker = true;
                if (username.length() > 40 || username.isEmpty()) { // checking if username is empty or longer than 40 characters
                    checker = false;
                    System.out.println("The username you entered was invalid! Please enter your username:");
                } else if (usernameList.contains(username)) { // checking if username is already taken by a user
                    checker = false;
                    System.out.println("This email is already taken! Please choose another.");
                }
            } while (!checker);
        } catch (IOException e) {
            do {
                System.out.println("The username you entered was invalid! Please enter your username:");
                username = sc.nextLine();
                if (username.length() > 40 || username.isEmpty()) { // checking if username is empty or longer than 40 characters
                    checker = false;
                } else {
                    checker = true;
                }
            } while (!checker);
        }
        return username;
    }

    // verifies password according to limitations
    public String checkPassword(Scanner sc) {
        boolean checker = true;
        System.out.println("Please enter your password:");
        String password = sc.nextLine();
        do {
            checker = true;
            if (password.length() < 4) { // checking that password length is under 4 characters
                checker = false;
                System.out.println("The password you entered was invalid! Please enter your password:");
            }
        } while (!checker);
        return password;
    }

    // verifies if account exists for User
    public boolean checkAccountExists(String username) {
        File accountInfo2 = new File("User_" + username + ".txt");
        return accountInfo2.exists();
    }

    // changes email if User decides to do so
    public String changeEmail(Scanner sc) {
        boolean emailChecker;
        boolean existsEmail;
        boolean origExists;
        String emailOrig;
        String email;

        do {
            System.out.println("Enter your new email:"); // entering email user wants to replace it with
            email = sc.nextLine();

            if (email == null) { // checking if user's response is null
                emailChecker = false;
                System.out.println("ERROR! Please enter an email!");
            } else if (email.isEmpty()) { // checking if user's response is empty
                emailChecker = false;
            } else if (!email.contains("@") || email.contains(" ") || email.charAt(email.length() - 4) != '.') {
                // checking same qualities as earlier in the checkEmail method
                emailChecker = false;
                System.out.println("ERROR! the email you entered was incorrectly formatted.");
                System.out.println("Please enter an email of the form: ___@___.___ with no spaces!");
            } else {
                emailChecker = true;
            }

            if (emailChecker) {
                existsEmail = checkAccountExists(email);
                if (existsEmail) {
                    System.out.println("ERROR! An account with this email already exists!");
                }
            }
        } while (!emailChecker);

        return email;
    }

    // changes bio if User decides to do so
    public String changeBio(Scanner sc) {
        boolean goodBio;
        String bio;

        do {
            System.out.println("Enter a new bio for yourself:");
            bio = sc.nextLine();

            if (bio == null) {
                goodBio = false;
                System.out.println("ERROR! Please make sure your bio is between 20 to 40 characters!");
            } else if (bio.isEmpty()) {
                goodBio = false;
                System.out.println("ERROR! Please make sure your bio is between 20 to 40 characters!");
            } else if (bio.length() > 40) {
                goodBio = false;
                System.out.println("ERROR! Please make sure your bio is between 20 to 40 characters!");
            } else {
                goodBio = true;
            }

        } while (!goodBio);

        return bio;
    }

    // changes first name if User decides to do so
    public String changeFirstName(Scanner sc) {
        boolean goodFirstName;
        String firstName;

        do {
            System.out.println("Enter your first name:");
            firstName = sc.nextLine();

            if (firstName == null) {
                goodFirstName = false;
                System.out.println("ERROR! Please enter your first name!");
            } else if (firstName.isEmpty()) {
                goodFirstName = false;
                System.out.println("ERROR! Please enter your first name!");
            } else if (firstName.contains(" ")) {
                goodFirstName = false;
                System.out.println("ERROR! Only your first name with no spaces!");
            } else {
                goodFirstName = true;
            }

        } while (!goodFirstName);

        return firstName;
    }

    // changes last name if User decides to do so
    public String changeLastName(Scanner sc) {
        boolean goodLastName;
        String lastName;

        do {
            System.out.println("Enter your new last name:");
            lastName = sc.nextLine();

            if (lastName == null) {
                goodLastName = false;
                System.out.println("ERROR! Please enter your last name!");
            } else if (lastName.isEmpty()) {
                goodLastName = false;
                System.out.println("ERROR! Please enter your last name!");
            } else if (lastName.contains(" ")) {
                goodLastName = false;
                System.out.println("ERROR! Only your last name with no spaces!");
            } else {
                goodLastName = true;
            }

        } while (!goodLastName);

        return lastName;
    }

    // changes username if User decides to do so
    public String changeUsername(Scanner sc) {
        boolean goodUsername;
        String username;

        do {
            System.out.println("Enter your new username:");
            username = sc.nextLine();

            if (username == null) {
                goodUsername = false;
                System.out.println("ERROR! Please enter your username!");
            } else if (username.contains(" ")) {
                goodUsername = false;
                System.out.println("ERROR! There should be no spaces in your username!");
            } else {
                goodUsername = true;
            }

        } while (!goodUsername);

        return username;
    }

    // changes password if User decides to do so
    public String changePassword(Scanner sc) {
        boolean goodPassword;
        String password;

        do {
            System.out.println("Enter a new password:");
            password = sc.nextLine();

            if (password.length() < 4) {
                goodPassword = false;
                System.out.println("ERROR! Your password must be at least 4 characters long!");
            } else {
                goodPassword = true;
            }

        } while (!goodPassword);

        return password;
    }

    // creates new account based on info from scanner in main method
    public User createAccount(Scanner sc) {
        String firstName = checkFirstName(sc);
        String lastName = checkLastName(sc);
        String email = checkEmail(sc);
        String bio = checkBio(sc);
        String username = checkUsername(sc);
        String password = checkPassword(sc);

        User user = new User(firstName, lastName, email, bio, username, password);

        File userFile = new File("User_" + username + ".txt");

        // writing account file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile))) {
            bw.write(firstName + "\n");
            bw.write(lastName + "\n");
            bw.write(email + "\n");
            bw.write(bio + "\n");
            bw.write(username + "\n");
            bw.write(password + "\n");
        } catch (IOException e) {
            System.out.println("File doesn't exist!");
        }
        System.out.println("Account created successfully");

        File allUsersFile = new File("All_User_Info.txt"); // creates file with all current existing users
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(allUsersFile, true))) {
            bw.write(username + "\n");
        } catch (IOException e) {
            System.out.println("Was unable to write to file!");
        }

        File userFriendsFile = new File("User_" + username + "_Friends.txt"); // creates file with user's friends
        if (!userFriendsFile.exists()) {
            try {
                userFriendsFile.createNewFile();
            } catch (IOException e) {
                System.out.println("File doesn't exist!");
            }
        }

        Friends member = new Friends(user.getFirstName(), user.getLastName(), user.getEmail(), user.getBio(), user.getUsername(),
                user.getPassword()); // creates new friends object

        return member;

    }

    // edits existing account based on info from scanner in main method
    public User editAccount(Scanner sc) {
        boolean goodOption;
        int editOption;
        String newFirstName;
        String newLastName;
        String newEmail;
        String newBio;
        String newUsername;
        String newPassword;

        User newUser = new User(this.getFirstName(), this.getLastName(), this.getEmail(), this.getBio(), this.getUsername(),
                this.getPassword()); // creates current user

        do {
            do {
                goodOption = true;
                editOption = -1;
                System.out.println("What do you want to edit about your account? " +
                        "Please only enter a number from the options below:");

                System.out.println("1. First name" + "\n" + "2. Last name" + "\n" + "3. Bio" + "\n" + "4. Email"
                        + "\n" + "5. Username" + "\n" + "6. Password");

                try {
                    editOption = Integer.parseInt(sc.nextLine());
                    // edge case where user doesn't enter an existing number
                    if (editOption != 1 && editOption != 2 && editOption != 3 && editOption != 4 && editOption != 5 &&
                            editOption != 6) {
                        goodOption = false;
                        System.out.println("ERROR! Please only enter a number from the options below!");
                    }
                } catch (NumberFormatException e) { // used when user doesn't input a number at all
                    goodOption = false;
                    System.out.println("ERROR! Please only enter a number from the options below!");
                }
            } while (!goodOption);

            // editOption variable will be checked before it can enter the switch statements
            switch (editOption) {
                case 1: {
                    newFirstName = changeFirstName(sc); // changes first name
                    newUser.setFirstName(newFirstName); // sets new first name to permanent first name
                    break;
                }
                case 2: {
                    newLastName = changeLastName(sc); // changes last name
                    newUser.setLastName(newLastName); // sets new last name to permanent last name
                    break;
                }
                case 3: {
                    newBio = changeBio(sc); // changes bio
                    newUser.setBio(newBio); // sets new bio to permanent bio
                    break;
                }
                case 4: {
                    newEmail = changeEmail(sc); // changes email
                    newUser.setEmail(newEmail); // sets new email to permanent email
                    break;
                }
                case 5: {
                    newUsername = changeUsername(sc); // changes username
                    newUser.setUsername(newUsername); // sets new username to permanent username
                    break;
                }
                case 6: {
                    newPassword = changePassword(sc); // changes password
                    newUser.setPassword(newPassword); // sets new password to permanent password
                    break;
                }
            }

            do {
                goodOption = true;
                editOption = -1;
                System.out.println("Do you want to continue to edit your account?");
                System.out.println("1. Yes" + "\n" + "2. No");

                try {
                    editOption = Integer.parseInt(sc.nextLine());
                    if (editOption != 1 && editOption != 2) { // verifies number is either 1 or 2
                        goodOption = false;
                        System.out.println("ERROR! Please only enter a number from the options below!");
                    }
                } catch (NumberFormatException e) { // verifies input is a number
                    goodOption = false;
                    System.out.println("ERROR! Please only enter a number from the options below!");
                }
            } while (!goodOption); // loops until input is correct


        } while (editOption == 1); // repeats entire process if user wants to continue editing their account

        File userFile = new File("User_" + username + ".txt"); // creates new file with new username

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, false))) { // writes new information to file
            bw.write(newUser.getFirstName() + "\n");
            bw.write(newUser.getLastName() + "\n");
            bw.write(newUser.getEmail() + "\n");
            bw.write(newUser.getBio() + "\n");
            bw.write(newUser.getUsername() + "\n");
            bw.write(newUser.getPassword() + "\n");
        } catch (IOException e) {
            System.out.println("Unable to write to file!");
        }
        return newUser;
    }

    // verifies login based on info from scanner in main method
    public User logIn(Scanner scan) {
        User user = new User();
        String[] read = new String[6];
        String passwordInput = "";
        int reEnterPasswordOption = 0;
        int reEnterEmailOption = 0;
        boolean usernameCorrect = false;
        do {
            System.out.println("Please Enter your username.");
            String username = scan.nextLine(); // verifies username exists
            File f = new File("User_" + username + ".txt");
            if (f.exists()) { // verifies account exists
                try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                    for (int i = 0; i < 6; i++) {
                        read[i] = bfr.readLine(); // reads through all account information
                    }
                } catch (IOException e) {
                    System.out.println("Error");
                }
                // splits up account information and sets this user to that information
                user.setFirstName(read[0]);
                user.setLastName(read[1]);
                user.setEmail(read[2]);
                user.setUsername(read[3]);
                user.setBio(read[4]);
                user.setPassword(read[5]);
                do {
                    System.out.println("Please enter your password.");
                    passwordInput = scan.nextLine();
                    if (passwordInput.equals(read[5])) { // making sure password input matches the password saves on file
                        System.out.println("Log in successful");
                        usernameCorrect = true;
                    } else {
                        System.out.println("Your password is incorrect");
                        do { // loops password until it is entered correctly or the user quits
                            try {
                                System.out.println("Please re-enter your password or exit\n1.Re-enter\n2.Exit");
                                reEnterPasswordOption = Integer.parseInt(scan.nextLine());
                                switch (reEnterPasswordOption) {
                                    case 1: {
                                        usernameCorrect = false;
                                        break;
                                    }
                                    case 2: {
                                        return null;
                                    }
                                    default: { // what happens when number entered is not a 1 or 2
                                        System.out.println("Please choose one of the options!");
                                        System.out.println("Please re-enter your password or exit\n1.Re-enter\n2.Exit");
                                        reEnterPasswordOption = Integer.parseInt(scan.nextLine());
                                    }
                                }
                            } catch (InputMismatchException | NumberFormatException e) { // checking if what is entered is not a number
                                System.out.println("Please choose one of the options!");
                                System.out.println("Please re-enter your password or exit\n1.Re-enter\n2.Exit");
                            }
                        } while (reEnterPasswordOption != 1 && reEnterPasswordOption != 2); // loops until number entered is an option
                    }
                } while (!usernameCorrect); // loops until password and username both exist in same account
            } else {
                do {
                    try {
                        System.out.println("The username you entered is wrong, please re-enter it or create a new account.");
                        System.out.println("1. Re-enter username\n2. Create Account\n3. Exit");
                        reEnterEmailOption = Integer.parseInt(scan.nextLine());
                        switch (reEnterEmailOption) {
                            case 1: { // makes program loop to enter username again
                                usernameCorrect = false;
                                break;
                            }
                            case 2: { // create a new account and exists this method
                                createAccount(scan);
                                return null;
                            }
                            case 3: { // ends program and exits
                                return null;
                            }
                            default: { // what happens when 1, 2, or 3 is not entered
                                System.out.println("Please print a valid number!");
                                System.out.println("The username you entered is wrong, please re-enter it or create a new account.");
                                System.out.println("1. Re-enter username\n2. Exit");
                                reEnterEmailOption = Integer.parseInt(scan.nextLine());
                            }
                        }
                    } catch (NumberFormatException e) { // what is entered when user doesn't enter a number
                        System.out.println("Please print a number!");
                        System.out.println("1. Re-enter username\n2. Create Account\n3.Exit");
                    }
                } while (reEnterEmailOption != 1); // if user doesn't enter 1, the username input will not loop
            }
        } while (!usernameCorrect); // loops until username entered actually exists

        Friends member = new Friends(user.getFirstName(), user.getLastName(), user.getEmail(), user.getBio(), user.getUsername(),
                user.getPassword()); // creates new friends object

        return member;
    }

    // formats User's information in their own file
    public String toString() {
        return firstName + "\n"
                + lastName + "\n"
                + email + "\n"
                + bio + "\n"
                + username + "\n"
                + password;
    }

    // checks if more than one user exists on the "All_User_Info.txt" file
    public static boolean checkMoreOneUser() {
        File allUsersFile = new File("All_User_Info.txt");
        ArrayList<String> users = new ArrayList<>();
        if (allUsersFile.exists()) { // there are users to look at
            String fileLine;
            try (BufferedReader br = new BufferedReader(new FileReader(allUsersFile))) {
                fileLine = br.readLine();
                while (fileLine != null && !fileLine.trim().equals("")) {
                    users.add(fileLine.trim());
                    fileLine = br.readLine();
                }
            } catch (IOException e) {
                return false; // Consider the check failed in case of an exception
            }
            return users.size() > 1; // Return true if more than one user exists
        } else {
            return false; // File doesn't exist, so definitely less than two users
        }
    }

}