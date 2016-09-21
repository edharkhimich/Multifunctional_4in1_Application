package com.edgar.myfirsthomework.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.edgar.myfirsthomework.Databases.ContactHelper;

import info.androidhive.materialtabs.R;


public class MyCursorAdapter extends CursorAdapter {
    LayoutInflater cursorInflater;

    public MyCursorAdapter(Context context) {
        super(context, null, true);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.list_details, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameInList = (TextView) view.findViewById(R.id.nameInList);
        TextView idInList = (TextView) view.findViewById(R.id.idInList);
        TextView surnameInList = (TextView) view.findViewById(R.id.surnameInList);
        TextView telephoneInList = (TextView) view.findViewById(R.id.telephoneInList);

        nameInList.setText(cursor.getString(cursor.getColumnIndex(ContactHelper.NAME_COLUMN)));
        surnameInList.setText(cursor.getString(cursor.getColumnIndex(ContactHelper.SURNAME_COLUMN)));
        idInList.setText((cursor.getPosition() + 1) + "");
        telephoneInList.setText(cursor.getString(cursor.getColumnIndex(ContactHelper.TELEPHONE_COLUMN)));
    }
}
