package cn.com.clm.response;

import java.util.List;
import java.util.Map;

/**
 * describe: common return data
 *
 * @author liming.cao
 */
public class ReturnDate {
    private String msgCode;
    private String msg;
    private Object content;
    private Integer status;
    private Integer total;

    public ReturnDate() {
    }

    public ReturnDate(String msgCode, String msg, Object content, Integer status, Integer total) {
        this.msgCode = msgCode;
        this.msg = msg;
        this.content = content;
        this.status = status;
        this.total = total;
    }

    public ReturnDate(String msgCode, String msg, Integer status) {
        this.setTotal(0);
        this.msgCode = msgCode;
        this.msg = msg;
        this.status = status;
    }

    public ReturnDate(List<?> list){
        this("10000","OK",1);
        this.setContent(list);
        this.setTotal(null == list ? 0 : list.size());
    }

    public ReturnDate(Map<?, ?> map) {
        this("10000","OK",1);
        this.setContent(map);
        this.setTotal(null == map ? 0 : map.size());
    }

    public ReturnDate(Object content) {
        this("10000","OK",1);
        this.setTotal(null == content ? 0 : 1);
        this.content = content;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
