package cs3500.solored;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import cs3500.solored.model.CardColor;
import cs3500.solored.model.CardNumber;
import cs3500.solored.model.SoloRedCard;
import cs3500.solored.model.SoloRedGameModel;
import cs3500.solored.model.gameMode.AdvancedSoloRedGameModel;

/**
 * This tests the RedGameModel implementation from all the methods
 * from the interface RedGameModel.
 */
public class AdvancedSoloRedGameTest {
  private AdvancedSoloRedGameModel model;
  private List<SoloRedCard> customDeck;
  private List<SoloRedCard> smallDeck;

  @Before
  public void setUp() {
    model = new AdvancedSoloRedGameModel();
    customDeck = new ArrayList<>();
    smallDeck = new ArrayList<>();
    smallDeck.add(new SoloRedCard(CardColor.INDIGO, CardNumber.ONE));
    smallDeck.add(new SoloRedCard(CardColor.BLUE, CardNumber.ONE));
    smallDeck.add(new SoloRedCard(CardColor.INDIGO, CardNumber.THREE));
    // R1 B2 O3 I4 V5 R6 B7
    customDeck.add(new SoloRedCard(CardColor.RED, CardNumber.ONE));
    customDeck.add(new SoloRedCard(CardColor.BLUE, CardNumber.TWO));
    customDeck.add(new SoloRedCard(CardColor.ORANGE, CardNumber.THREE));
    customDeck.add(new SoloRedCard(CardColor.INDIGO, CardNumber.FOUR));
    customDeck.add(new SoloRedCard(CardColor.VIOLET, CardNumber.FIVE));
    customDeck.add(new SoloRedCard(CardColor.RED, CardNumber.SIX));
    customDeck.add(new SoloRedCard(CardColor.BLUE, CardNumber.SEVEN));
  }

  @Test
  public void testPlayToPaletteValid() {
    model.startGame(customDeck, false, 2, 3);
    int winningPaletteIndex = model.winningPaletteIndex();
    int paletteToPlay = winningPaletteIndex - 1;
    model.playToPalette(paletteToPlay, 0);
    if (!model.isGameOver()) {
      Assert.assertEquals(2, model.getHand().size());
    }
  }

  @Test
  public void testGameNotStartedPlayToPalette() {
    Assert.assertThrows("Cannot play to palette when game has not started",
            IllegalStateException.class, () -> model.playToPalette(0, 1));
  }

