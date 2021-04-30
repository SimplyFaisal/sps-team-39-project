// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

class Paginator {

  constructor() {
    /** @private @const */
    this.pages = [];

    /** @private @const */
    this.cursors = [];

    /** @private */
    this.filter = "ALL";
  }

  push(page, cursor) {
    this.cursors.push(cursor);
    this.pages.push(page);
  }

  pop() {
    if(this.pages.length > 1) {
      this.cursors.pop();
      this.pages.pop()
    }
  }

  topPage() {
    const length = this.pages.length;
    return length > 0 ? this.pages[length - 1] : [];
  }

  topCursor() {
    const length = this.cursors.length;
    return length > 0 ? this.cursors[length - 1] : "";
  }

  getFilter() {
    return this.filter;
  }

  setFilter(filter) {
    this.cursors = [];
    this.pages = [];
    this.filter = filter;
  }
}

function createRow(puzzle) {
  return `<td>${puzzle.name}</td>` + 
      `<td>${puzzle.username}</td>` +
      `<td>${puzzle.difficulty}</td>` +
      `<td><img src="${puzzle.imageUrl}"></td>` + 
      `<td><a href="/viewpuzzle?id=${puzzle.puzzleId}">Link</a></td>`;
}

function insertRows(table, puzzles) {
  table.innerHTML = "";
  puzzles.forEach(puzzle => {
    const tr = document.createElement("tr");
    tr.innerHTML = createRow(puzzle);
    table.appendChild(tr);
  });
}

const paginator = new Paginator();

function prevPage() {
  const puzzleTable = document.getElementById("puzzle-table");
  paginator.pop();
  insertRows(puzzleTable, paginator.topPage());
}

async function nextPage() {
  //does a request
  const serverResponse = await fetch(`/listpuzzles?cursor=${paginator.topCursor()}&difficulty=${paginator.getFilter()}`);
  const responseObject = await serverResponse.json();

  const puzzleTable = document.getElementById("puzzle-table");
  const puzzles = responseObject.puzzles;

  if(puzzles.length > 0) {
    //adds a page
    paginator.push(puzzles, responseObject.cursorUrl);
    //adds a rows to the table
    insertRows(puzzleTable, puzzles);
  }
}

function filterByDifficulty() {
  const difficulty = document.getElementById("difficulty-filter").value;
  paginator.setFilter(difficulty);
  nextPage();
}
