package snake.jeet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javafx.embed.swing.JFXPanel;

public class GamePanel extends JFXPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1l;
	public static final int WIDTH = 500, HEIGHT = 500;
	private Thread thread;
	private boolean running=true;

	private boolean right = true, left = false, up = false, down = false;

	private BodyPart b;
	private ArrayList<BodyPart> Snake;

	private Apple apple;
	private ArrayList<Apple> apples;
	private Random r;
	private int xCoor = 10, yCoor = 10, size =5;
	private int ticks = 0;

	public GamePanel() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);

		Snake = new ArrayList<BodyPart>();

		apples = new ArrayList<Apple>();
		r = new Random();
		start();
	}

	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void tick() {

		if (Snake.size() == 0) {
			b = new BodyPart(xCoor, yCoor, 10);
			Snake.add(b);
		}
		ticks++;
		
		if (ticks > 250000) {
			if (right)
				xCoor++;
			if (left)
				xCoor--;
			if (up)
				yCoor--;
			if (down)
				yCoor++;
			ticks = 0;
			b = new BodyPart(xCoor, yCoor, 10);
			Snake.add(b);
			if (Snake.size() > size) {
				Snake.remove(0);
			}
		}
            if(apples.size()==0) {
            	int xCoor=r.nextInt(49);
            	int yCoor=r.nextInt(49);
            	
            	apple = new Apple(xCoor, yCoor, 10);
            	apples.add(apple);
            }
            for(int i=0;i<apples.size();i++) {
            	if(xCoor==apples.get(i).getxCoor()&&yCoor==apples.get(i).getxCoor()) {
            		size++;
            		apples.remove(i);
            		i++;
            	}
            }
            	for(int i1=0;i1<Snake.size();i1++)
            	{
            		if(xCoor==Snake.get(i1).getxCoor()&&yCoor==Snake.get(i1).getyCoor()) {
            			if(i1!=Snake.size()-1) {
            				System.out.println("Game Over");
            				stop();
            			}
            		}
            	}
            	if(xCoor<0||xCoor>49||yCoor<0||yCoor>49) {
            		System.out.println("Game Over");
            		stop();
            	}
            }
	
	@Override
	public void paint(Graphics g) {
		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (int i = 0; i < WIDTH / 10; i++) {
			g.drawLine(i * 10, 0, i * 10, WIDTH);
		}

		for (int i = 0; i < HEIGHT / 10; i++) {
			g.drawLine(0, i * 10, HEIGHT, i * 10);
		}

		for (int i = 0; i < Snake.size(); i++) {
			Snake.get(i).draw(g);
		}
		for (int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g);
		}
		System.out.println("DonePaint");
	}
	public void run() {
		while (running) {
			tick();
			repaint();
		}
	}

	public void Pressed(KeyEvent e) {
		int Key = e.getKeyCode();

		if (Key == KeyEvent.VK_RIGHT && !left) {
			right = true;
			up = false;
			down = false;
		}
		if (Key == KeyEvent.VK_LEFT && !right) {
			left = true;
			up = false;
			down = false;
		}
		if (Key == KeyEvent.VK_UP && !down) {
			up = true;
			left = false;
			right = false;
		}
		if (Key == KeyEvent.VK_DOWN && !up) {
			down = true;
			left = false;
			right = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int Key = e.getKeyCode();

		if (Key == KeyEvent.VK_RIGHT && !left) {
			right = true;
			up = false;
			down = false;
		}
		if (Key == KeyEvent.VK_LEFT && !right) {
			left = true;
			up = false;
			down = false;
		}
		if (Key == KeyEvent.VK_UP && !down) {
			up = true;
			left = false;
			right = false;
		}
		if (Key == KeyEvent.VK_DOWN && !up) {
			down = true;
			left = false;
			right = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
