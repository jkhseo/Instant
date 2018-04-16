package food.instant.instant;

/**
 * Created by mpauk on 1/30/2018.
 * This class represents a Restaurant, with all the required information
 * needed to load the necessary information for the user, and the server.
 */
public class Restaurant {
    private double latitude;
    private double longitude;
    private String name;
    private String address;
    private double rating;
    private double distance;
    private String cuisineMain;
    private String cuisineSec;
    private int Rest_ID;

    /**
     * Constructor for the restaurant to fill out all the fields at initialization
     * This is used with the database query
     * @param Rest_ID primary key for restuarant in the database
     * @param name name of the restaurant
     * @param lat latitude value of the restaurant
     * @param longitude longitude value of the restaurant
     * @param address address of the restaurant
     * @param rating rating of the restaurant
     */
    public Restaurant(int Rest_ID,String name, double lat, double longitude, String address, double rating){
        this.rating = rating;
        this.latitude = lat;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.distance = -1;
        this.Rest_ID = Rest_ID;
    }

    public Restaurant(int Rest_ID,String name, double lat, double longitude, String address, double rating, String mainCuisine, String secCuisine){
        this.rating = rating;
        this.latitude = lat;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.distance = -1;
        this.Rest_ID = Rest_ID;
        this.cuisineMain = mainCuisine;
        this.cuisineSec = secCuisine;
    }

    /**
     * Copy constructor for a restaurant
     * @param oldres the restaurant being copied
     */
    public Restaurant(Restaurant oldres){
        this.name = new String(oldres.name);
        this.address = new String(oldres.address);
        this.latitude = oldres.latitude;
        this.longitude = oldres.longitude;
        this.rating = oldres.rating;
        this.distance=oldres.distance;
        this.Rest_ID = oldres.Rest_ID;
    }

    /**
     * accessor for the latitude
     * @return restaurant's latitude
     */
    public double getLatitude() {
        return latitude;
    }

    public String getCuisineMain() { return cuisineMain; }

    public String getCuisineSec() { return cuisineSec; }

    /**
     * accessor for the longitude
     * @return restaurant's longitude
     */
    public double getLongitude() { return longitude;}

    /**
     * accessor for the name
     * @return restaurant's name
     */
    public String getName() {
        return name;
    }

    /**
     * accessor for the address
     * @return restaurant's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * accessor for the rating
     * @return restaurant's rating
     */
    public double getRating() {return rating;}

    /**
     * accessor for the distance from current location
     * @return restaurant's distance from current location
     */
    public double getDistance() {
        return distance;
    }

    /**
     * method to update restaurant's distance from current location
     * @param distance new distance to store
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * accessor to get the restaurant's ID
     * @return the restaurant's ID
     */
    public int getRest_ID() {
        return Rest_ID;
    }
}
