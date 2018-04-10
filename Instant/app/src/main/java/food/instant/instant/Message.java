package food.instant.instant;

/**
 * Created by mpauk on 3/20/2018.
 */

/**
 * Data object that represents a message, used for chat system
 */
public class Message {
    /**
     * Contains the content of the message
     */
    private String message;
    /**
     * Type of the receiver of the message, either Vendor or Customer
     */
    private String recieverType;
    /**
     * ID of the reciever of the message
     */
    private int recieverID;
    /**
     * ID of the sender of the message
     */
    private int senderID;
    /**
     * Type of the sender of the message, either Vendor or Customer
     */
    private String senderType;

    /**
     * Constructor for Message Object containing all fields.
     * @param recieverID ID of the reciever of the message
     * @param recieverType Type of the receiver of the message, either Vendor or Customer
     * @param message Contains the content of the message
     * @param senderType Type of the sender of the message, either Vendor or Customer
     * @param senderID ID of the sender of the message
     */
    public Message(int recieverID,String recieverType, String message,String senderType,int senderID){
        this.recieverID=recieverID;
        this.recieverType=recieverType;
        this.message=message;
        this.senderType = senderType;
        this.senderID = senderID;
    }

    /**
     * Alternate Constructor where formatted message is given as input and parsed to
     * provide values for all of the fields
     * @param formattedMessage formatted message to be parsed
     */
    public Message(String formattedMessage){
        this.recieverType = formattedMessage.substring(0,5);
        this.recieverID = Integer.parseInt(formattedMessage.substring(5,formattedMessage.indexOf("::")));
        int endMessage = formattedMessage.lastIndexOf("::");
        this.message= formattedMessage.substring(formattedMessage.indexOf("::")+2,endMessage);
        this.senderType = formattedMessage.substring(endMessage+2,endMessage+7);
        this.senderID = Integer.parseInt(formattedMessage.substring(endMessage+7));
    }

    /**
     * Get formatted message of form: RecieverTypeRecieverID::message::SenderTypeSenderID
     * @return formatted message
     */
    public String getFormattedMessage(){
        return recieverType+recieverID+"::"+message+"::"+senderType+senderID;
    }

    /**
     * Get message text
     * @return message text
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets reciever type instance variable
     * @return reciever type
     */
    public String getRecieverType() {
        return recieverType;
    }

    /**
     * gets receiver ID instance variable
     * @return receiver ID
     */
    public int getRecieverID() {
        return recieverID;
    }

    /**
     * Gets sender ID instance variable
     * @return Sender ID
     */
    public int getSenderID() {
        return senderID;
    }

    /**
     * Gets Sender type instance variable
     * @return Sender Type
     */
    public String getSenderType() {
        return senderType;
    }


}
