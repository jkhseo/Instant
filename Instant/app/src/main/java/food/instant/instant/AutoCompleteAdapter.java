package food.instant.instant;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by mpauk on 2/14/2018.
 */

public class AutoCompleteAdapter extends CursorAdapter{
    public AutoCompleteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.auto_complete_view,viewGroup,false);
        TextView auto_complete = view.findViewById(R.id.auto_complete_text);
        return auto_complete;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView)view).setText(cursor.getString(cursor.getColumnIndex("Restaurant")));

    }
}
