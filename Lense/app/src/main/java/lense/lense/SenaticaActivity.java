package lense.lense;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class SenaticaActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ImageView letter_a;
    private ImageView letter_b;
    private ImageView letter_c;
    private ImageView letter_d;
    private ImageView letter_e;
    private ImageView letter_f;
    private ImageView letter_g;
    private ImageView letter_h;
    private ImageView letter_i;
    private ImageView letter_j;
    private ImageView letter_k;
    private ImageView letter_l;
    private ImageView letter_m;
    private ImageView letter_n;
    private ImageView letter_nn;
    private ImageView letter_o;
    private ImageView letter_p;
    private ImageView letter_q;
    private ImageView letter_r;
    private ImageView letter_s;
    private ImageView letter_t;
    private ImageView letter_u;
    private ImageView letter_v;
    private ImageView letter_w;
    private ImageView letter_x;
    private ImageView letter_y;
    private ImageView letter_z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senatica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        letter_a = (ImageView) findViewById(R.id.letter_a);
        letter_b = (ImageView) findViewById(R.id.letter_b);
        letter_c = (ImageView) findViewById(R.id.letter_c);
        letter_d = (ImageView) findViewById(R.id.letter_d);
        letter_e = (ImageView) findViewById(R.id.letter_e);
        letter_f = (ImageView) findViewById(R.id.letter_f);
        letter_g = (ImageView) findViewById(R.id.letter_g);
        letter_h = (ImageView) findViewById(R.id.letter_h);
        letter_i = (ImageView) findViewById(R.id.letter_i);
        letter_j = (ImageView) findViewById(R.id.letter_j);
        letter_k = (ImageView) findViewById(R.id.letter_k);
        letter_l = (ImageView) findViewById(R.id.letter_l);
        letter_m = (ImageView) findViewById(R.id.letter_m);
        letter_n = (ImageView) findViewById(R.id.letter_n);
        letter_nn = (ImageView) findViewById(R.id.letter_nn);
        letter_o = (ImageView) findViewById(R.id.letter_o);
        letter_p = (ImageView) findViewById(R.id.letter_p);
        letter_q = (ImageView) findViewById(R.id.letter_q);
        letter_r = (ImageView) findViewById(R.id.letter_r);
        letter_s = (ImageView) findViewById(R.id.letter_s);
        letter_t = (ImageView) findViewById(R.id.letter_t);
        letter_u = (ImageView) findViewById(R.id.letter_u);
        letter_v = (ImageView) findViewById(R.id.letter_v);
        letter_w = (ImageView) findViewById(R.id.letter_w);
        letter_x = (ImageView) findViewById(R.id.letter_x);
        letter_y = (ImageView) findViewById(R.id.letter_y);
        letter_z = (ImageView) findViewById(R.id.letter_z);

        letter_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("A");
            }
        });
        letter_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("B");
            }
        });
        letter_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("C");
            }
        });
        letter_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("D");
            }
        });
        letter_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("E");
            }
        });
        letter_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("F");
            }
        });
        letter_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("G");
            }
        });
        letter_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("H");
            }
        });
        letter_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("I");
            }
        });
        letter_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("J");
            }
        });
        letter_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("K");
            }
        });
        letter_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("L");
            }
        });
        letter_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("M");
            }
        });
        letter_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("N");
            }
        });
        letter_nn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("Ñ");
            }
        });
        letter_o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("O");
            }
        });
        letter_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("P");
            }
        });
        letter_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("Q");
            }
        });
        letter_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("R");
            }
        });
        letter_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("S");
            }
        });
        letter_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("T");
            }
        });
        letter_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("U");
            }
        });
        letter_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("V");
            }
        });
        letter_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("W");
            }
        });
        letter_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("X");
            }
        });
        letter_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("Y");
            }
        });
        letter_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartActivity("Z");
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void StartActivity(String letter)
    {
        Intent i = new Intent(SenaticaActivity.this,SenaticaOnClickActivity.class);
        i.putExtra("letter",letter);
        startActivity(i);
    }

}
