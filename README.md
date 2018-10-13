# cutlet

cutlet - Panel Cut Optimization [![Build Status](https://travis-ci.org/mru00/cutlet.svg?branch=master)](https://travis-ci.org/mru00/cutlet)


**not production ready**


cutlet is my naiive approach on guillotine panel cut optimization, primarily concerned with cutting panels from stock sheets of wood.


There are many [commercial solutions](#commercial-products) available, and quite some [Open Source implementations](#open-source-implementations).

This project is implemented in Java 1.8, using Maven. Gui is in JavaFX/FXML.

![image](https://user-images.githubusercontent.com/581904/46910471-1cf03f00-cf45-11e8-95e0-7b8de67c140a.png)

If you want to contribute - please do so, any help would be appreciated.

If you want to use this project - please do so, I would be happy to hear how it is working for you, and what features are missing.

## Overview


There are 3 projects:
* Lib
* Gui
* Benchmark

The library implements the data structures and algorithms. 
Gui will become... well... the GUI. 
Benchmark runs examples and compares them.

A goal was to implement a solid data structure to provide a good framework for research / surveys.

## Goals

- [x] Platform agnostic
- [x] easy "getting started" and deployment
 - Java 1.8, Maven, JavaFX
- [x] built around reusable core library
- [x] build solid data structure
- [x] optimization algorithm agnostic / easy to add new algorithms
- [x] open source
- [x] enable meaningful evaluation of optimization algorithms
- [x] translatable gui

## Implementation status

This project is in it's very early stages, so don't expect too much.

### Lib 

- [x] Basic Data structure
- [x] Basic algorithms
- [x] calculate statistics for a generated layout
- [ ] Rotation & grain
- [ ] material database
- [ ] stock database 
- [x] saw kerf / blade thickness


### Gui


- [x] visalize layout
- [x] run optimization in worker thread
- [x] i18n prepared
- [x] save & load (via java serialization)
- [x] layout in FXML / SceneBuilder
- [x] panning
- [ ] zooming
- [ ] parts editor / table
- [ ] stock keeping
- [ ] settings/configuration 

### Benchmark

- [x] basically works

It is executed as a unit test in "mvn test", also in travis.
You can have a look at [![Build Status](https://travis-ci.org/mru00/cutlet.svg?branch=master)](https://travis-ci.org/mru00/cutlet) to see it's output.

Example output:

```
Method     dataset          #panels    #sheets     waste%      #cuts  cutlength    fitness
Smart      Data1                  7          1       58.8         12       3378    16214.4
Smart      Random_100            15          2       67.0         29       7651    73449.6
Smart      Random_201            14          2       73.1         28       9014    86534.4
Smart      DataRegal             40          3       22.9         60      37040  3472500.0
Smart      Tisch                 17          2       31.6         25      16104   483120.0
Smart      Data1                  0          0        NaN          0          0        0.0
Naiive     Data1                  7          1       58.8         14       4327    20769.6
Naiive     Random_100            15          1       34.0         30       8267    39681.6
Naiive     Random_201            14          1       46.2         28       8929    42859.2
Naiive     DataRegal             40          3       22.9         68      42208  3957000.0
Naiive     Tisch                 17          2       31.6         30      18020   540600.0
Naiive     Data1                  0          0        NaN          0          0        0.0
SA         Data1                  7          1       58.8         12       3629    17419.2
SA         Random_100            15          1       34.0         28       7055    33864.0
SA         Random_201            14          1       46.2         27       7816    37516.8
SA         DataRegal             40          3       22.9         61      38290  3589687.5
SA         Tisch                 17          2       31.6         25      15020   450600.0
SA         Data1                  0          0        NaN          0          0        0.0
SA2        Data1                  7          1       58.8         12       3629    17419.2
SA2        Random_100            15          1       34.0         29       6993    33566.4
SA2        Random_201            14          1       46.2         27       7739    37147.2
SA2        DataRegal             40          3       22.9         63      38374  3597562.5
SA2        Tisch                 17          2       31.6         25      15580   467400.0
SA2        Data1                  0          0        NaN          0          0        0.0
GA         Data1                  7          1       58.8         12       3629    17419.2
GA         Random_100            15          1       34.0         29       7911    37972.8
GA         Random_201            14          1       46.2         27       8300    39840.0
GA         DataRegal             40          3       22.9         67      38542  3613312.5
GA         Tisch                 17          2       31.6         25      16012   480360.0
GA         Data1                  0          0        NaN          0          0        0.0
```

## Implemented optimization algorithms

* Simmulated Annealing (based on http://cs.gettysburg.edu/~tneller/resources/sls/index.html)
* another SA implementation
* a Genetic Algorithm implementation
* A naiive single pass algorithm
* A less naiive single pass algorithm


## TODO


- [ ] attract collaborators
- [ ] collect "must have" features & implement them
- [ ] test suite
- [ ] implement panel editor in GUI
- [ ] add more algorithms
- [ ] find better cost/fitness functions 
- [ ] interface to saw
- [ ] cost calculation
- [ ] printing
 - labels, part list, 
- [ ] search for more alternatives; populate related work
- [ ] manual layouts 
- [x] saw blade thickness / saw kerf
- [x] find deluxe simulated annealing framework
- [x] find deluxe genetic algorithm framework


## Dependencies

Project | type | description
--------|------|------------
http://jenetics.io/ | maven | genetic algorithm
https://github.com/google/guava | maven | general java stuff
https://pathfinder.readme.io/blog/simmulated-annealing | maven | simulated annealing algorithm
http://cs.gettysburg.edu/~tneller/resources/sls/index.html | source | another sim. ann.

## Compile & run

This project uses [Maven](https://maven.apache.org/) to manage library dependencies, build & run.

This means: install maven and execute:

 mvn test

The code does not compile on openjdk, the dependency lombok does not work. So the ubuntu users out there should install the oracle jdk.

## Related Work

https://github.com/Jack000/SVGnest

Simulated Annealing taken from http://cs.gettysburg.edu/~tneller/resources/sls/index.html

- http://stackoverflow.com/questions/4360637/cut-optimisation-algorithm
- http://stackoverflow.com/questions/23922557/algorithm-for-pipe-cutting-optimization?rq=1
- https://en.wikipedia.org/wiki/Cutting_stock_problem
- https://en.wikipedia.org/wiki/Guillotine_problem
- http://www.amzi.com/articles/papercutter.htm




### Open source implementations


Project | Looks active? | Lang | Comment
------------ | ------------- | ------------- | -------------
[COPanno](https://sourceforge.net/projects/copanno) | yes | Java | french source code?
[raskroy](https://github.com/denisenkom/raskroy) [SF](https://sourceforge.net/projects/cutoptima) | no | C++ / C# | english c#, some russian
[Cut Micro](https://sourceforge.net/projects/ctmc) | yes | C# |
[Cutting Problem](https://sourceforge.net/projects/cuttingproblem) | | Java |



### Commercial Products
* http://www.maxcutsoftware.com/
* http://www.optimalon.com/online_cut_optimizer.htm
* http://www.panel.com.hr/EngHome.aspx
* http://alternativeto.net/software/gncutter/

Project | Looks active? | Lang | Comment
------------ | ------------- | ------------- | -------------
[Optimierung](https://sourceforge.net/projects/optimierung) | | Delphi | 1D optimization



