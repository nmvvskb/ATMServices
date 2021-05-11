package com.mobiquity.atm.services.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Configuration;

@Component
@Configuration
@PropertySource(value="classpath:application.properties", ignoreResourceNotFound=true)
public class PropertyUtils {

	@Autowired
	private Environment environment;

	/**
	 * This method will provide value of the property which is defined in properties file.
	 * @param properyName
	 * @return
	 */
	public String getProperty(String properyName) {
		return environment.getProperty(properyName);
	}

}
