//.
public interface Message {
    String getContent();
    User getSendUser();
    String getReceiveUser();
    void setContent(String content);
    void setSendUser(User sendUser);
    void setReceiveUser(String receiveUser);
    String toString();
    boolean checkReceiver();
    boolean sendMessage();
    boolean deleteMessage(String message);

}
