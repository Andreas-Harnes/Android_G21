package no.hiof.fredrivo.budgetapp;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawLayout;
    private Toolbar toolbar;
    private NavigationView navView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawLayout = findViewById(R.id.drawer_layout);

        navView = findViewById(R.id.navigation_view);

        setUpDrawerContent(navView);
    }

    private void setUpDrawerContent(NavigationView navView){
        navView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem){
                    selectDrawerItem(menuItem);
                    return true;
                }
            });

    }

    public void selectDrawerItem(MenuItem menuItem){
        Fragment fragment = null;
        Class fragmentClass;
        switch (menuItem.getItemId()){
            case R.id.menu_Item1:
                fragmentClass = FirstFragment.class;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
