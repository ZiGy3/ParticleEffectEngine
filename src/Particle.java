public class Particle {
	protected float x;
	protected float y;
	protected float direction;
	protected float speed;

	public Particle(float x, float y, float direction, float speed) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.speed = speed;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void calculateLocation () {
		this.x *= direction;
		this.y *= direction;
	}
}
