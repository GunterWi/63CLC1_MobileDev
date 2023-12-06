package com.nguyenquocthai.real_time_tracker.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nguyenquocthai.real_time_tracker.Model.NotificationItem;
import com.nguyenquocthai.real_time_tracker.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<NotificationItem> notificationItems;

    public NotificationsAdapter(List<NotificationItem> notificationItems) {
        this.notificationItems = notificationItems;
    }

    @NonNull
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.ViewHolder holder, int position) {
        NotificationItem item = notificationItems.get(position);
        holder.textView.setText(item.getMessage());
        holder.timeView.setText(item.getTimestamp());
        //holder.iconView.setImageResource(item.getIconResource());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationItems.size();
    }
    public List<NotificationItem> getNotificationItems() {
        return notificationItems;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconView;
        public TextView textView;
        public TextView timeView;

        public ViewHolder(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.icon);
            textView = itemView.findViewById(R.id.notification_text);
            timeView = itemView.findViewById(R.id.notification_time);
        }
    }

}
