# CS 180 GroupProject
## Group members

Alexia Gil, Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson

## Instructions

When the program starts running, it brings up a welcome message and a menu that allows the user to choose between creating an account, logging in, and exiting. When being run for the first time, the log-in functionality won’t work as no accounts exist to log into. After a user creates an account/logs in, another menu appears with the options of viewing a list of all accounts on the platform, searching for a specific account, or viewing a list of the user’s friends. Upon choosing the first option, the program displays a list of every account and prompts the user to choose an account to look into. After selecting an account the “viewing all accounts” and “search for account” functionalities have the same outcome as both will display a menu that allows the user to see the selected account’s profile, send a friend request, or exit to the main menu. If at the main menu the user chooses to see their friends list, the list and a menu will appear that prompts the use to message their friends (select a friend to message and type the content of the message), view friend requests (view a list of pending friend requests sent to the user by other accounts with the options of approving or declining each friend request), remove a friend (select a current friend to be removed from the friends list), block a friend (removes the friend and prevents them from interacting with the user again), and exiting to the main menu. When a user exits the program, they are prompted to choose whether or not to edit their account info before finally logging out

## MainMenu
Holds all the functionality for users to interact with the system. First a welcome message is displayed and then the user logs in or creates an account. When a user logs in to their account the menu gives them different options. The menu allows the user to loop through and perform as many actions as they want, and then when they are done they exit. Once they exit, the menu prompts them if they want to make any edits to their account before they log out, and they can make as many edits as they like. After the user is done editing, a goodbye message is displayed and the program ends.

## User

This class allows for the manipulation of all the basic information about a user. This information is: the first and last names of the user, the email and password of the user, the bio, and the account file object for the file that holds all the login information about a user. This class also contains the basic functions to create and edit accounts and to log in to an account. All of this account information is stored in an account file, and every user has their own account file that is created when they create an account, and used to remember the user for when they log in again. A user can edit their first and last names, their bio, and their password, but not their email as most files associated with a user are created using their email as the unique identifier.

## Friends

This class extends the User class and allows for a user to make, accept, and decline friend requests, block other users, view their friends list, and remove friends from the list. The friends list is stored as a file associated with the user and is updated every time they perform one of the above actions.

## MessageList

This class allows for a user to message one of their friends by creating a message object that contains the message being sent, the message sender, and the message recipient. The class contains methods that check if the recipient is a friend of the sender, create and update a file for both the sender and the recipient that contains the message history of the two users, and delete messages from the message history.

## Server

This class recieves commands from various clients and accesses the database of the social media platform to read from and write to the files of the platform's users for platform functions like creating an account, editing an account, logging in and out, sending friend requests, accepting and denying friend requests, and messaging and blocking other users.

## Client

This class runs the GUI that users will interact with when they use the social media platform. It stores the commands and data that the users choose and input to send to the platform's server and displays interfaces based on data received from the server.

## Testing

We created three accounts on the platform, one to represent the “current” user and the other two represent “other” users. The current user sends friend requests to both of the other users and each of the other users log in and accept the friend request. The current user logs back in and messages one of the new friends and removes and blocks the other one. The message recipient logs back in and replies to the message. Then, the blocked user logs back and tries to message the user that blocked them but can’t. This tests all the functionalities of our program.
