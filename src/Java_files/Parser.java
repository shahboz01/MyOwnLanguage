package Java_files;

import java.util.ArrayList;

public class Parser {
    ArrayList<Token> tokens;
    int pos=0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
    public Token receive(String[] need){
        Token curToken;
        if (pos<tokens.size()) {
            curToken = tokens.get(pos);
            for (String tokenTypeName : need)
                if (tokenTypeName.equals(curToken.type.typeName)) {
                    pos++;
                    return curToken;
                }
        }
        return null;
    }
    public void need(String[] expected){
        Token token= receive(expected);
        if(token==null){
            throw new Error("На позииции "+pos+" ожидается "+expected[0]);
        }
    }
    public Java_files.Node parseVarNum(){
        if (tokens.get(pos).type.typeName.equals("NUM")){
            pos++;
            return new Java_files.NumberNode(tokens.get(pos-1));
        }
        if (tokens.get(pos).type.typeName.equals("VAR")){
            pos++;
            return new Java_files.VarNode(tokens.get(pos-1));
        }
        throw new Error("Переменная или число, ожидаемое в позиции: "+pos);
    }
    public Java_files.Node parsePar(){
        if (tokens.get(pos).type.typeName.equals("LPAR")){
            pos++;
            Java_files.Node node = parseFormula();
            need(new String[]{"RPAR"});
            return node;
        }
        else
            return parseVarNum();
    }
    public Java_files.Node parseMultDiv(){
        Java_files.Node leftVal= parsePar();
        Token operator= receive(new String[]{"MULT","DIV"});
        while (operator!=null){
            Java_files.Node rightVal= parsePar();
            leftVal=new BinOpNode(operator,leftVal,rightVal);
            operator= receive(new String[]{"MULT","DIV"});
        }
        return leftVal;
    }
    public Java_files.Node parseFormula(){
        Java_files.Node leftVal= parseMultDiv();
        Token operator= receive(new String[]{"PLUS","MINUS"});
        while (operator!=null){
            Java_files.Node rightVal= parseMultDiv();
            leftVal=new BinOpNode(operator,leftVal,rightVal);
            operator= receive(new String[]{"PLUS","MINUS"});
        }
        return leftVal;
    }
    public Java_files.Node parseString(){
        if (tokens.get(pos).type.typeName.equals("VAR")) {
            Java_files.Node varNode = parseVarNum();
            Token assign = receive(new String[]{"ASSIGN"});
            if (assign != null) {
                Java_files.Node rightVal = parseFormula();
                return new BinOpNode(assign, varNode, rightVal);
            }
            throw new Error("После ожидаемой переменной = в позиции:"+pos);
        }
        else if (tokens.get(pos).type.typeName.equals("PRINT")){
            pos++;
            return new Java_files.UnOpNode(tokens.get(pos-1), this.parseFormula());
        }
        else if(tokens.get(pos).type.typeName.equals("WHILE")){
            pos++;
            return  parseWhile();
        }
        else if(tokens.get(pos).type.typeName.equals("FOR"))
        {
            pos++;
            return parseFor();
        }
        throw new Error("Ошибка позиционирования: "+pos+". Ожидалось действие или переменная");
    }
    public Java_files.Node parseFor(){
        Java_files.Node leftVal=parseFormula();
        Token operator=receive(new String[]{"LESS","MORE","EQUAL"});
        Java_files.Node rightVal=parseFormula();

        need(new String[]{"END"});

        Java_files.Node varNode = parseVarNum();
        Token assign = receive(new String[]{"ASSIGN"});
        Java_files.Node rightActVal = parseFormula();
        BinOpNode action= new BinOpNode(assign, varNode, rightActVal);
        if (assign == null)
            throw new Error("После переменной ожидается = на позиции:"+pos);
        Java_files.ForNode forNode= new Java_files.ForNode(operator,leftVal,rightVal,action);
        need(new String[]{"LRectPar"});
        while(!tokens.get(pos).type.typeName.equals("RRectPAR")) {
            forNode.addOperations(getOperations());
            if (pos==tokens.size())
                throw new Error("Ошибка, ожидалось }");
        }
        pos++;
        return forNode;
    }
    public Java_files.Node parseWhile(){
        Java_files.Node leftVal=parseFormula();
        Token operator=receive(new String[]{"LESS","MORE","EQUAL"});
        Java_files.Node rightVal=parseFormula();
        Java_files.WhileNode whileNode=new Java_files.WhileNode(operator,leftVal,rightVal);
        need(new String[]{"LRectPar"});
        while(!tokens.get(pos).type.typeName.equals("RRectPAR")) {
            whileNode.addOperations(getOperations());
            if (pos==tokens.size())
                throw new Error("Ошибка, ожидалось }");
        }
        pos++;
        return whileNode;
    }
    public Java_files.Node getOperations(){
        Java_files.Node codeStringNode=parseString();
        need(new String[]{"END"});
        return codeStringNode;
    }
    public Java_files.RootNode parseTokens(){
        Java_files.RootNode root=new Java_files.RootNode();
        while (pos<tokens.size()){
            Java_files.Node codeStringNode= parseString();
            need(new String[]{"END"});
            root.addNode(codeStringNode);
        }
        return root;
    }
}
