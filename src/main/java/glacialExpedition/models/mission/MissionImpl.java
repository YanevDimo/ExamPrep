package glacialExpedition.models.mission;

import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.states.State;

import java.util.Collection;
import java.util.List;

public class MissionImpl implements Mission{



    @Override
    public void explore(State state, Collection<Explorer> explorers) {
        //iterate explorer
        //iterate exhibit
        Collection<String> stateExhibits = state.getExhibits();
        for (Explorer explorer : explorers) {
            while (explorer.canSearch() && stateExhibits.isEmpty());//stateExhibits.iterator().hasNext(){
                //explorer have energy
                //have exhibits
                String currentExhibits = stateExhibits.iterator().next();
                explorer.getSuitcase().getExhibits().add(currentExhibits);
                stateExhibits.remove(currentExhibits);
                explorer.search();// decrease energy
            }
        }
    }

