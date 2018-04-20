package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Peter on 4/20/18.
 */

public class pending_rest_adapter extends BaseAdapter {
    private ArrayList<Restaurant> rests;
    private Context context;

    public pending_rest_adapter(ArrayList<Restaurant> rests, Context c){
        this.rests = rests;
        this.context = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Restaurant rest = rests.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.)
        return null;
    }
}
