package pk.edu.iiu.ReviewYourBook;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main7Activity extends AppCompatActivity {
    private UserBO selectedUser;private DatabaseHelper databaseHelper; private Button btnUpdate, btnDelete;
    private TextView p1,p2,p3,p4;long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        databaseHelper = new DatabaseHelper( this );
        p1=(TextView) findViewById(R.id.w);
        p2=(TextView) findViewById(R.id.r);
        p3=(TextView) findViewById(R.id.y);
        p4=(TextView) findViewById(R.id.i);
        btnUpdate = findViewById(R.id.btnUpdate );
        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateUser();
            }
        });

        btnDelete = findViewById( R.id.btnDelete );
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteUser();
            }
        });
        id=getIntent().getLongExtra(Constants.KEY_USER_ID, -1);
        populateFields( id);
    }

    private void populateFields( long userId )
    {
        if( userId != -1 )
        {
            UserBO selectedUser = getUserbyId(userId);
            if( selectedUser != null )
            {
                p1.setText( selectedUser.getName() );
                p2.setText( selectedUser.getauthor() );
                p3.setText(String.valueOf(selectedUser.getpages()));
                p4.setText( selectedUser.getr1() );
            }
            else
            {
                Toast.makeText(this, "Book not found!", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Book not available", Toast.LENGTH_SHORT).show();
        }
    }

    private UserBO getUserbyId( long userId )
    {
        selectedUser = null;

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] columns = {
                DBContract.USER._ID,
                DBContract.USER.COL_FULL_NAME,
                DBContract.USER.COL_author,
                DBContract.USER.COL_FULL_pages,
                DBContract.USER._1,
                DBContract.USER._2
        };

        Cursor cursor = db.query(
                DBContract.USER.TABLE_NAME,
                columns,
                DBContract.USER._ID + " = ?",
                new String[]{ String.valueOf(userId)},
                null,
                null,
                null );

        if( cursor != null )
        {
            cursor.moveToFirst();

            selectedUser = new UserBO();
            selectedUser.setId(cursor.getLong(0) );
            selectedUser.setName( cursor.getString(1) );
            selectedUser.setauthor( cursor.getString(2) );
            selectedUser.setpages( cursor.getLong(3) );
            selectedUser.setr1( cursor.getString(4) );
            selectedUser.setr2( cursor.getString(5) );
        }

        cursor.close();
        db.close();

        return selectedUser;
    }
    private void deleteUser()
    {
        if( selectedUser != null )
        {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            int deletedRows = db.delete(
                    DBContract.USER.TABLE_NAME,
                    DBContract.USER._ID + " = ?",
                    new String[]{ String.valueOf(selectedUser.getId())} );

            db.close();

            if( deletedRows > 0 )
            {
                setResult( Constants.RESULT_CODE_DELETE );
                finish();
            }
            else
            {
                Toast.makeText( this, "Book not deleted!", Toast.LENGTH_LONG ).show();
            }
        }
    }

    private void updateUser()
    {

        Log.d( "ID", id + "");

        Intent i = new Intent( this, Main3Activity.class );
        i.putExtra( Constants.KEY_USER_ID, id );

        startActivityForResult( i, Constants.REQUEST_CODE_GET );

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if( resultCode == Constants.RESULT_CODE_EDIT )
        { populateFields( id);
            Toast.makeText(this, "Book has been updated.", Toast.LENGTH_SHORT).show();
        }



    }


}
