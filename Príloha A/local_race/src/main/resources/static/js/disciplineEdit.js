let disciplines = ["40 m","50 m","60 m"];
let categories = ["Muži","Ženy","Juniori","Juniorky"];
let disciplineInput = document.getElementById("disciplineName");
let categoryInput = document.getElementById("categoryName");
autocomplete(disciplineInput, disciplines);
autocomplete(categoryInput, categories);