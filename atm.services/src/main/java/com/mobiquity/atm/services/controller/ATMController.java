package com.mobiquity.atm.services.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.atm.services.entities.ATMDetails;
import com.mobiquity.atm.services.utils.ATMServiceConstants;
import com.mobiquity.atm.services.utils.PropertyUtils;

@RestController
@RequestMapping("/atm")
public class ATMController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PropertyUtils propertyUtil;
	
	@GetMapping("/getATMsList")
	public List<ATMDetails> getATMDetails(){
		List<ATMDetails> atmDetailsList = new ArrayList<ATMDetails>();
		String BASE_URL = propertyUtil.getProperty(ATMServiceConstants.ATM_LIST_END_POINT_URL);
		System.out.println("BASE_URL : "+BASE_URL);
		try{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<?> entity = new HttpEntity<Object>(headers);
			ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity,String.class);
			String responseBody = responseEntity.getBody();
			HttpStatus statusCode = responseEntity.getStatusCode();
			if(statusCode.toString().contains("200")){
				responseBody = responseBody.substring(5, responseBody.length());
				ObjectMapper objMapper = new ObjectMapper();
				atmDetailsList = objMapper.readValue(responseBody, new TypeReference<List>() {});
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return atmDetailsList;
	}
}
