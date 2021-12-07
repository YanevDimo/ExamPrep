package glacialExpedition.repositories;

import glacialExpedition.models.states.State;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class StateRepository implements Repository<State>{

    Map<String, State> stateMap;

    public StateRepository() {
        this.stateMap = new LinkedHashMap<>();
    }



    @Override
    public Collection getCollection() {
        return Collections.unmodifiableCollection(this.stateMap.values());
    }

    @Override
    public void add(State entity) {
       this.stateMap.put(entity.getName(),entity);
    }

    @Override
    public boolean remove(State entity) {
        return this.stateMap.remove(entity.getName()) != null;
    }

    @Override
    public State byName(String name) {
        return this.stateMap.get(name);
    }
}
