package com.edgar.myfirsthomework.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.edgar.myfirsthomework.Adapters.MyCursorAdapter;
import com.edgar.myfirsthomework.Databases.ContactHelper;
import com.edgar.myfirsthomework.Fragments.FourFragment;

import info.androidhive.materialtabs.R;


public class ListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    String key = "value";

    ListView listViewFFF;
    SQLiteDatabase sqLiteDatabase;
    MyCursorAdapter adapter;
    Cursor cursor;
    ContactHelper contactHelper;


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_five);

        listViewFFF = (ListView) findViewById(R.id.list_view_five);
        adapter = new MyCursorAdapter(getApplicationContext());
        listViewFFF.setAdapter(adapter);
        contactHelper = new ContactHelper(this);
        sqLiteDatabase = contactHelper.getWritableDatabase();
        getSupportLoaderManager().restartLoader(0, null, ListActivity.this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, sqLiteDatabase);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        cursor = sqLiteDatabase.query(FourFragment.DATABASE_TABLE,
                null, null, null, null, null, null);

        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {
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
            return db.query(FourFragment.DATABASE_TABLE,
                    null, null, null, null, null, null);
        }
    }
}
