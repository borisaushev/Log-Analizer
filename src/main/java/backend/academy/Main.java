package backend.academy;

import backend.academy.analyser.LogAnalyser;

public class Main {
    public static void main(String[] args) {
        args = new String[] {"--path", "src/main/resources/testLogs.txt", "--to", "2024-08-31", "--format", "markdown"};

        LogAnalyser logAnalyser = new LogAnalyser();
        logAnalyser.analise(args);
    }
}
