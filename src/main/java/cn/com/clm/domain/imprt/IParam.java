package cn.com.clm.domain.imprt;

import java.util.List;

/**
 * describe: common item
 *
 * @author liming.cao
 */
public interface IParam {

    Integer getCellCount();

    List<String> getFieldNames();

    List<String> getDateFields();

    List<String> getTimeFields();

}
