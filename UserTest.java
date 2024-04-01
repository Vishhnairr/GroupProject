import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class UserTest {

    private final PrintStream originalOutput = System.out;
    private final InputStream originalSysin = System.in;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayInputStream testIn;

    @SuppressWarnings("FieldCanBeLocal")
    private ByteArrayOutputStream testOut;

    @Before
    public void outputStart() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreInputAndOutput() {
        System.setIn(originalSysin);
        System.setOut(originalOutput);
    }

    @SuppressWarnings("SameParameterValue")
    private void receiveInput(String str) {
        testIn = new ByteArrayInputStream(str.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testUserConstructor() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username","password");
        } catch (Exception e) {
            Assert.fail();
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
    public void testCheckAccountExists() {
        try {
            User user = new User("first", "last", "email", "bio", "username","password");
            Assert.assertNotNull(user);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeEmail() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = "aa@b.c";
            receiveInput(input);
            user.setEmail(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getEmail(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeBio() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = "bioo";
            receiveInput(input);
            user.setBio(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getBio(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeFirstName() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = "firstt";
            receiveInput(input);
            user.setFirstName(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getFirstName(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeLastName() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = "lastt";
            receiveInput(input);
            user.setLastName(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getLastName(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangeUsername() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = "usernamee";
            receiveInput(input);
            user.setUsername(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getUsername(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testChangePassword() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = "passwordd";
            receiveInput(input);
            user.setPassword(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getPassword(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testCreateAccount() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = user.toString();
            receiveInput(input);
            user.setEmail(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getEmail(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testEditAccount() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = user.toString();
            receiveInput(input);
            user.setEmail(input);
            Assert.assertNotNull(user);
            Assert.assertEquals(user.getEmail(), input);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testLogIn() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            String input = "username" + "password";
        } catch (Exception e) {
            Assert.fail();
        }
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
    public void testCheckOneMoreUser() {
        try {
            User user = new User("first", "last", "a@b.c", "bio", "username", "password");
            User.checkMoreOneUser();
        } catch (Exception e) {
            Assert.fail();
        }
    }

}
