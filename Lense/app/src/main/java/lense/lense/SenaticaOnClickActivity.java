package lense.lense;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

public class SenaticaOnClickActivity extends AppCompatActivity {

    private String letter;
    private LinearLayout contentSenatica;
    private LinearLayout contentSign;
    private LinearLayout contentDescription1;
    private LinearLayout contentDescription2;
    private ImageView translateImage;
    private TextView wordT;
    private TextView category;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senatica_on_click);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        letter = getIntent().getStringExtra("letter");

        contentSenatica = (LinearLayout) findViewById(R.id.content_senatica);
        contentSign = (LinearLayout) findViewById(R.id.content_sign);
        contentDescription1 = (LinearLayout) findViewById(R.id.content_description1);
        contentDescription2 = (LinearLayout) findViewById(R.id.content_description2);
        translateImage = (ImageView) findViewById(R.id.translateImageView);
        wordT = (TextView) findViewById(R.id.word_text);
        category = (TextView) findViewById(R.id.category_text);

        AddSign("Hola","Saludo","http://i.stack.imgur.com/e8nZC.gif");

    }

    public void AddSign(String word,String cat,String url)
    {
        LinearLayout signContent = new LinearLayout(getApplicationContext());
        LinearLayout desc1Content = new LinearLayout(getApplicationContext());
        LinearLayout desc2Content = new LinearLayout(getApplicationContext());
        TextView wordText = new TextView(getApplicationContext());
        TextView categoryText = new TextView(getApplicationContext());
        ImageView imageView = new ImageView(getApplicationContext());

        signContent.setLayoutParams(contentSign.getLayoutParams());
        desc1Content.setLayoutParams(contentDescription1.getLayoutParams());
        desc2Content.setLayoutParams(contentDescription2.getLayoutParams());
        wordText.setLayoutParams(wordT.getLayoutParams());
        categoryText.setLayoutParams(category.getLayoutParams());
        imageView.setLayoutParams(translateImage.getLayoutParams());

        signContent.setOrientation(LinearLayout.VERTICAL);
        desc1Content.setOrientation(LinearLayout.VERTICAL);
        desc2Content.setOrientation(LinearLayout.VERTICAL);

        signContent.setBackgroundColor(getResources().getColor(R.color.new_color_buttom));
        desc1Content.setBackgroundColor(getResources().getColor(R.color.new_color_back));
        wordText.setTextColor(wordT.getTextColors());
        categoryText.setTextColor(category.getTextColors());

        wordText.setTextSize(19);
        categoryText.setTextSize(19);

        wordText.setText(word);
        categoryText.setText(cat);
        Ion.with(imageView).load(url);

        signContent.addView(imageView);

        desc2Content.addView(wordText);
        desc2Content.addView(categoryText);
        desc1Content.addView(desc2Content);

        contentSenatica.addView(signContent);
        contentSenatica.addView(desc1Content);
    }

}
