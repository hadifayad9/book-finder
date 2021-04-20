package ca.mohawkcollege.fayad;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * DialogFragment class that is used to display the book information
 */
public class DialogFragment extends androidx.fragment.app.DialogFragment{
    MyDBHelper myDBHelperl;
    private TextView bookName;
    URL imageurl;
    public static String TAG = "==DialogFragment==";

    /**
     * Constructor for the dialogFragment
     */
    public DialogFragment() {
        Log.d(TAG,"Construction");
        // Required empty public constructor
    }
    /**
     * OnCreate for the dialog fragment that displays the fragment. This method grabs the information from the
     * downloadAsyncTask using a bundle and sets the book information to the respective text.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        Log.d(TAG, "onCreateView, myview"+ v);
        Bundle bundle = this.getArguments(); //Creates a bundle

        myDBHelperl = new MyDBHelper(v.getContext());
        bookName = v.findViewById(R.id.bookName);
        TextView bookInformation = v.findViewById(R.id.bookDescription);
        TextView bookPublisher = v.findViewById(R.id.publisherText);
        TextView bookPublisherDate = v.findViewById(R.id.dateOfPublishText);
        Button addButton = v.findViewById(R.id.addToFav);
        addButton.setOnClickListener(this::addData);
        ImageView bookImage = v.findViewById(R.id.bookImage);
//
//        Log.d(TAG, "Book Name: " + bundle.getString("bookTitle"));
//        Log.d(TAG, "Book Information: " + bundle.getString("bookInformation"));
//        Log.d(TAG, "Date of Publish: " + bundle.getString("bookDateOfPublish"));
//        Log.d(TAG, "Book Publisher: " + bundle.getString("bookPublisher"));
//        Log.d(TAG, "Authors of Book:" + bundle.getStringArrayList("authorList"));

        //Sets the text to the text the bundle sends over using the key
        bookPublisher.setText(bundle.getString("bookPublisher"));
        bookPublisherDate.setText(bundle.getString("bookDateOfPublish"));
        bookName.setText(bundle.getString("bookTitle"));
        bookInformation.setText(bundle.getString("bookInformation"));
        //Uses Picasso to set the image of the book to the imageview
        try {
            imageurl = new URL (bundle.getString("urlIMG"));
            Log.d(TAG, "UrlIMG: "+bundle.getString("urlIMG"));
            Picasso.with(v.getContext()).load(String.valueOf(imageurl)).fit().into(bookImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return v;
    }
    public void addData(View view){
        Log.d(TAG, "Book Name (ADD DATA): " + bookName.getText());
        Log.d(TAG, "addData: UrlIMG" +  String.valueOf(imageurl));
        SQLiteDatabase db = myDBHelperl.getWritableDatabase();
        ContentValues userAddition = new ContentValues();
        userAddition.put(MyDBHelper.NAME,bookName.getText().toString());
        userAddition.put(MyDBHelper.IMGURL, String.valueOf(imageurl));
        long newrowID = db.insert(MyDBHelper.BOOKTABLE, null, userAddition);
    }
}