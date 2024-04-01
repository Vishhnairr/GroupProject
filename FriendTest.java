import org.junit.Assert;
import org.junit.Test;

public class FriendTest {
    @Test
    public void testFriendConstructor() {
        try {
            Friends friend = new Friends("first", "last", "email@me.com", "bio",
                    "user", "pass");
        } catch (Exception e) {
            Assert.fail("Failed to create a friend!");
        }
    }
    @Test
    public void testMakeFriendRequest() {

    }

    @Test
    public void testBlockUser() {

    }

    @Test
    public void testHandleFriendRequest() {

    }

    @Test
    public void testUpdateFriendRequestStatus() {

    }
    @Test
    public void testRemoveUser() {

    }

    @Test
    public void testFriendViewer() {

    }

    @Test
    public void testViewProfile() {

    }

    @Test
    public void testHasFriends() {

    }
}
