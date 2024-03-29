// imports here

import java.io.*;
import java.util.Scanner;

/**
 * Project 4 - User
 * <p>
 * This allows for the manipulation of all the basic information about a user. This information is: the first and
 * last names of the user, the email and password of the user, the type of user that the user is, and the account file
 * object for the file that holds all the login information about a user. This class also contains the basic
 * functions to create, edit, and delete accounts, and to log in to an account. All of this account information is
 * stored in an account file, and every user has their own account file that is created when they create an account,
 * and used to remember the user for when they log in again. A user can edit there first and last names and their
 * password, but not their email or user type as most files associated with a user are created using their email
 * as the unique identifier. When deleting an account, the account file gets deleted. If the user is a customer, then
 * their customer appointments file gets deleted. If the user is a seller, their owned stores file gets deleted, there
 * stores are removed from the All_Store_Info file, and every store info file associated with the deleted user also
 * get deleted. Calendar files and customer appointment files are not updated, because customers can never reach
 * a calendar for a store that doesn't exist, and it is assumed that even though the seller deleted their account, the
 * customers who still made appointments will still want to show up for their appointment.
 */

public class User {
    private String firstName; // the first name of the user
    private String lastName; // the last name of the user
    private String email; // the email of the user
    private String bio; // the bio of the user
    private String username; // the username of the user
    private String password; // the password of the user
    private File accountFile;  // file object for the account file holding the account information about the user

    // default constructor
    public User() {
        this.firstName = "none";
        this.lastName = "none";
        this.email = "none";
        this.bio = "none";
        this.username = "none";
        this.password = "none";
        this.accountFile = null;
    }


    // customizable constructor
    public User(String firstName, String lastName, String email, String bio, String username,
                String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.bio = bio;
        this.username = username;
        this.password = password;
        this.accountFile = new File("User_" + email.split("@")[0] + ".txt");
    }

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public boolean validateName(String name) {
        return name == null || name.equals("")
                || name.equals("none") || name.split(" ").length > 1;
    }

