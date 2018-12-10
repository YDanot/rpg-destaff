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
            target.health = target.health - damage;
        }
    }

}
