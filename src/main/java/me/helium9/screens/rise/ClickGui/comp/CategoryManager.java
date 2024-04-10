package me.helium9.screens.rise.ClickGui.comp;

import java.util.ArrayList;

import me.helium9.screens.rise.ClickGui.ClickGui;

public class CategoryManager {
	
	public static int currentPage = 0;
	public static boolean openDragScreen = false;
	
	public static void thisPage(int number) {
		currentPage=number;
		ArrayList<ClickGuiCategoryButton> category = ClickGui.getClickGuiCategoryButton();
		
		for(int i = 0; i< category.size(); i++) {
			if(i != currentPage) {
				category.get(i).setIsOnThisPage(false);
			}
		}
	}
}
