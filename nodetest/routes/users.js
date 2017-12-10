var express = require('express');
var router = express.Router();
var db = require('../database/database');
var mail = require('../services/mail');
var validator = require('../services/validation');


/* GET users listing. */
router.post('/signup', function (req, res, next) {

    var name = req.body.name;
    var email = req.body.email;
    var password = req.body.password;
    var universityID = req.body.universityID;
    var isLibrarian = email.endsWith("@sjsu.edu");
    var code = Math.floor((Math.random() * 10000) + 1000);
    var signUpUserQuery;


    validator.isExistingUser(function (userStatus) {
        console.log("userStatus :", userStatus)
        if (userStatus) {
            res.status(401).json({
                result: "SignUp Failed",
                status: 401
            });
        } else {

            if (isLibrarian) {
                signUpUserQuery = "INSERT INTO Librarian (Name,Email,Password,UniversityID,Code)VALUES('" +
                    name +
                    "','" +
                    email +
                    "','" +
                    password +
                    "','" +
                    universityID +
                    "','" +
                    code + "')";

            } else {

                signUpUserQuery = "INSERT INTO Patron (Name,Email,Password,UniversityID,Code)VALUES('" +
                    name +
                    "','" +
                    email +
                    "','" +
                    password +
                    "','" +
                    universityID +
                    "','" +
                    code + "')";

            }
            console.log("Query is:" + signUpUserQuery);

            db.executeQuery(function (err, results) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {
                    var mailIndex=1;
                    mail.sendMail(req, res,mailIndex,"",code, function (err) {
                        if (err) {
                            console.log("Error while sending email")
                        }
                        else {
                            console.log("Mail successfully sent")
                        }
                    });

                    res.status(201).json({
                        result: "SignUp Successfull",
			            email: email,
                        status: 201
                    });

                }
            }, signUpUserQuery);
        }

    }, email, isLibrarian);

});


