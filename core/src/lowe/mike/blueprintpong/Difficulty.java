package lowe.mike.blueprintpong;

import com.badlogic.gdx.utils.ObjectMap;

/**
 * {@code Difficulty} enum represents the various difficulties
 * of the game.
 *
 * @author Mike Lowe
 */
public enum Difficulty {

    EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

    private static final ObjectMap<String, Difficulty> stringToEnum
            = new ObjectMap<String, Difficulty>();

    static {
        for (Difficulty difficulty : values()) {
            stringToEnum.put(difficulty.toString(), difficulty);
        }
    }

    private final String string;

    Difficulty(String string) {
        this.string = string;
    }

    /**
     * Takes a {@link String} and returns the {@code Difficulty} associated with it.
     *
     * @param string the {@link String} representation of the {@code Difficulty}
     * @return the {@code Difficulty}
     */
    public static Difficulty fromString(String string) {
        return stringToEnum.get(string);
    }

    @Override
    public String toString() {
        return string;
    }

}