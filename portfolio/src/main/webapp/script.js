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
  }  
}
