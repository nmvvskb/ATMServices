package com.mobiquity.atm.services.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private Logger logger = (Logger) LoggerFactory.getLogger(ATMController.class);
	
	@GetMapping("/getATMsList")
	public List<ATMDetails> getATMDetails(){
		logger.info("ATMController - getATMDetails() :: Start");
		List<ATMDetails> atmDetailsList = new ArrayList<ATMDetails>();
		String BASE_URL = propertyUtil.getProperty(ATMServiceConstants.ATM_LIST_END_POINT_URL);
		System.out.println("BASE_URL : "+BASE_URL);
		logger.info("ATMController - BASE_URL : "+BASE_URL);
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
//				logger.info("atmDetailsList : "+atmDetailsList.toString());
			}
		}catch(Exception e){
			logger.info("Exception In ATMController : "+e.getMessage());
			e.printStackTrace();
		}
		logger.info("ATMController atmDetailsList size : "+atmDetailsList.size());
		logger.info("ATMController - getATMDetails() :: End");
		return atmDetailsList;
	}
}
