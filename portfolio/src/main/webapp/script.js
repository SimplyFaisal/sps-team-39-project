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

    const imageEl = document.getElementById("puzzle-image");
    imageEl.src = responseObject.imageUrl;

    const DIFFICULTY_TO_LEVEL = {
        "EASY": 1,
        "MEDIUM": 3,
        "HARD": 5
    };

    const settings = { 
        level: DIFFICULTY_TO_LEVEL[responseObject.difficulty],
        mixed: true, 
        snap: 12, 
        nokeys: true, 
        aborder: true, 
        polygon: true, 
        aimage: true, 
        aopacity: 0.3, 
        space: 15, 
        callback: function(){solvedPuzzle()}
    };

    imageEl.onload = function() {
        snapfit.add(imageEl, settings);
    }

    document.getElementById("downloadImg").innerHTML = `<a href="${responseObject.imageUrl}">Click here to download image!</a>`;
  }  
}

function solvedPuzzle(){
    document.getElementById("solved").style.display = "block";
}