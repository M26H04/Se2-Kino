package de.uni_hamburg.informatik.swt.se2.kino.ui.platzverkauf;

import de.uni_hamburg.informatik.swt.se2.kino.wertobjekte.Geldbetrag;

import javax.swing.*;
import java.awt.*;

/**
 * Das Fenster für die Barzahlung.
 * Zeigt an, wie viel zu zahlen ist und berechnet das Rückgeld.
 * 
 * @author SE2-Team
 * @version SoSe 2025
 */
class VerkaufsfensterView
{
    // Das Fenster selbst
    private JDialog _dialog;
    
    // Die Anzeigefelder
    private JLabel _preisLabel;
    private JLabel _fehlenderBetragLabel;
    private JLabel _restbetragLabel;
    private JLabel _fehlermeldungLabel;
    
    // Eingabe und Buttons
    private JTextField _gezahltField;
    private JButton _okButton;
    private JButton _abbrechenButton;

    /**
     * Erstellt das Barzahlungsfenster.
     */
    public VerkaufsfensterView(JFrame parent)
    {
        // Fenster erstellen
        _dialog = new JDialog(parent, "Barzahlung", true);
        _dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        // Inhalt aufbauen
        baueInhaltAuf();
        
        // Fenster konfigurieren
        _dialog.setSize(400, 280);
        _dialog.setLocationRelativeTo(parent);
    }

    /**
     * Baut den kompletten Inhalt des Fensters auf.
     */
    private void baueInhaltAuf()
    {
        // Hauptpanel mit grauem Hintergrund
        JPanel hauptPanel = new JPanel();
        hauptPanel.setLayout(new BorderLayout(0, 10));
        hauptPanel.setBackground(new Color(200, 210, 220));
        hauptPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Formular in der Mitte
        hauptPanel.add(erstelleFormular(), BorderLayout.CENTER);
        
        // Fehlermeldung (anfangs unsichtbar)
        _fehlermeldungLabel = erstelleFehlermeldung();
        hauptPanel.add(_fehlermeldungLabel, BorderLayout.NORTH);
        
        // Buttons unten
        hauptPanel.add(erstelleButtonPanel(), BorderLayout.SOUTH);
        
        _dialog.setContentPane(hauptPanel);
    }

    /**
     * Erstellt das Formular mit allen Eingabefeldern.
     */
    private JPanel erstelleFormular()
    {
        JPanel formular = new JPanel();
        formular.setLayout(new GridLayout(4, 2, 10, 10));
        formular.setBackground(new Color(200, 210, 220));
        
        // Zeile 1: Zu zahlen
        formular.add(macheFettesLabel("Zu zahlen:"));
        _preisLabel = new JLabel("0,00 €");
        formular.add(_preisLabel);
        
        // Zeile 2: Gegeben
        formular.add(macheFettesLabel("Gegeben:"));
        _gezahltField = new JTextField();
        formular.add(_gezahltField);
        
        // Zeile 3: Fehlender Betrag
        formular.add(macheFettesLabel("Fehlender Betrag:"));
        _fehlenderBetragLabel = new JLabel("0,00 €");
        formular.add(_fehlenderBetragLabel);
        
        // Zeile 4: Rückgeld
        formular.add(macheFettesLabel("Rückgeld:"));
        _restbetragLabel = new JLabel("0,00 €");
        formular.add(_restbetragLabel);
        
        return formular;
    }

    /**
     * Erstellt ein Label mit fetter Schrift.
     */
    private JLabel macheFettesLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setFont(label.getFont().deriveFont(Font.BOLD));
        return label;
    }

    /**
     * Erstellt die rote Fehlermeldung.
     */
    private JLabel erstelleFehlermeldung()
    {
        JLabel fehler = new JLabel();
        fehler.setForeground(Color.WHITE);
        fehler.setBackground(Color.RED);
        fehler.setOpaque(true);
        fehler.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fehler.setHorizontalAlignment(SwingConstants.CENTER);
        fehler.setVisible(false);
        return fehler;
    }

    /**
     * Erstellt das Panel mit den Buttons.
     */
    private JPanel erstelleButtonPanel()
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(200, 210, 220));
        
        // Buttons erstellen
        _abbrechenButton = new JButton("Abbrechen");
        _okButton = new JButton("OK");
        _okButton.setEnabled(false);
        
        // Größe setzen
        Dimension buttonGroesse = new Dimension(100, 30);
        _abbrechenButton.setPreferredSize(buttonGroesse);
        _okButton.setPreferredSize(buttonGroesse);
        
        // Hinzufügen
        buttonPanel.add(_abbrechenButton);
        buttonPanel.add(_okButton);
        
        return buttonPanel;
    }

    // ===== ÖFFENTLICHE METHODEN FÜR DEN CONTROLLER =====

    /**
     * Zeigt das Fenster an.
     */
    public void zeigeDialog()
    {
        _gezahltField.requestFocusInWindow();
        _dialog.setVisible(true);
    }

    /**
     * Schließt das Fenster.
     */
    public void verbergeDialog()
    {
        _dialog.setVisible(false);
    }

    /**
     * Setzt den zu zahlenden Betrag.
     */
    public void setzePreis(Geldbetrag preis)
    {
        _preisLabel.setText(preis.getFormatiertenString() + " €");
    }

    /**
     * Zeigt an, wie viel noch fehlt.
     */
    public void setzeFehlenderBetrag(Geldbetrag betrag)
    {
        _fehlenderBetragLabel.setText(betrag.getFormatiertenString() + " €");
    }

    /**
     * Zeigt das Rückgeld an.
     */
    public void setzeRestbetrag(Geldbetrag betrag)
    {
        _restbetragLabel.setText(betrag.getFormatiertenString() + " €");
    }

    /**
     * Zeigt eine Fehlermeldung in rot an.
     */
    public void zeigeFehlermeldung(String meldung)
    {
        _fehlermeldungLabel.setText(meldung);
        _fehlermeldungLabel.setVisible(true);
    }

    /**
     * Versteckt die Fehlermeldung wieder.
     */
    public void verbergeFehlermeldung()
    {
        _fehlermeldungLabel.setVisible(false);
    }

    /**
     * Leert das Eingabefeld.
     */
    public void leereEingabefeld()
    {
        _gezahltField.setText("");
    }

    /**
     * Gibt zurück, was der Benutzer eingegeben hat.
     */
    public String getGezahltText()
    {
        return _gezahltField.getText();
    }

    /**
     * Aktiviert oder deaktiviert den OK-Button.
     */
    public void setzeOkButtonAktiv(boolean aktiv)
    {
        _okButton.setEnabled(aktiv);
    }

    // ===== GETTER FÜR DEN CONTROLLER =====

    public JButton getOkButton()
    {
        return _okButton;
    }

    public JButton getAbbrechenButton()
    {
        return _abbrechenButton;
    }

    public JTextField getGezahltField()
    {
        return _gezahltField;
    }

    public JDialog getDialog()
    {
        return _dialog;
    }
}