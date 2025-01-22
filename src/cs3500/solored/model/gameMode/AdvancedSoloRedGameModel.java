package cs3500.solored.model.gameMode;

import cs3500.solored.model.RedGameModel;
import cs3500.solored.model.SoloRedCard;
import cs3500.solored.model.SoloRedGameModel;

/**
 * The purpose of the AdvancedSoloRedGameModel is to implement all the methods
 * in the interface RedGameModel by extending to SoloRedGameModel. This class is
 * responsible for the game's logic and alters the game state. The only thing that
 * differs is the drawForHand under the new rule if the user chooses ADVANCED.
 */
public class AdvancedSoloRedGameModel extends SoloRedGameModel
        implements RedGameModel<SoloRedCard> {

  /**
   * This constructor initializes game into a state that’s ready
   * for someone to call one of the startGame methods and begin
   * playing as the same as SoloRedGameModel.
   */
  public AdvancedSoloRedGameModel() {
    super();
  }

  @Override
  public void drawForHand() {
    gameNotStarted();
    gameIsOver();
    if (alreadyPlayedCanvas && deck.size() > 1
            && getCanvas().getNumber().cardNumToString()
            > getPalette(winningPaletteIndex()).size()) {
      SoloRedCard currentCard = deck.remove(0);
      hand.add(currentCard);
      currentCard = deck.remove(0);
      hand.add(currentCard);
    } else if (!deck.isEmpty()) {
      SoloRedCard currentCard = deck.remove(0);
      hand.add(currentCard);
    }
    alreadyPlayedCanvas = false;
  }
}
