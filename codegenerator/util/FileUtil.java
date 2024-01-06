/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author to
 */
public class FileUtil {
    // get the file content to a string
    public static String toString(String filePath) throws Exception {
        Path path = Paths.get(filePath);
        String content =  new String(Files.readAllBytes(path));
        return content;
    }
    
    // create a file with a content
    public static void createFileWithContent(String content, String destinationPath, String fileName) {
        try {
            // Créer le chemin complet du fichier
            String filePath = destinationPath + File.separator + fileName;

            // Créer un objet File pour représenter le fichier
            File file = new File(filePath);

            // Vérifier si le répertoire de destination existe, sinon le créer
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            // Créer le fichier et écrire le contenu
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }

            System.out.println("Le fichier a été créé avec succès : " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la création du fichier.");
        }
    }
}
