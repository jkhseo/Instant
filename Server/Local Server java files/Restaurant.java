package hello;

import java.util.ArrayList;

public class Restaurant implements Comparable
{
	
	public String name;
	public int ID;
	public String cuisine_main;
	public String cuisine_secondary;
	public String Rest_Address;
	public String Rest_Coordinate_Lat;
	public String Rest_Coordinate_Long;
	
	public String[] keywords;
	
	public int rank = 0;//Rank for searching purposes
	
	public Restaurant(String name, int ID, String cuisine_main, String cuisine_secondary, String Rest_Address, String Rest_Coordinate_Lat, String Rest_Coordinate_Long, String[] keywords) 
	{
		this.name = name;
		this.ID = ID;
		this.cuisine_main = cuisine_main;
		this.cuisine_secondary = cuisine_secondary;
		this.Rest_Address = Rest_Address;
		this.Rest_Coordinate_Lat = Rest_Coordinate_Lat;
		this.Rest_Coordinate_Long = Rest_Coordinate_Long;
		this.keywords = keywords; //Careful. This is a shallow copy ;)
	}

	@Override
	public int compareTo(Object o)
	{
		// TODO Auto-generated method stub
		if(o!= null && ((Restaurant) o).rank > this.rank)
			return 1;
		if(o!= null && ((Restaurant) o).rank < this.rank)
			return -1;
		return 0;
	}
	
}	
