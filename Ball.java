import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

public class Ball {

	public double x, y, dx, dy, speed = 1.6;
	public int width, height, pa=0, pv=0;
    public static boolean resetPts; 
	
	public Ball(int x, int y) {
        this.pv = 0;
		this.x = x;
		this.y = y;
		this.width = 3;
		this.height = 3;
	
		int angle = new Random().nextInt(120 - 45) + 45 + 1;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void tick() throws IOException {
		
        int[] pts = Ponts.loadPTS();
        this.pa = pts[0];
        this.pv = pts[1];

        if (resetPts == true) {
            resetPts = false;
            pts = Ponts.resetPTS();
            this.pa = pts[0];
            this.pv = pts[1];
            Ponts.savePTS(this.pa, this.pv);
            System.out.println("\n\nRESET!!!");
            new Game();
        }

		if(x+(dx*speed) + width >= Game.WIDTH) {
			dx*=-1;
		}else if(x+(dx*speed) < 0) {
			dx*=-1;
		}
		
		if(y >= Game.HEIGHT) {
			//Ponto do Inimigo
            this.pv++;
            Ponts.savePTS(this.pa, this.pv);
			System.out.println("\n\nPonto do Time VERMELHO!!!");
            System.out.println("\nAzul  Vs  Vermelho");
            System.out.println(this.pa + "Pts  |  " + this.pv + "Pts");
			new Game();
			return;
			
		}else if (y < 0) {
			//Ponto do Jogador
            this.pa++;
            Ponts.savePTS(this.pa, this.pv);
			System.out.println("\n\nPonto do Time AZUL!!!");
            System.out.println("\nAzul  Vs  Vermelho");
            System.out.println(this.pa + "Pts  |  " + this.pv + "Pts");
			new Game();
			return;
		}
		
		Rectangle bounds = new Rectangle((int)(x+(dx*speed)),(int)(y+(dy*speed)), width, height);
		
		Rectangle boundsPlayer = new Rectangle(Game.player.x, Game.player.y, Game.player.width, Game.player.height);
		Rectangle boundsEnemy = new Rectangle((int)Game.enemy.x, (int)Game.enemy.y, Game.enemy.width, Game.enemy.height);
		
		if (bounds.intersects(boundsPlayer)) {
			int angle = new Random().nextInt(120 - 45) + 45 + 1;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if (dy > 0) {
				dy*=-1;
			}
		}else if (bounds.intersects(boundsEnemy)) {
			int angle = new Random().nextInt(120 - 45) + 45 + 1;
			dx = Math.cos(Math.toRadians(angle));
			dy = Math.sin(Math.toRadians(angle));
			if (dy < 0) {
				dy*=-1;
			}
		}
		
		x+=dx*speed;
		y+=dy*speed;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, width, height);
	}	
}
