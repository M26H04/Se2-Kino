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
    private JDialog _dialog;
    private JLabel _preisLabel;
    private JLabel _fehlenderBetragLabel;
    private JLabel _restbetragLabel;
    private JLabel _fehlermeldungLabel;
    private JTextField _gezahltField;
    private JButton _okButton;
    private JButton _abbrechenButton;

    /**
     * Erstellt das Barzahlungsfenster.
     */
    public VerkaufsfensterView(JFrame parent)
    {
        _dialog = new JDialog(parent, "Barzahlung", true);
        _dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        baueInhaltAuf();
        _dialog.setSize(400, 280);
        _dialog.setLocationRelativeTo(parent);
    }

    /**
     * Baut den kompletten Inhalt des Fensters auf.
     */
    private void baueInhaltAuf()
    {
        JPanel hauptPanel = new JPanel();
        hauptPanel.setLayout(new BorderLayout(0, 10));
        hauptPanel.setBackground(new Color(200, 210, 220));
        hauptPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        hauptPanel.add(erstelleFormular(), BorderLayout.CENTER);
        _fehlermeldungLabel = erstelleFehlermeldung();
        hauptPanel.add(_fehlermeldungLabel, BorderLayout.NORTH);
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
        formular.add(macheFettesLabel("Zu zahlen:"));
        _preisLabel = new JLabel("0,00 €");
        formular.add(_preisLabel);
        formular.add(macheFettesLabel("Gegeben:"));
        _gezahltField = new JTextField();
        formular.add(_gezahltField);
        formular.add(macheFettesLabel("Fehlender Betrag:"));
        _fehlenderBetragLabel = new JLabel("0,00 €");
        formular.add(_fehlenderBetragLabel);
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
        _abbrechenButton = new JButton("Abbrechen");
        _okButton = new JButton("OK");
        _okButton.setEnabled(false);
        Dimension buttonGroesse = new Dimension(100, 30);
        _abbrechenButton.setPreferredSize(buttonGroesse);
        _okButton.setPreferredSize(buttonGroesse);
        buttonPanel.add(_abbrechenButton);
        buttonPanel.add(_okButton);
        
        return buttonPanel;
    }

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

    /**
     * Gibt den OkButton zurück
     * @return der OkButton
     */
    public JButton getOkButton()
    {
        return _okButton;
    }

    /**
     * Gibt den AbbrechenButton zurück
     * @return der AbbrechenButton
     */
    public JButton getAbbrechenButton()
    {
        return _abbrechenButton;
    }

    /**
     * Gibt das GezahltFeld zurück
     * @return das GezahltFeld
     */
    public JTextField getGezahltField()
    {
        return _gezahltField;
    }

    /**
     * Gibt den Dialog zurück
     * @return den Dialog
     */
    public JDialog getDialog()
    {
        return _dialog;
    }
}