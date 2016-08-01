package com.happel.pokemon;

import com.pokegoapi.api.pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.happel.pokemon.Utils.println;

public abstract class PokemonPanel extends JPanel {
    public PokemonPanel(List<Credentials> credentials) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton refreshButton = new JButton("Refresh");
        UpdatableTableModel tableModel = createTableModel(Arrays.asList(new Pokemon[]{}));
        JTable table = new JTable(tableModel);
        add(refreshButton);
        add(buildTableContainer(table));
        refreshButton.addActionListener((e -> {
            new Thread() {
                public void run() {
                    println("Running fetch");
                    List<Pokemon> pokemonList = PokemonRetriever.getAllPokemon(credentials.get(0));
                    println("Size " + pokemonList.size());
                    tableModel.updateData(generatePokemonGrid(pokemonList));
                    println("Finishing");
                }
            }.start();
        }));
    }

    private UpdatableTableModel createTableModel(final List<Pokemon> pokemonList) {
        Object[][] rowData = generatePokemonGrid(pokemonList);
        return new UpdatableTableModel(getColumnNames(), rowData);
    }

    private JPanel buildTableContainer(JTable table){
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    protected abstract String[] getColumnNames();
    protected abstract Object[][] generatePokemonGrid(List<Pokemon> pokemonList);

}
