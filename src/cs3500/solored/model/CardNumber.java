package cs3500.solored.model;

/**
 * The purpose of this enum is to only allow the user to pick
 * specific numbers. These numbers, 1-7 are the only numbers
 * that are allowed in the game which cracks down on bugs.
 */
public enum CardNumber {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7);
  private final int num;
  CardNumber(int num) {
    this.num = num;
  }

  public int cardNumToString() {
    return num;
  }
}
