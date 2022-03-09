package dk.hannehue.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity collidingEntities: world.getEntities()){
                LifePart entityLife = entity.getPart(LifePart.class);
                LifePart collisionLife = collidingEntities.getPart(LifePart.class);

                if (entity.getID().equals(collidingEntities.getID())) continue;

                if (entityLife.getExpiration() <= 0) {
                    world.removeEntity(entity);
                    if (collisionLife.getExpiration() <= 0){
                        world.removeEntity(collidingEntities);
                    }
                }

                if (Collides(entity, collidingEntities)){
                    System.out.println("Collision between " + entity.getID() + " And " + collidingEntities.getID());
//                    if (entityLife.getLife() > 0) {
//                        entityLife.setLife(entityLife.getLife() - 1);
//                        entityLife.setIsHit(true);
//                        if (entityLife.getLife() <= 0){
//                            world.removeEntity(entity);
//                        }
//                    }
                }
            }
        }
    }

    public Boolean Collides(Entity entity1, Entity entity2){
        PositionPart entity1PositionPart = entity1.getPart(PositionPart.class);
        PositionPart entity2PositionPart = entity2.getPart(PositionPart.class);
        float dx = entity1PositionPart.getX() - entity2PositionPart.getX();
        float dy = entity1PositionPart.getY() - entity2PositionPart.getX();
        float distance = (float) Math.sqrt(dx * dx - dy * dy);

        return distance < entity1.getRadius() + entity2.getRadius();
    }
}
