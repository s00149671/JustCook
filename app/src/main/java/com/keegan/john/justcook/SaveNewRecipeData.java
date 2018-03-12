package com.keegan.john.justcook;

import java.util.ArrayList;

/**
 * Created by emily on 12/03/2018.
 */

public class SaveNewRecipeData {

    String name, description;
    ArrayList<String> ingredients_list = new ArrayList<String>();
    Boolean Vegan;
    Boolean Vegetarian;
    Boolean Pescetarian;
    Boolean Celiac;
    Boolean NutFree;
    Boolean DairyFree;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getIngredients_list() {
        return ingredients_list;
    }

    public void setIngredients_list(ArrayList<String> ingredients_list) {
        this.ingredients_list = ingredients_list;
    }

    public Boolean getVegan() {
        return Vegan;
    }

    public void setVegan(Boolean vegan) {
        Vegan = vegan;
    }

    public Boolean getVegetarian() {
        return Vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        Vegetarian = vegetarian;
    }

    public Boolean getPescetarian() {
        return Pescetarian;
    }

    public void setPescetarian(Boolean pescetarian) {
        Pescetarian = pescetarian;
    }

    public Boolean getCeliac() {
        return Celiac;
    }

    public void setCeliac(Boolean celiac) {
        Celiac = celiac;
    }

    public Boolean getNutFree() {
        return NutFree;
    }

    public void setNutFree(Boolean nutFree) {
        NutFree = nutFree;
    }

    public Boolean getDairyFree() {
        return DairyFree;
    }

    public void setDairyFree(Boolean dairyFree) {
        DairyFree = dairyFree;
    }

    public void SaveNewRecipeData(String Name, String Description,ArrayList<String> Ingredient_List,
                                  Boolean vegetarian, Boolean vegan, Boolean celiac, Boolean pesscetarian,
                                  Boolean nutfree, Boolean dairyfree){


        Name = name;
        Description = description;
        vegetarian = Vegetarian;
        vegan = Vegan;
        celiac = Celiac;
        pesscetarian = Pescetarian;
        nutfree = NutFree;
        dairyfree = DairyFree;
        ingredients_list = Ingredient_List;
    }


}
