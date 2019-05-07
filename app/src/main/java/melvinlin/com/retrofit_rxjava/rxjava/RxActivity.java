package melvinlin.com.retrofit_rxjava.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import melvinlin.com.retrofit_rxjava.R;
import melvinlin.com.retrofit_rxjava.normal.GankApi;
import melvinlin.com.retrofit_rxjava.normal.GetBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxActivity extends AppCompatActivity {

    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);

        //Step1 拿到 Retrofit 實例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                //引入Gson解析庫，就可以直接以實體的形式拿到返回值
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //Step2 拿到 Api 實例
        final GankApi gankApi = retrofit.create(GankApi.class);

        mTvResult = findViewById(R.id.tvResult);

        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable<GetBean> observable = gankApi.getDataByRxJava("Android", "10", "1");
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<GetBean>() {
                            @Override
                            public void accept(GetBean getBean) throws Exception {
                                mTvResult.setText("RxJava: " + getBean.getResults().get(1).getDesc());
                            }
                        });
            }
        });

        findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable<Object> observable = gankApi.postDataByRxJava("http://square.github.io/retrofit/",
                        "測試數據",
                        "Android Studio",
                        "Android",
                        "true");

                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<Object>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Object value) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }
        });
    }
}
