package cs3500.solored;

import cs3500.solored.model.RedGameModel;
import cs3500.solored.model.SoloRedCard;
import cs3500.solored.model.gameMode.RedGameCreator;

/**
 * The purpose of the SoloRed final class is to create the SoloRed
 * game depending on the users desires.
 */
public final class SoloRed {

  /**
   * The purpose of the main is to get the users input and executes
   * when the user inputs a command line that is valid.
   * @param args the users command line.
   */
  public static void main(String[] args) {

    if (args == null) {
      throw new IllegalArgumentException("Game type not specified");
    }

    String commandLineGameType = args[0].toLowerCase();
    RedGameCreator.GameType gameType = null;

    switch (commandLineGameType) {
      case "basic":
        gameType = RedGameCreator.GameType.BASIC;
        break;
      case "advanced":
        gameType = RedGameCreator.GameType.ADVANCED;
        break;
      default:
        throw new IllegalArgumentException("Game type specified is not a valid game type: "
                + commandLineGameType);
    }

    int numPalettes = 4;
    int handSize = 7;

    // changes the default numPalettes if index value is greater than 4.
    if (args.length > 1) {
      try {
        if (Integer.parseInt(args[1]) > 4) {
          numPalettes = Integer.parseInt(args[1]);
        }
      } catch (Exception ignore) {
        // ignored exception
      }
    }

    // changes the default hand size if index value is greater than 7.
    if (args.length > 2) {
      try {
        if (Integer.parseInt(args[2]) > 7) {
          numPalettes = Integer.parseInt(args[2]);
        }
      } catch (Exception ignore) {
        // ignored exception
      }
    }

    RedGameModel<SoloRedCard> model = RedGameCreator.createGame(gameType);
    model.startGame(model.getAllCards(), true, numPalettes, handSize);
  }

}
