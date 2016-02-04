package jodleif;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
	private final BorderPane hovedLayout = new BorderPane();
	private final Canvas tegneOmråde = new Canvas();
	private final GraphicsContext grafikk = tegneOmråde.getGraphicsContext2D();
	protected final static double WIDTH = 800;
	protected final static double HEIGHT = 600;
	private final static VinkelCache cache = new VinkelCache();
	private final VBox kontrollPanel = new VBox();
	private final HBox knappePanel = new HBox();
	private final HBox varPanel = new HBox();
	private final ScrollBar vinkel = new ScrollBar();
	private final ScrollBar lengde = new ScrollBar();
	private Tre tre = new Tre();

	// VARIABLER FOR TEGNING AV TREET
	private static double VEKSTFAKTOR = 0.7;
	private static double MIN_LEN = 2.0;
	private static DoubleProperty VINKEL_VEKST ;
	private static DoubleProperty INITIELL_LENGDE;
	static int nivå=0;


	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(hovedLayout, WIDTH, HEIGHT);

		settOppGui();
		//tegnGrein((WIDTH-100)/2.0,HEIGHT-100,150,0);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
		tre.oppdater();
		System.out.println(cache);
	}

	private void settOppGui()
	{
		hovedLayout.setCenter(tegneOmråde);
		tegneOmråde.setWidth(WIDTH-100);
		tegneOmråde.setHeight(HEIGHT-100);
		hovedLayout.setBottom(kontrollPanel);
		kontrollPanel.getChildren().addAll(knappePanel,varPanel);

		Button tegnPåNytt = new Button("Tegn");
		tegnPåNytt.setOnAction(e -> {
			tre.oppdater();
			tre.tegn(tegneOmråde);
		});
		knappePanel.getChildren().add(tegnPåNytt);
		vinkel.setUnitIncrement(0.1);
		vinkel.setMax(Math.PI);
		vinkel.setMin(0);
		VINKEL_VEKST = vinkel.valueProperty();
		lengde.setUnitIncrement(5);
		lengde.setMin(5);
		lengde.setMax(200);
		INITIELL_LENGDE = lengde.valueProperty();
		varPanel.getChildren().addAll(vinkel,lengde);


	}
	/**
	 * "Hjelpemetode" for å starte tegning av treet
	 */
	private void tegnTre()
	{
		tegnGrein((WIDTH-100)/2.0,HEIGHT-100,INITIELL_LENGDE.getValue(),0);
	}

	private void tegnGrein(double x0, double y0, double length, double vinkel)
	{
		if(length <= MIN_LEN){
			return;
		}
		// Minus da treet vokser i negativ y-retning
		double nyX = x0-length*cache.getSinVinkel(vinkel);
		double nyY = y0-length*cache.getCosVinkel(vinkel);

		grafikk.strokeLine(x0, y0, nyX,nyY);

		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel+VINKEL_VEKST.getValue());
		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel-VINKEL_VEKST.getValue());
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
