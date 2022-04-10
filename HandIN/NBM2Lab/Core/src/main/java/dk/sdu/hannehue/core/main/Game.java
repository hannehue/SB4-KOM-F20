package dk.sdu.hannehue.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.hannehue.core.managers.GameInputProcessor;
import dk.sdu.hannehue.data.Entity;
import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;
import dk.sdu.hannehue.data.entityparts.PositionPart;
import dk.sdu.hannehue.services.IEntityProcessingService;
import dk.sdu.hannehue.services.IGamePluginService;
import dk.sdu.hannehue.services.IPostEntityProcessingService;
import org.openide.util.Lookup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private final Lookup lookup = Lookup.getDefault();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private List<IPostEntityProcessingService> postEntityProcessingServices = new ArrayList<>();
    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        // Lookup all Game Plugins using Lookup
        for (IGamePluginService iGamePlugin : getEntityPlugins()) {
            iGamePlugin.start(gameData, world);
        }
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessors()) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService postEntityProcessingService : getPostEntityProcessingService()) {
            postEntityProcessingService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

//            if (entity instanceof Enemy){
//                sr.setColor(1, 0, 0, 1);
//            } else if (entity instanceof Asteroid) {
//                sr.setColor(0, 0, 1, 1);
//            } else {
//                sr.setColor(1,1,1,1);
//            }

            sr.setColor(1,1,1,1);


            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                 i < shapex.length;
                 j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
//            PositionPart posPart = entity.getPart(PositionPart.class);
//            sr.begin(ShapeRenderer.ShapeType.Line);
//            sr.circle(posPart.getX(), posPart.getY(), entity.getRadius());
//            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessors() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IGamePluginService> getEntityPlugins() {
        return lookup.lookupAll(IGamePluginService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingService() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }
}

