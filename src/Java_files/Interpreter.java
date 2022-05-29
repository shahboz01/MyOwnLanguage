package Java_files;

import java.util.HashMap;

public class Interpreter {
    HashMap<String,String> scope=new HashMap<>();

    public String run(Java_files.Node node){
        if (node.getClass()== Java_files.UnOpNode.class) {
            if (((Java_files.UnOpNode) node).operator.type.typeName.equals("PRINT")) {
                System.out.println(this.run(((Java_files.UnOpNode) node).value));
            }
        }
        if (node.getClass()==BinOpNode.class) {
            if (((BinOpNode) node).operator.type.typeName.equals("ASSIGN"))
            {
                String res = this.run(((BinOpNode) node).rightVal);
                Java_files.VarNode varNode = (Java_files.VarNode) (((BinOpNode) node).leftVal);
                this.scope.put(varNode.var.value, res);
                return res;
            }
            else
            {
                int left=Integer.parseInt(this.run(((BinOpNode) node).leftVal));
                int right=Integer.parseInt(this.run(((BinOpNode) node).rightVal));
                switch (((BinOpNode) node).operator.type.typeName){
                    case "PLUS":
                        return String.valueOf(left+right);
                    case "MINUS":
                        return String.valueOf(left-right);
                    case "MULT":
                        return String.valueOf(left*right);
                    case "DIV":
                        return String.valueOf(left/right);
                    case "ASSIGN":
                }
            }
        }
        if (node.getClass()== Java_files.VarNode.class) {
            return scope.get(((Java_files.VarNode) node).var.value);
        }
        if (node.getClass()== Java_files.NumberNode.class) {
            return ((Java_files.NumberNode) node).number.value;
        }
        if (node.getClass()== Java_files.WhileNode.class){
            int left=Integer.parseInt(this.run(((Java_files.WhileNode) node).leftVal));
            int right=Integer.parseInt(this.run(((Java_files.WhileNode) node).rightVal));
            switch (((Java_files.WhileNode) node).operator.type.typeName) {
                case "LESS":
                    while (left < right) {
                        for (int i = 0; i < ((Java_files.WhileNode) node).operations.size(); i++)
                            this.run(((Java_files.WhileNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((Java_files.WhileNode) node).leftVal));
                        right = Integer.parseInt(this.run(((Java_files.WhileNode) node).rightVal));
                    }
                    break;
                case "MORE":
                    while (left > right) {
                        for (int i = 0; i < ((Java_files.WhileNode) node).operations.size(); i++)
                            this.run(((Java_files.WhileNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((Java_files.WhileNode) node).leftVal));
                        right = Integer.parseInt(this.run(((Java_files.WhileNode) node).rightVal));
                    }
                    break;
                case "EQUAL":
                    while (left == right) {
                        for (int i = 0; i < ((Java_files.WhileNode) node).operations.size(); i++)
                            this.run(((Java_files.WhileNode) node).operations.get(i));
                        left = Integer.parseInt(this.run(((Java_files.WhileNode) node).leftVal));
                        right = Integer.parseInt(this.run(((Java_files.WhileNode) node).rightVal));
                    }
                    break;
            }
        }
        if (node.getClass()== Java_files.ForNode.class){
            int left=Integer.parseInt(this.run(((Java_files.ForNode) node).leftVal));
            int right=Integer.parseInt(this.run(((Java_files.ForNode) node).rightVal));
            switch (((Java_files.ForNode) node).operator.type.typeName) {
                case "LESS":
                    while (left < right) {
                        for (int i = 0; i < ((Java_files.ForNode) node).operations.size(); i++)
                            this.run(((Java_files.ForNode) node).operations.get(i));
                        this.run(((Java_files.ForNode) node).action);
                        left = Integer.parseInt(this.run(((Java_files.ForNode) node).leftVal));
                        right = Integer.parseInt(this.run(((Java_files.ForNode) node).rightVal));
                    }
                    break;
                case "MORE":
                    while (left > right) {
                        for (int i = 0; i < ((Java_files.ForNode) node).operations.size(); i++)
                            this.run(((Java_files.ForNode) node).operations.get(i));
                        this.run(((Java_files.ForNode) node).action);
                        left = Integer.parseInt(this.run(((Java_files.ForNode) node).leftVal));
                        right = Integer.parseInt(this.run(((Java_files.ForNode) node).rightVal));
                    }
                    break;
                case "EQUAL":
                    while (left == right) {
                        for (int i = 0; i < ((Java_files.ForNode) node).operations.size(); i++)
                            this.run(((Java_files.ForNode) node).operations.get(i));
                        this.run(((Java_files.ForNode) node).action);
                        left = Integer.parseInt(this.run(((Java_files.ForNode) node).leftVal));
                        right = Integer.parseInt(this.run(((Java_files.ForNode) node).rightVal));
                    }
                    break;
            }
        }
        return "";
    }
}
