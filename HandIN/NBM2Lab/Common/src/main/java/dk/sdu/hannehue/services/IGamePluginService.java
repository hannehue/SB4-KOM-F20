package dk.sdu.hannehue.services;


import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
