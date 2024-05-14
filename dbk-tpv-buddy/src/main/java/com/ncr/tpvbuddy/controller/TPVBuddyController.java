package com.ncr.tpvbuddy.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TPVBuddyController {

    @PostMapping("/redirect")
    public void processRequest(@RequestBody String xmlData){


    }
}
