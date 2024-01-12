/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.util;

/**
 *
 * @author to
 */ 
public class WordFormatter {

    public static String toCamelCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        StringBuilder camelCase = new StringBuilder();

        // Split the input string by spaces or underscores
        String[] words = name.split("[_\\s]");

        // Convert the first word to lowercase
        camelCase.append(words[0].toLowerCase());

        // Convert the remaining words to camel case
        for (int i = 1; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                camelCase.append(words[i].substring(0, 1).toUpperCase());
                camelCase.append(words[i].substring(1).toLowerCase());
            }
        }

        return camelCase.toString();
    }
    
    public static String capitalizeFirstLetter(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        return Character.toUpperCase(word.charAt(0)) + word.substring(1);
    }
    
    public static String preparePath(String path) {
        if (!path.endsWith("/")) {
            return path + "/";
        }
        
        return path;
    }

    public static String toSpacedUpperCase(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        StringBuilder camelCase = new StringBuilder();
        String[] words = name.split("[_\\s]");
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                camelCase.append(words[i].substring(0, 1).toUpperCase());
                camelCase.append(words[i].substring(1).toLowerCase());
                camelCase.append(" ");
            }
        }
        return camelCase.toString();
    }
    
}
