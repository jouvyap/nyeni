package bravostudio.nyeni.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bravostudio.nyeni.R;

/**
 * Created by jouvyap on 7/20/16.
 */
public class AccountFragment extends Fragment {

    private View accountFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        accountFragmentView =  inflater.inflate(R.layout.fragment_account, container, false);

        return accountFragmentView;
    }
}
