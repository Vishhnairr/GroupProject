//.

import java.util.ArrayList;

/**
 *
 * MessageList
 *
 * This class allows for a user to message one of their friends by creating a message object
 * that contains the message being sent, the message sender, and the message recipient. The
 * class contains methods that check if the recipient is a friend of the sender, create and update
 * a file for both the sender and the recipient that contains the message history of the two
 * users, and delete messages in the message history.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13.
 *
 * @version March 31, 2024
 *
 */
import java.util.ArrayList;

public interface MessageList {
    // Get the content of the message
    String getContent();

    // Get the user who sent this message
    User getSendUser();

    // Get the user who is supposed to receive this message
    String getReceiveUser();

    // Set the content of the message
    void setContent(String content);

    // Set the user who sends this message
    void setSendUser(User sendUser);

    // Set the user who will receive this message
    void setReceiveUser(String receiveUser);

    // Returns a string representation of the message
    String toString();

    // Verify if the receiving user exists
    boolean verifyUser();

    // Send the message to the receiving user
    boolean sendMessage();

    // Delete a specific message from the message history
    boolean deleteMessage(String delete);

    // View the message history between the sender and receiver
    ArrayList<String> viewMessageHistory();

    // Rewrite the message history with new messages
    boolean rewriteMessageHistory(ArrayList<String> newMessages);
}

