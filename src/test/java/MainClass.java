import org.apache.log4j.xml.DOMConfigurator;

import java.util.logging.Logger;
import com.ag.framework.utilities.LogsGenerator;


public class MainClass {


    private static Logger Log = Logger.getLogger(MainClass.class.getName());

    public static void main(String[] args) throws Exception {
        try {

            DOMConfigurator.configure("log4j.xml");

           LogsGenerator.info("Start Test");
            ExecuteSequence();

        } catch (Exception e) {
           LogsGenerator.error(e.toString());
           System.out.println(e.toString());
        }
    }


}
