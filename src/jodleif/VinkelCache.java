package jodleif;

import java.util.HashMap;

/**
 * Created by Jo Øivind Gjernes on 03.02.2016.
 */
public class VinkelCache
{
	private final HashMap<Double, Double> cosCache = new HashMap<>();
	private final HashMap<Double, Double> sinCache = new HashMap<>();
	private static int miss = 0;
	private static int hit = 0;

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

	public double getSinVinkel(double radianer)
	{
		if(sinCache.containsKey(radianer)){
			return sinCache.get(radianer);
		} else {
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
