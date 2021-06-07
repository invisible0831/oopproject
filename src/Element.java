import java.util.ArrayList;
import java.util.List;

public abstract class Element {
    public String name;
    public int positiveNode;
    public int negativeNode;
    public double value;
    public double current = 0;
    public double pastCurrent = 0;
    public double differentialVoltage = 0;
    boolean isCurrentUpdated = false;
    public String currentResults = "";
    public String differentialVoltageResults = "";
    public String powerResults = "";
    abstract void updateCurrent();
    public void updatePastCurrent(){
        this.pastCurrent = this.current;
    }
    public void updateDifferentialVoltage(){
        this.differentialVoltage = Initial.getInstance().nodes.get(this.positiveNode).voltage - Initial.getInstance().nodes.get(this.negativeNode).voltage;
    }
}
