import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class OverTimeEmitter extends Circle {
	int size = 10;
	int NParticles = Graphics.NParticles;
	double centerX1;
	double centerY1;
	Point2D A;
	Point2D B;
	Vec2d direction;
	ThreadLocalRandom rnd = ThreadLocalRandom.current();
	double delta = Graphics.delta;

	public OverTimeEmitter(double centerX1, double centerY1, double centerX2, double centerY2) {
		this.A = new Point2D(centerX1, centerY1);
		this.B = new Point2D(centerX2, centerY2);
		this.centerX1 = centerX1;
		this.centerY1 = centerY1;
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
		//! spawn over time in calculated direction with some delta
		ArrayList<Particle> test = new ArrayList<>();
		for (int i = 0; i < Graphics.NParticles; i++) {
			double d = rnd.nextDouble(0, delta);
			double dX = d* ( rnd.nextBoolean() ? 1 : -1 );
			double dY = d* ( rnd.nextBoolean() ? 1 : -1 );
			Particle p = new Particle((int) centerX1, (int) centerY1,
					direction.x + dX, direction.y + dY);
			Graphics.particles.add(p);
			((Pane) getParent()).getChildren().add(p);
		}
	}
}