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


function isAuthenticUser(callbackFunction, email, password) {
    var isAuthenticUserQuery = "SELECT * FROM USERS WHERE EMAIL='" + email + "' AND PASSWORD='" + password + "'";
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


exports.isExistingUser = isExistingUser;
exports.isAuthenticUser = isAuthenticUser