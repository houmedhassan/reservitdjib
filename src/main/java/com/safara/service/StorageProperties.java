package com.safara.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	
	private String location = System.getProperty("user.dir")+"profil/image";
	
	public String getLocation()
	{
		return location;
	}
	
	public void setLocation(String location)
	{
		this.location =location;
	}
}
