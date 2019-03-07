import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.ThreadLocalRandom;

public class OverTimeEmitter extends Circle {
	int size = 10;
	int NParticles = Graphics.NParticles;
	Point2D A;
	Point2D B;
	Vec2d direction;
	ThreadLocalRandom rnd = ThreadLocalRandom.current();

	public OverTimeEmitter(double centerX1, double centerY1, double centerX2, double centerY2) {
		this.A = new Point2D(centerX1, centerY1);
		this.B = new Point2D(centerX2, centerY2);
		this.setCenterX(centerX1);
		this.setCenterY(centerY1);
		this.setRadius(size);
		this.setFill(Color.rgb(255, 133, 27));
		direction = new Vec2d(B.getX() - A.getX(), B.getY() - A.getY());
		double vsota = Math.sqrt((direction.x * direction.x) + (direction.y * direction.y));
		direction.set(direction.x / vsota, direction.y / vsota);
		System.out.println(direction);
	}

	public void init() {
		if (A == B) {
			double d = rnd.nextDouble();
			//! spawn over time in direction of d with some delta
		}
		else {
			//! spawn over time in calculated direction with some delta
		}
	}
}

// listen for mousePressed and mouseReleased event to determine location and direction of emitter
// then spawn emitters with similar direction