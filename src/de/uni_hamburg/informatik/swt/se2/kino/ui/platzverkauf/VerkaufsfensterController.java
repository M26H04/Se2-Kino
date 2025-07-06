package de.uni_hamburg.informatik.swt.se2.kino.ui.platzverkauf;

import de.uni_hamburg.informatik.swt.se2.kino.wertobjekte.Geldbetrag;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Der Controller für das Verkaufsfenster (Barzahlungsdialog).
 * Diese Klasse verwaltet die Geschäftslogik und die Interaktion
 * zwischen View und Model.
 * 
 * @author SE2-Team
 * @version SoSe 2025
 */
public class VerkaufsfensterController
{
    private VerkaufsfensterView _view;
    private Geldbetrag _preis;
    private boolean _bezahlungErfolgreich;

    /**
     * Initialisiert den Controller mit einer neuen View.
     * 
     * @param parent Das Elternfenster für den modalen Dialog
     */
    public VerkaufsfensterController(JFrame parent)
    {
        _view = new VerkaufsfensterView(parent);
        registriereUIAktionen();
    }

    /**
     * Registriert alle UI-Aktionen und Event-Listener.
     */
    private void registriereUIAktionen()
    {
        _view.getOkButton().addActionListener(e -> okGedrueckt());
        _view.getAbbrechenButton().addActionListener(e -> abbrechenGedrueckt());
        _view.getDialog().addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                abbrechenGedrueckt();
            }
        });
        _view.getGezahltField().getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                eingabeGeaendert();
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                eingabeGeaendert();
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                eingabeGeaendert();
            }
        });
    }

    /**
     * Gibt das UI-Dialog zurück.
     * 
     * @return Der JDialog
     */
    public JDialog getUIDialog()
    {
        return _view.getDialog();
    }

    /**
     * Aktualisiert das Fenster mit dem gegebenen Betrag.
     * 
     * @param betrag Der neue Betrag
     */
    public void aktualisiereFenster(Geldbetrag betrag)
    {
        _preis = betrag;
        _view.setzePreis(betrag);
        eingabeGeaendert();
    }

    /**
     * Setzt das Fenster zurück.
     */
    public void reset()
    {
        _view.leereEingabefeld();
        _view.setzeRestbetrag(Geldbetrag.ausEurocent(0));
        _view.setzeFehlenderBetrag(Geldbetrag.ausEurocent(0));
        _view.verbergeFehlermeldung();
        _view.setzeOkButtonAktiv(false);
    }

    /**
     * Zeigt den Dialog für den angegebenen Preis.
     * 
     * @param preis Der zu zahlende Preis
     * @return true, wenn die Bezahlung erfolgreich war, false bei Abbruch
     * 
     * @require preis != null
     */
    public boolean zeigeBarzahlung(Geldbetrag preis)
    {
        assert preis != null : "Vorbedingung verletzt: preis != null";
        _preis = preis;
        _bezahlungErfolgreich = false;
        reset();
        _view.setzePreis(preis);
        _view.setzeFehlenderBetrag(preis); 
        _view.zeigeDialog();
        return _bezahlungErfolgreich;
    }

    /**
     * Wird aufgerufen, wenn die Eingabe im Gezahlt-Feld geändert wird.
     * Validiert die Eingabe und aktualisiert die Anzeige entsprechend.
     */
    private void eingabeGeaendert()
    {
        String eingabe = _view.getGezahltText();
        _view.verbergeFehlermeldung();       
        if (eingabe.isEmpty())
        {
            _view.setzeFehlenderBetrag(_preis);
            _view.setzeRestbetrag(Geldbetrag.ausEurocent(0));
            _view.setzeOkButtonAktiv(false);
        }
        else if (Geldbetrag.istGueltigerGeldbetragString(eingabe))
        {
            try
            {
                Geldbetrag gezahlt = Geldbetrag.ausString(eingabe);
                
                if (gezahlt.compareTo(_preis) >= 0)
                {
                    Geldbetrag rueckgeld = gezahlt.subtrahiere(_preis);
                    _view.setzeFehlenderBetrag(Geldbetrag.ausEurocent(0));
                    _view.setzeRestbetrag(rueckgeld);
                    _view.setzeOkButtonAktiv(true);
                }
                else
                {
                    Geldbetrag fehlend = _preis.subtrahiere(gezahlt);
                    _view.setzeFehlenderBetrag(fehlend);
                    _view.setzeRestbetrag(Geldbetrag.ausEurocent(0));
                    _view.setzeOkButtonAktiv(false);
                }
            }
            catch (Exception e)
            {
                _view.zeigeFehlermeldung("Ungültige Eingabe!");
                _view.setzeFehlenderBetrag(_preis);
                _view.setzeRestbetrag(Geldbetrag.ausEurocent(0));
                _view.setzeOkButtonAktiv(false);
            }
        }
        else
        {
            _view.zeigeFehlermeldung("Bitte Betrag im Format XX,XX eingeben!");
            _view.setzeFehlenderBetrag(_preis);
            _view.setzeRestbetrag(Geldbetrag.ausEurocent(0));
            _view.setzeOkButtonAktiv(false);
        }
    }

    /**
     * Wird aufgerufen, wenn der OK-Button gedrückt wird.
     * Schließt den Dialog und markiert die Bezahlung als erfolgreich.
     */
    private void okGedrueckt()
    {
        _bezahlungErfolgreich = true;
        _view.verbergeDialog();
    }

    /**
     * Wird aufgerufen, wenn der Abbrechen-Button gedrückt wird.
     * Schließt den Dialog und markiert die Bezahlung als abgebrochen.
     */
    private void abbrechenGedrueckt()
    {
        _bezahlungErfolgreich = false;
        _view.verbergeDialog();
    }
}