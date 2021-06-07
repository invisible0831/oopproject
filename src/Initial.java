import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.Math;
// naraye error input moshakhas kon k solve has errorsh true beshe

public class Initial {
    private static Initial instance = new Initial();
    private Initial(){}
    public static Initial getInstance(){
        return instance;
    }
    public Map<String, Element> elementMap = new HashMap<>();
    public Map<Integer, Node> nodes = new HashMap<>();
    public List<String> lines = new ArrayList<>();
    public Union ground;
    private Path inputFilePath, outputFilePath;
    public void initial(Path input){
        setInputFilePath(input);
        try {
            lines = Files.readAllLines(inputFilePath);
        } catch (IOException e) {
            System.out.println("error");
        }
        createGround();
        int line  = 1;
        for(String string : lines) {
            Pattern pattern = Pattern.compile("^((\\s*)(\\*))");
            Matcher matcher = pattern.matcher(string);
            if (!matcher.find()) {
                String[] str = string.split("\\s+");
                char c = str[0].charAt(0);
                if (str.length > 3) {
                    Pattern pattern1 = Pattern.compile("\\D");
                    Matcher matcher1 = pattern1.matcher(str[1]);
                    Matcher matcher2 = pattern1.matcher(str[2]);
                    if((matcher1.find()) || (matcher2.find())){
                        inputErrorMessage(line);
                        return;
                    }
                    Element element;
                    int positiveNode = Integer.parseInt(str[1]);
                    int negativeNode = Integer.parseInt(str[2]);
                    Node nodeNegative;
                    Node nodePositive;
                    if (!nodes.containsKey(positiveNode)) {
                        nodePositive = new Node();
                        nodePositive.union = positiveNode;
                        nodePositive.number = positiveNode;
                        nodes.put(positiveNode, nodePositive);
                    } else {
                        nodePositive = nodes.get(positiveNode);
                    }
                    if (!nodes.containsKey(negativeNode)) {
                        nodeNegative = new Node();
                        nodeNegative.union = negativeNode;
                        nodeNegative.number = negativeNode;
                        nodes.put(negativeNode, nodeNegative);
                    } else {
                        nodeNegative = nodes.get(negativeNode);
                    }
                    if(c == 'I') {
                        try {
                            element = new IndependentCurrentSource(str[0], positiveNode, negativeNode, getValue(str[3]), getValue(str[4]), getValue(str[5]), getValue(str[6]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'V') {
                        try {
                            element = new IndependentVoltageSource(str[0], positiveNode, negativeNode, getValue(str[3]), getValue(str[4]), getValue(str[5]), getValue(str[6]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'R') {
                        try {
                            element = new Resistor(str[0], positiveNode, negativeNode, getValue(str[3]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                        if(element.value < 0){
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'L') {
                        try {
                            element = new Inductor(str[0], positiveNode, negativeNode, getValue(str[3]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                        if(element.value < 0){
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'C') {
                        try {
                            element = new Capacitor(str[0], positiveNode, negativeNode, getValue(str[3]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                        if(element.value < 0){
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'F') {
                        try {
                            element = new CurrentSourceDependCurrent(str[0], positiveNode, negativeNode, str[3], getValue(str[4]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'E') {
                        try {
                            element = new VoltageSourceDependVoltage(str[0], positiveNode, negativeNode, Integer.parseInt(str[3]), Integer.parseInt(str[4]), getValue(str[5]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'H') {
                        try {
                            element = new VoltageSourceDependCurrent(str[0], positiveNode, negativeNode, str[3], getValue(str[4]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'G') {
                        try {
                            element = new CurrentSourceDependVoltage(str[0], positiveNode, negativeNode, Integer.parseInt(str[3]), Integer.parseInt(str[4]), getValue(str[5]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(c == 'D'){
                        element = new Diode();
                    }
                    else{
                        inputErrorMessage(line);
                        return;
                    }
                    elementMap.put(str[0], element);
                    nodePositive.elementsAtPositiveNode.add(element);
                    nodeNegative.elementsAtNegativeNode.add(element);
                }
                else if(c == 'd'){
                    char a = str[0].charAt(1);
                    if(a == 'V'){
                        try {
                            Solve.getInstance().setDv(getValue(str[1]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(a == 'T'){
                        try {
                            Solve.getInstance().setDt(getValue(str[1]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else if(a == 'I'){
                        try {
                            Solve.getInstance().setDi(getValue(str[1]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else{
                        inputErrorMessage(line);
                        return;
                    }
                }
                else if(c == '.'){
                    if(str[0].equals(".tran")){
                        try {
                            Solve.getInstance().setTotalTime(getValue(str[1]));
                        } catch (Exception e) {
                            inputErrorMessage(line);
                            return;
                        }
                    }
                    else{
                        inputErrorMessage(line);
                        return;
                    }
                }
            }
            line++;
        }
        errorsMessage();
    }
    public double getValue(String string) throws Exception{
        double s;
        Pattern pattern = Pattern.compile("^((\\d+)(\\.\\d+)?)$");
        Matcher matcher = pattern.matcher(string);
        if(matcher.find()) {
            s = Double.parseDouble(string);
        }
        else{
            char c = string.charAt(string.length() - 1);
            String string1 = string.substring(0, string.length() - 1);
            if (c == 'p') {
                s = Double.parseDouble(string1) * 1e-12;
            }
            else if (c == 'n') {
                s = Double.parseDouble(string1) * 1e-9;
            }
            else if (c == 'u') {
                s = Double.parseDouble(string1) * 1e-6;
            }
            else if (c == 'm') {
                s = Double.parseDouble(string1) * 1e-3;
            }
            else if (c == 'k') {
                s = Double.parseDouble(string1) * 1e3;
            }
            else if (c == 'M') {
                s = Double.parseDouble(string1) * 1e6;
            }
            else if (c == 'G') {
                s = Double.parseDouble(string1) * 1e9;
            }
            else {
                throw new Exception("");
            }
        }
        return s;
    }
    private void createGround(){
        Node node = new Node();
        node.added = true;
        node.union = 0;
        node.number = 0;
        node.updateVoltage = true;
        nodes.put(0, node);
        ground = new Union();
        ground.nodes.add(node);
    }
    private boolean hasErrorOne(){
        String string = lines.get(lines.size() - 1);
        Pattern pattern = Pattern.compile("^(\\.tran)");
        Matcher matcher = pattern.matcher(string);
        if((!matcher.find()) || (Solve.getInstance().getDv() <= 0) || (Solve.getInstance().getDi() <= 0) || (Solve.getInstance().getDt() <= 0) || (Solve.getInstance().getTotalTime() <= 0)){
            return true;
        }
        return false;
    }
    private boolean hasErrorTwo(){
        for(Node node : nodes.values()){
            if((node.isAllElementsAreCurrentSource()) && (Math.abs(node.getCurrent()) > Solve.getInstance().getDi())){
                return true;
            }
        }
        return false;
    }
    private boolean hasErrorThree(){
        return true;
    }
    private boolean hasErrorFour(){
        for(Element element : elementMap.values()){
            if((element.positiveNode == 0) || (element.negativeNode == 0)){
                return false;
            }
        }
        return true;
    }
    public void errorsMessage(){
        if(hasErrorOne()){
            Program.getInstance().mainPage.showErrorMessage("error -1");
            Program.getInstance().mainPage.disableButtons();
            Solve.getInstance().hasError = true;
            return;
        }
        if(hasErrorTwo()){
            Program.getInstance().mainPage.showErrorMessage("error -2");
            Program.getInstance().mainPage.disableButtons();
            Solve.getInstance().hasError = true;
            return;
        }
        if(hasErrorFour()){
            Program.getInstance().mainPage.showErrorMessage("error -4");
            Program.getInstance().mainPage.disableButtons();
            Solve.getInstance().hasError = true;
            return;
        }
    }
    public void setInputFilePath(Path path){
        this.inputFilePath = path;
    }
    public void setOutputFilePath(Path path){
        this.outputFilePath = path;
    }
    public Path getOutputFilePath(){
        return outputFilePath;
    }
    private void inputErrorMessage(int line){
        String s = Integer.toString(line);
        Program.getInstance().mainPage.showErrorMessage("input error\n" + "line number: " + s);
        Program.getInstance().mainPage.disableButtons();
        Solve.getInstance().hasError = true;
    }
    public void addResultsToFile(){
        try {
            Files.writeString(outputFilePath, "");
        } catch (IOException e) {
            System.out.println("output path is incorrect");
            return;
        }
        for(Element element : elementMap.values()){
            String elementResults = element.name + "\nDifferential Voltage:\n" + element.differentialVoltageResults + "\nCurrent:\n" + element.currentResults + "\nPower:\n" + element.powerResults + "\n";
            try {
                Files.writeString(outputFilePath, elementResults, StandardOpenOption.APPEND);
            } catch (IOException e) {

            }
        }
        for(Node node : nodes.values()){
            String nodeResults = "Node " + Integer.toString(node.number) + "\n" + node.voltageResults + "\n";
            try {
                Files.writeString(outputFilePath, nodeResults, StandardOpenOption.APPEND);
            } catch (IOException e) {

            }
        }
    }
    public void resetAllParameters(){
        elementMap.clear();
        nodes.clear();
        lines.clear();
        Solve.getInstance().queue.clear();
        Solve.getInstance().unionMap.clear();
        Solve.getInstance().setDi(-1);
        Solve.getInstance().setDv(-1);
        Solve.getInstance().setDt(-1);
        Solve.getInstance().setTime(0);
        Solve.getInstance().hasError = false;
    }
}
