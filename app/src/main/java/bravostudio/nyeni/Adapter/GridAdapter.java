package bravostudio.nyeni.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import bravostudio.nyeni.Custom.SquareImageView;
import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/19/16.
 */
public class GridAdapter extends BaseAdapter {

    private Context mContext;

    public GridAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return 14;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new SquareImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER);

            Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(imageView);
        } else {
            imageView = (SquareImageView) convertView;
        }

        return imageView;
    }
}
