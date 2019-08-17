package com.glriverside.xgqin.ggtimer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TimerLogAdapter extends ArrayAdapter<TimerLog> {
    private Context mContext;
    private int resourceId;
    private List<TimerLog> mLogData;

    public TimerLogAdapter(@NonNull Context context, int resource, List<TimerLog> data) {
        super(context, resource, data);
        this.mContext = context;
        this.resourceId = resource;
        this.mLogData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TimerLog log = getItem(position);
        View view;

        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(resourceId, parent , false);
            viewHolder = new ViewHolder();
            viewHolder.tvNumber = view.findViewById(R.id.tv_number);
            viewHolder.tvTimestamp = view.findViewById(R.id.tv_timestamp);
            viewHolder.tvTickItem = view.findViewById(R.id.tv_interval_item);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tvNumber.setText("No. " + (log.getNumber()));
        viewHolder.tvTickItem.setText("Tick: " + log.intervalToString());
        // String dateFormat = "HH:mm:ss";

        // SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // viewHolder.tvTimestamp.setText(formatter.format(log.getTime()));
        viewHolder.tvTimestamp.setText(log.toString());
        return view;
    }

    class ViewHolder {
        TextView tvNumber;
        TextView tvTimestamp;
        TextView tvTickItem;
    }
}
