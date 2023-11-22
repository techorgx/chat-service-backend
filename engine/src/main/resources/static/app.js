
var stompClient = null

function connect() {
    var headers = {
       "Authorization": "Bearer your_token",
       "Username": $("#source-username").val()
    }
    stompClient = new StompJs.Client({
        brokerURL: 'ws://localhost:8080/chat-app',
        connectHeaders: headers,
        debug: function (str) {
            console.log(str);
          },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });
    stompClient.activate();
    stompClient.onConnect = connectCallback;
    stompClient.onStompError = errorCallback;
    stompClient.onDisconnect = function () {
        setConnected(false);
        console.log("Disconnected");
    };
}


function connectCallback(frame) {
    console.log('Connected ' + frame);
    setConnected(true);
}

function errorCallback() {
   console.log('Error');
}

function setConnected(connected) {
    if (connected) {
        $("#connect").prop("disabled", true);
        $("#disconnect").prop("disabled", false);
    } else {
        // Update UI or perform actions when disconnected
        $("#connect").prop("disabled", false);
        $("#disconnect").prop("disabled", true);
    }
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

document.addEventListener('DOMContentLoaded', function() {
    var messageInput = document.getElementById('message');
    var destinationUsernameInput = document.getElementById('destination-username');
    var sendButton = document.getElementById('send');

    function updateSendButtonStatus() {
        var isMessageEmpty = !messageInput.value.trim();
        var isDestinationUsernameEmpty = !destinationUsernameInput.value.trim();
        sendButton.disabled = isMessageEmpty || isDestinationUsernameEmpty;
    }

    // Attach the event listener to the input elements
    messageInput.addEventListener('input', updateSendButtonStatus);
    destinationUsernameInput.addEventListener('input', updateSendButtonStatus);
});