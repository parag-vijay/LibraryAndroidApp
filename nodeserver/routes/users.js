var express = require('express');
var router = express.Router();
var db = require('../database/database');
var mail = require('../services/mail');
var validator = require('../services/validation');


/* GET users listing. */
router.post('/signup', function(req, res, next) {

    var name=req.body.name;
    var email=req.body.email;
    var password=req.body.password;
    var universityID=req.body.universityID;
    var isLibrarian = email.endsWith("@sjsu.edu");
    var signUpUserQuery;


    validator.isExistingUser(function(userStatus) {
        console.log("userStatus :",userStatus)
        if (userStatus) {
            res.status(401).json({
                result: "SignUp Failed",
                status: 401
            });
        } else {

            if(isLibrarian) {
                signUpUserQuery = "INSERT INTO Librarian (Name,Email,Password,UniversityID)VALUES('" +
                    name +
                    "','" +
                    email +
                    "','" +
                    password +
                    "','" +
                    universityID + "')";

            } else {

                signUpUserQuery = "INSERT INTO Patron (Name,Email,Password,UniversityID)VALUES('" +
                    name +
                    "','" +
                    email +
                    "','" +
                    password +
                    "','" +
                    universityID + "')";
            }
            console.log("Query is:" + signUpUserQuery);

            db.executeQuery(function(err, results) {
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

        mail.sendMail(req,res, function(err, data) {

        });
    }, email,isLibrarian);

});


router.post('/signin', function(req, res) {

    var email = req.body.email;
    var password = req.body.password;


    validator.isAuthenticUser(function(userStatus) {
        console.log("loginStatus :" + userStatus)

        if(userStatus)
        {

            var resultJson= {
                firstName: "",
                lastName:"",
                emailID:"",
                password:"",
                age:"",
                gender:"",
                status :"",
                files:[]

            };

            var selectQuery = "select firstname, lastname, email, password, age, gender from Users where email ='" +
                email + "'";
            console.log("Query is:" + selectQuery);

            db.executeQuery(function(err, resultsUserDetails) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {
                    console.log("hi, "+resultsUserDetails[0].firstname);
                    resultJson.firstName= resultsUserDetails[0].firstname;
                    resultJson.lastName=resultsUserDetails[0].lastname;
                    resultJson.emailID=resultsUserDetails[0].email;
                    resultJson.password= resultsUserDetails[0].password;
                    resultJson.age= resultsUserDetails[0].age;
                    resultJson.gender= resultsUserDetails[0].gender;
                    resultJson.status= 200;
                    console.log(resultJson);
                }
            }, selectQuery);


            /*"select  FilePath,  FileName, 'isAdmin',true,'isChecked',false,'isDeleted',false,'isFolder', CASE WHEN FileType = 'file' THEN true ELSE false END, 'isSelected',false,'isShared',false,'isStared',false,'lastModified', now()) from UserAccess JOIN Files using (FilePath) where UserId = '"+
                             emailID + "'";
            */
            var selectFilesQuery = "select  FilePath,  FileName,  CASE WHEN FileType = 'file' THEN false ELSE true END as isFolder,  now() as lastModified from UserAccess JOIN Files using (FilePath) where UserId = '"+
                email + "'";
            console.log("Query is:" + selectFilesQuery);

            db.executeQuery(function(err, resultFiles) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {
                    //console.log("hi, "+results[0].firstname);
                    for(var file in resultFiles)
                    {
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

    }, email,password);

});

module.exports = router;
