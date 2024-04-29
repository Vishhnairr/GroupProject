import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * TestMessage
 *
 * Test for message
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version April 28, 2024
 *
 */

public class TestMessage {
    @Test
    public void testMessageConstructor() {
        try {
            User test = new User("testMesusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setMesname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            MessageList messageList = new MessageList("Content.", test, user.getUsername());

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testMessageToString() {
        try {
            User test = new User("testMesusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setMesname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            MessageList messageList = new MessageList("Content.", test, user.getUsername());

            String message = messageList.toString();

            Assert.assertEquals("testMesusername: Content.", message);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testMessageSendMessageFriend() {
        try {
            User test = new User("testMesusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, true);

            User user = new User("setMesname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, false);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());
            friends.makeFriendRequest();
            friends.addFriend();

            MessageList messageList = new MessageList("Content.", test, user.getUsername());
            messageList.sendMessage();
            ArrayList<String> history = messageList.viewMessageHistory();
            int check = 0;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).equals(messageList.toString())) {
                    check++;
                }
            }
            if (check == 0) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testMessageSendMessageBlock() {
        try {
            User test = new User("testMesBlousername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, true);

            User user = new User("setMesBloname", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, false);

            test.createAccount();
            user.createAccount();

            Friends friends = new Friends(test, user.getUsername());
            friends.blockUser();

            MessageList messageList = new MessageList("Content.", test, user.getUsername());
            messageList.sendMessage();
            ArrayList<String> history = messageList.viewMessageHistory();
            int check = 0;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).equals(messageList.toString())) {
                    check++;
                }
            }
            if (check != 0) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testDeleteMessage() {
        try {
            User test = new User("testMesDleusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setMesDlename", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            MessageList messageList = new MessageList("Content.", test, user.getUsername());
            messageList.sendMessage();
            messageList.deleteMessage(messageList.getContent());
            ArrayList<String> history = messageList.viewMessageHistory();
            int check = 0;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).equals(messageList.toString())) {
                    check++;
                }
            }
            if (check != 0) {
                Assert.fail();
            }
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testMessageViewMessageHistory() {
        try {
            User test = new User("testMesVieusername", "testpassword",
                    "testfirst", "testlast", "test@gmail.com",
                    "This is a bio", true, false);

            User user = new User("setMesViename", "thisispassword",
                    "First", "Last", "email@gmail.com",
                    "This is a short bio.", true, true);

            test.createAccount();
            user.createAccount();

            MessageList messageList = new MessageList("Content.", test, user.getUsername());
            messageList.sendMessage();
            ArrayList<String> history = messageList.viewMessageHistory();
            ArrayList<String> check = new ArrayList<>();
            check.add(messageList.toString());
            Assert.assertEquals(check, history);
        } catch (Exception e) {
            Assert.fail();
        }
    }


}
