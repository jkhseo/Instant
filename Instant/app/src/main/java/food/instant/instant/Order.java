package food.instant.instant;

import java.util.ArrayList;


public class Order {
    private int Order_ID;
    private char status;
    private int Dummy_PK;  //
    private String comments;
    private int Order_Confirmation_Code;  //
    private int Food_Quantity;
    private String date_submitted;  //
    private String date_pickedUp;  //
    private int User_ID;
    private int Rest_ID;
    private String User_First_Name;
    private String User_Last_Name;
    private String User_Email;
    private String Restaurant_Name;
    private Food food;



    public Order(int Order_ID,int User_ID, Food food,String comments,int Food_Quantity, String Restaurant_Name,char status, int Dummy_PK, int Order_Confirmation_Code, String date_submitted, String date_pickedUp, String user_first_name, String user_last_name, String user_email, int rest_id)
    {
        this.Order_ID = Order_ID;
        this.User_ID=User_ID;
        this.food=food;
        this.comments=comments;
        this.Restaurant_Name = Restaurant_Name;
        this.Food_Quantity = Food_Quantity;
        this.status = status;
        this.Dummy_PK = Dummy_PK;
        this.Order_Confirmation_Code = Order_Confirmation_Code;
        this.date_submitted = date_submitted;
        this.date_pickedUp = date_pickedUp;
        this.User_First_Name = user_first_name;
        this.User_Last_Name = user_last_name;
        this.User_Email = user_email;
        this.Rest_ID = rest_id;
    }
    public Order(int Order_ID,int User_ID, Food food,String comments,int Food_Quantity, String Restaurant_Name,char status, String date_pickedUp)
    {
        this.Order_ID = Order_ID;
        this.User_ID=User_ID;
        this.food=food;
        this.comments=comments;
        this.Restaurant_Name = Restaurant_Name;
        this.Food_Quantity = Food_Quantity;
        this.status = status;
        this.date_pickedUp = date_pickedUp;
    }

    public Order(int User_ID)
    {
        this.User_ID=User_ID;
        this.comments="";
    }

    public int getUser_ID() { return this.User_ID;}

    public Food getFood() {return this.food;}

    public String getComments() {return this.comments;}

    public String getRestaurant_Name() {
        return Restaurant_Name;
    }

    public void setRestaurant_Name(String restaurant_Name) {
        Restaurant_Name = restaurant_Name;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getFood_Quantity() {
        return Food_Quantity;
    }

    public void setFood_Quantity(int food_Quantity) {
        Food_Quantity = food_Quantity;
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public void setOrder_ID(int order_ID) {
        Order_ID = order_ID;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public int getDummyPK(){ return this.Dummy_PK; }

    public int getOrder_Confirmation_Code(){ return this.Order_Confirmation_Code; }

    public String getDate_submitted(){ return this.date_submitted; }

    public String getDate_pickedUp(){ return this.date_pickedUp; }

    public String getUser_First_Name(){ return this.User_First_Name; }

    public String getUser_Last_Name(){ return this.User_Last_Name; }

    public String getUser_Email(){ return this.User_Email; }

    public int getRest_ID(){ return this.Rest_ID; }
}
