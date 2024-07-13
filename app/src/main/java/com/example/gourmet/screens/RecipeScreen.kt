package com.example.gourmet.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gourmet.R
import com.example.gourmet.ui.theme.Background
import com.example.gourmet.ui.theme.DarkAccent
import com.example.gourmet.ui.theme.LightAccent
import com.example.gourmet.ui.theme.LightText
import com.example.gourmet.ui.theme.Stroke
import com.example.gourmet.ui.theme.TextColor

@Composable
fun RecipeScreen() {
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
                IconButton(onClick = { /*TODO*/ }) {
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
                EditMainInfo()
                EditIngredients()
                EditSteps()
            }
        }
    }
}

@Composable
fun MainInfo() {
    Text(
        text = "Основное",
        modifier = Modifier
            .padding(10.dp),
        fontSize = 16.sp,
        color = TextColor
    )
    Box(
        modifier = Modifier
//            .height(150.dp)
            .fillMaxWidth()
//        .border(0.5.dp, Stroke)
            .background(LightAccent)
//            .shadow(1.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
//                alignment = Alignment.Center,
                painter = painterResource(R.drawable.food),
                contentDescription = "Фотография блюда",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .heightIn(max = 200.dp)
                    .padding(top = 15.dp),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "Маринованные огурцы",
                color = TextColor,
                fontSize = 18.sp,
                lineHeight = 16.sp,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth(0.9f),
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(0.dp, 15.dp),
                color = Stroke,
                thickness = 0.5.dp,

                )
            Text(
                text = "Выглядит вкусно, по сути вкусно, по вкусу тоже вкусно, Выглядит вкусно, по " +
                        "сути вкусно, по вкусу тоже вкусно, \n\nВыглядит вкусно, по сути вкусно, по " +
                        "вкусу тоже вкусно, Выглядит вкусно",
                color = LightText,
                fontSize = 16.sp,
                lineHeight = 16.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(0.9f),
            )
        }
    }
}

@Composable
fun Ingredient() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth(0.9f)
    ) {
        Text(
            text = "Маринованные огу р ц ы",
            color = TextColor,
            fontSize = 18.sp,
            lineHeight = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.weight(0.4f))

        Text(
            text = "30",
            color = TextColor,
            fontSize = 18.sp,
            lineHeight = 18.sp
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
fun Ingredients() {
    Text(
        text = "Ингредиенты",
        modifier = Modifier
            .padding(10.dp),
        fontSize = 16.sp,
        color = TextColor
    )

    Box(
        modifier = Modifier
//            .height(150.dp)
            .fillMaxWidth()
//        .border(0.5.dp, Stroke)
            .background(LightAccent)
//            .shadow(1.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            repeat(10) {
                EditIngredient()

            }
        }

    }
}


@Composable
fun StepCard() {
    Box(
        modifier = Modifier
//            .height(150.dp)
            .fillMaxWidth()
            .padding(bottom = 5.dp)
//        .border(0.5.dp, Stroke)
            .background(LightAccent)
//            .shadow(1.dp)

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
//                alignment = Alignment.Center,
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
fun Steps() {
    Text(
        text = "Приготовление",
        modifier = Modifier
            .padding(10.dp),
        fontSize = 16.sp,
        color = TextColor
    )
    repeat(10) {
        EditStepCard()
    }
}

