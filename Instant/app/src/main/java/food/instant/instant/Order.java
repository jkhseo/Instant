package food.instant.instant;

import java.util.ArrayList;

/**
 * This class is represents part of an order that a user will submit to a restaurant.
 * Any actual order may actually be made up of multiple Order classes with the same Order_ID
 * It stores all the necessary information related to the who, what, when, and where
 * of a user's order. It also has the necessary data required to make a database entry.
 */
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


    /**
     * Constructor for Order to initialize it by passing all the data fields to it.
     * Useful when creating Order objects out of Database query results
     * @param Order_ID key to associate 'pieces' of an order together as a group
     * @param User_ID uid the person who placed the order
     * @param food the food object which the order is requesting
     * @param comments additional comments about the order
     * @param Food_Quantity quantity of current food item that this object references
     * @param Restaurant_Name name of the restaurant that this order is being sent to
     * @param status data field to hold whether this order is pending, confirmed, or completed
     * @param Dummy_PK dummy primary key field that is needed for the database
     * @param Order_Confirmation_Code unique code to verify that the correct person has picked up this order
     * @param date_submitted the time when this order was sent from the phone
     * @param date_pickedUp the time when this order will be picked up
     * @param user_first_name first name of the person who sent this order
     * @param user_last_name last name of the person who sent this order
     * @param user_email username of the person who sent this order
     * @param rest_id id of the restaurant to which this order is being sent
     */
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

    /**
     * Constructor for Order needed when user is adding food items before submitting
     * their order.
     * @param Order_ID key to associate 'pieces' of an order together as a group
     * @param User_ID uid the person who placed the order
     * @param food the food object which the order is requesting
     * @param comments additional comments about the order
     * @param Food_Quantity quantity of current food item that this object references
     * @param Restaurant_Name name of the restaurant that this order is being sent to
     * @param status data field to hold whether this order is pending, confirmed, or completed
     * @param date_pickedUp the time when this order will be picked up
     */
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

    /**
     * Constructor needed as part of the logic in storing the order into the sqlite database
     * @param User_ID id of the user submitting this order
     */
    public Order(int User_ID)
    {
        this.User_ID=User_ID;
        this.comments="";
    }

    /**
     * Accessor for this order's user id
     * @return user id
     */
    public int getUser_ID() { return this.User_ID;}

    /**
     * Accessor for this order's food object
     * @return food object
     */
    public Food getFood() {return this.food;}

    /**
     * Accessor for this order's comments
     * @return comments
     */
    public String getComments() {return this.comments;}

    /**
     * Accessor for this order's restaurant name
     * @return restaurant name
     */
    public String getRestaurant_Name() {
        return Restaurant_Name;
    }

    /**
     * Method to set the restaurant name of an order
     * @param restaurant_Name new restaurant name
     */
    public void setRestaurant_Name(String restaurant_Name) {
        Restaurant_Name = restaurant_Name;
    }

    /**
     * Method to set the food object for this order
     * @param food new food to set it to
     */
    public void setFood(Food food) {
        this.food = food;
    }

    /**
     * Accessor for the food quantity of this order
     * @return the food quantity
     */
    public int getFood_Quantity() {
        return Food_Quantity;
    }

    /**
     * Method to set the food quantity on this order
     * @param food_Quantity the food quantity to set it to
     */
    public void setFood_Quantity(int food_Quantity) {
        Food_Quantity = food_Quantity;
    }

    /**
     * Accessor to get the order id of this order
     * @return the order id
     */
    public int getOrder_ID() {
        return Order_ID;
    }

    /**
     * Method to set the order id of this order
     * @param order_ID new order id to which this is being set
     */
    public void setOrder_ID(int order_ID) {
        Order_ID = order_ID;
    }

    /**
     * Accessor to get the current status of this order
     * @return the order status
     */
    public char getStatus() {
        return status;
    }

    /**
     * Method to set the status of this order
     * @param status status to which this order is being set
     */
    public void setStatus(char status) {
        this.status = status;
    }

    /**
     * Accessor to get the Dummy Primary Key of this order
     * @return the Dummy Primary Key
     */
    public int getDummyPK(){ return this.Dummy_PK; }

    /**
     * Accessor to get the confirmation code of this order
     * @return this order's confirmation code
     */
    public int getOrder_Confirmation_Code(){ return this.Order_Confirmation_Code; }

    /**
     * Accessor to get the date that this order was submitted
     * @return the date that this order was submitted
     */
    public String getDate_submitted(){ return this.date_submitted; }

    /**
     * Accessor to get the time when this order will be picked up
     * @return when this order will be picked up
     */
    public String getDate_pickedUp(){ return this.date_pickedUp; }

    /**
     * Accessor to get the first name of the user that is associated with this order
     * @return this order's user's first name
     */
    public String getUser_First_Name(){ return this.User_First_Name; }

    /**
     * Accessor to get the last name of the user that is associated with this order
     * @return this order's user's last name
     */
    public String getUser_Last_Name(){ return this.User_Last_Name; }

    /**
     * Accessor to get the email of the user that is associated with this order
     * @return this order's user's email
     */
    public String getUser_Email(){ return this.User_Email; }

    /**
     * Accessor to get the restaurant id associated with this order
     * @return this order's restaurant id
     */
    public int getRest_ID(){ return this.Rest_ID; }
}
