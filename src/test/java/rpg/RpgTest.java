package rpg;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RpgTest {

    @Test
    public void new_character_should_start_with_1000_HP() {
        Assertions.assertThat(aNewCharacter().health()).isEqualTo(1000);
    }

    private RpgCharacter aNewCharacter() {
        return new RpgCharacter();
    }

    private class RpgCharacter {
        public int health() {
            return 1000;
        }
    }
}
