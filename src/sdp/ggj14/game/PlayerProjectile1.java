package sdp.ggj14.game;

import sdp.ggj14.util.Sprite;

public class PlayerProjectile1 extends Unit {
	
	public PlayerProjectile1(int x, int y, int hp) {
		super(x, y, hp);
		//super.sprite = "/projectiles/player_default.png";
		super.spriteObj = new Sprite(new String[] {"/projectiles/player_default.png"}, 0);
	}

}
