document.addEventListener("DOMContentLoaded", function(event) {

    tokenVerification();

    checkAdminRoles();
});

function tokenVerification() {

    if (typeof Cookies.get('token') === 'undefined') {
        console.log("Cookie not detected");
        document.location.href="index.html";
    }
    console.log("Cookie detected");
}

function checkAdminRoles() {
    console.log("Checking admin roles");

    var bearer = 'Bearer ' + Cookies.get('token');
    var headers = {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': bearer
            };
    fetch("/api/check", {
            method: 'GET',
            headers: headers
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            if(data.result == "Ko") {
                console.log("Current user doesn´t have enough credentials");
                //document.location.href = "out.html";
            }
        });
}