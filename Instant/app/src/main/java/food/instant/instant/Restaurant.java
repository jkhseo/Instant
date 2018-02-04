package food.instant.instant;

/**
 * Created by mpauk on 1/30/2018.
 */

public class Restaurant {
    private double latitude;
    private double longitude;
    private String name;
    private String address;
    private int Rest_Id;
    private int rating;
    public Restaurant(int id, String name, double lat, double longitude, String address, int rating){
        this.rating = rating;
        this.Rest_Id = id;
        this.latitude = lat;
        this.longitude = longitude;
        this.name = name;
        this.address = address;
    }
    public Restaurant(Restaurant oldres){
        this.name = new String(oldres.name);
        this.address = new String(oldres.address);
        this.latitude = oldres.latitude;
        this.longitude = oldres.longitude;
        this.Rest_Id = oldres.Rest_Id;
        this.rating = oldres.rating;
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

    public int getRest_Id() {
        return Rest_Id;
    }

    public int getRating() {return rating;}
}
