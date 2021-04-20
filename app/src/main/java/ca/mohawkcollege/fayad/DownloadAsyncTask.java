package ca.mohawkcollege.fayad;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * DownloadAsync class that gets the JSON object and is used to get information from that object
 */
public class DownloadAsyncTask extends AsyncTask<String, Void, String> {
    public static final String TAG = "==DownloadActivity==";
    private TextView bookTitle;
    Books books = new Books();
    Activity currentActivity = MainActivity2.getCurrentActivity();
    /**
     * Download method. This method creates a httpUrl and checks for the connection using the
     * google api url, if the connection is sucessful then it should return 200 and it should return the book json file
     * then it opens up a inputstream and a buffer reader that reads the json file
     */
    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "Starting Background Task");
        StringBuilder results = new StringBuilder();
        try {
            URL url = new URL(params[0]);
            String line = null;
            // Open the Connection
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            // Read the response
            int statusCode = conn.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = new BufferedInputStream(
                        conn.getInputStream());
                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream,
                                "UTF-8"));
                while ((line = bufferedReader.readLine()) != null) {
                    results.append(line);
                }
            }
            if(statusCode == 400){
                Log.d(TAG, "doInBackground: Status Code 400");
            }
            Log.d(TAG, "Data received = " + results.length());
            Log.d(TAG, "Response Code: " + statusCode);

        } catch (IOException ex) {
            Log.d(TAG, "Caught Exception: " + ex);
        }
        return results.toString();
    }

    /**
     * OnPostExecute. This code takes in the json object and converts that object into a readable class that uses
     * the books class to assign the json objects to the books class property, this method also creates an array adapter
     * and sets the list to whatever information it gets from the json file.
     * @param result
     */
    protected void onPostExecute(String result) {
        if (result == null) {
            Log.d(TAG, "No JSON RESULT");
        } else {
            Gson gson = new Gson();
            books = gson.fromJson(result, Books.class); // Sets the result received from the google api to a gson object using googles gson
            Log.d(TAG, "Result: " + result);
        }
        ListView lv = currentActivity.findViewById(R.id.listView);
        if (books != null) {
            ArrayAdapter<Items> adapter =
                    new ArrayAdapter<Items>(currentActivity,
                            android.R.layout.simple_list_item_1, books.items); //Creates an array adapter of simple list that  holds the array list of books

            lv.setAdapter(adapter);
            lv.setOnItemClickListener(this::onItemClick);
        } else {
            // clear the list
            lv.setAdapter(null);
        }
    }
    /**
     * The purpose of this method is to check when the user clicks on a book to display a dialog fragment
     * that displays the book picture, title, description, publisher, and date of publish. This method also uses the
     * onItemClick position to determine which book is clicked and gets its information
     * @param parent
     * @param v
     * @param position
     * @param id
     */
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        Log.d(TAG, "item clicked on = " + position);
        Log.d(TAG, "adapter = " + parent);
        Log.d(TAG, "View = " + v);
        Log.d(TAG, "id = " + id);

        Activity activity = MainActivity2.getCurrentActivity();
        FragmentManager ft = ((FragmentActivity)activity).getSupportFragmentManager();
        books.items.get(position);

        String urlIMG = books.items.get(position).volumeInfo.imageLinks.thumbnail;
        String bookTitle = books.items.get(position).volumeInfo.title;
        String bookPublisher = books.items.get(position).volumeInfo.publisher;
        String bookDateOfPublish =books.items.get(position).volumeInfo.publishedDate;
        String bookInformation = books.items.get(position).volumeInfo.description;
        ArrayList<String> bookAuthor = books.items.get(position).volumeInfo.authors;



        //Uses bundle to set information over to display in the dialogfragment
        Bundle bc = new Bundle();
        bc.putString("bookTitle",bookTitle);
        bc.putString("bookPublisher",bookPublisher);
        bc.putString("bookDateOfPublish",bookDateOfPublish);
        bc.putString("bookInformation",bookInformation);
        bc.putString("urlIMG",urlIMG);
        bc.putStringArrayList("authorList", bookAuthor);

        DialogFragment myDialog = new DialogFragment();
        myDialog.setArguments(bc);
        myDialog.show(ft,null);
    }

}