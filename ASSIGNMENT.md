# Homework 2: Detailed Assignment Instructions

## Background Story

Your RPG game (from Homework 1) has heroes — warriors, mages, and archers. Now it needs **enemies** for those heroes to fight!

The lead game designer gives you these requirements:

### Enemy System Requirements
- Enemies range from simple minions (Goblins) to complex raid bosses (Ancient Dragons)
- Boss enemies have **15+ attributes**: stats, abilities, phases, loot tables, AI behavior, elements
- The game needs **hundreds of enemy variants**: difficulty tiers, elemental types, regional variants
- Enemy components (abilities, loot, AI) must be **themed** — Fire enemies must have fire abilities, fire loot, and matching AI
- **Must be efficient** — building every variant from scratch wastes developer time
- **Must be maintainable** — adding a new enemy theme should not require changing existing code

### Why Design Patterns?

Without patterns, you'd face:

**Problem 1: Constructor Hell**
```java
// 15 parameters — which is which?!
new DragonBoss("Fire Dragon", 50000, 500, 200, 50, "FIRE",
    abilities, 30000, 15000, 5000, loot, "AGGRESSIVE", true, true, 20);
```

**Problem 2: Variant Explosion**
```java
// Creating each variant manually?
Enemy fireGoblin = new Goblin("Fire Goblin", 100, 15, 5, 35, "FIRE", fireAbilities...);
Enemy iceGoblin = new Goblin("Ice Goblin", 100, 15, 5, 35, "ICE", iceAbilities...);
// Repeat for EVERY variant? That's insane!
```

**Problem 3: Theme Mixing**
```java
// Nothing prevents this bug!
Enemy dragon = new Dragon();
dragon.setAbilities(fireAbilities);
dragon.setLoot(iceLoot);  // MISMATCH! Fire dragon drops ice items?!
```

Your job: **Use Builder, Prototype, Abstract Factory, and Factory Method to solve these problems.**

---

## Part 1: Abstract Factory Pattern — Themed Enemy Components

### The Problem
Enemies have themed components that must match. A Fire Dragon must have:
- Fire abilities (Flame Breath, Fire Shield, Meteor Storm)
- Fire loot drops (Fire Gem, Dragon Scale, Flame Rune)
- Fire-appropriate AI (Aggressive)

You CANNOT mix Fire abilities with Ice loot — that breaks the game theme.

### Your Tasks

#### 1.1 Complete the Ability Interface
- Define what all abilities have in common
- Methods: `getName()`, `getDamage()`, `getDescription()`, `clone()`
- Remember: abilities must be clonable for Prototype pattern!

#### 1.2 Complete the LootTable Interface
- Define what all loot tables have in common
- Methods: `getItems()`, `getGoldDrop()`, `getExperienceDrop()`, `clone()`
- Remember: loot tables must be clonable too!

#### 1.3 Implement Themed Abilities (6+ abilities)
Create at least 2 abilities per theme:

**Fire Theme:**
- FlameBreath (AoE damage + burn)
- FireShield (defensive buff)
- MeteorStorm (ultimate, high AoE damage) *[optional 3rd]*

**Ice Theme:**
- FrostBreath (damage + slow)
- IceShield (defensive buff)
- Blizzard (ultimate, AoE damage) *[optional 3rd]*

**Shadow Theme:**
- ShadowStrike (high single-target damage + blind)
- Vanish (stealth/evasion)
- DarkNova (ultimate, AoE damage) *[optional 3rd]*

#### 1.4 Implement Themed Loot Tables (3+)
- FireLootTable (Fire Gems, Dragon Scales, Flame Runes)
- IceLootTable (Ice Gems, Frost Scales, Ice Runes)
- ShadowLootTable (Shadow Gems, Dark Essences, Shadow Runes)

#### 1.5 Implement Concrete Component Factories (3)
Using the provided `EnemyComponentFactory` interface:
- `FireComponentFactory` — creates fire abilities + fire loot + aggressive AI
- `IceComponentFactory` — creates ice abilities + ice loot + defensive AI
- `ShadowComponentFactory` — creates shadow abilities + shadow loot + tactical AI

