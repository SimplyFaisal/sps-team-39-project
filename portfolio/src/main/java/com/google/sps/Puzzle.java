package com.google.sps;

/** Puzzle domain object */
public class Puzzle {

  /*
  * Datastore generated id
   */
  private Long puzzleId;
  
  /*
  * Blobstore url
   */
  private String imageUrl;

  /*
  * Difficulty of the puzzle
  */
  public enum Difficulty {
      EASY,
      MEDIUM,
      HARD
  }
  
  private Difficulty difficulty;

  private String name = "";

  public Long getPuzzleId() {
    return this.puzzleId;
  }

  public Puzzle setPuzzleId(Long puzzleId) {
    this.puzzleId = puzzleId;
    return this;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public Puzzle setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public Puzzle setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
    return this;
  }

  public Difficulty getDifficulty() {
      return this.difficulty;
  }

  public Puzzle setName(String name) {
      this.name = name;
      return this;
  }

  public String getName() {
      return this.name;
  }

}
