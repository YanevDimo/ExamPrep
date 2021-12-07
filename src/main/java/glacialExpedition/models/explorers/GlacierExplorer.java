package glacialExpedition.models.explorers;

public class GlacierExplorer extends BaseExplorer{

    private static final int UNIT_ENERGY = 40;

    public GlacierExplorer(String name) {
        super(name, UNIT_ENERGY);
    }
}
