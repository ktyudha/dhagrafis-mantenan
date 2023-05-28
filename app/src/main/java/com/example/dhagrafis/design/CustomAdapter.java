package com.example.dhagrafis.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.dhagrafis.R;
import com.example.dhagrafis.models.PaketList;

public class CustomAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<PaketList> paketLists;

    public CustomAdapter(Context context, ArrayList<PaketList> paketLists) {
        this.context = context;
        this.paketLists = paketLists;
    }

    @Override
    public int getCount() {
        return paketLists.size();
    }

    @Override
    public Object getItem(int position) {
        return paketLists.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_card, parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        PaketList list = paketLists.get(position);
//        holderView.iconList.setImageResource(list.getPacketMediaIcon());
        holderView.paketTitle.setText(list.getName());
        holderView.paketDetaildesc.setText(list.getDescription());
        holderView.paketDetailprice.setText(Integer.toString(list.getPrice()));
        holderView.paketDetailname.setText(list.getCategory());
        return convertView;
    }

    static class HolderView{
        private final ImageView iconList;
        private final TextView paketTitle;
        private final TextView paketDetailname;
        private final TextView paketDetaildesc;
        private final TextView paketDetailprice;


        public HolderView(View view) {
            iconList = view.findViewById(R.id.listpaket);
            paketTitle = view.findViewById(R.id.namelistpaket);
            paketDetailname = view.findViewById(R.id.namedetailpaket);
            paketDetaildesc = view.findViewById(R.id.descdetailpaket);
            paketDetailprice = view.findViewById(R.id.pricedetailpaket);
        }
    }
}
