package de.uni_hamburg.informatik.swt.se2.kino.ui.platzverkauf;

import de.uni_hamburg.informatik.swt.se2.kino.wertobjekte.Geldbetrag;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Ein modaler Dialog zur Abwicklung von Barzahlungen.
 * Dieser Dialog wird vom PlatzVerkaufsController verwendet.
 * 
 * @author SE2-Team
 * @version SoSe 2025
 */
class BarzahlungDialog
{
    private JDialog _dialog;
    private JLabel _preisLabel;
    private JTextField _gezahltField;
    private JLabel _restbetragLabel;
    private JButton _okButton;
    private JButton _abbrechenButton;
    
    private Geldbetrag _preis;
    private boolean _bezahlungErfolgreich;

    /**
     * Initialisiert den Dialog.
     * 
     * @param parent Das Elternfenster
     */
    public BarzahlungDialog(JFrame parent)
    {
        _dialog = new JDialog(parent, "Barzahlung", true);
        _dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        
        erstelleUI();
        registriereUIAktionen();
        
        _dialog.setSize(400, 250);
        _dialog.setLocationRelativeTo(parent);
    }

    /**
     * Erstellt die UI-Komponenten.
     */
    private void erstelleUI()
    {
        JPanel hauptPanel = new JPanel(new BorderLayout(10, 10));
        hauptPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Inhaltspanel
        JPanel inhaltPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Zu zahlender Betrag
        gbc.gridx = 0;
        gbc.gridy = 0;
        inhaltPanel.add(new JLabel("Zu zahlen:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        _preisLabel = new JLabel("0,00 €");
        _preisLabel.setFont(_preisLabel.getFont().deriveFont(Font.BOLD));
        inhaltPanel.add(_preisLabel, gbc);
        
        // Gezahlter Betrag
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        inhaltPanel.add(new JLabel("Gegeben:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        _gezahltField = new JTextField();
        _gezahltField.setToolTipText("Betrag im Format XX,XX eingeben");
        inhaltPanel.add(_gezahltField, gbc);
        
        // Restbetrag
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;
        inhaltPanel.add(new JLabel("Rückgeld:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        _restbetragLabel = new JLabel("0,00 €");
        _restbetragLabel.setFont(_restbetragLabel.getFont().deriveFont(Font.BOLD));
        inhaltPanel.add(_restbetragLabel, gbc);
        
        hauptPanel.add(inhaltPanel, BorderLayout.CENTER);
        
        // Button-Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        _abbrechenButton = new JButton("Abbrechen");
        _okButton = new JButton("OK");
        _okButton.setEnabled(false);
        
        buttonPanel.add(_abbrechenButton);
        buttonPanel.add(_okButton);
        
        hauptPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        _dialog.setContentPane(hauptPanel);
    }

    /**
     * Registriert die UI-Aktionen.
     */
    private void registriereUIAktionen()
    {
        _okButton.addActionListener(e -> okGedrueckt());
        
        _abbrechenButton.addActionListener(e -> abbrechenGedrueckt());
        
        _dialog.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                abbrechenGedrueckt();
            }
        });
        
        _gezahltField.getDocument().addDocumentListener(new DocumentListener()
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
        
        // UI zurücksetzen
        _preisLabel.setText(preis.getFormatiertenString() + " €");
        _gezahltField.setText("");
        _restbetragLabel.setText("0,00 €");
        _okButton.setEnabled(false);
        
        _gezahltField.requestFocusInWindow();
        _dialog.setVisible(true);
        
        return _bezahlungErfolgreich;
    }

    /**
     * Wird aufgerufen, wenn die Eingabe im Gezahlt-Feld geändert wird.
     */
    private void eingabeGeaendert()
    {
        String eingabe = _gezahltField.getText();
        
        if (Geldbetrag.istGueltigerGeldbetragString(eingabe))
        {
            try
            {
                Geldbetrag gezahlt = Geldbetrag.ausString(eingabe);
                
                if (gezahlt.compareTo(_preis) >= 0)
                {
                    // Rückgeld berechnen
                    Geldbetrag rueckgeld = gezahlt.subtrahiere(_preis);
                    _restbetragLabel.setText(rueckgeld.getFormatiertenString() + " €");
                    _okButton.setEnabled(true);
                }
                else
                {
                    _restbetragLabel.setText("Betrag zu gering!");
                    _okButton.setEnabled(false);
                }
            }
            catch (Exception e)
            {
                _restbetragLabel.setText("Ungültige Eingabe!");
                _okButton.setEnabled(false);
            }
        }
        else
        {
            if (eingabe.isEmpty())
            {
                _restbetragLabel.setText("0,00 €");
            }
            else
            {
                _restbetragLabel.setText("Format: XX,XX");
            }
            _okButton.setEnabled(false);
        }
    }

    /**
     * Wird aufgerufen, wenn der OK-Button gedrückt wird.
     */
    private void okGedrueckt()
    {
        _bezahlungErfolgreich = true;
        _dialog.setVisible(false);
    }

    /**
     * Wird aufgerufen, wenn der Abbrechen-Button gedrückt wird.
     */
    private void abbrechenGedrueckt()
    {
        _bezahlungErfolgreich = false;
        _dialog.setVisible(false);
    }
}