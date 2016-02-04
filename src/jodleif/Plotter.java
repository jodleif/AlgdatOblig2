package jodleif;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jo Øivind Gjernes on 04.02.2016.
 *
 * Klasse for å plotte et "tegnbart" interface
 */
public class Plotter
{
	/**
	 * Funksjon for å tegne et "nivå" (en arraylist med linjer) på et Canvas.
	 * @param punkter Arraylist med double[] verider som er formatert slik: double[0] = x0, double[1] = y0, double[2] = x1, double[3] = y1
	 * @param grafikk graphicscontext fra Canvas man skal tegne på
	 * @param avstandTilTopp antall nivåer man er unna toppen. (det øverste nivået har avstandtilTopp == 0, brukes kun for fargelegging)
	 */
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

	/**
	 * Offentlig metode som man kaller for å tegne et tegnbart objekt t
	 * @param tegnbar Tegnbart objekt
	 * @return Canvas med det tegnbare objektet tegnet på.
	 */
	public Canvas tegn(Tegnbar tegnbar)
	{
		Timer timer = new Timer();
		int i = 0;
		Canvas nyttTegneområde = new Canvas();
		nyttTegneområde.setHeight(Main.HEIGHT);
		nyttTegneområde.setWidth(Main.WIDTH);

		int høyde = tegnbar.getHøyde();

		for(ArrayList<double[]> punkter : tegnbar.getPunkter()){
			timer.schedule(new DrawTimer(høyde-i-1, punkter, nyttTegneområde.getGraphicsContext2D())
				, 100*i);
			i++;
		}
		return nyttTegneområde;
	}

	/**
	 * Indre klasse som brukes for å kunne planlegge utføring av "tegning" på canvas, i en timer.
 	 */
	class DrawTimer extends TimerTask
	{
		protected int avstandTilTopp;
		protected ArrayList<double[]> punkter;
		protected GraphicsContext cont;

		public DrawTimer(int avstandTilTopp, ArrayList<double[]> punkter, GraphicsContext cont)
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