#### 1.6 Questions to Guide You
- How is this similar to HW1's EquipmentFactory?
- What guarantees Fire abilities always come with Fire loot?
- How would you add a "Nature" theme later?

---

## Part 2: Builder Pattern — Complex Enemy Construction

### The Problem
Creating complex boss enemies requires setting **15+ attributes**. The telescoping constructor in `DragonBoss.java` is unreadable, error-prone, and unmaintainable.

### Your Tasks

#### 2.1 Design the Builder Interface
Create `EnemyBuilder` with fluent methods:
- `setName(String name)` — returns `this` for chaining
- `setHealth(int health)` — returns `this`
- `setDamage(int damage)` — returns `this`
- `setDefense(int defense)` — returns `this`
- `setSpeed(int speed)` — returns `this`
- `setElement(String element)` — returns `this`
- `addAbility(Ability ability)` — returns `this`
- `setAbilities(List<Ability> abilities)` — returns `this`
- `addPhase(int phaseNumber, int healthThreshold)` — returns `this`
- `setLootTable(LootTable loot)` — returns `this`
- `setAI(String aiBehavior)` — returns `this`
- `build()` — validates and returns final `Enemy`

#### 2.2 Implement Concrete Builders (2+)
- `BasicEnemyBuilder` — builds simple enemies (Goblins, Skeletons, Orcs)
- `BossEnemyBuilder` — builds complex bosses (Dragons, Demon Lords)

#### 2.3 Implement Director
Create `EnemyDirector` with preset construction methods:
- `createMinion(EnemyComponentFactory factory)` — simple weak enemy
- `createElite(EnemyComponentFactory factory)` — medium difficulty
- `createMiniBoss(EnemyComponentFactory factory)` — challenging enemy
- `createRaidBoss(EnemyComponentFactory factory)` — ultimate challenge

The Director uses Builder internally — note how Factory Method appears here!

#### 2.4 Validation
Your `build()` method must validate:
- Name is not null/empty
- Health is positive
- At least the mandatory fields are set
- Throw `IllegalStateException` if validation fails

#### 2.5 Questions to Guide You
- What makes this different from Factory Method?
- Which fields are mandatory vs optional?
- Why should the built Enemy be immutable?
- Where is Factory Method in this structure?

---

## Part 3: Prototype Pattern — Enemy Variants

### The Problem
You need hundreds of enemy variants:
- **Difficulty tiers**: Normal Goblin (1x) → Elite Goblin (2x) → Champion (5x) → King (10x)
- **Elemental variants**: Dragon → Fire Dragon → Ice Dragon → Shadow Dragon
- **Regional variants**: Forest Goblin → Cave Goblin → Swamp Goblin

Building each from scratch wastes time. Clone and modify!

### Your Tasks

#### 3.1 Implement Clone Methods
Add `clone()` to your Enemy implementations with **DEEP COPY**:
- Primitive fields (health, damage) → direct copy
- Ability list → new list with cloned abilities
- Loot table → cloned loot table
- Phase map → new map with copied entries

**Critical**: Test that modifying a clone does NOT affect the original!

#### 3.2 Implement Enemy Registry
Create `EnemyRegistry` (in `prototype/` package):
- `registerTemplate(String key, Enemy template)` — stores a template
- `createFromTemplate(String key)` — returns a CLONE (not the original!)
- `listTemplates()` — shows all available templates

**Critical**: The registry must return clones, never the original template!

#### 3.3 Create Base Templates and Variants
Register at least 3 base templates, then create 3+ variants from each:

**Base: Goblin**
→ Elite Goblin (2x stats)
→ Champion Goblin (5x stats + extra abilities)
→ Goblin King (10x stats + boss phases)

**Base: Dragon** (built using Builder)
→ Fire Dragon (+ fire components from FireComponentFactory)
→ Ice Dragon (+ ice components from IceComponentFactory)
→ Shadow Dragon (+ shadow components from ShadowComponentFactory)

