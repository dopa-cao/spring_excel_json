package cn.com.clm.service.impl;

import cn.com.clm.domain.People;
import cn.com.clm.domain.imprt.ImportParam;
import cn.com.clm.service.ImportService;
import cn.com.clm.util.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * describe: import service impl
 *
 * @author liming.cao
 */
@Service
public class ImportServiceImpl implements ImportService {


    @Autowired
    private ImportParam importParam;

    @Override
    public List<People> importFile(MultipartFile file) {
        String jsonStr = ExcelUtil.handleExcelFile(file, importParam.getPeopleItem());
        List<People> peoples = JSONObject.parseArray(jsonStr, People.class);
        return peoples.stream().filter(people -> people.getSex() % 2 == 0).collect(Collectors.toList());
    }

}
