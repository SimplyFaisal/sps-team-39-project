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
  private String difficulty;

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

  public Puzzle setDifficulty(String difficulty) {
    this.difficulty = difficulty;
    return this;
  }

  public String getDifficulty() {
      return this.difficulty;
  }

}