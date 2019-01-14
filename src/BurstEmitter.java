// Eventually emitter will spawn particles. BurstEmitter will have direction and particles will have direction tied to
// emitter's direction. Particles will have initial position same as emitter or in a half circle or line.
/*public class BurstEmitter {

}*/

// - add emitter (also add "time to live" to particle, move spawning of particles to emitter, maybe keep random spawning
//   of particles as a feature, add different types of emitters (maybe each in its own class), figure out who will spawn
//   emitters etc etc. Basically a lot of work to be done on emitters

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BurstEmitter extends Circle {
	int size = 10;
	int NParticles = 100;

	public BurstEmitter(int centerX, int centerY) {
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.setRadius(size);
		this.setFill(Color.ORANGE);

		for (int i = 0; i < NParticles; i++) {
			Graphics.particles.add(new Particle(centerX, centerY));
		}

		for (int i = 0; i < NParticles; i++) {

		}
	}
}