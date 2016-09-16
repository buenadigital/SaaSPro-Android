package com.buenadigital.saaspro.navDrawer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.buenadigital.saaspro.Constants;
import com.buenadigital.saaspro.R;
import com.buenadigital.saaspro.activities.LoginActivity;
import com.buenadigital.saaspro.services.SettingsService;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawerFragment extends Fragment implements NavigationAdapter.ClickListener {

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSaveInstanseState;
    private View mContainerView;

    private RecyclerView mRecyclerView;

    private NavigationAdapter mNavigationAdapter;
    private ImageView mSocialFacebook;
    private ImageView mSocialTwitter;
    private ImageView mSocialYoutube;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), Constants.PREFERENCES_LEARNDRAWER_KEY, "false"));
        if(savedInstanceState != null){
            mFromSaveInstanseState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mRecyclerView = (RecyclerView)layout.findViewById(R.id.navigation_list);
        mNavigationAdapter = new NavigationAdapter(getActivity(), getNavigationData());
        mNavigationAdapter.setClickListener(this);
        mRecyclerView.setAdapter(mNavigationAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }


    public List<NavigationItem> getNavigationData(){
        List<NavigationItem> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_logout};
        String[] titles = {getString(R.string.logout)};
        ColorStateList[] colorStateLists = {
                ColorStateList.valueOf(getResources().getColor(R.color.primary_text))};

        for(int i = 0; i<titles.length && i<icons.length; i++){
            NavigationItem currentItem = new NavigationItem();
            currentItem.iconId = icons[i];
            currentItem.title = titles[i];
            currentItem.txtColor =colorStateLists[i];
            data.add(currentItem);
        }
        return data;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolBar) {
        mContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;


        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolBar, R.string.open, R.string.close){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), Constants.PREFERENCES_LEARNDRAWER_KEY, mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }


        };

//        if(!mUserLearnedDrawer && !mFromSaveInstanseState){
//            mDrawerLayout.openDrawer(containerView);
//        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }
    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }

    public void clearSettings(){
        SettingsService.settings.token = "";
        SettingsService.settings.securityQuestion = "";
        SettingsService.settings.securityQuestionValidated = false;
        SettingsService.saveSettings();
    }


    public void navigateToLogout(){
        clearSettings();
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


    @Override
    public void itemClicked(View view, int position) {
        switch (position){
            case 0: // Logout
                navigateToLogout();
                break;
            default:
                break;
        }
    }


}
