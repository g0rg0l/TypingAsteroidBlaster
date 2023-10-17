package self.Gorgol.entity.utilities;

import self.Gorgol.entity.objects.asteroids.Asteroid;
import self.Gorgol.entity.objects.player.Player;

public class MathGameLogic {
    public static float getAngleFromPlayerToAsteroid(Player player, Asteroid asteroid) {
        Vector2f playerCenter = player.body.getCenter();
        Vector2f asteroidCenter = asteroid.body.getCenter();

        float x1 = playerCenter.x;
        float y1 = playerCenter.y;
        float x2 = asteroidCenter.x;
        float y2 = asteroidCenter.y;

        double angle = Math.PI / 2 + Math.atan2(y2 - y1, x2 - x1);

        return (float) angle;
    }
}
