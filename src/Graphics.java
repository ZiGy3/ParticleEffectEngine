import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

//FIXME to-do list
// - FIX PARTICLES GOING OVER THE EDGE AFTER RESIZING WINDOW!!! (can be done with "StageStyle.TRANSPARENT" but I need resizing!
// - DONE fix frame rate going over 60 on ubuntu
// - DONE (MAYBE) add collisions
// - DONE add gravity
// - add emitter (also add "time to live" to particle, move spawning of particles to emitter, maybe keep random spawning
//   of particles as a feature, add different types of emitters (maybe each in its own class), figure out who will spawn
//   emitters etc etc. Basically a lot of work to be done on emitters

public class Graphics extends Application {
	public static int width = 800;
	public static int height = 600;
	public static int NParticles = 100;
	public static ArrayList<Particle> particles = new ArrayList<>();
	private static PerformanceTracker tracker;

	@Override
	public void start(Stage stage) {
		Pane root = new Pane();
//		for (Particle particle:
//			 particles) {
//			root.getChildren().add(particle);
//		}

		Scene scene = new Scene(root, width, height);

		stage.setTitle("Particle Effect Engine");
		stage.setScene(scene);
//		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();

		root.setOnMouseClicked(event -> {
			root.getChildren().add(new BurstEmitter((int) event.getX(), (int) event.getY()));
			for (Particle particle:
					particles) {
				root.getChildren().add(particle);
			}
		});

//		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
//			width = newVal.intValue();
//		});
//		stage.heightProperty().addListener((obs, oldVal, newVal) -> {
//			height = newVal.intValue();
//		});


		tracker = PerformanceTracker.getSceneTracker(scene);
		AnimationTimer timer = new AnimationTimer() {

			private long lastUpdate = 0;
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= 0_000_000) {
					for (Particle particle:
							particles) {
						if (particle.active) {
							particle.checkBounce();
							particle.update();
							for (Particle particle2:
									particles) {
								if(particle.isColliding(particle2)) particle.collide(particle2);
							}
							//System.out.println(particle.direction.y);
							//System.out.println(particle.getCenterX() + ", " + particle.getCenterY());
						}
					}
					lastUpdate = now;
				}
				//System.out.println(getFps());
			}
		};

		timer.start();
	}

	public static void main(String[] args) {
//		for (int i = 0; i < NParticles; i++) {
//			particles.add(new Particle());
//		}
		launch(args);
	}

	private float getFps() {
		float fps = tracker.getAverageFPS();
		tracker.resetAverageFPS();
		return fps;
	}
}
