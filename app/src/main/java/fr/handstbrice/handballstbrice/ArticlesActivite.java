package fr.handstbrice.handballstbrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.handstbrice.handballstbrice.model.Article;
import fr.handstbrice.handballstbrice.rss.FluxRSS;

public class ArticlesActivite extends AppCompatActivity {


    private List<Article> listArticles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_activite);
        update();
    }

    private void update() {

            listArticles = FluxRSS.scanArticles(this);
            LinearLayout ll = findViewById(R.id.layoutArticles);
            for(final Article a : listArticles) {
                Log.i("articles", a.getTitre());
                View ligne= LayoutInflater.from(this).inflate(R.layout.article_item,ll,false );

                TextView titre_article=(TextView)ligne.findViewById(R.id.titre_article);
                titre_article.setText(a.getTitre());

                ImageView image_article=(ImageView)ligne.findViewById(R.id.image_article);
                new DownloadImageTask(image_article)
                        .execute(a.getImgUrl().toString());

                TextView text_article=(TextView)ligne.findViewById(R.id.text_article);
                text_article.setText(a.getTexte());

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(a.getUrl());
                        startActivity(i);
                    }
                };

                text_article.setOnClickListener(listener);
                image_article.setOnClickListener(listener);
                titre_article.setOnClickListener(listener);
                ll.addView(ligne);
            }


    }
}
