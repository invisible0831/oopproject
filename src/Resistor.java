public class Resistor extends Element {
    @Override
    void updateCurrent(){
        this.current = this.differentialVoltage / this.value;
    }
    public Resistor(String name, int positiveNode, int negativeNode, double value){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.value = value;
    }
}
