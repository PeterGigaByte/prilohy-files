//bloky na registráciu/editáciu
let registrationBlock = document.getElementById("registrationBlock");
let detailsBlock = document.getElementById("detailsBlock");
let settingsBlock = document.getElementById("settingsBlock");
//

//
//Listeners na zmenu blokov medzi sebou
let registrationClick = document.getElementById("first");
let detailsClick = document.getElementById("second");
let settingsClick = document.getElementById("third");
detailsClick.addEventListener('click',changeToDetails);
registrationClick.addEventListener('click',changeToRegistration);
settingsClick.addEventListener('click',changeToSettings);
//

//nastavenia automatické generovanie dráh
let tracksNumber = document.getElementById("tracksNumber");
let tracks = document.getElementById("tracks").children;
tracksNumber.addEventListener('keyup',tracksCheck);
tracks.item(0).classList.remove("hidden");

//checkbox Hala Prepojenie
let checkboxH = document.getElementById("checkboxH");
let checkboxH2 = document.getElementById("checkboxH2");
let checkboxR = document.getElementById("checkboxR");
let checkboxO = document.getElementById("checkboxO");
checkboxH.addEventListener("change",sync);
checkboxH2.addEventListener("change",sync2);
checkboxR.addEventListener("change",f1);
checkboxO.addEventListener("change",f2);

f(checkboxH);f(checkboxH2);f(checkboxO);f(checkboxR);
sync();
//generovanie

//

function tracksCheck() {
    for(let index = 0;index<tracks.length;index++){
        if(index<tracksNumber.value){
            tracks.item(index).classList.remove("hidden");
        }else {
            tracks.item(index).classList.add("hidden");
        }
    }
}

function changeToSettings() {
    detailsBlock.style.display="none";
    registrationBlock.style.display='none';
    settingsBlock.style.display='block';
}
function changeToRegistration() {
    detailsBlock.style.display="none";
    settingsBlock.style.display="none";
    registrationBlock.style.display='block';
}
function changeToDetails() {
    registrationBlock.style.display='none';
    settingsBlock.style.display="none";
    detailsBlock.style.display="block";

}
function sync() {
    syncAll(checkboxH,checkboxH2);


}
function sync2() {
    syncAll(checkboxH2, checkboxH);

}
function syncAll(x,y) {
    x.value = !!x.checked;
    y.value=x.value;
    y.checked=x.checked;
    f(x);f(y);
    if(x.checked){
        tracksNumber.value=4;
        tracksCheck();
    }
    else{
        tracksNumber.value=8;
        tracksCheck();
    }
}
function f(checkboxT) {
    if (checkboxT.checked){
        checkboxT.value=0;
    }else{
        checkboxT.value=1;
    }
}
function f1() {
    f(checkboxR);
}
function f2() {
    f(checkboxO);
}
