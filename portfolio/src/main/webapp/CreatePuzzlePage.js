const createPuzzleForm = document.getElementById("create-puzzle-form");
const difficultyInput = document.getElementById("puzzle-difficulty");
const fileInput = document.getElementById("file-input");
const successMessage = document.getElementById("upload-success-message");
successMessage.classList.add("d-none")

//Handles the Create Puzzle Form Submission
const handleCreatePuzzleFormSubmission = (e) =>{
    e.preventDefault();
    console.log(fileInput.value);
    console.log(difficultyInput.value);
    createPuzzleForm.reset();
    getPuzzleLink();
}
async function getPuzzleLink () {
    const response = await fetch("/upload");
    const text = await response.json();
    //Creates a success message with the link to the new puzzle and displays it to the client
    const link = document.createElement("a");
    successMessage.innerText = "Puzzle successfully created! Share the image with friends!";
    link.href = text;
    link.innerText = `${text}`;
    successMessage.append(link);
    successMessage.classList.remove("d-none");
}
//Event Listeners for the Create Puzzle Form
createPuzzleForm.addEventListener("submit", (e)=>{
    handleCreatePuzzleFormSubmission(e);
})