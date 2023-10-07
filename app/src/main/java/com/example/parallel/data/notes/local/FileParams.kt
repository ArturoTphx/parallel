package com.example.parallel.data.notes.local

// Sealed class in charge of separate mono thread and multi thread note files
sealed class FileParams(val filePath: String, val fileName: String) {
    object Mono: FileParams(fileName = "mono", filePath = "mono_path")
    object Multi: FileParams(fileName = "multi", filePath = "multi_path")
}