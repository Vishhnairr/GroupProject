# CS 180 GroupProject
## Group members

Alexia Gil, Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson

## Instructions
To run this program, you need to download all the files and run Servers.java first, and than you can run multiple Clients as you want. Every Client will have a signle GUI face. Please follow the instruction
and interact with you friends.  
To test this program, you should run UserTest.java FIRST!!! And then you can run FriendTest.java and TestMessage.java.

## Introduction
Our social media platform provides a dynamic environment for user interaction through a client-server architecture that handles basic social networking functions. The platform facilitates user interactions through account management, dating interactions, messaging and other features. We have incorporated design, real-time updates, and permanent storage to make the platform user-friendly, efficient, and powerful.

## A list of who submitted which parts of the assignment
### Zixian: Submitted Vocareum workspace.
### Alexia: Submitted Presentation video.
### Breaden: Submitted Report.

## Features
### Account Management
Account Creation: Users can sign up by providing essential information, which is securely stored in individual files.    
Login System: Secure login functionality allows users to access their profiles and interact with the platform.  
Profile Editing: Users can update their personal information, including names, biographies, and preferences related to profile visibility and message reception.

### Friendship Management
Friend Requests: Users can send, accept, or decline friend requests, enabling them to build a network of connections.  
Friend List Management: The platform allows users to view their current friends, remove friends, or block users, which also prevents any further interactions.

### Messaging
Direct Messaging: Users can send private messages to their friends.  
Message History: Users can view and manage their message history, including deleting messages to keep their conversation relevant and tidy.

### Search Functionality
User Search: Users can search for other profiles using usernames. This feature includes auto-complete suggestions to help find users quickly.

### User Profiles
Profile Viewing: Users can view their own and others’ profiles, with the visibility contingent upon the user’s privacy settings.  
Dynamic Profiles: Profiles dynamically update based on user interactions and changes to the account settings.

### Real-time Interaction
Immediate Updates: Changes made by users, like sending messages or updating profiles, are reflected in real-time, enhancing the interactive experience.  
Responsive GUI: The graphical user interface is designed to be intuitive and responsive, ensuring that users can navigate and use the platform effortlessly.

### Advanced Features
Block System: Users can block other users, which restricts those blocked from sending messages or friend requests.  
Friend Request Management: The platform offers a specialized interface for managing incoming friend requests, including options to accept or reject with immediate feedback.

### Client-Server Communication
Robust Server Backend: Handles requests like account creation, login, messaging, and more, ensuring data consistency and security.  
Efficient Data Handling: The server efficiently processes and stores user data, while the client handles user interactions, making the platform scalable and robust.

## Each class introduction
### ClientList
Functionality: This interface declares methods that any client-side class should implement to communicate with the server.  
Relationship: Implemented by the Clients class to ensure it adheres to the protocol defined for client operations.

### Clients
Functionality: Manages the client-side user interface and network communication. It handles user inputs, sends requests to the server, and processes responses from the server.  
Relationship: Implements ClientList and interacts with Servers for backend communication.

### ServerList
Functionality: An interface that defines methods that the server should implement to handle client requests.  
Relationship: Implemented by the Servers class to standardize the server operations.

### Servers
Functionality: Manages the server-side operations, including client connections, requests processing, and data management.  
Relationship: Interacts with Clients to handle the network communications.

### UserList
Functionality: An interface that declares the methods related to user account management.  
Relationship: Implemented by User to define standard user operations.

### User
Functionality: Encapsulates all user-related data and provides methods for account management such as login, profile viewing, and editing.  
Testing: Tested by UserTest.java  
Relationship: Central to many operations; interacts with Clients, Friends, and other classes that manage user-related data.

### UserTest
Functionality: A class dedicated to testing the User class functionalities.  
Relationship: Tests the User class for reliability and correctness.

### FriendList
Functionality: An interface that outline the methods related to friend operations such as adding, removing, blocking, and listing friends.  
Relationship: Implemented by the Friends class to define friend-related operations.

### Friends
Functionality: Handles friend request operations, including making, accepting, and declining friend requests, as well as blocking and unblocking users.  
Testing: Tested by FriendTest.java.  
Relationship: Interacts with User for accessing user-specific data and implement FriendList.

### FriendTest
Functionality: A test class for the Friends functionality.  
Relationship: Tests the Friends class and its interaction with User.

### MessageList
Functionality: An interface that defines the methods for message handling, including sending, deleting, and retrieving message histories.  
Relationship: Implemented by Message.

### Message
Functionality: Represents a message object containing details like sender, recipient, and the message content.  
Testing: Tested by TestMessage.java  
Relationship: Interacts with User and used by Client.

### TestMessage
Functionality: A test class for Message related operations.  
Testing: Includes unit tests for the creation, sending, and receiving of Message objects.  
Relationship: Tests the Message class's integration and functionality.

### MessageHistoryEditor
Functionality: Provides a user interface for viewing and managing the message history between users.  
Relationship: Used by Clients to offer users the ability to view and edit message histories.

### EditableUserProfile
Functionality: Provides a graphical user interface for users to edit and save their profile information.  
Relationship: Used by the Clients class when a user wishes to edit their profile.

### GeneralSelectionPane
Functionality: A generic class that provides a dropdown selection pane used across various parts of the client interface.  
Relationship: Used by Clients and possibly other GUI classes to provide a consistent selection interface.

### TextBoxes
Functionality: Provides a GUI component for text input, which could be used across various parts of the client interface for data entry.  
Relationship: Could be used by Clients or other GUI classes requiring text input.

### UserProfileDisplay
Functionality: Provides a GUI for displaying user profiles.  
Relationship: Used by Clients when a profile needs to be viewed.
