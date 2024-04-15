//package Model;
//
//import java.io.*;
//
///**
// * database.UserList
// *
// * This interface outlines all methods used in the database.User class.
// *
// */
//
//public interface UserList {
//
//    String getFirstName();
//
//    String getUsername();
//
//    String getLastName();
//
//    String getBio();
//
//    String getEmail();
//
//    String getPassword();
//
//    File getUserFile();
//
//    File getFriends();
//
//    File getBlock();
//
//    File getFriendRequest();
//
//    boolean getProfileView();
//
//    boolean getMessageCheck();
//
//    boolean setFirstName(String firstName);
//
//    boolean setLastName(String lastName);
//
//    boolean setUsername(String username) throws IOException;
//
//    boolean setEmail(String email);
//
//    boolean setBio(String bio);
//
//    boolean setPassword(String password);
//
//    boolean setProfileView(boolean check);
//
//    boolean setMessageCheck(boolean check);
//
//    boolean setAccountFile();
//
//    boolean checkAccountExists(String username) throws IOException;
//
//    boolean createAccount();
//
//    static User logIn(String username, String passwordInput) {
//        return null;
//    }
//
//    String toString();
//
//    String[] viewFriends();
//
//    String[] viewBlocks();
//
//    String viewFile();
//
//    String[] viewAllUsers();
//}