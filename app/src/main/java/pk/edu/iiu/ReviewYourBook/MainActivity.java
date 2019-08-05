package pk.edu.iiu.ReviewYourBook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private UserBO selectedUser;private DatabaseHelper databaseHelper;
    private EditText txtName;private Button btnsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById( R.id.i1 );
        databaseHelper = new DatabaseHelper( this );
        btnsearch = findViewById(R.id.btn1 );
        btnsearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user();

            }
        });
    }

    private void user()
    {
        String s = txtName.getText().toString();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor;
        String[] columns = {
                DBContract.USER._ID,
                DBContract.USER.COL_FULL_NAME,
                DBContract.USER.COL_author,
                DBContract.USER.COL_FULL_pages,
                DBContract.USER._1,
                DBContract.USER._2
        };

        cursor = db.query(
                DBContract.USER.TABLE_NAME,
                columns,
                DBContract.USER.COL_FULL_NAME+ " = ?",
                new String[]{s},
                null,
                null,
                null );
        if( cursor == null )
        {cursor.close();
            Toast.makeText(this, "Book not available", Toast.LENGTH_SHORT).show();
        }
        else if (!cursor.moveToFirst()) {
            cursor.close();
            Toast.makeText(this, "Enter Book name", Toast.LENGTH_SHORT).show();
        }
        else
        {
            cursor.moveToFirst();

            selectedUser = new UserBO();
            selectedUser.setId(cursor.getLong(0) );
            selectedUser.setName( cursor.getString(1) );
            selectedUser.setauthor( cursor.getString(2) );
            selectedUser.setpages( cursor.getLong(3) );
            selectedUser.setr1( cursor.getString(4) );
            selectedUser.setr2( cursor.getString(5) );
            Log.d("ID", selectedUser.getId() + "");

            Intent i = new Intent(this, Main7Activity.class);
            i.putExtra(Constants.KEY_USER_ID, selectedUser.getId());
            txtName.setText( "" );
            startActivity(i); cursor.close();
        }


        db.close();



    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.b:
                Intent intent = new Intent(MainActivity.this, Main4Activity.class);
                startActivity(intent);
                return true;
            case R.id.a:
                Intent i = new Intent(MainActivity.this, Main6Activity.class);
                startActivity(i);
                return true;
            case R.id.c:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void showHelp(){
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }

}
