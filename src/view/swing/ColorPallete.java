/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.swing;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;


public class ColorPallete {

    private List<String> colorList;

    public ColorPallete() {
        //create object colorlist
        colorList = new ArrayList<>();
        
        colorList.add("#2e5090");
        
        colorList.add("#2774AE");
        
        colorList.add("#FEFEE3");
        
        colorList.add("#00356B");
        
        colorList.add("#D68C45");
    }

    public List<String> getColorListAsString() {
        return colorList;
    }

    public List<Color> getColorListAsColor() {
        List<Color> colorListAsColor = new ArrayList<>();
        for (String color : colorList) {
            colorListAsColor.add(Color.decode(color));
        }
        return colorListAsColor;
    }

    public Color getColor(int number) {
        if (number < 0) {
            return Color.decode(colorList.get(0));
        } else if (number > colorList.size() - 1) {
            return Color.decode(colorList.get(colorList.size() - 1));
        } else {
            return Color.decode(colorList.get(number));
        }
    }

    public Color getWhite() {
        return Color.decode("#FFFFFF");
    }

    public Color getBlack() {
        return Color.decode("#000000");
    }

}
