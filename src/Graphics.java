import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

//FIXME to-do list
// - fix particles going over the edge after resizing window
// - fix frame rate going over 60 on ubuntu
// - add collisions
// - add gravity

public class Graphics extends Application {
	public static int width = 800;
	public static int height = 600;
	public static int NParticles = 10;
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

		stage.widthProperty().addListener((obs, oldVal, newVal) -> width = newVal.intValue());
		stage.heightProperty().addListener((obs, oldVal, newVal) -> height = newVal.intValue());


		AnimationTimer timer = new AnimationTimer() {

			private long lastUpdate = 0;
			@Override
			public void handle(long now) {
				if (now - lastUpdate >= 0) {
					for (Particle particle :
 							particles) {
						particle.checkBounce();
						particle.update();
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
