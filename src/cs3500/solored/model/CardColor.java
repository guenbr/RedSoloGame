package cs3500.solored.model;

/**
 * The purpose of this enum is so the user cannot input any other
 * color besides the colors in the game. Each color has the color
 * associated with them and also, has a number. This number describes
 * how close the color is to the color red.
 */
public enum CardColor {
  //  R, O, B, I, V;
  RED("R", 0), ORANGE("O", 1),
  BLUE("B", 2), INDIGO("I", 3),
  VIOLET("V", 4);

  private final String color;
  private final int closeToRed;
  CardColor(String color, int closeToRed) {
    this.closeToRed = closeToRed;
    this.color = color;
  }

  public int cardColorToRedNum() {
    return closeToRed;
  }

  public String cardColorToString() {
    return color;
  }
}



