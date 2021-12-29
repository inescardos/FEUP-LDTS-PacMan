package view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import model.Menu.InstructionMenuModel;

import java.io.IOException;

public class ViewInstructionMenu extends ViewerMenu<InstructionMenuModel> {

    public ViewInstructionMenu(InstructionMenuModel model) {
        super(model);
    }

    public void draw() throws IOException {

        String s1 = "      _ ___ _     _ ___  _      _";
        String s2 = "||\\ ||_  | |_|| ||   | || ||\\ ||_ ";
        String s3 = "|| \\| _| | |\\ |_||_  | ||_|| \\| _|";

        String s4 = "      ___      ";
        String s5 = "     | ^ |     ";
        String s6 = "     |_|_|     ";
        String s7 = " ___  ___  ___ ";
        String s8 = "| <-|| | ||-> |";
        String s9 = "|___||_v_||___|";
        graphics.setForegroundColor(TextColor.Factory.fromString("#08ecd9"));
        graphics.putString(1, 1, "<-BACK", SGR.BLINK);
        graphics.setForegroundColor(TextColor.Factory.fromString("#ffca18"));//yellow
        graphics.putString(10, 2, s1);
        graphics.putString(10, 3, s2);
        graphics.putString(10, 4, s3);

        graphics.setForegroundColor(TextColor.Factory.fromString("#ffffff"));
        graphics.putString(22, 14, s4);
        graphics.putString(22, 15, s5);
        graphics.putString(22, 16, s6);
        graphics.putString(22, 17, s7);
        graphics.putString(22, 18, s8);
        graphics.putString(22, 19, s9);
        screen.refresh();
    }
}