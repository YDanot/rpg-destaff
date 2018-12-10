package rpg;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class RpgTest {

    @Test
    public void new_character_should_start_with_1000_HP() {
        Assertions.assertThat(aNewCharacter().health()).isEqualTo(1000);
    }

    @Test
    public void new_character_should_start_at_level_1() {
        Assertions.assertThat(aNewCharacter().level()).isEqualTo(1);
    }

    @Test
    public void new_character_should_be_alive() {
        Assertions.assertThat(aNewCharacter().isAlive()).isTrue();
    }

    @Test
    public void character_can_damage_another() {
        RpgCharacter attacker = aNewCharacter();
        RpgCharacter target = aNewCharacter();
        attacker.attack(target, 200);
        Assertions.assertThat(target.health()).isEqualTo(800);
    }

    @Test
    public void character_should_be_healed() {
        RpgCharacter attacker = aNewCharacter();
        RpgCharacter target = aNewCharacter();
        attacker.attack(target, 200);
        attacker.heal(target, 200);
        Assertions.assertThat(target.health()).isEqualTo(1000);
    }

    @Test
    public void character_can_die_from_damage() {
        RpgCharacter attacker = aNewCharacter();
        RpgCharacter target = aNewCharacter();
        attacker.attack(target, 1200);
        Assertions.assertThat(target.health()).isEqualTo(0);
        Assertions.assertThat(target.isAlive()).isFalse();
    }

    @Test
    public void dead_character_cannot_be_healed() {
        RpgCharacter attacker = aNewCharacter();
        RpgCharacter target = aNewCharacter();
        attacker.attack(target, 1200);
        attacker.heal(target, 100);
        Assertions.assertThat(target.health()).isEqualTo(0);
        Assertions.assertThat(target.isAlive()).isFalse();
    }

    @Test
    public void character_cannot_be_healed_over_1000() {
        RpgCharacter attacker = aNewCharacter();
        RpgCharacter target = aNewCharacter();
        attacker.heal(target, 100);
        Assertions.assertThat(target.health()).isEqualTo(1000);
    }

    private RpgCharacter aNewCharacter() {
        return new RpgCharacter();
    }

    interface HealthEvent {
        int replayOn(int currentHealth);
    }

    class DamageEvent implements HealthEvent {

        private final int amount;

        DamageEvent(int amount) {
            this.amount = amount;
        }

        @Override
        public int replayOn(int currentHealth) {
            return Math.max(0, currentHealth - amount);
        }

    }

    class HealEvent implements HealthEvent {

        private final int amount;

        HealEvent(int amount) {
            this.amount = amount;
        }

        @Override
        public int replayOn(int currentHealth) {
            return Math.min(RpgCharacter.MAX_HEALTH, currentHealth + amount);
        }

    }


    private class RpgCharacter {

        public static final int MAX_HEALTH = 1000;
        private int health = MAX_HEALTH;
        private Collection<HealthEvent> healthEvents = new ArrayList<>();

        public int health() {
            int currentHealth = this.health;
            for (HealthEvent event : healthEvents) {
                currentHealth = event.replayOn(currentHealth);
            }
            return currentHealth;
        }

        public int level() {
            return 1;
        }

        public boolean isAlive() {
            return health() > 0;
        }

        public void attack(RpgCharacter target, int damage) {
            target.attackedFor(damage);
        }

        public void heal(RpgCharacter target, int health) {
            target.healedFor(health);
        }

        private void healedFor(int health) {
            if (isAlive()) {
                healthEvents.add(new HealEvent(health));
            }
        }

        private void attackedFor(int damage) {
            healthEvents.add(new DamageEvent(damage));
        }

    }

}
