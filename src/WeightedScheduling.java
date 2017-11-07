import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WeightedScheduling {
    static ArrayList<Job> jobList;
    static ArrayList<Integer> memo;

    private static void readFile(File inFile) throws IOException {
        jobList = new ArrayList<>();

        FileReader inReader = new FileReader(inFile);
        BufferedReader inReadBuff = new BufferedReader(inReader);

        String line = inReadBuff.readLine();
        int length = line.length();
        int index = line.indexOf("  ");

        while (length != 0) {
            int start = -1;
            int end = -1;
            int weight = -1;
            int numCounter = 0;
            int lastSpace = 0;

            if(index == -1){
                index = length;
            }

            String segment = line.substring(0, index);
            int segmentLength = segment.length();

            for (int i = 0; i < 3; i++) {
                int nextSpace = segment.indexOf(" ", lastSpace);

                switch (numCounter) {
                    case 0:
                        start = Integer.parseInt(segment.substring(lastSpace, nextSpace));
                        numCounter++;
                        break;
                    case 1:
                        end = Integer.parseInt(segment.substring(lastSpace, nextSpace));
                        numCounter++;
                        break;
                    case 2:
                        weight = Integer.parseInt(segment.substring(lastSpace, segmentLength));
                        numCounter++;
                        break;
                }
                lastSpace = nextSpace + 1;
            }

            jobList.add(new Job(start, end, weight));

            if(index != length) {
                line = line.substring(index + 2);
                index = line.indexOf("  ");
                length = line.length();
            }
            else break;
        }
    }

    private static int calcP_Value(int index){
        //p(j) = largest index i < j such that job i is compatible with j.
        int jobStart = jobList.get(index).getStart();

        for (int i = index; i >= 0; i--) {
           if(jobList.get(i).getEnd() <= jobStart){
               return i;
           }
        }
        return -1;
    }

    private static void calcAllP_Values(){
            for(int i = 0; i < jobList.size(); i++){
                jobList.get(i).setP_val(calcP_Value(i));
            }
    }

    private static int max(int opt1, int opt2){
        if(opt1 > opt2){
            return opt1;
        }
        else return opt2;
    }

    private static void initMemo(){
        memo = new ArrayList<>();

        for (Job j: jobList) {
            memo.add(-1);
        }
        memo.set(0, jobList.get(0).getWeight());

    }

    private static int computeOptimal(int jobIndex){
        // Plain recursive
        if(jobIndex == -1)
            return 0;
        else {
            int jobWeight = jobList.get(jobIndex).getWeight();
            int jobP_Val = jobList.get(jobIndex).getP_val();

            return max((jobWeight + computeOptimal(jobP_Val)), computeOptimal(jobIndex - 1));
        }
    }

    private static int memoComputeOptimal(int jobIndex){
        // Memo recursive
        if(memo.get(jobIndex) == -1){
            int jobWeight = jobList.get(jobIndex).getWeight();
            int jobP_Val = jobList.get(jobIndex).getP_val();

            if(jobP_Val == -1){
                memo.set(jobIndex, (max((jobWeight), memoComputeOptimal(jobIndex - 1))));
            }
            else {
                memo.set(jobIndex, (max((jobWeight + memoComputeOptimal(jobP_Val)), memoComputeOptimal(jobIndex - 1))));
            }
        }

        return memo.get(jobIndex);
    }

    private static void getSolutionJobs(int jobIndex){
        if(jobIndex == 0){
            System.out.println(jobIndex);
        }
        else if((jobList.get(jobIndex).getWeight() + memo.get(jobList.get(jobIndex).getP_val())) > memo.get(jobIndex -1)){
            System.out.println(jobIndex);
            getSolutionJobs(jobList.get(jobIndex).getP_val());
        }
        else {
            getSolutionJobs(jobIndex - 1);
        }
    }

    public static void weightedIntervalScheduling(File inFile)throws IOException{
        readFile(inFile);
        initMemo();
        calcAllP_Values();
        System.out.println("Optimal Weight Value: " + memoComputeOptimal(jobList.size() - 1));
        System.out.println("Optimal Job Indexes: ");
        getSolutionJobs(jobList.size() - 1);
    }

    public static void main(String[] args) throws IOException{
        weightedIntervalScheduling(new File("src/source.txt"));
    }
}
