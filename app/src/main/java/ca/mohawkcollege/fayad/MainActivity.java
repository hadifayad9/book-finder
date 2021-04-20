package ca.mohawkcollege.fayad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

/*
I, Hadi Fayad, 000397306 certify that this material is my original work.
No other person's work has been used without due
acknowledgement.
https://youtu.be/nAP7qwP-ycc
 */

/**
 * MainActivity one is used to use the user input and determine weather the user wants a book title or author search
 * also uses the navbar
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "==MainActivity==";
    private DrawerLayout myDrawer;
    NavigationView myNavView;
    public Button bookButton;
    public Switch authorSwitch;
    public EditText bookTitle;
    @Override
    /**
     * Oncreate for the main method that calls the different methods like the navbarView() and the bookTitleCheck();
     * The oncreate also calls the buttons and sets the click listener
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navBarView();

        bookButton = findViewById(R.id.bookSearchButton);
        authorSwitch = findViewById(R.id.authorSwitch);
        bookButton.setOnClickListener(this::BookButtonClick);
        bookTitleInputCheck();
        Log.d(TAG,"onCreate");
        bookTitle.setText("");
    }
    /**
     * Displays the navbar using the navigation view and the actionbar. It also sets the toggles for checking
     * when the navbar is open
     */
    public void navBarView(){
        Log.d(TAG,"navBarView");
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
     * Search button for the application that sends information to another activity that uses the startDownload method
     * this method also checks if the user wants to use the author mode or the title mode
     * @param view
     */
    public void BookButtonClick(View view){
        Log.d(TAG,"BookButtonClick");
        Intent switchActivity = new Intent(MainActivity.this, MainActivity2.class);
        bookTitle = (EditText) findViewById(R.id.bookFinderText);
        if (authorSwitch.isChecked()) { // If the switch is checked then use the author search
            String authorSearch = bookTitle.getText().toString();
            switchActivity.putExtra("authorSearch", authorSearch); // Go to next activity
        } else {
            String bookTitleText = bookTitle.getText().toString();
            switchActivity.putExtra("bookSearch", bookTitleText);
        }
        startActivity(switchActivity);
    }
    /**
     * Navbar method that checks if the drawer is open or not when the hamburger menu is opened
     * @param item
     * @return item
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG,"onOptionsItemSelected");
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
        Log.d(TAG,"onNavigationItemSelected");
        myNavView.setCheckedItem(item);
        myDrawer.closeDrawers();
        switch (item.getItemId()) {
            case R.id.searchNavBar:
                Intent switchActivitySearch = new Intent(MainActivity.this,MainActivity.class);
                startActivity(switchActivitySearch);
                break;
            case R.id.aboutNavBar:
                Intent switchActivityAbout = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(switchActivityAbout);
                break;
            case R.id.bookFavNav:
                Intent switchActivityFav = new Intent(MainActivity.this,MainActivity4.class);
                startActivity(switchActivityFav);
        }
        return false;
    }
    /**
     * The purpose of this method is to check for the book title text
     * if the text field is empty then the button and switch is disabled
     * if the there is text then the button and switch are enabled
     */
    public void bookTitleInputCheck(){
        bookTitle = (EditText) findViewById(R.id.bookFinderText);
        TextView textChecker = findViewById(R.id.textChecker);
        bookTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            /**
             * onTextChange execute the code if the string is empty then disable it
             * @param s
             * @param start
             * @param before
             * @param count
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputVal = bookTitle.getText().toString().trim();
                if(inputVal.isEmpty()){
                    textChecker.setText(getResources().getString(R.string.textChecker));
                    bookButton.setEnabled(false);
                    authorSwitch.setEnabled(false);
                }else{
                    textChecker.setText("");
                    bookButton.setEnabled(true);
                    authorSwitch.setEnabled(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}