// imports here

import java.io.*;
import java.util.ArrayList;

/**
 * User
 * <p>
 * This class creates a User with a first name, a last name, an email, a bio, a username
 * a password and a file that holds users. This method is able to check that every input is correct.
 * It also holds the methods of creating a user, logging in to an account, and editing an account if
 * the user desires. There is also a method to check the amount of users on a created file and to
 * confirm there is more than one for the main method to use.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 * @version March 31, 2024
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

    // verifies if account exists for User
    public boolean checkAccountExists(String username) {
        File accountInfo2 = new File("User_" + username + ".txt");
        return accountInfo2.exists();
    }

    // checks email if User decides to do so
    public boolean checkEmail(String email) {
        if (email == null || email.isEmpty() || !email.contains("@") || email.contains(" ") || email.charAt(email.length() - 4) != '.') {
            System.out.println("ERROR! the email you entered was incorrectly formatted.");
            System.out.println("Please enter an email of the form: ___@___.___ with no spaces!");
            return false;
        } else if (checkAccountExists(email)) { // Assuming `checkAccountExists` is a method you have defined to check if an account with the email already exists.
            System.out.println("ERROR! An account with this email already exists!");
            return false;
        }
        return true;
    }

    // checks bio if User decides to do so
    public boolean checkBio(String bio) {
        if (bio == null) {
            System.out.println("ERROR! Please make sure your bio is between 20 to 40 characters!");
            return false;
        } else if (bio.isEmpty()) {
            System.out.println("ERROR! Please make sure your bio is between 20 to 40 characters!");
            return false;
        } else if (bio.length() > 40) {
            System.out.println("ERROR! Please make sure your bio is between 20 to 40 characters!");
            return false;
        } else {
            return true;
        }
    }

    // checks first name if User decides to do so
    public boolean checkFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty() || firstName.contains(" ")) {
            System.out.println("ERROR! Please enter your first name correctly, without spaces!");
            return false;
        }
        return true;
    }

    // checks last name if User decides to do so
    public boolean checkLastName(String lastName) {
        if (lastName == null || lastName.isEmpty() || lastName.contains(" ")) {
            System.out.println("ERROR! Please enter your last name correctly, without spaces!");
            return false;
        }
        return true;
    }

    // checks username if User decides to do so
    public boolean checkUsername(String username) {
        if (username == null || username.isEmpty() || username.contains(" ")) {
            System.out.println("ERROR! There should be no spaces in your username!");
            return false;
        }
        return true;
    }

    // checks password if User decides to do so
    public boolean checkPassword(String password) {
        if (password.length() < 4) {
            System.out.println("ERROR! Your password must be at least 4 characters long!");
            return false;
        }
        return true;
    }

    // creates new account based on info from scanner in main method
    public User createAccount(String firstName, String lastName, String email, String bio, String username, String password) {
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

        File allUsersFile = new File("All_User_Info.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(allUsersFile, true))) {
            bw.write(username + "\n");
        } catch (IOException e) {
            System.out.println("Was unable to write to file!");
        }

        File userFriendsFile = new File("User_" + username + "_Friends.txt");
        if (!userFriendsFile.exists()) {
            try {
                userFriendsFile.createNewFile();
            } catch (IOException e) {
                System.out.println("File doesn't exist!");
            }
        }

        Friends member = new Friends(firstName, lastName, email, bio, username, password);
        return member;
    }

    // edits existing account based on info from scanner in main method
    public User editAccount(int editOption, String newValue) {
        User newUser = new User(this.getFirstName(), this.getLastName(), this.getEmail(),
                this.getBio(), this.getUsername(), this.getPassword());
        // Assuming there are methods to validate the newValue based on the editOption
        // For example, if editOption is 1, newValue is validated as a valid first name.

        switch (editOption) {
            case 1:
                this.setFirstName(newValue);
                break;
            case 2:
                this.setLastName(newValue);
                break;
            case 3:
                this.setBio(newValue);
                break;
            case 4:
                this.setEmail(newValue);
                break;
            case 5:
                this.setPassword(newValue);
                break;
            default:
                System.out.println("Invalid option.");
                return this;  // Return the current object without changes.
        }

        // Update the file with new user information.
        // Assuming the filename format and structure from your original method.
        File userFile = new File("User_" + this.getUsername() + ".txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFile, false))) {
            bw.write(this.getFirstName() + "\n");
            bw.write(this.getLastName() + "\n");
            bw.write(this.getEmail() + "\n");
            bw.write(this.getBio() + "\n");
            bw.write(this.getUsername() + "\n");
            bw.write(this.getPassword() + "\n");
        } catch (IOException e) {
            System.out.println("Unable to write to file!");
        }

        return newUser;
    }

    // verifies login based on info from scanner in main method
    public User logIn(String username, String passwordInput) {
        User user = new User();
        String[] read = new String[6];
        File f = new File("User_" + username + ".txt");

        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            for (int i = 0; i < 6; i++) {
                read[i] = bfr.readLine();
            }
        } catch (IOException e) {
            System.out.println("This user does not exist.");
            return null;  // Error reading the file
        }

        user.setFirstName(read[0]);
        user.setLastName(read[1]);
        user.setEmail(read[2]);
        user.setUsername(read[3]);
        user.setBio(read[4]);
        user.setPassword(read[5]);

        if (!passwordInput.equals(read[5])) {
            System.out.println("Your password is incorrect.");
            return null;  // Incorrect password
        }

        System.out.println("Log in successful");
        return new Friends(user.getFirstName(), user.getLastName(), user.getEmail(), user.getBio(), user.getUsername(), user.getPassword());
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
