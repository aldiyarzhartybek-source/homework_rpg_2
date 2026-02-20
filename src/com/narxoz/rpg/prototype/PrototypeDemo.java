package com.narxoz.rpg.prototype;

import com.narxoz.rpg.combat.FlameBreath;
import com.narxoz.rpg.enemy.DragonBoss;
import com.narxoz.rpg.enemy.Enemy;
import com.narxoz.rpg.enemy.Goblin;
import com.narxoz.rpg.factory.EnemyComponentFactory;
import com.narxoz.rpg.factory.FireComponentFactory;
import com.narxoz.rpg.factory.IceComponentFactory;
import com.narxoz.rpg.factory.ShadowComponentFactory;

import java.util.List;

public final class PrototypeDemo {

    public static void run() {
        EnemyRegistry registry = new EnemyRegistry();


        Goblin goblinTemplate = new Goblin("Goblin");
        registry.registerTemplate("goblin", goblinTemplate);

        DragonBoss dragonTemplate = new DragonBoss(
                "Dragon",
                50000, 500, 200, 50,
                "NONE",
                List.of(),
                50000, 30000, 15000,
                null,
                "BOSS",
                true, true, 20
        );
        registry.registerTemplate("dragon", dragonTemplate);


        Enemy eliteGoblin = registry.createFromTemplate("goblin");
        eliteGoblin.setElement("NONE");
        eliteGoblin.setAIBehavior("AGGRESSIVE");
        eliteGoblin.multiplyStats(2.0);

        Enemy championGoblin = registry.createFromTemplate("goblin");
        championGoblin.multiplyStats(5.0);
        championGoblin.addAbility(new FlameBreath());

        Enemy goblinKing = registry.createFromTemplate("goblin");
        goblinKing.multiplyStats(10.0);
        goblinKing.setAIBehavior("BOSS");
        goblinKing.addPhase(1, goblinKing.getHealth());
        goblinKing.addPhase(2, Math.max(1, goblinKing.getHealth() / 2));
        goblinKing.addPhase(3, Math.max(1, goblinKing.getHealth() / 5));


        Enemy cloneForProof = registry.createFromTemplate("goblin");
        cloneForProof.addAbility(new FlameBreath());
        System.out.println("=== Deep Copy Proof ===");
        System.out.println("Clone abilities: " + cloneForProof.getAbilities().size());
        System.out.println("Template abilities: " + goblinTemplate.getAbilities().size());
        System.out.println("(Template must NOT change)\n");


        Enemy fireDragon = makeElementalDragon(registry, "dragon", new FireComponentFactory(), "Fire Dragon");
        Enemy iceDragon = makeElementalDragon(registry, "dragon", new IceComponentFactory(), "Ice Dragon");
        Enemy shadowDragon = makeElementalDragon(registry, "dragon", new ShadowComponentFactory(), "Shadow Dragon");


        System.out.println("=== Variants ===");
        eliteGoblin.displayInfo();
        championGoblin.displayInfo();
        goblinKing.displayInfo();

        fireDragon.displayInfo();
        iceDragon.displayInfo();
        shadowDragon.displayInfo();
    }

    private static Enemy makeElementalDragon(EnemyRegistry registry, String key,
     EnemyComponentFactory factory, String name)
    {
        Enemy dragon = registry.createFromTemplate(key);
        dragon.setElement(factory.getElement());
        dragon.setAIBehavior(factory.createAIBehavior());
        dragon.setAbilities(factory.createAbilities());
        dragon.setLootTable(factory.createLootTable());
        dragon.multiplyStats(1.1);
        return dragon;
    }
}