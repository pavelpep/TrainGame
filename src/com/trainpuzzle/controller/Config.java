package com.trainpuzzle.controller;

import java.util.ArrayList;
import java.util.List;

public class Config {
	public List<String> campaigns;
	public int currentCampaign;
	
	public Config(){
		campaigns = new ArrayList<String>();
		campaigns.add("Campaign1");
		currentCampaign = 1;
	}
}
