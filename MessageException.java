/**
 * Project 4 - MessageException
 * 
 * This class is an exception to be thrown when the sender or recipient of a message
 * can't be found.
 */
public class MessageException extends Exception{
    public MessageException(String message) {
        super(message);
    }
}
