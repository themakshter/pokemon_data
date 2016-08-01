package com.happel.pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class PokemonGrid extends JFrame {

    private JPanel contentPanel;
    private List<Credentials> credentials;
    public PokemonGrid() {
        credentials = new ArrayList<Credentials>();
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Stats");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPanel = (JPanel) getContentPane();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(createRadioButtons(), BorderLayout.PAGE_START);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Evolutions", new PokemonEvolutionsPanel(credentials));
        tabbedPane.addTab("Individual Stats", new PokemonStatsPanel(credentials));
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createRadioButtons() {
        JRadioButton ptcButton = new JRadioButton("PTC");
        ptcButton.setActionCommand("ptc");
        ptcButton.setSelected(true);
        JRadioButton googleButton = new JRadioButton("Google");
        googleButton.setActionCommand("google");
        JRadioButton google2Button = new JRadioButton("Google2");
        google2Button.setActionCommand("google2");
        ButtonGroup group = new ButtonGroup();
        group.add(ptcButton);
        group.add(googleButton);
        group.add(google2Button);
        ActionListener listener = (e) -> {
            Credentials c = Credentials.buildCredentials(e.getActionCommand() + "_creds.properties");
            credentials.clear();
            credentials.add(c);
        };
        ptcButton.addActionListener(listener);
        googleButton.addActionListener(listener);
        google2Button.addActionListener(listener);
        ptcButton.doClick();
        JPanel panel = new JPanel(new GridLayout(1,3));
        panel.add(ptcButton);
        panel.add(googleButton);
        panel.add(google2Button);
        return panel;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            PokemonGrid pg = new PokemonGrid();
            pg.setVisible(true);
        });
    }
}
