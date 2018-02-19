package food.instant.instant;

/**
 * Created by mpauk on 1/30/2018.
 */

public class Restaurant {
    private double latitude;
    private double longitude;
    private String name;
    private String address;
    private double rating;
    private double distance;
    private int Rest_ID;
    public Restaurant(int Rest_ID,String name, double lat, double longitude, String address, double rating){
        this.rating = rating;
        this.latitude = lat;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
        this.distance = -1;
        this.Rest_ID = Rest_ID;
    }
    public Restaurant(Restaurant oldres){
        this.name = new String(oldres.name);
        this.address = new String(oldres.address);
        this.latitude = oldres.latitude;
        this.longitude = oldres.longitude;
        this.rating = oldres.rating;
        this.distance=oldres.distance;
        this.Rest_ID = oldres.Rest_ID;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() { return longitude;}

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getRating() {return rating;}

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getRest_ID() {
        return Rest_ID;
    }

    public void setRest_ID(int rest_ID) {
        Rest_ID = rest_ID;
    }
}
