package cn.com.clm.error;

/**
 * describe: error code
 *
 * @author liming.cao
 */
public enum EnumError implements CommonError {

    //通用错误类型
    PARAMETER_ERROR(30001,"参数不合法"),
    UNKNOWN_ERROR(20001,"未知错误"),

    //1开头：用户错误码
    USER_NOT_EXIST(10001,"用户不存在")
    ;

    private int errorCode;
    private String errorMsg;

    private EnumError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
