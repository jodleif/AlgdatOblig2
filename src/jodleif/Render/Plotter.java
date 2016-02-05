package jodleif.Render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import jodleif.Logikk.Tegnbar;
import jodleif.Main;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by Jo Øivind Gjernes on 04.02.2016.
 *
 * Klasse for å plotte et "tegnbart" interface basert på hvilket type BasisObjekt som skal tegnes
 *
 * Format for Linjer:
 * ArrayList<ArrayList<double[]>>
 *         med en layout: double {x0,y0,x1,y1};
 * Format for Triangler:
 * ArrayList<ArrayList<double[]>>
 *         med layout: double {
 *                 x0,y0
 *                 x1,y1
 *                 x2,y2
 *         }
 *
 *
 *
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
		switch(tegnbar.getBasisObjekt()){
			case Linje:
				return tegnLinje(tegnbar);
			case Triangel:
				return tegnTriangler(tegnbar);
			default:
				return new Canvas();
		}
	}
	private Canvas tegnTriangler(Tegnbar tegnbar)
	{
		Canvas nyttTegneområde = lagNyttTegneområde();
		GraphicsContext grafikkKontekst = nyttTegneområde.getGraphicsContext2D();
		for(ArrayList<double[]> lister : tegnbar.getPunkter())
		{
			for(double[] triangel : lister){

				/**
				 * Tegne en trekant ved hjelp av tre punkter
				 * (ett i hvert hjørne)
				 */
				tegnTriangel(triangel, grafikkKontekst);
			}
		}
		return nyttTegneområde;
	}

	private Canvas tegnLinje(Tegnbar tegnbar)
	{
		Canvas nyttTegneområde = lagNyttTegneområde();
		GraphicsContext grafikk = nyttTegneområde.getGraphicsContext2D();
		int høyde = tegnbar.getHøyde();
		int teller = 1;
		for(ArrayList<double[]> punkter : tegnbar.getPunkter())
		{
			tegnNivå(punkter, grafikk, (høyde-teller));
			++teller;
		}
		return nyttTegneområde;
	}
/*
	private Canvas tegnAnimerteTriangler(Tegnbar tegnbar)
	{
		Canvas nyttTegneområde = lagNyttTegneområde();
		GraphicsContext grafikkKontekst = nyttTegneområde.getGraphicsContext2D();
		ArrayList<double[]> punkter = tegnbar.getPunkter().get(0);
		ArrayList<DrawTriangles> taskListe = new ArrayList<>();
		int teller = 0;

		ArrayList<double[]> punktKopi = new ArrayList<>();
		for(double[] punkt : punkter)
		{
			++teller;
			punktKopi.add(punkt);
			// for hver gruppe med 25 trekanter
			if(teller%3==0)
			{
				// lag en tegneoppgave
				taskListe.add(new DrawTriangles(punktKopi, grafikkKontekst));
				// opprett en ny liste med trekanter
				punktKopi = new ArrayList<>();
			}
		}
		Timer timer = new Timer();

		for(int i=0;i<taskListe.size();++i)
		{
			timer.schedule(taskListe.get(i), i*10);
		}
		return nyttTegneområde;
	}*/

	private static void tegnTriangel(double[] triangel, GraphicsContext cnt)
	{
		cnt.fillPolygon(
			new double[]{triangel[0],triangel[2],triangel[4]},
			new double[]{triangel[1],triangel[3],triangel[5]},
			3
		);
	}

	private static Canvas lagNyttTegneområde()
	{
		Canvas nyttTegneområde = new Canvas();
		nyttTegneområde.setHeight(Main.HEIGHT);
		nyttTegneområde.setWidth(Main.WIDTH);
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

	class DrawTriangles extends TimerTask
	{
		protected ArrayList<double[]> punkter;
		protected GraphicsContext cnt;
		public DrawTriangles(ArrayList<double[]> trekanter, GraphicsContext cnt)
		{
			this.cnt = cnt;
			this.punkter = trekanter;
		}

		@Override
		public void run()
		{
			punkter.forEach(doubles -> cnt.fillPolygon(
				new double[]{ // x-punkter
					doubles[0],
					doubles[2],
					doubles[4]
				}, new double[] { // y-punkter
					doubles[1],
					doubles[3],
					doubles[5],
				},
				3
			));
		}

	}
}
