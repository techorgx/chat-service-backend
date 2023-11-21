const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/chat-app'
});


function sendAuthenticationHeaders(connected) {

       stompClient.publish({
                   destination: '/app/on-connect',
                   headers: {
                       Authorization: 'Bearer your_token', // Replace with your actual authentication token
                       Username: $("#source-username").val() // Replace with your actual username
                   }
         });
}

stompClient.onConnect = (frame) => {
     setConnected(true);
     sendAuthenticationHeaders(); // Sending headers only when connected
     console.log('Connected: ' + frame);
};

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
    var payload = {
            sourceUsername: $("#source-username").val(),
            destinationUsername: $("#destination-username").val(),
            message: $("#message").val()
        };
    stompClient.publish({
        destination: "/app/send-message",
        body: JSON.stringify(payload)
    });
    appendOutgoingMsg();
}

function appendOutgoingMsg() {
 // Append the outgoing message to the chat container
        var outgoingChats = $(".outgoing-chats");
        var message = $("#message").val()
        var outgoingMsgContainer = $("<div class='outgoing-msg'></div>");
        var outgoingMsg = $("<div class='outgoing-chats-msg'></div>");
        var msgParagraph = $("<p>" + message + "</p>");
        var timeSpan = $("<span class='time'>" + getCurrentTime() + "</span>");

        outgoingMsg.append(msgParagraph);
        outgoingMsg.append(timeSpan);
        outgoingMsgContainer.append(outgoingMsg);
        outgoingChats.append(outgoingMsgContainer);

        // Clear the message input
        messageInput.val('');
}

function getCurrentTime() {
    var currentDate = new Date();
    var hours = currentDate.getHours();
    var minutes = currentDate.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';

    // Convert hours to 12-hour format
    hours = hours % 12;
    hours = hours ? hours : 12; // The hour '0' should be '12'

    // Add leading zero to minutes if needed
    minutes = minutes < 10 ? '0' + minutes : minutes;

    // Get month name
    var months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
    var month = months[currentDate.getMonth()];

    // Get day of the month
    var day = currentDate.getDate();

    return hours + ':' + minutes + ' ' + ampm + ' | ' + month + ' ' + day;
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});

$(document).ready(function () {
        // Disable the Connect button initially
        $("#connect").prop("disabled", true);

        // Check the input value on input change
        $("#source-username").on("input", function () {
            // Enable the Connect button if there is some text in the input
            $("#connect").prop("disabled", $(this).val().trim() === "");
        });
});
