public class IndependentVoltageSource extends Element {
    double frequency;
    double phase;
    double amplitude;
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
        double phase1 = Math.toRadians(2 * Math.PI * this.frequency * Solve.getInstance().getTime() + this.phase);
        this.differentialVoltage = this.value + this.amplitude * Math.sin(phase1);
    }
    public IndependentVoltageSource(String name, int positiveNode, int negativeNode, double value, double amplitude, double frequency, double phase){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.value = value;
        this.frequency = frequency;
        this.phase = phase;
        this.amplitude = amplitude;
    }
}
