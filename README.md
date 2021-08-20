# Parallel_Mandelbrot_Java

Generating the Mandelbrot set via the Thread class and Runnable interface in the Java programming language.

## Quick start

### For multithreaded version
```console
cd ParMandel
./makeMe.sh
./runMe.sh <numThreads> <granularity> <complexity>
```

### For singlethreaded version
```console
run ParMandel.java with IntelliJ
```

## Results
See `ParMandel/res.png`. Note that it is in 4K and will likely not fit in the window.

## TODO

- [X] more appropriate class names
- [X] more colors
- [X] add command line parameters
- [X] implement Runnable interface (and put actual code in the run method)
- [X] use granularity
- [X] add profiling
- [X] add complexity as a command-line parameter
- [ ] make one thread that starts `p - 1` other threads
- [ ] write a master-slave version

## References:
 
- how to: https://www.youtube.com/watch?v=6z7GQewK-Ks&t=247s
- colors: https://stackoverflow.com/questions/29664389/adding-color-layers-to-the-mandelbrot-set
