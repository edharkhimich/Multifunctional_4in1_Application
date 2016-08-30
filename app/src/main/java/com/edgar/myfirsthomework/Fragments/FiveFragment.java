package com.edgar.myfirsthomework.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import info.androidhive.materialtabs.R;

public class FiveFragment extends Fragment {
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_five, container, false);
        listView = (ListView) v.findViewById(R.id.list_view_five);
        return v;
    }
}
