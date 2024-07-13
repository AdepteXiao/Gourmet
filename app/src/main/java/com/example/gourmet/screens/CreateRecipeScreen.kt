package com.example.gourmet.screens

import android.net.Uri
import coil.compose.AsyncImage
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.request.ImageRequest
import com.example.gourmet.R
import com.example.gourmet.ui.theme.Additional
import com.example.gourmet.ui.theme.Background
import com.example.gourmet.ui.theme.DarkAccent
import com.example.gourmet.ui.theme.LightAccent
import com.example.gourmet.ui.theme.LightText
import com.example.gourmet.ui.theme.Stroke
import com.example.gourmet.ui.theme.TextColor
import com.example.gourmet.vmodels.RecipeViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
fun CreateRecipeScreen(vm: RecipeViewModel, navHostController: NavHostController) {
    var isEditing by remember { mutableStateOf(false) }
    var searchText: String by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .background(Background),

        ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .background(DarkAccent)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.return_arrow_icon),
                        contentDescription = "Добавить рецепт",
                        modifier = Modifier.padding(10.dp),
                        tint = LightText
                    )
                }

            }
        }
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                CreateMainInfo(vm)
                CreateIngredients(vm)
                CreateSteps(vm)
            }
        }
    }
}

@Composable
fun EditableTextField() {
    var isEditing by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("Название") }

    if (isEditing) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { isEditing = false }),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 12.dp)
        )
    } else {
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 12.dp)
                .clickable { isEditing = true }
        )
    }
}

@Composable
fun CreateMainInfo(vm: RecipeViewModel) {
    var imageUri by remember { mutableStateOf<Uri?>(vm.recipe.image) }
    var isEditing by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(vm.recipe.name) }
    var editDescription by remember { mutableStateOf(vm.recipe.description) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
                vm.onImageSelected(it)
            }
        }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Text(
        text = "Основное",
        modifier = Modifier
            .padding(10.dp),
        fontSize = 16.sp,
        color = TextColor
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightAccent)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Фотография блюда",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        launcher.launch("image/*")
                        imageUri = vm.recipe.image
                    }
                    .fillMaxWidth(0.9f)
                    .heightIn(max = 250.dp)
                    .padding(top = 15.dp),
                placeholder = ColorPainter(MaterialTheme.colorScheme.primary)

            )
            TextField(
                value = if (isEditing) editName ?: "" else vm.recipe.name ?: "",
                onValueChange = { newText ->
                    editName = newText
                    isEditing = true
                },
                placeholder = { Text(text = "Название") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    vm.recipe.name = editName
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    isEditing = false
                }),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = LightText,
                ),
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 12.dp)
            )

            TextField(
                value = if (isEditing) editDescription ?: "" else vm.recipe.description ?: "",
                onValueChange = { newText ->
                    editDescription = newText
                    isEditing = true
                },
                placeholder = { Text(text = "Описание") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    vm.recipe.description = editDescription
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    isEditing = false
                }),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = LightText,
                    unfocusedTextColor = LightText,
                    unfocusedPlaceholderColor = LightText,
                ),
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Composable
fun CreateIngredient(vm: RecipeViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isEditing by remember { mutableStateOf(false) }
    var editIngName by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.9f)
    ) {
        TextField(
            value = editIngName ?: "",
            onValueChange = { newText ->
                editIngName = newText
                isEditing = true
            },
            placeholder = { Text(text = "Ингредиент") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                vm.recipe.description = editIngName
                keyboardController?.hide()
                focusManager.clearFocus()
                isEditing = false
            }),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightAccent,
                focusedContainerColor = LightAccent,
                focusedTextColor = LightText,
                unfocusedTextColor = LightText,
                unfocusedPlaceholderColor = LightText,
            ),
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 12.dp)
        )
        Spacer(modifier = Modifier.weight(0.4f))

        OutlinedTextField(
            value = editIngName ?: "",
            onValueChange = { newText ->
                editIngName = newText
                isEditing = true
            },
            placeholder = { Text(text = "Ингредиент") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                vm.recipe.description = editIngName
                keyboardController?.hide()
                focusManager.clearFocus()
                isEditing = false
            }),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = LightAccent,
                focusedContainerColor = LightAccent,
                focusedTextColor = TextColor,
                unfocusedTextColor = TextColor,
                unfocusedPlaceholderColor = LightText,
            ),
            maxLines = 2,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 12.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "Кг",
            color = TextColor,
            fontSize = 18.sp,
            lineHeight = 18.sp,
            modifier = Modifier
                .padding(end = 15.dp)
        )

    }
}

@Composable
fun CreateIngredients(vm: RecipeViewModel) {
    Text(
        text = "Ингредиенты",
        modifier = Modifier
            .padding(10.dp),
        fontSize = 16.sp,
        color = TextColor
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightAccent)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(10) {
                CreateIngredient(vm)

            }
        }

    }
}


@Composable
fun CreateStepCard(vm: RecipeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp)
            .background(LightAccent)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Шаг 1",
                modifier = Modifier
                    .padding(15.dp),
                fontSize = 16.sp,
                color = TextColor
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                color = Stroke,
                thickness = 0.5.dp
            )
            Image(
                painter = painterResource(R.drawable.food),
                contentDescription = "Фотография блюда",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .heightIn(max = 200.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Выглядит вкусно, по сути вкусно, по вкусу тоже вкусно",
                color = TextColor,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                modifier = Modifier
                    .padding(0.dp, 20.dp)
                    .fillMaxWidth(0.9f),
            )
        }
    }
}

@Composable
fun CreateSteps(vm: RecipeViewModel) {
    Text(
        text = "Приготовление",
        modifier = Modifier
            .padding(10.dp),
        fontSize = 16.sp,
        color = TextColor
    )
    repeat(10) {
        CreateStepCard(vm)
    }
}

