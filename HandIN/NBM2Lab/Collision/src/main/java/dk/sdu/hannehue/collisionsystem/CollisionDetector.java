package dk.sdu.hannehue.collisionsystem;

import dk.sdu.hannehue.common.asteroid.Asteroid;
import dk.sdu.hannehue.common.bullet.Bullet;
import dk.sdu.hannehue.data.Entity;
import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;
import dk.sdu.hannehue.data.entityparts.LifePart;
import dk.sdu.hannehue.data.entityparts.PositionPart;
import dk.sdu.hannehue.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
        @ServiceProvider(service = IPostEntityProcessingService.class)
})
public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity collidingEntities: world.getEntities()){

                if (entity.getID().equals(collidingEntities.getID())) continue;
                if (entity instanceof Bullet && collidingEntities instanceof Bullet) continue;
                if (entity instanceof Asteroid && collidingEntities instanceof Asteroid) continue;

                LifePart entityLife = entity.getPart(LifePart.class);
                LifePart collisionLife = collidingEntities.getPart(LifePart.class);
                if (entityLife.getExpiration() <= 0) {
                    world.removeEntity(entity);
                    if (collisionLife.getExpiration() <= 0){
                        world.removeEntity(collidingEntities);
                    }
                }

                if (this.collides(entity, collidingEntities)){
                    if (entityLife.getLife() > 0) {
                        entityLife.setLife(entityLife.getLife() - 1);
                        entityLife.setIsHit(true);
                        if (entityLife.getLife() <= 0){
                            world.removeEntity(entity);
                        }
                    }
                }
            }
        }
    }

    private Boolean collides(Entity entity1, Entity entity2){
        PositionPart entity1PositionPart = entity1.getPart(PositionPart.class);
        PositionPart entity2PositionPart = entity2.getPart(PositionPart.class);
        float dx = entity1PositionPart.getX() - entity2PositionPart.getX();
        float dy = entity1PositionPart.getY() - entity2PositionPart.getX();
        float distance = (float) Math.sqrt(dx * dx - dy * dy);
        //System.out.println("Distance between " + entity1.getName() + " and " + entity2.getName() + " is: " + distance);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}
