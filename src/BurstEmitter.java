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
	int NParticles = 5;

	public BurstEmitter(int centerX, int centerY) {
		this.setCenterX(centerX);
		this.setCenterY(centerY);
		this.setRadius(size);
		this.setFill(Color.ORANGE);

//		for (int i = 0; i < NParticles; i++) {
//			Graphics.particles.add(new Particle(centerX, centerY));
//		}

//		for (int i = 0; i < NParticles; i++) {
//		}
		int c = 0;
		int index = 0;
		while (index < NParticles) {
			if (!Graphics.particles.isEmpty() && !Graphics.particles.get(index).active) {
				if (c == NParticles) break;
				c++;
				Graphics.particles.get(index).setCenterX(centerX);
				Graphics.particles.get(index).setCenterY(centerY);
				Graphics.particles.get(index).active = true;
				((Pane) BurstEmitter.this.getParent()).getChildren().add(Graphics.particles.get(index));
				// what I'm trying to do is to reuse removed particles. as of now the way I see this would be possible
				// is to first check if there are enough deleted particles for this emitter. If there aren't, create
				// them. If yes, move to method in Graphics. There I have to iterate over the entire ArrayList, get
				// NParticles, set their position to center of emitter, set their active status to true, randomize direction
			}
			index++;
		}

		if (c < NParticles) {
			for (int i = 0; i < NParticles - c; i++) {
				Graphics.particles.add(new Particle(centerX, centerY));
				System.out.println(Graphics.particles.get(Graphics.particles.size() - 1));
//				((Pane) BurstEmitter.this.getParent()).getChildren().add(Graphics.particles.get(Graphics.particles.size() - 1));
			}
			for (int i = Graphics.particles.size() - NParticles + c; i < Graphics.particles.size(); i++) {
				((Pane) BurstEmitter.this.getParent()).getChildren().add(Graphics.particles.get(i));
				// ! make method in Graphics class to add nodes from particles to pane with parameters start and stop
			}
		}
	}
}