package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mpauk on 4/19/2018.
 */

public class vendor_messages_adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> Rest_Names;
    private ArrayList<Integer> Rest_IDs;
    public vendor_messages_adapter(Context c, ArrayList<String> Rest_names,ArrayList<Integer> Rest_ids){
        this.context=c;
        this.Rest_Names = Rest_names;
        this.Rest_IDs= Rest_ids;
    }
    @Override
    public int getCount() {
        return Rest_Names.size();
    }

    @Override
    public Object getItem(int i) {
        return Rest_Names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.vendor_message_element, null);
        TextView textView = view.findViewById(R.id.vendor_names);
        textView.setText(Rest_Names.get(i));
        ImageView imageView = view.findViewById(R.id.vendor_read);
        OrderDbHelper helper = new OrderDbHelper(context);
        if(helper.isAllRead(helper.getReadableDatabase(),Rest_IDs.get(i))){
            imageView.setVisibility(View.INVISIBLE);
        }
        else{
            imageView.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
