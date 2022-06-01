package cs321.search;

public class QueryObject<E> extends Object{

    private String gene;
    private int bit;
    private int frequency;
    private int numKeys;

    public QueryObject(int l, String str){
        this.numKeys = l;
        this.gene = str;
        this.bit = 0;
        this.frequency = 0;
    }

    public String toString(){
        return gene + ": " + getFrequency() + "\n";
    }

    public void setGene(String str) {
        this.gene = str;
    }

    public String getGene() {
        return this.gene;
    }

    public int getNumKeys(){
        return numKeys;
    }

    public void setNumKeys(int k){
        this.numKeys = k;
    }

    public void incrementFreq() {
        this.frequency++;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public int getBit() {
        return this.bit;
    }

    public void setBit() {
        //this.bit = geneToBit(gene);
    }
}
