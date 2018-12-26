package cn.com.clm.util;

import cn.com.clm.constant.ExportConstant;
import cn.com.clm.domain.imprt.IParam;
import cn.com.clm.domain.imprt.PeopleItem;
import cn.com.clm.exception.CustomException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * describe: excel util
 *
 * @author liming.cao
 */
public class ExcelUtil {

    /**
     * export
     *
     * @param suffix        suffix
     * @param map           data
     * @param shiftTitles   sheet titles
     * @return              workbook
     */
    public static Workbook makeExcelFile(String suffix, Map<String, String> map, List<String> shiftTitles) {
        if (null == map) {
           throw new CustomException("msg.error.required_map_cannot_be_null");
        } else {
            if (null != shiftTitles && !shiftTitles.isEmpty()) {
                if (map.size() != shiftTitles.size()) {
                    throw new CustomException("msg.error.sheet_title_size_should_equal_to_data_size");
                }
            } else {
                shiftTitles = new ArrayList<>();
                for (int i = 1; i <= map.size(); i++) {
                    shiftTitles.add("sheet" + i);
                }
            }
            Workbook wb;
            if (ExportConstant.SUFFIX_2007.equals(suffix)) {
                wb = new SXSSFWorkbook();
            } else {
                wb = new HSSFWorkbook();
            }

            CellStyle headerCellStyle = wb.createCellStyle();
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
            Font headerFont = wb.createFont();
            headerFont.setBold(Boolean.TRUE);
            headerFont.setColor(IndexedColors.BLUE.getIndex());
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setBorderTop(BorderStyle.THIN);
            headerCellStyle.setBorderBottom(BorderStyle.THIN);
            headerCellStyle.setBorderLeft(BorderStyle.THIN);
            headerCellStyle.setBorderRight(BorderStyle.THIN);
            CellStyle rowCellStyle = wb.createCellStyle();
            rowCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            rowCellStyle.setAlignment(HorizontalAlignment.LEFT);
            rowCellStyle.setBorderTop(BorderStyle.THIN);
            rowCellStyle.setBorderRight(BorderStyle.THIN);
            rowCellStyle.setBorderBottom(BorderStyle.THIN);
            rowCellStyle.setBorderLeft(BorderStyle.THIN);

            int sheetTitleIndex = 0;
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Sheet sheet = wb.createSheet(shiftTitles.get(sheetTitleIndex++));
                sheet.setDefaultColumnWidth(17);

                JSONObject item = JSONObject.parseObject(entry.getValue());

                JSONArray headers = item.getJSONArray(ExportConstant.KEY_HEADERS);
                JSONArray fields = item.getJSONArray(ExportConstant.KEY_FIELDS);
                if (null == headers || headers.isEmpty() || null == fields || fields.isEmpty()) {
                    throw new CustomException("msg.error.required_headers_and_fields_cannot_be_null");
                } else if (headers.size() != fields.size()) {
                    throw new CustomException("msg.error.headers_size_should_equal_to_fields_size");
                }

                int rowIndex = 0;
                Row headerRow = sheet.createRow(rowIndex++);
                for (int i = 0; i < headers.size(); i++) {
                    Cell cell = headerRow.createCell(i, CellType.STRING);
                    cell.setCellStyle(headerCellStyle);
                    cell.setCellValue(headers.getString(i));
                }

                JSONArray rows = item.getJSONArray(ExportConstant.KEY_ROWS);
                if (null == rows) {
                    throw new CustomException("msg.error.required_rows_cannot_be_null");
                }

                for (Object object : rows) {
                    JSONObject row = (JSONObject)object;
                    Row dataRow = sheet.createRow(rowIndex++);
                    for (int i = 0; i < fields.size(); i++) {
                        Cell cell = dataRow.createCell(i, CellType.STRING);
                        cell.setCellStyle(rowCellStyle);
                        cell.setCellValue(StringUtils.isBlank(row.getString(fields.getString(i))) ?
                                null : row.getString(fields.getString(i)));
                    }
                }
            }
            return wb;
        }

    }

    /**
     * import
     *
     * @param file          excel file
     * @param param         param
     * @return              json
     */
    public static String handleExcelFile(MultipartFile file, IParam param) {
        int cellCount = param.getCellCount();
        List<String> fieldNames = param.getFieldNames();
        List<String> dateFields = param.getDateFields();
        List<String> timeFields = param.getTimeFields();
        valid(file, cellCount, fieldNames);
        Workbook workbook = getWorkbook(file);
        if (null == workbook) {
            throw new CustomException("msg.error.file_handle_fail");
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat(ExportConstant.DATE_PATTERN);
            SimpleDateFormat timeFormat = new SimpleDateFormat(ExportConstant.TIME_PATTERN);
            JSONArray jsonArray = new JSONArray();
            // 可扩展多sheet
            Sheet sheet = workbook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();

            Row titleRow = sheet.getRow(0);
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < cellCount; i++) {
                Cell cell = titleRow.getCell(i);
                cell.setCellType(CellType.STRING);
                titles.add(cell.getStringCellValue().trim());
            }

            for (int j = 1; j <= lastRowNum; j++) {
                JSONObject jo = new JSONObject();
                Row row = sheet.getRow(j);
                for (int i = 0; i < cellCount; i++) {
                    Cell cell = row.getCell(i);
                    String fieldName = fieldNames.get(i);
                    try {
                        handleCell(dateFields, timeFields, fieldName, cell, dateFormat, timeFormat, jo);
                    } catch (IllegalStateException e) {
                        throw new CustomException("导入第[" + j + "]行数据中[" + titles.get(i) + "]字段转换失败！");
                    }
                }
                if (!jo.isEmpty()) {
                    jsonArray.add(jo);
                }
            }
            return jsonArray.toString();
        }
    }

    /**
     * set value to jsonObject from cell
     *
     * @param dateFields    date filed
     * @param timeFields    time filed
     * @param fieldName     file name
     * @param cell          cell
     * @param dateFormat    data format
     * @param timeFormat    time format
     * @param jo            jsonObject
     */
    private static void handleCell(List<String> dateFields, List<String> timeFields, String fieldName, Cell cell,
                                   SimpleDateFormat dateFormat, SimpleDateFormat timeFormat, JSONObject jo) {
        if (null != cell && cell.getCellTypeEnum() != CellType.BLANK) {
            if (dateFields.contains(fieldName) && null != cell.getDateCellValue()) {
                jo.put(fieldName, dateFormat.format(cell.getDateCellValue()));
            } else if (timeFields.contains(fieldName) && null != cell.getDateCellValue()) {
                jo.put(fieldName, timeFormat.format(cell.getDateCellValue()));
            } else {
                cell.setCellType(CellType.STRING);
                if (!StringUtils.isBlank(cell.getStringCellValue())) {
                    jo.put(fieldName, cell.getStringCellValue().trim());
                }
            }
        }
    }

    /**
     * get workbook
     *
     * @param file  file
     * @return      workbook
     */
    private static Workbook getWorkbook(MultipartFile file) {
        Workbook workbook = null;
        String fileName = file.getOriginalFilename();
        try {
            assert fileName != null;
            if (fileName.endsWith(ExportConstant.SUFFIX_2003)) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (fileName.endsWith(ExportConstant.SUFFIX_2007)) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
        } catch (IOException e) {
            throw new CustomException("msg.error.file_handle_fail");
        }
        return workbook;
    }

    /**
     * 基本验证
     *
     * @param file          file
     * @param cellCount     cell num
     * @param fieldNames    field name
     */
    private static void valid(MultipartFile file, int cellCount, List<String> fieldNames) {
        if (null == file) {
            throw new CustomException("msg.error.file_is_null");
        }
        if (null == fieldNames || fieldNames.isEmpty()) {
            throw new CustomException("msg.error.fields_can_not_be_null");
        }
        if (cellCount != fieldNames.size()) {
            throw new CustomException("msg.error.fields_count_not_eq_cells_count");
        }
    }

}













































