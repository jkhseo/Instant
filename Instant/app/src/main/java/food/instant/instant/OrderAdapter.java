package food.instant.instant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 2/20/18.
 */

public class OrderAdapter extends ArrayAdapter<Order>{
    private Context context;

    public OrderAdapter(@NonNull Context context, @NonNull List<Order> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Order order = getItem(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
        TextView foodName = convertView.findViewById(R.id.tv_foodName);
        TextView comments = convertView.findViewById(R.id.tv_Comments);
        String foods = "";
        ArrayList<String> foodList = order.getFoods();
        if(foodList.size() != 0) {
            for (int i = 0; i < foodList.size(); i++) {
                if (foods.equals("")) {
                    foods = foodList.get(i);
                }
                else{
                    foods = foods + ", " + foodList.get(i);
                }
            }
        }
        foodName.setText(foods);
        comments.setText(order.getComments());

        return convertView;
    }
}
