import com.sun.javafx.geom.Vec2d;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.sqrt;

public class Particle extends Circle {
	Vec2d direction;
	long speed;
	int size = 4;
	double subX = 0;
	boolean alwaysMoveX = false;
//	double gravity = 0.981;
	double gravity = 0.5;
	double drag = 0.02;
	double bounceRatio = 0.75;
	float ttl = Graphics.ttl;
	double opacityStep;
	boolean active = true;
	ThreadLocalRandom rnd = ThreadLocalRandom.current();

	public Particle() {
		this.setFill(Color.BLACK);
		this.setRadius(size);
		this.setCenterX(rnd.nextInt(Graphics.width));
		this.setCenterY(rnd.nextInt(Graphics.height));
		this.opacityStep = 1.0 / ttl;
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
	}

	public Particle(int centerX, int centerY) {
		this.setFill(Color.BLACK);
		this.setRadius(size);
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		double d = rnd.nextDouble();
		this.opacityStep = 1.0 / ttl;
		this.speed = rnd.nextLong(1, 20);
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
	}

	public Particle(int centerX, int centerY, double directionX, double directionY) {
		this.setFill(Color.BLACK);
		this.setRadius(size);
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.opacityStep = 1.0 / ttl;
		this.speed = rnd.nextLong(5, 20);
		this.direction = new Vec2d(directionX * speed, directionY * speed);
		if (direction.x >= 1 || direction.x <= -1) {
			alwaysMoveX = true;
		}
	}

	public void update() {
		System.out.println(direction);
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
		if (this.getOpacity() > 0) {
			this.setOpacity(this.getOpacity() - opacityStep);
			if (this.getOpacity() < 0) {
				this.setOpacity(0);
			}
		} else if (this.getOpacity() == 0) {
			((Pane) getParent()).getChildren().remove(Particle.this);
			active = false;
		}
		if (direction.x < 0) {
			direction.x += drag;
		} else if (direction.x > 0) {
			direction.x -= drag;
		}
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

	void randomize() {
		this.setOpacity(1);
		double d = rnd.nextDouble();
		this.speed = rnd.nextLong(1, 20);
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
		opacityStep = 1.0 / Graphics.ttl;
	}
}
