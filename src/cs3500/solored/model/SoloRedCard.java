package cs3500.solored.model;

import java.util.Objects;

/**
 * There is no need to implement an override version of the equals method
 * because all cards within the entire deck are unique. 5 colors and 7 numbers make
 * a total of 35 cards. Meaning that each color has 7 cards numbered from 1 to 7 (inclusive).
 */
public class SoloRedCard implements NewCard {
  private final CardNumber number;
  private final CardColor color;

  /**
   * The purpose of SoloRedCard is to create new cards that
   * are within SoloRedSeven.
   * @param color color must be type CardColor and defines the cards color.
   * @param number number must be type CardNumber and defines the cards number.
   */
  public SoloRedCard(CardColor color, CardNumber number) {
    if (number == null || color == null) {
      throw new IllegalArgumentException("Number cannot be null");
    }
    this.color = color;
    this.number = number;
  }

  /**
   * This function gets the color of the card.
   * @return the color of the card of type CardColor.
   */
  public CardColor getColor() {
    return color;
  }

  /**
   * This function gets the number of the card.
   * @return the number of the card of type CardNumber.
   */
  public CardNumber getNumber() {
    return number;
  }

  @Override
  public String toString() {
    return color.cardColorToString() + number.cardNumToString();
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, number);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SoloRedCard other = (SoloRedCard) obj;
    return number == other.number && color == other.color;
  }

}
