#! /bin/bash -xeu


for d in CutletLib CutletBenchmark CutletGui2; do

  ( cd $d && mvn -B compile && mvn -B test )

done

cd CutletBenchmark && mvn -B run


