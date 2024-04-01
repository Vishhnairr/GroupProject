//.

import java.util.ArrayList;

/**
 * 
 * Message Interface
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
public interface Message {
    String getContent();
    User getSendUser();
    String getReceiveUser();
    void setContent(String content);
    void setSendUser(User sendUser);
    void setReceiveUser(String receiveUser);
    String toString();
    boolean sendMessage();
    boolean deleteMessage(String message);
    ArrayList<String> viewMessageHistory();

}
