package Java_files;

public class BinOpNode extends Java_files.Node {
    Java_files.Token operator;
    Java_files.Node leftVal;
    Java_files.Node rightVal;

    public BinOpNode(Java_files.Token operator, Java_files.Node leftVal, Java_files.Node rightVal) {
        super();
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
    }
}
