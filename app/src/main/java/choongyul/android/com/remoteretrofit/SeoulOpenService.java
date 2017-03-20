package choongyul.android.com.remoteretrofit;

import choongyul.android.com.remoteretrofit.domain.Data;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by myPC on 2017-03-07.
 */
// 레트로핏 예제에서 사용하는 GitHubService 를 이름만 변경하고 따로 세팅.
public interface SeoulOpenService {

    // 풀 어드레스 http://openapi.seoul.go.kr:8088/566d677961726f6331397471525a50/json/SearchParkingInfo/1/10/
    // 중에서 포트 이하 부분을 get 이하에 쓴다.
    @GET("566d677961726f6331397471525a50/json/SearchParkingInfo/1/10/{gu}")
    Call<Data> listRepos(@Path("gu") String user); // path는 리스트 리포함수를 통해서 데이터를 가져오게되는데 거기 들어오는 갑승ㄹ path를 통해 url을 세팅한다.
                                // {gu} 부분을 설정하는 String user를 가져온다.
}
