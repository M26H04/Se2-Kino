package de.uni_hamburg.informatik.swt.se2.kino.wertobjekte;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GeldbetragTest 
{
	@Test
    public final void testGeldbetrag()
    {
        Geldbetrag betrag = new Geldbetrag(100);
        assertEquals("1,00", betrag.getFormatiertenString());

        betrag = new Geldbetrag(0);
        assertEquals("0,00", betrag.getFormatiertenString());

        betrag = new Geldbetrag(99);
        assertEquals("0,99", betrag.getFormatiertenString());

        betrag = new Geldbetrag(101);
        assertEquals("1,01", betrag.getFormatiertenString());
        
        assertThrows(AssertionError.class, () -> {
        	Geldbetrag betrag1 = new Geldbetrag(-100);
        });
    }

    @Test
    public final void testEqualsHashcode()
    {
        Geldbetrag betrag1 = new Geldbetrag(100);
        Geldbetrag betrag2 = new Geldbetrag(100);
        assertEquals(betrag1, betrag2);
        assertEquals(betrag1.hashCode(), betrag2.hashCode());

        Geldbetrag betrag3 = new Geldbetrag(101);
        assertNotEquals(betrag1, betrag3);
        assertNotEquals(betrag1.hashCode(), betrag3.hashCode());

        Geldbetrag betrag4 = new Geldbetrag(1000);
        assertNotEquals(betrag1, betrag4);
        assertNotEquals(betrag1.hashCode(), betrag4.hashCode());
    }
    
    @Test
    public final void testAusEuroCent()
    {
    	Geldbetrag betrag = Geldbetrag.ausEurocent(100);
    	assertEquals("1,00", betrag.getFormatiertenString());
    	assertThrows(AssertionError.class, () -> {
            Geldbetrag.ausEurocent(-200);
        });	
    }
    
    @Test
    public final void testAusEuroUndCent()
    {
    	Geldbetrag betrag = Geldbetrag.ausEuroUndCent(2, 50);
    	assertEquals("2,50", betrag.getFormatiertenString());
    	assertThrows(AssertionError.class, () -> {
    		Geldbetrag.ausEuroUndCent(-1, 50);
        });
    	assertThrows(AssertionError.class, () -> {
    		Geldbetrag.ausEuroUndCent(2, -50);
        });
    	assertThrows(AssertionError.class, () -> {
    		Geldbetrag.ausEuroUndCent(2, 150);
        });
    	assertThrows(AssertionError.class, () -> {
    		Geldbetrag.ausEuroUndCent(Integer.MAX_VALUE, 50);
        });
    }
    
    @Test 
    public final void testAusString()
    {
    	Geldbetrag betrag = Geldbetrag.ausString("1,50");
    	assertEquals("1,50", betrag.getFormatiertenString());
    	betrag = Geldbetrag.ausString("100,50");
    	assertEquals("100,50", betrag.getFormatiertenString());
    	assertThrows(AssertionError.class, () -> {
    		Geldbetrag.ausString("10,500");
        });
    	assertThrows(AssertionError.class, () -> {
    		Geldbetrag.ausString(null);
        });
    	assertThrows(AssertionError.class, () -> {
    		Geldbetrag.ausString("10,5");
        });
    	assertEquals("100,50", betrag.getFormatiertenString());
    }
    
    @Test
    public final void testIstGueltigerGeldbetragString()
    {
    	assertTrue(Geldbetrag.istGueltigerGeldbetragString("100,30"));
    	assertFalse(Geldbetrag.istGueltigerGeldbetragString(null));
    	assertFalse(Geldbetrag.istGueltigerGeldbetragString("Geldbetrag"));
    	assertFalse(Geldbetrag.istGueltigerGeldbetragString("20,1234"));
    	assertFalse(Geldbetrag.istGueltigerGeldbetragString("10,3"));
    	try
    	{
    		assertFalse(Geldbetrag.istGueltigerGeldbetragString("100000000000000000 ,30"));
    	}
    	catch(NumberFormatException e)
    	{
    		
    	}
    	
    }
    
    @Test
    public final void testGetCentBetrag()
    {
    	Geldbetrag betrag = new Geldbetrag(1234);
    	assertEquals(1234, betrag.getCentBetrag());
    	betrag = new Geldbetrag(0);
    	assertEquals(0, betrag.getCentBetrag());
    }
    
    
    @Test
    public final void testAddiere()
    {
        Geldbetrag betrag1 = new Geldbetrag(100);
        Geldbetrag betrag2 = new Geldbetrag(102);
        Geldbetrag max = new Geldbetrag(Integer.MAX_VALUE);
        Geldbetrag betrag3 = betrag1.addiere(betrag2);
        assertEquals("2,02", betrag3.getFormatiertenString());
        assertThrows(AssertionError.class, () -> {
            betrag1.multipliziere(Integer.MAX_VALUE);
        });	
        assertEquals("2,02", betrag3.getFormatiertenString());
    }
    
    @Test
    public final void testKannAddieren()
    {
    	Geldbetrag betrag1 = new Geldbetrag(5000);
    	Geldbetrag betrag2 = new Geldbetrag(3213);
    	Geldbetrag betrag3 = new Geldbetrag(Integer.MAX_VALUE);
    	assertTrue(betrag1.kannAddieren(betrag2));
    	assertFalse(betrag1.kannAddieren(null));
    	assertFalse(betrag1.kannAddieren(betrag3));
    }
    
    @Test
    public final void testSubtrahiere()
    {
    	Geldbetrag betrag1 = new Geldbetrag(100);
        Geldbetrag betrag2 = new Geldbetrag(102);
        Geldbetrag betrag3 = betrag2.subtrahiere(betrag1);
        assertEquals("0,02", betrag3.getFormatiertenString());
        assertThrows(AssertionError.class, () -> {
        	betrag1.subtrahiere(betrag2);
        });	
        assertEquals("0,02", betrag3.getFormatiertenString());
    }
    
    @Test
    public final void testKannSubtrahieren()
    {
    	Geldbetrag betrag1 = new Geldbetrag(5000);
    	Geldbetrag betrag2 = new Geldbetrag(3213);
    	assertTrue(betrag1.kannSubtrahieren(betrag2));
    	assertFalse(betrag1.kannSubtrahieren(null));
    	assertFalse(betrag2.kannSubtrahieren(betrag1));
    }
    
    @Test
    public final void testMultipliziere()
    {
    	Geldbetrag betrag1 = new Geldbetrag(100);
        Geldbetrag betrag3 = betrag1.multipliziere(3);
        assertEquals("3,00", betrag3.getFormatiertenString());
        assertThrows(AssertionError.class, () -> {
        	betrag1.multipliziere(Integer.MAX_VALUE);
        });
        assertThrows(AssertionError.class, () -> {
        	betrag1.multipliziere(-2);
        });
        assertEquals("3,00", betrag3.getFormatiertenString());
        betrag3 = betrag1.multipliziere(0);
        assertEquals("0,00", betrag3.getFormatiertenString());
    }
    
    @Test
    public final void testKannMultiplizieren()
    {
    	Geldbetrag betrag1 = new Geldbetrag(50);
    	assertTrue(betrag1.kannMultiplizieren(6));
    	assertFalse(betrag1.kannMultiplizieren(-1));
    	assertTrue(betrag1.kannMultiplizieren(0));
    	assertFalse(betrag1.kannMultiplizieren(Integer.MAX_VALUE));
    }
    
}
