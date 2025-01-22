package cs3500.solored.controller;

import java.util.List;

import cs3500.solored.model.Card;
import cs3500.solored.model.RedGameModel;

/**
 * This is a mock implementation of the RedGameModel. This
 * allows SoloRedTextController to use mock models in order to test
 * specifically the controller.
 */
public class MockRedGameModel implements RedGameModel<Card> {

  /**
   * This is a mock constructor and does not matter.
   * @param log this takes in the log of the view.
   */
  public MockRedGameModel(StringBuilder log) {
    // does not matter
  }


  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    // mock does not matter.
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    // mock does not matter.
  }

  @Override
  public void drawForHand() {
    // mock does not matter.
  }

  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPalettes, int handSize) {
    // mock does not matter.
  }

  @Override
  public int numOfCardsInDeck() {
    return 0;
  }

  @Override
  public int numPalettes() {
    return 0;
  }

  @Override
  public int winningPaletteIndex() {
    return 0;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean isGameWon() {
    return false;
  }

  @Override
  public List<Card> getHand() {
    return null;
  }

  @Override
  public List<Card> getPalette(int paletteNum) {
    return null;
  }

  @Override
  public Card getCanvas() {
    return null;
  }

  @Override
  public List<Card> getAllCards() {
    return null;
  }
}
