import java.io.*;

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

    boolean checkAccountExists(String username);

    boolean checkEmail(String email);

    boolean checkBio(String bio);

    boolean checkFirstName(String firstName);

    boolean checkLastName(String lastName);

    boolean checkUsername(String username);

    boolean checkPassword(String password);

    User createAccount(String firstName, String lastName, String email, String bio, String username, String password);

    User editAccount(int editOption, String newValue);

    User logIn(String username, String passwordInput);

    String toString();

    static boolean checkMoreOneUser() {
        return false;
    }

}
