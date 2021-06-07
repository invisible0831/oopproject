public class VoltageSourceDependCurrent extends Element{
    String nameTwo;
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
    public VoltageSourceDependCurrent(String name, int positiveNode, int negativeNode, String nameTwo, double value){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.nameTwo = nameTwo;
        this.value = value;
    }
    @Override
    public void updateDifferentialVoltage(){
        this.differentialVoltage = this.value * Initial.getInstance().elementMap.get(this.nameTwo).current;
    }
}
