import com.sun.javafx.geom.Vec2d;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class OverTimeEmitter extends Circle {
	int size = 10;
	int NParticles = Graphics.NParticles;
	int centerX;
	int centerY;
	Vec2d direction;

	public OverTimeEmitter(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.setRadius(size);
		this.setFill(Color.rgb(255, 133, 27));

	}

	public void init() {

	}
}

// listen for mousePressed and mouseReleased event to determine location and direction of emitter
// then spawn emitters with similar direction