router.post('/verify', function (req, res, next) {

    var email = req.body.email;
    var code = req.body.code;
    var isLibrarian = email.endsWith("@sjsu.edu");
    var signUpVerifyQuery;
    var selectBooksQuery;
    var resultJson;
    var selectQuery;



            if (isLibrarian) {
                signUpVerifyQuery =  "select code from Librarian where email ='" + email + "' and code ='" + code + "'";

            } else {

                signUpVerifyQuery =  "select code from Patron where email ='" + email + "' and code ='" + code + "'";

            }
            console.log("Query is:" + signUpVerifyQuery);

            db.executeQuery(function (err, results) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {

                    if (results.length > 0) {


                        if(!isLibrarian) {

                            resultJson = {
                                name: "",
                                email: "",
                                password: "",
                                universityID: "",
                                totalBooksCount: "",
                                issuedBooksCount: "",
                                fine: "",
                                books: []

                            };

                            selectQuery = "select name, email, password, universityID, totalBooksCount, issuedBooksCount,fine from Patron where email ='" +
                                email + "'";
                            console.log("Query is:" + selectQuery);

                            db.executeQuery(function (err, resultsUserDetails) {
                                if (err) {
                                    console.log("ERROR: " + error.message);
                                    throw err;
                                } else {
                                    resultJson.name = resultsUserDetails[0].name;
                                    resultJson.email = resultsUserDetails[0].email;
                                    resultJson.password = resultsUserDetails[0].password;
                                    resultJson.universityID = resultsUserDetails[0].universityID;
                                    resultJson.totalBooksCount = resultsUserDetails[0].totalBooksCount;
                                    resultJson.issuedBooksCount = resultsUserDetails[0].issuedBooksCount;
                                    resultJson.fine = resultsUserDetails[0].fine;
                                    resultJson.status = 200;
                                    console.log(resultJson);
                                }
                            }, selectQuery);


                        } else {

                            resultJson = {
                                name: "",
                                email: "",
                                password: "",
                                universityID: "",
                                books: []

                            };

                            selectQuery = "select name, email, password, universityID from Librarian where email ='" +
                                email + "'";
                            console.log("Query is:" + selectQuery);

                            db.executeQuery(function (err, resultsUserDetails) {
                                if (err) {
                                    console.log("ERROR: " + error.message);
                                    throw err;
                                } else {
                                    resultJson.name = resultsUserDetails[0].name;
                                    resultJson.email = resultsUserDetails[0].email;
                                    resultJson.password = resultsUserDetails[0].password;
                                    resultJson.universityID = resultsUserDetails[0].universityID;
                                    resultJson.status = 200;
                                    console.log(resultJson);
                                }
                            }, selectQuery);

                        }


                        if(isLibrarian) {
                            selectBooksQuery = "SELECT B1.*\n" +
                                "FROM \n" +
                                "    cmpe277.LibrarianTransaction  T1\n" +
                                "    LEFT JOIN cmpe277.LibrarianTransaction T2 ON T1.BookId=T2.BookId and T1.ActionType=\"ADD\"  and T2.ActionType=\"REMOVE\"  and T1.Email=T2.Email\n" +
                                "    INNER JOIN cmpe277.Books  B1 ON B1.BookId = T1.BookId\n" +
                                "WHERE \n" +
                                "    T2.BookId IS NULL and\n" +
                                "    T1.Email='" +
                                email + "'";

                        } else {


                            selectBooksQuery = "SELECT B1.*\n" +
                                "FROM \n" +
                                "    cmpe277.PatronTransaction  T1\n" +
                                "    LEFT JOIN cmpe277.PatronTransaction T2 ON T1.BookId=T2.BookId and T1.ActionType=\"ADD\"  and T2.ActionType=\"REMOVE\"  and T1.Email=T2.Email\n" +
                                "    INNER JOIN cmpe277.Books  B1 ON B1.BookId = T1.BookId\n" +
                                "WHERE \n" +
                                "    T2.BookId IS NULL and\n" +
                                "    T1.Email='" +
                                email + "'";
                        }
                        console.log("Query is:" + selectBooksQuery);

                        db.executeQuery(function (err, bookResults) {
                            if (err) {
                                console.log("ERROR: " + error.message);
                                throw err;
                            } else {
                                //console.log("hi, "+results[0].firstname);
                                for (var book in bookResults) {
                                    resultJson.books.push({
                                        author: bookResults[book].Author,
                                        title: bookResults[book].Title,
                                        callNumber: bookResults[book].CallNumber,
                                        publisher: bookResults[book].Publisher,
                                        yearOfPublication: bookResults[book].YearOfPublication,
                                        locationInTheLibrary: bookResults[book].LocationInTheLibrary,
                                        numberOfCopies: bookResults[book].NumberOfCopies,
                                        currentStatus: bookResults[book].CurrentStatus,
                                        keywords: bookResults[book].Keywords,
                                        coverageImage: bookResults[book].CoverageImage,
                                        dateOfIssue: bookResults[book].DateOfIssue,
                                        dateOfReturn: bookResults[book].DateOfReturn,
                                        bookId: bookResults[book].BookId

                                    })

                                }
                                res.status(200).json(resultJson);
                                console.log(resultJson);
                            }
                        }, selectBooksQuery);
                        console.log(resultJson);


                    } else {
                        res.status(401).json(resultJson);

                    }
                }
            }, signUpVerifyQuery);




});



