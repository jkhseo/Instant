package food.instant.instant;

import java.util.Arrays;

/**
 * Food data object used to represent a food item belonging to a restaurant
 * Created by mpauk on 2/12/2018.
 * This class represents a food item that will either be part of an order
 * or a used as a list of objects to display a restaurant's menu.
 * It stores all the necessary fields needed to create a database entry for a food item.
 */
public class Food {
    /**
     * Stores the id of the restaurant to which this food instance belongs
     */
    private int Rest_ID;

    /**
     * Stores the id which is unique to the rest_id
     */
    private int Food_ID;

    /**
     * Stores the name of this food instance
     */
    private String Food_Name;

    /**
     * Stores the price of this food instance
     */
    private double Food_Price;

    /**
     * Stores the description of this food instance
     */
    private String Food_Desc;

    /**
     * Stores the id of the menu to which this food instance belongs to
     */
    private int Menu_Id;


    /**
     * Stores the main food tags of this food instance
     */
    private String Food_Tags_Main;


    /**
     * Stores the secondary food tags of this food instance
     */
    private String Food_Tags_Secondary;

    /**
     * Constructor to create a food object using from the data returned in a server get request
     * @param Rest_ID id of the restaurant
     * @param Food_Name name of the food
     * @param Food_Price price of the food
     * @param Food_Desc description of the food
     * @param Menu_ID menu id of the food
     * @param Food_Tags_Main main tags of the food
     * @param Food_Tags_Secondary secondary tags of the food
     * @param Food_ID id of the food (its unique to its rest_id)
     */
    public Food(int Rest_ID, String Food_Name, double Food_Price, String Food_Desc, int Menu_ID, String Food_Tags_Main, String Food_Tags_Secondary, int Food_ID){
        this.Rest_ID = Rest_ID;
        this.Food_Name = Food_Name;
        this.Food_Price = Food_Price;
        this.Food_Desc = Food_Desc;
        this.Menu_Id = Menu_ID;
        this.Food_Tags_Main = Food_Tags_Main;
        this.Food_Tags_Secondary = Food_Tags_Secondary;
        this.Food_ID=Food_ID;

    }

    /**
     * Constructor used when adding food items to an order on the customer side
     * @param Rest_ID id of the restaurant
     * @param Food_Name name of the food
     * @param Food_Price price of the food
     * @param Food_ID id of the food
     */
    public Food(int Rest_ID,String Food_Name,double Food_Price,int Food_ID){
        this.Rest_ID = Rest_ID;
        this.Food_Name = Food_Name;
        this.Food_Price = Food_Price;
        this.Food_ID=Food_ID;
    }

    /**
     * Copy constructor used when displaying the food items that have been added
     * to a user's current cached order
     * @param oldFood the old food item that is being copied
     */
    public Food(Food oldFood){
        this.Rest_ID = oldFood.Rest_ID;
        this.Food_Name = oldFood.Food_Name;
        this.Food_Price = oldFood.Food_Price;
        this.Food_Desc = oldFood.Food_Desc;
        this.Menu_Id = oldFood.Menu_Id;
        this.Food_Tags_Main = oldFood.Food_Tags_Main;
        this.Food_Tags_Secondary = oldFood.Food_Tags_Secondary;
        this.Food_ID = oldFood.Food_ID;
    }

    /**
     * Accessor for the rest_id
     * @return the rest_id
     */
    public int getRest_ID() {
        return Rest_ID;
    }

    /**
     * Accessor for the food name
     * @return the food name
     */
    public String getFood_Name() {
        return Food_Name;
    }

    /**
     * Accessor for the food price
     * @return the food price
     */
    public double getFood_Price() {
        return Food_Price;
    }

    /**
     * Accessor for the food description
     * @return the food description
     */
    public String getFood_Desc() {
        return Food_Desc;
    }

    /**
     * Accessor for the menu id
     * @return the menu id
     */
    public int getMenu_Id() {
        return Menu_Id;
    }

    /**
     * Accessor for the main food tags
     * @return the main food tags
     */
    public String getFood_Tags_Main() {
        return Food_Tags_Main;
    }

    /**
     * Accessor for the secondary food tags
     * @return the secondary food tags
     */
    public String getFood_Tags_Secondary() {
        return Food_Tags_Secondary;
    }

    /**
     * Accessor for the food id
     * @return the food id
     */
    public int getFood_ID() {
        return Food_ID;
    }

}
