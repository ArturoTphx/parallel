package com.example.parallel.utils

import com.example.parallel.domain.notes.models.Benchmark
import com.example.parallel.domain.notes.models.Note

object Constants {

    // Identifiers for each mode of threading
    const val MONO_THREAD = "mono"
    const val MULTI_THREAD = "multi"

    // List of benchmarks mock for previews
    val MOCK_BENCHMARKS: List<Benchmark> = listOf(
        Benchmark(dataAmount = 100, monoSecs = 15f, multiSecs = 10f),
        Benchmark(dataAmount = 200, monoSecs = 25f, multiSecs = 20f),
        Benchmark(dataAmount = 300, monoSecs = 35f, multiSecs = 30f),
        Benchmark(dataAmount = 400, monoSecs = 45f, multiSecs = 40f),
        Benchmark(dataAmount = 500, monoSecs = 55f, multiSecs = 50f)
    )

    // List of notes mock for previews
    val FAKE_NOTES = listOf(
        Note(id = "0", title = "Viaje", body = "- Alistar la comida\n- Alistar la ropa"),
        Note(id = "0", title = "Examen", body = "- Paradigmas\n- Progra"),
        Note(id = "0", title = "Proyecto", body = "- Agruparse\n- Investigar"),
        Note(id = "0", title = "Tesis", body = "- Investigar\n- Hacer documento")
    )
}