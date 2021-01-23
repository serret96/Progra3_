package BaseDeDades;

import java.util.Arrays;

public class LlistaProductors {
    private int nProductors;
    private Productor[] llistap;


    public LlistaProductors (int n) {
        nProductors = 0;
        llistap = new Productor[n];
    }

    public int getnProductors() {
        return nProductors;
    }

    public void setnProductors(int nProductors) {
        this.nProductors = nProductors;
    }

    public Productor[] getLlistap() {
        return llistap;
    }

    public void setLlistap(Productor[] llistap) {
        this.llistap = llistap;
    }


    @Override
    public String toString() {
        return "LlistaProductors{" +
                "nproductors=" + nProductors +
                ", llistap=" + Arrays.toString(llistap) +
                '}';
    }

    public void nouElement(Productor productor)
    {
        llistap[nProductors] = productor;
        nProductors++;
    }

    public boolean Existeix(Productor productor)
    {
        for (int i = 0; i < nProductors; i++) {
            if (productor.getNif().equals(llistap[i].getNif()))
            {
                return true;
            }
        }
        return false;
    }
}
