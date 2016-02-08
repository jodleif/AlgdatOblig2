package jodleif.Logikk;

import jodleif.Main;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes og Magnus Poppe Wang on 05.02.2016.
 *
 * Sierpinsky triangel
 */
public class Sierpinsky implements Tegnbar
{
	ArrayList<ArrayList<double[]>> triangler;

	/**
	 * Lage sierpinsky triangel.
	 * Punkter genereres når konstruktøren kalles
	 * @param nivåer antall "nivåer" av triangler
	 * @param basisLengde Lengde på grunnlinjen til trekanten
	 */
	public Sierpinsky(int nivåer, double basisLengde)
	{
		triangler = new ArrayList<>();
		tegn(nivåer, basisLengde);
	}

	/**
	 * Hjelpemetode for å tegne trekant
	 * @param nivåer antall nivåer med rekursjon
	 * @param basisLengde lengden på grunnlinjen
	 */
	private void tegn(int nivåer, double basisLengde)
	{
		triangler.add(new ArrayList<>());
		double startx = Main.WIDTH / 2 + basisLengde/2;
		double starty = Main.HEIGHT;
		rekursivTegn(startx,starty,basisLengde,nivåer);
	}

	/**
	 * Rekursiv funksjon for å tegne en sierpinsky triangle
	 * @param x0 startsposisjon hjørne trekant
	 * @param y0 startsposisjon hjørne trekant
	 * @param lengde lengde på grunnlinjen
	 * @param nivå brukes for å holde kontroll på "dybden" av rekursjonen. Nivå 0 -> bunn
	 */
	private void rekursivTegn(double x0, double y0, double lengde, int nivå)
	{
		if(nivå<=0) { // BASECASE
			triangler.get(0).add(new double[]{
				x0,y0,
				x0-lengde/2, y0-lengde,
				x0-lengde,y0
			});
			return;
		}

		// Rekursjon
		rekursivTegn(x0,y0,
			lengde/2,nivå-1);
		rekursivTegn(x0-lengde/4,y0-lengde/2,
			lengde/2,nivå-1);
		rekursivTegn(x0-lengde/2,y0,
			lengde/2,nivå-1);
	}

	@Override
	public ArrayList<ArrayList<double[]>> getPunkter()
	{
		return triangler;
	}

	@Override
	public int getHøyde()
	{
		return 0;
	}

	@Override
	public BasisObjekt getBasisObjekt()
	{
		return BasisObjekt.Triangel;
	}
}
