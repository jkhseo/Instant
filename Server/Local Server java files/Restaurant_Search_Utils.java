package hello;

import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant_Search_Utils 
{
	private class Bucket
	{
		ArrayList<Restaurant> list = new ArrayList<Restaurant>();
	}
	
	
	ArrayList<Restaurant> restaurants;
	String[] keywords;
	
	public Restaurant_Search_Utils(ArrayList<Restaurant> restaurants)
	{
		this.restaurants = restaurants;
	}
	
	public String Search(String[] keywords)
	{
		HashMap<String, Restaurant> categorizedKeywords = new HashMap<String, Restaurant>();
		
		return "";
	}
	
}
