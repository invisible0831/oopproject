import java.util.ArrayList;
import java.util.List;

public class Union {
    List<Node> nodes = new ArrayList<>();
    double I1;
    double I2;
    boolean isEquationCorrect = true;
    public void updateCurrentOne() {
        I1 = 0;
        for (Node node : nodes) {
            I1 += node.getCurrent();
        }
    }
    public void updateCurrentTwo() {
        nodes.get(0).voltage += Solve.getInstance().getDv();
        for (Element element : nodes.get(0).elementsAtNegativeNode) {
            char c = element.name.charAt(0);
            element.updateDifferentialVoltage();
            if((c != 'V') && (c != 'E') && (c != 'H')) {
                element.updateCurrent();
            }
        }
        for (Element element : nodes.get(0).elementsAtPositiveNode) {
            char c = element.name.charAt(0);
            element.updateDifferentialVoltage();
            if((c != 'V') && (c != 'E') && (c != 'H')) {
                element.updateCurrent();
            }
        }
        I2 = 0;
        for (Node node : nodes) {
            I2 += node.getCurrent();
        }
        nodes.get(0).voltage -= Solve.getInstance().getDv();
        for (Element element : nodes.get(0).elementsAtNegativeNode) {
            char c = element.name.charAt(0);
            element.updateDifferentialVoltage();
            if((c != 'V') && (c != 'E') && (c != 'H')) {
                element.updateCurrent();
            }
        }
        for (Element element : nodes.get(0).elementsAtPositiveNode) {
            char c = element.name.charAt(0);
            element.updateDifferentialVoltage();
            if((c != 'V') && (c != 'E') && (c != 'H')) {
                element.updateCurrent();
            }
        }
    }
    public void updateVoltage() {
        checkEquation();
        Node node = nodes.get(0);
        node.updateVoltage = true;
        //vaqti I1 o I2 mosavi mishe bazi vaqta halate mosavi javabe bazi vaqta N v nmidoni kdom toye hameye zamana javabe
        if ((I1 >= I2) && isEquationCorrect) {
            node.voltage += I1 * Solve.getInstance().getDv() / Solve.getInstance().getDi();
        }
        else {
            node.voltage -= I1 * Solve.getInstance().getDv() / Solve.getInstance().getDi();
        }
    }
    public void updateOtherVoltages() {
        Node node1;
        for (Node node : nodes) {
            char c;
            for (Element element : node.elementsAtPositiveNode) {
                c = element.name.charAt(0);
                if ((c == 'V') || (c == 'E') || (c == 'H')) {
                    node1 = Initial.getInstance().nodes.get(element.negativeNode);
                    if (node1.updateVoltage == false) {
                        node1.updateVoltage = true;
                        node1.voltage = node.voltage - element.differentialVoltage;
                    }
                }
            }
            for (Element element : node.elementsAtNegativeNode) {
                c = element.name.charAt(0);
                if ((c == 'V') || (c == 'E') || (c == 'H')) {
                    node1 = Initial.getInstance().nodes.get(element.positiveNode);
                    if (node1.updateVoltage == false) {
                        node1.updateVoltage = true;
                        node1.voltage = node.voltage + element.differentialVoltage;
                    }
                }
            }
        }
    }
    private void checkEquation(){
        if(Math.abs(I1) > 10){
            this.isEquationCorrect = false;
        }
    }
}
