---
title: 'EvoMaster: A Search-Based System Test Generation Tool'
tags:
  - Java
  - Kotlin
  - SBST
  - test generation
  - system testing
  - REST

authors:
  - name: Andrea Arcuri
    orcid: 0000-0003-0799-2930
    affiliation: 1
  - name: Juan Pablo Galeotti
    orcid: 0000-0002-0747-8205
    affiliation: 2
  - name: Bogdan Marculescu
    orcid: 0000-0002-1393-4123
    affiliation: 1
  - name: Man Zhang
    orcid: 0000-0003-1204-9322
    affiliation: 1
affiliations:
 - name: Kristiania University College, Department of Technology, Oslo, Norway
   index: 1
 - name: FCEyN-UBA, and ICC, CONICET-UBA, Depto. de Computaci\'on, Buenos Aires, Argentina
   index: 2
date:  January 2019
bibliography: paper.bib
---


# Summary

Testing web/enterprise applications is complex and expensive when done manually.
Therefore, in *Software Engineering* (SE) research, a lot of effort has been spent in trying 
to design and implement novel techniques aimed at automating several different tasks in SE.
*Search-Based Software Testing* (SBST) casts the problem of software testing as an optimization problem,
aimed at, for example, maximizing code coverage and fault detection.   

``EvoMaster`` [@arcuri2018evomaster]  is a SBST tool 
that automatically *generates* system-level test cases.
Internally, it uses an *Evolutionary Algorithm* 
and *Dynamic Program Analysis*  to be able to generate effective test cases.
The approach is to *evolve* test cases from an initial population of 
random ones, using code coverage and fault detection as fitness function.

When addressing the testing of real-world web/enterprise applications, there are many challenges. 
To face and overcome those challenges, EvoMaster has been used to experiment with several novel techniques.
This led to several publications:
novel search algorithms such as *MIO* [@mio2017][@arcuri2018test],
addressing the white-box testing of RESTful APIs [@arcuri2017restful][@arcuri2019restful],
resource-dependency handling [@zhang2019resource], accesses to SQL databases [@arcuri2019sql],
and novel *testability transformations* [@arcuri2020testability].


``EvoMaster`` is aimed both at practitioners that want to automatically test their software, 
and at researchers that need generated test cases for the SE problems that they are investigating.  

At the moment, ``EvoMaster`` targets RESTful APIs compiled to 
JVM __8__ and __11__ bytecode.
The APIs must provide a schema in *OpenAPI/Swagger* format (either _v2_ or _v3_).
The tool generates JUnit (version 4 or 5) tests, written in either Java or Kotlin.


# Acknowledgements
We thank Annibale Panichella for providing a review and fix of our implementation of his MOSA algorithm. 
This work is funded by the Research Council of Norway (project on Evolutionary Enterprise Testing, grant agreement No 274385), and 
partially by UBACYT-2018 20020170200249BA, PICT-2015-2741.

# References