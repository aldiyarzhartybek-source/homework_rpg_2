package com.narxoz.rpg.loot;

import java.util.List;
import java.util.StringJoiner;

public interface LootTable {

    List<String> getItems();

    int getGoldDrop();

    int getExperienceDrop();


    LootTable clone();


    default String getLootInfo() {
        List<String> items = getItems();
        StringJoiner joiner = new StringJoiner(", ");
        if (items != null) {
            for (String item : items) {
                joiner.add(item);
            }
        }
        String itemsText = (items == null || items.isEmpty()) ? "none" : joiner.toString();

        return String.format("Items: [%s], Gold: %d, XP: %d", itemsText, getGoldDrop(), getExperienceDrop());
    }
}
