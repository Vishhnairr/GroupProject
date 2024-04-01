import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;

public class UserTest {

    Scanner scan = new Scanner(System.in);

    @Test
    public void testUserConstructor() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username","password");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testUserConstructorFails() {
        try {
            User user = new User(null, "last", "a@b.c", "bio", "username", "3");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testCheckFirstName() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getFirstName(), "first");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckFirstNameFails() {
        try {
            User user = new User(null, "last", "a@b.c", "bio", "username", "3");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testCheckLastName() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getLastName(), "last");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckLastNameFails() {
        try {
            User user = new User("first", null, "a@b.c", "bio", "username", "3");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testCheckEmail() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getEmail(), "email");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckEmailFails() {
        try {
            User user = new User("first", "last", null, "bio", "username", "3");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testCheckBio() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getBio(), "bio");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckBioFails() {
        try {
            User user = new User("first", "last", "a@b.c", null, "username", "3");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testCheckUsername() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getUsername(), "username");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckUsernameFails() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", null, "3");
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testCheckPassword() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getPassword(), "password");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckPasswordFails() {
        try {
            User user = new User(null, "last", "a@b.c", "bio", "username", null);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testCheckAccountExists() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckAccountExistsFails() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Failed to create a new user...", e.getMessage());
        }
    }

    @Test
    public void testChangeEmail() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.setEmail(user.changeEmail(scan));

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeEmailFails() {

    }

    @Test
    public void testChangeBio() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.changeBio(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeBioFails() {

    }

    @Test
    public void testChangeFirstName() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.changeFirstName(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeFirstNameFails() {

    }

    @Test
    public void testChangeLastName() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.changeLastName(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeLastNameFails() {

    }

    @Test
    public void testChangeUsername() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.changeUsername(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeUsernameFails() {

    }

    @Test
    public void testChangePassword() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.changePassword(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangePasswordFails() {

    }

    @Test
    public void testCreateAccount() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.createAccount(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCreateAccountFails() {

    }

    @Test
    public void testEditAccount() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.editAccount(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testEditAccountFails() {

    }

    @Test
    public void testLogIn() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            user.logIn(scan);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testLogInFails() {

    }

    @Test
    public void testToString() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            Assert.assertEquals(user.toString(), "first\nlast\na@b.c\nbio\nusername\npassword");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testToStringFails() {

    }

    @Test
    public void testCheckOneMoreUser() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            User.checkMoreOneUser();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCheckOneMoreUserFails() {

    }

}
