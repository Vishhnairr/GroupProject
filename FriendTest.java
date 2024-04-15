import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class FriendTest {

    @Test
    public void testFriendConstructor() {
        try {
            User test = new User("testusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testVerifyUser() {
        try {
            User test = new User("testusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());


            if (!friends.verifyUser()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testMakeFriendRequest() {
        try {
            User test = new User("testusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());

            if (!friends.makeFriendRequest()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testAddFriend() {
        try {
            User test = new User("testusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());
            Friends friends1 = new Friends(user, test.getUsername());

            friends.makeFriendRequest();

            if (!friends1.addFriend()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testRemoveFriend() {
        try {
            User test = new User("testReusername", "testpassword",
                    "testRefirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());
            Friends friends1 = new Friends(user, test.getUsername());

            friends.makeFriendRequest();
            friends1.addFriend();

            if (!friends.removeFriend()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testBlockUser() {
        try {
            User test = new User("testusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());

            if (!friends.blockUser()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testRemoveBlock() {
        try {
            User test = new User("testBlousername", "testpassword",
                    "testBlofirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());
            friends.blockUser();

            if (!friends.removeBlock()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testViewProfile() {
        try {
            User test = new User("testusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());

            String expect = "Username: setname\n" +
                    "First name: First\n" +
                    "Last name: Last\n" +
                    "Email: email@gmail.com\n" +
                    "Bio: This is a short bio.";

            Assert.assertEquals(expect, friends.viewProfile());
        } catch (Exception e) {
            Assert.fail();
        }
    }
}

