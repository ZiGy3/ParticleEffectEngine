import com.sun.javafx.geom.Vec2d;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Particle extends Circle {
	Vec2d position;
	Vec2d direction;
	int speed;
	int size = 4;

	public Particle() {
		Random rnd = new Random();
		this.setCenterX(rnd.nextInt(Graphics.width));
		this.setCenterY(rnd.nextInt(Graphics.height));
		this.setRadius(size);
		this.direction.set(rnd.nextInt(6), rnd.nextInt(6));
		this.speed = rnd.nextInt(5);
	}

	public void update() {
		this.relocate(this.getCenterX() + (direction.x * speed), this.getCenterY() + (direction.y * speed));
	}
}
