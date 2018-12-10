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

    private RpgCharacter aNewCharacter() {
        return new RpgCharacter();
    }

    private class RpgCharacter {
        public int health() {
            return 1000;
        }

        public int level() {
            return 1;
        }
    }
}
