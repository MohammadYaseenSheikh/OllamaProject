const globalError = "An error occurred. Please try again later.";

function getResponse(){
    var data = document.getElementById("promptInputBox").value;
    const url = "/ai/lite";
    const prompt = data;
    const options = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({prompt})
    };
    fetch(url, options)
    .then(response => {
        if (!response.ok) {
            throw new Error(globalError);
        }
        return response.text(); // Use text() instead of json() to handle empty responses
    })
    .then(text => {
        console.log("Response Text:", text); // Log the response text for debugging
        if (text) {
            const responseData = JSON.parse(text);
            const responseBox = document.getElementById("responseBox");
            const responseText = responseBox.querySelector(".response-text");
            responseText.textContent = responseData.result;
            responseBox.style.visibility = "visible";
        } else {
            throw new Error(globalError);
        }
    })
    .catch(error => {
        console.log(error);
        alert(globalError);
    });
}

function getCVResponse(){
    var jobDescription = document.getElementById("cvJDInputBox").value;
    var cv = document.getElementById("cvInputBox");
    const url = "/ai/resume-scanner";

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
        if (!response.ok) {
            throw new Error(globalError);
        }
        return response.text(); // Use text() instead of json() to handle empty responses
    })
    .then(text => {
        console.log("Response Text:", text); // Log the response text for debugging
        if (text) {
            const responseData = JSON.parse(text);
            const suggestionData = JSON.parse(responseData.suggestion); // Parse the suggestion string
            const responseBox = document.getElementById("cvResponseBox");
            const responseText = responseBox.querySelector(".response-text");
            responseText.innerHTML = `<strong>Score:</strong> ${suggestionData.score}<br><strong>Suggestions:</strong><ul>${suggestionData.suggestions.map(suggestion => `<li>${suggestion}</li>`).join('')}</ul>`;
            responseBox.style.visibility = "visible";
        } else {
            throw new Error(globalError);
        }
    })
    .catch(error => {
        console.log(error);
        alert(globalError);
    });
}

function clearPrompt_Card(){
    document.getElementById("promptInputBox").value = "";
    const responseBox = document.getElementById("responseBox");
    const responseText = responseBox.querySelector(".response-text");
    responseText.textContent = "";
    responseBox.style.visibility = "hidden";
}

function clearCV_Card(){
    document.getElementById("cvInputBox").value = "";
    document.getElementById("cvJDInputBox").value = "";
    const responseBox = document.getElementById("cvResponseBox");
    const responseText = responseBox.querySelector(".response-text");
    responseText.textContent = "";
    responseBox.style.visibility = "hidden";
    const fileLabel = document.getElementById('cvInputColorBox');
    const fileIcon = document.getElementById('cvIcon');
    fileLabel.classList.remove('bg-success');
    fileLabel.classList.add('bg-secondary');
    fileIcon.classList.remove('bi-check-circle');
    fileIcon.classList.add('bi-paperclip');
}

function handleFileUpload() {
    const fileInput = document.getElementById('cvInputBox');
    const fileLabel = document.getElementById('cvInputColorBox');
    const fileIcon = document.getElementById('cvIcon');
    if (fileInput.files.length > 0) {
        fileLabel.classList.remove('bg-secondary');
        fileLabel.classList.add('bg-success');
        fileIcon.classList.remove('bi-paperclip');
        fileIcon.classList.add('bi-check-circle');
    } else {
        fileLabel.classList.remove('bg-success');
        fileLabel.classList.add('bg-secondary');
        fileIcon.classList.remove('bi-check-circle');
        fileIcon.classList.add('bi-paperclip');
    }
}

function fetchUserDetails() {
    fetch('/user', { method: 'GET', credentials: 'include' })
        .then(response => response.json())
        .then(data => {
            if (data.username) {
                document.getElementById("usernameDisplay").textContent = "Welcome, " + data.username;
            } else {
                document.getElementById("usernameDisplay").textContent = "Guest";
            }
        })
        .catch(error => console.error("Error fetching user data:", error));
}

function logout() {
    fetch('/logout', { method: 'POST', credentials: 'include' })
        .then(() => {
            window.location.href = "/login.html";
        })
        .catch(error => console.error("Logout failed:", error));
}

function copyToClipboard(elementId) {
    const textarea = document.getElementById(elementId);
    navigator.clipboard.writeText(textarea.value)
        .then(() => {
            console.log('Text copied to clipboard');
        })
        .catch(err => {
            console.error('Failed to copy text: ', err);
        });
}

// Call fetchUserDetails on page load
document.addEventListener('DOMContentLoaded', fetchUserDetails);