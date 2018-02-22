package food.instant.instant;

import java.util.ArrayList;

/**
 * Created by Peter on 2/20/18.
 */

public class Order {
    private int Rest_ID;
    private int User_ID;
    private ArrayList<String> foods;
    private String comments;

    public Order(int Rest_ID, int User_ID, ArrayList<String> foods, String comments)
    {
        this.Rest_ID = Rest_ID;
        this.User_ID = User_ID;
        this.foods = foods;
        this.comments = comments;
    }

    public Order(int Rest_ID, int User_ID)
    {
        this.Rest_ID = Rest_ID;
        this.User_ID = User_ID;
        this.foods = new ArrayList<String>();
        this.comments = "";
    }

    public int getRest_ID() {return this.Rest_ID;}

    public int getUser_ID() {return this.User_ID;}

    public ArrayList<String> getFoods() {return this.foods;}

    public String getComments() {return this.comments;}
}
