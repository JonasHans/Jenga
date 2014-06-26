import java.util.*;
import java.text.DecimalFormat;
import java.io.*;
import java.io.FileReader;

/*****************************************************************************

    This class reads out the value of the matlab image processing function,
    using a buffered reader. It transforms the generated string into a double
    and returns the value. 

*****************************************************************************/

public class ReadTxt {

    double surface;

        public ReadTxt()  {
            surface = 0.0;
            try {    
                BufferedReader br = new BufferedReader(new FileReader("surface.txt"));

                String line = br.readLine();
                String[] splitLine = line.split("\\+e");
                surface = Double.parseDouble(splitLine[0]) * (Math.pow(10,  Double.parseDouble(splitLine[1])));
                System.out.println(surface);
             }
            catch (Exception e) {
                e.printStackTrace();
             }
        }

    public static void main(String args[]) {
        ReadTxt txt = new ReadTxt();
    }
}