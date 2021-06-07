public class CurrentSourceDependCurrent extends Element{
    String nameTwo;
    @Override
    void updateCurrent() {
        this.current = this.value * Initial.getInstance().elementMap.get(this.nameTwo).current;
    }
    public CurrentSourceDependCurrent(String name, int positiveNode, int negativeNode, String nameTwo, double value){
        this.name = name;
        this.positiveNode = positiveNode;
        this.negativeNode = negativeNode;
        this.nameTwo = nameTwo;
        this.value = value;
    }
}
