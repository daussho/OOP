package takis.FilesIO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    String[] s = new String[1000];
    String[] ss = s;
    public ReadFile(String path){
        int i = 0;
        try {
            FileReader f = new FileReader(path);
            BufferedReader br = new BufferedReader(f);
            String line;
            while ((line = br.readLine()) != null) {
                s[i] = line;
                i++;
            }
            ss = new String[i];
            
            for (int j=0;j<i;j++){
                ss[j] = s[j];
            }
            f.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String[] readFile(){
        return ss;
    }
}