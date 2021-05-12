package com.mobiquity.atm.services.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ATMDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private ATMAddressDetails address;
	private String distance;
	private List<ATMOpeningHours> openingHours;
	private String functionality;
	private String type;
	
	public ATMAddressDetails getAddress() {
		return address;
	}
	public void setAddress(ATMAddressDetails address) {
		this.address = address;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	
	public List<ATMOpeningHours> getOpeningHours() {
		return openingHours;
	}
	public void setOpeningHours(List<ATMOpeningHours> openingHours) {
		this.openingHours = openingHours;
	}
	public String getFunctionality() {
		return functionality;
	}
	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "ATMDetails [address=" + address + ", distance=" + distance + ", openingHours=" + openingHours
				+ ", functionality=" + functionality + ", type=" + type + "]";
	}
	
	
}


