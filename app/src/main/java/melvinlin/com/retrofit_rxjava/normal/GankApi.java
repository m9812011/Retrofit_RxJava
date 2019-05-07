package melvinlin.com.retrofit_rxjava.normal;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * The interface Gank api.
 */
public interface GankApi {
    /**
     *
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     *
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    @GET("api/data/{category}/{size}/{page}")
    Call<GetBean> getData(@Path("category") String category,
                               @Path("size") String size,
                               @Path("page") String page);


    @GET
    Call<GetBean> getDataWithUrl(@Url String url);

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
    @FormUrlEncoded
    @POST("api/add2gank")
    Call<ResponseBody> postData(@Field("url") String url,
                                @Field("desc") String desc,
                                @Field("who") String who,
                                @Field("type") String type,
                                @Field("debug") String debug);


    /**
     *
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     *
     * RxJava的形式返回
     *
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */
    @GET("api/data/{category}/{size}/{page}")
    Observable<GetBean> getDataByRxJava(@Path("category") String category,
                                    @Path("size") String size,
                                    @Path("page") String page);

    @FormUrlEncoded
    @POST("api/add2gank")
    Observable<Object> postDataByRxJava(@Field("url") String url,
                                @Field("desc") String desc,
                                @Field("who") String who,
                                @Field("type") String type,
                                @Field("debug") String debug);
}
