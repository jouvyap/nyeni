package bravostudio.nyeni.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import bravostudio.nyeni.Adapter.GridAdapter;
import bravostudio.nyeni.Custom.CircleImageView;
import bravostudio.nyeni.Custom.NyeniConstant;
import bravostudio.nyeni.Custom.SimpleClientTrackingActivity;
import bravostudio.nyeni.MainActivity;
import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/20/16.
 */
public class AccountFragment extends Fragment {

    private View accountFragmentView;
    public GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        accountFragmentView =  inflater.inflate(R.layout.fragment_account, container, false);

        gridView = (GridView) accountFragmentView.findViewById(R.id.grid_view);
        GridAdapter gridAdapter = new GridAdapter(getContext());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changeFragment(NyeniConstant.MENU_TAB.PHOTO_VIEW);
            }
        });

        CircleImageView profileImage = (CircleImageView) accountFragmentView.findViewById(R.id
                .image_profile);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), SimpleClientTrackingActivity.class);
                startActivity(intent);
            }
        });

        return accountFragmentView;
    }
}
