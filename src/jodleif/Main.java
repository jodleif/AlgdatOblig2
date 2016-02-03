package jodleif;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application
{
	private final BorderPane hovedLayout = new BorderPane();
	private final Canvas tegneOmråde = new Canvas();
	private final static double WIDTH = 800;
	private final static double HEIGHT = 600;

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(hovedLayout, WIDTH, HEIGHT);

		settOppGui();

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private void settOppGui()
	{
		hovedLayout.setCenter(tegneOmråde);
		tegneOmråde.setWidth(WIDTH-100);
		tegneOmråde.setHeight(HEIGHT-100);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
