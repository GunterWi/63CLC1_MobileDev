package vn.nguyenquocthai.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BookNavigationActivity extends AppCompatActivity {
    SQLiteDatabase bookDB;
    Cursor csPointToRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_navigation);
        bookDB = openOrCreateDatabase("Sach.db", MODE_PRIVATE, null );
        bookDB.execSQL("DROP TABLE IF EXISTS BOOKS");
        bookDB.execSQL("CREATE TABLE BOOKS(BookID integer PRIMARY KEY, BookName text, Page integer, Price Float, Description text);");
        bookDB.execSQL("INSERT INTO BOOKS VALUES(1, 'Java', 100, 9.99, 'sách về java')");
        bookDB.execSQL("INSERT INTO BOOKS VALUES(2, 'Python', 100, 9.99, 'sách về python')");
        bookDB.execSQL("INSERT INTO BOOKS VALUES(3, 'C#', 100, 9.99, 'sách về c#')");
        bookDB.execSQL("INSERT INTO BOOKS VALUES(4, 'C++', 100, 9.99, 'sách về c++')");

        csPointToRecord= bookDB.rawQuery("Select * from BOOKS", null);
        csPointToRecord.moveToFirst(); //

        Button btFirst = findViewById(R.id.btnBookFirst);
        Button btLast = findViewById(R.id.btnBookLast);
        Button btNext = findViewById(R.id.btnBookNext);
        Button btPrevious = findViewById(R.id.btnBookPrevious);

        btFirst.setOnClickListener(xuLyFirst);
        btNext.setOnClickListener(xuLyNext);
        btLast.setOnClickListener(xuLyLast);
        btPrevious.setOnClickListener(xuLyPrevious);
    }  // end of OnCreate
    //==========================================================================
    // Cac bo lang nghe va xu lys
    View.OnClickListener xuLyFirst = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            csPointToRecord.moveToFirst();
            capNhatView();
        }
    };
    //==========================================================================
    void capNhatView() {
        //Lay du lieu, dat len cac dieu khien
        int ID = csPointToRecord.getInt(0) ;
        String name  =csPointToRecord.getString(1) ;
        int numPage = csPointToRecord.getInt(2) ;
        float price = csPointToRecord.getInt(3) ;
        String des= csPointToRecord.getString(4);
        TextView tvID  = findViewById(R.id.tvBookId);
        TextView tvName  = findViewById(R.id.tvBookName);
        TextView tvPage  = findViewById(R.id.tvBookPage);
        TextView tvPrice  = findViewById(R.id.tvBookPrice);
        TextView tvDes  = findViewById(R.id.tvBookDes);
        tvID.setText( String.valueOf(ID) );
        tvName.setText(name);
        tvPage.setText(String.valueOf(numPage) );
        tvPrice.setText( String.valueOf(price) );
        tvDes.setText( des );
    }
    //==========================================================================
    View.OnClickListener xuLyNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!csPointToRecord.isLast()) {
                csPointToRecord.moveToNext();
                capNhatView();
            }
        }
    };
    View.OnClickListener xuLyLast = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            csPointToRecord.moveToLast();
            capNhatView();
        }
    };
    View.OnClickListener xuLyPrevious = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(!csPointToRecord.isFirst()) {
                csPointToRecord.moveToPrevious();
                capNhatView();
            }
        }
    };


}
