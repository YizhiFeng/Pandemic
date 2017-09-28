<h1>Pandemic</h1>
The goal of this project is to recreate the board game Pandemic using Java. 
<h2>Definition of Done</h2>
We will know that we are done with this project when the following conditions are met.
<h3>Player Actions</h3>

<ul>
<li>&#10004; Drive/Ferry Travel</li> 
<ul>
			
<li>Player cannot move to the same city they are currently in.</li>
<li>Player can move to adjacent city.</li>
<li>Player cannot move to to a city if it is two cities away from the current city.</li>
</ul>
<br/>
<li>&#10004; Direct Flight</li>
<ul>
<li>Player is able to move to city of discarded card.</li>
<li>Player is not able to move to city that is not adjacent when you do not have that card.</li>
<li>Card is discarded if the city the player is moving to is two (or more) cities away from the current city.</li>
<li>Card is not discarded when the city the player is moving to is adjacent to their current city.</li>
</ul>
<br/>

<li>&#10004; Charter Flight</li>
<ul>
<li>Player cannot move to the city they are currently in.</li>
<li>A card should be discarded if they are moving to a nonadjacent city.</li>
<li>A card should not be discarded if they are adjacent to that city.</li>
</ul>
<br/>

<li>&#10004; Shuttle Flight</li>
<ul>
<li>The player can move from a city with a research station to another city with a research station.</li>
<li>The player cannot move to the city they are currently in.</li>
<li>The player cannot move to a city with no research station.</li>
<li>The player is not able to move to a research station when they are not currently at a research statement</li>
</ul>
<br/>

<li>&#10004; Build Research Station on Current City</li>
<ul>
<li>The player can build a research station when they discard a card of the city they are currently at.</li>
<li>The player cannot build a research station without the card of the city they are currently at.</li>
<li>The player cannot build a research station if one already exists on that city.</li>
</ul>
<br/>

<li>&#10004; Cure your current city, removing one disease cube from it.</li>
<ul>
<li>The player can cure and remove a disease cube if there is one disease cube at the current city.</li>
<li>The player cannot cure a city if there is no disease on the city.</li>
<li>The player can remove a disease cube if there is more than one disease cube at the current city. The city would not be cured because there are still disease cubes leftover.</li>
</ul>
<br/>

<li>&#10004; Pass a card matching your current city to another player in the same city.</li>
<ul>
<li>A player can pass a city card to a player if the card matches the city they are both currently in.</li>
<li>A player cannot pass a non-city card to a player in the same city as them.</li>
<li>A player cannot pass a non-city card to a player that is not in the same city as them.</li>
<li>A player cannot pass a city card to a player that is not in the same city as them</li>
<li>A player cannot pass a city card to a player that is in the same city as them if that does not match their current city.</li>
</ul>
<br/>

<li>&#10004; Discard five cards of the same color at a research station to cure that color disease.</li>
<ul>
<li>The player cannot cure that color disease if they do not have enough cards of the same color. Test both if they have only four matching or zero matching cards.</li>
<li>The player cannot cure an entire disease if they are not at a research station.</li>
<li>The player can cure the disease if they have five cards of that disease color while at a research station.</li>
<li>The player cannot cure the disease if the disease is already entirely cured.</li>
</ul>
<br/>

<li>&#10004; At the end of turn draw two cards.</li>
<ul>
<li>If there are more than 2 cards left, the player draws top two cards.</li>
<li>If there is only one card left, the game ends and the players lose.</li>
</ul>
<br/>

<li>&#10004; If a player has more than seven cards, they must discard or play enough cards until they only have seven cards.</li>
<ul>
<li>If a player has seven cards, nothing happens</li>
<li>If a player has eight cards, they must choose one card to discard.</li>
</ul>
<br/>

<li>&#10004; An event card is played.</li>
<ul>
<li>If the card is played on your turn, no action is used.</li>
<li>If the card is played on another person's turn, no action is used.</li>
</ul>
</ul>
<br/>

