import Union.AlgorIthm.WeightQuickUnion;
import Union.Model.Union;
import Union.Reader.UnionReader;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {
        System.out.println("Hello World!");

        UnionReader reader = new UnionReader();
        ArrayList<Union> list = reader.readUnion("data.txt");

        WeightQuickUnion wqu = new WeightQuickUnion(10);
        for (Union u : list) {
            wqu.union(u);
        }
        wqu.print();
    }
}
