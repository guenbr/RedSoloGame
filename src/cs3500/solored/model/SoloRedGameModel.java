package cs3500.solored.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * The purpose of the SoloRedGameModel is to implement all the methods
 * in the interface RedGameModel. This class is responsible for the game's logic
 * and alters the game state.
 */
public class SoloRedGameModel implements RedGameModel<SoloRedCard> {
  protected List<SoloRedCard> deck;
  private boolean shuffle;
  private int numPalettes;
  protected List<SoloRedCard> hand;
  protected int handSize;
  private boolean isGameStarted;
  private boolean gameOver;
  private List<List<SoloRedCard>> palettes;
  private SoloRedCard canvas;
  protected boolean alreadyPlayedCanvas;
  boolean gameWon;
  private Random random = new Random();

  /**
   * This constructor initializes game into a state that’s ready
   * for someone to call one of the startGame methods and begin playing.
   */
  public SoloRedGameModel() {
    if (isGameStarted) {
      throw new IllegalStateException("Game has already started");
    }
    this.deck = new ArrayList<>();
    this.shuffle = false;
    this.numPalettes = 2;
    this.hand = new ArrayList<>();
    this.palettes = new ArrayList<>();
    this.handSize = 1;
    this.isGameStarted = false;
    this.gameOver = false;
    this.canvas = new SoloRedCard(CardColor.RED, CardNumber.ONE);
    this.gameWon = false;
    this.alreadyPlayedCanvas = false;
  }

  /**
   * This constructor helps perform shuffling.
   *
   * @param random is to help the randomness of shuffling the deck.
   */
  public SoloRedGameModel(Random random) {
    if (random == null) {
      throw new IllegalArgumentException("Random cannot be null");
    }
    this.random = random;
  }

  // Helper method that throws an exception if game has not started yet.
  protected void gameNotStarted() {
    if (!isGameStarted) {
      throw new IllegalStateException("Game has not started, testing helper");
    }
  }

  // Helper method that throws exception if game is over.
  protected void gameIsOver() {
    if (isGameOver()) {
      throw new IllegalStateException("Game is over");
    }
  }

  /*
   Helper method that throws exception if palette index is less than zero.
   And if palette index is more than or equal to total palettes.
   */
  private void palIdxLessZeroOrMoreThanTotal(int paletteIdx) {
    if (paletteIdx < 0 || paletteIdx >= numPalettes()) {
      throw new IllegalArgumentException("paletteIdx is invalid");
    }
  }

  /*
   Helper method that throws exception if card index is less than zero.
   And card index is more than or equal to hand size.
   */
  private void cardIdxLessZeroOrGECardInHand(int cardIdxInHand) {
    if (cardIdxInHand < 0 || cardIdxInHand >= hand.size()) {
      throw new IllegalArgumentException("cardIdxInHand is invalid");
    }
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    gameNotStarted();
    gameIsOver();
    palIdxLessZeroOrMoreThanTotal(paletteIdx);
    cardIdxLessZeroOrGECardInHand(cardIdxInHand);
    if (paletteIdx == winningPaletteIndex()) {
      throw new IllegalStateException("Cannot play to winning palette");
    }
    alreadyPlayedCanvas = false;
    // Adds card to palette while removing it from current hand.
    SoloRedCard currentCard = hand.remove(cardIdxInHand);
    palettes.get(paletteIdx).add(currentCard);
    // resets played to canvas state since played to palette.

    if (paletteIdx != winningPaletteIndex()) {
      gameOver = true;
      gameWon = false;
    }

    if (hand.isEmpty() && deck.isEmpty()) {
      gameOver = true;
      gameWon = true;
    }
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    gameNotStarted();
    gameIsOver();
    cardIdxLessZeroOrGECardInHand(cardIdxInHand);
    if (alreadyPlayedCanvas) {
      throw new IllegalStateException("Method already called once in a given turn");
    }
    if (hand.size() == 1) {
      throw new IllegalStateException("Cannot put last card on canvas");
    }
    alreadyPlayedCanvas = true;
    SoloRedCard currentCard = hand.remove(cardIdxInHand);
    canvas = new SoloRedCard(currentCard.getColor(), currentCard.getNumber());
  }

  @Override
  public void drawForHand() {
    gameNotStarted();
    gameIsOver();
    alreadyPlayedCanvas = false;
    while (hand.size() < handSize && !deck.isEmpty()) {
      SoloRedCard currentCard = deck.remove(0);
      hand.add(currentCard);
    }
  }

