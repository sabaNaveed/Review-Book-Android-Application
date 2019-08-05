package pk.edu.iiu.ReviewYourBook;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;long id;private UserBO selectedUser;
    private Button btnUpdate;
    private EditText txtName, author,pages,r1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        databaseHelper = new DatabaseHelper( this );
        btnUpdate = findViewById(R.id.u );
        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                updateUser();
            }
        });

        txtName = findViewById( R.id.s );
        author = findViewById( R.id.f );
        pages = findViewById( R.id.h );
        r1 = findViewById( R.id.text );
        id=getIntent().getLongExtra(Constants.KEY_USER_ID, -1);

        if( id != -1 )
        {
            UserBO selectedUser = getUserbyId(id);
            if( selectedUser != null )
            {
                txtName.setText( selectedUser.getName() );
                author.setText( selectedUser.getauthor() );
                pages.setText(String.valueOf(selectedUser.getpages()));
                r1.setText( selectedUser.getr1() );
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



        return selectedUser;
    }
    private void updateUser()
    {
        String name = txtName.getText().toString();
        String auth = author.getText().toString();
        String p = pages.getText().toString();
        String re = r1.getText().toString();

        if(TextUtils.isEmpty(name) )
        {
            txtName.setError( "Please enter name" );
        }
        else if(TextUtils.isEmpty(auth) )
        {
            author.setError( "Please enter author" );
        }
        else if(TextUtils.isEmpty(p) )
        {
            pages.setError( "Please enter page" );
        }
        else if(TextUtils.isEmpty(re) )
        {
            r1.setError( "Please enter review" );
        }
        else
        {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put( DBContract.USER.COL_FULL_NAME, name.trim() );
            values.put( DBContract.USER.COL_author, auth.trim() );
            values.put( DBContract.USER.COL_FULL_pages, p.trim() );
            values.put( DBContract.USER._1, re.trim() );
            int updatedRows = db.update(
                    DBContract.USER.TABLE_NAME,
                    values,
                    DBContract.USER._ID + " = ?",
                    new String[]{ String.valueOf(selectedUser.getId())});

            db.close();

            if( updatedRows > 0 )
            {
                setResult( Constants.RESULT_CODE_EDIT );
                finish();
            }
            else
            {
                Toast.makeText( this, "Book not updated!", Toast.LENGTH_LONG ).show();
            }
        }
    }
}
