package com.flipboard.myapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.myapplication.R;
import com.flipboard.myapplication.data.db.apihelper.RealmHelper;
import com.flipboard.myapplication.data.db.model.NotificationDB;
import com.flipboard.myapplication.ui.ProductDetail.ProductDetailActivity;
import com.flipboard.myapplication.ui.pdf.PdfActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationDB> dataSet;
    private Activity mContext;

    public NotificationAdapter(List<NotificationDB> os_versions, Activity context) {
        this.dataSet = os_versions;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.layout_notification_recyclerview, viewGroup, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        NotificationDB fp = dataSet.get(i);

        if (fp.getmIsRead()){
            viewHolder.cardViewRecyclerViewNotification.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }else{
            viewHolder.cardViewRecyclerViewNotification.setBackgroundColor(Color.WHITE);
        }

        viewHolder.txtNotificationRecyclerViewTitle.setText(fp.getmNotificationTitle());
        viewHolder.txtNotificationRecyclerViewContent.setText(fp.getmNotificationMessage());
        viewHolder.txtNotificationRecyclerViewTime.setText(GetTimeSpan(fp.getmTime()));
        viewHolder.cardViewRecyclerViewNotification.setOnClickListener(v -> {
            NotificationDB fp1 = dataSet.get(i);
            new RealmHelper(mContext).updateToRead(fp1.getmProductId());
            if (fp1.getmType().equals("product")){
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id", fp1.getmProductId());
                mContext.startActivity(intent);
            }else {
                Intent intent = new Intent(mContext, PdfActivity.class);
                mContext.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_notification_recyclerView_title)
        TextView txtNotificationRecyclerViewTitle;
        @BindView(R.id.txt_notification_recyclerView_time)
        TextView txtNotificationRecyclerViewTime;
        @BindView(R.id.txt_notification_recyclerView_content)
        TextView txtNotificationRecyclerViewContent;
        @BindView(R.id.cardView_recyclerView_notification)
        LinearLayout cardViewRecyclerViewNotification;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            ButterKnife.bind(this, itemLayoutView);
        }

    }

    private String GetTimeSpan(String time){
        String GetTime = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date past = null;
        try {
            past = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date now = new Date();
        long seconds= TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
        long minutes=TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
        long hours=TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
        long days=TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());

        if(seconds<60)
        {
            GetTime = seconds+" seconds ago";
        }
        else if(minutes<60)
        {
            GetTime = minutes+" minutes ago";
        }
        else if(hours<24)
        {
            GetTime = hours+" hours ago";
        }
        else
        {
            GetTime = days+" days ago";
        }
        return GetTime;
    }
}

