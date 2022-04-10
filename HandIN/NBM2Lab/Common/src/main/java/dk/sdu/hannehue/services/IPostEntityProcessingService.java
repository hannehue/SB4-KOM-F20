package dk.sdu.hannehue.services;


import dk.sdu.hannehue.data.GameData;
import dk.sdu.hannehue.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}