  @Override
  public void startGame(List<SoloRedCard> deck, boolean shuffle, int numPalettes, int handSize) {
    if (isGameStarted) {
      throw new IllegalStateException("Game has already started");
    }
    if (numPalettes < 2) {
      throw new IllegalArgumentException("numPalettes must be 2 or more");
    }
    if (handSize <= 0) {
      throw new IllegalArgumentException("handSize cannot be negative");
    }
    if (deck == null || deck.size() < numPalettes + handSize) {
      throw new IllegalArgumentException("Deck size is not large enough");
    }
    Set<SoloRedCard> cardSet = new HashSet<>(deck);
    if (deck.size() != cardSet.size()) {
      throw new IllegalArgumentException("Deck has non-unique or null card(s)");
    }
    if (deck.contains(null)) {
      throw new IllegalArgumentException("Deck has non-unique or null card(s)");
    }

    this.deck = new ArrayList<>(deck);
    this.shuffle = shuffle;
    this.numPalettes = numPalettes;
    this.handSize = handSize;
    this.palettes = new ArrayList<>();

    if (shuffle) {
      Collections.shuffle(this.deck, random);
    }
    for (int i = 0; i < numPalettes; i++) {
      List<SoloRedCard> palette = new ArrayList<>();
      SoloRedCard card = this.deck.remove(0);
      palette.add(card);
      this.palettes.add(palette);
    }
    this.canvas = new SoloRedCard(CardColor.RED, CardNumber.ONE);
    this.hand = new ArrayList<>();
    for (int i = 0; i < handSize; i++) {
      SoloRedCard card = this.deck.remove(0);
      this.hand.add(card);
    }

    this.isGameStarted = true;
    this.gameOver = false;
    this.gameWon = false;
    this.alreadyPlayedCanvas = false;
  }

  private boolean isGameStarted() {
    return isGameStarted;
  }

  @Override
  public int numOfCardsInDeck() {
    gameNotStarted();
    return deck.size();
  }

  @Override
  public int numPalettes() {
    gameNotStarted();
    return palettes.size();
  }

  @Override
  public int winningPaletteIndex() {
    gameNotStarted();
    switch (canvas.getColor()) {
      case RED:
        return redRule(palettes);
      case ORANGE:
        return orangeRule(palettes);
      case BLUE:
        return blueRule(palettes);
      case INDIGO:
        return indigoRule(palettes);
      case VIOLET:
        return violetRule(palettes);
      default:
        throw new IllegalCallerException("Did not get color");
    }
  }

  private int redRule(List<List<SoloRedCard>> palettes) {
    if (palettes.contains(null)) {
      throw new IllegalArgumentException("palettes cannot have null");
    }
    int highestValue = -1;
    int indexOfWinningPalette = -1;
    SoloRedCard winningCard = null;
    for (int currentPalette = 0; currentPalette < palettes.size(); currentPalette++) {
      List<SoloRedCard> palette = palettes.get(currentPalette);
      for (SoloRedCard card : palette) {
        int currentCardNumber = card.getNumber().cardNumToString();
        if (currentCardNumber > highestValue) {
          highestValue = currentCardNumber;
          winningCard = card;
          indexOfWinningPalette = currentPalette;
        } else if (currentCardNumber == highestValue) {
          int currentCardColorValue = card.getColor().cardColorToRedNum();
          int winningCardColorValue = winningCard.getColor().cardColorToRedNum();
          if (currentCardColorValue < winningCardColorValue) {
            winningCard = card;
            indexOfWinningPalette = currentPalette;
          }
        }
      }
    }

    return indexOfWinningPalette;
  }

  private int orangeRule(List<List<SoloRedCard>> palettes) {
    int mostSingleNumber = 0;
    int winningPaletteIndex = -1;
    List<Integer> tiedPalettes = new ArrayList<>();
    for (int currentPalette = 0; currentPalette < palettes.size(); currentPalette++) {
      List<SoloRedCard> palette = palettes.get(currentPalette);
      if (!palette.isEmpty()) {
        int highestFrequency = findMostFrequentNumber(palette);
        if (highestFrequency > mostSingleNumber) {
          mostSingleNumber = highestFrequency;
          winningPaletteIndex = currentPalette;
          tiedPalettes.clear();
          tiedPalettes.add(currentPalette);
        } else if (highestFrequency == mostSingleNumber) {
          tiedPalettes.add(currentPalette);
        }
      }
    }
    if (tiedPalettes.size() > 1) {
      winningPaletteIndex = tieBreak(tiedPalettes);
    }

    return winningPaletteIndex;
  }


  private int findMostFrequentNumber(List<SoloRedCard> palette) {
    ArrayList<Integer> numberFrequency = new ArrayList<>();
    for (int i = 0; i <= 7; i++) {
      numberFrequency.add(0);
    }
    for (SoloRedCard card : palette) {
      int cardNumberValue = card.getNumber().cardNumToString();
      numberFrequency.set(cardNumberValue, numberFrequency.get(cardNumberValue) + 1);
    }
    int highestFrequency = 0;
    for (int frequency : numberFrequency) {
      if (frequency > highestFrequency) {
        highestFrequency = frequency;
      }
    }

    return highestFrequency;
  }

