package cn.com.clm.service;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * describe: interface
 *
 * @author liming.cao
 */
public interface ExportService {

    /**
     * get workbook data
     *
     * @return  workbook
     */
    Workbook getData();

}
