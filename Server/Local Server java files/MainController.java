package hello;

import org.hibernate.boot.model.relational.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {

	
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
	

}
