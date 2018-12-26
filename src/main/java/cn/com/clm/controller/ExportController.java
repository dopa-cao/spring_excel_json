package cn.com.clm.controller;

import cn.com.clm.response.ReturnDate;
import cn.com.clm.service.ExportService;
import cn.com.clm.service.ImportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import static cn.com.clm.constant.ExportConstant.SUFFIX_2007;

/**
 * describe: export controller
 *
 * @author liming.cao
 */

@Api("excel相关的controller")
@RestController
@RequestMapping(value = "/v1")
public class ExportController extends BaseController{

    @Autowired
    private ExportService exportService;

    @Autowired
    private ImportService importService;

    @ApiOperation("excel导出")
    @GetMapping(value = "/exportTest")
    public ReturnDate exportTest(HttpServletResponse response) {
        Workbook wb = exportService.getData();
        this.writeToResponse(wb,response,"人员信息" + SUFFIX_2007);
        return new ReturnDate(Boolean.TRUE);
    }

    @ApiOperation("excel导出")
    @PostMapping(value = "/importTest")
    public ResponseEntity importTest(@RequestParam MultipartFile file){
        return ResponseEntity.ok(new ReturnDate(importService.importFile(file)));
    }


}
