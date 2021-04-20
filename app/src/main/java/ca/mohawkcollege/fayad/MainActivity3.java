package ca.mohawkcollege.fayad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

/**
 * MainActivity3 is used for the about page
 */
public class MainActivity3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout myDrawer;
    NavigationView myNavView;
    public static final String TAG = "==MainActivity3==";
    @Override
    /**
     * OnCreate for the about page. THis method calls the navBarView() Method
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Log.d(TAG,"onCreate");
        navBarView();
    }
    /**
     * Displays the navbar using the navigation view and the actionbar. It also sets the toggles for checking
     * when the navbar is open
     */
    public void navBarView(){
        myDrawer = (DrawerLayout)
                findViewById(R.id.drawer_layout);
        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle myactionbartoggle = new
                ActionBarDrawerToggle(this, myDrawer,
                (R.string.open), (R.string.close));
        myDrawer.addDrawerListener(myactionbartoggle);
        myactionbartoggle.syncState();
        myNavView = (NavigationView)
                findViewById(R.id.nav_view);
        myNavView.setNavigationItemSelectedListener(this);
    }
    /**
     * Navbar method that checks if the drawer is open or not when the hamburger menu is opened
     * @param item
     * @return item
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Find out the current state of the drawer (open or closed)
        boolean isOpen = myDrawer.isDrawerOpen(GravityCompat.START);
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // Home button - open or close the drawer
                if (isOpen == true) {
                    myDrawer.closeDrawer(GravityCompat.START);
                } else {
                    myDrawer.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * NavBar on item selected that when a item is clicked on the navbar it execute code
     * the code takes the user to the intended activity they clicked on
     * @param item
     * @return item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        myNavView.setCheckedItem(item);
        myDrawer.closeDrawers();
        switch (item.getItemId()) {
            case R.id.searchNavBar:
                Intent switchActivitySearch = new Intent(MainActivity3.this,MainActivity.class);
                startActivity(switchActivitySearch);
                break;
            case R.id.aboutNavBar:
                Intent switchActivityAbout = new Intent(MainActivity3.this,MainActivity3.class);
                startActivity(switchActivityAbout);
                break;
            case R.id.bookFavNav:
                Intent switchActivityFav = new Intent(MainActivity3.this,MainActivity4.class);
                startActivity(switchActivityFav);
        }
        return false;
    }
}