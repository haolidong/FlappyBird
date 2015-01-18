package com.iamlishuai.flappybird;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JFrame;

/**
 * 主UI类
 * @author Dangerous
 *
 */

public class GameUI extends JFrame implements MouseListener, MouseMotionListener {

	/*
	 * 游戏界面组成
	 * gamePanel : 游戏画面的容器，也是画板，主要绘制游戏界面
	 * bird 	 : 游戏中的主角，即小鸟
	 * pipe		 : 游戏中的障碍物，即管道
	 * score 	 : 游戏得分
	 */
	private MyPanel gamePanel;  
	private Bird bird;
	private Pipe[] pipe;
	private static int score;
	
	//管道夹缝宽度 
	public static final int w = 100;
	//两个管道之间的宽度
	public static final int d = 300;
	
	/*
	 * 游戏状态标志
	 * 0 ： 游戏未开始
	 * 1 ： 游戏进行中
	 * 2：  游戏结束
	 */
	public static int flag = 0;
	
	//游戏开始时间
	public static long start = 0;
	
	//声音播放器(一个专门播放声音的线程)
	private PlaySounds player;
	
	//游戏状态
	private GameStatus gs;
	
	//管道位置
	private int x = 800;
	private int[] ypoints = { new Random().nextInt(322 - GameUI.w) + GameUI.w + 10,
							  new Random().nextInt(322 - GameUI.w) + GameUI.w + 10,
							  new Random().nextInt(322 - GameUI.w) + GameUI.w + 10,
							  new Random().nextInt(322 - GameUI.w) + GameUI.w + 10  };
	
	public GameUI(){
		
		//初始化游戏得分
		GameUI.score = 0;
		
		//初始化游戏状态
		gs = new GameStatus();
		
		//初始化游戏开始时间
		GameUI.start = System.currentTimeMillis();
		
		//初始化声音播放器
		player = new PlaySounds();
		
		//初始化小鸟
		bird = new Bird(300,200,10,5,60);
		
		// 初始化管道
		pipe = new Pipe[4];
		for (int i = 0; i < ypoints.length; i++) {
			pipe[i] = new Pipe(x + i * GameUI.d, ypoints[i], 7);
		}
		
		// 游戏画面
		gamePanel = new MyPanel(bird, pipe, gs, player);

		// 添加鼠标监听
		gamePanel.addMouseListener(this);
		gamePanel.addMouseMotionListener(this);
		
		//启动线程
		bird.start();
		player.start();
		for(int i = 0; i < pipe.length; i++){
			pipe[i].start();
		}
		Thread gp = new Thread(gamePanel);
		gp.start();
		
		//设置窗体相关属性
		add(gamePanel);
		setResizable(false);
		setTitle("flappy bird");
		setVisible(true);
		setBounds(10,10,800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//获取游戏得分
	public static int getScore(){
		return GameUI.score;
	}

	//加分
	public static void addScore(){
		if(GameUI.flag == 1){
			GameUI.score++;
		}
	}
	
	//改变游戏状态
	public void setStatus(int status){
		GameUI.flag = status;
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		//将小鸟的状态设置为飞行状态
		bird.setFlyStatus();
		
		/*
		 * 当游戏结束后，根据点击鼠标的位置
		 * 来判断是不是点击了重新开始按钮
		 */
		if(GameUI.flag == 2){
			
			if(x > 225 && x < 381 && y > 400 && y < 493 ){
				GameUI.flag = 0;
			}
			
			GameUI.score = 0;
			GameUI.start = System.currentTimeMillis();
			return;
		} 

		//将游戏状态设置为游戏进行中
		GameUI.flag = 1;
		//将声音播放器设置为播放游戏进行中相关的音乐
		player.setIsPlay(1);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

//		int x = e.getX();
//		int y = e.getY();
//		if(GameUI.flag == 2 && x > 225 && x < 381 && y > 400 && y < 493 ){
//			gs.setRestart();
//			//gamePanel.repaint();
//		}
		
	}
	

	
}
