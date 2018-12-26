package cn.com.clm.response;

/**
 * describe: common return data
 *
 * @author liming.cao
 */
public class CommonReturnData {

    private String status;
    private Object data;


    public static CommonReturnData create(Object result) {
        return CommonReturnData.create(result,"success");
    }

    public static CommonReturnData create(Object result, String status) {
        CommonReturnData returnData = new CommonReturnData();
        returnData.setStatus(status);
        returnData.setData(result);
        return returnData;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
