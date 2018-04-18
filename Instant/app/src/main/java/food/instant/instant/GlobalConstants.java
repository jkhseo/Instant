package food.instant.instant;

/**
 * Constants file used to identify Responses for Requests made to the Server
 * Created by mpauk on 2/13/2018.
 */

public class GlobalConstants {
    /**
     * Response Code for Fuzzy Search GET request
     */
    public static final int FUZZY_SEARCH_CODE = 99;
    /**
     * Response Code for Restaurant Info Get Request
     */
    public static final int RESTAURANT_SEARCH_CODE = 77;
    /**
     * Response Code for Empty Response from any GET Request
     */
    public static final int EMPTY_JSON = 88;
    /**
     * Response Code for Location Permission Request
     */
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 42;
    /**
     * Response Code for Google Maps API Distance GET request
     */
    public static final int GOOGLE_MAPS_DISTANCES = 66;
    /**
     * Response Code for Password GET Request
     */
    public static final int PASSWORD = 33;
    /**
     * Response Code for User Info GET Request
     */
    public static final int USERINFO = 11;
    /**
     * Response Code for Restaurants for a given User GET Request
     */
    public static final int USER_RESTAURANTS = 22;
    /**
     * Response Code for Orders for a user GET Request
     */
    public static final int ORDERS = 21;
    /**
     * Response Code for Confirmed Orders
     */
    public static final int ORDERSCON = 23;
    /**
     * Response Code for Completed Orders
     */
    public static final int ORDERSCOM = 24;

    /**
     * Response Code for Confirmation of Order POST Request
     */

    public static final int ORDER_SUBMISSION_RESPONSE = 17;
    /**
     * Response Code for QR Code GET Request
     */
    public static final int QRCODE = 109;
    /**
     * Response Code for information about a Restaurant GET Request
     */
    public static final int RESTAURANT_INFO = 34;
    /**
     * Response Code for status of an Order GET Request
     */
    public static final int UPDATE_STATUS = 145;
    /**
     * Response Code for adding a Food POST Request
     */
    public static final int ADD_FOOD = 146;
    /**
     * Response for updating a Food POST Request
     */
    public static final int UPDATE_FOOD = 147;

    public static final int RSA_KEY= 999;
}
