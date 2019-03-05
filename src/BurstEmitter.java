// Eventually emitter will spawn particles. BurstEmitter will have direction and particles will have direction tied to
// emitter's direction. Particles will have initial position same as emitter or in a half circle or line.
/*public class BurstEmitter {

}*/

// - add emitter (also add "time to live" to particle, move spawning of particles to emitter, maybe keep random spawning
//   of particles as a feature, add different types of emitters (maybe each in its own class), figure out who will spawn
//   emitters etc etc. Basically a lot of work to be done on emitters

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BurstEmitter extends Circle {
	int size = 10;
	int NParticles = Graphics.NParticles;
	int centerX;
	int centerY;

	public BurstEmitter(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.setRadius(size);
		this.setFill(Color.rgb(255, 65, 54));

	}
	public void init() {
		int c = 0;
		int index = 0;
		while (index < Graphics.particles.size()) {
			if (!Graphics.particles.isEmpty() && !Graphics.particles.get(index).active) {
				if (c == NParticles) break;
				c++;
				Graphics.particles.get(index).setCenterX(centerX);
				Graphics.particles.get(index).setCenterY(centerY);
				Graphics.particles.get(index).active = true;
				Graphics.particles.get(index).randomize();
				Particle p = Graphics.particles.get(index);
				((Pane) getParent()).getChildren().add(p);
			}
			index++;
		}

		if (c < NParticles) {
			for (int i = 0; i < NParticles - c; i++) {
				Particle p = new Particle(centerX,centerY);
				Graphics.particles.add(p);
				((Pane) getParent()).getChildren().add(p);
			}
		}
	}
}