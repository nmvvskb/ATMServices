package com.mobiquity.atm.services.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ATMOpeningHours implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String dayOfWeek;
	private ATMHours hours;
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public ATMHours getHours() {
		return hours;
	}
	public void setHours(ATMHours hours) {
		this.hours = hours;
	}
	
	
}
