public class Program {
    private static Program instance = new Program();
    private Program(){}
    public static Program getInstance(){
        return instance;
    }
    MainPage mainPage;
    public void run(){
        mainPage = new MainPage();
    }
}
