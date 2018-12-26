package cn.com.clm.controller;

import cn.com.clm.error.EnumError;
import cn.com.clm.exception.CommonException;
import cn.com.clm.response.CommonReturnData;
import cn.com.clm.util.DataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * describe: test
 *
 * @author liming.cao
 */

@Api("test相关的controller")
@RestController
@RequestMapping(value = "/v1")
public class TestController extends BaseController {


    @ApiOperation(value = "通用风格的数据返回和异常", notes = "测试")
    @GetMapping(value = "/test")
    public CommonReturnData test() throws CommonException {
        if (1 / 0 == 2) {
            throw new CommonException(EnumError.PARAMETER_ERROR);
        }
        return CommonReturnData.create(DataUtil.buildList());
    }

}
