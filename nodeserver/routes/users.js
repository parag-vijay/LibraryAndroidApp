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

                signUpUserQuery = "INSERT INTO Patron (Name,Email,Password,UniversityI,Code)VALUES('" +
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
                    res.status(201).json({
                        result: "SignUp Successfull",
                        status: 201
                    });

                }
            }, signUpUserQuery);
        }

        mail.sendMail(req, res, function (err) {
            if (err) {
                console.log("Error while sending email")
            }
            else {
                console.log("Mail successfully sent")
            }
        });
    }, email, isLibrarian);

});


router.post('/signin', function (req, res) {

    var email = req.body.email;
    var password = req.body.password;
    var isLibrarian = email.endsWith("@sjsu.edu");


    validator.isAuthenticUser(function (userStatus) {
        console.log("loginStatus :" + userStatus)

        if (userStatus) {

            if(isLibrarian) {

                var resultJson = {
                    name: "",
                    email: "",
                    password: "",
                    universityID: "",
                    totalBooksCount: "",
                    issuedBooksCount: "",
                    fine: "",
                    books: []

                };

                var selectQuery = "select name, email, password, universityID, totalBooksCount, issuedBooksCount,fine from Librarian where email ='" +
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

                var resultJson = {
                    name: "",
                    email: "",
                    password: "",
                    universityID: "",
                    books: []

                };

                var selectQuery = "select name, email, password, universityID from Patron where email ='" +
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

            var selectFilesQuery = "select  FilePath,  FileName,  CASE WHEN FileType = 'file' THEN false ELSE true END as isFolder,  now() as lastModified from UserAccess JOIN Files using (FilePath) where UserId = '" +
                email + "'";
            console.log("Query is:" + selectFilesQuery);

            db.executeQuery(function (err, resultFiles) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {
                    //console.log("hi, "+results[0].firstname);
                    for (var file in resultFiles) {
                        console.log(file)
                        resultJson.files.push({
                            filePath: resultFiles[file].FilePath,
                            fileName: resultFiles[file].FileName,
                            isAdmin: true,
                            isChecked: true,
                            isDeleted: false,
                            isFolder: resultFiles[file].isFolder,
                            isSelected: false,
                            isShared: false,
                            isStared: false,
                            lastModified: resultFiles[file].lastModified

                        })

                    }
                    res.status(200).json(resultJson);
                    console.log(resultJson);
                }
            }, selectFilesQuery);
            console.log(resultJson)


        }
        else {


            res.status(400).json({
                result: "Failed",
                status: 400
            });
        }

    }, email, password);

});


router.post('/signout', function (req, res, next) {


});

module.exports = router;
