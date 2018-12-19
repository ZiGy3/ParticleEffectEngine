import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

public class Particle extends Circle {
	Vec2d direction;
	long speed;
	int size = 4;
	double subX = 0;
	double subY = 0; // REMOVE AFTER APPLYING GRAVITY
	boolean alwaysMoveX = false;
	boolean alwaysMoveY = false; // REMOVE AFTER APPLYING GRAVITY
//	double gravity = 0.981;
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
		if (direction.y >= 1 || direction.y <= -1) {// REMOVE AFTER APPLYING GRAVITY
			alwaysMoveY = true; // REMOVE AFTER APPLYING GRAVITY
		} // REMOVE AFTER APPLYING GRAVITY
//		UNUSED BECAUSE Y CHANGES WITH GRAVITY AND EVENTUALLY ALL PARTICLES WILL MOVE
	}

	public void update() {
//		this.direction.y += gravity; //GRAVITY
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
		if (!alwaysMoveY) { // REMOVE AFTER APPLYING GRAVITY
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
//			this.direction.y *= -bounceRatio;
			this.direction.y *= -1;
		}
	}

	public boolean isColliding(Particle particle) {
		double xD = this.getCenterX() - particle.getCenterX();
		double yD = this.getCenterY() - particle.getCenterY();

		double sumRadius = this.getRadius() + particle.getRadius();
		double sqrRadius = sumRadius * sumRadius;

		double sqrD = (xD * xD) + (yD * yD);
		if (sqrD <= sqrRadius) {
			return true;
		}
		return false;
	}

	public void collide(Particle particle) {
		if (this == particle) return;

		Vec2d delta = new Vec2d(this.getCenterX() - particle.getCenterX(), this.getCenterY() - particle.getCenterY());
		double d = Math.sqrt(delta.x * delta.x + delta.y * delta.y);
		Vec2d mtd = new Vec2d(delta.x * (((this.size + particle.size) - d) / d), delta.y * (((this.size + particle.size) - d) / d));

		double im1 = 1 / size;
		double im2 = 1 / particle.size;

		this.setCenterX(this.getCenterX() + (int) (mtd.x * (im1 / (im1 + im2))) + 1);
		this.setCenterY(this.getCenterY() + (int) (mtd.y * (im1 / (im1 + im2))) + 1);

		particle.setCenterX(particle.getCenterX() - (int) (mtd.x * (im2 / (im1 + im2))) - 1);
		particle.setCenterY(particle.getCenterY() - (int) (mtd.y * (im2 / (im1 + im2))) - 1);

		Vec2d v = new Vec2d(this.direction.x - particle.direction.x, this.direction.y - particle.direction.y);
		Vec2d nmtd = new Vec2d(mtd.x / (mtd.x + mtd.y), mtd.y / (mtd.x + mtd.y));
		double vn = (v.x * nmtd.x) + (v.y * nmtd.y);

		if(vn > 0.0d) return;

		double i = (-(1.0d + 0.8d) * vn) / (im1 + im2);
		Vec2d impulse = new Vec2d(mtd.x * i, mtd.y * i);

		this.direction.x += impulse.x * im1;
		this.direction.y += impulse.y * im1;

		particle.direction.x -= impulse.x * im2;
		particle.direction.y -= impulse.y * im2;
	}
}
