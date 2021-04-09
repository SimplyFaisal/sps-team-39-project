const createPuzzleForm = document.getElementById("create-puzzle-form");
const responseMessage = document.getElementById("upload-message");

//Handles the Create Puzzle Form Submission
const handleCreatePuzzleFormSubmission = (e) =>{
    e.preventDefault();
    createPuzzleForm.reset();

    //Getting the link to the newly created puzzle
    getPuzzleLink();
}

async function getPuzzleLink () {
    try{
        const response = await fetch("/upload");
        const text = await response.json();

        //constructing the success message
        const message = document.createElement("p");
        message.innerText = "Puzzle successfully created! Share this link to your puzzle with friends!";
        message.classList.add("text-success","my-0");

        //constructing the link to the puzzle
        const link = document.createElement("a");
        link.href = text;
        link.innerText = `${text}`;
        link.classList.add("d-block", "mx-auto","text-center","pb-2");

        //Add both message and link nodes as children of the 'upload-message' div 
        responseMessage.append(link);
        responseMessage.append(message);
        responseMessage.classList.remove("d-none");
    }
    catch(error){
        //constructing the error message
        const message = document.createElement("p");
        message.innerText =  "There was an error in creating a puzzle for your image. Please try again!";
        message.classList.add("text-danger","my-0");

        //Add the message node as a child of the 'upload-message' div
        responseMessage.append(message);
        responseMessage.classList.remove("d-none");

    }
}
//Event Listeners for the Create Puzzle Form
createPuzzleForm.addEventListener("submit", (e)=>{
    handleCreatePuzzleFormSubmission(e);
})