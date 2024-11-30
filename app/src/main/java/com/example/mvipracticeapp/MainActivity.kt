package com.example.mvipracticeapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mvipracticeapp.api.AnimalService
import com.example.mvipracticeapp.model.Animal
import com.example.mvipracticeapp.ui.theme.MVIPracticeAppTheme
import com.example.mvipracticeapp.view.AnimalIntents
import com.example.mvipracticeapp.view.AnimalStates
import com.example.mvipracticeapp.viewmodel.AnimalViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val animalViewModel: AnimalViewModel by inject()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
         //val animalViewModel = getViewModel<AnimalViewModel> ()
        val onButtonClick: () -> Unit = {
            lifecycleScope.launch {
                animalViewModel.userIntent.send(AnimalIntents.FetchAnimals)
            }
        }
        setContent {
            MVIPracticeAppTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Animals List") }) },
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        MainScreen(viewModel = animalViewModel, onButtonClick = onButtonClick)
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen(viewModel: AnimalViewModel, onButtonClick: () -> Unit) {
    val state by viewModel.uiState.collectAsState()
    when(state) {
        AnimalStates.Idle -> IdleScreen(onButtonClick)
        AnimalStates.Loading -> LoadingState()
        is AnimalStates.Error -> { IdleScreen(onButtonClick)
            Toast.makeText(
                LocalContext.current,
                (state as AnimalStates.Error).error,
                Toast.LENGTH_SHORT
            ).show()
        }
        is AnimalStates.ShowAnimalState -> AnimalListScreen(animals = (state as AnimalStates.ShowAnimalState).animals)
    }
}

@Composable
fun IdleScreen(onButtonCLick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Button(onClick = onButtonCLick) {
            Text("Fetch Animals")
        }
    }
}

@Composable
fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun AnimalListScreen(animals: List<Animal>) {
    LazyColumn {
       items(items = animals) { animal ->
           AnimalItems(animal = animal)
           Spacer(modifier = Modifier.height(4.dp))
           HorizontalDivider(
               color = Color.Red,
               modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
           )
       }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AnimalItems(animal: Animal) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val url = AnimalService.BASE_URL + animal.image
        val painter = rememberImagePainter(data = url)
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(100.dp).padding(4.dp).clip(CircleShape),
            contentScale = ContentScale.FillHeight


        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 4.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center

        ) {
            Text(text = animal.name, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(text = animal.location.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() })
        }
    }
}



