# cutlet

cutlet - Panel Cut Optimization [![Build Status](https://travis-ci.org/mru00/cutlet.svg?branch=master)](https://travis-ci.org/mru00/cutlet)


**not production ready**


cutlet is my naiive approach on guillotine panel cut optimization, primarily concerned with cutting panels from stock sheets of wood.


There are many [commercial solutions](#commercial-products) available, and quite some [Open Source implementations](#open-source-implementations).

This project is implemented in Java 1.8, using Maven. Gui is in JavaFX/FXML.

![image](https://cloud.githubusercontent.com/assets/581904/23991289/0bb93818-0a39-11e7-9533-50c75cacdcda.png)

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
- [x] run optimization in worker 
- [x] i18n prepared
- [x] save & load (via java serialization)
- [x] layout in FXML / SceneBuilder
- [ ] panning & zooming
- [ ] parts editor / table
- [ ] stock keeping
- [ ] settings/configuration 

### Benchmark

- [x] basically works

It is executed as a unit test in "mvn test", also in travis.
You can have a look at [![Build Status](https://travis-ci.org/mru00/cutlet.svg?branch=master)](https://travis-ci.org/mru00/cutlet) to see it's output.

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


## Related Work

https://github.com/Jack000/SVGnest

Simulated Annealing taken from http://cs.gettysburg.edu/~tneller/resources/sls/index.html




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



