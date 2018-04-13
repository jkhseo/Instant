package food.instant.instant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;

import java.io.IOException;
import java.net.URL;

/**
 * Created by mpauk on 4/12/2018.
 */

public class GetServerImages extends AsyncTask<String,Void,Bitmap[]> {
    ViewPager viewPager;
    Context c;
    public GetServerImages(ViewPager pager,Context c){
        viewPager=pager;
        this.c=c;

    }
    @Override
    protected Bitmap[] doInBackground(String... urls) {
        Bitmap[] bitmaps = new Bitmap[urls.length];
        for(int i=0;i<urls.length;i++){
            try {
                bitmaps[i] = BitmapFactory.decodeStream(new URL(urls[i]).openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmaps;
    }
    @Override
    protected void onPostExecute(Bitmap[] bitmap){
        ImageAdapter adapter = new ImageAdapter(c,bitmap);
        viewPager.setAdapter(adapter);
    }
}
