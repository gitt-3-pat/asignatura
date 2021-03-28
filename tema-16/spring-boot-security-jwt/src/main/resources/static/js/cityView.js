document.addEventListener("DOMContentLoaded", function(event) {

    tokenVerification();

    loadingContents();
});

function tokenVerification() {

    if (typeof Cookies.get('token') === 'undefined') {
        console.log("Cookie not detected");
        document.location.href="index.html";
    }
    console.log("Cookie detected");
}

function loadingContents() {

    console.log("Loading contents");

const queryString = window.location.search;
console.log(queryString);
const urlParams = new URLSearchParams(queryString);
const id = urlParams.get('id')
console.log(id);

    getCity(id);
}

function showContent(arrData, div) {

    document.getElementById("city").innerHTML = arrData.municipio_nombre;

    var html = []
    html.push("<ul>");

    for (let key in arrData) {
        html.push("<li>");
        html.push("<strong>");
        html.push(key);
        html.push("</strong>");
        html.push(" : ");
        html.push(arrData[key])
        html.push("</li>");
    }
    html.push("</ul>");
    document.getElementById(div).innerHTML = html.join("");
}

function getCity(id) {
    console.log("Retrieving data from cities");

    var bearer = 'Bearer ' + Cookies.get('token');
    var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': bearer
            };
    //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise
    fetch("/api/cities/" + id, {
            method: 'GET',
            headers: headers
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            showContent(data, "content");
        });

    console.log("End GetCities");
}