package Union.Reader;

import Union.Model.Union;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UnionReader {

    public ArrayList<Union> readUnion(String path){
        BufferedReader reader;
        ArrayList<Union> list = null;
        try{
            list = new ArrayList<>();
            reader = new BufferedReader(new FileReader(path));
            String line = reader.readLine();
            while (line != null){
                String[] numbers = line.split(" ");
                list.add(new Union(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])));
                line = reader.readLine();
            }
            reader.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }

}
