package Java_files;

import java.util.ArrayList;

public class ForNode extends Java_files.Node {
    Token operator;
    Java_files.Node leftVal;
    Java_files.Node rightVal;
    Java_files.Node action;
    public ArrayList<Java_files.Node> operations = new ArrayList<>();

    public ForNode(Token operator, Java_files.Node leftVal, Java_files.Node rightVal, Java_files.Node action) {
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
        this.action=action;
    }
    public void addOperations(Java_files.Node op){
        operations.add(op);
    }
}
