package cs3500.solored.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The purpose of this class is to if the card construction works
 * as intended.
 */
public class CreateCardsTest {
  SoloRedCard card;
  SoloRedCard failCard;

  @Before
  public void setUp() {
    card = new SoloRedCard(CardColor.BLUE, CardNumber.TWO);
  }

  @Test
  public void NuLLTest() {
    Assert.assertThrows("Number Null",
            IllegalArgumentException.class, () -> failCard = new SoloRedCard(CardColor.BLUE,
                    null));
    Assert.assertThrows("Color Null",
            IllegalArgumentException.class, () -> failCard = new SoloRedCard(null,
                    CardNumber.TWO));
  }

  @Test
  public void GetNumberTest() {
    Assert.assertEquals(CardNumber.TWO, card.getNumber());
  }

  @Test
  public void GetColorTest() {
    Assert.assertEquals(CardColor.BLUE, card.getColor());
  }

  @Test
  public void ToStringTest() {
    Assert.assertEquals("B2", card.toString());
  }

  @Test
  public void ChangeCanvasColorTest() {
    SoloRedCard canvas;
    SoloRedCard testCard = new SoloRedCard(CardColor.BLUE, CardNumber.ONE);
    canvas = new SoloRedCard(testCard.getColor(), testCard.getNumber());
    Assert.assertEquals(CardColor.BLUE, canvas.getColor());
  }

  @Test
  public void ColorNumberTest() {
    Assert.assertEquals(2, card.getColor().cardColorToRedNum());
  }
}
