package food.instant.instant;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CustomerActivity extends user_template {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup navDrawerView = findViewById(R.id.drawer_layout);
        getLayoutInflater().inflate(R.layout.activity_customer,navDrawerView,true);
        Button searchButton = findViewById(R.id.searchButton);
        Button mapButton = findViewById(R.id.mapButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this,user_home_search.class);
                startActivity(intent);
            }
        });
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerActivity.this,user_home_search.class);
                startActivity(intent);
            }
        });

    }

}
