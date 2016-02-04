package jodleif;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
	private final BorderPane hovedLayout = new BorderPane();
	private Canvas tegneOmråde = new Canvas();
	protected final static double WIDTH = 800;
	protected final static double HEIGHT = 600;
	private final VBox kontrollPanel = new VBox();
	private final HBox knappePanel = new HBox();
	private final HBox varPanel = new HBox();
	private final ScrollBar vinkel = new ScrollBar();
	private final ScrollBar lengde = new ScrollBar();
	private Tre tre;
	private Plotter plotter = new Plotter();

	// VARIABLER FOR TEGNING AV TREET
	private static double VEKSTFAKTOR = 0.7;
	private static double MIN_LEN = 2.0;
	private static DoubleProperty VINKEL_VEKST ;
	private static DoubleProperty INITIELL_LENGDE;
	static int nivå=0;


	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(hovedLayout, WIDTH, HEIGHT+50);

		settOppGui();

		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Her settes gui initialiseres og konfigureres de fleste GUI-objekter
	 */
	private void settOppGui()
	{
		hovedLayout.setCenter(tegneOmråde);
		hovedLayout.setBottom(kontrollPanel);
		kontrollPanel.getChildren().addAll(knappePanel,varPanel);

		Button tegnPåNytt = new Button("Tegn");
		tegnPåNytt.setOnAction(e -> tegnTre());

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
	 * Funksjon for å tegne tre!
	 */
	private void tegnTre()
	{
		tre = new Tre(18, INITIELL_LENGDE.get(), VINKEL_VEKST.get());
		hovedLayout.setCenter(plotter.tegn(tre));
	}

	/**
	 * Entrypoint
	 * @param args Kommandolinjeargumenter
	 */
	public static void main(String[] args)
	{
		launch(args);
		System.exit(0);
	}
}
