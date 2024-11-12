package backend.academy.analyser;

public class CLILogAnalyser extends LogAnalyser {
    @SuppressWarnings("RegexpSinglelineJava")
    public void analyzeAndDisplay(String[] args) {
        try {
            System.out.println(analise(args));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
}
