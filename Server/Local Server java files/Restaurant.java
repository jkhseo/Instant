package hello;

import java.util.ArrayList;

public class Restaurant
{
	
	public String name;
	public int ID;
	public String cuisine_main;
	public String cuisine_secondary;
	public String[] keywords;
	
	
	public Restaurant(String name, int ID, String cuisine_main, String cuisine_secondary, String[] keywords)
	{
		this.name = name;
		this.ID = ID;
		this.cuisine_main = cuisine_main;
		this.cuisine_secondary = cuisine_secondary;
		this.keywords = keywords; //Careful. This is a shallow copy ;)
	}
	
}	
