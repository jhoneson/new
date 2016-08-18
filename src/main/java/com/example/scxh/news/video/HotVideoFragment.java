package com.example.scxh.news.video;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.scxh.news.R;
import com.example.scxh.news.httpLoadTxtUntils.ConnectionUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotVideoFragment extends Fragment {
    private ListView listView;
    private ConnectionUtils connectionUtils;
    private List<VideoHelper> list;
    private MyAdapter adapter;
    int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    int pageSize = 10; //页大小，显示每页多少条数据
    String video_type_id = "V9LG4B3A0";  //视频类型标识, 此处表示热点视频
    private String httpUrl="http://c.3g.163.com/nc/video/list/"+ video_type_id + "/n/" +pageNo*pageSize+ "-"  +pageSize+".html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hot_video, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView= (ListView) getView().findViewById(R.id.video_hot_list);
        connectionUtils=new ConnectionUtils(getContext());
        list=new ArrayList<>();
        adapter=new MyAdapter(getContext());
        listView.setAdapter(adapter);
        setData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mp4Url=list.get(position).getMp4_url();
                Log.e("mp4Url","====="+mp4Url);
                PlayMp4(mp4Url);
            }
        });
    }
    public void PlayMp4(String Mp4url){
        Uri uri=Uri.parse(Mp4url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "video/mp4");
        startActivity(intent);
    }

    public void setData(){
        connectionUtils.asycnConnect(httpUrl, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                Gson gson=new Gson();
                video video=gson.fromJson(content, video.class);
                list=video.getV9LG4B3A0();
                Log.e("list",">>>>>>"+list.size());
                adapter.setList(list);
            }
        });
    }
    class MyAdapter extends BaseAdapter{
        private List<VideoHelper> list=new ArrayList<>();
        LayoutInflater layoutInflater;
        public MyAdapter(Context context){
            layoutInflater=LayoutInflater.from(context);
        }
        public void setList(List<VideoHelper> list){
            this.list=list;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VideoViewHolder videoViewHolder;
            if(convertView==null){
                convertView=layoutInflater.inflate(R.layout.video_item_layout,null);
                TextView title= (TextView) convertView.findViewById(R.id.video_item_dsc);
                ImageView imageView= (ImageView) convertView.findViewById(R.id.video_item_img);
                videoViewHolder=new VideoViewHolder();
                videoViewHolder.textView=title;
                videoViewHolder.imageView=imageView;

                convertView.setTag(videoViewHolder);
            }
            videoViewHolder= (VideoViewHolder) convertView.getTag();
            VideoHelper item= (VideoHelper) getItem(position);
            Log.e("tag","item.getDescription()"+item.getTopicDesc());
            videoViewHolder.textView.setText(item.getTopicDesc());
            Log.e("tag","item.getTopicImg()"+item.getTopicImg());
            Glide.with(getContext()).load(item.getTopicImg()).override(300,200).into(videoViewHolder.imageView);
            return convertView;
        }

    }
}
