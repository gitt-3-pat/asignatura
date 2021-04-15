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

    getCities();
}

function showContent(arrData, div) {

    var html = []
    html.push("<ul>");
    arrData.forEach(element => {
        html.push("<li>");
        html.push(element.nuts4_nombre)
        html.push(" ")
        html.push("<a href=\"city.html?id=" + element.municipio_codigo + "\">")
        html.push(element.municipio_nombre)
        html.push("</a>");
        html.push("</li>");
    });
    html.push("</ul>");
    document.getElementById(div).innerHTML = html.join("");
}

function getCities() {
    console.log("Retrieving data from cities");

    var bearer = 'Bearer ' + Cookies.get('token');
    var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': bearer
            };
    //https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Promise
    fetch("/api/cities", {
            method: 'GET',
            headers: headers
        })
        .then(response => response.json())
        .then(data => {
            //console.log(data);
            showContent(data, "content");
        });

    console.log("End GetCities");
}