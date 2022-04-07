package dk.sdu.hannehue.common.bullet;

import dk.sdu.hannehue.data.Entity;
import dk.sdu.hannehue.data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity entity, GameData gameData);
}
