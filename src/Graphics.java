import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Graphics extends Application implements Runnable {

	@Override
	public void run() {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Particle Effect Engine");
		Button btn = new Button();
		btn.setText("Say 'Hello World'");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				System.out.println("Helo World!");
			}
		});

		StackPane root = new StackPane();
		root.getChildren().add(btn);
		stage.setScene(new Scene(root, 800, 600));
		stage.show();
	}
}
