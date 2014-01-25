package sdp.ggj14.util;

public class Vector2f {
	public float x, y;
	
	public Vector2f() {
		this(0, 0);
	}
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void scale(float multiplier) {
		this.x *= multiplier;
		this.y *= multiplier;
	}

}
