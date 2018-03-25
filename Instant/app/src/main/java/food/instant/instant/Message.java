package food.instant.instant;

/**
 * Created by mpauk on 3/20/2018.
 */

public class Message {
    private String message;

    public String getRecieverType() {
        return recieverType;
    }

    public void setRecieverType(String recieverType) {
        this.recieverType = recieverType;
    }

    public int getRecieverID() {
        return recieverID;
    }

    public void setRecieverID(int recieverID) {
        this.recieverID = recieverID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    private String recieverType;
    private int recieverID;
    private int senderID;
    private String senderType;
    public Message(int recieverID,String userType, String message,String senderType,int senderID){
        this.recieverID=recieverID;
        this.recieverType=userType;
        this.message=message;
        this.senderType = senderType;
        this.senderID = senderID;
    }
    public Message(String formattedMessage){
        this.recieverType = formattedMessage.substring(0,5);
        this.recieverID = Integer.parseInt(formattedMessage.charAt(5)+"");
        int endMessage = formattedMessage.lastIndexOf("::");
        this.message= formattedMessage.substring(8,endMessage);
        this.senderType = formattedMessage.substring(endMessage+2,endMessage+7);
        this.senderID = Integer.parseInt(formattedMessage.charAt(endMessage+7)+"");
    }
    public String getFormattedMessage(){
        return recieverType+recieverID+"::"+message+"::"+senderType+senderID;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
