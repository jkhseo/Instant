package food.instant.instant;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by mpauk on 2/24/2018.
 */

public class user_OrderAdapter extends BaseExpandableListAdapter {
    private String[] categories;
    private ArrayList<ArrayList<Order>> orders;
    private Context context;
    public user_OrderAdapter(Context context, ArrayList<ArrayList<Order>> orders){
        this.orders = orders;
        this.context = context;
    }
    @Override
    public int getGroupCount() {
        return orders.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return orders.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return orders.get(i);
    }

    @Override
    public Object getChild(int g, int c) {
        return orders.get(g).get(c);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int g, int c) {
        return c;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String Rest_Name = orders.get(i).get(0).getRestaurant_Name();
        view = LayoutInflater.from(context).inflate(R.layout.order_group,null);
        TextView orderName = view.findViewById(R.id.order_name);
        orderName.setText(Rest_Name+" Order");
        Button orderStatus = view.findViewById(R.id.order_status);
        return view;
    }

    @Override
    public View getChildView(int g, int c, boolean b, View view, ViewGroup viewGroup) {
        Food food = orders.get(g).get(c).getFood();
        view = LayoutInflater.from(context).inflate(R.layout.menu_child,null);
        TextView foodName = view.findViewById(R.id.menu_child_food);
        foodName.setText(food.getFood_Name());
        TextView foodPrice = view.findViewById(R.id.menu_child_price);
        foodPrice.setText(""+food.getFood_Price());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
