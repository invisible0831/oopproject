import java.util.*;

public class Solve {
    private static Solve instance = new Solve();
    private Solve(){}
    public static Solve getInstance(){
        return instance;
    }
    List<Node> queue = new LinkedList<>();
    Map<Integer, Union> unionMap = new HashMap<>();
    private double dv = -1, di = -1, dt = -1;
    private double time = 0, totalTime;
    public boolean hasError = false;
    public void setDi(double di) {
        this.di = di;
    }
    public void setDv(double dv) {
        this.dv = dv;
    }
    public void setDt(double dt) {
        this.dt = dt;
    }
    public double getDi() {
        return di;
    }
    public double getDt() {
        return dt;
    }
    public double getDv() {
        return dv;
    }
    public double getTotalTime() {
        return totalTime;
    }
    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }
    public double getTime() {
        return time;
    }
    public void setTime(double time){
        this.time = time;
    }
    private void createUnion(){
        for(Node node : queue) {
            if (node.union != 0) {
                if (unionMap.containsKey(node.union)) {
                    Union union = unionMap.get(node.union);
                    union.nodes.add(node);
                }
                else{
                    Union union = new Union();
                    union.nodes.add(node);
                    unionMap.put(node.union, union);
                }
            }
            else{
                Initial.getInstance().ground.nodes.add(node);
            }
        }
    }
    private void createQueue(){
        Node ground = Initial.getInstance().nodes.get(0);
        for(Element element : ground.elementsAtPositiveNode){
            Node node1;
            node1 = Initial.getInstance().nodes.get(element.negativeNode);
            char c = element.name.charAt(0);
            if((c == 'V') || (c == 'E') || (c == 'H')){
                node1.union = 0;
            }
            if(node1.added == false) {
                node1.added = true;
                queue.add(node1);
            }
        }
        for(Element element : ground.elementsAtNegativeNode){
            Node node1;
            node1 = Initial.getInstance().nodes.get(element.positiveNode);
            char c = element.name.charAt(0);
            if((c == 'V') || (c == 'E') || (c == 'H')){
                node1.union = 0;
            }
            if(node1.added == false) {
                node1.added = true;
                queue.add(node1);
            }
        }
        for(int i = 0; i < queue.size(); i++) {
            Node node = queue.get(i);
            Node node1;
            for (Element element : node.elementsAtPositiveNode) {
                node1 = Initial.getInstance().nodes.get(element.negativeNode);
                if(node1.added == false) {
                    node1.added = true;
                    queue.add(node1);
                }
                char c = element.name.charAt(0);
                if ((c == 'V') || (c == 'E') || (c == 'H')) {
                    node1.union = node.union;
                }
            }
            for (Element element : node.elementsAtNegativeNode) {
                node1 = Initial.getInstance().nodes.get(element.positiveNode);
                if(node1.added == false) {
                    node1.added = true;
                    queue.add(node1);
                }
                char c = element.name.charAt(0);
                if ((c == 'V') || (c == 'E') || (c == 'H')) {
                    node1.union = node.union;
                }
            }
        }
    }
    private boolean getIsAllVoltageSourcesCurrentUpdated(){
        for(Node node : Initial.getInstance().nodes.values()){
            if(node.amountOfVoltageSourceCurrentIsNotUpdated() != 0){
                return false;
            }
        }
        return true;
    }
    private void updateParametersAndResults(){
        for (Node node : Initial.getInstance().nodes.values()) {
            String voltage = String.format("%.5f ", node.voltage);
            node.voltageResults = node.voltageResults + voltage;
            node.updatePastVoltage();
        }
        for (Element element : Initial.getInstance().elementMap.values()){
            element.updatePastCurrent();
            String current = String.format("%.5f ", element.current);
            element.currentResults = element.currentResults + current;
            String differentialVoltage = String.format("%.5f ", element.differentialVoltage);
            element.differentialVoltageResults = element.differentialVoltageResults + differentialVoltage;
            String power = String.format("%.5f ", element.differentialVoltage * element.current);
            element.powerResults = element.powerResults + power;
            char c = element.name.charAt(0);
            if((c == 'V') || (c == 'E') || (c == 'H')){
                element.isCurrentUpdated = false;
            }
        }
    }
    private void quantifyVoltageSourcesCurrent(){
        while(!getIsAllVoltageSourcesCurrentUpdated()) {
            for (Element element : Initial.getInstance().elementMap.values()) {
                char c = element.name.charAt(0);
                if ((c == 'V') || (c == 'E') || (c == 'H')) {
                    element.updateCurrent();
                }
            }
        }
    }
    public void solve(){
        createQueue();
        createUnion();
        while (this.time <= this.totalTime){
            for(int i = 0; i < 1000; i++){
                Initial.getInstance().ground.updateOtherVoltages();
                for (Union union : unionMap.values()) {
                    union.updateCurrentOne();
                    union.updateCurrentTwo();
                    union.updateVoltage();
                    union.updateOtherVoltages();
                }
                for (Element element : Initial.getInstance().elementMap.values()) {
                    char c = element.name.charAt(0);
                    element.updateDifferentialVoltage();
                    if ((c != 'V') && (c != 'E') && (c != 'H')) {
                        element.updateCurrent();
                    }
                }
                for (Node node : Initial.getInstance().nodes.values()) {
                        node.updateVoltage = false;
                }
                Initial.getInstance().nodes.get(0).updateVoltage = true;
            }
            this.quantifyVoltageSourcesCurrent();
            Initial.getInstance().errorsMessage();
            if(hasError){
                return;
            }
            this.updateParametersAndResults();
            this.time += this.dt;
        }
    }
}
