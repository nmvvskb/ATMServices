package com.mobiquity.atm.services.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ATMHours implements Serializable {

	private static final long serialVersionUID = 1L;
	private String hourFrom;
	private String hourTo;
	
	public String getHourFrom() {
		return hourFrom;
	}
	public void setHourFrom(String hourFrom) {
		this.hourFrom = hourFrom;
	}
	public String getHourTo() {
		return hourTo;
	}
	public void setHourTo(String hourTo) {
		this.hourTo = hourTo;
	}
	
	
}
