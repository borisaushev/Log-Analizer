package backend.academy;

import backend.academy.analyser.LogAnalyser;

public class Main {
    public static void main(String[] args) {
        LogAnalyser logAnalyser = new LogAnalyser();
        logAnalyser.analise(args);
    }
}