router.post('/signin', function (req, res) {

    var email = req.body.email;
    var password = req.body.password;
    var isLibrarian = email.endsWith("@sjsu.edu");
    var selectBooksQuery;
    var resultJson;
    var selectQuery;


    validator.isAuthenticUser(function (userStatus) {
        console.log("loginStatus :" + userStatus)

        if (userStatus) {

            if(!isLibrarian) {

                resultJson = {
                    name: "",
                    email: "",
                    password: "",
                    universityID: "",
                    totalBooksCount: "",
                    issuedBooksCount: "",
                    fine: "",
                    books: []

                };

                selectQuery = "select name, email, password, universityID, totalBooksCount, issuedBooksCount,fine from Patron where email ='" +
                    email + "'";
                console.log("Query is:" + selectQuery);

                db.executeQuery(function (err, resultsUserDetails) {
                    if (err) {
                        console.log("ERROR: " + error.message);
                        throw err;
                    } else {
                        resultJson.name = resultsUserDetails[0].name;
                        resultJson.email = resultsUserDetails[0].email;
                        resultJson.password = resultsUserDetails[0].password;
                        resultJson.universityID = resultsUserDetails[0].universityID;
                        resultJson.totalBooksCount = resultsUserDetails[0].totalBooksCount;
                        resultJson.issuedBooksCount = resultsUserDetails[0].issuedBooksCount;
                        resultJson.fine = resultsUserDetails[0].fine;
                        resultJson.status = 200;
                        console.log(resultJson);
                    }
                }, selectQuery);


            } else {

                resultJson = {
                    name: "",
                    email: "",
                    password: "",
                    universityID: "",
                    books: []

                };

                selectQuery = "select name, email, password, universityID from Librarian where email ='" +
                    email + "'";
                console.log("Query is:" + selectQuery);

                db.executeQuery(function (err, resultsUserDetails) {
                    if (err) {
                        console.log("ERROR: " + error.message);
                        throw err;
                    } else {
                        resultJson.name = resultsUserDetails[0].name;
                        resultJson.email = resultsUserDetails[0].email;
                        resultJson.password = resultsUserDetails[0].password;
                        resultJson.universityID = resultsUserDetails[0].universityID;
                        resultJson.status = 200;
                        console.log(resultJson);
                    }
                }, selectQuery);

            }

            if(isLibrarian) {
                selectBooksQuery = "SELECT B1.*\n" +
                    "FROM \n" +
                    "    cmpe277.LibrarianTransaction  T1\n" +
                    "    LEFT JOIN cmpe277.LibrarianTransaction T2 ON T1.BookId=T2.BookId and T1.ActionType=\"ADD\"  and T2.ActionType=\"REMOVE\"  and T1.Email=T2.Email\n" +
                    "    INNER JOIN cmpe277.Books  B1 ON B1.BookId = T1.BookId\n" +
                    "WHERE \n" +
                    "    T2.BookId IS NULL and\n" +
                    "    T1.Email='" +
                    email + "'";

            } else {


                 selectBooksQuery = "SELECT B1.*\n" +
                    "FROM \n" +
                    "    cmpe277.PatronTransaction  T1\n" +
                    "    LEFT JOIN cmpe277.PatronTransaction T2 ON T1.BookId=T2.BookId and T1.ActionType=\"ADD\"  and T2.ActionType=\"REMOVE\"  and T1.Email=T2.Email\n" +
                    "    INNER JOIN cmpe277.Books  B1 ON B1.BookId = T1.BookId\n" +
                    "WHERE \n" +
                    "    T2.BookId IS NULL and\n" +
                    "    T1.Email='" +
                    email + "'";
            }
            console.log("Query is:" + selectBooksQuery);

            db.executeQuery(function (err, bookResults) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {
                    //console.log("hi, "+results[0].firstname);
                    for (var book in bookResults) {
                        resultJson.books.push({
                            author: bookResults[book].Author,
                            title: bookResults[book].Title,
                            callNumber: bookResults[book].CallNumber,
                            publisher: bookResults[book].Publisher,
                            yearOfPublication: bookResults[book].YearOfPublication,
                            locationInTheLibrary: bookResults[book].LocationInTheLibrary,
                            numberOfCopies: bookResults[book].NumberOfCopies,
                            currentStatus: bookResults[book].CurrentStatus,
                            keywords: bookResults[book].Keywords,
                            coverageImage: bookResults[book].CoverageImage,
                            dateOfIssue: bookResults[book].DateOfIssue,
                            dateOfReturn: bookResults[book].DateOfReturn,
                            bookId: bookResults[book].BookId

                        })

                    }
                    res.status(200).json(resultJson);
                    console.log(resultJson);
                }
            }, selectBooksQuery);
            console.log(resultJson)


        }
        else {


            res.status(400).json({
                result: "Failed",
                status: 400
            });
        }

    }, email, password,isLibrarian);

});


router.post('/signout', function (req, res, next) {


});

module.exports = router;
