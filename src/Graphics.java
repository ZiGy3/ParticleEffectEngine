import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Graphics extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Particle Effect Engine");
		StackPane layout = new StackPane();

		Scene scene = new Scene(layout, 800, 600);
		stage.setScene(scene);
		stage.show();
	}
}
