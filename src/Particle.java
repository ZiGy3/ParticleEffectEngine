import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

// if direction * speed < 1, particle doesn't move. Implement movement by 1 pixel after 1 or more ticks.
// Possible solution: add variable that adds direction to itself each until it comes to 1, then it resets to this - 1

public class Particle extends Circle {
	Vec2d direction;
	int speed;
	int size = 4;

	public Particle() {
		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		this.setCenterX(rnd.nextInt(Graphics.width));
		this.setCenterY(rnd.nextInt(Graphics.height));
		this.setRadius(size);
		float d = rnd.nextFloat();
		this.direction = new Vec2d(d, 1 - d);
		int sign = rnd.nextInt(-1, 2);
		while (sign == 0) {
			sign = rnd.nextInt(-1, 2);
		}
		direction.x *= sign;
		do {
			sign = rnd.nextInt(-1, 2);
		} while (sign == 0);
		direction.y *= sign;
//		while (direction.x == 0 || direction.y == 0) {
//			direction.set(rnd.nextInt(-5, 6), rnd.nextInt(-5, 6));
//		}
//		this.speed = rnd.nextInt(1, 6);
		this.speed = 1;
		this.setFill(Color.BLACK);
	}

	public void update() {
		this.setCenterX((int) this.getCenterX() + (direction.x * speed));
		this.setCenterY((int) this.getCenterY() + (direction.y * speed));
	}

	public void checkBounce() {
		if ((this.getCenterX() - this.getRadius() <= 0 && this.direction.x < 0)
					|| (this.getCenterX() + this.getRadius() >= Graphics.width && this.direction.x > 0)) {
			this.direction.x *= -1;
		}
		if ((this.getCenterY() - this.getRadius() <= 0 && this.direction.y < 0)
					|| (this.getCenterY() + this.getRadius() >= Graphics.height && this.direction.y > 0)) {
			this.direction.y *= -1;
		}
	}
}
