package food.instant.instant;

import java.util.ArrayList;


public class Order {
    private int Order_ID;
    private int User_ID;
    private int Food_Quantity;
    private String Restaurant_Name;
    private Food food;
    private String comments;
    private char status;

    public Order(int Order_ID,int User_ID, Food food,String comments,int Food_Quantity, String Restaurant_Name,char status)
    {
        this.Order_ID = Order_ID;
        this.User_ID=User_ID;
        this.food=food;
        this.comments=comments;
        this.Restaurant_Name = Restaurant_Name;
        this.Food_Quantity = Food_Quantity;
        this.status = status;
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
}
