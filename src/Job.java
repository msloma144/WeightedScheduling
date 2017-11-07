/**
 * Created by Michael Sloma on 10/31/2017.
 */
public class Job {
    private int start;
    private int end;
    private int weight;
    private int p_val;

    public Job(int start, int end, int weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
        this.p_val = -1;

    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getP_val() {
        return p_val;
    }

    public void setP_val(int p_val) {
        this.p_val = p_val;
    }
}
