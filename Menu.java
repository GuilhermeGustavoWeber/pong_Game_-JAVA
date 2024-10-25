import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {

	public String[] options = { "Fácil", "Médio", "Difícil", "Sair" };
	public int currentOption = 0, maxOptions = options.length - 1;
	public boolean up, down, enter;

	public void tick() {

		if (Game.gameState == "MENU") {

			if (up) {
				up = false;
				currentOption--;
				if (currentOption < 0) {
					currentOption = maxOptions;
				}
			}
			if (down) {
				down = false;
				currentOption++;
				if (currentOption > maxOptions) {
					currentOption = 0;
				}
			}
			if (enter) {

				enter = false;
				if (options[currentOption] == "Fácil") {
					Game.gameState = "FAC";

				} else if (options[currentOption] == "Médio") {
					Game.gameState = "MED";

				} else if (options[currentOption] == "Difícil") {
					Game.gameState = "DEF";

				} else if (options[currentOption] == "Sair") {
					System.exit(1);
				}
			}
		}
	}

	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 0, 100));
		g.fillRect(0, 0, Game.WIDTH * Game.SCALE, Game.HEIGHT * Game.SCALE);

		if (Game.gameState == "MENU") {
			g.setColor(Color.CYAN);
			g.setFont(new Font("arial", Font.BOLD, 30 / 3));
			g.drawString("GW7's Studios", (Game.WIDTH * Game.SCALE) / 2 - 195, (Game.HEIGHT * Game.SCALE) / 2 - 170);
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 55 / 3));
			g.drawString("Pong Game", (Game.WIDTH * Game.SCALE) / 2 - 210, (Game.HEIGHT * Game.SCALE) / 2 - 150);

			// opcoes do menu
			g.setColor(Color.WHITE);
			g.setFont(new Font("arial", Font.BOLD, 25 / 2));

			g.drawString("Fácil", (Game.WIDTH * Game.SCALE) / 2 - 172, (Game.HEIGHT * Game.SCALE) / 2 - 120);
			g.drawString("Médio", (Game.WIDTH * Game.SCALE) / 2 - 175, (Game.HEIGHT * Game.SCALE) / 2 - 105);
			g.drawString("Difícil", (Game.WIDTH * Game.SCALE) / 2 - 174, (Game.HEIGHT * Game.SCALE) / 2 - 90);
			g.drawString("Sair", (Game.WIDTH * Game.SCALE) / 2 - 171, (Game.HEIGHT * Game.SCALE) / 2 - 75);

			g.setColor(Color.yellow);
			if (options[currentOption] == "Fácil") {
				g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 185, (Game.HEIGHT * Game.SCALE) / 2 - 120);
			} else if (options[currentOption] == "Médio") {
				g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 189, (Game.HEIGHT * Game.SCALE) / 2 - 105);
			} else if (options[currentOption] == "Difícil") {
				g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 189, (Game.HEIGHT * Game.SCALE) / 2 - 90);
			} else if (options[currentOption] == "Sair") {
				g.drawString(">", (Game.WIDTH * Game.SCALE) / 2 - 186, (Game.HEIGHT * Game.SCALE) / 2 - 75);
			}
		}	
	}
}