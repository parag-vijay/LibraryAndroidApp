const db = require('../database/database');


function isExistingUser(callbackFunction, email, isLibrarian) {

    var isExistingUserQuery;
    if (isLibrarian) {
        isExistingUserQuery = "SELECT * FROM Librarian WHERE EMAIL='" + email + "'";

    } else {

        isExistingUserQuery = "SELECT * FROM Patron WHERE EMAIL='" + email + "'";
    }

    console.log("Query is :" + isExistingUserQuery);
    db.executeQuery(function (err, results) {
        if (err) {
            console.log("ERROR: " + error.message);
            throw err;
        } else {
            if (results.length > 0) {
                console.log("Not a new user...!!!");
                status = true;

            } else {
                console.log("New user...!!!");
                status = false;

            }

        }
        callbackFunction(status);
    }, isExistingUserQuery);

}


function isAuthenticUser(callbackFunction, email, password,isLibrarian) {

    var isAuthenticUserQuery;
    if (isLibrarian) {
        isAuthenticUserQuery = "SELECT * FROM Librarian WHERE EMAIL='" + email + "' AND PASSWORD='" + password + "'";

    } else {

        isAuthenticUserQuery = "SELECT * FROM Patron WHERE EMAIL='" + email + "' AND PASSWORD='" + password + "'";
    }

    console.log("Query is:" + isAuthenticUserQuery);
    db.executeQuery(function (err, results) {
        if (err) {
            console.log("ERROR: " + error.message);
            throw err;
        } else {
            if (results.length > 0) {
                console.log("Valid Login...!!!");
                status = true;

            } else {
                console.log("Invalid Login...!!!");
                status = false;

            }

        }
        callbackFunction(status);
    }, isAuthenticUserQuery);


}



function isBookPresent(callbackFunction, title, author) {

    var isBookPresentQuery;

    isBookPresentQuery = "SELECT * FROM Books WHERE Author='" + author + "' AND Title='" + title + "'";



    console.log("Query is :" + isBookPresentQuery);
    db.executeQuery(function (err, results) {
        if (err) {
            console.log("ERROR: " + error.message);
            throw err;
        } else {
            if (results.length > 0) {
                console.log("Book present in DB update count...!!!");
                console.log(results);
                status = true;
                callbackFunction(status, results);

            } else {
                console.log("Add Book...!!!");
                status = false;
                callbackFunction(status);

            }

        }

    }, isBookPresentQuery);

}

function isBookIssued(callbackFunction, bookId, issueStatus) {

    var isBookIssuedQuery;

    isBookIssuedQuery = "SELECT * FROM PatronTransaction WHERE BookId='" + bookId + "' AND ActionType='" + issueStatus + "'";

    console.log("Query is :" + isBookIssuedQuery);
    db.executeQuery(function (err, results) {
        if (err) {
            console.log("ERROR: " + error.message);
            throw err;
        } else {
            if (results.length > 0) {
                console.log("Book issued cannot be deleted...!!!");
                console.log(results);
                status = true;
                callbackFunction(status, results);

            } else {
                console.log("Book is not issued...!!!");
                status = false;
                callbackFunction(status);

            }

        }

    }, isBookIssuedQuery);

}



exports.isExistingUser = isExistingUser;
exports.isAuthenticUser = isAuthenticUser
exports.isBookPresent = isBookPresent;
exports.isBookIssued = isBookIssued;
