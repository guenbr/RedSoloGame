package cs3500.solored.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import cs3500.solored.model.Card;
import cs3500.solored.model.CardColor;
import cs3500.solored.model.CardNumber;
import cs3500.solored.model.SoloRedCard;

/**
 * Using mocks to test the controller because it is not depending on the
 * model right now.
 */
public class SoloRedTextControllerTest {
  Reader in;
  List<Card> deck = new ArrayList<>();

  @Before
  public void setUp() {
    List<Card> deck = new ArrayList<>();
    // R1 B2 O3 I4 V5 R6 B7
    SoloRedCard r1 = new SoloRedCard(CardColor.RED, CardNumber.ONE);
    SoloRedCard r2 = new SoloRedCard(CardColor.RED, CardNumber.TWO);
    SoloRedCard r3 = new SoloRedCard(CardColor.RED, CardNumber.THREE);
    SoloRedCard r4 = new SoloRedCard(CardColor.RED, CardNumber.FOUR);
    SoloRedCard r5 = new SoloRedCard(CardColor.RED, CardNumber.FIVE);
    SoloRedCard r6 = new SoloRedCard(CardColor.RED, CardNumber.SIX);
    SoloRedCard r7 = new SoloRedCard(CardColor.RED, CardNumber.SEVEN);
    deck.add(0, r1);
    deck.add(0, r2);
    deck.add(0, r3);
    deck.add(0, r4);
    deck.add(0, r5);
    deck.add(0, r6);
    deck.add(0, r7);
  }

  @Test
  public void testQuitGameWithMock() {
    Reader readIn = new StringReader("q");
    StringBuilder doNotCare = new StringBuilder();
    SoloRedTextController controller = new SoloRedTextController(readIn, doNotCare);

    StringBuilder log = new StringBuilder();
    MockRedGameModel mock = new MockRedGameModel(log);
    controller.playGame(mock, this.deck, false, 2, 2);
    String expectedOutput = "Game quit!\n"
            + "State of game when quit:\n"
            + "Number of cards in deck: 3\n";
    Assert.assertEquals(expectedOutput, controller.toString());
  }


}
