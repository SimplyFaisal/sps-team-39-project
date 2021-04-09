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
  enum difficultyEnum {
      Easy,
      Medium,
      Hard
  }

  private difficultyEnum difficulty;

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
    this.difficulty = difficultyEnum.valueOf(difficulty);
    return this;
  }

  public difficultyEnum getDifficulty() {
      return this.difficulty;
  }

}