import java.io.IOException;
import java.util.logging.*;
import java.util.*;

public class LogTest{
    public final static Logger ml=Logger.getLogger("myl");
    static{
    }
    public static void main() throws IOException {
        Logger myl=Logger.getLogger("myl");
        ml.setLevel(Level.INFO);
        ConsoleHandler ch=new ConsoleHandler();
        FileHandler fh=new FileHandler("lll.txt");
        ml.info("what");
        ml.addHandler(ch);
    }
}