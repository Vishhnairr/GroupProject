import java.util.ArrayList;

/**
 * MessageList
 *
 * This interface must be implemented by message.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version March 31, 2024
 *
 */
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
