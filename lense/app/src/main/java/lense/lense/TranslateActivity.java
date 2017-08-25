package lense.lense;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import com.koushikdutta.ion.Ion;

import lense.lense.server_conection.ServerData;

public class TranslateActivity extends AppCompatActivity {

    ImageView imageView;
    EditText translateText;
    ServerData serverData;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_translate);
        serverData = new ServerData();

        Typeface walkwayBold = Typeface.createFromAsset(getAssets(), "WalkwayBold.ttf");
        translateText = (EditText) findViewById(R.id.translate_text);
        translateText.setTypeface(walkwayBold);


        Button translateButton = (Button) findViewById(R.id.translateButton);
        translateButton.setTypeface(walkwayBold);
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImageGif(translateText.getText().toString());
            }
        });



        //Ion.with(imageView).load("https://img.gifamerica.com/0f48eb302f829e323d0311c9e123c234_publicada-por-deah-bastos-em-no-se-gif-animado_350-350.gif");

    }

    public void setImageGif(String text)
    {
        imageView = (ImageView) findViewById(R.id.translateImageView);


        if(text.equals("a"))
        {
            Ion.with(imageView).load("https://img.washingtonpost.com/express/wp-content/uploads/2016/04/Clinton.gif");
        }
        if(text.equals("b"))
        {
            Ion.with(imageView).load("http://lensechile.cl/videos/VID-20170304-WA0003.mp4");
        }
    }
}
