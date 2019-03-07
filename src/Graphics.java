import com.sun.javafx.perf.PerformanceTracker;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

import static oracle.jrockit.jfr.events.Bits.intValue;

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
	public static ArrayList<Particle> particles = new ArrayList<>();
	public static int NParticles = 100;
	public static int ttl = 150;
	private static PerformanceTracker tracker;
	private Pane root1 = new Pane();

	@Override
	public void start(Stage stage) {
		Pane root = root1;

		Scene scene = new Scene(root, width, height);

		final ComboBox emitterType = new ComboBox();
		emitterType.getItems().addAll(
				"Burst emitter",
				"Over Time emitter"
		);
		emitterType.setValue("Burst emitter");

		final Label particleNumberLabel = new Label("NParticles:");
		particleNumberLabel.setLayoutY(30);

		final Slider particleSlider = new Slider(1, 500, 100);
		particleSlider.setLayoutY(32);
		particleSlider.setLayoutX(80);

		final Label ParticleNumberStateLabel = new Label(Double.toString(particleSlider.getValue()));
		ParticleNumberStateLabel.setLayoutY(30);
		ParticleNumberStateLabel.setLayoutX(220);

		final Label ttlLabel = new Label("Time to live:");
		ttlLabel.setLayoutY(60);

		final Slider ttlSlider = new Slider(50, 1000, 150);
		ttlSlider.setLayoutY(62);
		ttlSlider.setLayoutX(80);

		final Label ttlSliderStateLabel = new Label(Double.toString(ttlSlider.getValue()));
		ttlSliderStateLabel.setLayoutY(60);
		ttlSliderStateLabel.setLayoutX(220);

		root.getChildren().addAll(emitterType, particleNumberLabel, particleSlider, ParticleNumberStateLabel,
				ttlLabel, ttlSlider, ttlSliderStateLabel);

		stage.setTitle("Particle Effect Engine");
		stage.setScene(scene);
//		stage.initStyle(StageStyle.TRANSPARENT);
		stage.show();

		particleSlider.valueProperty().addListener((ov, old_val, new_val) -> {
			NParticles = intValue(new_val);
			ParticleNumberStateLabel.setText(Integer.toString(NParticles));
		});

		ttlSlider.valueProperty().addListener((ov, old_val, new_val) -> {
			ttl = intValue(new_val);
			ttlSliderStateLabel.setText(Integer.toString(ttl));
		});

		root.setOnMouseClicked(event -> {
			if (emitterType.getValue() == "Burst emitter") {
				BurstEmitter b = new BurstEmitter((int) event.getX(), (int) event.getY());
				root.getChildren().add(b);
				b.init();
			}
		});

		root.setOnMousePressed(event -> {
			if (emitterType.getValue() == "Over Time emitter") {
				double x1 = event.getX();
				double y1 = event.getY();
				root.setOnMouseReleased(event1 -> {
					OverTimeEmitter e = new OverTimeEmitter(x1, y1, event1.getX(), event1.getY());
					root.getChildren().add(e);
					e.init();
				});
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
				if (now - lastUpdate >= 16_000_000) {
					for (Particle particle:
							particles) {
						if (particle.active) {
							particle.checkBounce();
							particle.update();
							for (Particle particle2:
									particles) {
								if(particle.isColliding(particle2)) particle.collide(particle2);
							}
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
		launch(args);
	}

	private float getFps() {
		float fps = tracker.getAverageFPS();
		tracker.resetAverageFPS();
		return fps;
	}
}
