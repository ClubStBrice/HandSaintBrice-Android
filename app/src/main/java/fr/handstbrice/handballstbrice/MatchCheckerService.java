package fr.handstbrice.handballstbrice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

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
