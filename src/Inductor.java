public class Inductor extends Element {
    @Override
    void updateCurrent() {
        this.current = this.pastCurrent + this.differentialVoltage * Solve.getInstance().getDt() / this.value;
    }
    public Inductor(String name, int positiveNode, int negativeNode, double value){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.value = value;
    }
}
