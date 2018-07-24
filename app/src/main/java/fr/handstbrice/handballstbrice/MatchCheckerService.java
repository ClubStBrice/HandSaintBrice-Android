package fr.handstbrice.handballstbrice;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import fr.handstbrice.handballstbrice.model.Match;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class MatchCheckerService extends Service {
    public MatchCheckerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        final Handler handler=new Handler();

        Runnable repetitiveTask=new Runnable() {
            @Override
            public void run() {
                //TODO à compléter
                try {
                    Match match=FluxRSS.scanLastMatch().execute().get();
                    SharedPreferences bdd = getBaseContext().getSharedPreferences("BDD", MODE_PRIVATE);
                    long dateDernierMatchUTC=bdd.getLong("dateDernierMatcthUTC", 0);

                    if (match!=null && (dateDernierMatchUTC==0 || dateDernierMatchUTC<match.getDateUTC())
                    {
                        //notification
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                            .setSmallIcon(R.drawable.notification_icon)
                            .setContentTitle("Handball St Brice Courcelles")
                            .setContentText("Un match aura lieu le ")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        //maj date
                        bdd.edit().putLong("dateDernierMatcthUTC", match.getDateUTC()).commit();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


                handler.postDelayed(this, 30L*60L*1000L);
            }
        };
        repetitiveTask.run();

    }
}
