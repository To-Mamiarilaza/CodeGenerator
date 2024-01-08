/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.util;

/**
 *
 * @author to
 */
public class CodeFormatter {

    public static String formatCode(String code) {
        StringBuilder formattedCode = new StringBuilder();
        int indentationLevel = 0;

        String[] lines = code.split("\n");
        for (String line : lines) {
            line = line.trim();

            if (line.endsWith("{")) {
                appendIndentedLine(formattedCode, line, indentationLevel);
                indentationLevel++;
            } else if (line.contains("{") && line.contains("}")) {
                appendIndentedLine(formattedCode, line, indentationLevel);
            } else if (line.endsWith("}")) {
                indentationLevel = Math.max(0, indentationLevel - 1);
                appendIndentedLine(formattedCode, line, indentationLevel);
            } else {
                appendIndentedLine(formattedCode, line, indentationLevel);
            }
        }

        return formattedCode.toString();
    }

    public static void appendIndentedLine(StringBuilder code, String line, int indentationLevel) {
        for (int i = 0; i < indentationLevel; i++) {
            code.append("    "); // 4 espaces pour chaque niveau d'indentation
        }
        code.append(line).append("\n");
    }

    public static String removeContainingLine(String target, String content) {
        String[] lines = content.split("\n");
        StringBuilder newContent = new StringBuilder();

        String[] targetSplit = target.split("\n");
        for (String line : lines) {
            boolean append = true;

            for (String element : targetSplit) {
                if (line.contains(element)) {
                    append = false;
                }
            }
            
            if (append) {
                newContent.append(line + "\n");
            }
        }

        return newContent.toString();
    }
}
