package com.happel.pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PokemonGrid extends JFrame {

    private JPanel contentPanel;
    private Credentials credentials;
    public PokemonGrid() {
        initUI();
    }

    private void initUI() {
        setTitle("Pokemon Stats");
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        contentPanel = (JPanel) getContentPane();
        contentPanel.setLayout(new BorderLayout());
        CredentialPanel credentialPanel = new CredentialPanel();
        credentials = credentialPanel.getCredentials();
        contentPanel.add(credentialPanel, BorderLayout.PAGE_START);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Evolutions", new PokemonEvolutionsPanel(credentials));
        tabbedPane.addTab("Individual Stats", new PokemonStatsPanel(credentials));
        contentPanel.add(tabbedPane, BorderLayout.CENTER);
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

class CredentialPanel extends JPanel {

    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private Credentials credentials;

    CredentialPanel() {
        super(new GridBagLayout());
        setMinimumSize(new Dimension(100, 100));
        credentials = new Credentials("","", Credentials.CredentialType.PTC);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3,3,3,3);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 5;
        c.weightx = 0.5;
        c.weighty = 0.5;
        buildTextFields(c);
        buildRadioButtons(c);
    }

    private void buildTextFields(GridBagConstraints c) {
        usernameTextField = new JTextField();
        passwordTextField = new JPasswordField();
        KeyListener textFieldListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getSource() == usernameTextField) {
                    credentials.username = usernameTextField.getText().trim();
                }
                if (e.getSource() == passwordTextField) {
                    credentials.password = passwordTextField.getText().trim();
                }
            }
        };
        usernameTextField.addKeyListener(textFieldListener);
        passwordTextField.addKeyListener(textFieldListener);
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("Username: "), c);
        c.gridx = 2;
        add(usernameTextField, c);
        c.gridx = 0;
        c.gridy = 1;
        add(new JLabel("Password: "), c);
        c.gridx = 2;
        add(passwordTextField, c);
    }

    private void buildRadioButtons(GridBagConstraints c) {
        JRadioButton ptcButton = new JRadioButton("PTC");
        ptcButton.setActionCommand("ptc");
        JRadioButton googleButton = new JRadioButton("Google");
        googleButton.setActionCommand("google");
        ActionListener listener = (e) -> {
            credentials.credentialType = Credentials.CredentialType.valueOf(e.getActionCommand().toUpperCase());
        };
        ptcButton.addActionListener(listener);
        googleButton.addActionListener(listener);
        c.gridx = 0;
        c.gridy = 2;
        add(new JLabel("Type: "), c);
        c.gridx = 1;
        add(ptcButton, c);
        c.gridx = 2;
        add(googleButton, c);
        ButtonGroup group = new ButtonGroup();
        group.add(ptcButton);
        group.add(googleButton);
        ptcButton.doClick();
    }

    public Credentials getCredentials() {
        return credentials;
    }
}
