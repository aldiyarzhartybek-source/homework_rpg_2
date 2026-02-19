package com.narxoz.rpg.enemy;

import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.loot.LootTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DragonBoss implements Enemy {


    private String name;
    private int health;
    private int damage;
    private int defense;
    private int speed;


    private String element;


    List<Ability> abilities;


    Map<Integer, Integer> phases;


    LootTable lootTable;


    private String aiBehavior;


    private boolean canFly;
    private boolean hasBreathAttack;
    private int wingspan;

    public DragonBoss(String name, int health, int damage, int defense,
                      int speed, String element,
                      List<Ability> abilities,
                      int phase1Threshold, int phase2Threshold, int phase3Threshold,
                      LootTable lootTable, String aiBehavior,
                      boolean canFly, boolean hasBreathAttack, int wingspan) {

        this.name = name;
        this.health = health;
        this.damage = damage;
        this.defense = defense;
        this.speed = speed;
        this.element = element;

        this.abilities = (abilities != null) ? abilities : new ArrayList<>();

        this.phases = new HashMap<>();
        this.phases.put(1, phase1Threshold);
        this.phases.put(2, phase2Threshold);
        this.phases.put(3, phase3Threshold);

        this.lootTable = lootTable;
        this.aiBehavior = aiBehavior;

        this.canFly = canFly;
        this.hasBreathAttack = hasBreathAttack;
        this.wingspan = wingspan;
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
        return Map.copyOf(phases);
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
            for (Ability a : abilities) this.abilities.add(a.clone());
        }
    }

    @Override
    public void addPhase(int phaseNumber, int healthThreshold) {
        this.phases.put(phaseNumber, healthThreshold);
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

        int p1 = phases.getOrDefault(1, 0);
        int p2 = phases.getOrDefault(2, 0);
        int p3 = phases.getOrDefault(3, 0);

        DragonBoss copy = new DragonBoss(
                this.name,
                this.health,
                this.damage,
                this.defense,
                this.speed,
                this.element,
                null,
                p1, p2, p3,
                null,
                this.aiBehavior,
                this.canFly,
                this.hasBreathAttack,
                this.wingspan
        );


        copy.abilities = new ArrayList<>();
        for (Ability a : this.abilities) {
            copy.abilities.add(a.clone());
        }


        copy.phases = new HashMap<>(this.phases);


        copy.lootTable = (this.lootTable == null) ? null : this.lootTable.clone();

        return copy;
    }

    @Override
    public void displayInfo() {
        System.out.println("=== " + name + " (Dragon Boss) ===");
        System.out.println("Health: " + health + " | Damage: " + damage
                + " | Defense: " + defense + " | Speed: " + speed);
        System.out.println("Element: " + element);
        System.out.println("AI Behavior: " + aiBehavior);

        System.out.println("Abilities (" + abilities.size() + "):");
        if (abilities.isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (Ability a : abilities) {
                System.out.println("  - " + a.getName()
                        + " (dmg=" + a.getDamage() + "): " + a.getDescription());
            }
        }

        System.out.println("Boss Phases: " + phases.size());
        for (Map.Entry<Integer, Integer> phase : phases.entrySet()) {
            System.out.println("  Phase " + phase.getKey()
                    + ": triggers at " + phase.getValue() + " HP");
        }

        System.out.println("Can Fly: " + canFly
                + " | Breath Attack: " + hasBreathAttack
                + " | Wingspan: " + wingspan);

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
