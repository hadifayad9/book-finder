package ca.mohawkcollege.fayad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

/**
 * MainActivity2 is used for getting the information about the book title or author and sending that information to downloadasync
 * which then displays information about a list of books to the user
 */
public class MainActivity2 extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG = "==MainActivity2==";
    private DrawerLayout myDrawer;
    NavigationView myNavView;
    private static Activity currentActivity = null;

    /**
     * OnCreate for the book information. This method calls the startDownload() Method and the navbarView() method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(TAG,"onCreate");
        navBarView();
        startDownload();
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
     * Navbar method that checks if the drawer is open or not
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
                Intent switchActivties = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(switchActivties);
                break;
            case R.id.aboutNavBar:
                Intent switchActivties2 = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(switchActivties2);
                break;
            case R.id.bookFavNav:
                Intent switchActivityFav = new Intent(MainActivity2.this,MainActivity4.class);
                startActivity(switchActivityFav);
        }
        return false;
    }

    /**
     * Creates a method to return the current activity its in
     * @return
     */
    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * StartDownload() method that gets the incomingAuthor or the incomingSearch text that the user enters
     * then it uses the downloadAsync Class to execute the search using googles api url with the users input
     * If the user uses the author Switch then it uses the author search
     * If the user searchers it uses the regular title search
     */
    public void startDownload() {
        Intent intent = getIntent();
        String incomingBookSearch = intent.getStringExtra("bookSearch");
        String incomingAuthorSearch = intent.getStringExtra("authorSearch");
        Log.d(TAG, "incomingBookSearch: "+incomingBookSearch);
        Log.d(TAG, "incomingAuthorSearch: "+incomingAuthorSearch);

        DownloadAsyncTask dl = new DownloadAsyncTask();
        //if the authorsearch is not null use the author search
        if (incomingAuthorSearch != null) {
            String authorSearchReplace = incomingAuthorSearch.replaceAll(" ",  "+");
            String uri = String.format("https://www.googleapis.com/books/v1/volumes?q=inauthor:%s&maxResults=25&orderBy=relevance", authorSearchReplace);
            Log.d(TAG,uri);
            dl.execute(uri);
        //if the booksearch is not null use the author search
        }else if(incomingBookSearch != null){
            String bookTitleReplace = incomingBookSearch.replaceAll(" ", "+");
            String uri = String.format("https://www.googleapis.com/books/v1/volumes?q=%s&maxResults=25&orderBy=relevance", bookTitleReplace);
            dl.execute(uri);
        }
    }
}