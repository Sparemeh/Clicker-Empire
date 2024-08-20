package ui;

import model.Building;
import model.Player;
import model.PopulationBuilding;

import javax.swing.*;
import java.awt.*;

//Represents the statistic panel
public class StatPanel extends JPanel {
    JLabel popLabel;
    JLabel incomeLabel;
    JLabel growthLabel;
    JLabel treasuryLabel;
    JLabel houseLabel;
    JLabel manorLabel;
    JLabel castleLabel;

    //Constructs a statistic panel
    public StatPanel() {
        setLayout(new GridLayout(7,1));

        popLabel = new JLabel();
        add(popLabel);
        popLabel.setBackground(Color.gray);
        incomeLabel = new JLabel();
        add(incomeLabel);
        growthLabel = new JLabel();
        add(growthLabel);
        treasuryLabel = new JLabel();
        add(treasuryLabel);
        houseLabel = new JLabel();
        add(houseLabel);
        manorLabel = new JLabel();
        add(manorLabel);
        castleLabel = new JLabel();
        add(castleLabel);
        setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.gray));
    }

    /*
     * MODIFIES: this
     * EFFECTS: Accepts player and 3 building parameters and updates all labels
     */
    public void updateClass(Player player, PopulationBuilding house, PopulationBuilding manor,
                            PopulationBuilding castle) {
        popLabel.setText("Population: " + player.getPopulationCount());
        incomeLabel.setText("Income: " + player.getIncome());
        growthLabel.setText("Population Growth: " + player.getPopulationTick());
        treasuryLabel.setText("Treasury: " + player.getTreasury());
        houseLabel.setText("Houses: " + player.getBuildingsListByType(house).size());
        manorLabel.setText("Manors: " + player.getBuildingsListByType(manor).size());
        castleLabel.setText("Castles: " + player.getBuildingsListByType(castle).size());
    }


}
