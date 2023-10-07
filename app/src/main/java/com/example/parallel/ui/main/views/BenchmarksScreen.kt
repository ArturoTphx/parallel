package com.example.parallel.ui.main.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.parallel.R
import com.example.parallel.domain.notes.models.Benchmark
import com.example.parallel.ui.main.interaction.MainEvent
import com.example.parallel.ui.main.interaction.MainState
import com.example.parallel.ui.theme.ParallelTheme
import com.example.parallel.utils.Constants
import com.example.parallel.utils.CustomPreviews

@Composable
fun BenchmarksScreen(mainState: MainState, onViewModelInteraction: (MainEvent) -> Unit) {

    val benchmarks = mainState.benchmarks
    val dataAmount = remember { mutableStateOf(50f) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = stringResource(R.string.data_amount, dataAmount.value))

            Spacer(Modifier.height(10.dp))

            Slider(
                steps = 10,
                valueRange = 100f..1000f,
                value = dataAmount.value,
                onValueChange = { dataAmount.value = it }
            )

            Spacer(Modifier.height(10.dp))

            if (!mainState.loading) {
                Button(onClick = { onViewModelInteraction(MainEvent.Run(dataAmount.value.toInt())) }) {
                    Text(text = stringResource(R.string.run))
                }
            } else {
                CircularProgressIndicator()
            }

            Spacer(Modifier.height(20.dp))

            TableScreen(benchmarks = benchmarks)
        }

    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    custom: Boolean = false,
    color: Color = Color.Green
) {
    Text(
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.onBackground)
            .weight(weight)
            .padding(8.dp),
        text = text,
        color = if (custom) color else MaterialTheme.colorScheme.onBackground
    )
}

@Composable
fun TableScreen(benchmarks: List<Benchmark>) {

    val colWeight = .2f

    LazyColumn(
        Modifier.fillMaxSize()
    ) {
        item {
            Row(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
                TableCell(text = stringResource(R.string.data_column), weight = colWeight)
                TableCell(text = stringResource(R.string.mono_thread_column), weight = colWeight)
                TableCell(text = stringResource(R.string.multi_thread_column), weight = colWeight)
                TableCell(text = stringResource(R.string.results_column), weight = colWeight)
            }
        }
        itemsIndexed(benchmarks) { _, benchmark ->
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = benchmark.dataAmount.toString(), weight = colWeight)
                TableCell(text = benchmark.monoSecs.toString(), weight = colWeight)
                TableCell(text = benchmark.multiSecs.toString(), weight = colWeight)
                TableCell(
                    text = benchmark.delta.toString(),
                    weight = colWeight,
                    custom = true,
                    color = if (benchmark.delta >= 0) Color.Green else Color.Red
                )
            }
        }
    }
}

@CustomPreviews
@Composable
fun HomePreview() {
    ParallelTheme {
        BenchmarksScreen(mainState = MainState(benchmarks = Constants.MOCK_BENCHMARKS), onViewModelInteraction = {})
    }
}