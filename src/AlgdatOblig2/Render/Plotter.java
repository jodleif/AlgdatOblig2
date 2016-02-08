package AlgdatOblig2.Render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import AlgdatOblig2.Logikk.Tegnbar;
import AlgdatOblig2.Main;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes og Magnus Poppe Wang on 04.02.2016.
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
	 *
	 * @param nivå Arraylist med double[] verider som er formatert slik: double[0] = x0, double[1] = y0, double[2] = x1, double[3] = y1
	 * @param grafikk graphicscontext fra Canvas man skal tegne på
	 * @param avstandTilTopp antall nivåer man er unna toppen. (det øverste nivået har avstandtilTopp == 0, brukes kun for fargelegging)
	 */
	public static void tegnNivå(ArrayList<double[]> nivå, GraphicsContext grafikk, int avstandTilTopp)
	{
		for(double[] linje : nivå){
			// linje[] -> {x0,y0,x1,y1}
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

	/**
	 * Tegn opp et triangelbasert Tegnbar objekt
	 * @param tegnbar Objektet som skal tegnes
	 * @return Canvas med figuren på
	 */
	private Canvas tegnTriangler(Tegnbar tegnbar)
	{
		Canvas nyttTegneområde = lagNyttTegneområde();
		GraphicsContext grafikkKontekst = nyttTegneområde.getGraphicsContext2D();
		for(ArrayList<double[]> triangler : tegnbar.getPunkter())
		{
			for(double[] triangel : triangler){

				/**
				 * Tegne en trekant ved hjelp av tre punkter
				 * (ett i hvert hjørne)
				 */
				tegnTriangel(triangel, grafikkKontekst);
			}
		}
		return nyttTegneområde;
	}


	/**
	 * Tegn et tegnbart objekt som består av linjer
	 * @param tegnbar tegnbart objekt
	 * @return (ferdig tegnet) canvas
	 */
	private Canvas tegnLinje(Tegnbar tegnbar)
	{
		Canvas nyttTegneområde = lagNyttTegneområde();
		GraphicsContext grafikk = nyttTegneområde.getGraphicsContext2D();
		int høyde = tegnbar.getHøyde();
		for(ArrayList<double[]> punkter : tegnbar.getPunkter())
		{
			tegnNivå(punkter, grafikk, --høyde);
		}
		return nyttTegneområde;
	}

	/**
	 * Tegn et triangel basert på koordinater gitt:
	 * double[] {
	 *         x0,y0,
	 *         x1,y1,
	 *         x2,y2
	 * }
	 * @param triangel 3 x,y koordinater til hjørner i triangel
	 * @param cnt grafisk kontekst fra canvaset som skal vises
	 */
	private static void tegnTriangel(double[] triangel, GraphicsContext cnt)
	{
		cnt.fillPolygon(
			new double[]{triangel[0],triangel[2],triangel[4]},
			new double[]{triangel[1],triangel[3],triangel[5]},
			3
		);
	}

	/**
	 * Opprette en tom Canvas med egenskaper som passer til hovedvinduet
	 * @return ny tom Canvas
	 */
	protected static Canvas lagNyttTegneområde()
	{
		Canvas nyttTegneområde = new Canvas();
		nyttTegneområde.setHeight(Main.HEIGHT);
		nyttTegneområde.setWidth(Main.WIDTH);
		return nyttTegneområde;
	}
}
