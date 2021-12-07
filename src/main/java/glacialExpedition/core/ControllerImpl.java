package glacialExpedition.core;

import glacialExpedition.common.ConstantMessages;
import glacialExpedition.common.ExceptionMessages;
import glacialExpedition.models.explorers.AnimalExplorer;
import glacialExpedition.models.explorers.Explorer;
import glacialExpedition.models.explorers.GlacierExplorer;
import glacialExpedition.models.explorers.NaturalExplorer;
import glacialExpedition.models.mission.Mission;
import glacialExpedition.models.mission.MissionImpl;
import glacialExpedition.models.states.State;
import glacialExpedition.models.states.StateImpl;
import glacialExpedition.repositories.ExplorerRepository;
import glacialExpedition.repositories.Repository;
import glacialExpedition.repositories.StateRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static glacialExpedition.common.ConstantMessages.*;

public class ControllerImpl implements Controller {

    private Repository<Explorer> explorerRepository;
    private Repository<State> stateRepository;
    private int stateExplored;

    public ControllerImpl() {
        this.explorerRepository = new ExplorerRepository();
        this.stateRepository = new StateRepository();

    }

    @Override
    public String addExplorer(String type, String explorerName) {
        Explorer explorer = null;
        switch (type) {
            case "AnimalExplorer":
                explorer = new AnimalExplorer(explorerName);
                break;
            case "GlacierExplorer":
                explorer = new GlacierExplorer(explorerName);
                break;
            case "NaturalExplore":
                explorer = new NaturalExplorer(explorerName);

                break;
            default:
                throw new IllegalArgumentException(ExceptionMessages.EXPLORER_INVALID_TYPE);
        }
        this.explorerRepository.add(explorer);
        return String.format(EXPLORER_ADDED, type, explorerName);
    }

    @Override
    public String addState(String stateName, String... exhibits) {
        State state = new StateImpl(stateName);
        Collection<String> stateExhibits = state.getExhibits();
        Collections.addAll(stateExhibits, exhibits);
        this.stateRepository.add(state);

        return String.format(STATE_ADDED, stateName);
    }

    @Override
    public String retireExplorer(String explorerName) {
        Explorer explorer = explorerRepository.byName(explorerName);
        if (explorer == null) {
            throw new IllegalArgumentException(String.format(ExceptionMessages.EXPLORER_DOES_NOT_EXIST, explorerName));
        }
        this.explorerRepository.remove(explorer);
        return String.format(EXPLORER_RETIRED, explorerName);
    }

    @Override
    public String exploreState(String stateName) {
        //взимаме изследователи с повече от 50 енергия
        List<Explorer> explores = this.explorerRepository.getCollection().stream()
                .filter(explorer -> explorer.getEnergy() > 50)
                .collect(Collectors.toList());

        //...ако няма такива хвъляме EXCEPTION
        if (explores.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.STATE_EXPLORERS_DOES_NOT_EXISTS);
        }
        //ако има започваме мисията
        State stateToExplore = this.stateRepository.byName(stateName);
        Mission mission = new MissionImpl();
        mission.explore(stateToExplore, explores);

        //да се върне колко озследователи се изтощили
        long retired = explores.stream().filter(explorer -> explorer.getEnergy() == 0).count();
        this.stateExplored++;

        return String.format(STATE_EXPLORER, stateName, retired);
    }

    @Override
    public String finalResult() {

        StringBuilder result = new StringBuilder();
        result.append(String.format(FINAL_STATE_EXPLORED, this.stateExplored));
        result.append(System.lineSeparator());
        result.append(FINAL_EXPLORER_INFO);
        result.append(System.lineSeparator());

        Collection<Explorer> explorers = this.explorerRepository.getCollection();
        for (Explorer explorer : explorers) {
            result.append(System.lineSeparator());
            result.append(String.format(FINAL_EXPLORER_NAME, explorer.getName()));
            result.append(System.lineSeparator());
            result.append(String.format(FINAL_EXPLORER_ENERGY, explorer.getEnergy()));
            result.append(System.lineSeparator());
            if (explorer.getSuitcase().getExhibits().isEmpty()) {
                result.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS, "None"));
            } else {
                result.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER,
                        String.join(FINAL_EXPLORER_SUITCASE_EXHIBITS_DELIMITER, explorer.getSuitcase().getExhibits())));
            }
        }
        return result.toString();
//        StringBuilder sb =new StringBuilder();
//
//        sb.append(String.format(FINAL_STATE_EXPLORED, exploreState)).append(System.lineSeparator());
//
//        sb.append(FINAL_EXPLORER_INFO);
//
//        for (Explorer explorer : explorerRepository.getCollection()) {
//            sb.append(System.lineSeparator());
//            sb.append(String.format(FINAL_EXPLORER_NAME, explorer.getName())).append(System.lineSeparator());
//            sb.append(String.format(FINAL_EXPLORER_ENERGY, explorer.getEnergy())).append(System.lineSeparator());
//            String exhibits = explorer.getSuitcase().getExhibits().isEmpty()
//                    ? "None"
//                    : String.join(", ", explorer.getSuitcase().getExhibits());
//            sb.append(String.format(FINAL_EXPLORER_SUITCASE_EXHIBITS, exhibits));
//        }
//
//        return sb.toString().trim();
    }
}
