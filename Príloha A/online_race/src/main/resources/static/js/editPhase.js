//bloky na registráciu/editáciu
let phaseBlock = document.getElementById("phaseBlock");
let settingsBlock = document.getElementById("settingsBlock");

//Listeners na zmenu blokov medzi sebou
let phaseClick = document.getElementById("first");
let settingsClick = document.getElementById("second");

phaseClick.addEventListener('click',changeToPhaseBlock);
settingsClick.addEventListener('click',changeToSettings);

function changeToSettings() {
    phaseBlock.style.display='none';
    settingsBlock.style.display='block';
}
function changeToPhaseBlock() {
    settingsBlock.style.display="none";
    phaseBlock.style.display='block';
}
