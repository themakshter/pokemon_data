package com.happel.pokemon;

import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.NoSuchItemException;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.happel.pokemon.Utils.println;
import static com.happel.pokemon.Utils.toTitleCase;

public class PokemonStatsPanel extends JPanel {

    public PokemonStatsPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JButton refreshButton = new JButton("Refresh");
        UpdatableTableModel tableModel = createTableModel(Arrays.asList(new Pokemon[]{}));
        JTable table = new JTable(tableModel);
        add(refreshButton);
        add(buildTableContainer(table));
        refreshButton.addActionListener((e -> {
            new Thread() {
                public void run() {
                    List<Pokemon> pokemonList = PokemonAnalyser.getAllPokemon();
                    println("Size " + pokemonList.size());
                    tableModel.updateData(generatePokemonGrid(pokemonList));
                    println("Finishing");
                }
            }.start();
        }));
    }

    private JPanel buildTableContainer(JTable table){
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        panel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private UpdatableTableModel createTableModel(final List<Pokemon> pokemonList) {
        String[] columnNames = {"Pokemon", "CP", "CP Multiplier", "Max CP",
                "Attack", "Defense", "Stamina", "IV Ratio"};
        Object[][] rowData = generatePokemonGrid(pokemonList);
        return new UpdatableTableModel(columnNames, rowData);
    }

    private static Object[][] generatePokemonGrid(List<Pokemon> pokemonList) {
        Stream<Object[]> rows = pokemonList.stream()
                .sorted((p1, p2) -> Integer.compare(p1.getPokemonId().getNumber(), p2.getPokemonId().getNumber()))
                .map(PokemonStatsPanel::generatePokemonRow);
        return rows.toArray(Object[][]::new);
    }

    private static Object[] generatePokemonRow(Pokemon pokemon) {
        String pokemonName = toTitleCase(pokemon.getPokemonId().name());
        int cp = pokemon.getCp();
        double cpMultipier = roundTo3dp(pokemon.getCpMultiplier());
        int maxCp = 0;
        try {
            maxCp = pokemon.getMaxCp();
        } catch (NoSuchItemException e) {
            e.printStackTrace();
        }
        int attack = pokemon.getIndividualAttack();
        int defense = pokemon.getIndividualDefense();
        int stamina = pokemon.getIndividualStamina();
        double ivRatio = roundTo3dp(pokemon.getIvRatio());
        return new Object[]{ pokemonName, cp, cpMultipier, maxCp, attack, defense, stamina, ivRatio};
    }

    private static double roundTo3dp(double d) {
        double d2 = Math.round(d * 1000);
        return d2/1000.0;
    }
}
