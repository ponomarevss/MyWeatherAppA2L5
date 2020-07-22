package com.ponomarevss.myweatherapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.customview.widget.Openable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.ponomarevss.myweatherapp.ui.history.HistoryFragment;
import com.ponomarevss.myweatherapp.ui.home.HomeFragment;
import com.ponomarevss.myweatherapp.ui.place.PlaceFragment;
import com.ponomarevss.myweatherapp.ui.settings.SettingsFragment;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    public AlertDialog alertDialog;
    private BroadcastReceiver networkReceiver;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = findViewById(R.id.drawer_layout);
        initToolbar(toolbar);
        initNavigationView();
        placeHomeFragment();



        //BROADCAST BLOCK
//        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.no_network + "\n", BaseTransientBottomBar.LENGTH_INDEFINITE);
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                checkNetwork(context) !=
//                checkBattery(context);
//                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//                    Network network = connectivityManager.getActiveNetwork();
//                    if (network == null) {
//                        snackbar.show();
//                        return;
//                    }
//                    if (snackbar.isShown())
//                        snackbar.dismiss();
//                }
            }
        };
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, filter);
        //END OF BROADCAST BLOCK
    }

    @Override
    public void onBackPressed() {
        if (alertDialog != null) alertDialog.dismiss();
        if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
        else if (Objects.requireNonNull(navigationView
                .getCheckedItem())
                .getItemId() != R.id.nav_home) {
            placeHomeFragment();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(networkReceiver);
        super.onDestroy();
    }


    //NAVIGATION METHODS BLOCK
    private void initToolbar(Toolbar toolbar) {
        toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();
    }

    private void initNavigationView() {
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    if (!item.isChecked()) {
                        replaceFragment(HomeFragment.class);
                        toggleBehaviour(toggle,true);
                    }

                    break;
                case R.id.nav_place:
                    if (!item.isChecked()) {
                        replaceFragment(PlaceFragment.class);
                        toggle.setDrawerIndicatorEnabled(false);
                        toggleBehaviour(toggle,false);
                    }
                    break;
                case R.id.nav_settings:
                    if (!item.isChecked()) {
                        replaceFragment(SettingsFragment.class);
                        toggle.setDrawerIndicatorEnabled(false);
                        toggleBehaviour(toggle,false);
                    }
                    break;
                case R.id.nav_history:
                    if (!item.isChecked()) {
                        replaceFragment(HistoryFragment.class);
                        toggle.setDrawerIndicatorEnabled(false);
                        toggleBehaviour(toggle,false);
                    }
                    break;
                default:
                    return false;
            }
            setTitle(item.getTitle());
            if (drawer.isDrawerOpen(GravityCompat.START)) drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void replaceFragment(Class<? extends Fragment> fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
        }
    }

    private void placeHomeFragment() {
        replaceFragment(HomeFragment.class);
        setTitle(R.string.menu_home);
        navigationView.setCheckedItem(R.id.nav_home);
        toggleBehaviour(toggle, true);
    }

    private void toggleBehaviour(final ActionBarDrawerToggle toggle, boolean b) {
        if (b) {
            toggle.setDrawerIndicatorEnabled(true);
            toggle.setToolbarNavigationClickListener(view ->
                    drawer.openDrawer(GravityCompat.START));
        }
        else {
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setToolbarNavigationClickListener(view -> {
                onBackPressed();
                toggle.setDrawerIndicatorEnabled(true);
            });
        }
    }
    //END OF NAVIGATION METHODS BLOCK


    //ALERT MESSAGE BLOCK
    public void showAlertMessage(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View contentView = getLayoutInflater().inflate(R.layout.alert_message, null);
        TextView message = contentView.findViewById(R.id.message_view);
        message.setText(s);
        builder.setView(contentView);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void hideAlertMessage() {
        alertDialog.hide();
    }
    //END OF ALERT MESSAGE BLOCK
}

