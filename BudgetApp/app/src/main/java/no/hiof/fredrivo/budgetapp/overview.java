package no.hiof.fredrivo.budgetapp;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

public class overview extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Intent intentInputActivity;

    private NotificationCompat.Builder notification;
    private GoogleSignInAccount account;

    //Ha med på flere activities
    private DrawerLayout draw;
  //  private ActionBarDrawerToggle drawerToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Google login
        account = GoogleSignIn.getLastSignedInAccount(this);


//        if(account != null) {
//            Toast.makeText(this, account.getGivenName(), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Ingen konto", Toast.LENGTH_SHORT).show();
//        }

        setContentView(R.layout.activity_overview);

        //Toolbar og navigationDrawer start:
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // implementering av navigation drawer!
        draw = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,draw,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_textView);
        txtProfileName.setText(account.getDisplayName());

        ImageView imgProfilePicture = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

        Picasso.get().load(account.getPhotoUrl()).into(imgProfilePicture);

        // slutt for navi drawer

        notification = new NotificationCompat.Builder(this);

        // Creates the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Sets up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




       intentInputActivity = new Intent(getApplicationContext(), InputActivity.class);
        // FAB button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                day_tab.clearExpensesList();
                startActivity(intentInputActivity);
            }
        });



        //setting up tabs with adapter from new class: sectionspageadapter
        mSectionsPagerAdapter= new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
//         tablayout end



    }

    //viewpager and adapter
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new day_tab(),"Day");
        adapter.addFragment(new week_tab(),"Week");
        adapter.addFragment(new month_tab(),"Month");
        viewPager.setAdapter(adapter);
    }

    // inflating button for camera
    @Override
    public boolean onCreateOptionsMenu(Menu m){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.camera_button,m);

        return true;
    }
    //action when button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.camera_button){
            Toast.makeText(this, "Opening Camera", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this,Camera_activity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        int id = menuItem.getItemId();

        if (id == R.id.overview) {
            draw.closeDrawers();

        } else if (id == R.id.profile) {
            Intent intent = new Intent(this, ProfilActivity.class);
            startActivity(intent);

        } else if (id == R.id.detail) {
            Intent intent = new Intent(this,DetailActivity.class);
            startActivity(intent);

        } else if (id == R.id.chart) {
            if (ProfilActivity.getIncome() == 0){
                Toast.makeText(this, "Pleas fill out profile settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ProfilActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, ChartActivity.class);
                startActivity(intent);

            }

        }

        draw.closeDrawer(GravityCompat.START);
        return true;
    }




}


