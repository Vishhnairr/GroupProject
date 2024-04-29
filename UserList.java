import java.io.File;
import java.io.IOException;

/**
 * User List
 *
 * This interface outlines all methods used in the User class.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 28, 2024
 *
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

    boolean setUsername(String username) throws IOException;

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
