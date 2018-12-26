package cn.com.clm.error;

/**
 * describe: common error
 *
 * @author liming.cao
 */
public interface CommonError {

    int getErrorCode();

    String getErrorMsg();

    CommonError setErrorMsg(String errorMsg);

}
