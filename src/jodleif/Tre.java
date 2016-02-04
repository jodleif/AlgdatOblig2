package jodleif;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jo Øivind Gjernes on 03.02.2016.
 *
 * Treet.
 */
public class Tre
{
	private ArrayList<ArrayList<double[]>> greiner;
	private static final VinkelCache cache = new VinkelCache();

	private double VEKSTFAKTOR = 0.7;
	private double VINKEL_VEKST ;
	private double INITIELL_LENGDE;
	private int nivåer;
	private int øktOppløsning;

	public Tre()
	{
		nivåer = 10;
		VINKEL_VEKST = 0.33;
		INITIELL_LENGDE = 150;
		øktOppløsning = 5;
	}
	public Tre(int nivåer, double initiell_lengde, double vinkel_vekst)
	{
		this.nivåer = nivåer;
		INITIELL_LENGDE = initiell_lengde;
		VINKEL_VEKST = vinkel_vekst;
		øktOppløsning = 5;
	}

	private void genererPunkter()
	{
		greiner = new ArrayList<>();
		for(int i=0;i<nivåer;++i) greiner.add(new ArrayList<>());

		tegnGrein((Main.WIDTH-100)/2.0,Main.HEIGHT-100,INITIELL_LENGDE,0,nivåer);
		Collections.reverse(greiner);
	}
	public void oppdater()
	{
		genererPunkter();
		this.greiner = Hjelpemetoder.økMengdenPunkter(greiner, øktOppløsning);
	}

	private void tegnGrein(double x0, double y0, double length, double vinkel, int nivå)
	{
		if(nivå <= 0){
			return;
		}
		// Minus da treet vokser i negativ y-retning
		double nyX = x0-length*cache.getSinVinkel(vinkel);
		double nyY = y0-length*cache.getCosVinkel(vinkel);

		greiner.get(nivå-1).add(new double[] {x0,y0,nyX,nyY});

		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel+VINKEL_VEKST, nivå-1);
		tegnGrein(nyX,nyY, length*VEKSTFAKTOR, vinkel-VINKEL_VEKST, nivå-1);
	}


	public static void tegnNivå(ArrayList<double[]> punkter, GraphicsContext grafikk, int avstandTilTopp)
	{
		switch(avstandTilTopp) {
			case 0:
				grafikk.setStroke(Color.LIGHTPINK);
				break;
			case 1:
				grafikk.setStroke(Color.LIGHTGREEN);
				break;
			case 2:
			case 3:
				grafikk.setStroke(Color.GREEN);
				break;
			default:
				grafikk.setStroke(Color.BLACK);
		}

		for(double[] linje : punkter){
			grafikk.strokeLine(linje[0],linje[1],linje[2],linje[3]);
		}
	}

	public Canvas tegn()
	{
		Timer timer = new Timer();
		int i = 0;
		Canvas canvas = new Canvas();
		canvas.setHeight(Main.HEIGHT-100);
		canvas.setWidth(Main.WIDTH);
		for(ArrayList<double[]> punkter : greiner){
			timer.schedule(new TimerTaskJodl(nivåer-i/øktOppløsning-1, punkter, canvas.getGraphicsContext2D())
			, 100*i);
			i++;
		}
		System.out.println(cache);
		return canvas;
	}

	class TimerTaskJodl extends TimerTask
	{
		protected int avstandTilTopp;
		protected ArrayList<double[]> punkter;
		protected GraphicsContext cont;
		public TimerTaskJodl(int avstandTilTopp, ArrayList<double[]> punkter, GraphicsContext cont)
		{
			this.avstandTilTopp = avstandTilTopp;
			this.punkter = punkter;
			this.cont = cont;
		}
		@Override
		public void run()
		{
			tegnNivå(punkter, cont, avstandTilTopp);
		}
	}
}
