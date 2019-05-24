package Java_exercise.nineproblem;

import java.io.IOException;
import java.net.*;
import java.nio.channels.*;
import java.util.*;

public class MyNetWork {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket serverSocket=serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(8080));

        Selector selector=Selector.open();
        serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();

    }
}
