package de.uni_hamburg.informatik.swt.se2.kino.ui.platzverkauf;

import de.uni_hamburg.informatik.swt.se2.kino.wertobjekte.Geldbetrag;

import javax.swing.*;
import java.awt.*;

/**
 * Die View-Komponente des Verkaufsfensters für Barzahlungen.
 * Diese Klasse ist ausschließlich für die Darstellung der UI zuständig.
 * 
 * @author SE2-Team
 * @version SoSe 2025
 */
class VerkaufsfensterView
{
    private JDialog _dialog;
    private JLabel _preisLabel;
    private JTextField _gezahltField;
    private JLabel _fehlenderBetragLabel;
    private JLabel _restbetragLabel;
    private JLabel _fehlermeldungLabel;
    private JButton _okButton;
    private JButton _abbrechenButton;

    /**
     * Initialisiert die View.
     * 
     * @param parent Das Elternfenster für den modalen Dialog
     */
    public VerkaufsfensterView(JFrame parent)
    {
        _dialog = new JDialog(parent, "Barzahlung", true);
        _dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        erstelleUI();
        
        _dialog.setSize(400, 300);
        _dialog.setLocationRelativeTo(parent);
    }

    /**
     * Erstellt die UI-Komponenten mit BorderLayout.
     */
    private void erstelleUI()
    {
        // Hauptpanel mit BorderLayout
        JPanel hauptPanel = new JPanel(new BorderLayout());
        hauptPanel.setBackground(new Color(200, 210, 220));
        
        // CENTER: Inhaltspanel
        JPanel inhaltPanel = new JPanel();
        inhaltPanel.setLayout(new BoxLayout(inhaltPanel, BoxLayout.Y_AXIS));
        inhaltPanel.setBackground(new Color(200, 210, 220));
        inhaltPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        // Formular-Panel
        JPanel formularPanel = erstelleFormularPanel();
        inhaltPanel.add(formularPanel);
        
        // Abstand
        inhaltPanel.add(Box.createVerticalStrut(10));
        
        // Fehlermeldung
        _fehlermeldungLabel = new JLabel("Bitte Betrag im Format XX,XX eingeben!");
        _fehlermeldungLabel.setForeground(Color.WHITE);
        _fehlermeldungLabel.setBackground(Color.RED);
        _fehlermeldungLabel.setOpaque(true);
        _fehlermeldungLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        _fehlermeldungLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        _fehlermeldungLabel.setVisible(false);
        inhaltPanel.add(_fehlermeldungLabel);
        
        hauptPanel.add(inhaltPanel, BorderLayout.CENTER);
        
        // SOUTH: Button-Panel
        JPanel buttonPanel = erstelleButtonPanel();
        hauptPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        _dialog.setContentPane(hauptPanel);
    }

    /**
     * Erstellt das Formular-Panel mit GridBagLayout für flexible Anordnung.
     */
    private JPanel erstelleFormularPanel()
    {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(200, 210, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Spalte 0: Labels (linksbündig)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JLabel zuZahlenLabel = new JLabel("Zu zahlen:");
        zuZahlenLabel.setFont(zuZahlenLabel.getFont().deriveFont(Font.BOLD));
        panel.add(zuZahlenLabel, gbc);
        
        // Spalte 1: Wert (mit festem Abstand)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 50; // Fester Abstand nach links
        _preisLabel = new JLabel("11,90 €");
        panel.add(_preisLabel, gbc);
        
        // Zeile 1: Gegeben
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 0;
        JLabel gegebenLabel = new JLabel("Gegeben:");
        gegebenLabel.setFont(gegebenLabel.getFont().deriveFont(Font.BOLD));
        panel.add(gegebenLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 0;
        _gezahltField = new JTextField(12);
        _gezahltField.setPreferredSize(new Dimension(150, 25));
        panel.add(_gezahltField, gbc);
        
        // Zeile 2: Fehlender Betrag
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        JLabel fehlenderLabel = new JLabel("Fehlender Betrag:");
        fehlenderLabel.setFont(fehlenderLabel.getFont().deriveFont(Font.BOLD));
        panel.add(fehlenderLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.ipadx = 50;
        _fehlenderBetragLabel = new JLabel("11,90 €");
        panel.add(_fehlenderBetragLabel, gbc);
        
        // Zeile 3: Rückgeld
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.ipadx = 0;
        JLabel rueckgeldLabel = new JLabel("Rückgeld:");
        rueckgeldLabel.setFont(rueckgeldLabel.getFont().deriveFont(Font.BOLD));
        panel.add(rueckgeldLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.ipadx = 50;
        _restbetragLabel = new JLabel("0,00 €");
        panel.add(_restbetragLabel, gbc);
        
        return panel;
    }

    /**
     * Erstellt das Button-Panel mit FlowLayout.
     */
    private JPanel erstelleButtonPanel()
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(200, 210, 220));
        
        _abbrechenButton = new JButton("Abbrechen");
        _abbrechenButton.setPreferredSize(new Dimension(100, 30));
        
        _okButton = new JButton("OK");
        _okButton.setPreferredSize(new Dimension(100, 30));
        _okButton.setEnabled(false);
        
        panel.add(_abbrechenButton);
        panel.add(_okButton);
        
        return panel;
    }

    /**
     * Zeigt den Dialog an.
     */
    public void zeigeDialog()
    {
        _gezahltField.requestFocusInWindow();
        _dialog.setVisible(true);
    }

    /**
     * Verbirgt den Dialog.
     */
    public void verbergeDialog()
    {
        _dialog.setVisible(false);
    }

    /**
     * Setzt den anzuzeigenden Preis.
     * 
     * @param preis Der Preis als Geldbetrag
     */
    public void setzePreis(Geldbetrag preis)
    {
        _preisLabel.setText(preis.getFormatiertenString() + " €");
    }

    /**
     * Setzt den anzuzeigenden fehlenden Betrag.
     * 
     * @param betrag Der fehlende Betrag als Geldbetrag
     */
    public void setzeFehlenderBetrag(Geldbetrag betrag)
    {
        _fehlenderBetragLabel.setText(betrag.getFormatiertenString() + " €");
    }

    /**
     * Setzt den anzuzeigenden Restbetrag.
     * 
     * @param betrag Der Restbetrag als Geldbetrag
     */
    public void setzeRestbetrag(Geldbetrag betrag)
    {
        _restbetragLabel.setText(betrag.getFormatiertenString() + " €");
    }

    /**
     * Zeigt eine Fehlermeldung an.
     * 
     * @param meldung Die anzuzeigende Fehlermeldung
     */
    public void zeigeFehlermeldung(String meldung)
    {
        _fehlermeldungLabel.setText(meldung);
        _fehlermeldungLabel.setVisible(true);
    }

    /**
     * Verbirgt die Fehlermeldung.
     */
    public void verbergeFehlermeldung()
    {
        _fehlermeldungLabel.setVisible(false);
    }

    /**
     * Leert das Eingabefeld für den gezahlten Betrag.
     */
    public void leereEingabefeld()
    {
        _gezahltField.setText("");
    }

    /**
     * Gibt den eingegebenen Text im Gezahlt-Feld zurück.
     * 
     * @return Der eingegebene Text
     */
    public String getGezahltText()
    {
        return _gezahltField.getText();
    }

    /**
     * Aktiviert oder deaktiviert den OK-Button.
     * 
     * @param aktiv true zum Aktivieren, false zum Deaktivieren
     */
    public void setzeOkButtonAktiv(boolean aktiv)
    {
        _okButton.setEnabled(aktiv);
    }

    // Getter für die Komponenten, die der Controller benötigt

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