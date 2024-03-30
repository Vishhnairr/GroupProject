public interface Message {
    String getContent();
    User getSendUser();
    User getReceiveUser();
    void setContent(String content);
    void setSendUser(User sendUser);
    void setReceiveUser(User receiveUser);
    String toString();

}
