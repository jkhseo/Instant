package food.instant.instant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mpauk on 3/14/2018.
 */

public class vendor_home_order_adapter extends BaseAdapter {
    private ArrayList<Order> orders;
    private Context context;
    private vendor_home_order fragment;
    public vendor_home_order_adapter(Context context, ArrayList<Order> orders, vendor_home_order fragment){
        this.context = context;
        this.orders = orders;
        this.fragment = fragment;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final Order order = orders.get(i);
        Food food = order.getFood();
        view = LayoutInflater.from(context).inflate(R.layout.order_child, null);
        TextView foodName = view.findViewById(R.id.name);
        ImageButton delete = view.findViewById(R.id.delete);
        if(order.getStatus()=='L') {
            delete.setTag(this);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OrderDbHelper dbHelper = new OrderDbHelper(context);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    dbHelper.removeOrder(orders.get(i).getOrder_ID(), database);
                    dbHelper.close();
                    if (orders.size() == 1) {
                        orders.remove(orders.get(i));
                        ((MainActivity) context).swapFragments(new vendor_home());
                    } else {
                        orders.remove(orders.get(i));
                    }
                    ((vendor_home_order_adapter) view.getTag()).notifyDataSetChanged();
                    fragment.updateTotalPrice();
                }
            });
        }
        else {
            delete.setVisibility(View.GONE);
        }
        foodName.setText(food.getFood_Name());
        TextView foodPrice = view.findViewById(R.id.price);
        TextView foodQuantity = view.findViewById(R.id.quantity);
        foodQuantity.setText("" + orders.get(i).getFood_Quantity());
        foodPrice.setText("" + food.getFood_Price());
        return view;
    }
}
