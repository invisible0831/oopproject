public class CurrentSourceDependVoltage extends Element{
    int dependPositiveNode;
    int dependNegativeNode;
    @Override
    void updateCurrent() {
        this.current = this.value * (Initial.getInstance().nodes.get(dependPositiveNode).voltage - Initial.getInstance().nodes.get(dependNegativeNode).voltage);
    }
    public CurrentSourceDependVoltage(String name, int positiveNode, int negativeNode, int dependPositiveNode, int dependNegativeNode, double value){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.dependPositiveNode = dependPositiveNode;
        this.dependNegativeNode = dependNegativeNode;
        this.value = value;
    }
}
