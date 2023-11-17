const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/chat-app'
});

const headers = {
    Authorization: 'Bearer your_token', // Replace with your actual authentication token
    Username: 'your_username' // Replace with your actual username
};

stompClient.configure({
    connectHeaders: headers,
});

stompClient.onConnect = (frame) => {
     setConnected(true);
     sendAuthenticationHeaders(); // Sending headers only when connected
     console.log('Connected: ' + frame);
};

function sendAuthenticationHeaders(connected) {
        stompClient.publish({
            destination: '/app/on-connect',
            headers: {
                Authorization: 'Bearer your_token', // Replace with your actual authentication token
                Username: 'your_username' // Replace with your actual username
            }
        });
}

function setConnected(connected) {
    if (connected) {
        $("#status").text("Connected");
        $("#connect").prop("disabled", true);
        $("#disconnect").prop("disabled", false);
    } else {
        // Update UI or perform actions when disconnected
        $("#status").text("Disconnected");
        $("#connect").prop("disabled", false);
        $("#disconnect").prop("disabled", true);
    }
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.publish({
        destination: "/app/send-message",
        body: JSON.stringify({'message': $("#message").val()})
    });
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});
