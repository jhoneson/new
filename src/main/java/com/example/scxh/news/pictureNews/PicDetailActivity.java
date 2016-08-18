package com.example.scxh.news.pictureNews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scxh.news.R;

import java.util.ArrayList;

public class PicDetailActivity extends AppCompatActivity {
    private TextView title,content,toolbar;
    private ImageView imageView;
    private ArrayList<PicHelper> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_detail);
        toolbar= (TextView) findViewById(R.id.mian_text);
        title= (TextView) findViewById(R.id.picture_titlt);
        content= (TextView) findViewById(R.id.picture_content);
        imageView= (ImageView) findViewById(R.id.picture_img);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        Log.e("link>>>>>>>+++++++++","=="+bundle.getString("link"));
        Log.e("title>>>>>>>+++++++++","=="+bundle.getString("lead"));
        Glide.with(this).load(bundle.getString("link")).override(400,200).into(imageView);
        title.setText(bundle.getString("lead"));
        content.setText(Html.fromHtml(bundle.getString("content")));
    }
}
