Improved Multithreaded Unit Testing (IMUnit) research, University of Illinois Urbana-Champaign, Professor Darko Marinov

This is the demo version of the IMUnit research that I helped Vilas Jagannath as a undergraduate research assistant from Sep 2011 to May 2012.

IMUnit is a framework for writing and executing multithreaded unit tests. 
A multithreaded test exercises the code under test with two or more threads.
Each test execution follows some schedule/interleaving of the multiple threads, and different schedules can give different results. 
IMUnit enables explicit and modular specification of schedules as orderings on events encountered during test execution.
IMUnit also provides a tool that automatically enforces test execution to follow the specified schedules. 
Contrasted to the currently dominant sleep-based tests, IMUnit tests are more intuitive, do not have false positives or negatives, and can also run substantially faster.

For more information, please refer to http://mir.cs.illinois.edu/imunit/
