package com.thelazypeople.markov_chain_visualiser

class TrainingDataKeeper {
    val dictFutureWord = mutableMapOf<String, MutableMap<String,Int>>()
    val dictCompleteWord = mutableMapOf<String, MutableMap<String,Int>>()
}