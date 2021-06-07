public class Capacitor extends Element{
    double pastDifferentialVoltage = 0;
    public Capacitor(String name, int positiveNode, int negativeNode, double value){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.value = value;
    }
    @Override
    void updateCurrent() {
        this.pastDifferentialVoltage = Initial.getInstance().nodes.get(this.positiveNode).pastVoltage - Initial.getInstance().nodes.get(this.negativeNode).pastVoltage;
        current = (this.differentialVoltage - this.pastDifferentialVoltage) / Solve.getInstance().getDt() * this.value;
    }
}
