package fr.handstbrice.handballstbrice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.handstbrice.handballstbrice.model.Partenaire;

public class PartenaireDetailsActivite extends AppCompatActivity {
    private Partenaire partenaire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partenaire_details);
        partenaire=(Partenaire) getIntent().getExtras().get("partenaire");

        TextView tvNom=(TextView)findViewById(R.id.NomPartenaire);
        tvNom.setText(partenaire.getNom());

        ImageView logoPartenaire = (ImageView)findViewById(R.id.logoPartenaire);
        logoPartenaire.setImageURI(partenaire.getUrlImg());

        TextView bonusPartenaire = (TextView)findViewById(R.id.bonusPartenaire);
        bonusPartenaire.setText(partenaire.getOffre());

        Button boutonLienPartenairesSite = (Button)findViewById(R.id.boutonLienPartenairesSite);
        boutonLienPartenairesSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(partenaire.getUrl());
                startActivity(i);
            }
        });
    }
}
