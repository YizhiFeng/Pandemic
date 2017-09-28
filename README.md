<h1>Pandemic</h1>
The goal of this project is to recreate the board game Pandemic using Java. 
<h2>Definition of Done</h2>
We will know that we are done with this project when the following conditions are met.
<h3>Player Actions</h3>

<ul>
     
    <li>&#10004; Drive/Ferry Travel</li> 
        <ul>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L86">
            <li>Player cannot move to the same city they are currently in.</li> </a>
            <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L71">
			<li>Player can move to adjacent city.</li> </a>
            <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L98">
			<li>Player cannot move to to a city if it is two cities away from the current city.</li></a>
        </ul>
		<br/>
    <li>&#10004; Direct Flight</li>
        <ul>
            <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L112">
			<li>Player is able to move to city of discarded card.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L135">
            <li>Player is not able to move to city that is not adjacent when you do not have that card.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L111">
            <li>Card is discarded if the city the player is moving to is two (or more) cities away from the current city.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L156">
            <li>Card is not discarded when the city the player is moving to is adjacent to their current city.</li></a>
        </ul>
		<br/>
 
    <li>&#10004; Charter Flight</li>
        <ul>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L199">
            <li>Player cannot move to the city they are currently in.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L176">
			<li>A card should be discarded if they are moving to a nonadjacent city.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L238">
            <li>A card should not be discarded if they are adjacent to that city.</li></a>
        </ul>
		<br/>
		
    <li>&#10004; Shuttle Flight</li>
        <ul>
            <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L262">
			<li>The player can move from a city with a research station to another city with a research station.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L289">
			<li>The player cannot move to the city they are currently in.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L301">
            <li>The player cannot move to a city with no research station.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L276">
            <li>The player is not able to move to a research station when they are not currently at a research statement</li></a>
        </ul>
		<br/>
    
    <li>&#10004; Build Research Station on Current City</li>
        <ul>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L314">
            <li>The player can build a research station when they discard a card of the city they are currently at.</li></a> 
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L344">
            <li>The player cannot build a research station without the card of the city they are currently at.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L357">
            <li>The player cannot build a research station if one already exists on that city.</li></a>
        </ul>
		<br/>
     
    <li>&#10004; Cure your current city, removing one disease cube from it.</li>
        <ul>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L370">
            <li>The player can cure and remove a disease cube if there is one disease cube at the current city.</li></a>
            <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/CityTest.java#L136">
			<li>The player cannot cure a city if there is no disease on the city.</li></a>
			<a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L385">
            <li>The player can remove a disease cube if there is more than one disease cube at the current city. The city would not be cured because there are still disease cubes leftover.</li></a>
        </ul>
		<br/>
    
    <li>&#10004; Pass a card matching your current city to another player in the same city.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L401">
            <li>A player can pass a city card to a player if the card matches the city they are both currently in.</li></a>
           <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L421">
            <li>A player cannot pass a non-city card to a player in the same city as them.</li></a>
           <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L441">
            <li>A player cannot pass a non-city card to a player that is not in the same city as them.</li></a>
           <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L441">
            <li>A player cannot pass a city card to a player that is not in the same city as them</li></a>
           <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L460">
            <li>A player cannot pass a city card to a player that is in the same city as them if that does not match their current city.</li></a>
        </ul>
        <br/>
    
    <li>&#10004; Discard five cards of the same color at a research station to cure that color disease.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L494">
            <li>The player cannot cure that color disease if they do not have enough cards of the same color. Test both if they have only four matching or zero matching cards.</li></a>
          <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L353">
            <li>The player cannot cure an entire disease if they are not at a research station.</li></a>
           <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L627">
            <li>The player can cure the disease if they have five cards of that disease color while at a research station.</li></a>
           <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L732">
            <li>The player cannot cure the disease if the disease is already entirely cured.</li></a>
        </ul>
        <br/>
         
    <li>&#10004; At the end of turn draw two cards.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L421">
            <li>If there are more than 2 cards left, the player draws top two cards.</li></a>
          <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L447">
            <li>If there is only one card left, the game ends and the players lose.</li></a>
        </ul>
        <br/>
         
    <li>&#10004; If a player has more than seven cards, they must discard or play enough cards until they only have seven cards.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/main/java/pandemic_gui_control/GameControl.java#L373">
            <li>If a player has seven cards, nothing happens</li></a>
         <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/main/java/pandemic_gui_control/GameControl.java#L373">
            <li>If a player has eight cards, they must choose one card to discard.</li></a>
        </ul>
        <br/>
    
    <li>&#10004; An event card is played.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameEventCardTest.java#L21">
            <li>If the card is played on your turn, no action is used.</li></a>
         <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/main/java/pandemic_gui_control/GameControl.java#L528">
            <li>If the card is played on another person's turn, no action is used.</li></a>
        </ul>
