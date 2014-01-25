package sdp.ggj14.game;

public class Projectile extends Unit {
	
	public Projectile(double x, double y, int hp) {
		super(x, y, hp);
		super.sprite = "/projectiles/player_default.png";
	}

}
