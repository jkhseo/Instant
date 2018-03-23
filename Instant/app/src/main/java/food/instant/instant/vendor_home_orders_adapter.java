package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mpauk on 3/17/2018.
 */

public class vendor_home_orders_adapter extends BaseAdapter {
    private ArrayList<ArrayList<Order>> orders;
    private Context context;
    public vendor_home_orders_adapter(Context context,ArrayList<ArrayList<Order>> orders){
        this.context = context;
        this.orders = orders;
    }
    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Order order = orders.get(i).get(0);
        String Rest_Name = orders.get(i).get(0).getRestaurant_Name();
        view = LayoutInflater.from(context).inflate(R.layout.order_group, null);
        TextView orderName = view.findViewById(R.id.order_name);
        orderName.setText(Rest_Name + " Order");
        Button orderStatus = view.findViewById(R.id.order_status);
        if(order.getStatus()=='L'|| order.getStatus()=='P'){
            orderStatus.setText("Edit Order");
        }
        else{
            orderStatus.setText("View Order");
        }
        orderStatus.setTag(orders.get(i));
        orderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).swapFragments(new vendor_home_order((ArrayList<Order>) view.getTag()));
            }
        });
        return view;
    }
}
