const globalError = "An error occurred. Please try again later.";

function getResponse(){
    var data = document.getElementById("promptInputBox").value;
    const url = "http://localhost:8080/ai/lite";
    const prompt = data
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({prompt})
    };
    fetch(url, options)
    .then(response => {
        if(!response.ok){
            console.log(error);
            alert(globalError);
        }else{
            return response.json();
        }
    })
    .then(responseData => {
        document.getElementById("responseBox").innerText = responseData.result;
        document.getElementById("responseBox").style.visibility = "visible";
    })
    .catch(error => {
        console.log(error);
        alert(globalError);
    })
}

function getCVResponse(){
    var jobDescription = document.getElementById("cvJDInputBox").value;
    var cv = document.getElementById("cvInputBox");
    const url = "http://localhost:8080/ai/resume-scanner";

    if (cv.files.length === 0) {
        alert("Please upload a CV!");
        return;
    }

    const formData = new FormData();
    formData.append('job-description', jobDescription);
    formData.append('file', cv.files[0]);

    const options = {
        method: 'POST',
        body: formData
    };
    fetch(url, options)
    .then(response => {
        if(!response.ok){
            alert(globalError);
        }else{
            return response.json();
        }
    })
    .then(responseData => {
        document.getElementById("cvResponseBox").innerText = responseData.suggestion;
        document.getElementById("cvResponseBox").style.visibility = "visible";
    })
    .catch(error => {
        alert(globalError);
    })
}

function clearPrompt_Card(){
    document.getElementById("promptInputBox").value = "";
    document.getElementById("responseBox").innerText = "";
    document.getElementById("responseBox").style.visibility = "hidden";
}

function clearCV_Card(){
    document.getElementById("cvInputBox").value = "";
    document.getElementById("cvJDInputBox").value = "";
    document.getElementById("cvResponseBox").innerText = "";
    document.getElementById("cvResponseBox").style.visibility = "hidden";
}