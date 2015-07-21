# DungeonWiki
Use Wikipedia to generate an interactive Dungeon game room. Links to other pages are doors to other rooms.

## About the game
The game is a basic rendition of a dungeon crawler that uses Wikipedia pages to generate levels. 
 - (Almost) Every link on the page is a door to another room (another Wikipedia page)
 - headers are teleports (jumping around the level, not yet implemented)
 - img tags are monsters
 - a line with the word key in it will give bonus points (not yet implemented)
 - divs form walls/blocks, and there is some random chance that an uncategorized line will also create a wall.


It is worth noting that the html is split on a hackish regex, so a line in the code's eyes is not necessarily a line in html. Also, there can be some tuning involved (with the regex and probabilistic wall generation) but for now it works well in that you can move around to a few doors from any given spawn (at least in practice).
