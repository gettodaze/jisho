package com.example.jishorough2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

import static android.util.Log.d;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private EditText searchbox;
    private LinearLayout resultbox;
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        searchFragment = SearchFragment.newInstance();
        transaction.add(R.id.fragment_container, searchFragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        getSupportFragmentManager().getFragments().get(0);
        if (searchFragment != null && searchFragment.isVisible()) {
            searchFragment.onKeyUp(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }



}
