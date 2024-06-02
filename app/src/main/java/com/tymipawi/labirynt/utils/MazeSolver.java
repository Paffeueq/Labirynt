package com.tymipawi.labirynt.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class MazeSolver {

    public class Coords{
        public int x, y;
        public Coords(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coords coords = (Coords) o;
            return x == coords.x && y == coords.y;
        }

        @Override
        public String toString() {
            return '[' + String.valueOf(x) + ", " +String.valueOf(y) + ']';
        }
    }

    public char[][] getPath(char[][] maze, int startX, int startY,
                                     int endX, int endY, int sizeX, int sizeY){

        Queue<Coords> q = new LinkedList<Coords>();
        Map<Coords, Boolean> visited = new HashMap<>();
        Map<Coords, Coords> parent = new HashMap<>();
        char[][] path = new char[sizeY][sizeX];

        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                visited.put(new Coords(x, y), false);
                path[y][x] = maze[y][x];
            }
        }

        q.add(new Coords(startX, startY));
        
        Coords crt;
        Coords next;
        while(!q.isEmpty()){
            crt = q.remove();
            visited.put(crt, true);

            if(crt.x == endX && crt.y == endY){
                break;
            }

            if(crt.x + 2 < sizeX && maze[crt.y][crt.x+1] == ' '){
                next = new Coords(crt.x+2, crt.y);

                if(!visited.get(next)){
                    parent.put(next, crt);
                    q.add(next);
                }   
            }
            if(crt.x - 2 >= 0 && maze[crt.y][crt.x-1] == ' '){
                next = new Coords(crt.x-2, crt.y);

                if(!visited.get(next)){
                    parent.put(next, crt);
                    q.add(next);
                }  
            }
            if(crt.y + 2 < sizeY && maze[crt.y+1][crt.x] == ' '){
                next = new Coords(crt.x, crt.y+2);

                if(!visited.get(next)){
                    parent.put(next, crt);
                    q.add(next);
                }  
            }
            if(crt.y - 2 >= 0 && maze[crt.y-1][crt.x] == ' '){
                next = new Coords(crt.x, crt.y-2);

                if(!visited.get(next)){
                    parent.put(next, crt);
                    q.add(next);
                }  
            }

            
        }

        crt = new Coords(endX, endY);
        while(!crt.equals(new Coords(startX, startY))){
            path[crt.y][crt.x] = '^';

            crt = parent.get(crt);
        }
        path[crt.y][crt.x] = '^';


        return path;

    }
}