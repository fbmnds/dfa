# DFA - http://www.4clojure.com/problem/164

A deterministic finite automaton (DFA) is an abstract machine that recognizes a regular language. Usually a DFA is defined by a 5-tuple, but instead we'll use a map with 5 keys:
:states is the set of states for the DFA.
:alphabet is the set of symbols included in the language recognized by the DFA.
:start is the start state of the DFA.
:accepts is the set of accept states in the DFA.
:transitions is the transition function for the DFA, mapping :states ? :alphabet onto :states.

For the description of a fully generic DFA, two keys could be added:
:fn-transform-payload is the users' function for transformation of the specified payload
:fns-transform-conditions is a map {q-i -> [q-j, condition-ij]}, where condition-ij rules on the transformation from q-i to q-j

The usage of these additional keys could be straight forward included in the dfa.core code base.

## Usage

dfa.core/dfa-seq
([dfa])
Function
 lazy-seq of status defived from DFA definition map.

## License

Copyright © 2013 fbmnds

Distributed under the Eclipse Public License, the same as Clojure.
