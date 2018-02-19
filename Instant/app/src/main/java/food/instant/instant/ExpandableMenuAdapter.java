package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mpauk on 2/17/2018.
 */

public class ExpandableMenuAdapter extends BaseExpandableListAdapter {
    private String[] categories;
    private ArrayList<ArrayList<Food>> foodByCategory;
    private Context context;
    public ExpandableMenuAdapter(Context context,ArrayList<ArrayList<Food>> foodByCategory, String[] categories){
        this.foodByCategory = foodByCategory;
        this.categories = categories;
        this.context = context;

    }
    @Override
    public int getGroupCount() {
        return categories.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return foodByCategory.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return categories[i];
    }

    @Override
    public Object getChild(int g, int c) {
        return foodByCategory.get(g).get(c);
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
        String group = categories[i];
        view = LayoutInflater.from(context).inflate(R.layout.menu_group,null);
        TextView name = view.findViewById(R.id.menu_group_text);
        name.setText(group);
        return view;
    }

    @Override
    public View getChildView(int g, int c, boolean b, View view, ViewGroup viewGroup) {
        Food food = foodByCategory.get(g).get(c);
        view = LayoutInflater.from(context).inflate(R.layout.menu_child,null);
        TextView name = view.findViewById(R.id.menu_child_food);
        TextView price = view.findViewById(R.id.menu_child_price);
        name.setText(food.getFood_Name()+"\n"+food.getFood_Desc());
        price.setText(""+food.getFood_Price());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
