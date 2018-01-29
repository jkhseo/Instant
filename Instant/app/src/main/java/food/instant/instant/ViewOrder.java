package food.instant.instant;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewOrder extends AppCompatActivity {

    OrderDataBase helper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper = new OrderDataBase(this);



        //grabs list from database
        ArrayList<Order> list = helper.getOrderData();


        //instantiate custom adapter
        OrderFormatter adapter = new OrderFormatter(list, this);

        //handle listview and assign adapter
        ListView lView = (ListView)findViewById(R.id.list);
        lView.setAdapter(adapter);

    }

}
