package jodleif;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jodleif.Logikk.Sierpinsky;
import jodleif.Logikk.Tegnbar;
import jodleif.Logikk.Tre;
import jodleif.Render.Intro;
import jodleif.Render.Plotter;

public class Main extends Application
{
	private final BorderPane hovedLayout = new BorderPane();
	private Canvas tegneOmråde = new Canvas();
	public final static double WIDTH = 800;
	public final static double HEIGHT = 600;
	private final VBox kontrollPanel = new VBox();
	private final HBox knappePanel = new HBox();
	private final HBox varPanel = new HBox();

	private final Label vinkelLabel = new Label("Vinkel");
	private final Label lengdeLabel = new Label("Initiell lengde");
	private final Label nivåLabel = new Label("Rekursjon");
	private final Label lengdeVekstLabel = new Label("Vekstfaktor");
	private final Slider vinkel = new Slider();
	private final Slider lengde = new Slider();
	private final Slider nivåer = new Slider();
	private final Slider lengdeVekst = new Slider();
	private Tegnbar tre;
	private Plotter plotter = new Plotter();
	private ToggleGroup valgGruppe;
	private ToggleButton tglSierp;
	private ToggleButton tglTre;

	// VARIABLER FOR TEGNING AV TREET
	private static double VEKSTFAKTOR = 0.7;
	private static double MIN_LEN = 2.0;
	private static DoubleProperty VINKEL_VEKST ;
	private static DoubleProperty INITIELL_LENGDE;
	private static DoubleProperty NIVÅ;
	private static DoubleProperty LENGDE_VEKST;
	static int nivå=0;


	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Scene scene = new Scene(hovedLayout, WIDTH, HEIGHT+50);

		settOppGui();
		opprettSlidere();

		hovedLayout.setCenter(new Intro().tegnBokstaver());

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

		Button viskUt = new Button("Visk ut");
		Button tegnPåNytt = new Button("Tegn");
		knappePanel.getChildren().addAll(tegnPåNytt, viskUt);

		// Kople knappen til metoden tegnTre()
		tegnPåNytt.setOnAction(e -> tegnTre());
		// Visk ut nuller ut canvas
		viskUt.setOnAction(e-> hovedLayout.setCenter(null));


		valgGruppe = new ToggleGroup();
		tglSierp = new ToggleButton("Sierpinsky");
		tglSierp.setToggleGroup(valgGruppe);
		tglSierp.setOnAction(e->justerForSierpinsky());
		tglTre = new ToggleButton("Tre");
		tglTre.setToggleGroup(valgGruppe);
		tglTre.setOnAction(e->justerForTre());
		valgGruppe.selectToggle(tglTre);
		knappePanel.getChildren().addAll(tglSierp, tglTre);


	}

	/**
	 * Opprett slidere for justering av parametere for rekursjon
	 */
	private void opprettSlidere()
	{
		vinkel.setBlockIncrement(0.1);
		vinkel.setMax(Math.PI);
		vinkel.setMin(0);
		vinkel.showTickLabelsProperty().setValue(true);
		vinkel.showTickMarksProperty().setValue(true);
		vinkel.setMajorTickUnit(Math.PI/4.0);
		VINKEL_VEKST = vinkel.valueProperty();

		lengde.setBlockIncrement(10);
		lengde.setMajorTickUnit(50);
		lengde.setMin(0);
		lengde.setMax(200);
		lengde.showTickLabelsProperty().setValue(true);
		lengde.showTickMarksProperty().setValue(true);
		INITIELL_LENGDE = lengde.valueProperty();

		nivåer.setBlockIncrement(1.0);
		nivåer.setMajorTickUnit(4);
		nivåer.setMin(1);
		nivåer.setMax(17);
		nivåer.showTickMarksProperty().setValue(true);
		nivåer.showTickLabelsProperty().setValue(true);
		NIVÅ = nivåer.valueProperty();

		lengdeVekst.setBlockIncrement(0.1);
		lengdeVekst.setMajorTickUnit(0.1);
		lengdeVekst.setMin(0.1);
		lengdeVekst.setMax(0.9);
		lengdeVekst.showTickMarksProperty().setValue(true);
		lengdeVekst.showTickLabelsProperty().setValue(true);
		LENGDE_VEKST = lengdeVekst.valueProperty();

		varPanel.getChildren().addAll(vinkelLabel,vinkel,lengdeLabel,lengde, nivåLabel, nivåer, lengdeVekstLabel, lengdeVekst);
	}

	private void justerForSierpinsky()
	{
		varPanel.getChildren().clear();
		varPanel.getChildren().addAll(lengdeLabel,lengde,nivåLabel,nivåer);
		nivåer.setMin(1.0);
		nivåer.setMax(9.0);
		lengde.setMin(0);
		lengde.setMax(1000);
		lengde.setMajorTickUnit(200);
	}

	private void justerForTre()
	{
		varPanel.getChildren().clear();
		varPanel.getChildren().addAll(vinkelLabel,vinkel,lengdeLabel,lengde, nivåLabel, nivåer, lengdeVekstLabel, lengdeVekst);
		lengde.setMin(0);
		lengde.setMax(200);
		lengde.setMajorTickUnit(50);
		nivåer.setMin(1);
		nivåer.setMax(17);
	}

	/**
	 * Funksjon for å tegne tre!
	 */
	private void tegnTre()
	{
		if(valgGruppe.getSelectedToggle()==tglSierp) {
			tre = new Sierpinsky((int)NIVÅ.get(), INITIELL_LENGDE.get());
		} else {
			tre = new Tre((int)NIVÅ.get(), INITIELL_LENGDE.get(), VINKEL_VEKST.get(), LENGDE_VEKST.get());
		}
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
