![Title](https://github.com/S010MON/game-of-life-2/blob/master/src/main/resources/screenshots/screenshot_02.png)
# Game Of Life v2.0
An interactive version of the zero player game that follows the rules of the original game devised by the late John Conway

1. Any live cell with two or three live neighbours survives.
2. Any dead cell with three live neighbours becomes a live cell.
3. All other live cells die in the next generation. Similarly, all other dead cells stay dead.

More information available ![here](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)

## Features
### Placement
Place copies of pre-defined templates wherever you like.
![templates](https://github.com/S010MON/game-of-life-2/blob/master/src/main/resources/screenshots/screenshot_04.png)

### Dark and Light mode
![dark](https://github.com/S010MON/game-of-life-2/blob/master/src/main/resources/screenshots/darkmode.png)
![light](https://github.com/S010MON/game-of-life-2/blob/master/src/main/resources/screenshots/lightmode.png)


## Installation
### Requirements
Ensure you have the following installed before running:
1. git
2. java v17.0.1
3. gradle v7.3.1

### Instructions
Clone the GitHub repo
```bash
git clone https://github.com/S010MON/game-of-life-2
cd game-of-life-2
```
Run the game using gradle
```bash
gradle build
gradle run
```