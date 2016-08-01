package com.happel.pokemon;

import POGOProtos.Enums.PokemonIdOuterClass;
import com.pokegoapi.api.pokemon.Pokemon;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.happel.pokemon.Utils.println;
import static com.happel.pokemon.Utils.toTitleCase;

public class PokemonEvolutionsPanel extends PokemonPanel {

    public PokemonEvolutionsPanel(List<Credentials> credentials) {
        super(credentials);
    }

    @Override
    protected String[] getColumnNames() {
        return new String[] {"Pokemon", "Candy required", "Number",
                "Number candies", "No. can evolve", "No. trade in"};
    }

    @Override
    protected Object[][] generatePokemonGrid(List<Pokemon> pokemonList) {
        Map<PokemonIdOuterClass.PokemonId, List<Pokemon>> groupedPokemon = pokemonList.stream().collect(Collectors.groupingBy(Pokemon::getPokemonId));
        println("Grouped num" + groupedPokemon.size());
        Stream<Object[]> rows = groupedPokemon.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e1.getKey().getNumber(), e2.getKey().getNumber()))
                .map((e) -> generatePokemonRow(e.getKey(), e.getValue()));
        return rows.toArray(Object[][]::new);
    }
    private static Object[] generatePokemonRow(PokemonIdOuterClass.PokemonId pid, List<Pokemon> pokemon) {
        String pokemonName = toTitleCase(pid.name());
        int candiesToEvolve = pokemon.get(0).getCandiesToEvolve();
        int numOfType = pokemon.size();
        int numCandies = pokemon.get(0).getCandy();
        int numCanEvolve = calculateNumCanEvolve(candiesToEvolve, numOfType, numCandies);
        int numToTradeIn = 0;
        return new Object[]{ pokemonName, candiesToEvolve, numOfType, numCandies, numCanEvolve, numToTradeIn};
    }

    private static int calculateNumCanEvolve(final int candiesToEvolve, final int numOfType, final int numCandies) {
        if (candiesToEvolve == 0) {
            return 0;
        }
        int candyLim = 0;
        while (true) {
            if (candiesToEvolve * candyLim - candyLim + 1 > numCandies) {
                candyLim -= 1;
                break;
            } else {
                candyLim += 1;
            }
        }
        final int maxCanEvolve = Math.min(candyLim, numOfType);
        return maxCanEvolve;
    }
}
