import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import java.io.*;

public class FriendTest {

    private Friends friends;
    private final String username = "testUser";
    private final String friendUsername = "friendUser";
    private final String blockedUsername = "blockedUser";

    @Before
    public void setUp() {
        friends = new Friends("Test", "User", "test@example.com", "This is a bio", username, "password123");

        new File("User_" + username + "_Friends.txt").delete();
        new File("User_" + friendUsername + "_Friends.txt").delete();
        new File("User_" + blockedUsername + "_Friends.txt").delete();
    }

    @Test
    public void testMakeFriendRequest() {
        String inputMessage = "Hi, let's be friends!";
        System.setIn(new ByteArrayInputStream(inputMessage.getBytes()));
        Scanner scanner = new Scanner(System.in);


        friends.makeFriendRequest(scanner, friendUsername, username);

        File friendFile = new File("User_" + friendUsername + "_Friends.txt");
        Assert.assertTrue("Friend request file does not exist", friendFile.exists());
    }

    @Test
    public void testBlockUser() {
        friends.blockUser(blockedUsername, username);

        File blockedFile = new File("User_" + blockedUsername + "_Friends.txt");
        Assert.assertTrue("Blocked user file does not exist", blockedFile.exists());
    }

    @Test
    public void testRemoveUser() throws IOException {
        FileWriter writer = new FileWriter("User_" + username + "_Friends.txt", true);
        writer.write(friendUsername + " is your friend!\n");
        writer.close();

    }

    @Test
    public void testUpdateFriendRequestStatus() throws IOException {
        String friendRequestMessage = "Friend request from: " + username;
        FileWriter writerForFriend = new FileWriter("User_" + friendUsername + "_Friends.txt", true);
        writerForFriend.write(friendRequestMessage + "\n");
        writerForFriend.close();

        FileWriter writerForUser = new FileWriter("User_" + username + "_Friends.txt", true);
        writerForUser.write("You sent a friend request to: " + friendUsername + "\n");
        writerForUser.close();


        friends.updateFriendRequestStatus(friendUsername, username, true);

        File userFile = new File("User_" + username + "_Friends.txt");
        Scanner scanner = new Scanner(userFile);
        boolean found = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains(friendUsername + " is your friend!")) {
                found = true;
                break;
            }
        }
        scanner.close();
    }

    @Test
    public void testFriendViewer() throws IOException {
        FileWriter writer = new FileWriter("User_" + username + "_Friends.txt", true);
        writer.write(friendUsername + " is your friend!\n");
        writer.write(blockedUsername + " is your friend!\n");
        writer.close();
        ArrayList<String> friendsList = friends.friendViewer(username);
        Assert.assertTrue("The friends list should contain " + friendUsername + ".", friendsList.contains(friendUsername));
        Assert.assertTrue("The friends list should contain " + blockedUsername + ".", friendsList.contains(blockedUsername));
        Assert.assertEquals("The friends list should contain exactly 2 friends.", 2, friendsList.size());
    }
    @Test
    public void testHasFriends() throws IOException {
        FileWriter writerWithFriends = new FileWriter("User_" + username + "_Friends.txt", true);
        writerWithFriends.write(friendUsername + " is your friend!\n");
        writerWithFriends.close();
        boolean resultWithFriends = friends.hasFriends(username);
        Assert.assertTrue("hasFriends should return true when the user has friends", resultWithFriends);
        new File("User_" + username + "_Friends.txt").delete();
        setUp();
        boolean resultWithoutFriends = friends.hasFriends(username);
        Assert.assertFalse("hasFriends should return false when the user has no friends", resultWithoutFriends);
    }

}

