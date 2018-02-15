package food.instant.instant;

/**
 * Created by Peter on 2/13/18.
 */

public class OrderContract {
    private OrderContract(){}

    public static class OrderEntry
    {
        public static final String TABLE_NAME = "order_info";
        public static final String ORDER_ID = "order_id";
        public static final String FOOD_NAME = "food_name";
        public static final String EMAIL = "user_email";

    }
}
