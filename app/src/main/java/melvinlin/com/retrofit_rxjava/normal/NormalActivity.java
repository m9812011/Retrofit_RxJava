package melvinlin.com.retrofit_rxjava.normal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import melvinlin.com.retrofit_rxjava.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NormalActivity extends AppCompatActivity {

    private static final String TAG = "tag";
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
                .build();

        //Step2 拿到 Api 實例
        final GankApi gankApi = retrofit.create(GankApi.class);

        mTvResult = findViewById(R.id.tvResult);
        findViewById(R.id.btnGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Step3 通過 Api，拿到 Call
                Call<GetBean> android = gankApi.getData("Android", "10", "1");

                //Step4 請求網路
                android.enqueue(new Callback<GetBean>() {
                    @Override
                    public void onResponse(Call<GetBean> call, Response<GetBean> response) {
                        Toast.makeText(NormalActivity.this, "請求成功" , Toast.LENGTH_SHORT).show();
                        GetBean getBean = response.body();
                        Log.d(TAG, "onResponse() called with: call = [" + getBean + "]");
                        mTvResult.setText(getBean.getResults().get(0).getDesc());
                    }

                    @Override
                    public void onFailure(Call<GetBean> call, Throwable t) {
                        Toast.makeText(NormalActivity.this, "請求失敗：" + t , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *
                 * https://gank.io/api/add2gank 方式: POST
                 *
                 * 字段	描述	备注
                 * url	想要提交的网页地址
                 * desc	对干货内容的描述	单独的文字描述
                 * who	提交者 ID	干货提交者的网络 ID
                 * type	干货类型	可选参数: Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
                 * debug	当前提交为测试数据	如果想要测试数据是否合法, 请设置 debug 为 true! 可选参数: true | false
                 */
                //Step3 通過 Api，拿到 Call
                Call<ResponseBody> postData = gankApi.postData("http://square.github.io/retrofit/",
                        "測試數據",
                        "Android Studio",
                        "Android",
                        "true");

                //Step4 請求網路
                postData.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(NormalActivity.this, "請求成功" , Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(NormalActivity.this, "請求失敗：" + t , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        
        
        findViewById(R.id.btnUrl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Step3 通過 Api，拿到 Call
                final Call<GetBean> android = gankApi.getDataWithUrl("http://gank.io/api/data/Android/10/1");

                //Step4 請求網路
                android.enqueue(new Callback<GetBean>() {
                    @Override
                    public void onResponse(Call<GetBean> call, Response<GetBean> response) {
                        Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                        mTvResult.setText(response.body().getResults().get(0).getDesc());
                    }

                    @Override
                    public void onFailure(Call<GetBean> call, Throwable t) {

                    }
                });
            }
        });
        
    }
}
