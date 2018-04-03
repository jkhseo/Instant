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
	
	private final int EDIT_DISTANCE = 2;
	
	ArrayList<Restaurant> restaurants;
	HashMap<String, Bucket> catergorizedHashmap = new HashMap<String, Bucket>();
	String[] keywords;
	
	/**
	 * 
	 * @param restaurants The list of restaurants to search
	 */
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
			
			//Add name
			if(e.name != null && !catergorizedHashmap.containsKey(e.name.toLowerCase()))
				catergorizedHashmap.put(e.name.toLowerCase(), new Bucket());
			if(e.name != null)
				catergorizedHashmap.get(e.name.toLowerCase()).list.add(e);
			
			//Add Cuisine Tag
			if(e.cuisine_main != null && !catergorizedHashmap.containsKey(e.cuisine_main.toLowerCase()))
				catergorizedHashmap.put(e.cuisine_main.toLowerCase(), new Bucket());
			if(e.cuisine_main != null)
				catergorizedHashmap.get(e.cuisine_main.toLowerCase()).list.add(e);
			
			//Add Secondary Cuisine tag
			if(e.cuisine_secondary != null && !catergorizedHashmap.containsKey(e.cuisine_secondary.toLowerCase()))
				catergorizedHashmap.put(e.cuisine_secondary.toLowerCase(), new Bucket());
			if(e.cuisine_secondary != null)
				catergorizedHashmap.get(e.cuisine_secondary.toLowerCase()).list.add(e);
		}
	}
	
	/**
	 * Standard way
	 * @param keywords keywords to search for
	 * @return The  JSON Array of Restaurants that contain the keywords
	 */
	public String SearchStandard(String[] keywords)
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

	
	/**
	 * Search with spellcheck.
	 * @param keywords keywords to search for
	 * @return The  JSON Array of Restaurants that contain the keywords
	 */
	public String SearchSpellCheck(String[] keywords)
	{

			HashMap<Integer, Restaurant> results = new HashMap<Integer, Restaurant>();
			System.out.println("CMAP has " + catergorizedHashmap.size() + " entries ");
			long StartTime = System.currentTimeMillis();
			for(String e : keywords)
			{
				for(String key : catergorizedHashmap.keySet())
				{
					int editDistance = Levenshtein_Algorithm_Improved_Opitmal(e,key,EDIT_DISTANCE);
					//System.out.println("Edit Distance between " + key + " and " + e +" is " + editDistance);
					if(editDistance <= EDIT_DISTANCE)
					{
						String alertedWord = key;
						if(editDistance == 0)
							System.out.println("CMAP contains " + alertedWord +" with a size " + catergorizedHashmap.get(alertedWord.toLowerCase()).list.size() );
						else
						{
							System.out.println("CMAP contains " + alertedWord +" which has an edit distance of " + editDistance + " with input " + e + "  with a size " + catergorizedHashmap.get(alertedWord.toLowerCase()).list.size() );
							//System.out.println("DID YOU MEAN " + key + "? You typed " + e);
						}
						
						for(Restaurant r : catergorizedHashmap.get(alertedWord.toLowerCase()).list)
						{
							System.out.println("Restaurant " + r.name + " added! ");
							if(!results.containsKey(r.ID))
								results.put(r.ID, r);
							
							
							//Results that are correctly spelled get more of a boost 
							if(editDistance == 0)
								results.get(r.ID).rank+=3; 
							else
								results.get(r.ID).rank+=2; 
						}
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
			//System.out.println("Num of Results " + relevantRestaurants.length);
			Arrays.sort(relevantRestaurants);
			String JSONarray = arrayTOJSON(relevantRestaurants);
			
			//System.out.println("Search Results");
			//System.out.println(JSONarray);
			
			long EndStart = System.currentTimeMillis();
			System.out.println("Total Time searching " + (EndStart - StartTime) + " ms !!!");
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
				results += " \"Rest_Address\" : \"" + e.Rest_Address + "\",";
				results += " \"Rest_Coordinate_Lat\" : \"" + e.Rest_Coordinate_Lat + "\",";
				results += " \"Rest_Coordinate_Long\" : \"" + e.Rest_Coordinate_Long + "\",";
				results += " \"Rest_Keywords\" : \"" + e.keywords + "\"} ,";
				
			}
			results = results.substring(0, results.length()-1); //Erase that last comma
			//End JSON array
			results += "] }";
			
			return results;
		}
	
	
		//Dynamic Programming
		//Damerau–Levenshtein distance
	    //Allow for exiting early based on detection 
	    private static int Levenshtein_Algorithm_Improved_Opitmal(String word1, String word2, int max)
	    {
	    		int[][] matrix = new int[word1.length()][word2.length()];
	    		
	    		//Initial Point
	    		matrix[0][0] = 0;
	    		//Set up inital values
	    		for(int i=0; i<matrix.length;i++)
	    			matrix[i][0] = i;
	    		for(int j=0; j<matrix[0].length;j++)
	    			matrix[0][j] = j;
	    		
	    		for(int i=1; i<matrix.length; i++)
	    		{
	    			int min = matrix[i][0];
	    			for(int j=1; j<matrix[0].length;j++)
	    			{

	    				int top = matrix[i-1][j]+1; // deletion
	    				int left = matrix[i][j-1]+1; // insertion
	    				
	    				//Adds penalty if the chars dont match. 
	    				int match = 0;
	    				if(word1.toLowerCase().charAt(i-1) != word2.toLowerCase().charAt(j-1))
	    					match++; 
	    				
	    				int diagonal = matrix[i-1][j-1] + match; //Represents either no penalty for matching chars, or one for the change of one. 
	    			
	    				matrix[i][j] = Math.min(top, Math.min(left, diagonal));
	    				//End here for the Levenshtein
	    				//New Code is for Damerau-Levenshtein Algorithm. Adds switching of characters. 
	    				//System.out.println("i = " + i +  " j = " + j);
	    				if(i>1 && j>1 && word1.toLowerCase().charAt(i) == (word2.toLowerCase().charAt(j-1)) && word1.toLowerCase().charAt(i-1) == (word2.toLowerCase().charAt(j)))
	    				{
	    					matrix[i][j] = Math.min(matrix[i][j], matrix[i-2][j-2]+match);
	    				}
	    				
	    				if(matrix[i][j] < min)
	    					min = matrix[i][j];
	    			}
	    			if(i < word1.length()-1)
	    				min = Math.min(min, matrix[i+1][0]);
	    			//Break out early if there is no way to achieve the goal.
	    			if(min > max)
						return max+1;
	    		}
	    		
//	    		for(int i=0; i<matrix.length+1;i++)
//	    		{
//	    			for(int j=0; j<matrix[0].length+1;j++)
//	    			{
//	    				if(i==0 && j >0)
//	    					System.out.print(word2.charAt(j-1) + " ");
//	    				else if(j==0 && i > 0)
//	    					System.out.print(word1.charAt(i-1));
//	    				else if(i!=0 && j!=0)
//	    					System.out.print(matrix[i-1][j-1] + " ");
//	    				else 
//	    					System.out.print(" ");
//	    			}
//	    			System.out.println();
//	    		}
//	    		System.out.println();
//	    		System.out.println();
	    		return matrix[word1.length()-1][word2.length()-1];
	    }
	
}
