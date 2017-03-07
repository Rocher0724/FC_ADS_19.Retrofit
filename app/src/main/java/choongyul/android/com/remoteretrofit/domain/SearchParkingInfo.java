package choongyul.android.com.remoteretrofit.domain;

import java.util.List;

/**
 * Created by myPC on 2017-03-07.
 */

// 가져온 data의 둘째단
public class SearchParkingInfo {
    private RESULT RESULT;

    private String list_total_count;

    // array를 못받을 수도 있음. 나중에 확인하기
    private List<Row> row;

    public RESULT getRESULT ()
    {
        return RESULT;
    }

    public void setRESULT (RESULT RESULT)
    {
        this.RESULT = RESULT;
    }

    public String getList_total_count ()
    {
        return list_total_count;
    }

    public void setList_total_count (String list_total_count)
    {
        this.list_total_count = list_total_count;
    }

    public List<Row> getRow ()
    {
        return row;
    }

    public void setRow (List<Row> row)
    {
        this.row = row;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [RESULT = "+RESULT+", list_total_count = "+list_total_count+", row = "+row+"]";
    }
}
