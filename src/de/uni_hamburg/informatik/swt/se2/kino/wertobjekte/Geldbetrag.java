package de.uni_hamburg.informatik.swt.se2.kino.wertobjekte;

/**
 * Ein Geldbetrag in Euro, dargestellt als Wertobjekt.
 * 
 * @author SE2-Team
 * @version SoSe 2025
 */
public final class Geldbetrag implements Comparable<Geldbetrag>
{
    private final int _euroAnteil;
    private final int _centAnteil;

    /**
     * Wählt einen Geldbetrag aus.
     * 
     * @param eurocent Der Betrag in Eurocent
     * 
     * @require eurocent >= 0
     */
    Geldbetrag(int eurocent)
    {
        assert eurocent >= 0 : "Vorbedingung verletzt: eurocent >= 0";
        _euroAnteil = eurocent / 100;
        _centAnteil = eurocent % 100;
    }

    /**
     * Erzeugt einen Geldbetrag aus Eurocent.
     * 
     * @param eurocent Der Betrag in Eurocent
     * @return Ein neuer Geldbetrag
     * 
     * @require eurocent >= 0
     * @ensure result != null
     */
    public static Geldbetrag ausEurocent(int eurocent)
    {
        assert eurocent >= 0 : "Vorbedingung verletzt: eurocent >= 0";
        return new Geldbetrag(eurocent);
    }

    /**
     * Erzeugt einen Geldbetrag aus Euro und Cent.
     * 
     * @param euro Der Euro-Anteil
     * @param cent Der Cent-Anteil
     * @return Ein neuer Geldbetrag
     * 
     * @require euro >= 0
     * @require cent >= 0 && cent < 100
     * @ensure result != null
     */
    public static Geldbetrag ausEuroUndCent(int euro, int cent)
    {
        assert euro >= 0 : "Vorbedingung verletzt: euro >= 0";
        assert cent >= 0 && cent < 100 : "Vorbedingung verletzt: cent >= 0 && cent < 100";
        assert euro <= Integer.MAX_VALUE / 100 : "Vorbedingung verletzt: Betrag zu groß";
        
        return new Geldbetrag(euro * 100 + cent);
    }

    /**
     * Erzeugt einen Geldbetrag aus einem String im Format "EE,CC".
     * 
     * @param betragString Der String im Format "EE,CC"
     * @return Ein neuer Geldbetrag
     * 
     * @require betragString != null
     * @require betragString matches "\\d+,\\d{2}"
     * @ensure result != null
     */
    public static Geldbetrag ausString(String betragString)
    {
        assert betragString != null : "Vorbedingung verletzt: betragString != null";
        assert istGueltigerGeldbetragString(betragString) : "Vorbedingung verletzt: Ungültiges Format";        
        String[] teile = betragString.split(",");
        int euro = Integer.parseInt(teile[0]);
        int cent = Integer.parseInt(teile[1]);        
        return ausEuroUndCent(euro, cent);
    }

    /**
     * Prüft, ob ein String ein gültiger Geldbetrag ist.
     * 
     * @param betragString Der zu prüfende String
     * @return true, wenn der String ein gültiger Geldbetrag ist
     */
    public static boolean istGueltigerGeldbetragString(String betragString)
    {
        if (betragString == null) 
        {
        	return false;
        }
        if (!betragString.matches("\\d+,\\d{2}"))
        {
        	return false;
        }
        try 
        {
            String[] teile = betragString.split(",");
            int euro = Integer.parseInt(teile[0]);
            int cent = Integer.parseInt(teile[1]);           
            if (euro > Integer.MAX_VALUE / 100) 
            {
            	return false;
            }
            if (cent >= 100) 
            {
            	return false;           
            }
            return true;
        } 
        catch (NumberFormatException e) 
        {
            return false;
        }
    }
    
    public int getCentBetrag()
    {
    	return _euroAnteil * 100 + _centAnteil;
    }

