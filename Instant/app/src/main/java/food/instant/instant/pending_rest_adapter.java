package food.instant.instant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Peter on 4/20/18.
 */

public class pending_rest_adapter extends ArrayAdapter<Restaurant> {
    private Context context;
    public pending_rest_adapter(@NonNull Context context, Restaurant[] restaurants) {
        super(context, 0, restaurants);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        final Restaurant restaurant = getItem(position);
        view = LayoutInflater.from(context).inflate(R.layout.pending_rest_child,parent,false);
        TextView name = view.findViewById(R.id.tv_pend_rest_name);
        name.setText(restaurant.getName());
        return view;
    }
}
