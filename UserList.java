import java.io.File;
import java.io.IOException;

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

public interface UserList {
    String getFirstName();
    String getUsername();
    String getLastName();
    String getBio();
    String getEmail();
    String getPassword();
    File getUserFile();
    File getFriends();
    File getBlock();
    File getFriendRequest();
    boolean getProfileView();
    boolean getMessageCheck();

    boolean setFirstName(String firstName);
    boolean setLastName(String lastName);
    boolean setUsername(String username);
    boolean setEmail(String email);
    boolean setBio(String bio);
    boolean setPassword(String password);
    boolean setProfileView(boolean check);
    boolean setMessageCheck(boolean check);
    boolean setAccountFile();

    void setFriend(File file);
    void setBlock(File file);
    void setFriendRequest(File file);

    boolean checkAccountExists() throws IOException;
    boolean createAccount();
    User logIn();
    String toString();
    String[] viewFriendsRequest();
    String[] viewFriends();
    String[] viewBlocks();
    String viewFile();
    String[] viewAllUsers();
    boolean searchUser(String searchName);
    boolean updateProfile(String[] profileData);
}

