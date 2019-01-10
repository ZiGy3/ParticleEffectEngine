import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.sqrt;

public class Particle extends Circle {
	Vec2d direction;
	long speed;
	int size = 4;
	double subX = 0;
//	double subY = 0; // REMOVE AFTER APPLYING GRAVITY
	boolean alwaysMoveX = false;
//	boolean alwaysMoveY = false; // REMOVE AFTER APPLYING GRAVITY
//	double gravity = 0.981;
	double gravity = 0.5;
	double bounceRatio = 0.75;

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
//		if (direction.y >= 1 || direction.y <= -1) {// REMOVE AFTER APPLYING GRAVITY
//			alwaysMoveY = true; // REMOVE AFTER APPLYING GRAVITY
//		} // REMOVE AFTER APPLYING GRAVITY
//		UNUSED BECAUSE Y CHANGES WITH GRAVITY AND EVENTUALLY ALL PARTICLES WILL MOVE
	}

	public void update() {
		this.direction.y += gravity; //GRAVITY
		this.setCenterY((int) this.getCenterY() + (int) direction.y);

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
//		if (!alwaysMoveY) { // REMOVE AFTER APPLYING GRAVITY
//			subY += direction.y;
//			if (subY > 1 || subY < -1) {
//				if (direction.y > 0) {
//					this.setCenterY(this.getCenterY() + 1);
//					subY -= 1;
//				} else {
//					this.setCenterY(this.getCenterY() - 1);
//					subY += 1;
//				}
//			}
//		} else {
//			this.setCenterY((int) this.getCenterY() + (int) direction.y);
//		}
	}


	public void checkBounce() {
		if ((this.getCenterX() - this.getRadius() <= 0 && this.direction.x < 0)
					|| (this.getCenterX() + this.getRadius() >= Graphics.width && this.direction.x > 0)) {
			this.direction.x *= -1;
		}
		if ((this.getCenterY() - this.getRadius() <= 0 && this.direction.y < 0)
					|| (this.getCenterY() + this.getRadius() >= Graphics.height && this.direction.y > 0)) {
			this.direction.y *= -bounceRatio;
//			this.direction.y *= -1;
		}
	}

	public boolean isColliding(Particle particle) {
		double deltaX = particle.getCenterX() - this.getCenterX();
		double deltaY = particle.getCenterY() - this.getCenterY();

		double radiusSum = this.size + particle.size;

		if (deltaX * deltaX + deltaY * deltaY <= radiusSum * radiusSum) {
			if (deltaX * (particle.direction.x - this.direction.x)
						+ deltaY * (particle.direction.y - this.direction.y) < 0) {
				return true;
			}
		}
		return false;
	}

	public void collide(Particle particle) {
		double deltaX = particle.getCenterX() - this.getCenterX();
		double deltaY = particle.getCenterY() - this.getCenterY();

		double distance = sqrt(deltaX * deltaX + deltaY * deltaY);
		double unitContactX = deltaX / distance;
		double unitContactY = deltaY / distance;

		double u1 = this.direction.x * unitContactX + this.direction.y * unitContactY;
		double u2 = particle.direction.x * unitContactX + particle.direction.y * unitContactY;

		double massSum = this.size + particle.size;
		double massDiff = this.size - particle.size;

		double v1 = (2 * particle.size * u2 + u1 * massDiff) / massSum;
		double v2 = (2 * this.size * u1 - u2 * massDiff) / massSum;

		double u1PerpX = this.direction.x - u1 * unitContactX;
		double u1PerpY = this.direction.y - u1 * unitContactY;
		double u2PerpX = particle.direction.x - u2 * unitContactX;
		double u2PerpY = particle.direction.y - u2 * unitContactY;

		this.direction.set(v1 * unitContactX + u1PerpX, v1 * unitContactY + u1PerpY);
		particle.direction.set(v2 * unitContactX + u2PerpX, v2 * unitContactY + u2PerpY);
	}
}
