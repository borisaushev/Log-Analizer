package backend.academy;

import backend.academy.analyser.CLILogAnalyser;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        CLILogAnalyser logAnalyser = new CLILogAnalyser();
        logAnalyser.analyzeAndDisplay(args);
    }
}
