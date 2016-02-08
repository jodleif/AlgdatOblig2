package AlgdatOblig2.Felles;

import java.util.HashMap;

/**
 * Created by Jo Øivind Gjernes og Magnus Poppe Wang on 03.02.2016.
 *
 * Hurtiglager for utregning av cosinus og sinus
 *
 * Om dette er en forbedring over å bare regne ut verdier, har vi ikke undersøkt.
 */
public class VinkelCache
{
	private final HashMap<Double, Double> cosCache = new HashMap<>();
	private final HashMap<Double, Double> sinCache = new HashMap<>();
	private static int miss = 0;
	private static int hit = 0;

	/**
	 * Sjekker om en vinkel har blitt beregnet, hvis ikke
	 * beregnes den og legges i cosCache
	 * @param radianer radianer for vinkel
	 * @return cosinusverdi for @param radianer
	 */
	public double getCosVinkel(double radianer)
	{
		if(cosCache.containsKey(radianer)){
			++hit;
			return cosCache.get(radianer);
		} else {
			++miss;
			cosCache.put(radianer, Math.cos(radianer));
			return cosCache.get(radianer);
		}
	}

	/**
	 * Sjekker om en vinkel har blitt beregnet, hvis ikke
	 * beregnes den og legges i sinCache
	 * @param radianer radianer for vinkel
	 * @return sinusverdi for @param radianer
	 */
	public double getSinVinkel(double radianer)
	{
		if(sinCache.containsKey(radianer)){
			++hit;
			return sinCache.get(radianer);
		} else {
			++miss;
			sinCache.put(radianer, Math.sin(radianer));
			return sinCache.get(radianer);
		}
	}

	@Override
	public String toString()
	{
		return "VinkelCache: \nHits: " + hit + "\nMisses: " + miss;
	}
}
