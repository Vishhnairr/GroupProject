/**
 * FriendList
 *
 * This class extends the User class and allows for a user to make, accept, and decline
 * friend requests, block other users, view their friends list, and remove friends from
 * the list. The friends list is stored as a file associated with the user and is updated
 * every time they perform one of the above actions.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version March 31, 2024
 *
 */
public interface FriendList {
    boolean verifyUser();

    boolean makeFriendRequest();
    
    boolean rejectFriendRequest();

    boolean addFriend();

    boolean removeFriend();

    boolean blockUser();

    boolean removeBlock();

    String viewProfile();
}
