package Model;

import java.io.*;
import java.util.ArrayList;

/**
 * MessageList
 *
 * This class allows for a user to message one of their friends by creating a message object
 * that contains the message being sent, the message sender, and the message recipient. The
 * class contains methods that check if the recipient is a friend of the sender, create and update
 * a file for both the sender and the recipient that contains the message history of the two
 * users, and delete messages in the message history.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version March 31, 2024
 *
 */
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

    public synchronized boolean verifyUser() {
        File allUserNames = new File("All_User_Info.txt");
        ArrayList<String> allUsers = new ArrayList<>();
        try {
            FileReader fr = new FileReader(allUserNames);
            BufferedReader bfr =  new BufferedReader(fr);
            String line = bfr.readLine();

            while (line != null) {
                allUsers.add(line);
                line = bfr.readLine();
            }
            bfr.close();
        } catch (Exception e) {
            return false;
        }

        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).equals(this.receiveUser)) {
                return true;
            }
        }
        return false;
    }
    public synchronized boolean sendMessage() {
        try {
            File directory = new File("messages");
            if (!directory.exists()) {
                directory.mkdir();
            }
            File messageHistorySender = new File("messages/" + sendUser.getUsername() + "_" + receiveUser + ".txt");
            File messageHistoryReceiver = new File("messages/" + receiveUser + "_" + sendUser.getUsername() + ".txt");

            messageHistorySender.createNewFile();
            messageHistoryReceiver.createNewFile();

            try (PrintWriter pwSender = new PrintWriter(new FileOutputStream(messageHistorySender, true));
                 PrintWriter pwReceiver = new PrintWriter(new FileOutputStream(messageHistoryReceiver, true))) {
                String formattedMessage = String.format("%s: %s", sendUser.getUsername(), content);
                pwSender.println(formattedMessage);
                pwReceiver.println(formattedMessage);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public synchronized boolean deleteMessage(String delete) {
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
    }

    //View users message history
    public synchronized ArrayList<String> viewMessageHistory() {
        ArrayList<String> list = new ArrayList<>();
        String [] history;
        try {
            File messageHistorySender = new File(sendUser.getUsername() + "_" + receiveUser + ".txt");
            FileReader frS = new FileReader(messageHistorySender);
            BufferedReader bfrS = new BufferedReader(frS);

            String line = bfrS.readLine();

            while (line != null) {
                list.add(line);
                line = bfrS.readLine();
            }

            bfrS.close();
        } catch (Exception e) {
            return list;
        }
        return list;
    }

}