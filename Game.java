import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 160, HEIGHT = 120, SCALE = 3;
	public BufferedImage layer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	public static Player player;
	public static Enemy enemy;
	public static Ball ball;
   	public static Ponts ponts;
	public Menu menu;
	public static String gameState = "MENU";
	private boolean showMessageGamerOver = false;
	private int framesGamerOver = 0;
	public static boolean win = false, lose = false;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.addKeyListener(this);
		
		menu = new Menu();


		player = new Player(60, HEIGHT-5);
		enemy = new Enemy(60, 0);
		ball = new Ball(100, HEIGHT/2 - 1);
		
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
		JFrame frame = new JFrame("Pong Game | GW7's Studios");
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		new Thread(game).start();
	}
	
	public void tick() throws IOException {

		if (gameState == "FAC") {
			player.tick();
			enemy.tick(0.03);
			ball.tick();

		} else if (gameState == "MED") {
			player.tick();
			enemy.tick(0.045);
			ball.tick();

		} else if (gameState == "DEF") {
			player.tick();
			enemy.tick(0.06);
			ball.tick();

		} else if (gameState == "MENU") {
			menu.tick();
		} else if (gameState == "GAMEOVER") {
			this.framesGamerOver++;
			if (this.framesGamerOver == 30) {
				this.framesGamerOver = 0;
			
				if (this.showMessageGamerOver) {
					this.showMessageGamerOver = false;
				} else {
					this.showMessageGamerOver = true;
				}
			}
		}
	}
	
	public void render() {
		
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
	}
		
		Graphics g = layer.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		player.render(g);
		enemy.render(g);
		ball.render(g);
		
		if(gameState == "MENU") {
			menu.render(g);
		}

		if(gameState == "GAMEOVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 30 / 3));
			g.drawString("Game Over", (Game.WIDTH * Game.SCALE) / 2 - 185, (Game.HEIGHT * Game.SCALE) / 2 - 150);
			if (win) {
				g.setColor(Color.green);
				g.setFont(new Font("arial", Font.BOLD, 55 / 3));
				g.drawString("!Você Ganhou!", (Game.WIDTH * Game.SCALE) / 2 - 220, (Game.HEIGHT * Game.SCALE) / 2 - 130);
			} else if (lose) {
				g.setColor(Color.red);
				g.setFont(new Font("arial", Font.BOLD, 55 / 3));
				g.drawString("!Você Perdeu!", (Game.WIDTH * Game.SCALE) / 2 - 220, (Game.HEIGHT * Game.SCALE) / 2 - 130);
			}
			
			if(showMessageGamerOver) {
				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.BOLD, (26 / 3) + 1));
				g.drawString("> 'ENTER' <", (Game.WIDTH * Game.SCALE) / 2 - 185, (Game.HEIGHT * Game.SCALE) / 2 - 90);
			}
		}

		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		
		bs.show();
	}
	
	
	@Override
	public void run() {

		requestFocus();
		while(true) {
            try {
                tick();
            } catch (IOException ex) {}
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {	 
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
    
    }

	@Override
	public void keyPressed(KeyEvent e) {
		
        if(e.getKeyCode() == KeyEvent.VK_R) {
			if(gameState != "MENU")
				Ball.resetPts = true;
	    }

		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState == "MENU")
				menu.enter = true;
				Sound.confirm.play();
			
			if(gameState == "GAMEOVER") {
				gameState = "MENU";
				win = false;
				lose = false;
				Ball.resetPts = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			menu.down = true;
			Sound.select.play();
	    }
		
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			menu.up = true;
			Sound.select.play();
	    }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D ) {
			player.right = true;	
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;	
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
	}
}
