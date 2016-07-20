package bravostudio.nyeni.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import bravostudio.nyeni.Adapter.GridAdapter;
import bravostudio.nyeni.Custom.NyeniConstant;
import bravostudio.nyeni.MainActivity;
import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/20/16.
 */
public class HomeFragment extends Fragment {

    private View homeFragmentView;
    private SwipeRefreshLayout swipeRefresh;
    public GridView gridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeFragmentView =  inflater.inflate(R.layout.fragment_home, container, false);

        gridView = (GridView) homeFragmentView.findViewById(R.id.grid_view);
        GridAdapter gridAdapter = new GridAdapter(getContext());
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changeFragment(NyeniConstant.MENU_TAB.PHOTO_VIEW);
            }
        });

        swipeRefresh = (SwipeRefreshLayout) homeFragmentView.findViewById(R.id.swiperefresh);
        swipeRefresh.setColorSchemeResources(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(getActivity(), "Refreshing feed", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });

        return homeFragmentView;
    }

}
