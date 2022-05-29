package Java_files;

import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static String checkFile(){
        String code="";
        try(FileReader reader = new FileReader("code.txt"))
        {
            int c;
            while((c=reader.read())!=-1){

                code=code.concat(String.valueOf((char)c));
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return code;
    }

    public static void main(String[] args) {
        String s=checkFile();
        System.out.println(s);
        Java_files.Lexer lexer=new Java_files.Lexer(s);
        Java_files.Parser parser=new Java_files.Parser(lexer.analyze());
        Java_files.RootNode root=parser.parseTokens();
        Java_files.Interpreter interpreter =new Java_files.Interpreter();
        for(int i = 0; i<root.codeStr.size(); i++) {
            interpreter.run(root.codeStr.get(i));
        }
    }
}
