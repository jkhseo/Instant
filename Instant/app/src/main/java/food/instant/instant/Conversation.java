package food.instant.instant;

import java.util.ArrayList;

/**
 * Created by mpauk on 3/30/2018.
 */

public class Conversation {
    private ArrayList<Message> messages;
    private String type;
    private int id;
    public Conversation(ArrayList<Message> messages,String type, int id){
        this.messages=messages;
        this.type=type;
        this.id=id;

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
}
