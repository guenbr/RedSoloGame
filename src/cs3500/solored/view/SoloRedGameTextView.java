package cs3500.solored.view;

import java.io.IOException;
import java.util.List;

import cs3500.solored.model.RedGameModel;

/**
 * The purpose of this class is to showcase the current state
 * of the game. It follows the structure on the course website.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<?> model;
  private Appendable appendable;

  /**
   * This constructs a game view given the model and appendable for output.
   * @param model Any model type that must be at least RedGameModel.
   * @param appendable to have sequence of letters.
   */
  public SoloRedGameTextView(RedGameModel<?> model,
                             Appendable appendable) throws IllegalArgumentException {
    if (model == null || appendable == null) {
      throw new IllegalArgumentException("model/appendable cannot be null");
    }
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * Original constructor that creates just the model.
   * @param model any model type that must be at least RedGameModel.
   */
  public SoloRedGameTextView(RedGameModel<?> model) {
    this.model = model;
  }

  @Override
  public void render() throws IOException {
    if (appendable == null || model == null) {
      throw new IOException("Rendering failed, appendable or model is null");
    } else {
      try {
        appendable.append(this.toString());
      } catch (IOException e) {
        throw new IOException("Failed to render game state", e);
      }
    }
  }

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append("Canvas: ").append(model.getCanvas().toString().charAt(0)).append("\n");

    for (int i = 0; i < model.numPalettes(); i++) {
      if (i == model.winningPaletteIndex()) {
        output.append("> ");
      }
      output.append("P").append(i + 1).append(": ");

      List<?> currentPalette = model.getPalette(i);
      for (int j = 0; j < currentPalette.size(); j++) {
        output.append(currentPalette.get(j).toString());
        if (j != currentPalette.size() - 1) {
          output.append(" ");
        }
      }
      output.append("\n");
    }

    output.append("Hand: ");
    List<?> hand = model.getHand();
    for (int i = 0; i < hand.size(); i++) {
      output.append(hand.get(i).toString());
      if (i != hand.size() - 1) {
        output.append(" ");
      }
    }
    return output.toString();
  }
}