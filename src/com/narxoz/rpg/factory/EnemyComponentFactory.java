package com.narxoz.rpg.factory;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.List;

public interface EnemyComponentFactory {

    String getElement();
    String createAIBehavior();
    List<Ability> createAbilities();
    LootTable createLootTable();
}
