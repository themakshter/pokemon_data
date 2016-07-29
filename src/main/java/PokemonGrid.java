import POGOProtos.Enums.PokemonIdOuterClass;
import com.pokegoapi.api.pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JButton refreshButton = new JButton("Refresh");
        UpdateableTableModel tableModel = createTableModel(Arrays.asList(new Pokemon[]{}));
        JTable table = new JTable(tableModel);
        contentPanel.add(refreshButton);
        contentPanel.add(buildTableContainer(table));
        refreshButton.addActionListener((e -> {
            new Thread() {
                public void run() {
                    List<Pokemon> pokemonList = PokemonAnalyser.getAllPokemon();
                    print("Size " + pokemonList.size());
                    tableModel.updateData(generatePokemonGrid(pokemonList));
                    print("Finishing");
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

    private UpdateableTableModel createTableModel(final List<Pokemon> pokemonList) {
        String[] columnNames = {"Pokemon", "Candy required", "Number",
                "Number candies", "No. can evolve", "No. trade in"};
        Object[][] rowData = generatePokemonGrid(pokemonList);
        return new UpdateableTableModel(columnNames, rowData);
    }

    private static Object[][] generatePokemonGrid(List<Pokemon> pokemonList) {
        Map<PokemonIdOuterClass.PokemonId, List<Pokemon>> groupedPokemon = pokemonList.stream().collect(Collectors.groupingBy(Pokemon::getPokemonId));
        print("Grouped num" + groupedPokemon.size());
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

    private static String toTitleCase(final String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
    }

    private static int calculateNumCanEvolve(final int candiesToEvolve, final int numOfType, final int numCandies) {
        if (candiesToEvolve == 0){
            return 0;
        }
        int candyLim = 0;
        while(true) {
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
    private static void print(String string) {
        System.out.println(string);
    }
}
