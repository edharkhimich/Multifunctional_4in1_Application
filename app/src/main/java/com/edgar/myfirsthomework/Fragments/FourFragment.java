package com.edgar.myfirsthomework.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.ListView;
import android.widget.Toast;

import com.edgar.myfirsthomework.Activities.ListActivity;
import com.edgar.myfirsthomework.Adapters.MyCursorAdapter;
import com.edgar.myfirsthomework.Databases.ContactHelper;


import info.androidhive.materialtabs.R;

public class FourFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG = "myLogs";
    public static final String DATABASE_TABLE = "user_contacts";

    ContactHelper contactHelper;
    private Button buttonCreate, readBtn, clearBtn;
    SQLiteDatabase sqLiteDatabase;
    EditText nameEdTxt, surnameEdTxt, telephoneEdTxt;
    ListView contactsListView;
    MyCursorAdapter adapter;
    Cursor cursor;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG, "onCreateView Four Fragment");
        View v = inflater.inflate(R.layout.fragment_four, container, false);
        buttonCreate = (Button) v.findViewById(R.id.createBtn);
        readBtn = (Button) v.findViewById(R.id.readBtn);
        clearBtn = (Button) v.findViewById(R.id.clearBtn);
        nameEdTxt = (EditText) v.findViewById(R.id.nameEdTxt);
        surnameEdTxt = (EditText) v.findViewById(R.id.surNameEdTxt);
        telephoneEdTxt = (EditText) v.findViewById(R.id.telephoneEdTxt);
        contactsListView = (ListView) v.findViewById(R.id.contactListView);

        adapter = new MyCursorAdapter(getActivity());
        contactsListView.setAdapter(adapter);
        contactHelper = new ContactHelper(getActivity());
        sqLiteDatabase = contactHelper.getWritableDatabase();


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameEdTxt.getText().toString();
                final String surname = surnameEdTxt.getText().toString();
                final String telephone = telephoneEdTxt.getText().toString();

                if (sqLiteDatabase != null) {
                    Log.d(LOG, "IF");

                    ContentValues values = new ContentValues();

                    values.put(ContactHelper.NAME_COLUMN, name);
                    values.put(ContactHelper.SURNAME_COLUMN, surname);
                    values.put(ContactHelper.TELEPHONE_COLUMN, telephone);
                    sqLiteDatabase.insert(DATABASE_TABLE, null, values);

                    nameEdTxt.setText("");
                    surnameEdTxt.setText("");
                    telephoneEdTxt.setText("");
                    Toast.makeText(getActivity(), "Contact created", Toast.LENGTH_SHORT).show();

                }
                Log.d(LOG, "We put in database: name --> " + name + ", surnmae --> " + surname + ", telephone --> " + telephone);
            }
        });
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
//                }else
//                getActivity().getSupportLoaderManager().restartLoader(0, null, FourFragment.this);
//                adapter.notifyDataSetChanged();
            }
        });
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG, "Clear my DB");
                int clearCount = sqLiteDatabase.delete(DATABASE_TABLE, null, null);
                getActivity().getSupportLoaderManager().restartLoader(0, null,FourFragment.this);
                adapter.notifyDataSetChanged();
                Log.d(LOG, "Clear = " + clearCount);
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
        Log.d(LOG, "onCreateLoader");
        return new MyCursorLoader(getActivity(), sqLiteDatabase);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(LOG, "onLoadFinished");
        cursor = sqLiteDatabase.query(DATABASE_TABLE,
                null, null, null, null, null, null);

        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG, "onLoaderReset");
        adapter.swapCursor(null);
    }


    static class MyCursorLoader extends CursorLoader {

        SQLiteDatabase db;

        public MyCursorLoader(Context context, SQLiteDatabase db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Log.d(LOG, "loadInBackground");
            return db.query(DATABASE_TABLE,
                    null, null, null, null, null, null);
        }
    }
}





