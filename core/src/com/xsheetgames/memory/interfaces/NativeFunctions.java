package com.xsheetgames.memory.interfaces;

public interface NativeFunctions {
	public void showMessage(String title, String message);
	public void openURL(String url);
	public void share(String subject, String text);
	public void rate();

	//Analytics
	void initialize();	
    void trackPageView(String path);

    //Exception Handling
    void sendException(String description, boolean fatal);
    void sendEvent(String category, String subCategory, String component, long value);

    //Ads
    void showFullScreenAd();
    void showBannerAd();
    void closeBannerAd();
    void TriggerStandingInterstitials();
}
