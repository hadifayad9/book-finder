package ca.mohawkcollege.fayad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    private ArrayList<String> myBookList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        MyDBHelper mydbHelp = new MyDBHelper(this);
        ListView bookList = findViewById(R.id.bookList);
        ImageView bookListIMG = findViewById(R.id.bookListIMG);
        SQLiteDatabase db = mydbHelp.getReadableDatabase();
        String[] projection = {MyDBHelper.ID, MyDBHelper.NAME};

        Cursor mycursor = db.query(MyDBHelper.BOOKTABLE, projection,null,null,null,null,null,null);
        try {
            URL imageurl = new URL(MyDBHelper.IMGURL);
            Picasso.with(getApplication()).load(String.valueOf(imageurl)).fit().into(bookListIMG);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        String[] coloums = new String[] {MyDBHelper.NAME};
        SimpleCursorAdapter myAdapater = new SimpleCursorAdapter(this, R.layout.book_list, mycursor, coloums, new int[]{R.id.txtName, R.id.bookListIMG});
        bookList.setAdapter(myAdapater);
    }
}