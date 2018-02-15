package hello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Restaurant_Search_Utils 
{
	private class Bucket
	{
		ArrayList<Restaurant> list = new ArrayList<Restaurant>();
	}
	
	
	ArrayList<Restaurant> restaurants;
	HashMap<String, Bucket> catergorizedHashmap = new HashMap<String, Bucket>();
	String[] keywords;
	
	public Restaurant_Search_Utils(ArrayList<Restaurant> restaurants)
	{
		this.restaurants = restaurants;
		for(Restaurant e : restaurants)
		{
			for(String keyword : e.keywords)
			{
				if(!catergorizedHashmap.containsKey(keyword.toLowerCase()))
					catergorizedHashmap.put(keyword.toLowerCase(), new Bucket());
				catergorizedHashmap.get(keyword.toLowerCase()).list.add(e);
			}
		}
	}
	
	public String Search(String[] keywords)
	{

		HashMap<Integer, Restaurant> results = new HashMap<Integer, Restaurant>();
		for(String e : keywords)
		{
			if(catergorizedHashmap.containsKey(e.toLowerCase()))
			{
				System.out.println("CMAP contains " + e +" with a size " + catergorizedHashmap.get(e.toLowerCase()).list.size() );
				for(Restaurant r : catergorizedHashmap.get(e.toLowerCase()).list)
				{
					System.out.println("Restaurant " + r.name + " added! ");
					if(!results.containsKey(r.ID))
						results.put(r.ID, r);
					results.get(r.ID).rank++; 
				}
			}
		}
		
		//Now Results contains all the restaurants relevant to the keywords. 
		//Put them into the array for sorting purposes
		Restaurant[] relevantRestaurants = new Restaurant[results.size()];
		int index= 0;
		
		for(Integer e :results.keySet())
		{
			relevantRestaurants[index] = results.get(e);
			index++;
		}
		System.out.println("Num of Results " + relevantRestaurants.length);
		Arrays.sort(relevantRestaurants);
		String JSONarray = arrayTOJSON(relevantRestaurants);
		
		System.out.println("Search Results");
		System.out.println(JSONarray);
		
		return JSONarray;
	}
	
	private String arrayTOJSON(Restaurant[] rest)
	{
		String results = "";
		
		//Start JSON Array
		results = "{ \"Restaurant_Search_Results\": [ ";
		
		for(Restaurant e : rest)
		{
			results += "{ \"Rest_ID\" : \"" + e.ID + "\",";
			results += " \"Rest_Name\" : \"" + e.name + "\",";
			results += " \"Rank\" : \"" + e.rank + "\",";
			results += " \"Rest_Type_Cuisine_Main\" : \"" + e.cuisine_main + "\",";
			results += " \"Rest_Type_Cuisine_Secondary\" : \"" + e.cuisine_secondary + "\",";
			results += " \"Rest_Keywords\" : \"" + e.keywords + "\"} ,";
			
		}
		results = results.substring(0, results.length()-1); //Erase that last comma
		//End JSON array
		results += "] }";
		
		return results;
	}
	
}
