package com.javarush.games.snake;
import java.util.*;
import com.javarush.engine.cell.*;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;
    
    //Создаем змейку
    public  Snake(int x, int y){
         GameObject first = new GameObject(x,y);
         GameObject second = new GameObject(x + 1, y);
         GameObject third = new GameObject(x + 2, y);
          
         snakeParts.add(first);
         snakeParts.add(second);
         snakeParts.add(third);
    }
    
    private List<GameObject> snakeParts = new ArrayList<>();
    //Отрисовка змейки
    public void draw(Game game){
            for(int i = 0; i < snakeParts.size(); i++){
                    if (i == 0){
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
                         }
                     else {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, isAlive ? Color.BLACK : Color.RED, 75);
                         }
                }
            } 
        

    //Выбираем сторону движения
    public void setDirection(Direction direcnew) {
      if ( ((direction==Direction.RIGHT) || (direction==Direction.LEFT)) && (snakeParts.get(0).x == snakeParts.get(1).x)  ) {
			return;
        	}

	if ( (direction==Direction.DOWN || direction==Direction.UP) && (snakeParts.get(0).y == snakeParts.get(1).y) ) {
	
			return;
	}
	this.direction = direcnew;
	
       /* if(direction == Direction.UP && this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) {
             return;
            }
            if(direction == Direction.DOWN && this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) {
             return;
            }
            if(direction == Direction.RIGHT && this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) {
             return;
            }
            if(direction == Direction.LEFT && this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) {
             return;
            }*/
    }
     
     public void move(Apple apple) {
        //Проверка на выход за пределы
        GameObject newHead = createNewHead();
        if (checkCollision(newHead))
            isAlive = false;
        else { 
            if (newHead.x < 0 || newHead.x >= SnakeGame.WIDTH
                  || newHead.y < 0 || newHead.y >= SnakeGame.HEIGHT || newHead == apple) {
                isAlive = false;
            } 
             else if(newHead.x == apple.x && newHead.y == apple.y){
                    apple.isAlive = false;
                    snakeParts.add(0, newHead);
                }
                else {
                    snakeParts.add(0, newHead);
                    removeTail();
                }
            }
        }
    
           
       //Создание новой головы
    public GameObject createNewHead(){
           GameObject gameObject = null;
            if(direction == Direction.UP)
                gameObject =new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
            if(direction == Direction.DOWN)
                gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
            if(direction == Direction.RIGHT)
                gameObject = new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
            if(direction == Direction.LEFT)
                gameObject = new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y );
            return gameObject;
            }
       //Удаление последнего элемента
    public void removeTail(){
        snakeParts.remove(snakeParts.get(snakeParts.size() - 1));
    }
    //Проверка совпадает ли новая голова с другиими частями
    public boolean checkCollision(GameObject gameObject){
        for (GameObject obj : snakeParts) {
            if (gameObject.x == obj.x && gameObject.y == obj.y) {
                return true;
            }
        }
        return false;
        
    }
    
    public int getLength(){
        return snakeParts.size();
    }
}