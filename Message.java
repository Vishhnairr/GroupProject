import java.util.Scanner;
//
public interface Message {
    String getContent();
    User getSendUser();
    User getReceiveUser();
    void setContent(String content);
    void setSendUser(User sendUser);
    void setReceiveUser(User receiveUser);
    String toString();
    boolean checkReceiver();
    boolean sendMessage();
    boolean deleteMessage(Scanner message);

}
