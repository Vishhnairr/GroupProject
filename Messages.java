import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
//.

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
                if (friendList.get(i).equals(receiveUser.getUsername())) {
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
                File messageHistorySender = new File(sendUser.getUsername() + "_" + receiveUser.getUsername());
                File messageHistoryReceiver = new File (receiveUser.getUsername() + "_"
                        + receiveUser.getUsername());

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

    public boolean deleteMessage(Scanner message) {
        String delete = message.nextLine();
        if (checkReceiver()) {
            ArrayList<String> listOrigilnal = new ArrayList<>();
            ArrayList<String> listCopy = new ArrayList<>();
            try {
                File messageHistorySender = new File(sendUser.getUsername() + "_" + receiveUser.getUsername());
                File messageHistoryReceiver = new File (receiveUser.getUsername() + "_"
                        + receiveUser.getUsername());

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
