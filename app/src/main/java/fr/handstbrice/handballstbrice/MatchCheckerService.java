package fr.handstbrice.handballstbrice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class MatchCheckerService extends Service {
    private int notificationIDCounter=0;
    SimpleDateFormat df;
    SimpleDateFormat hf;
    public MatchCheckerService() {
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        df=new SimpleDateFormat(getString(R.string.date_format));
        hf=new SimpleDateFormat(getString(R.string.heure_format));
        startTimer();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("uk.ac.shef.oak.ActivityRecognition.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 0, 30L*60L*1000L); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                try {
                    List<Match> matchs=FluxRSS.scanLastMatch().execute().get();

                    SharedPreferences bdd = getBaseContext().getSharedPreferences("BDD", MODE_PRIVATE);
                    long dateDernierMatchUTC=bdd.getLong("dateDernierMatcthUTC", 0);
                    long dateDernierMatchUTCNew=dateDernierMatchUTC;
                    Intent intent = new Intent(MatchCheckerService.this, AccueilActivite.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MatchCheckerService.this, 0, intent, 0);

                    for (Match match : matchs)
                    {
                        if (match!=null && (dateDernierMatchUTC==0 || dateDernierMatchUTC<match.getDateUTC()))
                        {
                            Date date=new Date(match.getDateUTC());
                            //notification
                            NotificationCompat.Builder notification = new NotificationCompat.Builder(MatchCheckerService.this, getString(R.string.notification_channel_name))
                                .setSmallIcon(R.mipmap.logo_club_round)
                                .setContentTitle(getString(R.string.app_name))
                                .setContentText(String.format(getString(R.string.msg_nouveau_match), match.getEquipeLocale(), match.getEquipeExterieure(),
                                    df.format(date),
                                    hf.format(date),
                                    match.getScoreEquipeLocale(), match.getScoreEquipeExterieure()))
                                .setContentIntent(pendingIntent)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MatchCheckerService.this);

// notificationId is a unique int for each notification that you must define
                            notificationManager.notify(notificationIDCounter++, notification.build());
                            dateDernierMatchUTCNew=Math.max(dateDernierMatchUTCNew, match.getDateUTC());

                        }
                    }
                    //maj date
                    bdd.edit().putLong("dateDernierMatcthUTC", dateDernierMatchUTCNew).commit();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel  channel = new NotificationChannel(getString(R.string.notification_channel_name), name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
