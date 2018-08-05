package com.suraku.quickandroidtest.models;

public class Recipe
{
    public Recipe() {
        Title = Ingredients = Instruction = "";
    }

    private String Title;
    private String Ingredients;
    private String Instruction;

    public String getTitle() { return Title; }
    public void setTitle(String val) { Title = val; }

    public String getIngredients() { return Ingredients; }
    public void setIngredients(String val) { Ingredients = val; }

    public String getInstruction() { return Instruction; }
    public void setInstruction(String val) { Instruction = val; }
}
