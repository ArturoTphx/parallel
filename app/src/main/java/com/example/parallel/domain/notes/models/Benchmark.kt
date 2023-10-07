package com.example.parallel.domain.notes.models

// This class stores the benchmark result between mono threading and multi threading performance
data class Benchmark(
    val dataAmount: Int = 0,
    val monoSecs: Float = 0.0f,
    val multiSecs: Float = 0.0f,
    val delta: Float =  monoSecs - multiSecs
)
