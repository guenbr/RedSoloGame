package cs3500.solored.model;

/**
 * The purpose of this interface is to define more helper methods
 * that are not in the Card interface in order to help create
 * SoloRedCards.
 */
public interface NewCard extends Card {
  CardNumber getNumber();

  CardColor getColor();

}
