package food.instant.instant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AdminActivity extends AppCompatActivity {
    EditText Food, Rest , Quanity;
    OrderDataBase helper;


    /*Opens the View Order activity*/
    public void viewOrder(View view)
    {
        Intent myIntent = new Intent(AdminActivity.this, ViewOrder.class);
        AdminActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Food= (EditText) findViewById(R.id.editFood);
        Rest= (EditText) findViewById(R.id.editRestaurant);
        Quanity= (EditText) findViewById(R.id.editQuanity);


        helper = new OrderDataBase(this);
    }
    public void addOrder(View view)
    {
        String t1 = Food.getText().toString();
        String t2 = Rest.getText().toString();
        String t3 = Quanity.getText().toString();
        if(t1.isEmpty() || t2.isEmpty())
        {
            Message.message(getApplicationContext(),"Enter All Fields");
        }
        else
        {
            long id = helper.insertData(t1,t2, t3);
            if(id<=0)
            {
                Message.message(getApplicationContext(),"Insertion Unsuccessful");
                Food.setText("");
                Rest.setText("");
                Quanity.setText("");
            } else
            {
                Message.message(getApplicationContext(),"Insertion Successful");
                Food.setText("");
                Rest.setText("");
                Quanity.setText("");
            }
        }
    }



}
