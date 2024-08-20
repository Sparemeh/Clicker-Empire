package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationBuildingTest {
    PopulationBuilding testBuilding1;
    PopulationBuilding testBuilding2;

    @BeforeEach
    void runBefore() {
        testBuilding1 = new PopulationBuilding("a",0,0,0);
        testBuilding1 = new PopulationBuilding("b",1,1,1);
    }

    @Test
    void testEquals() {
        assertTrue(testBuilding1.equals(testBuilding1));
        assertFalse(testBuilding1.equals(testBuilding2));
    }


}
