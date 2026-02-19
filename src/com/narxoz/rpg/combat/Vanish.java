package com.narxoz.rpg.combat;

public final class Vanish implements Ability {

    @Override public String getName() {
        return "Vanish";
    }

    @Override public int getDamage() {
        return 0;
    }

    @Override public String getDescription() {
        return "Stealth/evasion move. Harder to hit for a short time.";
    }

    @Override public Ability clone() {
        return new Vanish();
    }
}
