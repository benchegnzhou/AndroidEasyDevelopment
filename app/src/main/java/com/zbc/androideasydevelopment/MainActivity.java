package com.zbc.androideasydevelopment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.zbc.androideasydevelopment.base.BaseActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;

/**
 * Created by benchengzhou on 2019/7/5  15:27 .
 * 作者邮箱： mappstore@163.com
 * 功能描述： 测试首页
 * 类    名： MainActivity
 * 备    注：
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refreshLayout)
    com.scwang.smartrefresh.layout.SmartRefreshLayout refreshLayout;
    private BaseQuickAdapter<String, BaseViewHolder> baseQuickAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        Logger.d("第一次打印日志好激动");
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        baseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_test, null) {

            @Override
            protected void convert(BaseViewHolder helper, String item) {

            }
        };
        recyclerview.setAdapter(baseQuickAdapter);
        baseQuickAdapter.bindToRecyclerView(recyclerview);
        //设置 Header 为 Material风格
//        refreshLayout.setRefreshHeader(new ClassicsHeader(this).setAccentColorId(R.color.apptheme));
        //设置 Footer 为 球脉冲
//        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
    }

    @Override
    protected void initData() {
        ArrayList<String> list = new ArrayList<>();
        list.add("aa");
        list.add("aa");
        list.add("aa");
        list.add("aa");

        baseQuickAdapter.setNewData(list);
    }
}
