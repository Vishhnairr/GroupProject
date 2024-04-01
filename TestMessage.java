import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A test case formessage.
 */
public class TestMessage {
    @Test
    public void testMessageConstructor() {
        try {
            User user = new User("first",
                    "last",
                    "email@gmail.com",
                    "This is a bio that need something.",
                    "username",
                    "passwordthatweneed");

            User receiverUser = new User("receivefirst",
                    "receivelast",
                    "recerive@gmail.com",
                    "This is another bio that need things.",
                    "receiveUser",
                    "receivepassword");

            MessageList messageList = new MessageList("Content.", user, "receiveUser");

        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testMessageToString() {
        try {
            User user = new User("first",
                    "last",
                    "email@gmail.com",
                    "This is a bio that need something.",
                    "username",
                    "passwordthatweneed");

            User receiverUser = new User("receivefirst",
                    "receivelast",
                    "recerive@gmail.com",
                    "This is another bio that need things.",
                    "receiveUser",
                    "receivepassword");

            MessageList messageList = new MessageList("Content.", user, "receiveUser");

            String message = messageList.toString();

            Assert.assertEquals("username: Content.", message);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void testMessageSendMessage() {
        try {
            User user = new User("first",
                    "last",
                    "email@gmail.com",
                    "This is a bio that need something.",
                    "username",
                    "passwordthatweneed");

            User receiverUser = new User("receivefirst",
                    "receivelast",
                    "recerive@gmail.com",
                    "This is another bio that need things.",
                    "receiveUser",
                    "receivepassword");

            MessageList messageList = new MessageList("Content.", user, "receiveUser");
            messageList.sendMessage();
            ArrayList<String> history = messageList.viewMessageHistory();
            int check = 0;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).equals(messageList.toString())) {
                    check ++;
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
    public void testMessage() {
        try {
            User user = new User("first",
                    "last",
                    "email@gmail.com",
                    "This is a bio that need something.",
                    "username",
                    "passwordthatweneed");

            User receiverUser = new User("receivefirst",
                    "receivelast",
                    "recerive@gmail.com",
                    "This is another bio that need things.",
                    "receiveUser",
                    "receivepassword");

            MessageList messageList = new MessageList("Delete.", user, "receiveUser");
            messageList.sendMessage();
            messageList.deleteMessage(messageList.getContent());
            ArrayList<String> history = messageList.viewMessageHistory();
            int check = 0;
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i).equals(messageList.toString())) {
                    check ++;
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
            User user = new User("first",
                    "last",
                    "email@gmail.com",
                    "This is a bio that need something.",
                    "anotherusername",
                    "passwordthatweneed");

            User receiverUser = new User("receivefirst",
                    "receivelast",
                    "recerive@gmail.com",
                    "This is another bio that need things.",
                    "anotherreceiveUser",
                    "receivepassword");

            MessageList messageList = new MessageList("Delete.", user, receiverUser.getUsername());
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
