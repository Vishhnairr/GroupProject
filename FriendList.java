import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 4 - FriendList
 *
 * This class extends the User class and allows for a user to make, accept, and decline
 * friend requests, block other users, view their friends list, and remove friends from
 * the list. The friends list is stored as a file associated with the user and is updated
 * every time they perform one of the above actions.
 *
 */
public interface FriendList {

    /**
     * Sends a friend request to another user.
     *
     * @param scanner        The Scanner object for user input.
     * @param friendUsername The username of the user receiving the friend request.
     * @param username       The username of the user sending the friend request.
     */
    void makeFriendRequest(Scanner scanner, String friendUsername, String username);

    /**
     * Blocks a user, preventing them from sending friend requests.
     *
     * @param usernameToBlock  The username of the user to block.
     * @param blockingUsername The username of the user performing the block.
     */
    void blockUser(String usernameToBlock, String blockingUsername);

    /**
     * Handles a friend request by either approving or declining it.
     *
     * @param friendUsername The friend the user is interacting with.
     * @param username   The user who is approving or declining a friend request.
     * @param approve   A boolean to check if request has been approved or denied.
     */
    public void updateFriendRequestStatus(String friendUsername, String username, boolean approve);

    /**
     * Removes a user from the friend list.
     *
     * @param scanner  The Scanner object for user input.
     * @param username The username of the user whose friend is to be removed.
     */
    void removeUser(Scanner scanner, String username);

    /**
     * Views and lists all friends of the user.
     *
     * @param username The username of the user whose friends are to be listed.
     * @return A list of usernames who are friends with the user.
     */
    ArrayList<String> friendViewer(String username);

    /**
     * Views and prints the profile of the selected user.
     *
     * @param selectedUserName The username of the user whose profile is to be viewed.
     */
    void viewProfile(String selectedUserName);

    /**
     * Checks if the user has any friends.
     *
     * @param username The username of the user to check for friends.
     * @return true if the user has friends, otherwise false.
     */
    boolean hasFriends(String username);
}
