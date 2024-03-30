public class Messages implements Message {
    //The content of the message
    private String content;

    //A user who send this message;
    private User sendUser;

    //A user who receive this message;
    private User receiveUser;

    public Messages(String content, User sendUser, User receiveUser) throws MessageException {
        if (sendUser == null) {
            throw new MessageException("Error! No send user!");
        }
        if (receiveUser == null) {
            throw new MessageException("Error! No receive user");
        }
        this.content = content;
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }

    public String getContent() {
        return content;
    }

    public User getSendUser() {
        return sendUser;
    }

    public User getReceiveUser() {
        return receiveUser;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String toString() {
        String a = "%s: %s";
        return String.format(a, sendUser.getUsername(), content);
    }
}
