import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

/**
 * UserTest
 *
 * Test for user
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 26, 2024
 *
 */

public class UserTest {

    @Test
    public void testUserConstructor() {
        try {
            User user = new User("username", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            User another = new User("Anousername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCreateAccount() {
        try {
            User user = new User("Creusername", "Crethisispassword",
                    "Crefirst", "Crelast", "Creemail@gmail.com",
                    "This is a short bio for Cre.", true, true);

            User another = new User("AnoCreusername", "AnoCrethisispassword",
                    "AnoCerfirst", "AnoCreLast", "AnoCreemail@gmail.com",
                    "This is an another short bio for ano Cre.", true, false);

            if (!user.createAccount()) {
                Assert.fail();
            }

            if (!another.createAccount()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckAccountExist() {
        try {
            User user = new User("username", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            User another = new User("Anousername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);

            if (!user.checkAccountExists()) {
                Assert.fail();
            }

            if (!another.checkAccountExists()) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testLogIn() {
        try {
            User another = new User("Anousername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);

            User logInUser = new User("Anousername", "Anothisispassword");
            User testUser = logInUser.logIn();

            //test if log in correctly
            Assert.assertNotNull(testUser);
            Assert.assertEquals(logInUser.getUsername(), testUser.getUsername());
            Assert.assertEquals(logInUser.getPassword(), testUser.getPassword());

            //test if log in incorrectly
            User wrongUser = new User("wrongname", "wrongpassword").logIn();
            Assert.assertNull(wrongUser);

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testViewFile() {
        try {
            User user = new User("username", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            User another = new User("Anousername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);

            String userProfile = "User name: username\n" + "Password: thisispassword\n"
                    + "First name: First\n" + "Last name: Last\n" + "Email: email@gmail.com\n"
                    + "Bio: This is a short bio.\n" + "Profile view: true\n" + "Message only: true";

            String anotherProfile = "User name: Anousername\n" + "Password: Anothisispassword\n"
                    + "First name: Anofirst\n" + "Last name: AnoLast\n" + "Email: Anoemail@gmail.com\n"
                    + "Bio: This is an another short bio.\n" + "Profile view: true\n" + "Message only: false";

            if (!user.viewFile().equals(userProfile)) {
                Assert.fail();
            }

            if (!another.viewFile().equals(anotherProfile)) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testSearchUser() {
        try {
            User user = new User("username", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            User another = new User("Anousername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);

            user.createAccount();
            another.createAccount();
            if (!user.searchUser(another.getUsername())) {
                Assert.fail();
            }

            if (!another.searchUser(user.getUsername())) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testSetUsername() {
        try {
            User user = new User("testsetusername", "thisispassword",
                    "TestFirst", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            User another = new User("Anousername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);

            user.createAccount();
            another.createAccount();
            if (!user.setUsername("settestname")) {
                Assert.fail();
            }

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testViewFriendRequest() {
        try {
            User user = new User("setFRname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            User another = new User("AnoFRusername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);

            user.createAccount();
            another.createAccount();

            Friends friends = new Friends(user, another.getUsername());
            friends.makeFriendRequest();

            String[] frForAno = {user.getUsername()};
            Assert.assertEquals(frForAno, another.viewFriendsRequest());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testViewFriends() {
        try {
            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            User another = new User("Anousername", "Anothisispassword",
                    "Anofirst", "AnoLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);

            user.createAccount();
            another.createAccount();
            Friends friends = new Friends(user, another.getUsername());
            Friends friends1 = new Friends(another, user.getUsername());

            friends.makeFriendRequest();
            friends1.addFriend();

            String[] expectForUser = {"Anousername"};
            String[] expectForAnother = {"setname"};

            Assert.assertEquals(expectForUser, user.viewFriends());
            Assert.assertEquals(expectForAnother, another.viewFriends());

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testViewBlocks() {
        try {

            User another = new User("AnoBlousername", "AnoBlothisispassword",
                    "AnoBlofirst", "AnoBloLast", "Anoemail@gmail.com",
                    "This is an another short bio.", true, false);


            User blockUser = new User("Blousername", "Blothisispassword", "Blofirst",
                    "BlooLast", "Bloemail@gmail.com",
                    "This is an another short bio for block.", true, false);

            another.createAccount();
            blockUser.createAccount();

            Friends friends = new Friends(another, blockUser.getUsername());
            friends.blockUser();

            String[] block = {"Blousername"};
            Assert.assertEquals(block, another.viewBlocks());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testViewAllUsers() {
        try {
            User user = new User("setname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            user.createAccount();

            String[] allUsers = {"setname"};

            Assert.assertEquals(allUsers, user.viewAllUsers());

        } catch (Exception e) {
            Assert.fail();
        }
    }

}
