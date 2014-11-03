import java.util.HashMap;

import com.sun.javaws.exceptions.InvalidArgumentException;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * WHAT I DO?
 */
public class Node {
    private int ownPort;
    private int otherPort;
    private HashMap<Integer,String> resources = new HashMap<Integer, String>();
    private String message;

    /**
     * Optionally create a Node that knows of another Node.
     */
    public Node(int ownPort, int otherPort) {
        //Open own port, and optionally know about a neighbour Node.
        this.ownPort = ownPort;

        if (otherPort != 0) {
            this.otherPort = otherPort;
        }
    }

    public void setMessage(int messageKey, String message) {
    	resources.put(messageKey,message);
    }

    public void getMessage(int messageKey, int getterPort) {
        if (this.messageKey == messageKey) {
            Put.put(getterPort, messageKey, message);
        }

        else {
            forward(messageKey, getterPort);
        }
    }

    private void forward(int messageKey, int originalPort) {
        if (otherPort != 0) {
            Get.get(messageKey, otherPort, originalPort);
        }
    }
    
    /**
     * Finished the Parser.
	 *	Get: messageKey : getterPort
	 *	Put: messageKey : Message
     * @param getMessage
     */
    private void parseInput(String getMessage)
    {
    	String[] input = getMessage.split(":");
    	if(input[0].equals("Get"))
    	{
    		getMessage(Integer.parseInt(input[1].trim()),Integer.parseInt(input[2].trim()));
    	}
    	else if(input[0].equals("Put"))
    	{
    		setMessage(Integer.parseInt(input[1].trim()), input[2].trim());
    	}
    }

    public static void main(String[] args) throws InvalidArgumentException {
        if (args[0] == null) {
            throw new InvalidArgumentException(args);
        }

        else if (args[1] == null) {
            new Node(Integer.parseInt(args[0]), 0);
            return;
        }

        new Node(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
