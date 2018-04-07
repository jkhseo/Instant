package food.instant.instant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.ArrayList;

/**
 * Created by mpauk on 3/30/2018.
 */

public class messages_adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Conversation> conversations;
    public messages_adapter(Context context, ArrayList<Conversation> conversations){
        this.context = context;
        this.conversations = conversations;
    }
    @Override
    public int getCount() {
        return conversations.size();
    }

    @Override
    public Object getItem(int i) {
        return conversations.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.conversation_list_view_element, null);
        TextView contact = view.findViewById(R.id.contact_name);
        contact.setText("");
        TextView lastMessage = view.findViewById(R.id.contact_last_message);
        lastMessage.setText(conversations.get(i).getLastMessage());
        return null;
    }
}