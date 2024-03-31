import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * UserList
 *
 * This interface outlines all methods used in the User class.
 *
 */

public interface UserList {

    String getFirstName();

    String getUsername();

    String getLastName();

    String getBio();

    String getEmail();

    String getPassword();

    File getAccountFile();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    void setUsername(String username);

    void setEmail(String email);

    void setBio(String bio);

    void setPassword(String password);

    void setAccountFile();

    String checkFirstName(Scanner sc);

    String checkLastName(Scanner sc);

    String checkEmail(Scanner sc);

    String checkBio(Scanner sc);

    String checkUsername(Scanner sc);

    String checkPassword(Scanner sc);

    boolean checkAccountExists(String username);

    String changeEmail(Scanner sc);

    String changeBio(Scanner sc);

    String changeFirstName(Scanner sc);

    String changeLastName(Scanner sc);

    String changeUsername(Scanner sc);

    String changePassword(Scanner sc);

    User createAccount(Scanner sc);

    User editAccount(Scanner sc);

    User logIn(Scanner scan);

    String toString();

    static boolean checkMoreOneUser() {
        return false;
    }

}


