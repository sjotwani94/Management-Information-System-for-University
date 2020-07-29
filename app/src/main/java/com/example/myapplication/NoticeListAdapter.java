package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.MyViewHolder> {
    private final Context context;
    private List<NoticeListData> functionsList;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    Activity activity;
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
        activity = (Activity) context;
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
                PackageManager pm = context.getPackageManager();
                try {
                    String s = new String(bb, "UTF-8");
                    Uri bitmapUri = Uri.parse(s);
                    Bitmap imgBitmap=BitmapFactory.decodeByteArray(bb, 0, bb.length);
                    String imgBitmapPath="";
                    if (ContextCompat.checkSelfPermission(context,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {

                        // Permission is not granted
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(activity,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            // Show an explanation to the user *asynchronously* -- don't block
                            // this thread waiting for the user's response! After the user
                            // sees the explanation, try again to request the permission.
                        } else {
                            // No explanation needed; request the permission
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        // Permission has already been granted
                        imgBitmapPath= MediaStore.Images.Media.insertImage(context.getContentResolver(),imgBitmap,"title",null);
                    }
                    Uri imgBitmapUri=Uri.parse(imgBitmapPath);
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, imgBitmapUri);
                    sendIntent.setType("image/*");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, listData.getNoticeSubject()+"\n"+listData.getNoticeSender()+"\n"+listData.getNoticeDescription());
                    PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    //Check if package exists or not. If not then code
                    //in catch block will be called
                    sendIntent.setPackage("com.whatsapp");
                    sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    context.startActivity(Intent.createChooser(sendIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
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