package com.haoxi.xgn.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseLazyFragment;
import com.haoxi.xgn.model.AboutActivity;
import com.haoxi.xgn.model.GoalActivity;
import com.haoxi.xgn.model.ProfileDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseLazyFragment implements View.OnClickListener {

    // 标志位，标志已经初始化完成。
    private boolean isPrepared;

    @BindView(R.id.profile_civ)
    ImageView mHeaderIv;
    @BindView(R.id.profile_name)
    TextView mNameTv;

    @BindView(R.id.profile_shop)
    TextView mShopTv;
    @BindView(R.id.profile_goal)
    TextView mGoalTv;
    @BindView(R.id.profile_shoes)
    TextView mShoesTv;
    @BindView(R.id.about_xgn)
    TextView mAboutTv;
    @BindView(R.id.profile_upgrade)
    TextView mUpgradeTv;
    @BindView(R.id.setting)
    ImageView mSettingIv;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        //XXX初始化view的各控件
        isPrepared = true;
        lazyLoad();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mShopTv.setOnClickListener(this);
        mGoalTv.setOnClickListener(this);
        mShoesTv.setOnClickListener(this);
        mAboutTv.setOnClickListener(this);
        mUpgradeTv.setOnClickListener(this);
        mSettingIv.setOnClickListener(this);
        mHeaderIv.setOnClickListener(this);
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        Log.e("jiazai","预加载----ProfileFragment-------1");
        //mHeaderIv.setImageResource(R.mipmap.run1);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()){
            case R.id.profile_shop:
                intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://xgnxl.m.tmall.com/?spm=a1z10.3-b-s.1997427721.d4918089.42cfcb65YmFdvn");
                intent.setData(content_url);
                break;
            case R.id.profile_goal:
                intent = new Intent(getActivity(), GoalActivity.class);
                break;
            case R.id.profile_shoes:

                break;
            case R.id.about_xgn:
                intent = new Intent(getActivity(), AboutActivity.class);
                break;
            case R.id.profile_upgrade:

                break;
            case R.id.setting:

                break;
            case R.id.profile_civ:
                intent = new Intent(getActivity(), ProfileDetailActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
