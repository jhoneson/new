package com.example.scxh.news;


import android.content.Context;
import android.content.Intent;
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
import com.example.scxh.news.httpLoadTxtUntils.ConnectionUtils;
import com.example.scxh.news.httpLoadTxtUntils.NewsDetailActivity;
import com.google.gson.Gson;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HotNewsFragment extends Fragment {
    private int ITEM_TYPE_ONE = 0;
    private int ITEM_TYPE_TWO = 1;
    int pageNo = 0; //页号 ，表示第几页,第一页从0开始
    int pageSize = 20; //页大小，显示每页多少条数据
    String news_type_id = "T1348647909107";  //新闻类型标识, 此处表示头条新闻
    private String httpUrl = "http://c.m.163.com/nc/article/headline/" + news_type_id + "/" + pageNo * pageSize + "-" + pageSize + ".html";
    private SliderLayout sliderLayout;
    private String postId;
    private List<First> list;
    private ListView mListView;
    private ListAdapter adapter;
    private ConnectionUtils connectionUtils;

    public static Fragment newInstance(){
        HotNewsFragment hotNewsFragment = new HotNewsFragment();
        return hotNewsFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hot_news, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView = (ListView) getView().findViewById(R.id.news_hot_list);
        connectionUtils=new ConnectionUtils(getContext());
        View v1 = LayoutInflater.from(getContext()).inflate(R.layout.item_diy_layout, null);
        sliderLayout = (SliderLayout) v1.findViewById(R.id.tests_slider_layout);
        getData();
        adapter=new ListAdapter(getContext());
        mListView.addHeaderView(v1);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                postId=list.get(position).getPostid();
                String textUrl="http://c.m.163.com/nc/article/"+postId +"/"+"full.html";
                Log.e("String textUrl",">>>>>"+textUrl);
                connectionUtils.asycnConnect(textUrl, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
                    @Override
                    public void execute(String content) {

                        Intent intent=new Intent(getContext(), NewsDetailActivity.class);
                        try {

                            JSONObject jsonObject=new JSONObject(content);
                            JSONObject object=jsonObject.getJSONObject(postId);
                            String body=object.getString("body");
                            Log.e("String body",">>>>>"+body);
                            intent.putExtra("CONTENT",body);

                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(sliderLayout != null){
            sliderLayout.stopAutoCycle();
            sliderLayout = null;
        }
    }

    public void getData() {
        connectionUtils.asycnConnect(httpUrl, ConnectionUtils.Method.GET, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                Log.e("hotFragment","httpUrl=="+httpUrl);
                getHeaderView(content);
                Gson gson=new Gson();
                News news=gson.fromJson(content,News.class);
                list=news.getT1348647909107();
                Log.e("list","hotnews=="+list.size());
                adapter.SetData(list);
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
                    int length = ads.length();
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

            class ListAdapter extends BaseAdapter {
                private List<First> list = new ArrayList<>();
                LayoutInflater layoutInflater;

                public ListAdapter(Context context) {
                    layoutInflater = LayoutInflater.from(context);
                }

                public void SetData(List<First> list) {
                    this.list = list;
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
                public int getViewTypeCount() {
                    return 2;

                }

                @Override
                public int getItemViewType(int position) {
                    First item = (First) getItem(position);
                    if (item.getImgextra() == null) {
                        return ITEM_TYPE_ONE;  //加载一张图片
                    } else {
                        return ITEM_TYPE_TWO;  //加载三张图片
                    }
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    int TYPE = getItemViewType(position);
                    if (TYPE == ITEM_TYPE_ONE) {
                        return OnegetView(position, convertView, parent);
                    } else {
                        return TwogetView(position, convertView, parent);
                    }
                }

                public View OnegetView(int position, View convertView, ViewGroup parent) {
                    ViewHolder viewHolder;
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.layout, null);
                        ImageView icon = (ImageView) convertView.findViewById(R.id.item_image);
                        TextView Title = (TextView) convertView.findViewById(R.id.item_title);
                        TextView Content = (TextView) convertView.findViewById(R.id.item_content);

                        viewHolder = new ViewHolder();
                        viewHolder.title = Title;
                        viewHolder.icon = icon;
                        viewHolder.content = Content;

                        convertView.setTag(viewHolder);
                    }
                    viewHolder = (ViewHolder) convertView.getTag();
                    First item = (First) getItem(position+1);

                    Glide.with(getContext()).load(item.getImgsrc()).into(viewHolder.icon);
                    viewHolder.title.setText(item.getTitle());
                    viewHolder.content.setText(item.getDigest());
                    return convertView;
                }

                public View TwogetView(int position, View convertView, ViewGroup parent) {
                    ViewHolders viewHolders;
                    if (convertView == null) {
                        convertView = layoutInflater.inflate(R.layout.item_three_pic_layout, null);
                        ImageView one = (ImageView) convertView.findViewById(R.id.pic1);
                        ImageView two = (ImageView) convertView.findViewById(R.id.pic2);
                        ImageView three = (ImageView) convertView.findViewById(R.id.pic3);

                        viewHolders = new ViewHolders();
                        viewHolders.ImgOne = one;
                        viewHolders.ImgTwo = two;
                        viewHolders.ImgThree = three;

                        convertView.setTag(viewHolders);
                    }
                    viewHolders = (ViewHolders) convertView.getTag();
                    First item = (First) getItem(position);
                    ArrayList<Imagextra> imagextras=item.getImgextra();
                    String one=imagextras.get(0).getImgsrc();
                    String two=imagextras.get(1).getImgsrc();

                    Glide.with(getContext()).load(item.getImgsrc()).into(viewHolders.ImgOne);
                    Glide.with(getContext()).load(one).into(viewHolders.ImgTwo);
                    Glide.with(getContext()).load(two).into(viewHolders.ImgThree);

                    return convertView;
                }

                class ViewHolder {
                    ImageView icon;
                    TextView title;
                    TextView content;
                }

                class ViewHolders {
                    ImageView ImgOne;
                    ImageView ImgTwo;
                    ImageView ImgThree;
                }
            }

    }

