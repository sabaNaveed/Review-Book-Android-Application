package pk.edu.iiu.ReviewYourBook;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main6Activity extends AppCompatActivity {

    private ArrayList<UserBO> users;
    private ListView usersListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        usersListView = findViewById( R.id.listUser );

        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                manageUser( users.get(position) );
            }
        });

        fetchAllUsers();

    }

    private void fetchAllUsers()
    {
        DatabaseHelper dbHelper = new DatabaseHelper( this );
        SQLiteDatabase db = dbHelper.getReadableDatabase();

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
                null,
                null,
                null,
                null,
                DBContract.USER.COL_FULL_NAME + " ASC"
        );

        users = new ArrayList<>();
        while(cursor.moveToNext() )
        {
            UserBO userBO = new UserBO();
            userBO.setId(cursor.getLong(0) );
            userBO.setName( cursor.getString(1) );
            userBO.setauthor( cursor.getString(2) );
            userBO.setpages( cursor.getLong(3) );
            userBO.setr1( cursor.getString(4) );
            userBO.setr2( cursor.getString(5) );

            users.add( userBO );
        }

        cursor.close();

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1,
                users );

        usersListView.setAdapter( adapter );

        db.close();
    }

    private void manageUser( UserBO userBo )
    {
        Log.d( "ID", userBo.getId() + "");

        Intent i = new Intent( this, Main7Activity.class );
        i.putExtra( Constants.KEY_USER_ID, userBo.getId() );

        startActivityForResult( i, Constants.REQUEST_CODE_GET );
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if( resultCode == Constants.RESULT_CODE_DELETE )
        {
            Toast.makeText(this, "Book has been deleted.", Toast.LENGTH_SHORT).show();
        }

        fetchAllUsers();
    }}

