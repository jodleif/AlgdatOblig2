package AlgdatOblig2.Logikk;

import AlgdatOblig2.Main;
import AlgdatOblig2.Felles.VinkelCache;

import java.util.ArrayList;

/**
 * Created by Jo Øivind Gjernes og Magnus Poppe Wang on 03.02.2016.
 *
 * Treet.
 */
public class Tre implements Tegnbar
{
	private ArrayList<ArrayList<double[]>> punkterITre;
	private static final VinkelCache cache = new VinkelCache();

	private double VEKSTFAKTOR = 0.7;
	private double VINKEL_VEKST ;
	private double INITIELL_LENGDE;
	private int nivåer;

	/**
	 * Konstruktør for "Tre-generator"
	 * @param nivåer antall nivåer av treet
	 * @param initiellLengde lengde på første linje
	 * @param vinkelVekst endring i vinkel per "rekursjon"
	 * @param lengdeVekst endring i lengde per rekursjon
	 */
	public Tre(int nivåer, double initiellLengde, double vinkelVekst, double lengdeVekst)
	{
		this.nivåer = nivåer;
		INITIELL_LENGDE = initiellLengde;
		VINKEL_VEKST = vinkelVekst;
		VEKSTFAKTOR = lengdeVekst;
		genererPunkter();
	}

	/**
	 * Hjelpemetode for å starte rekursjon
	 */
	private void genererPunkter()
	{
		punkterITre = new ArrayList<>();
		for(int i=0;i<nivåer;++i) punkterITre.add(new ArrayList<>());

		tegnGrein((Main.WIDTH)/2.0,Main.HEIGHT,INITIELL_LENGDE,0,0);
	}

	/**
	 * Rekursjonsfunksjon for å tegne treet
	 * @param x0 initiell x-pos
	 * @param y0 initiell y-pos
	 * @param lengde lengde på linjestykke
	 * @param vinkel vinkel på linje som skal tegnes
	 * @param nivå hvilket nivå rekursjonen er på
	 */
	private void tegnGrein(double x0, double y0, double lengde, double vinkel, int nivå)
	{
		if(nivå >= nivåer){
			return;
		}
		// Minus da treet vokser i negativ y-retning
		double nyX = x0-lengde*cache.getSinVinkel(vinkel);
		double nyY = y0-lengde*cache.getCosVinkel(vinkel);

		punkterITre.get(nivå).add(new double[] {x0,y0,nyX,nyY});

		tegnGrein(nyX,nyY, lengde*VEKSTFAKTOR, vinkel+VINKEL_VEKST, nivå+1);
		tegnGrein(nyX,nyY, lengde*VEKSTFAKTOR, vinkel-VINKEL_VEKST, nivå+1);
	}

	/**
	 * Typen objekt dette er
	 * @return linje
	 */
	public BasisObjekt getBasisObjekt()
	{
		return BasisObjekt.Linje;
	}


	@Override
	public ArrayList<ArrayList<double[]>> getPunkter()
	{
		return punkterITre;
	}

	@Override
	public int getHøyde()
	{
		return punkterITre.size();
	}
}
