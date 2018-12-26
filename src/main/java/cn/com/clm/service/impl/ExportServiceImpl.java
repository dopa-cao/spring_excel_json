package cn.com.clm.service.impl;

import cn.com.clm.constant.ExportConstant;
import cn.com.clm.domain.People;
import cn.com.clm.domain.export.ExportConfig;
import cn.com.clm.service.ExportService;
import cn.com.clm.util.DataUtil;
import cn.com.clm.util.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.com.clm.constant.ExportConstant.SUFFIX_2007;

/**
 * describe: service impl
 *
 * @author liming.cao
 */
@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportConfig exportConfig;

    @Override
    public Workbook getData() {
        JSONObject jsonObject = new JSONObject();
        List<People> peoples = DataUtil.buildList();
        jsonObject.put(ExportConstant.KEY_ROWS, JSON.toJSONString(peoples, SerializerFeature.WriteMapNullValue));
        jsonObject.put(ExportConstant.KEY_HEADERS, JSON.toJSONString(exportConfig.getPeopleHeaders()));
        jsonObject.put(ExportConstant.KEY_FIELDS, JSON.toJSONString(exportConfig.getPeopleFields()));
        Map<String, String> map = new HashMap<>(1);
        map.put("1", jsonObject.toJSONString());
        return ExcelUtil.makeExcelFile(SUFFIX_2007, map, exportConfig.getPeopleTitles());
    }

}