**Base: Skeleton** (or another of your choice)
→ 2+ variants

#### 3.4 Deep Copy Verification
In your demo, prove deep copy works:
```java
Enemy clone = registry.createFromTemplate("goblin");
clone.addAbility(new PoisonStab());
// Show that original goblin template does NOT have PoisonStab!
```

#### 3.5 Questions to Guide You
- What's the difference between deep and shallow copy?
- Why does the registry return clones instead of originals?
- When would Prototype be better than Builder for creating variants?

---

## Part 4: Factory Method — Identify and Explain

### Your Tasks

#### 4.1 Identify Factory Method in Your Code
Factory Method appears in two places:

1. **Builder.build()** — the factory method that creates Enemy objects
2. **Director delegation** — Director calls builder.build() polymorphically

#### 4.2 Add Explanatory Comments
In your code, add comments identifying Factory Method:
```java
// FACTORY METHOD: build() is the factory method.
// Different builders (BasicEnemyBuilder, BossEnemyBuilder) override
// how the Enemy is created — this is Factory Method delegation.
public Enemy build() {
    // ... validation ...
    return new ConcreteEnemy(...);
}
```

#### 4.3 Questions to Guide You
- How is Factory Method different from Builder?
- What's the "product" being created?
- How does the Director use Factory Method without knowing the concrete builder?

---

## Part 5: Integration & Demonstration

### 5.1 Complete Main.java
Your demo must show **all 4 patterns** clearly:

**Part 1**: Abstract Factory creates themed components
**Part 2**: Builder constructs complex enemies using factory components
**Part 3**: Prototype clones enemies into variants
**Part 4**: Full pipeline — all patterns working together

### 5.2 Demonstrate Extensibility
In comments or demo, explain how easy it is to:
- Add a new element theme (e.g., Nature)
- Add a new enemy type (e.g., Lich)
- Add a new difficulty tier (e.g., Mythic)

---

## Part 6: UML Diagrams

Create 2 UML class diagrams:

### Diagram 1: Builder + Factory Method
Show:
- EnemyBuilder interface and concrete builders
- Director class and its relationship to Builder
- Enemy class hierarchy
- How `build()` acts as Factory Method
- Key methods and relationships

### Diagram 2: Prototype + Abstract Factory
Show:
- Enemy clone() method and Prototype structure
- EnemyRegistry and template management
- EnemyComponentFactory and concrete factories
- Themed product families (abilities, loot)
- Key methods and relationships

### UML Requirements
- Show all classes, interfaces, and abstract classes
- Show relationships (inheritance, implementation, association, dependency)
- Include key methods and attributes
- Use proper UML notation

**Tools:** draw.io, Lucidchart, PlantUML, or hand-drawn (if clear)

---

## Grading Rubric (Total: 100 points)

### Builder Pattern — PRIMARY (25 points)
- Builder interface with fluent methods: 5 pts
- 2+ concrete builder implementations: 5 pts
- Director with preset construction methods: 5 pts
- Mandatory vs optional field handling + validation: 5 pts
- No telescoping constructors anywhere: 5 pts

### Prototype Pattern — PRIMARY (25 points)
- Clone method with correct deep copy: 5 pts
- Deep copy for complex fields (abilities, loot, phases): 8 pts
- Template registry/manager: 7 pts
- 3+ enemy variants from base templates: 5 pts

### Abstract Factory — SUPPORTING (15 points)
- EnemyComponentFactory interface properly used: 3 pts
- 3+ themed concrete factories: 6 pts
- Guaranteed component consistency (no mixing): 3 pts
- Builder integrates with component factories: 3 pts

### Factory Method — SUPPORTING (5 points)
- Factory Method visible in Builder.build() / Director: 3 pts
- Student identifies/explains FM in comments: 2 pts

### Code Quality (10 points)
- Java naming conventions: 2 pts
- Proper encapsulation and immutability: 3 pts
- Code organization and readability: 3 pts
- No duplication: 2 pts