  @Test
  public void testPlayToPaletteToPaletteIndexNegAndMore() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertThrows("Palette < 0", IllegalArgumentException.class, () ->
            model.playToPalette(-1, 2));
    Assert.assertThrows("PaletteIndex > numPalettes",
            IllegalArgumentException.class, () ->
                    model.playToPalette(2, 100));
  }

  @Test
  public void testPlayToPaletteInvalidHandIndex() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertThrows("Invalid hand index",
            IllegalArgumentException.class, () ->
                    model.playToPalette(0, -1));
    Assert.assertThrows("Invalid hand index, equal to cards in hand",
            IllegalArgumentException.class, () ->
                    model.playToPalette(0, model.getHand().size()));
  }

  @Test
  public void testPlayToPaletteWinningPalette() {
    model.startGame(customDeck, false, 2, 3);
    int winningPaletteIndex = model.winningPaletteIndex();
    Assert.assertThrows("Playing to winning palette",
            IllegalStateException.class, () ->
                    model.playToPalette(winningPaletteIndex, 0));
  }

  @Test
  public void testPlayToCanvasGameNotStartedGameOver() {
    Assert.assertThrows("Cannot play to canvas when game has not started",
            IllegalStateException.class, () -> model.playToCanvas(0));
    model.startGame(smallDeck, false, 2, 1);
    model.playToPalette(0, 0);
    Assert.assertTrue(model.isGameOver());
    Assert.assertThrows("Cannot play to canvas when game is over",
            IllegalStateException.class, () -> model.playToCanvas(0));
  }

  @Test
  public void testPlayToCanvasInvalidCardIndex() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertThrows("CardIndx < 0",
            IllegalArgumentException.class, () -> model.playToCanvas(-1));
    Assert.assertThrows("CardIndx = handSize",
            IllegalArgumentException.class, () -> model.playToCanvas(model.getHand().size()));
  }

  @Test
  public void testPlayToCanvasAlreadyPlayed() {
    model.startGame(customDeck, false, 2, 3);
    model.playToCanvas(0);
    Assert.assertThrows("Already played to canvas in this turn",
            IllegalStateException.class, () ->
                    model.playToCanvas(1));
  }

  @Test
  public void testPlayToCanvasLastCard() {
    model.startGame(customDeck, false, 2, 1); // Hand size 1
    Assert.assertThrows("Cannot put last card on canvas",
            IllegalStateException.class, () ->
                    model.playToCanvas(0));
  }

  @Test
  public void testDrawForHandValidButOnlyOneCard() {
    model.startGame(customDeck, false, 2, 3);
    model.playToPalette(0, 1);
    model.drawForHand();
    Assert.assertEquals(3, model.getHand().size());
  }

  @Test
  public void testDrawTwoCards() {
    List<SoloRedCard> deck = new ArrayList<>();
    deck.add(0, new SoloRedCard(CardColor.RED, CardNumber.ONE));
    deck.add(1, new SoloRedCard(CardColor.RED, CardNumber.TWO));
    deck.add(2, new SoloRedCard(CardColor.RED, CardNumber.SEVEN));
    deck.add(3, new SoloRedCard(CardColor.RED, CardNumber.THREE));
    deck.add(4, new SoloRedCard(CardColor.RED, CardNumber.FOUR));
    deck.add(5, new SoloRedCard(CardColor.ORANGE, CardNumber.THREE));
    deck.add(6, new SoloRedCard(CardColor.RED, CardNumber.FIVE));
    deck.add(7, new SoloRedCard(CardColor.RED, CardNumber.SIX));
    model.startGame(deck, false, 2, 3);
    model.playToCanvas(2);
    model.playToPalette(0, 1);
    model.drawForHand();
    Assert.assertEquals(3, model.getHand().size());
  }


  @Test
  public void testDrawForHandGameNotStarted() {
    Assert.assertThrows("Game has not started",
            IllegalStateException.class, () ->
                    model.drawForHand());
  }

  @Test
  public void testStartGameValid() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertFalse(model.isGameOver());
    Assert.assertEquals(2, model.numPalettes());
    Assert.assertEquals(3, model.getHand().size());
  }

  @Test
  public void testStartGameGameHasAlreadyStarted() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertThrows("Game has already started",
            IllegalStateException.class, () ->
                    model.startGame(customDeck, false, 2, 3));
  }

  @Test
  public void testStartGameGameIsOver() {
    model.startGame(smallDeck, false, 2, 1);
    model.playToPalette(0, 0);
    Assert.assertTrue(model.isGameOver());
    Assert.assertThrows("Game is over",
            IllegalStateException.class, () ->
                    model.startGame(smallDeck, false, 2, 1));
  }


  @Test
  public void testStartGameInvalidNumPalettesHandSize() {
    Assert.assertThrows("Number of palettes less than 2",
            IllegalArgumentException.class, () ->
                    model.startGame(customDeck, false, 0, 3));
    Assert.assertThrows("HandSize <= 0", IllegalArgumentException.class, () ->
            model.startGame(customDeck, false, 2, 0));
  }

  @Test
  public void testStartGameDeckTooSmall() {
    Assert.assertThrows("Deck too small",
            IllegalArgumentException.class, () ->
                    model.startGame(customDeck, false, 100, 100));
  }

  @Test
  public void testStartGameDeckHasNonUnique() {
    customDeck.add(new SoloRedCard(CardColor.RED, CardNumber.ONE)); // Already in deck.
    Assert.assertThrows("Deck has non-unique", IllegalArgumentException.class, () ->
            model.startGame(customDeck, false, 2, 2));
  }

  @Test
  public void testNumOfCardsInDeckValid() {
    model.startGame(customDeck, false, 2, 3);
    int expectedDeckSize = customDeck.size() - (2 + 3); // Subtract palettes and hand
    Assert.assertEquals(expectedDeckSize, model.numOfCardsInDeck());
  }

  @Test
  public void testNumOfCardsInDeckGameNotStarted() {
    Assert.assertThrows("Game has not started",
            IllegalStateException.class, () ->
                    model.numOfCardsInDeck());
  }

  @Test
  public void testNumPalettesValid() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertEquals(2, model.numPalettes());
  }

  @Test
  public void testNumPalettesGameNotStarted() {
    Assert.assertThrows("Game has not started",
            IllegalStateException.class, () ->
                    model.numPalettes());
  }

  @Test
  public void testWinningPaletteIndexValid() {
    model.startGame(customDeck, false, 2, 3);
    int winningPaletteIndex = model.winningPaletteIndex();
    Assert.assertTrue(winningPaletteIndex >= 0
            && winningPaletteIndex < model.numPalettes());
  }

  @Test
  public void testWinningPaletteIndexGameNotStarted() {
    Assert.assertThrows("Game has not started",
            IllegalStateException.class, () ->
                    model.winningPaletteIndex());
  }

  @Test
  public void testIsGameOverValid() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertFalse(model.isGameOver());
  }

  @Test
  public void testIsGameOverGameNotStarted() {
    Assert.assertThrows("Game has not started",
            IllegalStateException.class, () ->
                    model.isGameOver());
  }

  @Test
  public void testGetHandValid() {
    model.startGame(customDeck, false, 2, 3);
    List<SoloRedCard> hand = model.getHand();
    Assert.assertEquals(3, hand.size());
    Assert.assertEquals(customDeck.get(2), hand.get(0));
  }

  @Test
  public void testGetHandGameNotStart() {
    Assert.assertThrows("Game has not started",
            IllegalStateException.class, () -> model.getHand());
  }

  @Test
  public void testGetPaletteValid() {
    model.startGame(customDeck, false, 2, 3);
    List<SoloRedCard> palette = model.getPalette(0);
    Assert.assertEquals(1, palette.size());
    Assert.assertEquals(customDeck.get(0), palette.get(0));
  }

  @Test
  public void testGetPaletteGameNotStarted() {
    Assert.assertThrows("game has not started",
            IllegalStateException.class, () -> model.getPalette(0));
  }

  @Test
  public void testGetPaletteInvalidPaletteIdx() {
    model.startGame(customDeck, false, 2, 3);
    Assert.assertThrows("PaletteIdx < 0", IllegalArgumentException.class, () ->
            model.getPalette(-1));
    Assert.assertThrows("PaletteIdx > numPal", IllegalArgumentException.class, () ->
            model.getPalette(4));
  }

  @Test
  public void testGetCanvasValid() {
    model.startGame(customDeck, false, 2, 3);
    SoloRedCard canvasCard = model.getCanvas();
    Assert.assertEquals(CardColor.RED, canvasCard.getColor());
  }

  @Test
  public void testGetCanvasGameNotStarted() {
    Assert.assertThrows("Game has not started", IllegalStateException.class, () ->
            model.getCanvas());
  }

  @Test
  public void testGetAllCards() {
    List<SoloRedCard> allCards = model.getAllCards();
    Assert.assertEquals(35, allCards.size());
  }

  @Test
  public void testRandom() {
    SoloRedGameModel rand1 = new SoloRedGameModel(new Random(10));
    SoloRedGameModel rand2 = new SoloRedGameModel(new Random(10));
    rand1.startGame(customDeck, true, 2, 3);
    rand2.startGame(customDeck, true, 2, 3);
    for (int i = 0; i < 2; i++) {
      Assert.assertEquals(rand1.getPalette(i), rand2.getPalette(i));
    }
    Assert.assertEquals(rand1.getHand(), rand2.getHand());
  }

  @Test
  public void testGetHandDoesNotChangeState() {
    model.startGame(customDeck, false, 2, 3);
    model.getHand().clear();
    Assert.assertEquals(3, model.getHand().size());
  }

}
