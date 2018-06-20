package com.tianque.controller;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tianque.service.ChartService;

@Controller
@RequestMapping("/chart")
public class ChartController {

    @Inject
    private ChartService chartService;


    @RequestMapping(method = RequestMethod.GET)
    public String chart(Model model,HttpServletRequest request){

        String start= request.getParameter("start");
        String end=request.getParameter("end");
        if (start!=null){
            model.addAttribute("msg","该时间段");
        }
        model.addAttribute("addCustCount",chartService.addCustCount(start,end));
        model.addAttribute("successCount",chartService.successCount(start,end));
        model.addAttribute("saleMoney",chartService.saleMoney(start,end));
        return "chart/chart";
    }

    @RequestMapping(value = "/progress/data", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> loadPieData(@RequestParam(required = false, defaultValue = "") String start,
                                                 @RequestParam(required = false, defaultValue = "") String end) {
        return chartService.loadPieData(start, end);
    }


    @RequestMapping(value = "user/money", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> loadBarData( @RequestParam(required = false,defaultValue = "") String start,
                                           @RequestParam(required = false,defaultValue = "") String end) {

        List<Map<String,Object>> data = chartService.loadBarData(start,end);

        List<String> names = Lists.newArrayList();
        List<Object> values = Lists.newArrayList();

        for(Map<String,Object> map : data) {
            for(Map.Entry<String,Object> entry : map.entrySet()) {
                if(entry.getKey().equals("realname")) {
                    names.add(entry.getValue().toString());
                } else if(entry.getKey().equals("price")) {
                    values.add(entry.getValue().toString());
                }
            }
        }

        Map<String,Object> result = Maps.newHashMap();
        result.put("names",names);
        result.put("values",values);

        return result;
    }


}
