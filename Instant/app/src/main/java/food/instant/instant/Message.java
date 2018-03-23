package food.instant.instant;

/**
 * Created by mpauk on 3/20/2018.
 */

public class Message {
    private String message;
    private char userType;
    private int ID;
    public Message(int ID,char userType, String message){
        this.ID=ID;
        this.userType=userType;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public char getUserType() {
        return userType;
    }

    public void setUserType(char userType) {
        this.userType = userType;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
