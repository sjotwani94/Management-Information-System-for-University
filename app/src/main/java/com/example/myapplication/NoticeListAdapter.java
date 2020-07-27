package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.MyViewHolder> {
    private final Context context;
    private List<NoticeListData> functionsList;
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView noticeSender, noticeSubject,noticeDescription;
        ImageView noticeImage;
        Button shareWhatsApp;
        MyViewHolder(View view) {
            super(view);
            noticeSender = view.findViewById(R.id.notice_sender);
            noticeSubject = view.findViewById(R.id.notice_subject);
            noticeDescription = view.findViewById(R.id.notice_desc);
            noticeImage = view.findViewById(R.id.notice_image);
            shareWhatsApp = view.findViewById(R.id.share_whatsapp);
        }
    }
    public NoticeListAdapter(Context context, List<NoticeListData> functionsList) {
        this.context=context;
        this.functionsList = functionsList;
    }
    @NonNull
    @Override
    public NoticeListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.cardview_notice,parent,false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeListAdapter.MyViewHolder holder, int position) {
        final NoticeListData listData = functionsList.get(position);
        holder.noticeSender.setText(listData.getNoticeSender());
        holder.noticeSubject.setText(listData.getNoticeSubject());
        holder.noticeDescription.setText(listData.getNoticeDescription());
        final byte[] bb = listData.getNoticeImage();
        holder.noticeImage.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
        holder.shareWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = new String(bb, "UTF-8");
                    Uri bitmapUri = Uri.parse(s);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, listData.getNoticeSubject()+"\n"+listData.getNoticeSender()+"\n"+listData.getNoticeDescription());
                    sendIntent.setPackage("com.whatsapp");
                    context.startActivity(sendIntent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return functionsList.size();
    }
}