<h3>Special Player Actions</h3>
<ul>
<li>&#10004; Scientist</li>
<ul>
<li>Needs only four cards of the same color to discover a cure.</li>
<ul>
<li>If the player has three cards of the same color, nothing happens.</li>
<li>If the player has four cards of the same color, they discover the cure for the disease of that color.</li>
</ul>
</ul>
<br/>
<li>&#10004; Medic</li>
<ul>
<li>Can remove all cubes of a single color when treating a city.</li>
<ul>
<li>If there is only one disease cube, the medic removes that disease cube.</li>
<li>If the disease is not cured, the medic can still remove all disease cubes.</li>
<li>If there is more than one disease cube of the same color, the medic removes all of those disease cubes.</li>
</ul>
<li>Cubes of a cured disease are automatically removed from a city when they medic enters that city.</li>
<ul>
<li>Remove cubes of a disease that has been cured when the medic enters the city.</li>
<li>If the cure has not been found, the medic must use an action to remove cubes.</li>
</ul>
</ul>
<br/>
<li>&#10004; Dispatcher</li>
<ul>
<li>Can move any player, if they agree, to another city with another player as an action.</li>
<ul>
<li>Move the other player if the other city has another player and the other player agrees to move.</li>
<li>Do not move the other player if the other player agrees, but the city does not have another player.</li>
</ul>
</ul>
<br/>
<li>&#10004; Opertion Expert</li>
<ul>
<li>May build a research station for one action in current city.</li>
<ul>
<li>Build a research station if the research conditions are met and the player does not have the card.</li>
<li>Build a research station if the research conditions are met and the player does have the card, but the card will stay in the player's hand.</li>
<li>Do not build a research station if the research conditions have not been met.</li>
</ul>
</ul>
<br/>

<li>&#10004; Researcher</li>
<ul>
<li>Can give a player, in the same city, cards in their hand for one action per card.(This can occur on either player's turn)</li>
<ul>
<li>If the other player is in the current city, pass one card for one action.</li>
<li>Cannot pass a card if the other player is not in the current city.</li>
</ul>
</ul>
</ul>
<br/>
<h3>Board Actions</h3>
<ul>
<li>&#10004; Change turns when the current player has used up all four of their actions or they choose to end their turn.</li>
<ul>
<li>If the player has used all four actions, change turns to next player.</li>
<li>If the player has used three actions and does not want to continue, change turns to next player.</li>
<li>If the player has three actions and does want to continue, do not change turns.</li>
</ul>
<br>

<li>&#10004; The city gains another disease cube if there is an infection card is drawn.</li>
<ul>
<li>If an infection card is drawn and there are no cubes on the city, add one disease cube.</li>
<li>If an infection card is drawn and there is one cube on the city, add one more disease cube.</li>

</ul>
<br>

<li>&#10004;If a fourth cub is to be added to a city, an outbreak occurs and each adjacent city gains an addition cube of that disease color.</li>
<ul>
<li>If an adjacent city has no cubes, add a cube of the disease color.</li>
<li>If an adjacent city has three cubes, a cube of the disease color must be placed on all the adjacent cities of that city.</li>
</ul>
<br>

<li>&#10004;If an epidemic occurs, the player draws a card from the bottom of the infection deck, three cubes are placed in the drawn city, the infection card is placed in the infection discard pile, the infection discard pile is shuffled, the contents of the infection discard pile is moved to the top of the infection deck, and increment the infection rate.</li>
<ul>
<li>Test when there are zero cubes on the city, so no outbreak occurs.</li>
<li>Test when there one or more cubes on the city, an outbreak will occur on that city.</li>
</ul>
<br>

<li>&#10004;At the end of a player's turn, they draw the number of cards from the infection deck, that is equal to the infection rate, and place cubes on the cities drawn.</li>
<ul>
<li>If the infection rate is greater than zero, the player draws the number of cards equal to the infection rate.</li>
</ul>
<br>

<li>&#10004;Place a disease cube on a city.</li>
<ul>
<li>If there are zero cubes on the city, add one cube.</li>
<li>If there is three cubes on the city, an outbreak occurs and you do not add a fourth cube.</li>
</ul>
<br>

<li>&#10004;If a disease has been eliminated, do not add that disease cube of that color to the board.</li>
<ul>
<li>If a card for that city is drawn and the disease has been eradicated, do not add a disease cube to that city.</li>
<li>If a card for that city is drawn and the disease has not been eradicated, add a disease cube to that city.</li>
</ul>
<br>

</ul>
<h3>Win and Lose Conditions</h3>

<ul>
<li>&#10004; The players lose when more than seven outbreaks occur.</li>
<ul>
<li>The game continues if zero outbreaks have occurred.</li>
<li>The game ends and the players fail when 7 outbreaks have occurred.</li>
</ul>
<br>

<li>&#10004; The players lose if there are no more cubes of the specific disease color when they are needed during an Infection or Epidemic.</li>
<ul>
<li>The game continues if there are many cubes of the specific disease color left during an Infection or Epidemic.</li>
<li>The game continues if there is only one cube of the specific disease color left during an Infection or Epidemic.</li>
<li>The game ends and the players fail when there are no cubes of the specific disease color during an Infection or Epidemic.</li>
</ul>
<br>

<li>&#10004; The players lose if there are no more Player cards to be drawn.</li>
<ul>
<li>The game ends and the players fail if there are no Player cards left.</li>
</ul>
<br>

<li> &#10004; The players win if they discover the cure to all four diseases.</li>
<ul>
<li>The game ends and the players win if all four cures have been discovered.</li>
<li>The game continues if three cures have been discovered.</li>
</ul>
</ul>
