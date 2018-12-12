import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

public class Particle extends Circle {
	Vec2d direction;
	int speed;
	int size = 4;

	public Particle() {
		//Random rnd = new Random();
		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		this.setCenterX(rnd.nextInt(Graphics.width));
		this.setCenterY(rnd.nextInt(Graphics.height));
		this.setRadius(size);
		this.direction = new Vec2d(rnd.nextInt(-6, 6), rnd.nextInt(-6, 6));
		this.speed = rnd.nextInt(5);
		this.setFill(Color.BLACK);
	}

	public void update() {
		this.setCenterX(this.getCenterX() + (direction.x));
		this.setCenterY(this.getCenterY() + (direction.y));
	}
}
