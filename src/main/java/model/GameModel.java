package model;

import model.Elements.*;
import model.Elements.Coins.Coin;
import model.Elements.Coins.PowerCoin;
import model.Elements.Coins.SmallCoin;
import model.Elements.Ghosts.Ghost;
import model.Maps.Builders.MapBuilder;
import model.Maps.Builders.MapReader;
import model.Maps.Map;

import java.io.IOException;
import java.util.ArrayList;

public class GameModel implements Model{
    private Map map;
    private ArrayList<Coin> toRemove;
    boolean wasSmallCoin;
    int score;
    Ghost red, pink, cyan, orange;
    Pacman pacman;
    boolean isRunning;
    boolean lost;

    public GameModel() throws IOException {
        MapBuilder mapBuilder = new MapReader();
        map = mapBuilder.createMap("map");
        red = map.getRed();
        pink = map.getPink();
        cyan = map.getCyan();
        orange = map.getOrange();
        pacman = map.getPacman();
        isRunning=true;
        lost=false;
    }

    public Map getMap() {
        return map;
    }

    boolean collideWithWalls(Collider collider) {
        for (Wall wall : map.getWalls()) {
            if (wall.getCollider().colision(collider))
                return true;
        }
        return false;
    }

    public void update(long deltatime) {
        pacmanMoving(deltatime);
        coinCollisions(deltatime);
        ghostsPacmanCollisions(deltatime);

        map.getRed().update(deltatime);
        map.getOrange().update(deltatime);
        map.getPink().update(deltatime);
        map.getCyan().update(deltatime);

        // ver aqui se colide, diminuir vidas, e acabar se ele ficar com 0
   }

   private void pacmanMoving(long deltatime){
       Position oldPosition = new Position(pacman.getPosition().getRow(), pacman.getPosition().getCol());

       Position next = pacman.moveNextDirection(deltatime);
       if (collideWithWalls(new Collider(next, pacman.getCollider().getWidth(), pacman.getCollider().getHeight()))) {
           next = pacman.moveCurrentDirection(deltatime);

           if (collideWithWalls(new Collider(next, pacman.getCollider().getWidth(), pacman.getCollider().getHeight()))) {
               pacman.setPosition(oldPosition);
               pacman.setDirection(Direction.None);
           } else {
               pacman.setPosition(next);
           }
       } else {
           if (pacman.getNextDirection() != pacman.getCurrentDirection()) {
               int row = (int) Math.round(next.getRow() / 8.0);
               int col = (int) Math.round(next.getCol() / 12.0);

               switch (pacman.getNextDirection()) {
                   case Up:
                   case Down:
                       next.setCol(col * 12 + 1);
                       break;

                   case Left:
                   case Right:
                       next.setRow(row * 8 + 1);
                       break;
               }
           }
           pacman.setPosition(next);
           pacman.nextDirection();
       }
   }

   private void coinCollisions(long deltatime){
        Pacman pacman = map.getPacman();
       wasSmallCoin = false;
       // Acho que assim é preferivel do que o o for nas 140 coins
       for(int i= -1; i <= 1; i++) {
           for (int j = -1; j <= 1; j++) {
               Position p = new Position((pacman.getPosition().getRow()) / 8 + i, (pacman.getPosition().getCol()) /12 + j);
               SmallCoin smallCoin = map.getSmallCoins().get(p);
               if (smallCoin != null) {
                   if (smallCoin.getCollider().colision(pacman.getCollider())) {
                       map.getSmallCoins().remove(p);
                       pacman.increaseScore(SmallCoin.SmallCoinValue);
                       wasSmallCoin = true;
                       break;
                   }
               }
           }
       }
       if (!wasSmallCoin){
           toRemove = new ArrayList<>();
           for (PowerCoin powerCoin : map.getPowerCoins()) {
               if (powerCoin.getCollider().colision(pacman.getCollider())) {
                   toRemove.add(powerCoin);
                   pacman.increaseScore(PowerCoin.PowerCoinValue);
                   red.setFrightenedModeOn();
                   map.setRed(red);
                   pink.setFrightenedModeOn();
                   map.setPink(pink);
                   cyan.setFrightenedModeOn();
                   map.setCyan(cyan);
                   orange.setFrightenedModeOn();
                   map.setOrange(orange);
                   break;
               }
           }
           map.getPowerCoins().removeAll(toRemove);
       }
   }

   private void ghostsPacmanCollisions(long deltatime){
        if(pacman.getCollider().colision(red.getCollider())){
            if(red.getFrightenedModeOn()){
                pacman.increaseScore(red.getScore());
                red.updateScore();
                red.setPosition(red.getBeginPosition());
                red.setFrightenedModeOff();
            }
            else{
                isRunning=false;
                lost=true;
            }
        }
        else if(pacman.getCollider().colision(cyan.getCollider())){
            if(cyan.getFrightenedModeOn()) {
                pacman.increaseScore(cyan.getScore());
                cyan.updateScore();
                cyan.setPosition(cyan.getBeginPosition());
                cyan.setFrightenedModeOff();
            }
            else{
                isRunning=false;
                lost=true;
            }
        }
        else if(pacman.getCollider().colision(pink.getCollider())){
            if(pink.getFrightenedModeOn()) {
                pacman.increaseScore(pink.getScore());
                pink.updateScore();
                pink.setPosition(pink.getBeginPosition());
                pink.setFrightenedModeOff();
            }
            else{
                isRunning=false;
                lost=true;
            }
        }
        else if(pacman.getCollider().colision(orange.getCollider())){
            if(orange.getFrightenedModeOn()) {
                pacman.increaseScore(orange.getScore());
                orange.updateScore();
                orange.setPosition(orange.getBeginPosition());
                orange.setFrightenedModeOff();
            }
            else{
                isRunning=false;
                lost=true;
            }
        }
   }

    public boolean isRunning(){         // TO CHANGE LATER
        return isRunning;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public boolean hasLost() {
        return lost;
    }
}
