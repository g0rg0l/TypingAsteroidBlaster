package self.Gorgol.ui;

public class GameProperties {
    private int maxAsteroidCountPerSpawn = 5;
    private float asteroidTimePerSpawn = 5 ;

    private float volume = -20;
    private final float maxVolume = 0f;
    private final float minVolume = -80f;

    public void upgradeDifficulty() {
        if (asteroidTimePerSpawn > 0.5f) {
            asteroidTimePerSpawn -= 0.5f;
        }
    }

    public void downgradeDifficulty() {
        if (asteroidTimePerSpawn < 8) {
            asteroidTimePerSpawn += 0.5f;
        }
    }

    public void upgradeVolume() {
        float step = (maxVolume - minVolume) / 10;

        if (volume + step <= maxVolume) {
            volume += step;
        }
    }

    public void downgradeVolume() {
        float step = (maxVolume - minVolume) / 10;

        if (volume - step >= minVolume) {
            volume -= step;
        }
    }

    public void setSoloAsteroidSpawn() {
        maxAsteroidCountPerSpawn = 1;
    }

    public void setBunchAsteroidSpawn() {
        maxAsteroidCountPerSpawn = 5;
    }

    public int getMaxAsteroidCountPerSpawn() {
        return maxAsteroidCountPerSpawn;
    }

    public float getAsteroidTimePerSpawn() {
        return asteroidTimePerSpawn;
    }

    public float getVolume() {
        return volume;
    }
}
