package com.edgar.myfirsthomework.Databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

public class ContactHelper extends SQLiteOpenHelper {

    public static final String LOG = "myLogs";

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";

    //Имя таблицы
    private static final String DATABASE_TABLE = "user_contacts";

    //Название столбцов
    public static final String NAME_COLUMN = "name";
    public static final String ID = "_id";
    public static final String SURNAME_COLUMN = "surname";
    public static final String TELEPHONE_COLUMN = "telephone";

    //Создаем таблицу и помещаем ее в String
    private static final String DATABASE_CREATE_SCRIPT =
            "create table " + DATABASE_TABLE
                    + " (" + ID + " integer primary key autoincrement, "
                    + NAME_COLUMN + " text, "
                    + SURNAME_COLUMN + " text, "
                    + TELEPHONE_COLUMN + " text)";

    public ContactHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG, "Выполняется метод onCreate() из нашей БД");
        sqLiteDatabase.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(LOG, "Обновяется с версии: " + i + ", на новую версию: " + i1);

        sqLiteDatabase.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);

        onCreate(sqLiteDatabase);
    }
}

