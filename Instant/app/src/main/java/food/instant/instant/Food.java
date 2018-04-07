package food.instant.instant;

import java.util.Arrays;

/**
 * Food data object used to represent a food item belonging to a restaurant
 * Created by mpauk on 2/12/2018.
 */

public class Food {
    /**
     * ID of Restaurant
     */
    private int Rest_ID;
    /**
     * ID of this Food in database
     */
    private int Food_ID;
    /**
     * Name of this Food
     */
    private String Food_Name;
    /**
     * Price of this Food
     */
    private double Food_Price;
    /**
     * Description of this Food
     */
    private String Food_Desc;
    /**
     * ID of Menu for this Food
     */
    private int Menu_Id;
    /**
     * Main Tag associated with this Food
     */
    private String Food_Tags_Main;
    /**
     * Secondary Tag associated with this Food
     */
    private String Food_Tags_Secondary;

    /**
     * Constructor used for all fields
     * @param Rest_ID ID of Restaurant
     * @param Food_Name Name of this Food
     * @param Food_Price Price of this Food
     * @param Food_Desc Description of this Food
     * @param Menu_ID ID of Menu for this Food
     * @param Food_Tags_Main Main Tag associated with this Food
     * @param Food_Tags_Secondary Secondary Tag associated with this Food
     * @param Food_ID ID of this Food in database
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
     * Alternate Constructor used for a Food item that belongs to a Order Object
     * @param Rest_ID ID of Restaurant
     * @param Food_Name Name of this Food
     * @param Food_Price Price of this Food
     * @param Food_ID ID of this Food in database
     */
    public Food(int Rest_ID,String Food_Name,double Food_Price,int Food_ID){
        this.Rest_ID = Rest_ID;
        this.Food_Name = Food_Name;
        this.Food_Price = Food_Price;
        this.Food_ID=Food_ID;
    }

    /**
     * Copy Constructor
     * @param oldFood Food object to copy
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
     * Gets Restaurant ID instance variable
     * @return Restaurant ID
     */
    public int getRest_ID() {
        return Rest_ID;
    }

    /**
     * Gets Food Name instance variable
     * @return Food Name
     */
    public String getFood_Name() {
        return Food_Name;
    }

    /**
     * Gets Food Price instance variable
     * @return Food Price
     */
    public double getFood_Price() {
        return Food_Price;
    }

    /**
     * Gets Food Description instance variable
     * @return Food Description
     */
    public String getFood_Desc() {
        return Food_Desc;
    }

    /**
     * Gets Food ID instance variable
     * @return Food ID
     */
    public int getFood_ID() {
        return Food_ID;
    }

}
