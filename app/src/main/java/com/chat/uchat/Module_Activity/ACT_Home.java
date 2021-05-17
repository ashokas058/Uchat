package com.chat.uchat.Module_Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.chat.uchat.Module_Fragment.ViewPageAdapter;
import com.chat.uchat.Module_DataStore.StaticConfig;

import com.chat.uchat.Module_Fragment.FGMT_Friends;
import com.chat.uchat.Module_Fragment.FRMT_Group;
import com.chat.uchat.Module_Fragment.FGMT_Profile;
import com.chat.uchat.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ACT_Home extends AppCompatActivity {
    private static String TAG = "ACT_Home";
    private ViewPager viewPager;
    private TabLayout tabLayout = null;
    public static String STR_FRIEND_FRAGMENT = "FRIEND";
    public static String STR_GROUP_FRAGMENT = "GROUP";
    public static String STR_INFO_FRAGMENT = "INFO";
    private FloatingActionButton floatButton;
    private ViewPageAdapter viewPageAdapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        initHomeViewComponents();
        initTab();
        initFirebase();

    }
    private void initHomeViewComponents(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        floatButton = (FloatingActionButton) findViewById(R.id.fab);
    }
    private void initFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    StaticConfig.UID = user.getUid();
                } else {
                    startActivity(new Intent(getApplicationContext(), ACT_Login.class));
                    finish();
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    private void initTab() {
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorIndivateTab));
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_tab_person,
                R.drawable.ic_tab_group,
                R.drawable.ic_tab_infor
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFrag(new FGMT_Friends(), STR_FRIEND_FRAGMENT);
        viewPageAdapter.addFrag(new FRMT_Group(), STR_GROUP_FRAGMENT);
       viewPageAdapter.addFrag(new FGMT_Profile(), STR_INFO_FRAGMENT);
        floatButton.setOnClickListener(((FGMT_Friends) viewPageAdapter.getItem(0)).onClickFloatButton.getInstance(this));
        viewPager.setAdapter(viewPageAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (viewPageAdapter.getItem(position) instanceof FGMT_Friends) {
                    floatButton.setVisibility(View.VISIBLE);
                    floatButton.setOnClickListener(((FGMT_Friends) viewPageAdapter.getItem(position)).onClickFloatButton.getInstance(ACT_Home.this));
                    floatButton.setImageResource(R.drawable.plus);
                } else if (viewPageAdapter.getItem(position) instanceof FRMT_Group) {
                    floatButton.setVisibility(View.VISIBLE);
                    floatButton.setOnClickListener(((FRMT_Group) viewPageAdapter.getItem(position)).onClickFloatButton.getInstance(ACT_Home.this));
                    floatButton.setImageResource(R.drawable.ic_float_add_group);
                } else {
                    floatButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Toast.makeText(this, "Rivchat version 1.0", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}