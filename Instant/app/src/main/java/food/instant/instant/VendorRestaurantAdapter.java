package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Peter on 4/3/18.
 */

public class VendorRestaurantAdapter extends ArrayAdapter<Restaurant> {
    private Context context;
    public VendorRestaurantAdapter(Context context, Restaurant[] restaurants) {
        super(context, 0, restaurants);
        this.context=context;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent){
        Restaurant restaurant = getItem(position);
        view = LayoutInflater.from(context).inflate(R.layout.restaurant_item,parent,false);
        TextView name = view.findViewById(R.id.name);
        name.setText(restaurant.getName());

        return view;
    }
}
