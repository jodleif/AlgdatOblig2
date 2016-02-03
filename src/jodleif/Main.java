package jodleif;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application
{
	private final BorderPane hovedLayout = new BorderPane();
	private final Canvas tegneOmråde = new Canvas();
	private final GraphicsContext grafikk = tegneOmråde.getGraphicsContext2D();
	private final static double WIDTH = 800;
	private final static double HEIGHT = 600;
	private final static double VEKSTFAKTOR = 0.7;
	private final static double MIN_LEN = 2.0;
	private final static VinkelCache cache = new VinkelCache();
	private final HBox knappePanel = new HBox();

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(hovedLayout, WIDTH, HEIGHT);

		settOppGui();
		tegnGrein((WIDTH-100)/2.0,HEIGHT-100,150,0);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		System.out.println(cache);
	}

	private void settOppGui()
	{
		hovedLayout.setCenter(tegneOmråde);
		tegneOmråde.setWidth(WIDTH-100);
		tegneOmråde.setHeight(HEIGHT-100);
		hovedLayout.setBottom(knappePanel);
		Button tegnPåNytt = new Button("Tegn");
		tegnPåNytt.setOnAction(e -> {
			grafikk.clearRect(0,0,WIDTH,HEIGHT);
			tegnTre();
			System.out.println(cache);
		});
		knappePanel.getChildren().add(tegnPåNytt);
	}

	private void tegnTre()
	{
		tegnGrein((WIDTH-100)/2.0,HEIGHT-100,150,0);
	}

	private void tegnGrein(double x0, double y0, double length, double vinkel)
	{
		if(length <=MIN_LEN){
			return;
		}
		double nyX = x0-length*cache.getSinVinkel(vinkel);
		double nyY = y0-length*cache.getCosVinkel(vinkel);
		grafikk.strokeLine(x0, y0, nyX,nyY);
		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel+0.34);
		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel-0.34);
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
