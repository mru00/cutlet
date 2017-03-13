# cutlet

cutlet - Panel Cut Optimization

cutlet is my approach on guillotine panel cut optimization. There are many commercial solutions available, but I found no promising Open Source implementation.


![image](https://cloud.githubusercontent.com/assets/581904/23873417/3dcddb1e-0832-11e7-8f96-502afaa06bf2.png)

## Overview


There are 3 projects:
* Lib
* Gui
* Benchmark

The library implements the data structures and algorithms. Gui will become... well... the GUI. Benchmark run examples and compares them.

A goal was to implement a solid data structure to provide a good framework for research / surveys.

## Implementation status:

This project is in it's very early stages, so don't expect too much.

* Algorithms work & results don't look too bad
* GUI is just started
* Benchmark does something, useful to compare the algorithms


## Implemented optimization algorithms:
* Simmulated Annealing
* some cheap heuristic


## TODO:

- [ ] find collaborators
- [ ] implement panel editor in GUI
- [ ] collect "must have" features & implement them
- [ ] add more algorithms
- [ ] interface to saw


## Related Work:

### Open source implementations:
* https://sourceforge.net/projects/ctmc/

### Commercial Products:
* http://www.maxcutsoftware.com/
* http://www.optimalon.com/online_cut_optimizer.htm
* http://www.panel.com.hr/EngHome.aspx
* http://alternativeto.net/software/gncutter/
