document.addEventListener("DOMContentLoaded", function(event) {

    tokenVerification();

    var form = document.getElementById("myform");
    form.addEventListener("submit", function(e) {
        e.preventDefault();
        return validateForm();
    });
});

function tokenVerification() {

    if (typeof Cookies.get('token') !== 'undefined') {
        console.log("Cookie detected");
        document.location.href="home.html";
    }
}

function validateForm() {
    try {
        var inputValue1 = document.getElementById("input1").value;
        var inputValue2 = document.getElementById("input2").value;
        const data = { username: inputValue1, password: inputValue2 };
        const address = '/api/login';
        fetch(address, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
            })
            .then(response => response.json())
            .then(data => {
                console.log(data);
                if (typeof data.jwttoken !== 'undefined') {
                    console.log("Authenticated");
                    Cookies.set('token', data.jwttoken)
                    document.location.href="/home.html";
                } else {
                    alert("Credential not recognized");
                }
            });

    } catch (err) {
        console.error(err.message);
    }
    return false;
}