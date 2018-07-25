package fr.handstbrice.handballstbrice;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.handstbrice.handballstbrice.model.Partenaire;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class PartenairesActivite extends AppCompatActivity {

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partenaires_activite);
        scrollView = (ScrollView)findViewById(R.id.scrollViewPartenaires);
        update();
    }

    private void update() {
        scrollView.removeAllViews();

            List<Partenaire> l = FluxRSS.scanListPartenaires(this);
            View v=null;
            for (final Partenaire p : l ){
                int imageViewId;
                if (v == null ) {
                    v=LayoutInflater.from(this).inflate(R.layout.partenaire_item, scrollView);
                    imageViewId = R.id.imageViewL;
                } else {
                    imageViewId = R.id.imageViewR;
                }
                ImageView iv = (ImageView) v.findViewById(imageViewId);
                iv.setImageURI(p.getUrlImg());
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(PartenairesActivite.this, PartenaireDetailsActivite.class);
                        i.putExtra("partenaire", p);
                    }
                });
            }


    }
}
