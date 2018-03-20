package food.instant.instant;

/**
 * Created by Peter on 2/13/18.
 */

public class OrderContract {
    private OrderContract(){}

    public static class OrderEntry
    {
        public static final String TABLE_NAME = "order_info";
        public static final String FOOD_ID = "food_id";
        public static final String COMMENTS = "comments";
        public static final String RESTAURANT_ID = "restaurant_id";
        public static final String FOOD_NAME = "food_name";
        public static final String RESTAURANT_NAME = "restaurant_name";
        public static final String FOOD_PRICE = "food_price";
        public static final String FOOD_QUANTITY = "food_quantity";
        public static final String ORDER_STATUS = "order_status";

    }
}
