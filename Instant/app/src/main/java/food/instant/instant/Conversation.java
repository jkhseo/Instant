package food.instant.instant;

import java.util.ArrayList;

/**
 * Created by mpauk on 3/30/2018.
 */

public class Conversation {

    private ArrayList<Message> messages;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private int id;
    private int Rest_ID;
    public Conversation(String type,int id, int Rest_ID){
        this.type = type;
        this.id = id;
        this.Rest_ID = Rest_ID;
        this.name = null;
    }
    public Conversation(ArrayList<Message> messages,String type, int id,int Rest_ID){
        this.messages=messages;
        this.type=type;
        this.id=id;
        this.Rest_ID= Rest_ID;
        this.name = null;

    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void addMessage(Message message){
        messages.add(message);
    }
    public String getLastMessage(){
        if(messages.size()!=0)
            return messages.get(messages.size() - 1).getMessage();
        else
            return null;
    }

    public void setType(String type) {
        this.type = type;
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
    public int getRest_ID() {
        return Rest_ID;
    }
    public boolean isAllRead(){
        if(messages!=null&&messages.size()!=0){
            if(messages.get(messages.size()-1).getRead()==0){
                return false;
            }
        }
        return true;
    }
}
