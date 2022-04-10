package dk.sdu.hannehue.asteroid;

import dk.sdu.hannehue.common.asteroid.Asteroid;
import dk.sdu.hannehue.data.Entity;
import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;
import dk.sdu.hannehue.data.entityparts.MovingPart;
import dk.sdu.hannehue.data.entityparts.PositionPart;
import dk.sdu.hannehue.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.Random;

@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)
})
public class AsteroidControlSystem implements IEntityProcessingService {

    int numberOfPoints = 6  ;
    Random rnd = new Random(10);
    float angle = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);


            float speed = (float) Math.random() * 10f + 40f;
            if (rnd.nextInt() < 8) {
                movingPart.setMaxSpeed(speed);
                movingPart.setUp(true);
            } else {
                movingPart.setLeft(true);
            }

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            updateShape(asteroid);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();



        float[] shapex = new float[numberOfPoints];
        float[] shapey = new float[numberOfPoints];

        for (int i = 0; i < numberOfPoints; i++){
            shapex[i] = x + (float) Math.cos(angle + radians) * 26;
            shapey[i] = y + (float) Math.sin(angle + radians) * 26;
            angle += 2 * 3.1415f / numberOfPoints;
        }



        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
