package dk.sdu.hannehue.asteroid;

import dk.sdu.hannehue.common.asteroid.Asteroid;
import dk.sdu.hannehue.data.Entity;
import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;
import dk.sdu.hannehue.data.entityparts.LifePart;
import dk.sdu.hannehue.data.entityparts.MovingPart;
import dk.sdu.hannehue.data.entityparts.PositionPart;
import dk.sdu.hannehue.services.IGamePluginService;
import dk.sdu.hannehue.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
        @ServiceProvider(service = IGamePluginService.class),
        @ServiceProvider(service = IPostEntityProcessingService.class),
})
public class AsteroidPlugin implements IGamePluginService, IPostEntityProcessingService {

    private Entity asteroid;

    public AsteroidPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        for (int i = 0; i < 5; i++){
        asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {

        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();

        float x = (float) Math.random() * gameData.getDisplayWidth();
        float y = (float) Math.random() * gameData.getDisplayHeight();

        Entity asteroidEntity = new Asteroid();

        asteroidEntity.add(new MovingPart(0, speed, speed, 0));
        asteroidEntity.add(new PositionPart(x, y, radians));
        asteroidEntity.add(new LifePart(6, 69));
        asteroidEntity.setRadius(15);

        return (Asteroid) asteroidEntity;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroid);
    }

    @Override
    public void process(GameData gameData, World world) {

    }
}
