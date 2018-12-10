package rpg;

import org.assertj.core.api.Assertions;
import org.junit.Test;

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
        attacker.heal(target,100);
        Assertions.assertThat(target.health()).isEqualTo(0);
        Assertions.assertThat(target.isAlive()).isFalse();
    }

    @Test
    public void character_cannot_be_healed_over_1000() {
        RpgCharacter attacker = aNewCharacter();
        RpgCharacter target = aNewCharacter();
        attacker.heal(target,100);
        Assertions.assertThat(target.health()).isEqualTo(1000);
    }

    private RpgCharacter aNewCharacter() {
        return new RpgCharacter();
    }

    private class RpgCharacter {

        private int health = 1000;

        public int health() {
            return health;
        }

        public int level() {
            return 1;
        }

        public boolean isAlive() {
            return health() > 0;
        }

        public void attack(RpgCharacter target, int damage) {
            target.health = Math.max(0, target.health - damage);
        }

        public void heal(RpgCharacter target, int health) {
            if (target.isAlive()){
                target.health = Math.min(target.health + health, 1000);
            }
        }
    }

}
