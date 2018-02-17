package food.instant.instant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by mpauk on 2/11/2018.
 */

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private Context context;
    public RestaurantAdapter(Context context, Restaurant[] restaurants) {
        super(context, 0, restaurants);
        this.context=context;
    }
    @Override
    public View getView(int position, View view,ViewGroup parent){
        Restaurant restaurant = getItem(position);
        view = LayoutInflater.from(context).inflate(R.layout.search_list_item,parent,false);
        TextView name = view.findViewById(R.id.name);
        TextView distance = view.findViewById(R.id.distance);
        name.setText(restaurant.getName());
        if(restaurant.getDistance()<0.1&&restaurant.getDistance()>0){
            distance.setText(""+((int)(restaurant.getDistance()*5280))+" ft");
        }
        else if(restaurant.getDistance() !=-1) {
            double temp = Math.round(restaurant.getDistance()*10)/10.0;
            distance.setText("" + temp + " mi");
        }

        return view;
    }
}
