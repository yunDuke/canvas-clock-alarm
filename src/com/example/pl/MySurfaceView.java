package com.example.pl;

import java.util.Calendar;

import android.R.integer;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.media.MediaBrowserCompat.MediaItem.Flags;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class MySurfaceView extends SurfaceView implements Runnable{
private Thread thread =null;
private SurfaceHolder msurfaceHolder;
private boolean running = false;
private float length;
	public MySurfaceView(Context context,float l){
		super(context);
		this.length=l;
		 msurfaceHolder = getHolder();
		
		
	}
	public void onResumeMySurfaceView(){
		running = true;
		thread = new Thread(this);
		thread.start();
		
	}
	public void onPauseMySurfaceView(){
		
		boolean retry = true;
		running =false;
		while(retry){
			try {
				thread.join();
				retry=false;
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
	}
	public void run(){
		int hour=0,min=0,sec=0;
		
		    

		
   
		while (running) {
			
			if(msurfaceHolder.getSurface().isValid()){
				   Log.i("Notice","Surface holder is valid");
				   Canvas canvas = msurfaceHolder.lockCanvas();
				
				 Paint paint = new Paint(); 
					paint.setColor(Color.RED); 
				   float x = 0;
				float y = 0;
			
				      
				 Paint paint1 = new Paint(); 
					paint1.setColor(Color.RED); 
				paint1.setStrokeWidth(5);
			  
				RegPoly secMarks  = new RegPoly(60, length, getWidth()/2, getHeight()/2, canvas, paint);
				RegPoly hourMarks = new RegPoly(12, length-20, getWidth()/2, getHeight()/2, canvas, paint);
				RegPoly hourHand  = new RegPoly(60, length-130, getWidth()/2, getHeight()/2, canvas, paint1);
				RegPoly hourline  = new RegPoly(12, length-40, getWidth()/2, getHeight()/2, canvas, paint);
				RegPoly minHand   = new RegPoly(60, length-80, getWidth()/2, getHeight()/2, canvas, paint1);
				RegPoly secHand   = new RegPoly(60, length-30, getWidth()/2, getHeight()/2, canvas, paint1);
				canvas.drawColor(0, Mode.CLEAR);
				secMarks.drawPoints();
                paint.setTextSize(20f);
//			    secMarks.drawNum(paint);
               hourline.drawmin(paint);
//			    hourMarks.drawmin(paint);
//				hourMarks.drawPoints(); 
               Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
               d.setBounds(getWidth()/2-200, getHeight()/2-200, getWidth()/2+200, getHeight()/2+200);
               d.draw(canvas);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {e.printStackTrace();}
				// get the date
				Calendar calendar = Calendar.getInstance();
				hour = calendar.get(Calendar.HOUR); 
				min = calendar.get(Calendar.MINUTE); 
				sec = calendar.get(Calendar.SECOND);
				// draw the hands	
				
				secHand.drawRadius(sec+45);
				minHand.drawRadius(min+45);
				hourHand.drawRadius(hour*5+min/12+45);
				
				msurfaceHolder.unlockCanvasAndPost(canvas); 
				

				
			}
			else{
				   Log.i("Notice","Surface holder is invalid");
			}
			
		}
		
		
		
	
		
	}

}
