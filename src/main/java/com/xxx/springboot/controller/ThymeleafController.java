package com.xxx.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Controller
public class ThymeleafController {

    /**
     *  注意 这里不能用restcontroller，因为加了ResponseBody会导致
     *  返回的字符串写入Response中，所以返回的不是界面了
     * @return
     */
    @RequestMapping("/themeleaf")
    public String success(HashMap<String, Object> map) {
        map.put("Hello", "nihao");
        return "success";
    }

}
