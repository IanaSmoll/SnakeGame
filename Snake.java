package com.javarush.games.snake;
import java.util.ArrayList;
import java.util.List;
import com.javarush.engine.cell.*;


    public class Snake {
        private List<GameObject> snakeParts = new ArrayList<>();
        public Snake(int x, int y) {
            snakeParts.add(new GameObject(x, y));
            snakeParts.add(new GameObject(x + 1, y));
            snakeParts.add(new GameObject(x + 2, y));
        }
        private static final String HEAD_SIGN = "\uD83D\uDC7E";
        private static final String BODY_SIGN = "\u26AB";
        public boolean isAlive = true;

        public void draw(Game game) {
            if(isAlive) {
                game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.GREEN, 75);
                for (int i = 1; i < snakeParts.size(); i++) {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.GREEN, 75);
                }
            }
            if(!isAlive){
                game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                for (int i = 1; i < snakeParts.size(); i++) {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }
        public GameObject createNewHead(){
            GameObject newHead = snakeParts.get(0);
            switch (direction) {
                case LEFT:
                    return new GameObject(newHead.x - 1, newHead.y);
                case DOWN:
                    return new GameObject(newHead.x, newHead.y + 1);
                case UP:
                    return new GameObject(newHead.x, newHead.y - 1);
                case RIGHT:
                    return new GameObject(newHead.x + 1, newHead.y);
            }
            return newHead;
        }
        public void removeTail(){
            snakeParts.remove(snakeParts.size() - 1);
        }
        private Direction direction = Direction.LEFT;

        public Direction getOpposite() {
            switch (direction) {
                case LEFT:
                    return Direction.RIGHT;
                case RIGHT:
                    return Direction.LEFT;
                case UP:
                    return Direction.DOWN;
                case DOWN:
                    return Direction.UP;
                default:
                    return null;
            }
        }
        public void setDirection(Direction direction) {
            if((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && (snakeParts.get(0).x == snakeParts.get(1).x)){
                return;
            } if((this.direction == Direction.UP || this.direction == Direction.DOWN) && (snakeParts.get(0).y == snakeParts.get(1).y)){
                return;
            }

            Direction opposite = getOpposite();
            if (!direction.equals(opposite)) {
                this.direction = direction;
            }
        }

        public void move(Apple apple){
            GameObject newHead = createNewHead();
            if(newHead.y >= SnakeGame.HEIGHT || newHead.x >= SnakeGame.WIDTH || newHead.y < 0 || newHead.x < 0) {
                isAlive = false;
                return;
            }
            if(checkCollision(newHead) == true){
                isAlive = false;
                return;
            }
            else{
                isAlive = true;
            }
            snakeParts.add(0, newHead);
            if((newHead.x == apple.x) && (newHead.y == apple.y)){
                apple.isAlive = false;
                return;
            }
            removeTail();
        }

        public boolean checkCollision(GameObject gameObject){
            for (int i = 0; i < snakeParts.size(); i++){
                if (snakeParts.get(i).x == gameObject.x && snakeParts.get(i).y == gameObject.y) {
                    return true;
                }
            }
            return false;
        }
        public int getLength(){
            return snakeParts.size();
        }
    }
