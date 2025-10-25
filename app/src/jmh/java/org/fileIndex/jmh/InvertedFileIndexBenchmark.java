package org.fileIndex.jmh;

import java.util.concurrent.TimeUnit;
import org.fileIndex.InvertedFileIndex;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 25, time = 1, timeUnit = TimeUnit.SECONDS)
@Timeout(time = 5, timeUnit = TimeUnit.SECONDS)
@Fork(value = 4)
public class InvertedFileIndexBenchmark {

  // @Benchmark
  // public void theDogAndTheShadowBenchmark(Blackhole bh) {
  //   InvertedFileIndex inverted_file_index = new InvertedFileIndex(
  //     "../testing_texts/the_dog_and_the_shadow.txt"
  //   );
  //   String output = inverted_file_index.toString();
  //   bh.consume(output);
  // }

  // @Benchmark
  // public void theEagleAndTheFoxBenchmark(Blackhole bh) {
  //   InvertedFileIndex inverted_file_index = new InvertedFileIndex(
  //     "../testing_texts/the_eagle_and_the_fox.txt"
  //   );
  //   String output = inverted_file_index.toString();
  //   bh.consume(output);
  // }

  // @Benchmark
  // public void thePhoenixBirdBenchmark(Blackhole bh) {
  //   InvertedFileIndex inverted_file_index = new InvertedFileIndex(
  //     "../testing_texts/the_phoenix_bird.txt"
  //   );
  //   String output = inverted_file_index.toString();
  //   bh.consume(output);
  // }

  @Benchmark
  public void twoLittleSoldiersBenchmark(Blackhole bh) {
    InvertedFileIndex inverted_file_index = new InvertedFileIndex(
      "../testing_texts/two_little_soldiers.txt"
    );
    String output = inverted_file_index.toString();
    bh.consume(output);
  }
}
