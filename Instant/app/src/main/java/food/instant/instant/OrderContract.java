package food.instant.instant;

/**
 * Created by Peter on 2/13/18.
 * This class stores static variables used with instantiating
 * an SQLite database to store cached order info
 */
public class OrderContract {
    /**
     * Default constructor
     */
    private OrderContract(){}

    /**
     * Class to store table column names
     */
    public static class OrderEntry
    {
        /**
         * order_info column
         */
        public static final String TABLE_NAME = "order_info";

        /**
         * food_id column
         */
        public static final String FOOD_ID = "food_id";

        /**
         * comments column
         */
        public static final String COMMENTS = "comments";

        /**
         * restaurant_id column
         */
        public static final String RESTAURANT_ID = "restaurant_id";

        /**
         * food_name column
         */
        public static final String FOOD_NAME = "food_name";

        /**
         * restaurant_name column
         */
        public static final String RESTAURANT_NAME = "restaurant_name";

        /**
         * food_price column
         */
        public static final String FOOD_PRICE = "food_price";

        /**
         * food_quantity column
         */
        public static final String FOOD_QUANTITY = "food_quantity";

        /**
         * order_status column
         */
        public static final String ORDER_STATUS = "order_status";

    }
}
