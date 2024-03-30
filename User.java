// imports here

import java.io.*;
import java.util.Scanner;

/**
 * User
 *
 * This class creates a User with a first name, a last name, an email, a bio, a username
 * a password and a file that holds users.
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

    public boolean checkFirstName(String firstName) {
        boolean checker = true;
        if (firstName == null || firstName.isEmpty()) {
            checker = false;
        } else if (firstName.contains(" ")) {
            checker = false;
        } else {
            for (int i = 0; i < firstName.length(); i++) {
                if (!Character.isLetter(firstName.charAt(i))) {
                    checker = false;
                }
            }
        }
        return checker;
    }

    public boolean checkLastName(String lastName) {
        boolean checker = true;
        if (lastName == null || lastName.isEmpty()) {
            checker = false;
        } else if (lastName.contains(" ")) {
            checker = false;
        } else {
            for (int i = 0; i < lastName.length(); i++) {
                if (!Character.isLetter(lastName.charAt(i))) {
                    checker = false;
                }
            }
        }
        return checker;
    }

    public boolean checkEmail(String email) {
        boolean checker = true;
        if (!email.contains("@") || email.contains(" ")) {
            checker = false;
        }
        return checker;
    }

    public boolean checkBio(String bio) {
        boolean checker = true;
        if (bio.length() > 40 || bio.isEmpty()) {
            checker = false;
        }
        return checker;
    }

    public boolean checkUsername(String username) {
        boolean checker = true;
        if (username.length() > 40 || username.isEmpty()) {
            checker = false;
        }
        return checker;
    }

    public boolean checkPassword(String password) {
        boolean checker = true;
        if (password.length() < 4) {
            checker = false;
        }
        return checker;
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

        } while (!goodLastName); // end of validating last name

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
        File userFile;
        String email = "";
        String firstName = "";
        String lastName = "";
        String bio = "";
        String username = "";
        String password = "";
        User user;
        // getting user info
        firstName = changeFirstName(sc);
        lastName = changeLastName(sc);
        email = changeEmail(sc);
        bio = changeBio(sc);
        username = changeUsername(sc);
        password = changePassword(sc);


        // creating user object
        user = new User(firstName, lastName, email, bio, username, password);

        // creating account file;
        userFile = new File("User_" + username + ".txt");

        // writing account file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile))) {
            bw.write(firstName + "\n");
            bw.write(lastName + "\n");
            bw.write(email + "\n");
            bw.write(bio + "\n");
            bw.write(username + "\n");
            bw.write(password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File doesn't exist!");
        }
        System.out.println("Account created successfully");
        File allUsersFile = new File("All_User_Info.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(allUsersFile, true))) {
            bw.write(username + "\n");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Was unable to write to file!");
        }

        // Create a file for the user's messages
        File userMessagesFile = new File("User_" + username + "_messages.txt");
        if (!userMessagesFile.exists()) {
            try {
                userMessagesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("File doesn't exist!");
            }
        }

        // Create a file for the user's friends
        File userFriendsFile = new File("User_" + username + "_Friends.txt");
        if (!userFriendsFile.exists()) {
            try {
                userFriendsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("File doesn't exist!");
            }
        }


        return user;

    }

    public User editAccount(Scanner sc) {
        boolean invalidOption; // true if menu option user inputs is invalid
        int editOption;  // option the user
        String newFirstName; // string to hold the new first name;
        String newLastName; // string to hold the new last name;
        String newEmail;  // string to hold the new email;
        String newBio; // string to hold the new bio
        String newUsername; // String to hold the new username;
        String newPassword;  // string to hold the new password
        User newUser = new User(this.getFirstName(), this.getLastName(), this.getEmail(), this.getBio(), this.getUsername(),
                this.getPassword()); // the new userObject;

        do {  // loop that continually asks user if they want to keep editing there account
            do {  // loop that validates what the user wants to change
                invalidOption = false;
                editOption = -1;
                System.out.println("What do you want to edit about your account? " +
                        "Please only enter a number from the options below:");

                System.out.println(
                        """
                                1. First name
                                2. Last name
                                3. Bio
                                4. Username
                                5. Password""");
                try {
                    editOption = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    invalidOption = true;
                }

                if (!invalidOption) {
                    invalidOption = editOption != 1 && editOption != 2 && editOption != 3 && editOption != 4
                            && editOption != 5;
                }

                if (invalidOption) {
                    System.out.println("ERROR! Please only enter a number from the options below!");
                }


            } while (invalidOption);

            // changing what the user wants changed
            if (editOption == 1) {
                newFirstName = changeFirstName(sc);
                newUser.setFirstName(newFirstName);
            } else if (editOption == 2) {
                newLastName = changeLastName(sc);
                newUser.setLastName(newLastName);
            } else if (editOption == 3) {
                newBio = changeBio(sc);
                newUser.setBio(newBio);
            } else if (editOption == 4) {
                newUsername = changeUsername(sc);
                newUser.setUsername(newUsername);
            } else {
                newPassword = changePassword(sc);
                newUser.setPassword(newPassword);
            }

            // validating if the user wants to continue changing there account or not
            do {
                invalidOption = false;
                editOption = -1;
                System.out.println("Do you want to continue to edit your account?");

                System.out.println(
                        """
                                1. yes
                                2. no""");
                try {
                    editOption = Integer.parseInt(sc.nextLine());
                } catch (Exception e) {
                    invalidOption = true;
                }

                if (!invalidOption) {
                    invalidOption = editOption != 1 && editOption != 2;
                }

                if (invalidOption) {
                    System.out.println("ERROR! Please only enter a number from the options below!");
                }


            } while (invalidOption);


        } while (editOption == 1);  // checking if user wants to continue to edit their account

        File userFile = new File("User_" + username + ".txt");

        // writing account file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, false))) {
            bw.write(newUser.getFirstName() + "\n");
            bw.write(newUser.getLastName() + "\n");
            bw.write(newUser.getEmail() + "\n");
            bw.write(newUser.getBio() + "\n");
            bw.write(newUser.getUsername() + "\n");
            bw.write(newUser.getPassword() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return newUser;
    }

    public void deleteAccount(Scanner scan) {
        int delete = -1; // option chosen to delete file
        boolean invalidOption; // is true if the option input is invalid

        do {
            invalidOption = false;
            System.out.println("Are you sure you want to delete your account?\n1. yes\n2. no");
            try {
                delete = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                invalidOption = true;
            }

            if (!invalidOption) {
                invalidOption = delete != 1 && delete != 2;
            }

            if (invalidOption) {
                System.out.println("ERROR!! Please enter either 1 or 2!");
            }

        } while (invalidOption);

        if (delete == 1) {
            if (this.getAccountFile().exists()) {
                this.getAccountFile().delete();
            }
            /*
            if (this instanceof Friends) {
                Friends member = (Friends) this;
                member.setMemberAppointmentsFile(new File("Member_" +
                        member.getEmail().split("@")[0] + "_appointments.txt"));
                if (member.getMemberAppointmentsFile().exists()) {
                    member.getMemberAppointmentsFile().delete();
                }
            } */
            System.out.println("Your account has been deleted");
        } else {
            System.out.println("Exiting without deleting");
        }
    }

    public User logIn(Scanner scan) {
        User user = new User();
        String[] read = new String[6];
        String passwordInput = "";
        int reEnterPasswordOption = 0;
        int reEnterEmailOption = 0;
        boolean usernameCorrect = false;
        boolean passwordCorrect = false;
        do {
            System.out.println("Please Enter your username.");
            String username = scan.nextLine();
            File f = new File("User_" + username + ".txt");
            if (f.exists()) { //username correct
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
                        passwordCorrect = true;
                        usernameCorrect = true; //log in successful
                    } else {
                        System.out.println("Your password is incorrect"); //password wrong
                    }
                    if (!passwordCorrect) {
                        System.out.println("Please re-enter your password or exit\n1.Re-enter\n2.Exit");
                        reEnterPasswordOption = Integer.parseInt(scan.nextLine());
                        if (reEnterPasswordOption == 2) {
                            return null; //quit
                        }
                    }
                } while (!usernameCorrect); //re-enter password
            } else { //username wrong
                System.out.println("The username you entered is wrong, please re-enter it or create a new account.");
                System.out.println("1.Re-enter username\n2.Create new account\n3.Exit");
                reEnterEmailOption = Integer.parseInt(scan.nextLine());
                if (reEnterEmailOption == 2) { //create new account
                    return createAccount(scan);
                } else if (reEnterEmailOption == 3) { //quit
                    return null;
                }
            }
        } while (!usernameCorrect); //re-enter email

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

}
