package choongyul.android.com.remoteretrofit.domain;

/**
 * Created by myPC on 2017-03-07.
 */

// 가져온 json 데이터의 최상단
public class Data {
    private SearchParkingInfo SearchParkingInfo;

    public SearchParkingInfo getSearchParkingInfo ()
    {
        return SearchParkingInfo;
    }

    public void setSearchParkingInfo (SearchParkingInfo SearchParkingInfo)
    {
        this.SearchParkingInfo = SearchParkingInfo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchParkingInfo = "+SearchParkingInfo+"]";
    }
}
