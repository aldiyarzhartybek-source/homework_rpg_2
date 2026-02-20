package com.narxoz.rpg;

import com.narxoz.rpg.builder.BasicEnemyBuilder;
import com.narxoz.rpg.builder.BossEnemyBuilder;
import com.narxoz.rpg.builder.EnemyDirector;
import com.narxoz.rpg.combat.FlameBreath;
import com.narxoz.rpg.combat.Ability;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.factory.EnemyComponentFactory;
import com.narxoz.rpg.factory.FireComponentFactory;
import com.narxoz.rpg.factory.IceComponentFactory;
import com.narxoz.rpg.factory.ShadowComponentFactory;
import com.narxoz.rpg.loot.LootTable;
import com.narxoz.rpg.prototype.EnemyRegistry;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== RPG Enemy System - Creational Patterns Capstone ===\n");


        // PART 1: ABSTRACT FACTORY PATTERN

        printHeader("PART 1: ABSTRACT FACTORY - Themed Components");

        EnemyComponentFactory fireFactory = new FireComponentFactory();
        EnemyComponentFactory iceFactory = new IceComponentFactory();
        EnemyComponentFactory shadowFactory = new ShadowComponentFactory();

        createThemeDemo(fireFactory, "FIRE");
        createThemeDemo(iceFactory, "ICE");
        createThemeDemo(shadowFactory, "SHADOW");

        System.out.println("Note: One factory produces a MATCHING family (abilities + loot + AI). No theme mixing.\n");



        // PART 2: BUILDER PATTERN (+ Factory Method in build())

        printHeader("PART 2: BUILDER - Complex Enemy Construction");


        Enemy fireDragon = new BossEnemyBuilder()
                .setName("Ancient Fire Dragon")
                .setHealth(50000)
                .setDamage(500)
                .setDefense(200)
                .setSpeed(50)
                .setElement(fireFactory.getElement())
                .setAbilities(fireFactory.createAbilities())
                .setLootTable(fireFactory.createLootTable())
                .setAI(fireFactory.createAIBehavior())
                .addPhase(1, 50000)
                .addPhase(2, 30000)
                .addPhase(3, 15000)
                .build();

        System.out.println("[Fluent Boss Builder Result]");
        fireDragon.displayInfo();


        EnemyDirector director = new EnemyDirector(new BasicEnemyBuilder());
        Enemy iceMinion = director.createMinion(iceFactory);
        System.out.println("[Director -> BasicEnemyBuilder -> Minion]");
        iceMinion.displayInfo();

        director.setBuilder(new BossEnemyBuilder());
        Enemy shadowRaidBoss = director.createRaidBoss(shadowFactory);
        System.out.println("[Director -> BossEnemyBuilder -> RaidBoss]");
        shadowRaidBoss.displayInfo();

        System.out.println("Factory Method note: build() is the factory method; Director calls build() without knowing concrete builder.\n");



        // PART 3: PROTOTYPE PATTERN

        printHeader("PART 3: PROTOTYPE - Enemy Cloning & Variants");

        EnemyRegistry registry = new EnemyRegistry();


        Goblin goblinTemplate = new Goblin("Goblin");
        registry.registerTemplate("goblin", goblinTemplate);

        registry.registerTemplate("dragon", fireDragon);

        System.out.println("Templates registered: " + registry.listTemplates() + "\n");


        Enemy eliteGoblin = registry.createFromTemplate("goblin");
        eliteGoblin.multiplyStats(2.0);
        eliteGoblin.setAIBehavior("AGGRESSIVE");

        Enemy championGoblin = registry.createFromTemplate("goblin");
        championGoblin.multiplyStats(5.0);
        championGoblin.addAbility(new FlameBreath());

        Enemy goblinKing = registry.createFromTemplate("goblin");
        goblinKing.multiplyStats(10.0);
        goblinKing.setAIBehavior("BOSS");
        goblinKing.addPhase(1, goblinKing.getHealth());
        goblinKing.addPhase(2, Math.max(1, goblinKing.getHealth() / 2));
        goblinKing.addPhase(3, Math.max(1, goblinKing.getHealth() / 5));

        System.out.println("[Goblin Variants]");
        eliteGoblin.displayInfo();
        championGoblin.displayInfo();
        goblinKing.displayInfo();


        Enemy iceDragon = makeElementalVariant(registry, "dragon", iceFactory, "Ice Dragon", 1.05);
        Enemy shadowDragon = makeElementalVariant(registry, "dragon", shadowFactory, "Shadow Dragon", 1.10);

        System.out.println("[Dragon Elemental Variants]");
        iceDragon.displayInfo();
        shadowDragon.displayInfo();


        demonstrateDeepCopy(registry, "goblin", goblinTemplate);



        // PART 4: ALL PATTERNS WORKING TOGETHER
        printHeader("PART 4: ALL PATTERNS WORKING TOGETHER");


        EnemyComponentFactory themeFactory = shadowFactory;


        Enemy bossBuiltOnce = new BossEnemyBuilder()
                .setName("Shadow Overlord")
                .setHealth(60000)
                .setDamage(600)
                .setDefense(220)
                .setSpeed(55)
                .setElement(themeFactory.getElement())
                .setAbilities(themeFactory.createAbilities())
                .setLootTable(themeFactory.createLootTable())
                .setAI(themeFactory.createAIBehavior())
                .addPhase(1, 60000)
                .addPhase(2, 35000)
                .addPhase(3, 18000)
                .build();


        registry.registerTemplate("shadow-overlord", bossBuiltOnce);


        Enemy greaterOverlord = registry.createFromTemplate("shadow-overlord");
        greaterOverlord.multiplyStats(1.5);

        Enemy mythicOverlord = registry.createFromTemplate("shadow-overlord");
        mythicOverlord.multiplyStats(2.0);
        mythicOverlord.addAbility(new FlameBreath());

        System.out.println("[Built once]");
        bossBuiltOnce.displayInfo();

        System.out.println("[Clone -> 1.5x stats]");
        greaterOverlord.displayInfo();

        System.out.println("[Clone -> 2.0x stats + extra ability]");
        mythicOverlord.displayInfo();


        // SUMMARY
        printHeader("PATTERN SUMMARY");
        System.out.println("Abstract Factory: Themed component families (Fire, Ice, Shadow)");
        System.out.println("Builder: Step-by-step enemy construction with fluent interface + validation");
        System.out.println("Factory Method: Embedded in Builder.build() and Director delegation");
        System.out.println("Prototype: Templates + deep-copy cloning into variants");

        System.out.println("\n=== Demo Complete ===");
    }

    private static void printHeader(String title) {
        System.out.println("============================================");
        System.out.println(title);
        System.out.println("============================================\n");
    }

    private static void createThemeDemo(EnemyComponentFactory factory, String label) {
        List<Ability> abilities = factory.createAbilities();
        LootTable loot = factory.createLootTable();
        String ai = factory.createAIBehavior();

        System.out.println("[" + label + " Factory]");
        System.out.println("Element: " + factory.getElement());
        System.out.println("AI: " + ai);
        System.out.println("Abilities: " + abilities.stream().map(Ability::getName).toList());
        System.out.println("Loot: " + loot.getItems());
        System.out.println();
    }

    private static Enemy makeElementalVariant(EnemyRegistry registry, String key,
                                              EnemyComponentFactory factory,
                                              String newName, double statMultiplier) {
        Enemy e = registry.createFromTemplate(key);

        e.setElement(factory.getElement());
        e.setAIBehavior(factory.createAIBehavior());
        e.setAbilities(factory.createAbilities());
        e.setLootTable(factory.createLootTable());
        e.multiplyStats(statMultiplier);
        return e;
    }

    private static void demonstrateDeepCopy(EnemyRegistry registry, String key, Enemy originalTemplate) {
        Enemy clone = registry.createFromTemplate(key);
        int beforeTemplate = originalTemplate.getAbilities().size();

        clone.addAbility(new FlameBreath());

        int afterTemplate = originalTemplate.getAbilities().size();
        int afterClone = clone.getAbilities().size();

        System.out.println("=== Deep Copy Proof ===");
        System.out.println("Template abilities BEFORE: " + beforeTemplate);
        System.out.println("Template abilities AFTER : " + afterTemplate + "  (must be same)");
        System.out.println("Clone abilities AFTER    : " + afterClone + "  (must be bigger)");
        System.out.println();
    }
}