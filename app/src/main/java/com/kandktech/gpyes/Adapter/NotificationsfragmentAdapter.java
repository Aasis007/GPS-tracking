package com.kandktech.gpyes.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kandktech.gpyes.Model.NotificationsModel;
import com.kandktech.gpyes.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class NotificationsfragmentAdapter extends RecyclerView.Adapter<NotificationsfragmentAdapter.ViewHolder>  {

    List<NotificationsModel.Notification_data> notificationsModels;
    Context context;


    public NotificationsfragmentAdapter(Context context, List<NotificationsModel.Notification_data> notificationsModels) {
            this.context = context;
            this.notificationsModels = notificationsModels;



    }

    @NonNull
    @Override
    public NotificationsfragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notofication_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsfragmentAdapter.ViewHolder holder, int position) {


        holder.notititle.setText(Html.fromHtml(notificationsModels.get(position).getTitle()));
        holder.noti_desc.setText(Html.fromHtml(notificationsModels.get(position).getDescription()));

        //convert created at to time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            long time = Objects.requireNonNull(sdf.parse(notificationsModels.get(position).getCreated_at())).getTime();
            long now = System.currentTimeMillis();
            CharSequence ago =
                    DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS);
            holder.notidate.setText(ago);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return notificationsModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView notidate,notititle,noti_desc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noti_desc = itemView.findViewById(R.id.noti_desc);
            notidate = itemView.findViewById(R.id.noti_date);
            notititle = itemView.findViewById(R.id.noti_title);


        }
    }
}
