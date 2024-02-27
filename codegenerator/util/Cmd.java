package codegenerator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Cmd {
    // Class managing cmd execution
    // Execute command lin on the prompt
    public static List<String> execute(String command, File workingDirectory) throws Exception {
        // Creating the process
        // Process process = Runtime.getRuntime().exec("cmd /c " + command, null, workingDirectory);    // For windows
        Process process = Runtime.getRuntime().exec(command, null, workingDirectory);

        // In case of error
        InputStream errorStream = process.getErrorStream();
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));

        StringBuilder error = new StringBuilder();
        String errorLine;
        while ((errorLine = errorReader.readLine()) != null) {
            error.append(errorLine);
        }
        String errorString = error.toString();


        // read the result of process
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        List<String> commandResult = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("-> " + line);
            commandResult.add(line);
        }

        int exitCode = process.waitFor();
        System.out.println("La commande s'est terminée avec le code de sortie : " + exitCode);
        if (exitCode != 0) {
            throw new Exception(errorString);
        }

        return commandResult;
    }
}
