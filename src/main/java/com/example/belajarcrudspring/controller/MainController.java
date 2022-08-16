package com.example.belajarcrudspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {
    @RequestMapping("/")
    public Map<String, Object> helloWorld()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("statusCode", 200);
        map.put("msg", "Selamat Datang di sistem kami");
        return map;
    }
}
