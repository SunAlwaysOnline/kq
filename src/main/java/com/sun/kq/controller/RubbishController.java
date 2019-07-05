package com.sun.kq.controller;

import com.sun.kq.entity.Rubbish;
import com.sun.kq.service.RubbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rubbish")
public class RubbishController {

    @Autowired
    RubbishService rubbishService;


    @RequestMapping("/getType")
    public Rubbish getType(String name) {
        return rubbishService.getRubbishByName(name);
    }
}
