public class VoltageSourceDependVoltage extends Element {
    int dependPositiveNode;
    int dependNegativeNode;
    @Override
    void updateCurrent() {
        if(!isCurrentUpdated){
            Node node = Initial.getInstance().nodes.get(this.positiveNode);
            if(node.amountOfVoltageSourceCurrentIsNotUpdated() == 1){
                double allCurrent = node.getAllCurrent() - this.current;
                this.current = -1 * allCurrent;
                this.isCurrentUpdated = true;
            }
        }
        if(!isCurrentUpdated) {
            Node node = Initial.getInstance().nodes.get(this.negativeNode);
            if (node.amountOfVoltageSourceCurrentIsNotUpdated() == 1) {
                double allCurrent = node.getAllCurrent() + this.current;
                this.current = allCurrent;
                this.isCurrentUpdated = true;
            }
        }
    }
    @Override
    public void updateDifferentialVoltage(){
        this.differentialVoltage = this.value * (Initial.getInstance().nodes.get(dependPositiveNode).voltage - Initial.getInstance().nodes.get(dependNegativeNode).voltage);
    }
    public VoltageSourceDependVoltage(String name, int positiveNode, int negativeNode, int dependPositiveNode, int dependNegativeNode, double value){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.dependPositiveNode = dependPositiveNode;
        this.dependNegativeNode = dependNegativeNode;
        this.value = value;
    }
}
