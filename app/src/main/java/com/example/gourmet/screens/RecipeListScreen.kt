package com.example.gourmet.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gourmet.R
import com.example.gourmet.models.Recipe
import com.example.gourmet.navigation.Router
import com.example.gourmet.ui.theme.Additional
import com.example.gourmet.ui.theme.Background
import com.example.gourmet.ui.theme.DarkAccent
import com.example.gourmet.ui.theme.LightAccent
import com.example.gourmet.ui.theme.LightText
import com.example.gourmet.ui.theme.Stroke
import com.example.gourmet.ui.theme.TextColor
import com.example.gourmet.vmodels.RecipeListViewModel
import com.example.gourmet.withBaseUrl

@Composable
fun RecipeListScreen(
    navController: NavHostController,
    vm: RecipeListViewModel = viewModel { RecipeListViewModel() }
) {
    val recipeList by vm.recipeList.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var searchText: String by remember { mutableStateOf("")}
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(modifier = Modifier.background(Background)) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(DarkAccent)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        isEditing = true
                    },
                    placeholder = { Text(text = "Поиск") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                        isEditing = false
                    }),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = DarkAccent,
                        focusedContainerColor = Additional,
                        focusedTextColor = TextColor,
                        unfocusedTextColor = TextColor,
                        unfocusedPlaceholderColor = LightText,
                        focusedPlaceholderColor = LightText,
                        focusedIndicatorColor = Additional,
                    ),
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .fillMaxWidth(0.9f)
                )
                IconButton(onClick = {
                    navController.navigate(Router.CreateRecipe)
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_icon),
                        contentDescription = "Добавить рецепт",
                        modifier = Modifier.padding(10.dp),
                        tint = LightText
                    )
                }

            }
        }
        Row {
            Button(

                modifier = Modifier
                    .padding(5.dp),
                border = BorderStroke(0.5.dp, Stroke),
                contentPadding = PaddingValues(4.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(Background),
                onClick = { /*TODO*/ }) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Фильтр",
                    color = TextColor,
                    fontSize = 12.sp
                )
            }
            Button(
                modifier = Modifier
                    .padding(0.dp, 5.dp),
                border = BorderStroke(0.5.dp, Stroke),
                contentPadding = PaddingValues(4.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(Background),
                onClick = { /*TODO*/ }) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "Сортировать",
                    color = TextColor,
                    fontSize = 12.sp
                )
            }
        }
        LazyColumn {
            items(recipeList) { recipe ->
                RecipeCard(navController, recipe)
                Spacer(modifier = Modifier.height(5.dp))
            }

        }

    }
}

@Composable
fun RecipeCard(navController: NavHostController, recipe: Recipe) {
    val id: String = recipe.id
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
//        .border(0.5.dp, Stroke)
            .background(LightAccent)
            .clickable { navController.navigate(Router.Recipe + "/$id") }

    ) {
        Row {
            if (recipe.image != Uri.EMPTY) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recipe.image.withBaseUrl())
                    .crossfade(true)
                    .build(),
                contentDescription = "Фотография блюда",
                modifier = Modifier.fillMaxWidth(0.45f),
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(DarkAccent)
            )
        }
            Column(modifier = Modifier.padding(10.dp, 15.dp)) {
                Text(
                    text = recipe.name,
                    color = TextColor,
                    fontSize = 18.sp,
                    lineHeight = 18.sp
                )
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 7.dp)
                        .fillMaxWidth(0.4f),
                    thickness = 0.5.dp,
                    color = Stroke
                )
                Text(
                    text = recipe.description,
                    color = LightText,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    overflow = TextOverflow.Ellipsis,
                )

            }

        }
    }
}
