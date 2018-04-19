package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Peter on 4/9/18.
 */

public class food_adapter extends BaseAdapter{
    private ArrayList<Food> foods;
    private vendor_menu_details parent_fragment;
    private Context context;
    private vendor_menu_details.vendorMenuDetailsHandler handler;
    public food_adapter(Context context, ArrayList<Food> foods, vendor_menu_details parent, vendor_menu_details.vendorMenuDetailsHandler handler){
        this.foods = foods;
        this.context = context;
        this.parent_fragment = parent;
        this.handler = handler;
    }

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int i) {
        return foods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Food food = foods.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.food_child, null);
        final TextView foodName = view.findViewById(R.id.tv_food_name);
        final TextView foodDesc = view.findViewById(R.id.tv_food_desc);
        final TextView foodTags = view.findViewById(R.id.tv_tags_main);
        final TextView foodPrice = view.findViewById(R.id.tv_food_price);
        final TextView foodTagsSec = view.findViewById(R.id.tv_tags_sec);
        final EditText updateDesc = view.findViewById(R.id.et_update_desc);
        final EditText updatePrice = view.findViewById(R.id.et_update_price);
        final EditText updateMainTags = view.findViewById(R.id.et_update_maintags);
        final EditText updateSecTags = view.findViewById(R.id.et_update_sectags);
        final TextView tag1 = view.findViewById(R.id.tv_updatetag1);
        final TextView tag2 = view.findViewById(R.id.tv_updatetag2);
        final TextView tag3 = view.findViewById(R.id.tv_updatetag3);
        final TextView tag4 = view.findViewById(R.id.tv_updatetag4);

        final Button delete = view.findViewById(R.id.bn_delete);
        final Button edit = view.findViewById(R.id.bn_edit_food);
        final Button submit = view.findViewById(R.id.bn_update_food);
        final Button cancel = view.findViewById(R.id.bn_cancel_add_rest);
        foodName.setText(food.getFood_Name());
        foodDesc.setText("Description: \n" + food.getFood_Desc());
        foodTags.setText("Main Tags:\n" + food.getFood_Tags_Main());
        foodPrice.setText("Price: " + food.getFood_Price());
        foodTagsSec.setText("Secondary Tags:\n" + food.getFood_Tags_Secondary());

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parent_fragment.delete_food(handler, food);
            }
        });

        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                edit.setVisibility(View.GONE);
                foodDesc.setVisibility(View.GONE);
                foodTags.setVisibility(View.GONE);
                foodPrice.setVisibility(View.GONE);
                foodTagsSec.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);

                cancel.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                updateDesc.setText(food.getFood_Desc());
                updateMainTags.setText(food.getFood_Tags_Main());
                updatePrice.setText("" + food.getFood_Price());
                updateSecTags.setText(food.getFood_Tags_Secondary());
                tag1.setVisibility(View.VISIBLE);
                tag2.setVisibility(View.VISIBLE);
                tag3.setVisibility(View.VISIBLE);
                tag4.setVisibility(View.VISIBLE);
                updateDesc.setVisibility(View.VISIBLE);
                updatePrice.setVisibility(View.VISIBLE);
                updateMainTags.setVisibility(View.VISIBLE);
                updateSecTags.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cancel.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                tag1.setVisibility(View.GONE);
                tag2.setVisibility(View.GONE);
                tag3.setVisibility(View.GONE);
                tag4.setVisibility(View.GONE);
                updateDesc.setVisibility(View.GONE);
                updatePrice.setVisibility(View.GONE);
                updateMainTags.setVisibility(View.GONE);
                updateSecTags.setVisibility(View.GONE);

                edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                foodDesc.setVisibility(View.VISIBLE);
                foodTags.setVisibility(View.VISIBLE);
                foodPrice.setVisibility(View.VISIBLE);
                foodTagsSec.setVisibility(View.VISIBLE);
            }
        });

        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = foodName.getText().toString();
                String price = updatePrice.getText().toString();
                String desc = updateDesc.getText().toString();
                String mainTags = updateMainTags.getText().toString();
                String secTags = updateSecTags.getText().toString();
                Double dPrice;

                if(name.length() == 0 || price.length() == 0 || desc.length() == 0 || mainTags.length() == 0){
                    Toast.makeText(context, "Please fill in all required fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                dPrice = Double.parseDouble(price);
                InputMethodManager mgr = (InputMethodManager) parent_fragment.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(updateMainTags.getWindowToken(), 0);
                Food to_add = new Food(food.getRest_ID(), name, dPrice, desc, food.getMenu_Id(), mainTags, secTags, food.getFood_ID());
                parent_fragment.add_food(handler, to_add);
            }
        });


        return view;
    }
}
