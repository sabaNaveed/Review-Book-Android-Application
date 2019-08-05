package pk.edu.iiu.ReviewYourBook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "review.db";

    private static final String CREATE_TBL_USERS = "CREATE TABLE "
            + DBContract.USER.TABLE_NAME + " (" + DBContract.USER._ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBContract.USER.COL_FULL_NAME
            + " TEXT NOT NULL, " + DBContract.USER.COL_author
            + " TEXT NOT NULL,"+DBContract.USER.COL_FULL_pages+" INTEGER NOT NULL, " +DBContract.USER._1
            +" TEXT NOT NULL,"+DBContract.USER._2+" TEXT )";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( CREATE_TBL_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}