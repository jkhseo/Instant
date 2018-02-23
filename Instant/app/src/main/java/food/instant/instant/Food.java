package food.instant.instant;

import java.util.Arrays;

/**
 * Created by mpauk on 2/12/2018.
 */

public class Food {
    private int Rest_ID;
    private String Food_Name;
    private double Food_Price;
    private String Food_Desc;
    private int Menu_Id;
    private String Food_Tags_Main;
    private String Food_Tags_Secondary;

    public Food(int Rest_ID, String Food_Name, double Food_Price, String Food_Desc, int Menu_ID, String Food_Tags_Main, String Food_Tags_Secondary){
        this.Rest_ID = Rest_ID;
        this.Food_Name = Food_Name;
        this.Food_Price = Food_Price;
        this.Food_Desc = Food_Desc;
        this.Menu_Id = Menu_ID;
        this.Food_Tags_Main = Food_Tags_Main;
        this.Food_Tags_Secondary = Food_Tags_Secondary;

    }
    public Food(Food oldFood){
        this.Rest_ID = oldFood.Rest_ID;
        this.Food_Name = oldFood.Food_Name;
        this.Food_Price = oldFood.Food_Price;
        this.Food_Desc = oldFood.Food_Desc;
        this.Menu_Id = oldFood.Menu_Id;
        this.Food_Tags_Main = oldFood.Food_Tags_Main;
        this.Food_Tags_Secondary = oldFood.Food_Tags_Secondary;
    }
    public int getRest_ID() {
        return Rest_ID;
    }

    public void setRest_ID(int rest_ID) {
        Rest_ID = rest_ID;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String food_Name) {
        Food_Name = food_Name;
    }

    public double getFood_Price() {
        return Food_Price;
    }

    public void setFood_Price(double food_Price) {
        Food_Price = food_Price;
    }

    public String getFood_Desc() {
        return Food_Desc;
    }

    public void setFood_Desc(String food_Desc) {
        Food_Desc = food_Desc;
    }

    public int getMenu_Id() {
        return Menu_Id;
    }

    public void setMenu_Id(int menu_Id) {
        Menu_Id = menu_Id;
    }

    public String getFood_Tags_Main() {
        return Food_Tags_Main;
    }

    public void setFood_Tags_Main(String food_Tags_Main) {
        Food_Tags_Main = food_Tags_Main;
    }

    public String getFood_Tags_Secondary() {
        return Food_Tags_Secondary;
    }

    public void setFood_Tags_Secondary(String food_Tags_Secondary) {
        Food_Tags_Secondary = food_Tags_Secondary;
    }
}
