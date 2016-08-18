package com.example.scxh.news;

import android.os.Bundle;

import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity {
    private SlidingMenu slidingMenu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame_layout,NewsFragment.newInstance())
                .commit();

        setBehindContentView(R.layout.sliding_menu_layout);                //工具包里的布局，不可更改
        getSupportFragmentManager().beginTransaction()
                .add(R.id.sliding_menu_layout,MyFragment.newInstance())    //工具包里的布局的ID，不可更改
                .commit();
        slidingMenu=getSlidingMenu();
        slidingMenu.setSlidingEnabled(true);                              //设置可以划出
        slidingMenu.setBehindOffsetRes(R.dimen.activity_margin);          //可以划开的最大角度
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);      //从边上开始划
        slidingMenu.setMode(SlidingMenu.LEFT);                            //从左侧划出
    }
}
