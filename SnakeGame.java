package com.javarush.games.snake;
import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int HEIGHT = 15;
    public static final int WIDTH = 15;
    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(HEIGHT,WIDTH);
        createGame();
    }

    private void drawScene() {
        for(int x = 0; x < WIDTH; x++) {
            for(int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.BLANCHEDALMOND, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private void createGame() {
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        createNewApple();
        isGameStopped = false;

        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);

        score = 0;
        setScore(score);
    }

    @Override
    public void onTurn(int step){
        snake.move(apple);
        if(!apple.isAlive) {
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }

        if(!snake.isAlive){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch(key) {
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case UP:
                snake.setDirection(Direction.UP);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
        }
        if(key == Key.SPACE && isGameStopped == true){
            createGame();
        }
    }

    private void createNewApple(){
        Apple newApple;
        do {
            newApple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        }
        while(snake.checkCollision(newApple));
        apple = newApple;
    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "GAME OVER", Color.DARKRED, 75);
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN,"YOU WIN", Color.DARKRED, 75);
    }
}
