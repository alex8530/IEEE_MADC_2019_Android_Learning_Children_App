package com.arapeak.adkya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arapeak.adkya.R;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public SpinnerAdapter(List<String> list, Context context ) {
        this.list = list;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View mview = view;

        if (view==null)
            mview=layoutInflater.inflate(R.layout.row_spinner_item2,null);

        TextView textView= mview.findViewById(R.id.text1);
        textView.setText(list.get(i));
        return mview;
    }
}
