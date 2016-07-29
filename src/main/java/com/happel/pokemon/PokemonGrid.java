package com.happel.pokemon;

import javax.swing.*;
import java.awt.*;

public class PokemonGrid extends JFrame {

    private JPanel contentPanel;
    public PokemonGrid() {
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Stats");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPanel = (JPanel) getContentPane();
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Evolutions", new PokemonEvolutionsPanel());
        contentPanel.add(tabbedPane);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                PokemonGrid pg = new PokemonGrid();
                pg.setVisible(true);
            }
        });
    }
}
