package com.example.scxh.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private List<String> list_title;                           //tab名称列表
    private List<Fragment> list_fragment;                      //定义要装fragment的列表
    private HotNewsFragment hotNewsFragment;
    private EntertainmentFragment entertainmentFragment;
    private EconomyFragment economyFragment;
    private AthleteFragment athleteFragment;
    private ScienceFragment scienceFragment;
    private TabAdapter adapter;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_news_layout, container, false);
        initial(v);
        return v;
    }
    public void initial(View view){
        mViewPager= (ViewPager) view.findViewById(R.id.view_pager);
        tabLayout= (TabLayout) view.findViewById(R.id.tab_layout);

        hotNewsFragment= (HotNewsFragment) HotNewsFragment.newInstance();
        athleteFragment=new AthleteFragment();
        scienceFragment=new ScienceFragment();
        economyFragment=new EconomyFragment();
        entertainmentFragment=new EntertainmentFragment();

        list_fragment=new ArrayList<>();
        list_fragment.add(hotNewsFragment);
        list_fragment.add(economyFragment);
        list_fragment.add(athleteFragment);
        list_fragment.add(scienceFragment);
        list_fragment.add(entertainmentFragment);
        Log.e(">>>","list_fragment=="+list_fragment.size());

        list_title=new ArrayList<>();
        list_title.add("热点");
        list_title.add("财经");
        list_title.add("体育");
        list_title.add("科技");
        list_title.add("娱乐");
        Log.e(">>>","list_title=="+list_title.size());
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(4)));

        adapter=new TabAdapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
    }
    class TabAdapter extends FragmentStatePagerAdapter{
        private List<Fragment> list_fragment;                  //fragment列表
        private List<String> list_Title;                       //tab名的列表

        public TabAdapter(FragmentManager fm,List<Fragment> list_fragment,List<String> list_Title) {
            super(fm);
            this.list_fragment=list_fragment;
            this.list_Title=list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title.get(position);
        }
    }
}

