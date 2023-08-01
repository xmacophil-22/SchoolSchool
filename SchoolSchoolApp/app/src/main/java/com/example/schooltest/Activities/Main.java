package com.example.schooltest.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.schooltest.Helpers.ActivityHandler;
import com.example.schooltest.Database.MySQLiteHelper;
import com.example.schooltest.Fragments.FoodActivity;
import com.example.schooltest.Fragments.GradesActivity;
import com.example.schooltest.R;
import com.example.schooltest.Helpers.Snippet;
import com.google.android.material.navigation.NavigationView;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private MySQLiteHelper db;
    ///////////////////////////////Activität in der die Navigationsleiste läuft, mit der die verschiedenen Fregments (unsere Funktionen der App) aufgerufen werden können
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new MySQLiteHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    ///////////////////////////////navigiert in ein anderes Fragment oder Loggt aus

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_grades:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new GradesActivity()).commit();
                break;
            case R.id.nav_food:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FoodActivity()).commit();
                break;
            case R.id.nav_logout:
                db.delete_User(1);
                Snippet snippet = new Snippet();
                snippet.clearSnippet();
                db.change_Snippet(Snippet.mySnippet, 1);
                ActivityHandler.switchActivity(this, MainActivity.class, "","");
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }
}
