package bravostudio.nyeni.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bravostudio.nyeni.Custom.SquareImageView;
import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/20/16.
 */
public class PhotoViewFragment extends Fragment {

    private View photoViewFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        photoViewFragment = inflater.inflate(R.layout.fragment_photo_view, container, false);

        SquareImageView squareImageView = (SquareImageView) photoViewFragment.findViewById(R.id
                .image);
        Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png").into(squareImageView);

        RelativeLayout favorite = (RelativeLayout) photoViewFragment.findViewById(R.id
                .favorite_layout);
        final TextView favorite_count = (TextView) photoViewFragment.findViewById(R.id.favorite_count);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(favorite_count.getText().toString());
                count++;
                favorite_count.setText("" + count);
            }
        });

        return photoViewFragment;
    }
}
