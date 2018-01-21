package food.instant.instant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

public class user_home_orders extends user_template {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup navDrawerView = findViewById(R.id.drawer_layout);
        getLayoutInflater().inflate(R.layout.activity_user_home_orders,navDrawerView,true);
    }
}
