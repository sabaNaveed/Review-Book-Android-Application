package pk.edu.iiu.ReviewYourBook;

import android.provider.BaseColumns;

public class DBContract
{
    public static abstract class USER implements BaseColumns
    {
        public static final String TABLE_NAME = "review";
        public static final String COL_FULL_NAME = "full_name";

        public static final String COL_author= "author";
        public static final String COL_FULL_pages= "pages";
        public static final String _1= "r1";
        public static final String _2= "r2";

    }
}
