package hello;

import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		//data adding (String name, int ID, String cuisine_main, String cuisine_secondary, String[] keywords) 
//		String[] keywords_a = {"Chain", "Always Fresh Never Frozen"};
//		Restaurant r1 = new Restaurant("Wendy's", 1 , "American" , "Fast Food", keywords_a);
//		restaurants.add(r1);
//		
//		String[] keywords_b = {"Chain", "Chain", "Have it your way"};
//		Restaurant r2 = new Restaurant("Burger King", 2 , "American" , "Fast Food", keywords_b);
//		restaurants.add(r2);
//		
//		String[] keywords_c = {"Student Dining", "Never know what you will find"};
//		Restaurant r3 = new Restaurant("UDCC", 3 , "Mixed" , "Buffet", keywords_c );
//		restaurants.add(r3);
//		
//		
		String[] keywords = {"Amerkan"};
		System.out.println(DATABASE_GET.searchRestaurant_KeyWords("Amerkan"));
		
	}

}
