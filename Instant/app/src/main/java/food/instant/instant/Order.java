package food.instant.instant;

/**
 * Created by adamdegala on 1/29/18.
 */

public class Order
{
    String restaurant = "";
    String food = "";
    String quanity = "";

    public Order (String restaurant, String food, String quanity)
    {
        this.restaurant = restaurant;
        this.food = food;
        this.quanity = quanity;
    }

    public String toString()
    {
        return food + " " + restaurant + " " + quanity ;
    }


}