    /**
     * Addiert zwei Geldbeträge.
     * 
     * @param anderer Der andere Geldbetrag
     * @return Die Summe der beiden Geldbeträge
     * 
     * @require anderer != null
     * @require this.getEurocent() + anderer.getEurocent() <= Integer.MAX_VALUE
     * @ensure result != null
     */
    public Geldbetrag addiere(Geldbetrag anderer)
    {
        assert anderer != null : "Vorbedingung verletzt: anderer != null";
        assert kannAddieren(anderer) : "Vorbedingung verletzt: Summe zu groß";
        return new Geldbetrag(getCentBetrag() + anderer.getCentBetrag());
    }

    /**
     * Prüft, ob eine Addition möglich ist ohne Überlauf.
     * 
     * @param anderer Der andere Geldbetrag
     * @return true, wenn die Addition möglich ist
     */
    public boolean kannAddieren(Geldbetrag anderer)
    {
        if (anderer == null) 
        {	
        	return false;
        }
        return (long) getCentBetrag() + (long)anderer.getCentBetrag() <= Integer.MAX_VALUE;
    }

    /**
     * Subtrahiert einen Geldbetrag von diesem.
     * 
     * @param anderer Der abzuziehende Geldbetrag
     * @return Die Differenz der beiden Geldbeträge
     * 
     * @require anderer != null
     * @require this.getEurocent() >= anderer.getEurocent()
     * @ensure result != null
     */
    public Geldbetrag subtrahiere(Geldbetrag anderer)
    {
        assert anderer != null : "Vorbedingung verletzt: anderer != null";
        assert kannSubtrahieren(anderer) : "Vorbedingung verletzt: Ergebnis wäre negativ";
        
        return new Geldbetrag(getCentBetrag() - anderer.getCentBetrag());
    }

    /**
     * Prüft, ob eine Subtraktion möglich ist (Ergebnis >= 0).
     * 
     * @param anderer Der andere Geldbetrag
     * @return true, wenn die Subtraktion möglich ist
     */
    public boolean kannSubtrahieren(Geldbetrag anderer)
    {
        if (anderer == null) 
        {
        	return false;
        }
        return getCentBetrag() >= anderer.getCentBetrag();
    }

    /**
     * Multipliziert den Geldbetrag mit einer Zahl.
     * 
     * @param faktor Der Multiplikationsfaktor
     * @return Das Produkt
     * 
     * @require faktor >= 0
     * @require this.getEurocent() * faktor <= Integer.MAX_VALUE
     * @ensure result != null
     */
    public Geldbetrag multipliziere(int faktor)
    {
        assert faktor >= 0 : "Vorbedingung verletzt: faktor >= 0";
        assert kannMultiplizieren(faktor) : "Vorbedingung verletzt: Produkt zu groß";
        
        return new Geldbetrag(getCentBetrag() * faktor);
    }

    /**
     * Prüft, ob eine Multiplikation möglich ist ohne Überlauf.
     * 
     * @param faktor Der Multiplikationsfaktor
     * @return true, wenn die Multiplikation möglich ist
     */
    public boolean kannMultiplizieren(int faktor)
    {
        if (faktor < 0) 
        {
        	return false;
        }
        if (faktor == 0) 
        {
        	return true;
        }
        return getCentBetrag() <= Integer.MAX_VALUE / faktor;
    }

    /**
     * Gibt eine String-Repräsentation im Format "EE,CC" zurück.
     * 
     * @return Der formatierte String
     * @ensure result != null
     */
    public String getFormatiertenString()
    {
        return String.format("%d,%02d", _euroAnteil, _centAnteil);
    }

    @Override
    public String toString()
    {
        return getFormatiertenString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) 
        {
        	return true;
        }
        if (!(obj instanceof Geldbetrag)) 
        {
        	return false;
        }  
        Geldbetrag anderer = (Geldbetrag) obj;
        return getCentBetrag() == anderer.getCentBetrag();
    }

    @Override
    public int hashCode()
    {
        return Integer.hashCode(getCentBetrag());
    }

    @Override
    public int compareTo(Geldbetrag anderer)
    {
        assert anderer != null : "Vorbedingung verletzt: anderer != null";
        return Integer.compare(getCentBetrag(), anderer.getCentBetrag());
    }
}