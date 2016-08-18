package com.example.scxh.news.httpLoadTxtUntils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scxh.news.R;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView textView;
    private ScrollView scrollView;
    private ImageView imageView,icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        icon= (ImageView) findViewById(R.id.back_btn);
        textView= (TextView) findViewById(R.id.detail_txt);
        imageView= (ImageView) findViewById(R.id.detail_img);
        scrollView= (ScrollView) findViewById(R.id.item_txt_scroll);
        Intent intent=getIntent();
        String content=intent.getStringExtra("CONTENT");
        String src=intent.getStringExtra("imgSrc");
        Glide.with(this).load(src).into(imageView);
        Log.e("comtent>>>>>>","=="+content);
        textView.setText(Html.fromHtml(content));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
