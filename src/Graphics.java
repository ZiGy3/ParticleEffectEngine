import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Graphics extends Application {
	public static int width = 800;
	public static int height = 600;
	public static int NParticles = 100;
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
		stage.setResizable(false);
		stage.show();

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				for (Particle particle:
						particles) {
					particle.update();
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
