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

async function requestPuzzle() {
  //gets the url parameters
  const params = new URLSearchParams(window.location.search);
  
  if(params.has('id')) {
    //gets the id
    const puzzleId = params.get('id');

    //does a request to to get a json object with the puzzle data
    const serverResponse = await fetch(`/puzzle?id=${puzzleId}`);
    const responseObject = await serverResponse.json();

    //here it should be rendered to the page
    console.log(responseObject);
    
    const puzzlePage = document.getElementById("thisPuzzle");
    const puzzleDifficulty = responseObject.difficulty;
    const puzzleImage = responseObject.imageUrl;

    if (puzzleDifficulty == "EASY"){
        puzzlePage.innerHTML = `<img src=${puzzleImage} onLoad="snapfit.add((this), {level:1, mixed:true, snap:12, nokeys:true, aborder:true, polygon:true, aimage:true, aopacity: 0.3, space:15, callback:function(){solvedPuzzle()} });" width="750">`;
    }

    if (puzzleDifficulty == "MEDIUM"){
        puzzlePage.innerHTML = `<img src=${puzzleImage} onLoad="snapfit.add((this), {level:3, mixed:true, snap:12, nokeys:true, aborder:true, polygon:true, aimage:true, aopacity: 0.3, space:15, callback:function(){solvedPuzzle()} });" width="750">`;
    }

    if (puzzleDifficulty == "HARD"){
        puzzlePage.innerHTML = `<img src=${puzzleImage} onLoad="snapfit.add((this), {level:5, mixed:true, snap:12, nokeys:true, aborder:true, polygon:true, aimage:true, aopacity: 0.3, space:15, callback:function(){solvedPuzzle()} });" width="750">`;
    }

    //link to image download after solved
    document.getElementById("downloadImg").innerHTML = `<a href="${puzzleImage}">Click here to download image!</a>`;
  }  
}

async function solvedPuzzle(){
    document.getElementById("solved").style.display = "block";
}