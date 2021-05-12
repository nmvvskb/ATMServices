package com.mobiquity.atm.services.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.atm.services.entities.ATMAddressDetails;
import com.mobiquity.atm.services.entities.ATMDetails;
import com.mobiquity.atm.services.entities.ATMGeoLocation;
import com.mobiquity.atm.services.entities.ATMHours;
import com.mobiquity.atm.services.entities.ATMOpeningHours;
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
	public List<ATMDetails> getATMDetails() {
		logger.info("ATMController - getATMDetails() :: Start");
		List<ATMDetails> atmDetailsList = new ArrayList<ATMDetails>();
		String BASE_URL = getEndPointURL();
		logger.info("ATMController getATMDetails() -  BASE_URL : " + BASE_URL);
		try {
			ResponseEntity<String> responseEntity = getResponseFromEndPointURL(BASE_URL);
			String responseBody = responseEntity.getBody();
			if (responseEntity.getStatusCode().toString().contains("200")) {
				atmDetailsList = convertResponsetoObject(responseBody);
				// logger.info("atmDetailsList : "+atmDetailsList.toString());
			}
		} catch (Exception e) {
			logger.info("Exception In ATMController getATMDetails(): " + e.getMessage());
			e.printStackTrace();
		}
		logger.info("ATMController getATMDetails() - atmDetailsList size : " + atmDetailsList.size());
		logger.info("ATMController - getATMDetails() :: End");
		return atmDetailsList;
	}

	@GetMapping("/{city}")
	public List<ATMDetails> getATMByCity(@PathVariable("city") String cityName) {
		logger.info("ATMController getATMByCity() :: Start");
		logger.info("ATMController getATMByCity() :: cityName = " + cityName);
		List<ATMDetails> atmDetailsByCity = new ArrayList<>();
		String BASE_URL = getEndPointURL();
		logger.info("ATMController getATMDetails() -  BASE_URL : " + BASE_URL);
		try {
			ResponseEntity<String> responseEntity = getResponseFromEndPointURL(BASE_URL);
			String responseBody = responseEntity.getBody();
			if (responseEntity.getStatusCode().toString().contains("200")) {
				List<ATMDetails> atmDetailsList = convertResponsetoObject(responseBody);

				atmDetailsByCity = atmDetailsList.stream().filter(
						atmDet -> atmDet.getAddress().getCity().toUpperCase().equalsIgnoreCase(cityName.toUpperCase()))
						.collect(Collectors.toList());
			}

		} catch (Exception e) {
			logger.info("Exception In ATMController getATMByCity(): " + e.getMessage());
			e.printStackTrace();
		}
		logger.info("ATMController getATMByCity() - atmDetailsByCity size : " + atmDetailsByCity.size());
		logger.info("ATMController getATMByCity() :: End");
		return atmDetailsByCity;
	}

	/**
	 * this method will fetch the end point url from properties file.
	 * 
	 * @return
	 */
	private String getEndPointURL() {
		logger.info("ATMController getEndPointURL() :: Start");
		String url = propertyUtil.getProperty(ATMServiceConstants.ATM_LIST_END_POINT_URL);
		System.out.println("ATMController getEndPointURL : " + url);
		logger.info("ATMController getEndPointURL() :: End");
		return url;
	}

	/**
	 * this method will provide the response of the endpoint url
	 * 
	 * @param BASE_URL
	 * @return
	 */
	private ResponseEntity<String> getResponseFromEndPointURL(String BASE_URL) {
		logger.info("ATMController getResponseFromEndPointURL() :: Start");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> entity = new HttpEntity<Object>(headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL, HttpMethod.GET, entity, String.class);
		logger.info("ATMController getResponseFromEndPointURL() :: End");
		return responseEntity;
	}
	
	
	/**
	 * this method will convert the response to entities
	 * 
	 * @param responseBody
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private List<ATMDetails> convertResponsetoObject(String responseBody)
			throws JsonMappingException, JsonProcessingException {
		logger.info("ATMController - convertResponsetoObject() :: Start");
		List<ATMDetails> atmDetailsList = new ArrayList<ATMDetails>();
		responseBody = responseBody.substring(5, responseBody.length());
		ObjectMapper objMapper = new ObjectMapper();
		TypeReference<List> typeRef = new TypeReference<List>() {
		};
		atmDetailsList = mapResponseToObject(objMapper.readValue(responseBody, typeRef));
		logger.info("ATMController - convertResponsetoObject() :: End");
		return atmDetailsList;
	}


	/**
	 * this method will give the  Object from End point Response
	 * @param readValue
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private List<ATMDetails> mapResponseToObject(List readValue) throws JsonMappingException, JsonProcessingException {
		logger.info("ATMController mapResponseToObject() :: Start");
		List<ATMDetails> atmDetailsList = new ArrayList<ATMDetails>();
		if (readValue != null && readValue.size() > 0) {
			for (int i = 0; i < readValue.size(); i++) {
				Object obj = readValue.get(i);
				if (obj instanceof LinkedHashMap) {
					LinkedHashMap object = (LinkedHashMap) obj;
					ATMDetails atmDetailObj = new ATMDetails();

					ATMAddressDetails atmAddressDetails = getAtmAddressDetails(object.get("address"));
					atmDetailObj.setAddress(atmAddressDetails);

					String distance = String.valueOf(object.get("distance"));
					atmDetailObj.setDistance(distance);

					String functionality = String.valueOf(object.get("functionality"));
					String type = String.valueOf(object.get("type"));
					atmDetailObj.setFunctionality(functionality);
					atmDetailObj.setType(type);

					List<ATMOpeningHours> openingHoursList = getOpeningHoursDetails(object.get("openingHours"));
					atmDetailObj.setOpeningHours(openingHoursList);

					atmDetailsList.add(atmDetailObj);

				} else if (obj instanceof ATMDetails) {
					logger.info("ATMController getATMDetails() &&& ATMDetails");
				}
			}
		}
		logger.info("ATMController mapResponseToObject() :: End");
		return atmDetailsList;
	}

	/**
	 * this method will provide the ATM Address Details
	 * 
	 * @param atmAddress
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	private ATMAddressDetails getAtmAddressDetails(Object atmAddress)
			throws JsonMappingException, JsonProcessingException {
		logger.info("ATMController getAtmAddressDetails() :: Start");
		ATMAddressDetails atmAddressDetails = new ATMAddressDetails();
		LinkedHashMap addressObj = (LinkedHashMap) atmAddress;
		String street = String.valueOf(addressObj.get("street"));
		String housenumber = String.valueOf(addressObj.get("housenumber"));
		String postalcode = String.valueOf(addressObj.get("postalcode"));
		String city = String.valueOf(addressObj.get("city"));

		Object geoLocation = addressObj.get("geoLocation");
		LinkedHashMap geoLocationObj = (LinkedHashMap) geoLocation;
		String lat = String.valueOf(geoLocationObj.get("lat"));
		String lng = String.valueOf(geoLocationObj.get("lng"));

		ATMGeoLocation gLocationObj = new ATMGeoLocation();
		gLocationObj.setLat(lat);
		gLocationObj.setLat(lng);
		atmAddressDetails.setGeoLocation(gLocationObj);

		atmAddressDetails.setCity(city);
		atmAddressDetails.setHousenumber(housenumber);
		atmAddressDetails.setPostalcode(postalcode);
		atmAddressDetails.setStreet(street);

		logger.info("ATMController getAtmAddressDetails() :: End");
		return atmAddressDetails;
	}
	
	/**
	 * this method will give the Opening Hours Object 
	 * @param openingHours
	 * @return
	 */
	private List<ATMOpeningHours> getOpeningHoursDetails(Object openingHours) {
		logger.info("ATMController getOpeningHoursDetails() :: Start");
		List<ATMOpeningHours> atmOpeningHoursList = new ArrayList<>();
		
		List<?> list = isArrayList(openingHours);
		logger.info("ATMController getOpeningHoursDetails() :: list size = " + list.size());
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				if (obj instanceof LinkedHashMap) {
					LinkedHashMap oHoursMap = (LinkedHashMap) obj;
					ATMOpeningHours atmOpenHoursObj = new ATMOpeningHours();

					String dayOfWeek = String.valueOf(oHoursMap.get("dayOfWeek"));
					atmOpenHoursObj.setDayOfWeek(dayOfWeek);

					Object hours = oHoursMap.get("hours");
					List<?> hrList = isArrayList(hours);
					if (hrList != null && hrList.size() > 0) {
						Object hrObj = hrList.get(0);
						LinkedHashMap hoursObj = (LinkedHashMap) hrObj;
						String hourFrom = String.valueOf(hoursObj.get("hourFrom"));
						String hourTo = String.valueOf(hoursObj.get("hourTo"));

						ATMHours atmHours = new ATMHours();
						atmHours.setHourFrom(hourFrom);
						atmHours.setHourTo(hourTo);
						atmOpenHoursObj.setHours(atmHours);
					}
					
					atmOpeningHoursList.add(atmOpenHoursObj);
				}
			}
		}
		logger.info("ATMController getOpeningHoursDetails() :: End");
		return atmOpeningHoursList;
	}

	private List<?> isArrayList(Object openingHours) {
		List<?> list = new ArrayList<>();
		if (openingHours.getClass().isArray()) {
			list = Arrays.asList((Object[]) openingHours);
		} else if (openingHours instanceof Collection) {
			list = new ArrayList<>((Collection<?>) openingHours);
		}
		return list;
	}
}
