package com.haoxi.xgn.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.haoxi.xgn.R;
import com.haoxi.xgn.base.BaseLazyFragment;
import com.haoxi.xgn.bean.UpdateBean;
import com.haoxi.xgn.model.AboutActivity;
import com.haoxi.xgn.model.BandShoesActivity;
import com.haoxi.xgn.model.GoalActivity;
import com.haoxi.xgn.model.MyShoesActivity;
import com.haoxi.xgn.model.ProfileDetailActivity;
import com.haoxi.xgn.net.MethodConstant;
import com.haoxi.xgn.net.MethodParams;
import com.haoxi.xgn.update.IUpdateView;
import com.haoxi.xgn.update.VersionPresenter;
import com.haoxi.xgn.utils.ApiUtils;
import com.haoxi.xgn.utils.ContentKey;
import com.haoxi.xgn.widget.CustomDialog;

import java.io.File;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends BaseLazyFragment implements View.OnClickListener,IUpdateView {

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

    @BindView(R.id.powerBtn)
    ImageButton mPowerBtn;
    @BindView(R.id.loseBtn)
    ImageButton mLoseBtn;

    private boolean isOpenLose;
    private boolean isOpenPower;

    private VersionPresenter presenter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        presenter = new VersionPresenter();
        presenter.attachView(this);
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
        mLoseBtn.setOnClickListener(this);
        mPowerBtn.setOnClickListener(this);
        isOpenPower = SPUtils.getInstance().getBoolean("isOpenPower",false);
        isOpenLose = SPUtils.getInstance().getBoolean("isOpenLose",false);
        if (isOpenLose) {
            mLoseBtn.setImageResource(R.mipmap.icon_open);
        }else {
            mLoseBtn.setImageResource(R.mipmap.icon_closed);
        }
        if (isOpenPower) {
            mPowerBtn.setImageResource(R.mipmap.icon_open);
        }else {
            mPowerBtn.setImageResource(R.mipmap.icon_closed);
        }
    }

    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        //填充各控件的数据
        Log.e("jiazai","预加载----ProfileFragment-------1");
        //mHeaderIv.setImageResource(R.mipmap.run1);
        SPUtils.getInstance().put(ContentKey.MAIN_PAGE,0);
        mNameTv.setText(SPUtils.getInstance().getString(ContentKey.USER_NICKNAME));
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
                intent = new Intent(getActivity(), BandShoesActivity.class);
                break;
            case R.id.about_xgn:
                intent = new Intent(getActivity(), AboutActivity.class);
                break;
            case R.id.profile_upgrade:
                presenter.updateVar(getParaMap());
                break;
            case R.id.setting:
                break;
            case R.id.profile_civ:
                intent = new Intent(getActivity(), ProfileDetailActivity.class);
                break;
            case R.id.powerBtn:
                isOpenPower = SPUtils.getInstance().getBoolean("isOpenPower",false);
                if (isOpenPower) {
                    mPowerBtn.setImageResource(R.mipmap.icon_closed);
                    SPUtils.getInstance().put("isOpenPower",false);
                }else {
                    mPowerBtn.setImageResource(R.mipmap.icon_open);
                    SPUtils.getInstance().put("isOpenPower",true);
                }
                break;
            case R.id.loseBtn:
                isOpenLose = SPUtils.getInstance().getBoolean("isOpenLose",false);
                if (isOpenLose) {
                    mLoseBtn.setImageResource(R.mipmap.icon_closed);
                    SPUtils.getInstance().put("isOpenLose",false);
                }else {
                    mLoseBtn.setImageResource(R.mipmap.icon_open);
                    SPUtils.getInstance().put("isOpenLose",true);
                }
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    public String getMethod() {
        return MethodConstant.GET_CURRENT_VERSION;
    }

    @Override
    protected Map<String, String> getParaMap() {
        Map<String,String> map = super.getParaMap();
        map.put(MethodParams.PARAMS_USERID,mUserObjId);
        map.put(MethodParams.PARAMS_TOKEN,mToken);
        map.put(MethodParams.PARAMS_OS,"android");
        return map;
    }

    @Override
    public void toJudgeVer(UpdateBean updateBean) {

        Log.e("fiiiiii",updateBean.getData().getAndroid_info());
        Log.e("fiiiiii",updateBean.getData().getAndroid_url());
        Log.e("fiiiiii",updateBean.getData().getAndroid_version());

        //2 查看是否有最新版本，
        int version_app = versionConvert(ApiUtils.getVersionName(getActivity()));
        int version_air = versionConvert(updateBean.getData().getAndroid_version());

        if (version_app >= version_air){
            //一致，没有最新版本
            ApiUtils.showToast(getActivity(),"当前应用已经是最新版本！");
        }else {
            //不一致，有最新版本
            //弹出对话框，提醒用户更新版本
            StringBuilder sb = new StringBuilder();
            sb.append("有新版本,是否更新");
            showUpdateDialog(updateBean.getData().getAndroid_version(), updateBean.getData().getAndroid_url(), sb.toString());
        }
    }
    public static int versionConvert(String source) {
        int sum = 0;
        char[] array = source.toCharArray();
        for (char anArray : array) {
            if ((anArray >= '0') && (anArray <= '9')) {
                sum = sum * 10 + (anArray - '0');
            }
        }
        return sum;
    }
    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);//设置对话框不能消失
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void setProgressMax(int max) {
        progressDialog.setMax(max);
    }

    @Override
    public void setCuProgress(int progress) {
        progressDialog.setProgress(progress);
    }
    private void showUpdateDialog(String verCode, final String apkUrl, String desc) {

        final CustomDialog dialog = new CustomDialog(getActivity(),"发现新版本","发现新版本:v"+ verCode+"\n是否立即升级？","立即升级","稍后再说");
        dialog.setClickListenerInterface(new CustomDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                //升级操作，下载apk
//                presenter.downloadApk(apkUrl);
                Log.e("update","下载apk");
                dialog.dismiss();
            }

            @Override
            public void doCancel() {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void installApk(String path, int install) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(path)),"application/vnd.android.package-archive");
        startActivityForResult(intent,install);
    }
}
