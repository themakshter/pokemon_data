package com.happel.pokemon;

import com.pokegoapi.api.pokemon.Pokemon;
import com.pokegoapi.exceptions.NoSuchItemException;

import java.util.List;
import java.util.stream.Stream;

import static com.happel.pokemon.Utils.toTitleCase;

public class PokemonStatsPanel extends PokemonPanel {

    public PokemonStatsPanel(Credentials credentials) {
        super(credentials);
    }

    @Override
    protected String[] getColumnNames() {
        return new String[] {"Pokemon", "CP", "CP Multiplier", "Max CP",
                "Attack", "Defense", "Stamina", "IV Ratio"};
    }


    @Override
    protected Object[][] generatePokemonGrid(List<Pokemon> pokemonList) {
        Stream<Object[]> rows = pokemonList.stream()
                .sorted((p1, p2) -> {
                    if (p1.getPokemonId() == p2.getPokemonId()) {
                        return -Integer.compare(p1.getCp(), p2.getCp());
                    } else {
                        return Integer.compare(p1.getPokemonId().getNumber(), p2.getPokemonId().getNumber());
                    }
                })
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
