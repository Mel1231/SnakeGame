package com.javarush.games.snake;
import com.javarush.engine.cell.*;


public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean  isGameStopped;
    private static final int GOAL = 14;
    private int score;
    //Создаем поле
    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    //Создаем игру
    private void  createGame(){
        score = 0;
        snake = new Snake(WIDTH / 2, HEIGHT / 2);
        turnDelay = 300;
        setTurnTimer(turnDelay);
        setScore(score);
        createNewApple();
        isGameStopped = false;
        drawScene();
        //Apple apple = new Apple(5,5);
        //apple.draw(this);
    }
    //Отрисовка поля
    private void  drawScene(){
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                //После передвижения змейки нужно очищать игровое поле от уже несуществующих ее элементов
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }
    //Всё, что должно происходить в игре на протяжении одного хода, описывается здесь.
   
    public void onTurn(int step){
        snake.move(apple);
        if( apple.isAlive == false){
            createNewApple();
            score = score + 5;
            setScore(score);
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
        }
        if( snake.isAlive == false){
            gameOver();
        }
        if(snake.getLength() > GOAL){
            win();
        }
        /*if(apple.isAlive == false){
            score = score + 5;
            setScore(score);
            turnDelay = turnDelay - 10;
            setTurnTimer(turnDelay);
        }*/
        drawScene();
    }
    @Override
    public void onKeyPress(Key key){
        if(key == Key.LEFT)
            snake.setDirection(Direction.LEFT);
        if(key == Key.RIGHT)
            snake.setDirection(Direction.RIGHT);
        if(key == Key.UP)
            snake.setDirection(Direction.UP);
        if(key == Key.DOWN)
            snake.setDirection(Direction.DOWN);
        if(key == Key.SPACE && isGameStopped == true){
            createGame();
        }
        
    }
    //метод для генерации новых яблок
    private void createNewApple(){
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));
    }
    
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "GAME OVER", Color.BLUE, 75);
        
    }
     private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "YOU WIN", Color.BLUE, 75);
        
    }
    
}
