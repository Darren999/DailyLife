package view.darren.com.dailylife.ui.guide.presenter;

import android.app.Activity;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.subscriptions.CompositeSubscription;
import view.darren.com.dailylife.R;
import view.darren.com.dailylife.base.app.BaseApplication;
import view.darren.com.dailylife.db.cache.SelectFollow;
import view.darren.com.dailylife.db.cache.SelectUnFollow;
import view.darren.com.dailylife.model.SelectPoint;
import view.darren.com.dailylife.ui.guide.contract.SelectFollowContract;

import java.util.ArrayList;
import java.util.List;

public class SelectFollowPresenter implements SelectFollowContract.Presenter {

    private SelectFollowContract.View mView;
    private CompositeSubscription mSubscriptions;
    private Realm realm;
    private RealmResults<SelectUnFollow> selectUnFollows;
    private List<SelectPoint> list = new ArrayList<>();
    private int del;

    public SelectFollowPresenter(SelectFollowContract.View view){
        this.mView = view;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void addData(Activity activity) {
        String[] titles = activity.getResources().getStringArray(R.array.select_follow);
        for(int i = 0; i < titles.length; i++){
            SelectPoint selectPoint = new SelectPoint();
            selectPoint.setName(titles[i]);
            list.add(selectPoint);
        }

        mView.refreshData(list);
    }

    @Override
    public void addSelectToRealm(Integer[] selectedIndies) {
        initRealm();
        if(realm.where(SelectUnFollow.class).findAll() != null){
            selectUnFollows = realm.where(SelectUnFollow.class).findAll();
        }else{
            return;
        }

        for(int i = 0; i<selectedIndies.length; i++){
            realm.beginTransaction();
            SelectFollow selectFollow = realm.createObject(SelectFollow.class);
            selectFollow.setName(list.get(i).getName());
            realm.commitTransaction();

            for(int j = 0; j<selectUnFollows.size(); j++){
                if(selectUnFollows.get(i).getName().equals(list.get(selectedIndies[i]).getName())){
                    del = j;
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            selectUnFollows.deleteFromRealm(del);
                        }
                    });

                    j--;
                }
            }
        }
    }

    @Override
    public void subscribe() {
        initRealm();
        addUnSelectData();
    }


    private void initRealm() {
        if(null == realm){
            realm = BaseApplication.getInstance().getRealmHelper();
        }
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.unsubscribe();
    }

    private void addUnSelectData() {
        if(realm.where(SelectUnFollow.class).findAll() != null){
            selectUnFollows = realm.where(SelectUnFollow.class).findAll();
        }else{
            return;
        }

        for(int i = 0; i<list.size(); i++){
            realm.beginTransaction();
            SelectUnFollow unFollow = realm.createObject(SelectUnFollow.class);
            unFollow.setName(list.get(i).getName());
            realm.commitTransaction();
        }
    }

}
