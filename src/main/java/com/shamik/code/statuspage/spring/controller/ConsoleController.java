package com.shamik.code.statuspage.spring.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by shamik.shah on 10/23/17.
 */

@RestController
public class ConsoleController
{
    @CrossOrigin
    @RequestMapping("/console")
    public String console()
    {


        return null;

    }
}
