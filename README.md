

# GwentStone Lite (OOP assignment)
  **Andrei-Bogdan Marinescu - 325CA**

<div align="center"><img src="https://tenor.com/view/witcher3-gif-9340436.gif"
width="500px"></div>

## Task Description

  The program is a card game similar to Hearthstone and Gwent. Each player has
  their own set of decks with different cards
  with unique abilities, HP and Attack Damage.
  Both players also have a Hero assigned automatically by the AI,
  with a special ability.
  
## Implementation details

  The program is composed of many classes that interact with eachother to achieve the 
  wanted outcome.
  
###  GameHandler and CommandOutputGenerator

  These two classes are the main point of the program, and they
  work together to take the current action from the input and update the game
  or return the desired output.
  
  The actions that directly affect the game (e.g. Actions that kill a card/hero,
  Actions that freeze cards, ...) are directly handled by the GameHandler.
  This class checks for possible errors and does the required action.
  If an error occurred or if the action only requires an output the actions is
  redirected to the CommandOutputGenerator, alongside an error code,
  if there is any, or 0 if the action is only an output message.
  
  The CommandOutputGenerator then processes the data from the GameHandler
  and generates the required output message or error message.
  
### Card, MinionCard, Hero

  The Card class works as a template for the MinionCard and Hero classes.
  
  The MinionCard class is the main type of card used in the program.
  Each player's deck consists of such cards.
  This class implements the normal attack and the hero attack methods.
  It also contains a toJson method that takes the data
  about the card and turns it into JSON format.
  
  The hero class also works as a template for the HeroTypes.
  It implements a useAbility method that will be used by the successor classes.
  
### HeroTypes and SpecialMinions

  These two packages contain classes that extend the two described above.
  They override the useAbility method for the Hero and MinionCard respectively.
  
### Player

  This class stores the fields needed by each player
  such as their hand, deck, hero,... 
  Actions that affect the players are implemented in this class
  (e.g. drawCard, placeCard,...).
  
### Game

  This class is the center-piece of each game. It sets up both players
  with their chosen decks and heroes. It also has methods that check for errors
  to improve readability and reduce the checks made by the GameHandler.
