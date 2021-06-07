import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Program.getInstance().run();
        //Path input = Paths.get("/Users/amireza/IdeaProjects/1/src/one.txt");
        //Initial.getInstance().initial(input);
        //Solve.getInstance().solve();
        //System.out.println(Initial.getInstance().nodes.size());
        //System.out.println(Initial.getInstance().ground.nodes.size());
        /*for (int i = 0; i < Initial.getInstance().nodes.get(1).voltagesList.size(); i++){
            System.out.printf("%.3f ", Initial.getInstance().nodes.get(2).voltagesList.get(i) - Initial.getInstance().nodes.get(1).voltagesList.get(i));
        }*/
        /*for(double i : Initial.getInstance().nodes.get(2).voltagesList){
            System.out.printf("%.3f ", i);
        }*/
        //System.out.println(Initial.getInstance().elementMap.get("R1").negativeNode);
        /*for (double i : Initial.getInstance().elementMap.get("L1").currentsList){
            System.out.printf("%.4f ", i);
        }*/
        //System.out.println(Initial.getInstance().nodes.get(2).getCurrent());
        //System.out.println(Solve.getInstance().unionMap.get(2).nodes.size());
    }
}
