package com.xsheetgames.memory;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.ChartboostDelegate;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Privacy.model.GDPR;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.xsheetgames.memory.interfaces.NativeFunctions;

public class MainActivity extends AndroidApplication implements NativeFunctions {
	
	MainActivity me = this;
	MemoryGdxGame theGame;
	RelativeLayout layout;
	boolean doInterstitial = false;
	String appSignature = "";
	String appId = "";
	
	
	@Override
    public void onStart() {
    	super.onStart();
		Chartboost.startWithAppId(this.getContext(), appId, appSignature);
		Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
		Gdx.app.log("chartboost", "ads enabled");
    }
    
    @Override
    public void onStop() {
    	super.onStop();
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
		try {
			//Chartboost.onDestroy(this);
		} catch(Exception excp) {}
    }
    
    @Override
    public void onBackPressed() {
		if (Chartboost.onBackPressed()) return;
		else super.onBackPressed();
    }

	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	/* BASIC STUFF */
    	super.onCreate(savedInstanceState);
        this.layout = new RelativeLayout(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = true;
        this.theGame = new MemoryGdxGame(this);
        View gameView = initializeForView(this.theGame, cfg);
        layout.addView(gameView);
        /* END BASIC STUFF */
        
        /* ANALYTICS */
        this.initialize();        
        /* END ANALYTICS */
        
        setContentView(layout);
        
        /* Chartboost Stuff */

        if(Configuration.store.equals("play")) {
        	appId = Configuration.chartboostAppKeyPlay;
	        appSignature = Configuration.chartboostAppSigneturePlay;
        }
        if(Configuration.store.equals("amazon")) {
        	appId = Configuration.chartboostAppKeyAmazon;
	        appSignature = Configuration.chartboostAppSignetureAmazon;
        }
		Chartboost.addDataUseConsent(this.getContext(), new GDPR(GDPR.GDPR_CONSENT.NON_BEHAVIORAL));
		Chartboost.setDelegate(delegate);
        /* END Chartboost Stuff */
        
    }


	private final int SHOW_MESSAGE = 3;
	public void showMessage(String title, String message) {
		try {
			Message msg = Message.obtain();
			msg.what = 3;
			msg.obj = title + "###" + message;
			handler.sendMessage(msg);
		} catch(Exception e) {

		}
	}


	@Override
	public void openURL(String url) {
		Intent browserIntent = new Intent(
		"android.intent.action.VIEW",
		Uri.parse(url));
		startActivity(browserIntent);
	}


	@Override
	public void share(String subject, String text) {
		Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Configuration.shareAdress + this.getApplicationContext().getPackageName());
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
	}


	@Override
	public void rate() {
		Uri uri = Uri.parse(Configuration.rateTarget + this.getApplicationContext().getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
		  startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
		  try {
			  Toast.makeText(this.getApplicationContext(), "Couldn't launch the targeted store", Toast.LENGTH_LONG).show();
		  } catch(Exception noHandling) { }
		}		
	}


	
	
	/****GOOGLE ANALYTICS ***************************************************************************/
	GoogleAnalytics mGaInstance;
	Tracker mGaTracker;
	
	public void initialize() {
		mGaInstance = com.google.android.gms.analytics.GoogleAnalytics.getInstance(this);
		mGaTracker = this.getDefaultTracker();
	}
	synchronized public com.google.android.gms.analytics.Tracker getDefaultTracker() {
		// To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
		if (mGaTracker == null) {
			mGaTracker = mGaInstance.newTracker(Configuration.GoogleAnalyticsTracker);
			mGaTracker.setAnonymizeIp(true);
		}

		return mGaTracker;
	}


	public void trackPageView(String path) {
		mGaTracker.setScreenName(path);
		mGaTracker.send(new HitBuilders.ScreenViewBuilder().build());
	}


	public void sendException(String description, boolean fatal) {
		//mGaTracker.sendException(description, fatal);
	}


	public void sendEvent(String category, String subCategory, String component, long value) {
		mGaTracker.send(new HitBuilders.EventBuilder()
				.setCategory(category)
				.setAction(subCategory)
				.setLabel(component)
				.setValue(value)
				.build());
	}

	/**********HANDLER, UM DEM HAUPT-THREAD WAS ANZUWEISEN*****************************************************************************/
	@SuppressLint("HandlerLeak")
	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_MESSAGE:
				{
					try {
						AlertDialog.Builder builder = new AlertDialog.Builder(me);
						final String msgTitle = ((String) msg.obj).split("###")[0];
						String msgMessage = ((String) msg.obj).split("###")[1];
						builder.setTitle(msgTitle)
								.setMessage(msgMessage)
								.setCancelable(false)
								.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
										dialog.cancel();
										if(msgTitle.equals("Licencing")) Gdx.app.exit();
									}
								});
						AlertDialog alert = builder.create();
						alert.show();
					} catch(Exception e) { Gdx.app.log("Show Message","Failed to show Message");}
					break;
				}
			}
		}
	};
	/*****************************************************************************************************************************/
	
	
	/*** AD SHOWING FUNCTIONS ************************************************************************/
	
	@Override
	public void showFullScreenAd() {
		if(Configuration.useAds) {
			handler.post(new Runnable() {
				public void run() {
					try {
						if(Chartboost.hasInterstitial(CBLocation.LOCATION_DEFAULT)) {
							Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
							doInterstitial = false;
						} else doInterstitial = true;
					} catch(Exception e) {
						Gdx.app.log("Error posting ad","AD NOT POSTED");
					}
				}
			});
		}
	}

	@Override
	public void TriggerStandingInterstitials() {
		if(doInterstitial == true && Chartboost.hasInterstitial(CBLocation.LOCATION_DEFAULT)) {
			Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
			doInterstitial = false;
		}
	}

	@Override
	public void showBannerAd() {

	}


	@Override
	public void closeBannerAd() {

	}

	private ChartboostDelegate delegate = new ChartboostDelegate() {


		@Override
		public void didCacheInterstitial(String location) {
			if(doInterstitial == true) {
				Chartboost.showInterstitial(CBLocation.LOCATION_DEFAULT);
				doInterstitial = false;
			}
		}

		@Override
		public void didFailToLoadInterstitial(String location, CBError.CBImpressionError error) {}

		@Override
		public void didDisplayInterstitial(String location) {}


		@Override
		public void didCacheInPlay(String location) {};

		@Override
		public void didClickInterstitial(String location) {};

		@Override
		public void didCloseInterstitial(String location) {};

		@Override
		public void didDismissInterstitial(String location) {
			Chartboost.cacheInterstitial(CBLocation.LOCATION_DEFAULT);
		};

	};
}