### UML Diagrams (15 points)
- Builder + Factory Method diagram: 7 pts
- Prototype + Abstract Factory diagram: 8 pts

### Demonstration (5 points)
- Main.java demonstrates all 4 patterns: 3 pts
- Clear, professional output: 2 pts

---

## Common Pitfalls to Avoid

### Builder Pitfalls

**Telescoping constructors still present:**
```java
// You should REMOVE this and use Builder instead!
new DragonBoss("name", 50000, 500, 200, 50, "FIRE", ...);
```

**Not using fluent interface:**
```java
// BAD: Not chainable
builder.setHealth(100);
builder.setDamage(50);
Enemy e = builder.build();

// GOOD: Fluent chaining
Enemy e = builder.setHealth(100).setDamage(50).build();
```

**No validation in build():**
```java
// BAD: Builds with missing required fields
public Enemy build() { return new Enemy(...); }

// GOOD: Validates first
public Enemy build() {
    if (name == null) throw new IllegalStateException("Name required!");
    return new Enemy(...);
}
```

### Prototype Pitfalls

**Shallow copy only:**
```java
// WRONG: Shared reference!
copy.abilities = this.abilities;

// RIGHT: Deep copy!
copy.abilities = new ArrayList<>();
for (Ability a : this.abilities) {
    copy.abilities.add(a.clone());
}
```

**Registry returns original:**
```java
// WRONG: Returns the actual template!
return templates.get(key);

// RIGHT: Returns a clone!
return templates.get(key).clone();
```

### Abstract Factory Pitfalls

**Mixing themes:**
```java
// If your code allows this, Abstract Factory is not working:
abilities = fireFactory.createAbilities();
loot = iceFactory.createLootTable();  // MISMATCH!
```

**Not using the factory interface:**
```java
// WRONG: Depends on concrete class
FireComponentFactory factory = new FireComponentFactory();

// RIGHT: Depends on abstraction
EnemyComponentFactory factory = new FireComponentFactory();
```

---

## Tips for Success

1. **Study DragonBoss.java first** — feel the constructor pain before solving it
2. **Implement interfaces before classes** — define contracts first
3. **Build bottom-up**: Ability → LootTable → ComponentFactory → Builder → Prototype
4. **Test clone() thoroughly** — modify clones and check originals don't change
5. **Draw UML before coding** — plan your class hierarchy
6. **Read hint files** — `BUILDER_HINTS.md` and `FACTORY_HINTS.md` have guidance
7. **Run Main.java often** — catch errors early

---

## Submission Checklist

- [ ] All TODOs are completed or removed
- [ ] Code compiles without errors
- [ ] Main.java demonstrates all 4 patterns
- [ ] Builder: fluent interface, no telescoping constructors, validation
- [ ] Prototype: deep copy, registry, 3+ templates with variants
- [ ] Abstract Factory: 3+ themed factories, consistent components
- [ ] Factory Method: identified in Builder/Director with comments
- [ ] 2 UML diagrams included (PDF or image)
- [ ] Code follows Java naming conventions
- [ ] No hardcoded theme mixing (Abstract Factory enforces consistency)

---

## Academic Integrity

This is an individual assignment. You may:
- Research design patterns online
- Discuss concepts with classmates
- Ask instructor for clarification

**The struggle with design decisions IS the learning!**

---

## Resources

- Refactoring Guru: Builder, Prototype, Abstract Factory, Factory Method
- "Head First Design Patterns" — relevant chapters
- "Effective Java" by Joshua Bloch — Item 2 (Builder pattern)
- Your Homework 1 implementation (for Abstract Factory reference)
- `BUILDER_HINTS.md` and `FACTORY_HINTS.md` in this project

---

Good luck! By completing this assignment, you will have mastered **all four creational design patterns** and understood how they work together in real systems.

**These patterns appear everywhere** — game engines, web frameworks, database libraries, and enterprise applications. What you learn here will serve you throughout your career.
