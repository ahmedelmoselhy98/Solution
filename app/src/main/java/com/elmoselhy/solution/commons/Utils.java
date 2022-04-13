package com.elmoselhy.solution.commons;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

//import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import com.elmoselhy.solution.R;
import com.elmoselhy.solution.dialogs.MyCustomDialog;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Utils {
    public static void autoScrollRecycler(final RecyclerView recyclerView, final int dataListSize) {
        final int speedScroll = 3500;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (recyclerView != null) {
                    if (count > dataListSize + 1) {
                        count = 0;
                    }
                    if (count <= dataListSize + 1) {
                        recyclerView.smoothScrollToPosition(count);
                        count++;
                        handler.postDelayed(this, speedScroll);
                    }
                }
            }
        };
        handler.postDelayed(runnable, speedScroll);
    }

    //Get Device Id
    public static String getDeviceId() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
    }
    public static void animateCollapse(ImageView arrow) {
        RotateAnimation rotate =
                new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.clearAnimation();
        arrow.setAnimation(rotate);
    }



    @BindingAdapter("imageBinding")
    public static void bindUser(ImageView view, String imageUrl) {
        Picasso.get()
                .load(imageUrl).fit()
                .placeholder(R.color.line_color_grey)
                .into(view);
    }


    // Open Url
    public static void openUrl(Context context, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("openUrl Error:", e.getMessage());
        }
    }
    public static void dialPhoneNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
    @BindingAdapter("notNullText")
    public static void text(TextView textView, String str) {
        textView.setText(str == null || str.isEmpty() || str.trim().isEmpty() || str == "null"|| str == "null null" ? "" : str);
    }

    @BindingAdapter("notNullHtmlText")
    public static void textHtml(TextView textView, String str) {
        String txt = str == null || str.isEmpty() || str.trim().isEmpty() || str == "null" ? "" : str;
        textView.setText(Html.fromHtml(txt));
    }

    public static String formatDate(int dayOfMonth, int monthOfYear, int year) {
        return (dayOfMonth <= 9 ? "0" + dayOfMonth : dayOfMonth) + "/" + ((monthOfYear + 1) <= 9 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + year;
    }
    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return df.format(c);
    }
    public static String getCurrentDateNotFormatted() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        return df.format(c);
    }
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty() || str == "null";
    }
    public static void showWarningDialog(Context context, String message, MyCustomDialog.ClickAction clickAction){
        MyCustomDialog myCustomDialog = new MyCustomDialog(context);
        myCustomDialog.setTitle("Warning!");
        myCustomDialog.setMessage(message);
        myCustomDialog.isInfoDialog(true);
        myCustomDialog.clickAction(clickAction);
        myCustomDialog.show();
    }

    public static void showBlockDialog(Context context, String message, MyCustomDialog.ClickAction clickAction){
        MyCustomDialog myCustomDialog = new MyCustomDialog(context);
        myCustomDialog.setTitle("Blocked!");
        myCustomDialog.setMessage(message);
        myCustomDialog.isBlockDialog(true);
        myCustomDialog.clickAction(clickAction);
        myCustomDialog.show();
    }
    public static void openUrlWhatsApp(Context context, String smsNumber, String message) {
        String link="https://api.whatsapp.com/send?phone=" + smsNumber;
        if(!message.isEmpty())
            link+="&text="+message;
        Log.e("link:",link);
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }
    public static void openImagesPicker(Context context) {
        ImagePicker.with((Activity) context)
                .setRequestCode(100)
                .setMultipleMode(false)
                .setMaxSize(10)
                .start();
    }
}
