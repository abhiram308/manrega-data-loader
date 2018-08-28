package com.manrega.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manrega.service.PanchayatService;


@RestController
public class PanchayatController {

	@Autowired
	PanchayatService panchayatService;
	
    @RequestMapping(value="/panchayat", method=RequestMethod.POST)
    public void index() {
    	panchayatService.addPanchayats();;
    }

}
