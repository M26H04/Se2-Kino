package de.uni_hamburg.informatik.swt.se2.kino.ui.beobachter;

import java.util.HashSet;

/**
 * Abstrakte Klasse für beobachtbare Objekte
 */
public abstract class Beobachtbar 
{
	//Set von Beobachtern
	protected HashSet<Beobachter> _beobachter;
	
	/**
	 * Fügt beobachter dem Set hinzu
	 * @param beobachter Beobachter der hinzugefügt werden soll
	 */
	public void registriereBeobachter(Beobachter beobachter)
	{
		_beobachter.add(beobachter);
	}
	
	/**
	 * Entfernt Beobachter aus dem Set
	 * @param beobachter Beobachter der entfernt werden soll
	 */
	public void entferneBeobachter(Beobachter beobachter)
	{
		_beobachter.remove(beobachter);
	}
	
	/**
	 * Meldet Änderung an Beobachter eines Objektes
	 */
	public void meldeAenderung()
	{
		for (Beobachter beobachter : _beobachter)
		{
			beobachter.reagiereAufAenderung(this);
		}
	}
}
