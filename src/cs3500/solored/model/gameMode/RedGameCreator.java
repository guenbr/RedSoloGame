package cs3500.solored.model.gameMode;

import cs3500.solored.model.RedGameModel;
import cs3500.solored.model.SoloRedCard;
import cs3500.solored.model.SoloRedGameModel;

/**
 * The purpose of RedGameCreator is to receive the specific game type
 * from SoloRed main and return basic or advanced model depending on the
 * users input.
 */
public class RedGameCreator {

  /**
   * The purpose of this enum is to only allow the user to pick
   * basic or advanced game type. It will compare them to the user inputs.
   */
  public enum GameType {
    BASIC,
    ADVANCED
  }

  /**
   * This takes in the specific GameType from the users input. This will only be
   * called when there is a valid game type. It returns SoloRedGameModel when
   * the gameType is basic or returns AdvancedSoloRedGameModel when the gameType
   * is advanced.
   * @param gameType either basic or advanced.
   * @return the specific model depending on the gameType.
   */
  public static RedGameModel<SoloRedCard> createGame(GameType gameType) {
    switch (gameType) {
      case BASIC:
        return new SoloRedGameModel();
      case ADVANCED:
        return new AdvancedSoloRedGameModel();
      default:
        throw new IllegalArgumentException("Invalid Argument: " + gameType);
    }
  }
}
