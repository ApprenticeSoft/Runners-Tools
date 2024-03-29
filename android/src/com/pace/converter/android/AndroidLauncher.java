package com.pace.converter.android;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pace.converter.ActionResolver;
import com.pace.converter.MyGdxGame;
import com.pace.converter.android.IntentServiceTimer.MyBinder;

public class AndroidLauncher extends AndroidApplication implements ActionResolver{
	
	protected View gameView;
	int aa, bb, cc, dd;
	public final static String ECHAUFFEMENT = "Echauffement", REPOS = "repos", EFFORT = "effort", TOTAL = "total";
	Intent intentTimer;
	IntentServiceTimer intentServiceTimer;
	boolean Bound = false;
	boolean restart = false;
	
	private static final String GOOGLE_PLAY_GAME_URL = "https://play.google.com/store/apps/details?id=com.pace.converter.android";
	private static final String GOOGLE_PLAY_STORE_URL = "https://play.google.com/store/apps/developer?id=Apprentice+Soft";
	private static final String AMAZON_GAME_URL = "http://www.amazon.com/gp/mas/dl/android?p=com.pace.converter.android";
	private static final String AMAZON_STORE_URL = "http://www.amazon.com/gp/mas/dl/android?p=com.premier.jeu.android&showAll=1";
	private RelativeLayout rLayout;
	private ConnectivityManager connManager;
	private NetworkInfo info;
	private NetworkInfo info1;

	
	public AndroidLauncher() {
        super();
    }
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);					//Test en cours
		
	    rLayout = new RelativeLayout(this);
	    RelativeLayout.LayoutParams rParams = 
	            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
	                    RelativeLayout.LayoutParams.WRAP_CONTENT);
	        rParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
	        rParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
	    rLayout.setLayoutParams(rParams);

	    
		View gameView = createGameView(config);
		
		rLayout.addView(gameView);
		setContentView(rLayout);
		
		intentTimer = new Intent(AndroidLauncher.this, IntentServiceTimer.class);	//Cr�ation du service
		bindService(intentTimer, mConnection, Context.BIND_AUTO_CREATE);			//Liaison du service avec l'activit�
		
		connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	   	info = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	   	info1 = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
	}
	
	 private View createGameView(AndroidApplicationConfiguration config) {
		    gameView = initializeForView(new MyGdxGame(this), config);
		    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		    params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		    gameView.setLayoutParams(params);
		    return gameView;
	  }
	 
	 @Override
	    protected void onStop() {
	        super.onStop();
	        // Unbind from the service
	        if (Bound) {
	            unbindService(mConnection);
	            Bound = false;
	        }
	    }
	 
	 //Permet de lier un Service � l'activit�
	 private ServiceConnection mConnection = new ServiceConnection() {
	        @Override
	        public void onServiceConnected(ComponentName className,
	                IBinder service) {
	            // We've bound to LocalService, cast the IBinder and get LocalService instance
	        	MyBinder binder = (MyBinder) service;	//MyBinder est d�fini dans le service (IntentService dans ce cas)
	        	intentServiceTimer = binder.getService();
	            Bound = true;
	        }

	        @Override
	        public void onServiceDisconnected(ComponentName arg0) {
	            Bound = false;
	        }
	    };
	
	//M�thodes de l'interface ActionResolver permettant d'obtenir des donn�es de l'activit� et d'envoyer des donn�es vers l'activit�   
	@Override
	public void Timer(int a, int b, int c, int d) {
		aa = a;
		bb = b;
		cc = c;
		dd = d;

		intentTimer.putExtra(ECHAUFFEMENT, aa);
		intentTimer.putExtra(REPOS, bb);
		intentTimer.putExtra(EFFORT, cc);
		intentTimer.putExtra(TOTAL, dd);
		
		//if(!restart){
		//	startService(intentTimer);						//Si on utilise bindService, il ne faut pas utiliser startService()
		//	restart = true;
		//}
		//else if(restart)									//Important pour ne pas lancer deux services en m�me temps
			intentServiceTimer.startTimer(intentTimer);
	}

	@Override
	public void stopTimer() {
		intentServiceTimer.stopTimer();
	}
	
	@Override
	public int getTotal() {
		return intentServiceTimer.getTotal();
	}
	
	@Override
	public int getEchauffement() {
		return intentServiceTimer.getEchauffement();
	}
	
	@Override
	public int getEffort() {
		return intentServiceTimer.getEffort();
	}
	
	@Override
	  public void onResume() {
		    super.onResume();
	  }
	
  @Override
  public void onPause() {
	    super.onPause();
  }

  @Override
  public void onDestroy() {
	    super.onDestroy();
  }

  @Override
  public void onBackPressed() {
	    final Dialog dialog = new Dialog(this);
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	
	    LinearLayout linearLayout = new LinearLayout(this);
	    linearLayout.setOrientation(LinearLayout.VERTICAL);
	
	    Button quitButton = new Button(this);
	    quitButton.setText("Quit");
	    quitButton.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	    	  intentServiceTimer.stopTimer();
	    	  finish();
	      }
	    });
	    
	    Button rateButton = new Button(this);
	    rateButton.setText("Rate");
	    rateButton.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	    	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AMAZON_GAME_URL)));  
	        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_GAME_URL)));
	        dialog.dismiss();
	      }
	    });
	    
	    Button moreAppButton = new Button(this);
	    moreAppButton.setText("More Apps");
	    moreAppButton.setOnClickListener(new OnClickListener() {
	      public void onClick(View v) {
	    	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(AMAZON_STORE_URL)));  
	        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_URL)));
	        dialog.dismiss();
	      }
	    });

	    linearLayout.addView(moreAppButton);
	    linearLayout.addView(rateButton);
	    linearLayout.addView(quitButton);
	    
	    dialog.setContentView(linearLayout);
	    dialog.show();
  }	 

}