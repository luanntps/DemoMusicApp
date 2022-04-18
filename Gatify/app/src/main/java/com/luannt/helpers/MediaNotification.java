package com.luannt.helpers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.luannt.R;
import com.luannt.activities.MainActivity;
import com.luannt.model.Song;
import com.luannt.service.NotificationActionService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.pm.PackageManager;

public class MediaNotification  {
    public static final String CHANNEL_ID="channel1";
    public static final String ACTION_PREVIOUS ="actionprevious";
    public static final String ACTION_PLAY ="actionplay";
    public static final String ACTION_NEXT ="actionnext";
    public static final String ACTION_SEEK="actionseek";

    public static Notification notification;

    public static void createNotification(Context context, Song song, int playbutton, int pos, int size) throws IOException {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

            Bitmap icon=getBitmapFromURL(song.getUrl_song_pic());
            //
            PendingIntent pendingIntentPrevious;
            int drw_previous;

            if(pos==0){
                pendingIntentPrevious=null;
                drw_previous=0;
            }else{
                Intent intentPrevious = new Intent(context, NotificationActionService.class)
                .setAction(ACTION_PREVIOUS);
                pendingIntentPrevious=PendingIntent.getBroadcast(context,0,intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_previous=R.drawable.ic_baseline_skip_previous;
            }

            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_PLAY);
            PendingIntent pendingIntentPlay=PendingIntent.getBroadcast(context,0,intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent pendingIntentNext;
            int drw_next;
            if(pos==size){
                pendingIntentNext=null;
                drw_next=0;
            }else{
                Intent intentNext = new Intent(context,NotificationActionService.class)
                        .setAction(ACTION_NEXT);
                pendingIntentNext=PendingIntent.getBroadcast(context,0,intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_next=R.drawable.ic_baseline_skip_next;
            }
            //quay lai activity chay background
            Intent i = context.getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName())
                    .setPackage(null)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

            PendingIntent pIntent = PendingIntent.getActivity(context, 0, i, 0);

            //
            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.avt)
                    .setContentTitle(song.getSong_name())
                    .setLargeIcon(icon)
                    .setOnlyAlertOnce(true)
                    .setShowWhen(false)
                    .setContentIntent(pIntent)
                    .addAction(drw_previous,"Previous",pendingIntentPrevious)
                    .addAction(playbutton,"Play",pendingIntentPlay)
                    .addAction(drw_next,"Next",pendingIntentNext)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                            .setShowActionsInCompactView(0,1,2)
                            )
                    .setPriority(NotificationCompat.PRIORITY_LOW)
                    .build();
            notificationManagerCompat.notify(1,notification);
        }
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
