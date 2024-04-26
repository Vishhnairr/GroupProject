/**
 * Message Exception
 * 
 * This class is an exception to be thrown when the sender or recipient of a message
 * can't be found.
 *
 * @author Lisa Luo, Zixian Liu, Viswanath Nair, Braeden Patterson, Alexia Gil, lab sec 13
 *
 * @version March 31, 2024
 *
 */
public class MessageException extends Exception{
    public MessageException(String message) {
        super(message);
    }
}
