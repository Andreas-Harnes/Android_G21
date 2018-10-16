package no.hiof.fredrivo.budgetapp;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

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
                fragmentClass = InputActivity.class;
                break;
            case R.id.menu_Item2:
                fragmentClass = InputActivity.class;
                break;
            case R.id.menu_Item3:
                fragmentClass = InputActivity.class;
                break;
            default:
                fragmentClass = InputActivity.class;

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout container = findViewById(R.id.content_frame);
        inflater.inflate(R.layout.activity_main, container);

        menuItem.setChecked(true);

        setTitle(menuItem.getTitle());

        drawLayout.closeDrawers();
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
