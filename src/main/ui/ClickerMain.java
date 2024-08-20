package ui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import model.Event;
import model.EventLog;
import model.Player;
import model.PopulationBuilding;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Starts clicker game and handles UI
public class ClickerMain extends JFrame implements ActionListener {
    static final int REFRESH_RATE = 10; // How many milliseconds before each tick
    private static final String JSON_STORE = "./data/player.json";
    public static final int WIDTH = 1920;
    public static final int HEIGHT = 1080;

    private Player player;
    private PopulationBuilding house = new PopulationBuilding("House", 500, 1, 100);
    private PopulationBuilding manor = new PopulationBuilding("Manor", 5000, 10, 1000);
    private PopulationBuilding castle = new PopulationBuilding("Castle", 50000, 100, 10000);
    private Screen screen;
    private EventLog eventLog;

    JPanel topPanel;
    JPanel bottomPanel;
    StatPanel statPanel;
    ViewFinder viewFinder;
    JPanel buildingPanel;
    JPanel optionsPanel;


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // Constructs a clicker game
    public ClickerMain() throws IOException, InterruptedException {
        super("Clicker Empire");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        eventLog = EventLog.getInstance();

        // Setup UI screen
        setupScreen();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Event event: eventLog) {
                    event.display();
                }
                e.getWindow().dispose();
                System.out.println("Closed!");
            }
        });

        // Initialize new player instance
        player = new Player(0,0,0,0);
        beginTicks();
    }

    // MODIFIES: this
    // EFFECTS: sets up the screen
    private void setupScreen() throws IOException {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        // viewFinder setup
        viewFinder = new ViewFinder(player);

        // statPanel setup
        statPanel = new StatPanel();

        // topPanel setup
        topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,2));
        add(topPanel, BorderLayout.NORTH);
        topPanel.add(viewFinder);
        topPanel.add(statPanel);
        topPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT / 2));

        botPanelSetup();
        setVisible(true);
    }

    // Sets up the bottom panel
    private void botPanelSetup() {
        // bottomPanel setup
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(1,3,20,20));
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT / 2 - 150));

        buildingPanelSetup();

        // clickerButton setup
        JButton clickerButton = new JButton();
        clickerButton.setText("Click Me");
        bottomPanel.add(clickerButton);
        clickerButton.addActionListener(this);

        optionsPanelSetup();
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up options panel
     */
    private void optionsPanelSetup() {
        // optionsPanel setup
        optionsPanel = new JPanel(new GridLayout(3,1,10,10));
        optionsPanel.setPreferredSize(new Dimension(100,HEIGHT / 2 - 100));
        JButton loadButton = new JButton();
        JButton saveButton = new JButton();
        JButton exitButton = new JButton();
        optionsPanel.add(loadButton);
        optionsPanel.add(saveButton);
        optionsPanel.add(exitButton);
        loadButton.setText("Load Previous Save");
        saveButton.setText("Save File");
        exitButton.setText("Exit Game");
        loadButton.addActionListener(this);
        saveButton.addActionListener(this);
        exitButton.addActionListener(this);
        bottomPanel.add(optionsPanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets up building panel
     */
    private void buildingPanelSetup() {
        // buildingPanel setup
        buildingPanel = new JPanel(new GridLayout(3,2,10,10));
        bottomPanel.add(buildingPanel);

        buyButtonSetup();
        sellButtonSetup();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets up all building sell buttons
     */
    private void sellButtonSetup() {
        // sellManorButton setup
        JButton sellManorButton = new JButton();
        sellManorButton.setText("Sell Manor: " + manor.getValue());
        buildingPanel.add(sellManorButton);
        sellManorButton.addActionListener(this);
        sellManorButton.setActionCommand("Sell Manor");

        // buyCastleButton setup
        JButton buyCastleButton = new JButton();
        buyCastleButton.setText("Buy Castle: " + castle.getPrice());
        buildingPanel.add(buyCastleButton);
        buyCastleButton.addActionListener(this);
        buyCastleButton.setActionCommand("Buy Castle");

        // sellCastleButton setup
        JButton sellCastleButton = new JButton();
        sellCastleButton.setText("Sell Castle: " + castle.getValue());
        buildingPanel.add(sellCastleButton);
        sellCastleButton.addActionListener(this);
        sellCastleButton.setActionCommand("Sell Castle");
    }

    /*
     * MODIFIES: this
     * EFFECTS: Sets up all building buy buttons
     */
    private void buyButtonSetup() {
        // buyHouseButton setup
        JButton buyHouseButton = new JButton();
        buyHouseButton.setText("Buy House: " + house.getPrice());
        buildingPanel.add(buyHouseButton);
        buyHouseButton.addActionListener(this);
        buyHouseButton.setActionCommand("Buy House");
        System.out.println(buyHouseButton.getActionCommand());

        // sellHouseButton setup
        JButton sellHouseButton = new JButton();
        sellHouseButton.setText("Sell House: " + house.getValue());
        buildingPanel.add(sellHouseButton);
        sellHouseButton.addActionListener(this);
        sellHouseButton.setActionCommand("Sell House");

        // buyManorButton setup
        JButton buyManorButton = new JButton();
        buyManorButton.setText("Buy Manor: " + manor.getPrice());
        buildingPanel.add(buyManorButton);
        buyManorButton.setActionCommand("Buy Manor");
        buyManorButton.addActionListener(this);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Begins ticking every REFRESH_RATE,
     *          player ticks every player.getTickSpeed() ticks
     */
    private void beginTicks() throws IOException, InterruptedException {
        int playerTick = 0;
        while (true) {
            // Every player.getTickSpeed() triggers player.tick()
            if (playerTick >= player.getTickSpeed()) {
                player.tick();
                playerTick = 0;
                System.out.println("ticked!"); // Debug purpose only
            }
            playerTick++;
            tick();
            Thread.sleep(REFRESH_RATE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Process each tick, every tick processes user input and renders screen
    private void tick() throws IOException {
        render();
    }

    // MODIFIES: this
    // EFFECTS: Handles player inputs
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Click Me":
                player.addPop();
                System.out.println("added citizen"); // Debug purpose only
                System.out.println();
                break;
            case "Buy House":
                System.out.println(player.buyBuilding(house));
                viewFinder.addImage("house");
                //viewFinder.placeBuilding("house");
                break;
            case "Buy Manor":
                System.out.println(player.buyBuilding(manor));
                viewFinder.addImage("manor");
               // viewFinder.placeBuilding("manor");
                break;
            case "Buy Castle":
                System.out.println(player.buyBuilding(castle));
               // viewFinder.placeBuilding("castle");
                break;
            case "Sell House":
                System.out.println(player.sellBuilding(house));
                viewFinder.removeImage();
                break;
            case "Sell Manor":
                System.out.println(player.sellBuilding(manor));
                viewFinder.removeImage();
                break;
            case "Sell Castle":
                System.out.println(player.sellBuilding(castle));
                viewFinder.removeImage();
                break;
            case "Load Previous Save":
                loadPlayer();
                break;
            case "Save File":
                savePlayer();
                break;
            case "Exit Game":
                System.exit(0);
        }
    }

    // EFFECTS: Renders the screen
    private void render() {
        statPanel.updateClass(player, house, manor, castle);
        //viewFinder.update(player, house, manor, castle);

    }

    // EFFECTS: saves the player to file
    private String savePlayer() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        return "Saved to " + JSON_STORE;
    }

    // MODIFIES: this
    // EFFECTS: loads player from file
    private void loadPlayer() {
        try {
            player = jsonReader.read();
            System.out.println("Loaded " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
