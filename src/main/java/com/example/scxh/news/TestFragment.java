package com.example.scxh.news;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scxh.news.httpLoadTxtUntils.ConnectionUtils;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {
    private SliderLayout sliderLayout;
    private ListView listView;
    int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    int pageSize = 20; //页大小，显示每页多少条数据
    String news_type_id = "T1348647909107";  //新闻类型标识, 此处表示头条新闻
    private ConnectionUtils connectionUtils;
    private String[] array={"新闻1","新闻2","新闻3","新闻4","新闻5","新闻6","新闻7"};
    private String httpUrl = "http://c.m.163.com/nc/article/headline/" + news_type_id + "/" + pageNo * pageSize + "-" + pageSize + ".html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView= (ListView) getView().findViewById(R.id.imag_view);
        connectionUtils=new ConnectionUtils(getContext());
        View v1 = LayoutInflater.from(getContext()).inflate(R.layout.item_diy_layout, null);
        sliderLayout = (SliderLayout) v1.findViewById(R.id.tests_slider_layout);

        listView.addHeaderView(sliderLayout);
        ArrayAdapter adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,array);
        Log.e("adapter","=="+adapter);
        listView.setAdapter(adapter);
        getData();
    }

    public void getData(){
        connectionUtils.asycnConnect(httpUrl, ConnectionUtils.Method.POST, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                Log.e("content","=="+content);
                getHeaderView(content);
            }
        });
    }
    public void getHeaderView(String content) {
        JSONObject jsonObject;
        JSONArray ads;
        try {
            jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray(news_type_id);
            JSONObject Item = jsonArray.getJSONObject(0);
            ads = Item.getJSONArray("ads");
            Log.e("ads", "==" + ads);
            int length = ads.length();
            Log.e("length", "==" + length);
            for (int i = 0; i < length; i++) {
                JSONObject jsonObjectItem = jsonArray.getJSONObject(i);
                String imgsrc = jsonObjectItem.getString("imgsrc");
                String Title = jsonObjectItem.getString("title");
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView.description(Title)
                        .image(imgsrc)
                        .setScaleType(BaseSliderView.ScaleType.Fit);
                sliderLayout.addSlider(textSliderView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
