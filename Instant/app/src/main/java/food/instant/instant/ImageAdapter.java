package food.instant.instant;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by mpauk on 4/12/2018.
 */

public class ImageAdapter extends PagerAdapter {
    private Context context;
    private Bitmap[] bitmaps;

    public ImageAdapter(Context context,Bitmap[] bitmaps){
        this.context=context;
        this.bitmaps=bitmaps;
    }
    @Override
    public int getCount() {
        return bitmaps.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (object);
    }
    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position){
        View view =  LayoutInflater.from(context).inflate(R.layout.view_pager_element, null);
        ImageView imageView = view.findViewById(R.id.restaurant_image);
        imageView.setImageBitmap(bitmaps[position]);
        ((ViewPager)viewGroup).addView(view);
        return view;
    }
}
