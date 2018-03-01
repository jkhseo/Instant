package food.instant.instant;

import java.util.ArrayList;

/**
 * Created by mpauk on 2/24/2018.
 */

public class Order {
    private int User_ID;
    private int Food_Quantity;
    private String Restaurant_Name;
    private Food food;
    private String comments;

    public Order(int User_ID, Food food,String comments,int Food_Quantity, String Restaurant_Name)
    {
        this.User_ID=User_ID;
        this.food=food;
        this.comments=comments;
        this.Restaurant_Name = Restaurant_Name;
        this.Food_Quantity = Food_Quantity;
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
}
