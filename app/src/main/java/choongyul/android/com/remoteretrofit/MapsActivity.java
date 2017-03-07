package choongyul.android.com.remoteretrofit;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import choongyul.android.com.remoteretrofit.domain.Data;
import choongyul.android.com.remoteretrofit.domain.Row;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Spinner spinner;
    String gooArray[];
    private GoogleMap mMap;
    private String selectedGoo = "강남구";
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gooArray = new String[]{
                "강남구","강동구","강북구","강서구","관악구",
                "광진구","구로구", "금천구", "노원구", "도봉구",
                "동대문구","동작구", "마포구", "서대문구", "서초구",
                "성동구","성북구","송파구", "양천구", "영등포구",
                "용산구","은평구", "종로구", "중구","중랑구"
        };
        spinner = (Spinner) findViewById(R.id.spinner);
        // 스피너 데이터를 adapter를 사용하여 등록한다.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gooArray);
        //                                                      ^ 컨텍스트      ^스피너에서 사용할 레이아웃    ,       ^ 배열데이터
        // 3.2 스피너에 아답터 등록
        spinner.setAdapter(adapter);
        // 3.3 스피너 리스너에 등록
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 어떤 position이 선택되었는지 표시하는 toast 메시지 출력
                mMap.clear();
                Toast.makeText(MapsActivity.this, gooArray[position] + "선택하셨습니다.", Toast.LENGTH_SHORT).show();
                selectedGoo = gooArray[position];
                Log.e("Main","선택된 구 = " + selectedGoo);

                setMap();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 아무것도 선택하지 않았을때 무언가 하고싶을때
            }
        });


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng seoul = new LatLng(37.5666696, 126.977942);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,10));

//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));



        setMap();

    }

    public void setMap(){
        // 1. 레트로핏을 생성하고
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088/") // 포트까지가 베이스url이다.
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // 2. 사용할 인터페이스를 설정한다.
        SeoulOpenService service = retrofit.create(SeoulOpenService.class);
        // 3. 데이터를 가져온다
        Call<Data> result = service.listRepos(selectedGoo);

        result.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                // 값이 정상적으로 리턴되었을 경우
                if(response.isSuccessful()) {
                    Log.e("onResponse","값이 정상적으로 리턴되었다.");

                    Data data = response.body(); // 원래 반환 값인 jsonString이 Data 클래스로 변환되어 리턴된다.

                    for (Row row : data.getSearchParkingInfo().getRow()) {
                        LatLng parking = new LatLng(convertDouble(row.getLAT()), convertDouble(row.getLNG()));
                        Double capacity = convertDouble(row.getCAPACITY());

                        MarkerOptions marker = new MarkerOptions();
                        marker.position(parking);
                        marker.title("주차공간 : " + capacity);
                        mMap.addMarker(marker).showInfoWindow();


                    }
                } else {
                    //정상적이지 않을 경우 message에 오류내용이 담겨 온다.
                    Log.e("onResponse","값이 비정상적으로 리턴되었다. = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });
    }

    private double convertDouble(String value) {
        double result = 0;
        try{
            result = Double.parseDouble(value);
        } catch ( Exception e) {
            Log.e("convertDouble","문제가있다");

        }

        return result;
    }
    private int convertInt(String value) {
        int result = 0;
        try{
            result = Integer.parseInt(value);
        } catch (Exception e) {
            Log.e("convertInt","문제가있다");

        }

        return result;
    }
}
