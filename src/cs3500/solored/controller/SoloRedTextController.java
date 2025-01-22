package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.solored.model.Card;
import cs3500.solored.model.RedGameModel;
import cs3500.solored.view.RedGameView;
import cs3500.solored.view.SoloRedGameTextView;

/**
 * The purpose of this class is to implement all the RedGameController methods
 * and given the user input it tells the model what to execute.
 */
public class SoloRedTextController implements RedGameController {
  private final Appendable out;
  private final Scanner scanner;
  private RedGameView view;
  private boolean quit;

  /**
   * This constructs the SoloRedTextController and initializes the value.
   *
   * @param rd readable user input.
   * @param ap is the output of text.
   * @throws IllegalArgumentException if and only if either of its arguments are null.
   */
  public SoloRedTextController(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("rd/ap cannot be null");
    }
    this.out = ap;
    scanner = new Scanner(rd);
  }

  private <C extends Card> void quitGameHelper(RedGameModel<C> model) throws IOException {
    this.quit = true;
    try {
      out.append("Game quit!\n");
      out.append("State of game when quit:\n");
      view.render();
      out.append("\n");
      out.append("Number of cards in deck: ")
              .append(String.valueOf(model.getHand().size())).append("\n");
    } catch (IOException ex) {
      throw new IllegalStateException("Cannot render", ex);
    }
  }

  private <C extends Card> void isGameWonGameOverHelper(
          RedGameModel<C> model, Boolean gameWon, Boolean gameOver) throws IOException {
    gameWon = model.isGameWon();
    gameOver = model.isGameOver();
    if (gameWon) {
      out.append("Game won.\n");
      view.render();
      out.append("\nNumber of cards in deck: ")
              .append(String.valueOf(model.getHand().size())).append("\n");
    } else if (gameOver) {
      out.append("Game lost.\n");
      view.render();
      out.append("\nNumber of cards in deck: ")
              .append(String.valueOf(model.getHand().size())).append("\n");
    }
  }

  private <C extends Card> int loopValidInput(String[] breakUpLine, int index,
                                              RedGameModel<C> model) throws IOException {
    int parsedIndex = -1;
    boolean validInput = false;
    while (!validInput) {
      if (!scanner.hasNext()) {
        throw new IllegalStateException("Readable object failed.");
      }
      if ("q".equals(breakUpLine[index])) {
        quitGameHelper(model);
        break;
      }

      try {
        parsedIndex = Integer.parseInt(breakUpLine[index]) - 1;
        if (parsedIndex >= 0) {
          validInput = true;
        } else {
          try {
            breakUpLine[index] = scanner.nextLine().toLowerCase();
          } catch (Exception e) {
            throw new IllegalStateException("Readable failed", e);
          }
        }
      } catch (Exception ex) {
        try {
          breakUpLine[index] = scanner.nextLine().toLowerCase();
        } catch (Exception e) {
          throw new IllegalStateException("Readable failed", e);
        }
      }
    }

    return parsedIndex;
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck,
                                        boolean shuffle, int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.quit = false;
    try {
      try {
        model.startGame(deck, shuffle, numPalettes, handSize);
      } catch (IllegalArgumentException | IllegalStateException e) {
        throw new IllegalArgumentException("Game cannot be started", e);
      }
      this.view = new SoloRedGameTextView(model, out);
      while (!model.isGameOver() && !quit) {
        out.append("\n");
        view.render();
        out.append("Number of cards in deck: ")
                .append(String.valueOf(model.getHand().size())).append("\n");

        String userLine;
        try {
          userLine = scanner.nextLine().toLowerCase();
        } catch (Exception ex) {
          throw new IllegalStateException("Readable failed", ex);
        }

        while (userLine.isEmpty()) {
          try {
            userLine = scanner.nextLine().toLowerCase();
          } catch (Exception ex) {
            throw new IllegalStateException("Readable failed", ex);
          }
        }

        String[] breakUpLine = userLine.split("\\s+");

        for (String index : breakUpLine) {
          if ("q".equals(index)) {
            quitGameHelper(model);
            return;
          }
        }

        if ("canvas".equals(breakUpLine[0])) {
          int cardHandIndex = loopValidInput(breakUpLine, 1, model);
          try {
            model.playToCanvas(cardHandIndex);
          } catch (IllegalArgumentException | IllegalStateException e) {
            try {
              out.append("Invalid move. Try again. ").append(e.getMessage()).append("\n");
            } catch (IOException ex) {
              throw new IllegalStateException("Cannot append");
            }
          }
        }

        if ("palette".equals(breakUpLine[0])) {
          int paletteIndex = loopValidInput(breakUpLine, 1, model);
          int cardHandIndex = loopValidInput(breakUpLine, 2, model);
          try {
            model.playToPalette(paletteIndex, cardHandIndex);
            if (!model.isGameOver() && !model.isGameWon() && model.numOfCardsInDeck() != 0) {
              model.drawForHand();
            }
          } catch (IllegalArgumentException | IllegalStateException e) {
            try {
              out.append("Invalid move. Try again. ").append(e.getMessage()).append("\n");
            } catch (IOException ex) {
              throw new IllegalStateException("Cannot append");
            }
          }
        }

        if (!"palette".equals(breakUpLine[0]) && !"canvas".equals(breakUpLine[0])
                && !"q".equals(breakUpLine[0])) {
          out.append("Invalid command. Try again. ").append(userLine).append("\n");
        }
      }

      isGameWonGameOverHelper(model, model.isGameWon(), model.isGameOver());

    } catch (IOException ex) {
      throw new IllegalStateException("Append failed", ex);
    }
  }

}
