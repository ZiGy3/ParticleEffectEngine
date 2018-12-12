import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Graphics extends Application {
	public static int width = 800;
	public static int height = 600;
	public static int NParticles = 100;
	public static ArrayList<Particle> particles = new ArrayList<>();

	@Override
	public void start(Stage stage) throws Exception {
		Pane root = new Pane();
		for (Particle particle:
			 particles) {
			root.getChildren().add(particle);
		}

		Scene scene = new Scene(root, width, height);

		stage.setTitle("Particle Effect Engine");
		stage.setScene(scene);
		stage.show();

		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						for (Particle particle:
							 particles) {
							particle.update();
						}
					}
				}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public static void main(String[] args) {
		for (int i = 0; i < NParticles; i++) {
			particles.add(new Particle());
		}
		launch(args);
	}
}
