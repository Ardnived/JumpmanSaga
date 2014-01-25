package sdp.ggj14.game;

import sdp.ggj14.util.Sprite;

public class Projectile extends Unit {
	
	public Projectile(double x, double y, int hp) {
		super(x, y, hp);
		super.spriteObj = new Sprite(new String[] {"/projectiles/player_default.png"}, 1);
	}

}
