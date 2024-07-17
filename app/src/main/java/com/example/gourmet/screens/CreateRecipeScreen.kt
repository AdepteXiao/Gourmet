package com.example.gourmet.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gourmet.R
import com.example.gourmet.ui.theme.Background
import com.example.gourmet.ui.theme.DarkAccent
import com.example.gourmet.ui.theme.LightAccent
import com.example.gourmet.ui.theme.LightText
import com.example.gourmet.ui.theme.Stroke
import com.example.gourmet.ui.theme.TextColor
import com.example.gourmet.vmodels.CreateRecipeViewModel
import com.example.gourmet.vmodels.RecipeViewModel

@Composable
fun CreateRecipeScreen(vm: CreateRecipeViewModel, navHostController: NavHostController) {
    val context = LocalContext.current
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
                        contentDescription = "Вернуться к списку рецептов",
                        modifier = Modifier.padding(10.dp),
                        tint = LightText
                    )
                }

            }
        }
        LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
            item {
                TagInputField(vm)
                CreateMainInfo(vm)
                CreateIngredients(vm)
                CreateSteps(vm)
                Button(onClick = {vm.onSaveRecipe()}){
                    Text("Сохранить")
                }
            }
        }
    }
}

@Composable
fun TagInputField(vm: CreateRecipeViewModel) {
    var tagInput by remember { mutableStateOf("") }
    var tagsList by remember { mutableStateOf(vm.tags) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightAccent),
        contentAlignment = Alignment.Center

        ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = tagInput,
                onValueChange = { tagInput = it },
                label = { Text("Добавить тег") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 5.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = TextColor,
                    focusedLabelColor = Stroke,
                    unfocusedLabelColor = Stroke,
                    unfocusedBorderColor = Stroke,
                    focusedBorderColor = Stroke,

                    ),
            )
            IconButton(modifier = Modifier.size(50.dp), onClick = {
                if (tagInput.isNotEmpty())
                    tagsList = tagsList + tagInput
                    vm.tags = tagsList
                tagInput = ""
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = "Добавить рецепт",
//                    modifier = Modifier.padding(10.dp),
                    tint = LightText
                )
            }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            }
            tagsList.forEach { tag ->
                Text(text = tag, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun CreateMainInfo(vm: CreateRecipeViewModel) {
    var imageUri by remember { mutableStateOf<Uri?>(vm.recipe.image) }
    var isEditing by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(vm.recipe.name) }
    var editDescription by remember { mutableStateOf(vm.recipe.description) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
//                vm.onImageSelected(it)

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
                placeholder = ColorPainter(DarkAccent)

            )
            TextField(
                value = if (editName != "") editName ?: "" else vm.recipe.name ?: "",
                onValueChange = { newText ->
                    editName = newText
                },
                placeholder = { Text(text = "Название") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    vm.recipe.name = editName
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = LightText,
                    focusedLabelColor = Stroke,
                    unfocusedLabelColor = Stroke,
                    unfocusedBorderColor = Stroke,
                    focusedBorderColor = Stroke,

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
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = LightText,
                    focusedLabelColor = Stroke,
                    unfocusedLabelColor = Stroke,
                    unfocusedBorderColor = Stroke,
                    focusedBorderColor = Stroke,

                    ),
                maxLines = 2,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIngredient(vm: CreateRecipeViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isEditing by remember { mutableStateOf(false) }
    var editIngName by remember { mutableStateOf("") }
    var editIngAmount by remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxWidth(0.9f)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = editIngName ?: "",
                onValueChange = { newText ->
                    editIngName = newText
                    isEditing = true
                },
                label = { Text("Ингредиент") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
//                    vm.recipe.description = editIngName
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    isEditing = false
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = LightText,
                    focusedLabelColor = Stroke,
                    unfocusedLabelColor = Stroke,
                    unfocusedBorderColor = Stroke,
                    focusedBorderColor = Stroke,

                    ),
                maxLines = 4,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
            )
            Spacer(modifier = Modifier.width(5.dp))

            OutlinedTextField(
                value = editIngAmount ?: "",
                onValueChange = { newText ->
                    editIngAmount = newText
                    isEditing = true
                },
                label = { Text("Кол") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
//                    vm.recipe.ingredients[i].quantity = editIngAmount
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    isEditing = false
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = LightText,
                    focusedLabelColor = Stroke,
                    unfocusedLabelColor = Stroke,
                    unfocusedBorderColor = Stroke,
                    focusedBorderColor = Stroke,
                ),
                maxLines = 3,
                textStyle = LocalTextStyle.current.copy(lineHeight = 12.sp),
                modifier = Modifier
                    .widthIn(min = 40.dp, max = 75.dp)


            )

            Spacer(modifier = Modifier.width(5.dp))

            OutlinedTextField(
                value = editIngAmount ?: "",
                onValueChange = { newText ->
                    editIngAmount = newText
                    isEditing = true
                },
                label = { Text("Ед") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    isEditing = false
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = LightText,
                    focusedLabelColor = Stroke,
                    unfocusedLabelColor = Stroke,
                    unfocusedBorderColor = Stroke,
                    focusedBorderColor = Stroke,

                    ),
                maxLines = 3,
                textStyle = LocalTextStyle.current.copy(lineHeight = 12.sp),
                modifier = Modifier
                    .widthIn(min = 40.dp, max = 75.dp)


            )

        }
    }
}

@Composable
fun CreateIngredients(vm: CreateRecipeViewModel) {
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
fun CreateStepCard(vm: CreateRecipeViewModel) {
    var imageUri by remember { mutableStateOf<Uri?>(vm.recipe.image) }
    var isEditing by remember { mutableStateOf(false) }
    var editStep by remember { mutableStateOf("vm.recipe.name") }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri = it
//                vm.onImageSelected(it)
            }
        }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Фотография шага",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        launcher.launch("image/*")
//                        imageUri = vm.recipe.image
                    }
                    .fillMaxWidth(0.9f)
                    .heightIn(max = 250.dp)
                    .padding(top = 15.dp),
                placeholder = ColorPainter(MaterialTheme.colorScheme.primary)

            )
            OutlinedTextField(
                value = editStep ?: "",
                onValueChange = { newText ->
                    editStep = newText
                    isEditing = true
                },
                label = { Text("Описание") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
//                    vm.recipe.description = editStep
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    isEditing = false
                }),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = LightAccent,
                    focusedContainerColor = LightAccent,
                    focusedTextColor = TextColor,
                    unfocusedTextColor = TextColor,
                    unfocusedPlaceholderColor = LightText,
                    focusedLabelColor = Stroke,
                    unfocusedLabelColor = Stroke,
                    unfocusedBorderColor = Stroke,
                    focusedBorderColor = Stroke,

                    ),
                maxLines = 6,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 20.dp)
            )
        }
    }
}

@Composable
fun CreateSteps(vm: CreateRecipeViewModel) {
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




