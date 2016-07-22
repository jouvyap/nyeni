package bravostudio.nyeni;

import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.List;

import bravostudio.nyeni.Custom.NyeniConstant;
import bravostudio.nyeni.Fragment.AccountFragment;
import bravostudio.nyeni.Fragment.HomeFragment;
import bravostudio.nyeni.Fragment.PhotoViewFragment;
import bravostudio.nyeni.Fragment.UploadFragment;
import bravostudio.nyeni.Fragment.UploadPhotoFragment;
import bravostudio.nyeni.Fragment.UploadVideoFragment;


public class MainActivity extends AppCompatActivity {

    private FrameLayout mMainContent;
    private BottomBar mBottomBar;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainContent = (FrameLayout) findViewById(R.id.frame_layout);
        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
        if (savedInstanceState != null) {
            return;
        }

        // Create a new Fragment to be placed in the activity layout
        HomeFragment firstFragment = new HomeFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame_layout, firstFragment).commit();

        setUpBottomBar(savedInstanceState);
    }

    private void setUpBottomBar(Bundle savedInstanceState){
        mBottomBar = BottomBar.attach(this, savedInstanceState);
//        mBottomBar.setMaxFixedTabs(2);
        mBottomBar.setItems(R.menu.bottom_bar_menu);
        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {
                    changeFragment(NyeniConstant.MENU_TAB.HOME);
                } else if (menuItemId == R.id.bottomBarUpload) {
                    changeFragment(NyeniConstant.MENU_TAB.UPLOAD);
                } else if (menuItemId == R.id.bottomBarAccount) {
                    changeFragment(NyeniConstant.MENU_TAB.ACCOUNT);
                }
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (menuItemId == R.id.bottomBarHome) {
                    if(getVisibleFragment() instanceof HomeFragment){
                        HomeFragment homeFragment = (HomeFragment) getVisibleFragment();
                        homeFragment.gridView.smoothScrollToPosition(0);
                    } else{
                        changeFragment(NyeniConstant.MENU_TAB.HOME);
                    }
                } else if(menuItemId == R.id.bottomBarAccount){
                    if(getVisibleFragment() instanceof AccountFragment){
                        AccountFragment accountFragment = (AccountFragment) getVisibleFragment();
                        accountFragment.gridView.smoothScrollToPosition(0);
                    } else{
                        changeFragment(NyeniConstant.MENU_TAB.ACCOUNT);
                    }
                }
            }
        });

        // Setting colors for different tabs when there's more than three of them.
        // You can set colors for tabs in three different ways as shown below.
        mBottomBar.mapColorForTab(0, ContextCompat.getColor(this, R.color.colorAccent));
        mBottomBar.mapColorForTab(1, 0xFF5D4037);
        mBottomBar.mapColorForTab(2, "#7B1FA2");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Necessary to restore the BottomBar's state, otherwise we would
        // lose the current tab on orientation change.
        mBottomBar.onSaveInstanceState(outState);

    }

    //FRAGMENT TRANSACTION
    public void changeFragment(int menu, String... param){
        Fragment targetFragment = null;
        Bundle args = new Bundle();

        switch (menu){
            case NyeniConstant.MENU_TAB.HOME:
                targetFragment = new HomeFragment();
                break;
            case NyeniConstant.MENU_TAB.UPLOAD:
                targetFragment = new UploadFragment();
                break;
            case NyeniConstant.MENU_TAB.ACCOUNT:
                targetFragment = new AccountFragment();
                break;
            case NyeniConstant.MENU_TAB.PHOTO_VIEW:
                targetFragment = new PhotoViewFragment();
                break;
            case NyeniConstant.MENU_TAB.UPLOAD_PHOTO:
                targetFragment = new UploadPhotoFragment();
                args.putString(NyeniConstant.FRAGMENT_BUNDLE_FILE_URI, param[0]);
                targetFragment.setArguments(args);
                break;
            case NyeniConstant.MENU_TAB.UPLOAD_VIDEO:
                targetFragment = new UploadVideoFragment();
                args.putString(NyeniConstant.FRAGMENT_BUNDLE_FILE_URI, param[0]);
                targetFragment.setArguments(args);
                break;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.frame_layout, targetFragment);

        if(menu != NyeniConstant.MENU_TAB.HOME && menu != NyeniConstant.MENU_TAB.UPLOAD && menu
                != NyeniConstant.MENU_TAB.ACCOUNT) {
            transaction.addToBackStack(null);
        }

        // Commit the transaction
        transaction.commit();
    }

    //GET ACTIVE FRAGMENT
    private Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
}
