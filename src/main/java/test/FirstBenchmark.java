package test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import main.*;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class FirstBenchmark {
    private AnagramDictionary dic;

    public void readDic() {
        try {
            AnagramDictionary dic = new AnagramDictionary("sowpods.txt");
            this.dic = dic;
        } catch (FileNotFoundException ex) {
            System.out.println("sowpods.txt" + " doesn't exist");
        }
    }

    public void findAna(){
        Rack r = new Rack("gfasbdaasd");
        ArrayList<String> rackSubset = r.getSubset();
        //System.out.println(r.getRack());
        ArrayList<String> anagram = new ArrayList<String>();
        for(String subsetStr : rackSubset) {
            anagram.addAll(dic.getAnagramsOf(subsetStr));
        }
        if(anagram.size() > 0) {
            //System.out.println("ana :"+anagram);
        }
    }
    @Benchmark
    public void wrap() throws IOException {
        String[] args = new String[]{"sowpods.txt","testFiles/aestnlr.in"};
        wrapper.main(args);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FirstBenchmark.class.getSimpleName())
                .forks(1)
                .warmupIterations(5)
                .measurementIterations(20)
                .build();

        new Runner(opt).run();
    }
}