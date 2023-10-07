package com.example.parallel.data.notes.local

import android.content.Context
import android.util.Log
import com.example.parallel.data.notes.models.NoteItem
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * The FileEngine class is in charge of saving, reading and deleting the notes.
 * This class needs a Context to use the Android's File Manager
 */
class FileEngine @Inject constructor(private val context: Context) {

    /**
     * Deserializer in charge of transforming raw text from a file and transform it to a list of notes.
     */
    private fun deSerialize(block: String): List<NoteItem> {

        // Regular expression that detects a serialized note in raw text
        val regex = Regex("\\|id:=(\\w+)\\|\\|title:=(\\w+)\\|\\|body:=(\\w+)\\|")

        // This pointer is in charge of reading all the raw text (block) of the file
        // and later reduce it when it successfully captures a note
        var pointer = block

        // List of notes
        val noteItems = ArrayList<NoteItem>()

        // Match of the regular expression according to the pointer variable
        var match = regex.find(pointer)

        // While there is a match for the regex and the pointer variable
        while(match != null) {

            val rawNote = match.value

            // Reduces the pointer variable replacing the raw text note for an empty string
            pointer = pointer.replaceFirst(rawNote, "")

            // All the regular expression groups transformed into variables
            val (id, title, body) = match.destructured

            val noteItem = NoteItem(id = id, title = title, body = body)

            // The note is added to a list
            noteItems.add(noteItem)

            // Find another match (if possible), with the updated pointer variable
            match = regex.find(pointer)
        }

        return noteItems
    }

    /**
     * Loads a list of notes to a file making use of a serializer implemented by overriding the
     * toString() method of the note
     */
    fun loadToFile(notes: List<NoteItem>, fileParams: FileParams) {
        try {

            val file = File(context.getExternalFilesDir(fileParams.filePath), fileParams.fileName)

            val fOutputStream = FileOutputStream(file, true)

            for (note in notes) {
                fOutputStream.write(note.toString().toByteArray())
            }

            fOutputStream.close()

        } catch (e: Exception) {
            resetFile(fileParams)
            Log.e("Load file", e.toString())
        }
    }

    /**
     * Reads a list of notes to a file making use of the deserializer above
     */
    fun readFromFile(fileParams: FileParams): List<NoteItem> {
        return try {

            val file = File(context.getExternalFilesDir(fileParams.filePath), fileParams.fileName)

            val fInputStream = FileInputStream(file)

            val inputStream = InputStreamReader(fInputStream)

            val bufferedReader = BufferedReader(inputStream)

            val stringBuilder = StringBuilder()

            var text: String? = bufferedReader.readLine()

            while (text != null) {
                stringBuilder.append(text)
                text = bufferedReader.readLine()
            }

            fInputStream.close()

            val rawNotes = stringBuilder.toString()

            deSerialize(rawNotes)
        } catch (e: Exception) {
            resetFile(fileParams)
            Log.e("Load file", e.toString())
            emptyList()
        }
    }

    /**
     * Resets (or create) a file by initializing it with an empty string
     */
    fun resetFile(fileParams: FileParams) {
        try {
            val file = File(context.getExternalFilesDir(fileParams.filePath), fileParams.fileName)

            val fOutputStream = FileOutputStream(file)

            fOutputStream.write("".toByteArray())

            fOutputStream.close()
        } catch (e: Exception) {
            print(e)
        }
    }

}