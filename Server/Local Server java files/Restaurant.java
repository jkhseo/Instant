package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Restaurant {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer Rest_ID;

    private String Rest_Name;
    
    private String Rest_Coordinate_X;
    
    private String Rest_Coordinate_Y;
    
    private String Rest_Address;
    
    private String Rest_Rating;

	public Integer getId()
	{
		return Rest_ID;
	}

	public void setId(Integer id) {
		this.Rest_ID = id;
	}

	public String getName() {
		return Rest_Name;
	}

	public void setName(String name) {
		this.Rest_Name = name;
	}

	public String get_Rest_Coordinate_X() {
		return Rest_Coordinate_X;
	}

	public void set_Rest_Coordinate_X(String Rest_Coordinate_X) {
		this.Rest_Coordinate_X = Rest_Coordinate_X;
	}
	
	public String get_Rest_Coordinate_Y() {
		return Rest_Coordinate_Y;
	}

	public void set_Rest_Coordinate_Y(String Rest_Coordinate_Y) {
		this.Rest_Coordinate_Y = Rest_Coordinate_Y;
	}

	public String getRestAddress() {
		return Rest_Address;
	}

	public void setRest_Address(String Rest_Address) {
		this.Rest_Address = Rest_Address;
	}
	
	public String getRest_Rating() {
		return Rest_Rating;
	}

	public void setRest_Rating(String Rest_Rating) {
		this.Rest_Rating = Rest_Rating;
	}
}

