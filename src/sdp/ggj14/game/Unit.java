package sdp.ggj14.game;

public class Unit {
	int hp;
	int x, y;
	String sprite;

	public Unit(int x, int y, int hp) {
		this.hp = hp;
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getHP() {
		return this.hp;
	}
	
	public String getSprite() {
		return this.sprite;
	}

}
