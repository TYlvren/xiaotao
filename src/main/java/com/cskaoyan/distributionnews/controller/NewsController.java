package com.cskaoyan.distributionnews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class NewsController {

    @RequestMapping("/")
    public String toHeadPage(Model model){
        ArrayList<Object> objects = new ArrayList<>();
        model.addAttribute("vos",objects);
        return "home";
    }
}
