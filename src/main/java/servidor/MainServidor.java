package servidor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Servidor server=new Servidor();
        server.start(6666);
    }

}
