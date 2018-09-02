package com.manrega.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manrega.service.DistrictService;
import com.manrega.service.PanchayatService;


@RestController
public class PanchayatController {

	@Autowired
	PanchayatService panchayatService;
	
	@Autowired
	DistrictService districtService;
	
    @RequestMapping(value="/")
    public void index() throws Exception {
//    	panchayatService.addPanchayats();;
    	
    	districtService.addDistricts("");
    }

}
