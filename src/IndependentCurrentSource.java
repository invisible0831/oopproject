import java.lang.Math;
public class IndependentCurrentSource extends Element {
    double frequency;
    double phase;
    double amplitude;
    @Override
    void updateCurrent() {
        double phase1 = Math.toRadians(2 * Math.PI * this.frequency * Solve.getInstance().getTime() + this.phase);
        this.current = this.value + this.amplitude * Math.sin(phase1);
    }
    public IndependentCurrentSource(String name, int positiveNode, int negativeNode, double value, double amplitude, double frequency, double phase){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.value = value;
        this.frequency = frequency;
        this.phase = phase;
        this.amplitude = amplitude;
    }
}