</ul>
<br/>

<h3>Special Player Actions</h3>
<ul>
    <li>&#10004; Scientist</li>
        <ul>
            <li>Needs only four cards of the same color to discover a cure.</li>
                <ul>
                <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L474">
                    <li>If the player has three cards of the same color, nothing happens.</li></a>
                 <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L540">
                    <li>If the player has four cards of the same color, they discover the cure for the disease of that color.</li></a>
                </ul>
        </ul>
        <br/>
    <li>&#10004; Medic</li>
        <ul>
            <li>Can remove all cubes of a single color when treating a city.</li>
                <ul>
                <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameSpecialPlayerActionTest.java#L78">
                    <li>If there is only one disease cube, the medic removes that disease cube.</li></a>
                <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameSpecialPlayerActionTest.java#L78">
                    <li>If the disease is not cured, the medic can still remove all disease cubes.</li></a>
                  <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameSpecialPlayerActionTest.java#L100">
                    <li>If there is more than one disease cube of the same color, the medic removes all of those disease cubes.</li></a>
                </ul>
            <li>Cubes of a cured disease are automatically removed from a city when they medic enters that city.</li>
                <ul>
                <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameSpecialPlayerActionTest.java#L120">
                    <li>Remove cubes of a disease that has been cured when the medic enters the city.</li></a>
                 <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameSpecialPlayerActionTest.java#L78">
                    <li>If the cure has not been found, the medic must use an action to remove cubes.</li></a>
                </ul>
        </ul>
        <br/>
    <li>&#10004; Dispatcher</li>
        <ul>
            <li>Can move any player, if they agree, to another city with another player as an action.</li>
                <ul>
               <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameSpecialPlayerActionTest.java#L22">
                    <li>Move the other player if the other city has another player and the other player agrees to move.</li></a>
               <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameSpecialPlayerActionTest.java#L44">
                    <li>Do not move the other player if the other player agrees, but the city does not have another player.</li></a>
        </ul>
        </ul>
        <br/>
    <li>&#10004; Opertion Expert</li>
        <ul>
            <li>May build a research station for one action in current city.</li>
                <ul>
                <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L871">
                    <li>Build a research station if the research conditions are met and the player does not have the card.</li></a>
                  <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L847">
                    <li>Build a research station if the research conditions are met and the player does have the card, but the card will stay in the player's hand.</li></a>
                  <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L357">
                    <li>Do not build a research station if the research conditions have not been met.</li></a>
                </ul>
        </ul>
        <br/>
        
    <li>&#10004; Researcher</li>
        <ul>
            <li>Can give a player, in the same city, cards in their hand for one action per card.(This can occur on either player's turn)</li>
                <ul>
                <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L893">
                    <li>If the other player is in the current city, pass one card for one action.</li></a>
                <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/PlayerTest.java#L912">
                    <li>Cannot pass a card if the other player is not in the current city.</li></a>
                </ul>
        </ul>
</ul>
<br/>
<h3>Board Actions</h3>
<ul>
    <li>&#10004; Change turns when the current player has used up all four of their actions or they choose to end their turn.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/main/java/pandemic_gui_control/GameControl.java#L330">
            <li>If the player has used all four actions, change turns to next player.</li></a>
         <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/main/java/pandemic_gui_control/GameControl.java#L233">
            <li>If the player has used three actions and does not want to continue, change turns to next player.</li></a>
         <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameTest.java#L614">
            <li>If the player has three actions and does want to continue, do not change turns.</li></a>
        </ul>
        <br>
        
    <li>&#10004; The city gains another disease cube if there is an infection card is drawn.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L502">
            <li>If an infection card is drawn and there are no cubes on the city, add one disease cube.</li></a>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L535">
            <li>If an infection card is drawn and there is one cube on the city, add one more disease cube.</li></a>
        
        </ul>
        <br>
        
    <li>&#10004;If a fourth cub is to be added to a city, an outbreak occurs and each adjacent city gains an addition cube of that disease color.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L151">
            <li>If an adjacent city has no cubes, add a cube of the disease color.</li></a>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L181">
            <li>If an adjacent city has three cubes, a cube of the disease color must be placed on all the adjacent cities of that city.</li></a>
        </ul>
        <br>
        
    <li>&#10004;If an epidemic occurs, the player draws a card from the bottom of the infection deck, three cubes are placed in the drawn city, the infection card is placed in the infection discard pile, the infection discard pile is shuffled, the contents of the infection discard pile is moved to the top of the infection deck, and increment the infection rate.</li>
        <ul>
        <a herf="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L627">
            <li>Test when there are zero cubes on the city, so no outbreak occurs.</li></a>
        <a herf="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L721">
            <li>Test when there one or more cubes on the city, an outbreak will occur on that city.</li></a>
        </ul>
        <br>
        
    <li>&#10004;At the end of a player's turn, they draw the number of cards from the infection deck, that is equal to the infection rate, and place cubes on the cities drawn.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L720">
            <li>If the infection rate is greater than zero, the player draws the number of cards equal to the infection rate.</li></a>
        </ul>
        <br>
        
    <li>&#10004;Place a disease cube on a city.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/CityTest.java#L79">
            <li>If there are zero cubes on the city, add one cube.</li></a>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L151">
            <li>If there is three cubes on the city, an outbreak occurs and you do not add a fourth cube.</li></a>
        </ul>
        <br>
        
    <li>&#10004;If a disease has been eliminated, do not add that disease cube of that color to the board.</li>
        <ul>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L607">
            <li>If a card for that city is drawn and the disease has been eradicated, do not add a disease cube to that city.</li></a>
        <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L502">
            <li>If a card for that city is drawn and the disease has not been eradicated, add a disease cube to that city.</li></a>
        </ul>
        <br>
        
</ul>
<h3>Win and Lose Conditions</h3>

<ul>
<li>&#10004; The players lose when more than seven outbreaks occur.</li>
    <ul>
    <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L283">
        <li>The game continues if zero outbreaks have occurred.</li></a>
    <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L297">    
        <li>The game ends and the players fail when 7 outbreaks have occurred.</li></a>
    </ul>
    <br>
    
    <li>&#10004; The players lose if there are no more cubes of the specific disease color when they are needed during an Infection or Epidemic.</li>
    <ul>
    <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L502">
        <li>The game continues if there are many cubes of the specific disease color left during an Infection or Epidemic.</li></a>
    <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L535">
        <li>The game continues if there is only one cube of the specific disease color left during an Infection or Epidemic.</li></a>
    <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L181">
        <li>The game ends and the players fail when there are no cubes of the specific disease color during an Infection or Epidemic.</li></a>
    </ul>
    <br>
    
<li>&#10004; The players lose if there are no more Player cards to be drawn.</li>
    <ul>
    <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L447">
        <li>The game ends and the players fail if there are no Player cards left.</li></a>
    </ul>
    <br>
    
<li> &#10004; The players win if they discover the cure to all four diseases.</li>
    <ul>
    <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L483">
        <li>The game ends and the players win if all four cures have been discovered.</li></a>
     <a href="https://ada.csse.rose-hulman.edu/keinslc/SQA_Code_Junkies_201730/blob/master/src/test/java/pandemic_test/GameActionTest.java#L326">
        <li>The game continues if three cures have been discovered.</li></a>
    </ul>
</ul>
