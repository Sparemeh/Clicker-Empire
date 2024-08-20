package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a population building that contains a price, value, name and population growth.
public class PopulationBuilding implements Writable {
    private int price;
    private int value;
    private String name;
    private int popPerTick = 0;

    /*
     * REQUIRES: name has a non-zero length
     * EFFECTS: name on building is set to name; price, popPerTick and value
     *          are all positive integers.
     */
    public PopulationBuilding(String name, int price, int popPerTick, int value) {
        this.name = name;
        this.price = price;
        this.value = value;
        this.popPerTick = popPerTick;
    }

    public String getName() {
        return name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public int getValue() {
        return value;
    }

    public int getPopPerTick() {
        return popPerTick;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("price", price);
        json.put("value", value);
        json.put("popPerTick", popPerTick);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PopulationBuilding that = (PopulationBuilding) o;

        if (price != that.price) {
            return false;
        }
        if (value != that.value) {
            return false;
        }
        if (popPerTick != that.popPerTick) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = price;
        result = 31 * result + value;
        result = 31 * result + name.hashCode();
        result = 31 * result + popPerTick;
        return result;
    }
}
