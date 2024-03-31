// imports here

import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * User
 *
 * This class creates a User with a first name, a last name, an email, a bio, a username
 * a password and a file that holds users.
 *
 */

public class User {
    private String firstName;

    private String lastName;

    private String email;

    private String bio;

    private String username;

    private String password;

    private File userFile;

    public User() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
        this.bio = null;
        this.username = null;
        this.password = null;
        this.userFile = null;
    }

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

    public String getFirstName() {
        return this.firstName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getBio() {
        return this.bio;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public File getAccountFile() {
        return this.userFile;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountFile() {
        this.userFile = new File("User_" + this.username + ".txt");
    }

    public String checkFirstName(Scanner sc) {
        boolean checker = true;
        String firstname = null;
        do {
            System.out.println("Please enter your first name:");
            firstname = sc.nextLine();
            if (firstname == null || firstname.isEmpty()) {
                checker = false;
            } else if (firstname.contains(" ")) {
                checker = false;
            } else {
                for (int i = 0; i < firstname.length(); i++) {
                    if (!Character.isLetter(firstname.charAt(i))) {
                        checker = false;
                    }
                }
            }
        } while (!checker);
        return firstname;
    }

    public String checkLastName(Scanner sc) {
        boolean checker = true;
        String lastname = null;
        int verify = 0;
        do {
            System.out.println("Please enter your last name:");
            lastname = sc.nextLine();
            if (lastname == null || lastname.isEmpty()) {
                checker = false;
            } else if (lastname.contains(" ")) {
                checker = false;
            } else {
                for (int i = 0; i < lastname.length(); i++) {
                    if (!Character.isLetter(lastname.charAt(i))) {
                        checker = false;
                        verify = 1;
                    }
                }
                if (verify != 1) {
                    checker = true;
                }
            }
        } while (!checker);
        return lastname;
    }

    public String checkEmail(Scanner sc) {
        boolean checker = true;
        String email = null;
        do {
            System.out.println("Please enter your email:");
            email = sc.nextLine();
            if (!email.contains("@") || email.contains(" ")) {
                checker = false;
            } else {
                checker = true;
            }
        } while (!checker);
        return email;
    }

    public String checkBio(Scanner sc) {
        boolean checker = true;
        String bio = null;
        do {
            System.out.println("Please enter your bio:");
            bio = sc.nextLine();
            if (bio.length() > 40 || bio.isEmpty()) {
                checker = false;
            } else {
                checker = true;
            }
        } while (!checker);
        return bio;
    }

    public String checkUsername(Scanner sc) {
        boolean checker = true;
        String username = null;
        File allUsersFile = new File("All_User_Info.txt");
        ArrayList<String> usernameList = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(allUsersFile));
            String line = bfr.readLine();
            while (line != null) {
                usernameList.add(line);
                line = bfr.readLine();
            }
            do {
                System.out.println("Please enter your username:");
                username = sc.nextLine();
                if (username.length() > 40 || username.isEmpty()) {
                    checker = false;
                } else if (usernameList.contains(username)) {
                    checker = false;
                    System.out.println("This email is already taken! Please choose another.");
                } else {
                    checker = true;
                }
            } while (!checker);
        } catch (IOException e) {
            System.out.println("File doesn't exist!");
        }
        return username;
    }

    public String checkPassword(Scanner sc) {
        boolean checker = true;
        String password = null;
        do {
            System.out.println("Please enter your password:");
            password = sc.nextLine();
            if (password.length() < 4) {
                checker = false;
            } else {
                checker = true;
            }
        } while (!checker);
        return password;
    }

    public boolean checkAccountExists(String username) {
        File accountInfo2 = new File("User_" + username);
        return accountInfo2.exists();
    }

    public String changeEmail(Scanner sc) {
        boolean emailChecker;
        boolean existsEmail;
        boolean origExists;
        String emailOrig;
        String email;

        do {
            do {
                System.out.println("Enter your email:");
                emailOrig = sc.nextLine();
                origExists = checkAccountExists(emailOrig);
                if (!origExists) {
                    System.out.println("This account does not exist.");
                    System.out.println("Enter your email:");
                }
            } while (origExists);

            System.out.println("Enter your new email:");
            email = sc.nextLine();

            if (email == null) {
                emailChecker = false;
                System.out.println("ERROR! Please enter an email!");
            } else if (email.isEmpty()) {
                emailChecker = false;
            } else if (email.split(" ").length > 1 || email.split("@").length != 2
                    || email.split("@")[1].split("\\.").length != 2) {
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

    public String changeLastName(Scanner sc) {
        boolean goodLastName;
        String lastName;

        do {
            System.out.println("Enter your last name:");
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

    public String changeUsername(Scanner sc) {
        boolean goodUsername;
        String username;

        do {
            System.out.println("Enter your username:");
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

    public String changePassword(Scanner sc) {
        boolean goodPassword;
        String password;

        do {
            System.out.println("Enter a password:");
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

        File allUsersFile = new File("All_User_Info.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(allUsersFile, true))) {
            bw.write(username + "\n");
        } catch (IOException e) {
            System.out.println("Was unable to write to file!");
        }

        File userMessagesFile = new File("User_" + username + "_messages.txt");
        if (!userMessagesFile.exists()) {
            try {
                userMessagesFile.createNewFile();
            } catch (IOException e) {
                System.out.println("File doesn't exist!");
            }
        }

        File userFriendsFile = new File("User_" + username + "_Friends.txt");
        if (!userFriendsFile.exists()) {
            try {
                userFriendsFile.createNewFile();
            } catch (IOException e) {
                System.out.println("File doesn't exist!");
            }
        }

        return user;

    }

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
                this.getPassword());

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
                    if (editOption != 1 && editOption != 2 && editOption != 3 && editOption != 4 && editOption != 5 &&
                        editOption != 6) {
                        goodOption = false;
                        System.out.println("ERROR! Please only enter a number from the options below!");
                    }
                } catch (NumberFormatException e) {
                    goodOption = false;
                    System.out.println("ERROR! Please only enter a number from the options below!");
                }
            } while (!goodOption);

            switch (editOption) {
                case 1: {
                    newFirstName = changeFirstName(sc);
                    newUser.setFirstName(newFirstName);
                    break;
                }
                case 2: {
                    newLastName = changeLastName(sc);
                    newUser.setLastName(newLastName);
                    break;
                }
                case 3: {
                    newBio = changeBio(sc);
                    newUser.setBio(newBio);
                    break;
                }
                case 4: {
                    newEmail = changeEmail(sc);
                    newUser.setEmail(newEmail);
                    break;
                }
                case 5: {
                    newUsername = changeUsername(sc);
                    newUser.setUsername(newUsername);
                    break;
                }
                case 6: {
                    newPassword = changePassword(sc);
                    newUser.setPassword(newPassword);
                    break;
                }
                default: {
                    System.out.println("ERROR! Please only enter a number from the options below!");
                    System.out.println("1. First name" + "\n" + "2. Last name" + "\n" + "3. Bio" + "\n" + "4. Email"
                            + "\n" + "5. Username" + "\n" + "6. Password");
                    editOption = Integer.parseInt(sc.nextLine());
                    }
                }

            do {
                goodOption = true;
                editOption = -1;
                System.out.println("Do you want to continue to edit your account?");
                System.out.println("1. Yes" + "\n" + "2. No");

                try {
                    editOption = Integer.parseInt(sc.nextLine());
                    if (editOption != 1 && editOption != 2) {
                        goodOption = false;
                        System.out.println("ERROR! Please only enter a number from the options below!");
                    }
                } catch (NumberFormatException e) {
                    goodOption = false;
                    System.out.println("ERROR! Please only enter a number from the options below!");
                }
            } while (!goodOption);


        } while (editOption == 1);

        File userFile = new File("User_" + username + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, false))) {
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

    public User logIn(Scanner scan) {
        User user = new User();
        String[] read = new String[6];
        String passwordInput = "";
        int reEnterPasswordOption = 0;
        int reEnterEmailOption = 0;
        boolean usernameCorrect = false;
        do {
            System.out.println("Please Enter your username.");
            String username = scan.nextLine();
            File f = new File("User_" + username + ".txt");
            if (f.exists()) {
                try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                    for (int i = 0; i < 6; i++) {
                        read[i] = bfr.readLine();
                    }
                } catch (IOException e) {
                    System.out.println("Error");
                }
                user.setFirstName(read[0]);
                user.setLastName(read[1]);
                user.setEmail(read[2]);
                user.setUsername(read[3]);
                user.setBio(read[4]);
                user.setPassword(read[5]);
                do {
                    System.out.println("Please enter your password.");
                    passwordInput = scan.nextLine();
                    if (passwordInput.equals(read[5])) {
                        System.out.println("Log in successful");
                        usernameCorrect = true;
                    } else {
                        System.out.println("Your password is incorrect");
                        do {
                            try {
                                System.out.println("Please re-enter your password or exit\n1.Re-enter\n2.Exit");
                                reEnterPasswordOption = Integer.parseInt(scan.nextLine());
                                switch (reEnterPasswordOption) {
                                    case 1: {
                                        usernameCorrect = false;
                                        break;
                                    }
                                    case 2: {
                                        System.out.println("Thank you for using BoilerTown! Have a great day!");
                                        return null;
                                    }
                                    default: {
                                        System.out.println("Please choose one of the options!");
                                        System.out.println("Please re-enter your password or exit\n1.Re-enter\n2.Exit");
                                        reEnterPasswordOption = Integer.parseInt(scan.nextLine());
                                    }
                                }
                            } catch (InputMismatchException | NumberFormatException e) {
                                System.out.println("Please choose one of the options!");
                            }
                        } while (reEnterPasswordOption != 1 && reEnterPasswordOption != 2);
                    }
                } while (!usernameCorrect);
            } else {
                do {
                    try {
                        System.out.println("The username you entered is wrong, please re-enter it or create a new account.");
                        System.out.println("1. Re-enter username\n2. Exit");
                        reEnterEmailOption = Integer.parseInt(scan.nextLine());
                        switch (reEnterEmailOption) {
                            case 1: {
                                usernameCorrect = false;
                                break;
                            }
                            case 2: {
                                System.out.println("Thank you for using BoilerTown! Have a great day!");
                                return null;
                            }
                            default: {
                                System.out.println("Please print a valid number!");
                                System.out.println("The username you entered is wrong, please re-enter it or create a new account.");
                                System.out.println("1. Re-enter username\n2. Exit");
                                reEnterEmailOption = Integer.parseInt(scan.nextLine());
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please print a number!");
                    }
                } while (reEnterEmailOption != 1);
            }
        } while (!usernameCorrect);

        Friends member = new Friends(user.getFirstName(), user.getLastName(), user.getEmail(), user.getBio(), user.getUsername(),
                user.getPassword());

        return member;
    }

    public String toString() {
        return firstName + "\n"
                + lastName + "\n"
                + email + "\n"
                + bio + "\n"
                + username + "\n"
                + password;
    }

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
            } catch (Exception e) {
                return false; // Consider the check failed in case of an exception
            }
            return users.size() > 1; // Return true if more than one user exists
        } else {
            return false; // File doesn't exist, so definitely less than two users
        }
    }

}

