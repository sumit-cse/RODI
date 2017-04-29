package com.tnjdevelopers.rodi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChooseImage extends AppCompatActivity {

    public static int choice=0;
    TextView username=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_image);
        String usnm="";  /*MainActivity.name;*/
        usnm.toLowerCase();
        username=(TextView)findViewById(R.id.textUserName);
        username.setText("hello "+usnm+" !");

    }
    public void chooseGallery(View view)
    {
        choice=1;
        Intent intent=new Intent(this,ImageCrop.class);
        startActivity(intent);
    }
    public void chooseCamera(View view)
    {
        choice=2;
        Intent intent=new Intent(this,ImageCrop.class);
        startActivity(intent);
    }
}
