import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

// if direction * speed < 1, particle doesn't move. Implement movement by 1 pixel after 1 or more ticks.
// Possible solution: add variable that adds direction to itself each until it comes to 1, then it resets to this - 1

public class Particle extends Circle {
	Vec2d direction;
	long speed;
	int size = 4;
	double subX = 0;
	double subY = 0;
	boolean alwaysMoveX = false;
	boolean alwaysMoveY = false;

	public Particle() {
		this.setFill(Color.BLACK);
		this.setRadius(size);
		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		this.setCenterX(rnd.nextInt(Graphics.width));
		this.setCenterY(rnd.nextInt(Graphics.height));
		double d = rnd.nextDouble();
		this.speed = rnd.nextLong(1, 6);
		this.direction = new Vec2d(d * speed, (1 - d) * speed);
		int sign = rnd.nextInt(-1, 2);
		while (sign == 0) {
			sign = rnd.nextInt(-1, 2);
		}
		direction.x *= sign;
		do {
			sign = rnd.nextInt(-1, 2);
		} while (sign == 0);
		direction.y *= sign;
		if (direction.x >= 1 || direction.x <= -1) {
			alwaysMoveX = true;
		}
		if (direction.y >= 1 || direction.y <= -1) {
			alwaysMoveY = true;
		}
	}

	public void update() {
		if (!alwaysMoveX) {
			subX += direction.x;
			if (subX > 1 || subX < -1) {
				if (direction.x > 0) {
					this.setCenterX(this.getCenterX() + 1);
					subX -= 1;
				} else {
					this.setCenterX(this.getCenterX() - 1);
					subX += 1;
				}
			}
		} else {
			this.setCenterX((int) this.getCenterX() + (int) direction.x);
		}
		if (!alwaysMoveY) {
			subY += direction.y;
			if (subY > 1 || subY < -1) {
				if (direction.y > 0) {
					this.setCenterY(this.getCenterY() + 1);
					subY -= 1;
				} else {
					this.setCenterY(this.getCenterY() - 1);
					subY += 1;
				}
			}
		} else {
			this.setCenterY((int) this.getCenterY() + (int) direction.y);
		}
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
