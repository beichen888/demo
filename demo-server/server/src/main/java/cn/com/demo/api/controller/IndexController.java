package cn.com.demo.api.controller;


import cn.com.demo.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by miguo on 2018/5/8.
 */
@RestController
public class IndexController extends BaseController {
    @GetMapping(value = "/api")
    public Result hello() {
        return renderSuccess("hello yueryoudao api");
    }
}
