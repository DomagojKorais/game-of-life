[![Build Status](https://travis-ci.org/ginevracoal/game-of-life.svg?branch=master)](https://travis-ci.org/ginevracoal/game-of-life)

# game-of-life
Software development methods course project

Instructions about the exam: https://moodle2.units.it/mod/page/view.php?id=103390

## Group's members
Ginevra Carbone, Domagoj Korais, Diego Baldassar

## Problem Description

This Kata is about calculating the next n generations of Conway’s **Game of Life**, given any starting position. See http://en.wikipedia.org/wiki/Conway%27s_Game_of_Life for background about the game.

You start with a two dimensional grid of cells, where each cell is either alive or dead. In this version of the problem, the grid is finite, and no life can exist off the edges. When calculating the next generation of a grid, follow these rules:

1. Any live cell with fewer than two live neighbors dies, as if caused by underpopulation.
2. Any live cell with more than three live neighbors dies, as if by overcrowding.
3. Any live cell with two or three live neighbors lives on to the next generation.
4. Any dead cell with exactly three live neighbours becomes a live cell.

You should write a program that can accept an arbitrary grid of cells and the number of generations to compute. The program will output a similar grid for each computed generation.


## Clues

The input starting position could be a text file that looks like this:

```
Generation 1:
4 8
........
....*...
...**...
........
```

And the output could look like this:

```
Generation 2:
4 8
........
...**...
...**...
........
```
