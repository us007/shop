package com.flipboard.myapplication.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flipboard.myapplication.R;

import java.util.List;

import io.realm.RealmResults;

/**
 */

public class CartProductSpinnerAdapter extends ArrayAdapter<String> {

    private Context context1;
    private List<String> data;
    public Resources res;
    LayoutInflater inflater;

    public CartProductSpinnerAdapter(Context context, List<String> objects) {
        super(context, R.layout.layout_spinner_row, objects);
        context1 = context;
        data = objects;
        inflater = (LayoutInflater) context1
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(R.layout.layout_spinner_title, parent, false);
        TextView tvCategory = (TextView) row.findViewById(R.id.tvCategory);
       // tvCategory.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_white, 0);
        tvCategory.setText(data.get(position));
        return row;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.layout_spinner_row, parent, false);
        TextView tvCategory = (TextView) row.findViewById(R.id.tvCategory);
        tvCategory.setText(data.get(position));
        return row;
    }
}
