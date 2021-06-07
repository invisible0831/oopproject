import java.util.ArrayList;
import java.util.List;

public class Node {
    int number;
    int union;
    public String voltageResults = "";
    boolean added = false;
    boolean updateVoltage = false;
    List<Element> elementsAtPositiveNode = new ArrayList<>();
    List<Element> elementsAtNegativeNode = new ArrayList<>();
    double voltage = 0;
    double pastVoltage = 0;
    public double getCurrent(){
        double current = 0;
        char c;
        for(Element element : elementsAtPositiveNode){
            c = element.name.charAt(0);
            if((c != 'V') && (c != 'E') && (c != 'H')) {
                if((c == 'I') || (c == 'F') || (c == 'G')){
                    current -= element.current;
                }
                else if((c == 'L') || (c == 'R') || (c == 'C')){
                    current += element.current;
                }
            }
        }
        for (Element element : elementsAtNegativeNode){
            c = element.name.charAt(0);
            if((c != 'V') && (c != 'E') && (c != 'H')) {
                if((c == 'I') || (c == 'F') || (c == 'G')){
                    current += element.current;
                }
                else if((c == 'L') || (c == 'R') || (c == 'C')){
                    current -= element.current;
                }
            }
        }
        return current;
    }
    public void updatePastVoltage(){
        this.pastVoltage = this.voltage;
    }
    public int amountOfVoltageSourceCurrentIsNotUpdated(){
        int counter = 0;
        for(Element element : elementsAtPositiveNode){
            char c = element.name.charAt(0);
            if(((c == 'V') || (c == 'E') || (c == 'H')) && (!element.isCurrentUpdated)){
                counter++;
            }
        }
        return counter;
    }
    public double getAllCurrent(){
        double current = getCurrent();
        for(Element element : elementsAtPositiveNode){
            char c = element.name.charAt(0);
            if((c == 'V') || (c == 'E') || (c == 'H')){
                current += element.current;
            }
        }
        for(Element element : elementsAtNegativeNode){
            char c = element.name.charAt(0);
            if((c == 'V') || (c == 'E') || (c == 'H')){
                current -= element.current;
            }
        }
        return current;
    }
    public boolean isAllElementsAreCurrentSource(){
        for(Element element : elementsAtPositiveNode){
            char c = element.name.charAt(0);
            if((c != 'I') && (c != 'F') && (c != 'G')){
                return false;
            }
        }
        for(Element element : elementsAtNegativeNode){
            char c = element.name.charAt(0);
            if((c != 'I') && (c != 'F') && (c != 'G')){
                return false;
            }
        }
        return true;
    }
}
