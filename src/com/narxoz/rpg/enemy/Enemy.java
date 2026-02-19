package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.List;
import java.util.Map;

public interface Enemy {

    String getName();
    int getHealth();
    int getDamage();
    int getDefense();
    int getSpeed();


    String getElement();
    String getAIBehavior();


    List<Ability> getAbilities();
    LootTable getLootTable();


    Map<Integer, Integer> getPhases();


    void setElement(String element);
    void setAIBehavior(String aiBehavior);
    void setLootTable(LootTable lootTable);

    void addAbility(Ability ability);
    void setAbilities(List<Ability> abilities);
    void addPhase(int phaseNumber, int healthThreshold);

    void multiplyStats(double multiplier);


    Enemy clone();


    void displayInfo();
}
