import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Graphics extends Application {
	public static int width = 800;
	public static int height = 600;
	public static int NParticles = 1;
	public static ArrayList<Particle> particles = new ArrayList<>();

	@Override
	public void start(Stage stage) {
		Pane root = new Pane();
		for (Particle particle:
			 particles) {
			root.getChildren().add(particle);
		}

		Scene scene = new Scene(root, width, height);

		stage.setTitle("Particle Effect Engine");
		stage.setScene(scene);
		stage.show();

		stage.maxWidthProperty().addListener((obs, oldVal, newVal) -> {
			this.width = newVal.intValue();
		});
		stage.maxHeightProperty().addListener((obs, oldVal, newVal) -> {
			this.height = newVal.intValue();
		});


		AnimationTimer timer = new AnimationTimer() {

			private long lastUpdate = 0;
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= 16_000_000) {
					for (Particle particle:
							particles) {
						particle.checkBounce();
						particle.update();
						System.out.println(particle.direction);
					}
					lastUpdate = now;
				}
			}
		};

		timer.start();
	}

	public static void main(String[] args) {
		for (int i = 0; i < NParticles; i++) {
			particles.add(new Particle());
		}
		launch(args);
	}
}
