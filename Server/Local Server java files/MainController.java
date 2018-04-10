package hello;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {

	RSA_Encyption RSA = null;
	public static final int QRCODE_SIZE = 10000;
	
	@GetMapping(path="/getRestaurants")
	public @ResponseBody String getAllRestaurants() 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getAllRestaurant();
	}
	
	@GetMapping(path="/getPassword")
	public @ResponseBody String getPassword(@RequestParam String User_Email) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getPassword(User_Email);
	}
	@GetMapping(path="/getAllUserInfo")
	public @ResponseBody String getAllUserInfo(@RequestParam String User_Email) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getAllUserInfo(User_Email);
	}
	
	@GetMapping(path="/getMenu")
	public @ResponseBody String getMenu(@RequestParam String Restaurant_ID) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getMenu(Restaurant_ID);
	}
	@GetMapping(path="/getRestaurantFromOwnerUserID")
	public @ResponseBody String getRestaurantFromOwnerUserID(@RequestParam String User_ID) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getRestaurantFromOwnerUserID(User_ID);
	}
	
	@GetMapping(path="/getOrderStatus")
	public @ResponseBody String getOrderStatus(@RequestParam String Rest_ID,@RequestParam String Order_ID ) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getOrderStatus(Order_ID, Rest_ID);
	}
	
	@GetMapping(path="/getPendingOrderForRestaurant")
	public @ResponseBody String getPendingOrderForRestaurant(@RequestParam String Restaurant_ID) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getPendingOrderForRestaurant(Restaurant_ID);
	}
	
	@GetMapping(path="/getCompletedOrderForRestaurant")
	public @ResponseBody String getCompletedOrderForRestaurant(@RequestParam String Restaurant_ID) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getCompletedOrderForRestaurant(Restaurant_ID);
	}
	@GetMapping(path="/getConfirmedOrderForRestaurant")
	public @ResponseBody String getConfirmedOrderForRestaurant(@RequestParam String Restaurant_ID) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getConfirmedOrderForRestaurant(Restaurant_ID);
	}
	
	@GetMapping(path="/getCancelledOrderForRestaurant")
	public @ResponseBody String getCancelledOrderForRestaurant(@RequestParam String Restaurant_ID) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getCancelledOrderForRestaurant(Restaurant_ID);
	}
	
	
	@GetMapping(path="/getNearestRestaurants")
	public @ResponseBody String getNearestRestaurants(@RequestParam float latitude, @RequestParam float longitude, @RequestParam 
			String numOfRestaurants, @RequestParam String rangeInKm) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getNearestRestaurants(latitude, longitude, numOfRestaurants, rangeInKm);
	}
	@GetMapping(path="/getRestaurantsInView")
	public @ResponseBody String getRestaurantsInView(@RequestParam String max_Lat, @RequestParam String min_Lat, @RequestParam String max_Long, @RequestParam String min_Long) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.getRestaurantsInView(max_Lat, max_Long, min_Lat, min_Long);
	}
	
	@GetMapping(path="/getAllOrdersForUser")		
	public @ResponseBody String getAllOrdersForUser(@RequestParam String User_ID) 		
	{		
		// This returns a JSON or XML with the users		
		return DATABASE_GET.getAllOrdersForUser(User_ID);		
	}
	
	@GetMapping(path="/getConfirmationCode")		
	public @ResponseBody String getConfirmationCode(@RequestParam String Order_ID)		
	{		
		// This returns a JSON or XML with the users		
		return DATABASE_GET.getConfirmationCode(Order_ID);		
	}		
					
	@GetMapping(path="/getRestaurantFromID")		
	public @ResponseBody String getRestaurantFromID(@RequestParam String Rest_ID)		
	{		
		// This returns a JSON or XML with the users		
		return DATABASE_GET.getRestaurantFromID(Rest_ID);		
	}
	
	@GetMapping(path="/getRSAPublicKey") // Map ONLY GET Requests
	public @ResponseBody String getRSAPublicKey()
	{ 
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
	
		if(RSA == null)
			RSA = new RSA_Encyption();
		
	    return DATABASE_GET.getRSAKEY();
	}
	

		


	//USING RSA ENCRYPTION, POST A NEW AES KEY
	@GetMapping(path="/postAESKEY") // Map ONLY GET Requests
	public @ResponseBody String postAESKey(@RequestParam String VersionNumber, @RequestParam String EncryptedCode, @RequestParam String User_ID)
	{ 
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		String JSONreturned = DATABASE_GET.getRSAKEY();
		int Version = Integer.parseInt(JSONreturned.substring(JSONreturned.indexOf("Version") + 12, JSONreturned.indexOf("Encyption_Exponet")-4));
		
		if(Version != Integer.parseInt(VersionNumber))
			return "{ \"Success\" : \"False\"}";
		
		BigInteger deycrptedCode = RSA.DecryptMessage(Integer.parseInt(EncryptedCode));
		boolean added = DATABASE_POST.Add_New_AES_Key(User_ID, deycrptedCode.toString());
		
	    if(added)
    			return "{ \"Success\" : \"True\"}";
	    return "{ \"Success\" : \"False\"}";
	}
		

		
		@GetMapping(path="/updateOrderStatus") // Map ONLY GET Requests
		public @ResponseBody String updateOrderStatus(@RequestParam String Order_ID, @RequestParam String Rest_ID,  @RequestParam String Order_Status)
		{ 
			// @ResponseBody means the returned String is the response, not a view name
			// @RequestParam means it is a parameter from the GET or POST request
			
		    boolean added = DATABASE_POST.Update_Order_Status(Order_ID,Rest_ID, Order_Status);
		    
		    if(added)

		    		return "{ \"Update_Order_Status_Success\" : \"True\"}";
		    return "{ \"Update_Order_Status_Success\" : \"False\"}";

		}

	
	@GetMapping(path="/addOrder") // Map ONLY Post Requests
	public @ResponseBody String addNewOrder(@RequestParam String Rest_ID, @RequestParam String User_ID, @RequestParam String Food, @RequestParam String Comments,  @RequestParam String Quantity, @RequestParam String Order_Date_Pick_Up)
	{ 
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
	
		
		
		String[] FoodItems = Food.split(",+\\s*");
		String[] QuanityItems = Quantity.split(",+\\s*");
		String[] CommentsItems = Comments.split("NEWCOMMENTBLOCK"); //Imperfect Solution for an imperfect world. 
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		if(FoodItems.length != QuanityItems.length && FoodItems.length != CommentsItems.length)
			 return "{ \"Success\" : \"False\"}";
		else
		{
			int orderID = DATABASE_GET.getNextOrderID(Rest_ID);	
			String QR_CODE = "" +  ((int) (Math.random() * QRCODE_SIZE));
			System.out.println(Comments);
			System.out.println(FoodItems.length + " " + QuanityItems.length + " " + CommentsItems.length);
			
			
			boolean added = true;
			for(int i=0; i<FoodItems.length; i++)
			{
				if(!DATABASE_POST.Add_Order(orderID, Rest_ID,   User_ID,  FoodItems[i], dtf.format(now), Order_Date_Pick_Up ,null, CommentsItems[i], QuanityItems[i],QR_CODE))
				   added = false;
			}
		
		    		return "{ \"Add_New_Order_Success\" : \"True\"}";
		    return "{ \"Add_New_Order_Success\" : \"False\"}";
		}
	}
	@GetMapping(path="/addFood") // Map ONLY GET Requests
	public @ResponseBody String addNewFood(@RequestParam String Rest_ID, @RequestParam String Food_Name, @RequestParam String Food_Price,  @RequestParam String Food_Desc, @RequestParam String Menu_ID,@RequestParam String Food_Tags_Main, @RequestParam String Food_Tags_Secondary)
	{ 
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		String newFoodID = "" + DATABASE_GET.getNextFoodID(Rest_ID);
		
	    boolean added = DATABASE_POST.Add_Food(  Rest_ID,   Food_Name,   Food_Price,    Food_Desc,   Menu_ID,  Food_Tags_Main,   Food_Tags_Secondary, newFoodID);
	    
	    if(added)
	    		return "{ \"Add_Food_Item_Success\" : \"True\"}";
	    return "{ \"Add_Food_Item_Success\" : \"False\"}";
	}
	
	@GetMapping(path="/updateFood") // Map ONLY GET Requests
	public @ResponseBody String updateFood(@RequestParam String Rest_ID, @RequestParam String Food_Name, @RequestParam String Food_Price,  @RequestParam String Food_Desc, @RequestParam String Menu_ID,@RequestParam String Food_Tags_Main, @RequestParam String Food_Tags_Secondary, @RequestParam String Food_ID)
	{ 
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		

	    boolean added = DATABASE_POST.Update_Food(  Rest_ID,   Food_Name,   Food_Price,    Food_Desc,   Menu_ID,  Food_Tags_Main,   Food_Tags_Secondary, Food_ID);
	    
	    if(added)
	    		return "{ \"Add_Food_Item_Success\" : \"True\"}";
	    return "{ \"Add_Food_Item_Success\" : \"False\"}";
	}
	
	@GetMapping(path="/deleteFood") // Map ONLY GET Requests
	public @ResponseBody String deleteFood( @RequestParam String Rest_ID, @RequestParam String Food_ID)
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		boolean added = DATABASE_POST.Delete_Food(Rest_ID, Food_ID);
	    if(added)
    			return "{ \"Delete_Food\" : \"True\"}";
	    return "{ \"Delete_Food\" : \"False\"}";
	}
	
	@GetMapping(path="/addUser") // Map ONLY Post Requests
	public @ResponseBody String addNewUser( @RequestParam String  User_Type, @RequestParam String  First_Name,  @RequestParam String  Last_Name, @RequestParam String  User_Address,@RequestParam String  User_Birthday, @RequestParam String  User_Email, @RequestParam String  User_Password)
	{ 
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
	    boolean added = DATABASE_POST.Add_User( User_Type,   First_Name,    Last_Name,   User_Address,  User_Birthday,   User_Email,   User_Password);
		
	    if(added)
	    		return "{ \"Add_New_User_Success\" : \"True\"}";
	    return "{ \"Add_New_User_Success\" : \"False\"}";
	}
	
	
	@GetMapping(path="/addRestaurant") // Map ONLY GET Requests
	public @ResponseBody String addNewRestaurants ( @RequestParam String Rest_Name,
			@RequestParam String Rest_Coordinate_Lat, @RequestParam String Rest_Coordinate_Long, @RequestParam String Rest_Address, @RequestParam String Rest_Rating,  @RequestParam String Rest_Type_Cuisine_Main,  @RequestParam String Rest_Type_Cuisine_Secondary ,  @RequestParam String Rest_Keywords,  @RequestParam String User_ID) 
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
			
		
		boolean added = DATABASE_POST.Add_Restaurant( Rest_Name,   Rest_Coordinate_Lat,  Rest_Coordinate_Long,  Rest_Address,  Rest_Rating,  Rest_Type_Cuisine_Main,  Rest_Type_Cuisine_Secondary,  Rest_Keywords,  User_ID);
				
	    if(added)
    			return "{ \"Add_New_Restaurant\" : \"True\"}";
	    return "{ \"Add_New_Restaurant\" : \"False\"}";
	}
	
	@GetMapping(path="/updateRestaurant") // Map ONLY GET Requests
	public @ResponseBody String updateRestaurant (  @RequestParam String Rest_ID, @RequestParam String Rest_Name,
			@RequestParam String Rest_Coordinate_Lat, @RequestParam String Rest_Coordinate_Long, @RequestParam String Rest_Address, @RequestParam String Rest_Rating,  @RequestParam String Rest_Type_Cuisine_Main,  @RequestParam String Rest_Type_Cuisine_Secondary ,  @RequestParam String Rest_Keywords,  @RequestParam String User_ID) 
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
			
		
		boolean added = DATABASE_POST.Update_Restaurant(Rest_ID,  Rest_Name,   Rest_Coordinate_Lat,  Rest_Coordinate_Long,  Rest_Address,  Rest_Rating,  Rest_Type_Cuisine_Main,  Rest_Type_Cuisine_Secondary,  Rest_Keywords,  User_ID);
				
	    if(added)
    			return "{ \"Update_Restaurant\" : \"True\"}";
	    return "{ \"Update_Restaurant\" : \"False\"}";
	}
	
	
	
	@GetMapping(path="/deleteRestaurant") // Map ONLY GET Requests
	public @ResponseBody String deleteRestaurant ( @RequestParam String Rest_ID)
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		boolean added = DATABASE_POST.Delete_Restaurant(Rest_ID);
	    if(added)
    			return "{ \"Delete_Restaurant\" : \"True\"}";
	    return "{ \"Delete_Restaurant\" : \"False\"}";
	}
	
	
	@GetMapping(path="/getFuzzySearchRestaurants")
	public @ResponseBody String getFuzzySearch(@RequestParam String restaurantName) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.fuzzySearchRestaurant(restaurantName);
	}
	
	@GetMapping(path="/getSearchRestaurants")
	public @ResponseBody String getSearch(@RequestParam String Keywords) 
	{
		// This returns a JSON or XML with the users
		return DATABASE_GET.searchRestaurant_KeyWords(Keywords);
	}
	

}