    public boolean validateBio(String bio) {
        return bio == null || bio.equals("none") || bio.length() < 20 || bio.length() > 40;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public boolean validateEmail(String email) {
        return email == null || email.equals("")
                || email.equals("none") || email.split(" ").length > 1 || email.split("@").length != 2
                || email.split("@")[1].split("\\.").length != 2;
    }

    public boolean checkAccountExists(String email) {
        File accountFile = new File("User_" + email.split("@")[1]);
        return accountFile.exists();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validatePassword(String password) {
        return password.length() < 4;
    }

    public File getAccountFile() {
        return accountFile;
    }

    public void setAccountFile() {
        this.accountFile = new File("User_" + email.split("@")[0] + ".txt");
    }


    public String changeEmail(Scanner sc) {
        boolean invalidEmail;  // true if the entered email is invalid
        String email; // the email te user enters

        // validating email
        do {
            System.out.println("Enter your email:");
            email = sc.nextLine();
            invalidEmail = validateEmail(email);

            if (invalidEmail) {  // selection for what to print if email entered incorrectly
                if (email == null || email.equals("")
                        || email.equals("none")) {
                    System.out.println("ERROR! Please enter an email!");
                } else if (email.split(" ").length > 1 || email.split("@").length != 2
                        || email.split("@")[1].split("\\.").length != 2) {
                    System.out.println("ERROR! the email you entered was incorrectly formatted.");
                    System.out.println("Please enter an email of the form: ___@___.___ with no spaces!");
                }
            } else {
                invalidEmail = checkAccountExists(email);  // checked to see if account with entered email exists

                if (invalidEmail) {
                    System.out.println("ERROR! An account with this email already exists!");
                }
            }

        } while (invalidEmail);  // end of checking email

        return email;

    }
    public String changeBio(Scanner sc) {
        boolean invalidBio;  //true if the first name entered is invalid
        String bio;  // the first name of the user

        // validating first name
        do {
            System.out.println("Enter a bio for yourself:");
            bio = sc.nextLine();

            invalidBio = validateBio(bio);

            if (bio == null || bio.equals("")
                    || bio.equals("none")) {
                System.out.println("ERROR! Please make sure your bio is between 20 to 40 charactors!");
            } else if (bio.length() < 20 || bio.length() > 40) {
                System.out.println("ERROR! Please make sure your bio is between 20 to 40 charactors!");
            }

        } while (invalidBio);

        return bio;
    }

    public String changeFirstName(Scanner sc) {
        boolean invalidFirstName;  //true if the first name entered is invalid
        String firstName;  // the first name of the user

        // validating first name
        do {
            System.out.println("Enter your first name:");
            firstName = sc.nextLine();

            invalidFirstName = validateName(firstName);

            if (firstName == null || firstName.equals("")
                    || firstName.equals("none")) {
                System.out.println("ERROR! Please enter your first name!");
            } else if (firstName.split(" ").length > 1) {
                System.out.println("ERROR! Only your first name with no spaces!");
            }

        } while (invalidFirstName);

        return firstName;
    }

    public String changeUsername(Scanner sc) {
        boolean invalidUsername;  //true if the first name entered is invalid
        String Username;  // the first name of the user

        // validating first name
        do {
            System.out.println("Enter your username:");
            username = sc.nextLine();

            invalidUsername = validateName(username);

            if (username == null || username.equals("")
                    || username.equals("none")) {
                System.out.println("ERROR! Please enter your username!");
            } else if (username.split(" ").length > 1) {
                System.out.println("ERROR! There should be no spaces in your username!");
            }

        } while (invalidUsername);

        return username;
    }

    public String changeLastName(Scanner sc) {
        String lastName;  // the last name of the user
        boolean invalidLastName;  // true if the last name entered is invalid

        // validating last name
        do {
            System.out.println("Enter your last name:");
            lastName = sc.nextLine();

            invalidLastName = validateName(lastName);

            if (lastName == null || lastName.equals("")
                    || lastName.equals("none")) {
                System.out.println("ERROR! Please enter your last name!");
            } else if (lastName.split(" ").length > 1) {
                System.out.println("ERROR! Only your last name with no spaces!");
            }

        } while (invalidLastName); // end of validating last name

        return lastName;
    }


    public String changePassword(Scanner sc) {
        boolean invalidPassword;  // true if the password entered is invalid
        String password;  // the password of the user

        do {
            System.out.println("Enter a password:");
            password = sc.nextLine();
            invalidPassword = validatePassword(password);

            if (invalidPassword) {
                System.out.println("ERROR! Your password must be at least 4 characters long!");
            }
        } while (invalidPassword);  // end of check password

        return password;
    }


    public User createAccount(Scanner sc) {
        File userFile;  // the file the user account info will be stored in
        String email = "";  // the email the user enters
        String firstName = "";  // the first name of the user
        String lastName = "";  // the last name of the user
        String bio = ""; // the bio of the user
        String username = "";  // the username of the user
        String password = "";  // the password of the user
        User user;  // the user object to be returned

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Account created successfully");
        File allUsersFile = new File("All_User_Info.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(allUsersFile, true))) {
            bw.write(username + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create a file for the user's messages
        File userMessagesFile = new File("User_" + username + "_messages.txt");
        if (!userMessagesFile.exists()) {
            try {
                userMessagesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create a file for the user's friends
        File userFriendsFile = new File("User_" + username + "_Friends.txt");
        if (!userFriendsFile.exists()) {
            try {
                userFriendsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
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
            } else if (editOption == 4){
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

        File userFile = new File("User_" + email.split("@")[0] + ".txt");

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

            if (this instanceof Friends) {
                Friends member = (Friends) this;
                member.setMemberAppointmentsFile(new File("Member_" +
                        member.getEmail().split("@")[0] + "_appointments.txt"));
                if (member.getMemberAppointmentsFile().exists()) {
                    member.getMemberAppointmentsFile().delete();
                }
            }
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