  private int blueRule(List<List<SoloRedCard>> palettes) {
    int highestCount = -1;
    int winningPaletteIndex = -1;
    List<Integer> tiedPalettes = new ArrayList<>();
    for (int paletteIndex = 0; paletteIndex < palettes.size(); paletteIndex++) {
      List<SoloRedCard> palette = palettes.get(paletteIndex);
      int currentUniqueColorNum = findUniqueColorInPalette(palette);
      if (currentUniqueColorNum > highestCount) {
        highestCount = currentUniqueColorNum;
        winningPaletteIndex = paletteIndex;
        tiedPalettes.clear();
        tiedPalettes.add(paletteIndex);
      } else if (currentUniqueColorNum == highestCount) {
        tiedPalettes.add(paletteIndex);
      }
    }
    if (tiedPalettes.size() > 1) {
      winningPaletteIndex = tieBreak(tiedPalettes);
    }
    return winningPaletteIndex;
  }

  private int findUniqueColorInPalette(List<SoloRedCard> palette) {
    Set<CardColor> uniqueColors = new HashSet<>();
    for (SoloRedCard card : palette) {
      uniqueColors.add(card.getColor());
    }
    return uniqueColors.size();
  }

  private int tieBreak(List<Integer> tiedPalettes) {
    List<List<SoloRedCard>> tiedPalettesList = new ArrayList<>();
    for (int index : tiedPalettes) {
      tiedPalettesList.add(palettes.get(index));
    }
    int tiedPalettesIndex = redRule(tiedPalettesList);
    return tiedPalettes.get(tiedPalettesIndex);
  }

  private int indigoRule(List<List<SoloRedCard>> palettes) {
    int longestRun = 0;
    int winningPaletteIndex = -1;
    List<Integer> tiedPalettes = new ArrayList<>();
    for (int paletteIndex = 0; paletteIndex < palettes.size(); paletteIndex++) {
      List<SoloRedCard> palette = palettes.get(paletteIndex);
      List<Integer> cardNumbers = new ArrayList<>();
      for (SoloRedCard card : palette) {
        cardNumbers.add(card.getNumber().cardNumToString());
      }
      Collections.sort(cardNumbers);
      int currentRun = 1;
      for (int i = 1; i < cardNumbers.size(); i++) {
        if (cardNumbers.get(i) == cardNumbers.get(i - 1) + 1) {
          currentRun++;
        } else {
          currentRun = 1;
        }
        if (currentRun > longestRun) {
          longestRun = currentRun;
          winningPaletteIndex = paletteIndex;
          tiedPalettes.clear();
          tiedPalettes.add(paletteIndex);
        } else if (currentRun == longestRun) {
          tiedPalettes.add(paletteIndex);
        }
      }
    }
    if (tiedPalettes.size() > 1) {
      winningPaletteIndex = tieBreak(tiedPalettes);
    }

    return winningPaletteIndex;
  }

  private int violetRule(List<List<SoloRedCard>> palettes) {
    int cardsBelowFour = 0;
    int winningPaletteIndex = -1;
    List<Integer> tiedPalettes = new ArrayList<>();
    for (int paletteIndex = 0; paletteIndex < palettes.size(); paletteIndex++) {
      List<SoloRedCard> palette = palettes.get(paletteIndex);
      int countBelowFour = 0;
      for (SoloRedCard card : palette) {
        if (card.getNumber().cardNumToString() < 4) {
          countBelowFour++;
        }
      }
      if (countBelowFour > cardsBelowFour) {
        cardsBelowFour = countBelowFour;
        winningPaletteIndex = paletteIndex;
        tiedPalettes.clear();
        tiedPalettes.add(paletteIndex);
      } else if (countBelowFour == cardsBelowFour) {
        tiedPalettes.add(paletteIndex);
      }
    }
    if (tiedPalettes.size() > 1) {
      winningPaletteIndex = tieBreak(tiedPalettes);
    }

    return winningPaletteIndex;
  }

  @Override
  public boolean isGameOver() {
    gameNotStarted();
    return gameOver;
  }

  @Override
  public boolean isGameWon() {
    gameNotStarted();
    if (!isGameOver()) {
      throw new IllegalStateException("Game has not started, isGameWon");
    }
    return gameWon;
  }

  @Override
  public List<SoloRedCard> getHand() {
    gameNotStarted();
    return new ArrayList<>(hand);
  }

  @Override
  public List<SoloRedCard> getPalette(int paletteNum) {
    gameNotStarted();
    palIdxLessZeroOrMoreThanTotal(paletteNum);
    return new ArrayList<>(palettes.get(paletteNum));
  }

  @Override
  public SoloRedCard getCanvas() {
    gameNotStarted();
    return canvas;
  }

  @Override
  public List<SoloRedCard> getAllCards() {
    List<SoloRedCard> allCards = new ArrayList<>();
    for (CardColor color : CardColor.values()) {
      for (CardNumber number : CardNumber.values()) {
        allCards.add(new SoloRedCard(color, number));
      }
    }
    return allCards;
  }
}