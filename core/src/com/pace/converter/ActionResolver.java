package com.pace.converter;

public interface ActionResolver {
	public void Timer(int a, int b, int c, int d);
	public void stopTimer();
	public int getTotal();
	//public void showAds();
	//public void hideAds();
	//public void showOrLoadInterstital();
	int getEchauffement();
	int getEffort();
}