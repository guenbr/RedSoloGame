package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.Card;
import cs3500.solored.model.RedGameModel;

/**
 * The purpose of this interface is to allow the controller to tell the model
 * what to do and when.
 */
public interface RedGameController {

  /**
   * this method should play a new game of Solo Red using the provided model,
   * using the startGame method on the model.
   * @param model is the game model of type c that extends Card.
   * @param deck is the list of cards used for the current game.
   * @param shuffle indicates whether the game should be shuffled or not.
   * @param numPalettes is how many palettes the game should have.
   * @param handSize is the number of cards the player starts with and draws until
   *                 if there are more cards in the deck.
   * @param <C> represents a card that is type Card.
   * @throws IllegalArgumentException if the provided model is null.
   * @throws IllegalStateException if the provided model is null.
   * @throws IllegalArgumentException if game cannot be started.
   */
  <C extends Card> void playGame(RedGameModel<C> model, List<C> deck,
                                 boolean shuffle, int numPalettes, int handSize);
}
