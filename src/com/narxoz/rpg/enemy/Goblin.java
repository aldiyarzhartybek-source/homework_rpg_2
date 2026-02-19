package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Goblin implements Enemy {

    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;

    private String element = "NONE";
    private String aiBehavior = "BASIC";

    private List<Ability> abilities;
    private LootTable lootTable;

    public Goblin(String name) {
        this.name = name;


        this.health = 100;
        this.damage = 15;
        this.defense = 5;
        this.speed = 35;

        this.abilities = new ArrayList<>();
        this.lootTable = null;
    }



    @Override public String getName() {
        return name;
    }

    @Override public int getHealth() {
        return health;
    }

    @Override public int getDamage() {
        return damage;
    }

    @Override public int getDefense() {
        return defense;
    }

    @Override public int getSpeed() {
        return speed;
    }





    @Override public String getElement() {
        return element;
    }

    @Override public String getAIBehavior() {
        return aiBehavior;
    }



    @Override public List<Ability> getAbilities() {
        return List.copyOf(abilities);
    }

    @Override public LootTable getLootTable() {
        return lootTable;
    }



    @Override
    public Map<Integer, Integer> getPhases() {
        return Map.of(); // goblin has no phases
    }


    @Override public void setElement(String element) {
        this.element = element;
    }

    @Override public void setAIBehavior(String aiBehavior) {
        this.aiBehavior = aiBehavior;
    }

    @Override public void setLootTable(LootTable lootTable) {
        this.lootTable = lootTable;
    }

    @Override
    public void addAbility(Ability ability) {
        if (ability != null) abilities.add(ability);
    }

    @Override
    public void setAbilities(List<Ability> abilities) {
        this.abilities = new ArrayList<>();
        if (abilities != null) {
            for (Ability a : abilities) {
                this.abilities.add(a.clone());
            }
        }
    }

    @Override
    public void addPhase(int phaseNumber, int healthThreshold) {

    }

    @Override
    public void multiplyStats(double multiplier) {
        if (multiplier <= 0) return;
        health  = (int) Math.round(health  * multiplier);
        damage  = (int) Math.round(damage  * multiplier);
        defense = (int) Math.round(defense * multiplier);
        speed   = (int) Math.round(speed   * multiplier);
    }



    @Override
    public Enemy clone() {
        Goblin copy = new Goblin(this.name);
        copy.health = this.health;
        copy.damage = this.damage;
        copy.defense = this.defense;
        copy.speed = this.speed;

        copy.element = this.element;
        copy.aiBehavior = this.aiBehavior;


        copy.abilities = new ArrayList<>();
        for (Ability a : this.abilities) {
            copy.abilities.add(a.clone());
        }


        copy.lootTable = (this.lootTable == null) ? null : this.lootTable.clone();

        return copy;
    }

    @Override
    public void displayInfo() {
        System.out.println("=== " + name + " (Goblin) ===");
        System.out.println("Health: " + health + " | Damage: " + damage
                + " | Defense: " + defense + " | Speed: " + speed);
        System.out.println("Element: " + element + " | AI: " + aiBehavior);

        System.out.println("Abilities (" + abilities.size() + "):");
        if (abilities.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Ability a : abilities) {
                System.out.println("  - " + a.getName()
                        + " (dmg=" + a.getDamage() + "): " + a.getDescription());
            }
        }

        if (lootTable == null) {
            System.out.println("Loot: (none)");
        } else {
            System.out.println("Loot items: " + lootTable.getItems());
            System.out.println("Gold: " + lootTable.getGoldDrop()
                    + " | EXP: " + lootTable.getExperienceDrop());
        }
        System.out.println();
    }
}
