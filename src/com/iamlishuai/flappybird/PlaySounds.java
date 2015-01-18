package com.iamlishuai.flappybird;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

/*
 * 为了防止阻塞UI线程，创建下面这个专门播放声音的线程
 */

public class PlaySounds extends Thread {

	//播放器
	private AudioClip[] player;
	
	//是否播放声音
	private int isPlay = 0;
	
	public PlaySounds(){
		
		player = new AudioClip[3];
		
		try {
			
			player[0] = Applet.newAudioClip(new URL("file:res/Wing.wav"));
			player[1] = Applet.newAudioClip(new URL("file:res/Point.wav"));
			player[2] = Applet.newAudioClip(new URL("file:res/Hit.wav"));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
	
	//重置播放声音标志 
	public void setIsPlay(int i){
		isPlay = i;
	}
	
	//播放声音
	public void run(){
		
		while(true){
			
			if(isPlay == 1 && GameUI.flag == 1){
				
				player[0].play();
				isPlay = 0;
					
			} else if(isPlay == 2 && GameUI.flag == 1){
				
				player[1].play();
				isPlay = 0;
				
			} else if(isPlay == 3 && GameUI.flag == 2){
				
				player[2].play();
				isPlay = 0;
				
			}
			
			
		}
		
	}
	
}
