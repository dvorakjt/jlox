package com.craftinginterpreters.tool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenerateAst {
    public static void main(String[] args) throws IOException {
        if(args.length != 1) {
            System.err.println("Usage: generate_ast <output directory>");
            System.exit(64);
        }

        String outputDir = args[0];

        defineAst(outputDir, "Expr", Arrays.asList(
                "Binary   : Expr left, Token operator, Expr right",
                "Grouping : Expr expression",
                "Literal  : Object value",
                "Unary    : Token operator, Expr right"
        ));
    }

    private static void defineAst(String outputDir, String baseName, List<String> types) throws IOException {
        String path = outputDir + "/" + baseName + ".java";
        PrintWriter writer = new PrintWriter(path, "UTF-8");

        writer.println("package com.craftinginterpreters.lox;");
        writer.println();
        writer.println("import java.util.List;");
        writer.println();
        writer.println("abstract class " + baseName + " {");

        defineVisitor(writer, baseName, types);

        for (String type : types) {
            String className = type.split(":")[0].trim();
            String fields = type.split(":")[1].trim();
            defineType(writer, baseName, className, fields);
        }

        // The base accept() method.
        writer.println();
        writer.println(indent("abstract <R> R accept(Visitor<R> visitor);", 1));

        writer.println("}");
        writer.close();
    }

    private static void defineType(PrintWriter writer, String baseName, String className, String fieldList) {
        writer.println(indent("static class " + className + " extends " + baseName + " {", 1));
        writer.println(indent(className + "(" + fieldList + ") {", 2));

        // Constructor.
        String[] fields = fieldList.split(", ");
        for (String field : fields) {
            String name = field.split(" ")[1];
            writer.println(indent("this." + name + " = " + name + ";", 3));
        }

        writer.println(indent("}", 2));


        // Visitor pattern.
        writer.println();
        writer.println(indent("@Override", 2));
        writer.println(indent("<R> R accept(Visitor<T> visitor) {", 2));
        writer.println(indent("return visitor.visit" + className + baseName + "(this);", 3));
        writer.println(indent("}", 2));

        // Fields.
        writer.println();
        for (String field : fields) {
            writer.println(indent("final " + field + ";", 2));
        }

        writer.println(indent("}", 1));
    }

    private static void defineVisitor(
            PrintWriter writer,
            String baseName,
            List<String> types
    ) {
        writer.println(indent("interface Visitor<R> {", 1));

        for (String type : types) {
            String typeName = type.split(":")[0].trim();
            writer.println(indent("R visit" + typeName + baseName + "(" +  typeName + " " + baseName.toLowerCase() + ");", 2));
        }

        writer.println(indent("}", 1));
    }

    private static String indent(String line, int indentationLevel) {
        return indent(line, indentationLevel, 4);
    }

    private static String indent(String line, int indentationLevel, int padding) {
        return " ".repeat(indentationLevel * padding) + line;
    }
}