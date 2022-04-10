package dk.sdu.hannehue.services;

import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;

public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
