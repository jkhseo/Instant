package food.instant.instant;

import android.content.Context;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static food.instant.instant.HttpRequests.HttpPost;

/**
 * Created by mpauk on 2/24/2018.
 */

public class user_OrderAdapter extends BaseExpandableListAdapter {
    private ArrayList<ArrayList<Order>> orders;
    private Context context;
    private Handler handler;
    public user_OrderAdapter(Context context, ArrayList<ArrayList<Order>> orders,Handler handler){
        this.orders = orders;
        this.context = context;
        this.handler = handler;
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
        if(orders.get(i).size()!=0) {
            Order order = orders.get(i).get(0);
            String Rest_Name = orders.get(i).get(0).getRestaurant_Name();
            view = LayoutInflater.from(context).inflate(R.layout.order_group, null);
            TextView orderName = view.findViewById(R.id.order_name);
            orderName.setText(Rest_Name + " Order");
            Button orderStatus = view.findViewById(R.id.order_status);

            if(order.getStatus()=='L') {

                orderStatus.setText("Submit Order");
                orderStatus.setTag(orders.get(i));
                orderStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String rowids="";
                        ArrayList<Order> orderGroup = (ArrayList<Order>) view.getTag();
                        String[] orderInfo = {"","","","",""};
                        for(int j=0;j<orderGroup.size();j++){
                            rowids+= orderGroup.get(j).getOrder_ID();
                            if(j!=orderGroup.size()-1)
                                rowids+=", ";
                            orderInfo[0]= orderInfo[0]+orderGroup.get(j).getFood().getRest_ID()+",";
                            orderInfo[1]= orderInfo[1]+orderGroup.get(j).getUser_ID()+",";
                            orderInfo[2] = orderInfo[2]+orderGroup.get(j).getFood().getFood_ID()+",";
                            orderInfo[3] = orderInfo[3]+orderGroup.get(j).getComments()+",";
                            orderInfo[4] = orderInfo[4]+orderGroup.get(j).getFood_Quantity()+",";
                            orderGroup.get(j).setStatus('P');
                        }
                        String url = "addOrder?Rest_ID="+orderInfo[0].substring(0,orderInfo[0].length()-1)+
                                "&User_ID="+orderInfo[1].substring(0,orderInfo[1].length()-1)+
                                "&Food="+ orderInfo[2].substring(0,orderInfo[2].length()-1)+
                                "&Comments="+orderInfo[3].substring(0,orderInfo[3].length()-1)+
                                "&Quantity="+orderInfo[4].substring(0,orderInfo[4].length()-1);
                        OrderDbHelper dbHelper = new OrderDbHelper(context);
                        SQLiteDatabase database = dbHelper.getWritableDatabase();
                        dbHelper.updatePendingOrders(rowids,database);
                        dbHelper.close();
                        HttpPost(url,handler);
                    }
                });
            }
            else if(order.getStatus()=='P'){
                orderStatus.setText("Cancel Order");
            }
            else if(order.getStatus()=='X'){
                orderStatus.setText("Order Cancelled");
                orderStatus.setClickable(false);
                orderStatus.setEnabled(false);
            }
            else if(order.getStatus()=='C'){
                orderStatus.setText("Order Confirmed");
                orderStatus.setClickable(false);
                orderStatus.setEnabled(false);
            }
        }
        return view;
    }

    @Override
    public View getChildView(final int g, final int c, boolean b, View view, ViewGroup viewGroup) {
        Order order = orders.get(g).get(c);
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
                    dbHelper.removeOrder(orders.get(g).get(c).getOrder_ID(), database);
                    dbHelper.close();
                    if(c==0) {
                        orders.get(g).remove(c);
                        orders.remove(g);
                    }
                    else{
                        orders.get(g).remove(c);
                    }
                    ((user_OrderAdapter) view.getTag()).notifyDataSetChanged();
                }
            });
        }
        else {
            delete.setVisibility(View.GONE);
        }
        foodName.setText(food.getFood_Name());
        TextView foodPrice = view.findViewById(R.id.price);
        TextView foodQuantity = view.findViewById(R.id.quantity);
        foodQuantity.setText("" + orders.get(g).get(c).getFood_Quantity());
        foodPrice.setText("" + food.getFood_Price());


        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
