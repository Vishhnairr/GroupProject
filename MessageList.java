import java.io.*;
import java.util.ArrayList;
//.

public class MessageList implements Message {
    //The content of the message
    private String content;

    //A user who send this message;
    private User sendUser;

    //A user who receive this message;
    private String receiveUser;

    public MessageList(String content, User sendUser, String receiveUser){
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

    public String getReceiveUser() {
        return receiveUser;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSendUser(User sendUser) {
        this.sendUser = sendUser;
    }

    public void setReceiveUser(String receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String toString() {
        String a = "%s: %s";
        return String.format(a, sendUser.getUsername(), content);
    }

    public boolean checkReceiver() {
        ArrayList<String> friendList = new ArrayList<>();
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(
                    new File("User_" + sendUser.getUsername() + "_Friends.txt")));

            String line = bfr.readLine();

            while (line != null) {
                String[] splits = line.split(" ");
                friendList.add(splits[0]);
            }

            for (int i = 0; i < friendList.size(); i++) {
                if (friendList.get(i).equals(receiveUser)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public boolean sendMessage() {
        if (checkReceiver()) {
            try {
                File messageHistorySender = new File(sendUser.getUsername() + "_" + receiveUser + ".txt");
                File messageHistoryReceiver = new File (receiveUser + "_"
                        + sendUser.getUsername() + ".txt");

                FileOutputStream fosS = new FileOutputStream(messageHistorySender, true);
                FileOutputStream fosR = new FileOutputStream(messageHistoryReceiver, true);
                PrintWriter pwS = new PrintWriter(fosS);
                PrintWriter pwR = new PrintWriter(fosR);

                pwS.println(this.toString());
                pwR.println(this.toString());

                pwS.close();
                pwR.close();

                return true;

            } catch (Exception e) {
                return false;
            }
        }

        return false;
    }

    public boolean deleteMessage(String delete) {
        if (checkReceiver()) {
            ArrayList<String> listOrigilnal = new ArrayList<>();
            ArrayList<String> listCopy = new ArrayList<>();
            try {
                File messageHistorySender = new File(sendUser.getUsername() + "_" + receiveUser + ".txt");
                File messageHistoryReceiver = new File (receiveUser + "_"
                        + sendUser.getUsername() + ".txt");

                FileReader frS = new FileReader(messageHistorySender);
                BufferedReader bfrS = new BufferedReader(frS);

                String line = bfrS.readLine();

                while (line != null) {
                    listCopy.add(line);
                    line = line.substring(line.indexOf(" ") + 1);
                    listOrigilnal.add(line);
                    line = bfrS.readLine();
                }
                bfrS.close();

                for (int i = 0; i < listCopy.size(); i++) {
                    if (listOrigilnal.get(i).equals(delete)) {
                        listCopy.remove(i);
                    }
                }

                FileOutputStream fosS = new FileOutputStream(messageHistorySender, false);
                FileOutputStream fosR = new FileOutputStream(messageHistoryReceiver, false);
                PrintWriter pwS = new PrintWriter(fosS);
                PrintWriter pwR = new PrintWriter(fosR);

                for (int j = 0; j < listCopy.size(); j++) {
                    pwS.println(listCopy.get(j));
                    pwR.println(listCopy.get(j));
                }

                pwS.close();
                pwR.close();

                return true;

            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
