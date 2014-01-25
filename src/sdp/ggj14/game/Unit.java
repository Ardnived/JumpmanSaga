package sdp.ggj14.game;

public class Unit {
	int hp;
	int x, y;
	String sprite;

	public Unit(int x, int y, int hp, String sprite) {
		this.hp = hp;
		this.x = x;
		this.y = y;
		this.sprite = sprite;
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
