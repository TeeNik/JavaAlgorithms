package Union.AlgorIthm;

import Union.Model.Union;

public class WeightQuickUnion {

    private int[] id;
    private int[] sz;

    public WeightQuickUnion(int size){
        id = new int[size];
        sz = new int[size];
        for (int i = 0; i < size; ++i){
            id[i] = i;
            sz[i] = 1;
        }
    }

    public void union(Union u){
        if(!connected(u.x, u.y)){
            int l = root(u.x);
            int k = root(u.y);
            if(sz[l] > sz[k]){
                id[k] = l;
                sz[l] += sz[k];
            } else {
                id[l] = k;
                sz[k] += sz[l];
            }
        }
    }

    public boolean connected(int a, int b){
        int l = root(a);
        int k = root(b);
        return k == l;
    }

    public int root(int i){
        while (id[i] != i){
            i = id[i];
        }
        return i;
    }

    public void print(){

        for (int i = 0; i < id.length; i++) {
            System.out.print(String.format("%" + 3 + "s", i));
        }

        System.out.print("\n");

        for (int i = 0; i < id.length; i++) {
            System.out.print(String.format("%" + 3 + "s", id[i]));
        }
    }
}