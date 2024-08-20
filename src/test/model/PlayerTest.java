package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    @BeforeEach
    void RunBefore() {
        testPlayer1 = new Player(0,0,0,0);
        testPlayer2 = new Player(100,500,100,5);
        testPlayer3 = new Player(500,5000,500,50);
    }

    @Test
    void testConstructor() {
        assertEquals(100, testPlayer2.getPopulationCount());
        assertEquals(500, testPlayer2.getTreasury());
        assertEquals(100, testPlayer2.getIncome());
        assertEquals(5,testPlayer2.getPopulationTick());
    }

    @Test
    void testTick() {
        testPlayer1.tick();
        assertEquals(0,testPlayer1.getTreasury());
        assertEquals(0,testPlayer1.getPopulationCount());
    }

    @Test
    void testCalculateIncome() {
        testPlayer1.calculateIncome();
        assertEquals(0,testPlayer1.getIncome());
        testPlayer2.calculateIncome();
        assertEquals(100,testPlayer2.getIncome());
    }

    @Test
    void testCalculatePopulationTick() {
        testPlayer1.calculatePopulationTick();
        assertEquals(0,testPlayer1.getPopulationTick());

        testPlayer2.getPopBuildingList().add(new PopulationBuilding("testBuilding", 0, 5,0));
        testPlayer2.calculatePopulationTick();
        assertEquals(5,testPlayer2.getPopulationTick());

        testPlayer3.getPopBuildingList().add(new PopulationBuilding("testBuilding", 0, 25,0));
        testPlayer3.getPopBuildingList().add(new PopulationBuilding("testBuilding", 0, 50,0));
        testPlayer3.calculatePopulationTick();
        assertEquals(75,testPlayer3.getPopulationTick());
    }

    @Test
    void testBuyBuilding() {
        PopulationBuilding b1 = new PopulationBuilding("testBuilding", 0, 0,0);
        PopulationBuilding b2 = new PopulationBuilding("testBuilding", 550, 0,0);
        PopulationBuilding b3 = new PopulationBuilding("testBuilding", 1000, 0,0);

        testPlayer1.buyBuilding(b1);
        assertEquals(b1, testPlayer1.getPopBuildingList().get(0));
        assertEquals(0,testPlayer1.getTreasury());

        testPlayer2.buyBuilding(b2);
        assertEquals(0, testPlayer2.getPopBuildingList().size());
        assertEquals(500,testPlayer2.getTreasury());

        testPlayer3.buyBuilding(b3);
        testPlayer3.buyBuilding(b2);
        assertEquals(b3, testPlayer3.getPopBuildingList().get(0));
        assertEquals(b2, testPlayer3.getPopBuildingList().get(1));
        assertEquals(3450,testPlayer3.getTreasury());
    }

    @Test
    void testSellBuilding() {
        PopulationBuilding b1 = new PopulationBuilding("testBuilding", 0, 0,100);
        PopulationBuilding b2 = new PopulationBuilding("testBuilding", 550, 0,0);
        PopulationBuilding b3 = new PopulationBuilding("testBuilding", 1000, 0,0);

        testPlayer1.sellBuilding(b1);
        assertEquals(0,testPlayer1.getPopBuildingList().size());

        testPlayer2.getPopBuildingList().add(b1);
        testPlayer2.sellBuilding(b1);
        assertEquals(0,testPlayer2.getPopBuildingList().size());
        assertEquals(600,testPlayer2.getTreasury());

        testPlayer3.getPopBuildingList().add(b1);
        testPlayer3.getPopBuildingList().add(b1);
        testPlayer3.getPopBuildingList().add(b2);
        testPlayer3.sellBuilding(b1);
        testPlayer3.sellBuilding(b1);
        testPlayer3.sellBuilding(b1);
        assertEquals(1,testPlayer3.getPopBuildingList().size());
        assertEquals(5200,testPlayer3.getTreasury());

    }

    @Test
    void testGetBuildingListByType() {
        PopulationBuilding b1 = new PopulationBuilding("testBuilding", 0, 0,100);
        PopulationBuilding b2 = new PopulationBuilding("testBuilding", 550, 0,0);
        PopulationBuilding b3 = new PopulationBuilding("testBuilding", 1000, 0,0);

        testPlayer1.getPopBuildingList().add(b1);
        testPlayer1.getPopBuildingList().add(b1);
        testPlayer1.getPopBuildingList().add(b1);
        assertEquals(3,testPlayer1.getBuildingsListByType(b1).size());

        assertEquals(0,testPlayer2.getBuildingsListByType(b1).size());

        testPlayer3.getPopBuildingList().add(b2);
        testPlayer3.getPopBuildingList().add(b1);
        testPlayer3.getPopBuildingList().add(b1);
        assertEquals(1,testPlayer3.getBuildingsListByType(b2).size());
    }

    @Test
    void testAddPop() {
        testPlayer1.addPop();
        assertEquals(1, testPlayer1.getPopulationCount());
        testPlayer1.addPop();
        testPlayer1.addPop();
        assertEquals(3, testPlayer1.getPopulationCount());
    }

    @Test
    void testGetTickSpeed() {
        assertEquals(50, testPlayer1.getTickSpeed());
    }
}