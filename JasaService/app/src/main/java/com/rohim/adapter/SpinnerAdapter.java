package com.rohim.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nurochim on 19/10/2016.
 */

public class SpinnerAdapter extends ArrayAdapter {
    private Context context;
    private int textViewResourceId;
    private List<String> objects;
    public static boolean flag = false;

    public SpinnerAdapter(Context context, int textViewResourceId,
                          List<String> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = View.inflate(context, textViewResourceId, null);
        if (flag != false) {
            TextView tv = (TextView) convertView;
            tv.setText(objects.get(position));
        }
        return convertView;
    }
}
