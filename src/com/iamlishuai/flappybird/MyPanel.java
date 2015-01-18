package com.iamlishuai.flappybird;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements Runnable {

	//小鸟对象
	private Bird bird;	
	
	//管道对象
	private Pipe[] pipe;
	
	//声音播放器
	private PlaySounds player;
	
	//背景图片
	private Image background;	
	private Image ground;
	
	//游戏状态
	private GameStatus gs;
	private int rp = 1;
	
	//构造方法
	public MyPanel(Bird bird,Pipe[] pipe,GameStatus gs,PlaySounds player){
		
		//初始化背景
		background = new ImageIcon(getClass().getResource("/Background.png")).getImage();
		ground = new ImageIcon(getClass().getResource("/Ground.png")).getImage();
		
		//初始化小鸟
		this.bird = bird;
		
		
		//初始化声音播放器
		this.player = player;
		
		//初始化管道 
		this.pipe = new Pipe[4];
		for(int i = 0; i < this.pipe.length; i++){
			this.pipe[i] = pipe[i];
		}
		
		this.gs = gs;
		
	}
	

	public void paint(Graphics g){
		super.paint(g);			
		
		if(GameUI.flag == 0){
			bird.reStart();
		}
		for(int i = 0; i < pipe.length; i++){
			if(GameUI.flag == 0){
				pipe[i].reStart();
			}
		}
		//绘制背景
		g.drawImage(background,0,0,800,600,null);
		//绘制管道 
		for(int i = 0; i < pipe.length; i++){
			pipe[i].drawSelf(g);
			
			if(pipe[i].isBirdDied(bird)){
				player.setIsPlay(3);
				GameUI.flag = 2;
			};
			
			if(pipe[i].isBirdPass(bird)){
				player.setIsPlay(2);
				GameUI.addScore();
			}
		}
		//绘制背景
		g.drawImage(ground, 0, 480, 800, 112, null);
		
		
		//绘制小鸟
		bird.drawSelf(g);
		
		gs.drawSelf(g);
	}

	@Override
	public void run() {

		while(true){
			
			long start = System.currentTimeMillis();
			//当游戏状态变为游戏结束时，只重绘一次(为了显示游戏得分板)
			if(GameUI.flag == 2){
				if(rp == 1){
					repaint();
					rp = 0;
				} 
				continue;
			}
			repaint();
			if(rp == 0) rp = 1;
			long end = System.currentTimeMillis();
			
			//确定游戏帧率为60
			if(end - start < (1000 / 60)){
				try {
					Thread.sleep(1000 / 60 - end + start);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
}
