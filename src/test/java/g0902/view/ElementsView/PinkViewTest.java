package g0902.view.ElementsView;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import g0902.model.Direction;
import g0902.model.Game.MapElements.MovingElements.Ghosts.Types.Cyan;
import g0902.model.Game.MapElements.MovingElements.Ghosts.Types.Pink;
import g0902.model.Position;
import g0902.view.ElementsView.Ghosts.CyanView;
import g0902.view.ElementsView.Ghosts.PinkView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PinkViewTest {
    TextGraphics tg;
    Pink ghost;
    @BeforeEach
    void setUp(){
        tg = mock(TextGraphics.class);
        Position position=mock(Position.class);
        when(position.getCol()).thenReturn(2);
        when(position.getRow()).thenReturn(1);
        ghost= new Pink(position);
        ghost.setCurrentDirection(Direction.Right);
    }

    @Test
    void drawTestFrightenedModeOff() throws IOException {
        PinkView pinkView=new PinkView(ghost, tg);
        pinkView.draw();
        Mockito.verify(tg, Mockito.times(141)).setBackgroundColor(any());
        Mockito.verify(tg, Mockito.times(1)).fillRectangle(new TerminalPosition( 4, 3),  new TerminalSize(2, 1), ' ');
        Mockito.verify(tg, Mockito.times(1)).fillRectangle(new TerminalPosition( 20, 3),  new TerminalSize(2, 1), ' ');
        Mockito.verify(tg, Mockito.times(1)).fillRectangle(new TerminalPosition( 30, 12),  new TerminalSize(2, 1), ' ');
    }

    @Test
    void drawTestFrightenedModeOn() throws IOException {
        ghost.setFrightenedModeOn();
        PinkView pinkView=new PinkView(ghost, tg);
        pinkView.draw();
        Mockito.verify(tg, Mockito.times(1)).fillRectangle(new TerminalPosition( 3, 3),  new TerminalSize(2, 1), ' ');
        Mockito.verify(tg, Mockito.times(1)).fillRectangle(new TerminalPosition( 23, 3),  new TerminalSize(2, 1), ' ');
        Mockito.verify(tg, Mockito.times(1)).fillRectangle(new TerminalPosition( 29, 12),  new TerminalSize(2, 1), ' ');
        Mockito.verify(tg, Mockito.times(141)).setBackgroundColor(any());
        Mockito.verify(tg, Mockito.times(101)).setBackgroundColor(TextColor.Factory.fromString("#2121DE"));
    }
}
