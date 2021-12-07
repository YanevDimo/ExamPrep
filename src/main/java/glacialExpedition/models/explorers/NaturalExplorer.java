package glacialExpedition.models.explorers;

public class NaturalExplorer extends BaseExplorer{

    private static final int UNITS_ENERGY = 60;

    public NaturalExplorer(String name) {
        super(name, UNITS_ENERGY);
    }
    @Override
    public void search(){
        if (getEnergy() <= 7) {
            this.setEnergy(0);
        } else {
            this.setEnergy(this.getEnergy() - 7);
        }
        // or like that
        // this.energy = Math.max(0, this.energy - 7);
    }
}
