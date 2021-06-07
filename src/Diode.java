public class Diode extends Element {
    @Override
    void updateCurrent() {
        if(this.differentialVoltage <= 0){
            this.current = 0;
        }
        else {
            this.differentialVoltage = 0;
            Initial.getInstance().nodes.get(positiveNode).voltage = Initial.getInstance().nodes.get(negativeNode).voltage;
        }
    }
}
