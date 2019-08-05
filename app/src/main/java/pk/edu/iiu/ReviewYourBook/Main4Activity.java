package pk.edu.iiu.ReviewYourBook;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main4Activity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;

    private EditText txtName, author,pages,r1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        databaseHelper = new DatabaseHelper( this );

        txtName = findViewById( R.id.s );
        author = findViewById( R.id.f );
        pages = findViewById( R.id.h );
        r1 = findViewById( R.id.text );

    }
    public void f3(View v) {

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
            author.setError( "Please enter page" );
        }
        else if(TextUtils.isEmpty(re) )
        {
            author.setError( "Please enter review" );
        }
        else
        {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put( DBContract.USER.COL_FULL_NAME, name.trim() );
            values.put( DBContract.USER.COL_author, auth.trim() );
            values.put( DBContract.USER.COL_FULL_pages, p.trim() );
            values.put( DBContract.USER._1, re.trim() );


            long id = db.insert( DBContract.USER.TABLE_NAME, null, values );

            if( id > 0 )
            {
                Toast.makeText(this, "book added", Toast.LENGTH_SHORT).show();
            }

            db.close();

            txtName.setText( "" );
            author.setText( "" );
            pages.setText( "" );
            r1.setText( "" );
        }
        finish();
    }

}

