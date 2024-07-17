package com.example.gourmet.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gourmet.navigation.Router.Companion.RecipeList
import com.example.gourmet.screens.CreateRecipeScreen
import com.example.gourmet.screens.EditRecipeScreen
import com.example.gourmet.screens.RecipeScreen
import com.example.gourmet.screens.RecipeListScreen
import com.example.gourmet.vmodels.CreateRecipeViewModel
import com.example.gourmet.vmodels.RecipeViewModel

@Composable
fun NavGraph(
    navHostController: NavHostController,
) {
    NavHost(navController = navHostController,
        startDestination = RecipeList){
        composable(RecipeList){
            RecipeListScreen(navHostController)
        }
        composable(Router.Recipe+ "/{recipeId}"){
            val vm: RecipeViewModel = viewModel(key = Router.EditRecipe) { RecipeViewModel(it.arguments?.getString("recipeId") ?: "")}
            RecipeScreen(navHostController)
        }
        composable(Router.EditRecipe + "/{recipeId}"){
            val vm: RecipeViewModel = viewModel(key = Router.EditRecipe) { RecipeViewModel(it.arguments?.getString("recipeId") ?: "")}
            EditRecipeScreen()
        }
        composable(Router.CreateRecipe) {
            val vm: CreateRecipeViewModel = viewModel(key = Router.CreateRecipe) { CreateRecipeViewModel()}
            CreateRecipeScreen(vm, navHostController)
        }
    }
}