package com.edgar.myfirsthomework.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edgar.myfirsthomework.Databases.ContactHelper;

import info.androidhive.materialtabs.R;

public class FourFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG = "myLogs";
    private static final String DATABASE_TABLE = "user_contacts";

    ContactHelper contactHelper;
    private Button buttonCreate, readBtn, clearBtn;
    SQLiteDatabase sqLiteDatabase;
    EditText nameEdTxt, surnameEdTxt, telephoneEdTxt;
    TextView contactTxtView;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactHelper = new ContactHelper(getActivity());
        sqLiteDatabase = contactHelper.getWritableDatabase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_four, container, false);

        buttonCreate = (Button) v.findViewById(R.id.createBtn);
        readBtn = (Button) v.findViewById(R.id.readBtn);
        clearBtn = (Button) v.findViewById(R.id.clearBtn);
        nameEdTxt = (EditText) v.findViewById(R.id.nameEdTxt);
        surnameEdTxt = (EditText) v.findViewById(R.id.surNameEdTxt);
        telephoneEdTxt = (EditText) v.findViewById(R.id.telephoneEdTxt);
        contactTxtView = (TextView) v.findViewById(R.id.contactsTxtView);

        //Создаем обьект contactHelper для создания и управления версиями БД


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG, "Database created");

                final String name = nameEdTxt.getText().toString();
                final String surname = surnameEdTxt.getText().toString();
                final String telephone = telephoneEdTxt.getText().toString();

                ContentValues values = new ContentValues();
                //Задайем значение для каждого столбца
                values.put(ContactHelper.NAME_COLUMN, name);
                values.put(ContactHelper.SURNAME_COLUMN, surname);
                values.put(ContactHelper.TELEPHONE_COLUMN, telephone);
                sqLiteDatabase.insert(DATABASE_TABLE, null, values);
                nameEdTxt.setText("");
                surnameEdTxt.setText("");
                telephoneEdTxt.setText("");
                Log.d(LOG, "We put in database: name --> " + name + ", surnmae --> " + surname + ", telephone --> " + telephone);
                // sqLiteDatabase.close();
            }
        });
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportLoaderManager().initLoader(0, null, FourFragment.this);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG, "Clear my DB");
                int clearCount = sqLiteDatabase.delete(DATABASE_TABLE, null, null);
                Log.d(LOG, "Deleted rows count = " + clearCount);
                // sqLiteDatabase.close();
            }
        });
        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //  contactTxtView.setText("Имя : " + name + "\n Фамилия: " + surname + "\n Телефон: " + telephone);
        return new MyCursorLoader(getActivity(), sqLiteDatabase);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    static class MyCursorLoader extends CursorLoader {

        SQLiteDatabase db;

        public MyCursorLoader(Context context, SQLiteDatabase db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.query(DATABASE_TABLE,
                    null, null, null, null, null, null);
            String name;
            String surname;
            String telephone;

            if (cursor.moveToFirst()) {

                do {
                    int id2 = cursor.getInt(cursor.getColumnIndex(ContactHelper.ID));
                    name = cursor.getString(cursor.getColumnIndex(ContactHelper.NAME_COLUMN));
                    surname = cursor.getString(cursor.getColumnIndex(ContactHelper.SURNAME_COLUMN));
                    telephone = cursor.getString(cursor.getColumnIndex(ContactHelper.TELEPHONE_COLUMN));
                    Log.d(LOG, "ID = " + id2 + ", name = " + name + ", surname = " + surname +
                            ", telephone " + telephone);
                } while (cursor.moveToNext());
            } else
                Log.d(LOG, "There are 0 rows in the DB");
            return cursor;
        }
    }
}