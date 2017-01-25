package models.settings;

final class General {
    public enum Difficulty {
        EASY, NORMAL, HARD, INSANE
    }

    private Difficulty difficulty;

    public General() {
        this.difficulty = Difficulty.EASY;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty newDifficulty) {
        this.difficulty = newDifficulty;
    }
}
