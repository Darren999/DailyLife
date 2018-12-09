package view.darren.com.dailylife.ui.me.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.pedaily.yc.ycdialoglib.customToast.ToastUtil;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.mvp.BaseFragment;
import view.darren.com.dailylife.ui.guide.view.activity.MainActivity;
import view.darren.com.dailylife.ui.me.contract.MeFragmentContract;
import view.darren.com.dailylife.ui.me.presenter.MeFragmentPresenter;
import view.darren.com.dailylife.utils.AppToolUtils;

public class MeFragment extends BaseFragment<MeFragmentPresenter> implements
        View.OnClickListener , MeFragmentContract.View{

    @Bind(R.id.rl_me_timer)
    RelativeLayout rlMeTimer;
    @Bind(R.id.rl_me_qone)
    RelativeLayout rlMeQone;
    @Bind(R.id.rl_me_project)
    RelativeLayout rlMeProject;
    @Bind(R.id.rl_me_collect)
    RelativeLayout rlMeCollect;
    @Bind(R.id.rl_me_question)
    RelativeLayout rlMeQuestion;
    @Bind(R.id.rl_me_setting)
    RelativeLayout rlMeSetting;
    @Bind(R.id.rl_me_feed_back)
    RelativeLayout rlMeFeedBack;
    @Bind(R.id.tv_me_phone_number)
    TextView tvMePhoneNumber;
    @Bind(R.id.rl_me_phone)
    LinearLayout rlMePhone;
    @Bind(R.id.iv_person_image)
    ImageView ivPersonImage;
    @Bind(R.id.tv_person_name)
    TextView tvPersonName;
    @Bind(R.id.ll_person)
    LinearLayout llPerson;

    private MainActivity activity;
    private MeFragmentContract.Presenter presenter = new MeFragmentPresenter(this);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(activity != null){
            activity = null;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            default:
                ToastUtil.showToast(activity,"id:"+id);
                break;
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        setRipperView();
    }

    @Override
    public void initListener() {
        rlMeCollect.setOnClickListener(this);
        rlMeFeedBack.setOnClickListener(this);
        rlMePhone.setOnClickListener(this);
        rlMeCollect.setOnClickListener(this);
        rlMeQone.setOnClickListener(this);
        rlMeProject.setOnClickListener(this);
        rlMeQuestion.setOnClickListener(this);
        rlMeSetting.setOnClickListener(this);
        rlMeTimer.setOnClickListener(this);
    }

    @Override
    public void initData() {
        presenter.getRedHotMessageData();
    }

    private void setRipperView() {
        AppToolUtils.setRipper(rlMeTimer);
        AppToolUtils.setRipper(rlMeQone);
        AppToolUtils.setRipper(rlMeProject);
        AppToolUtils.setRipper(rlMeCollect);
        AppToolUtils.setRipper(rlMeQuestion);
        AppToolUtils.setRipper(rlMeSetting);
        AppToolUtils.setRipper(rlMeFeedBack);
        AppToolUtils.setRipper(rlMePhone);
    }
}