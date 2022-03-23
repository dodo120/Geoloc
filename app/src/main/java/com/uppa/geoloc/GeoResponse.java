package com.uppa.geoloc;

import java.util.List;

public class GeoResponse {
	private List<FeaturesItem> features;
	private String licence;
	private String attribution;
	private int limit;
	private String type;
	private String version;

	public void setFeatures(List<FeaturesItem> features){
		this.features = features;
	}

	public List<FeaturesItem> getFeatures(){
		return features;
	}

	public void setLicence(String licence){
		this.licence = licence;
	}

	public String getLicence(){
		return licence;
	}

	public void setAttribution(String attribution){
		this.attribution = attribution;
	}

	public String getAttribution(){
		return attribution;
	}

	public void setLimit(int limit){
		this.limit = limit;
	}

	public int getLimit(){
		return limit;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}
}