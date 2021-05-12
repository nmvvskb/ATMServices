package com.mobiquity.atm.services.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ATMAddressDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String street;
	private String housenumber;
	private String postalcode;
	private String city;
	private ATMGeoLocation geoLocation;
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getHousenumber() {
		return housenumber;
	}
	public void setHousenumber(String housenumber) {
		this.housenumber = housenumber;
	}
	public String getPostalcode() {
		return postalcode;
	}
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public ATMGeoLocation getGeoLocation() {
		return geoLocation;
	}
	public void setGeoLocation(ATMGeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}
	
	
}
