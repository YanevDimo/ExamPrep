package glacialExpedition.models.explorers;

public class AnimalExplorer extends BaseExplorer {
    private static final int UNIT_ENERGY = 100;

    public AnimalExplorer(String name) {
        super(name, UNIT_ENERGY);
    }
}
