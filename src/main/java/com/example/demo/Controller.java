package com.example.demo;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Controller {

    @GetMapping("/")
    public Map<String,Integer> get(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("FIRST" , 1);
        return hashMap;
    }
}
