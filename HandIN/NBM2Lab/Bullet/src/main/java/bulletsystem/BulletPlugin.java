package bulletsystem;


import dk.sdu.hannehue.common.bullet.Bullet;
import dk.sdu.hannehue.data.Entity;
import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;
import dk.sdu.hannehue.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
        @ServiceProvider(service = IGamePluginService.class),
})
public class BulletPlugin implements IGamePluginService {

    private Entity bullet;

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }

}
