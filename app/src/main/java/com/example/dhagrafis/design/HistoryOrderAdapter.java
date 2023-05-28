package com.example.dhagrafis.design;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dhagrafis.R;
import com.example.dhagrafis.models.Order;
import com.example.dhagrafis.models.PaketList;

import java.util.ArrayList;

public class HistoryOrderAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Order> orders;

    public HistoryOrderAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.history_card, parent, false);
            holderView = new HolderView(convertView);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        Order order = orders.get(position);
//        holderView.iconList.setImageResource(order.getPacketMediaIcon());
        holderView.nameHist.setText(order.getNameOrder());
        holderView.prizeHist.setText(order.getPriceOrder());
        return convertView;
    }

    private static class HolderView{
        private final ImageView iconList;
        private final TextView nameHist;
        private final TextView cateHist;
        private final TextView descHist;
        private final TextView prizeHist;


        public HolderView(View view) {
            iconList = view.findViewById(R.id.listpaket);
            nameHist = view.findViewById(R.id.nameHistory);
            cateHist = view.findViewById(R.id.cateHistory);
            descHist = view.findViewById(R.id.descHistory);
            prizeHist = view.findViewById(R.id.priceHistory);
        }
    }
}