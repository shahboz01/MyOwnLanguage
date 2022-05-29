package Java_files;

public class Token {
    Java_files.TokenType type;
    String value;
    int pos;

    public Token(Java_files.TokenType type, String value, int pos) {
        this.type = type;
        this.value = value;
        this.pos = pos;
    }
}
