package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Peter on 4/9/18.
 */

public class food_adapter extends BaseAdapter{
    private ArrayList<Food> foods;
    private vendor_menu_details parent_fragment;
    private Context context;
    private vendor_menu_details.vendorMenuDetailsHandler handler;
    public food_adapter(Context context, ArrayList<Food> foods, vendor_menu_details parent, vendor_menu_details.vendorMenuDetailsHandler handler){
        this.foods = foods;
        this.context = context;
        this.parent_fragment = parent;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int i) {
        return foods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Food food = foods.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.food_child, null);
        TextView foodName = view.findViewById(R.id.tv_food_name);
        TextView foodDesc = view.findViewById(R.id.tv_food_desc);
        TextView foodTags = view.findViewById(R.id.tv_tags_main);
        TextView foodPrice = view.findViewById(R.id.tv_food_price);
        Button delete = view.findViewById(R.id.bn_delete);
        foodName.setText(food.getFood_Name());
        foodDesc.setText("Description: \n" + food.getFood_Desc());
        foodTags.setText("Main Tags:\n" + food.getFood_Tags_Main());
        foodPrice.setText("Price: " + food.getFood_Price());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
