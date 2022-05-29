package Java_files;

import java.util.ArrayList;

public class RootNode extends Java_files.Node {
    ArrayList<Java_files.Node> codeStr=new ArrayList<>();
    public void addNode(Java_files.Node node){
        codeStr.add(node);
    }
}