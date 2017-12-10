var express = require('express');
var router = express.Router();
var async = require("async");
var mail = require('../services/mail');
var db = require('../database/database');


router.post('/issuebook', function (req, res, next) {

    var count=0;
    var email = req.body.email;
    var bookIdArray = req.body.bookIdArray;

    var dateOfIssue = new Date();
    var dateOfReturn = new Date();
    dateOfReturn.setDate(dateOfIssue.getDate() + 30);
    var resultArray = [];
    var index = 0;

    function timeConverter(UNIX_timestamp) {
        var a = new Date(UNIX_timestamp);
        var months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
        var year = a.getFullYear();
        var month = months[a.getMonth()];
        var date = a.getDate();
        var hour = a.getHours();
        var min = a.getMinutes();
        var sec = a.getSeconds();
        //YYYY-MM-DD HH:MM:SS
        //var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ;
        var time = year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec;
        return time;
    }

    var resArray=[];
    async.forEach(bookIdArray,function (bookId ,callback) {

        var isBookAvailableQuery = "select *  from PatronTransaction PT where PT.ActionType='ISSUE'  and PT.BookId='" + bookId.bookId + "'";

        db.executeQuery(function (err, results1) {
            if (err) {
                console.log("ERROR: " + error.message);
                throw err;
            } else {
                resultArray = results1;
                index = bookId;
                if (results1.length === 0) {
                    console.log("isBookAvailableQuery");
                    var hasBookCountExceededQuery = "select IssuedBooksCount from Patron where email ='" + email + "'";
                    db.executeQuery(function (err, results2) {
                        if (err) {
                            console.log("ERROR: " + error.message);
                            throw err;
                        } else {
                            if (results2[0].IssuedBooksCount < 9) {


                                var issueBookQuery = "INSERT INTO PatronTransaction (Email,DateOfIssue,DateOfReturn,ActionType,BookId)VALUES('" +
                                    email +
                                    "','" +
                                    timeConverter(dateOfIssue.getTime()) +
                                    "','" +
                                    timeConverter(dateOfReturn.getTime()) +
                                    "','" +
                                    "ISSUE" +
                                    "','" +
                                    bookId.bookId  + "')";

                                db.executeQuery(function (err, results3) {
                                    if (err) {
                                        console.log("ERROR: " + error.message);
                                        throw err;
                                    } else {

                                        var updatePatronQuery = "update Patron set IssuedBooksCount =" + (results2[0].IssuedBooksCount + 1) + "";
                                        db.executeQuery(function (err, results4) {
                                            if (err) {
                                                console.log("ERROR: " + error.message);
                                                throw err;
                                            } else {


                                                var updateBookStatusQuery = "update Books set CurrentStatus = 'CHECKED_OUT' where BookId='" + bookId.bookId  + "'";
                                                db.executeQuery(function (err, results4) {
                                                    if (err) {
                                                        console.log("ERROR: " + error.message);
                                                        throw err;
                                                    } else {
                                                        var mailIndex=2;
                                                        mail.sendMail(req, res,mailIndex,bookId.title,mailIndex, function (err) {
                                                            if (err) {
                                                                console.log("Error while sending email")
                                                            }
                                                            else {
                                                                console.log("Mail successfully sent")
                                                            }
                                                        });


                                                        var res1Json={
                                                            result: "Book Successfully Issued",
                                                            status: 201
                                                        }
                                                        resArray.push(res1Json);
                                                        count++;
                                                        if(count===bookIdArray.length){
                                                            callback(resArray);
                                                        }

                                                    }
                                                }, updateBookStatusQuery);

                                            }
                                        }, updatePatronQuery);


                                    }
                                }, issueBookQuery);


                            } else {

                                var res3Json={
                                    result: "You have issued maximum allowable books. Please return old books to issue new books.",
                                    status: 203
                                }
                                resArray.push(res3Json);
                                count++;
                                if(count===bookIdArray.length){
                                    callback(resArray);
                                }
                                // res.status(203).json({
                                //     result: "You have issued maximum allowable books. Please return old books to issue new books.",
                                //     status: 203
                                // });

                            }
                        }
                    }, hasBookCountExceededQuery);

                } else {

                    var waitListPriorityQuery = "Select ifnull(max(Priority),0) as Priority from WaitListTransaction WLT where WLT.BookId = '" + bookId.bookId + "'";

                    db.executeQuery(function (err, results4) {
                        if (err) {
                            console.log("ERROR: " + error.message);
                            throw err;
                        } else {


                            var waitListBookQuery = "INSERT INTO WaitListTransaction (Email,BookId,Priority)VALUES('" +
                                email +
                                "','" +
                                bookId.bookId +
                                "','" +
                                (results4[0].Priority + 1) + "')";

                            db.executeQuery(function (err, results5) {
                                if (err) {
                                    console.log("ERROR: " + error.message);
                                    throw err;
                                } else {


                                    var updateBookStatusQuery = "update Books set CurrentStatus = 'WAITING' where BookId='" + bookId.bookId  + "'";
                                    db.executeQuery(function (err, results4) {
                                        if (err) {
                                            console.log("ERROR: " + error.message);
                                            throw err;
                                        } else {

                                            var res2Json={
                                                result: "Book is currently not available in library. Your request has been placed in wait list..!!!",
                                                status: 202
                                            };
                                            resArray.push(res2Json);
                                            count++;
                                            if(count===bookIdArray.length){
                                                callback(resArray);
                                            }

                                            // res.status(202).json({
                                            //     result: "Book is currently not available in library. Your request has been placed in wait list..!!!",
                                            //     status: 202
                                            // });

                                        }
                                    }, updateBookStatusQuery);


                                }
                            }, waitListBookQuery);


                        }
                    }, waitListPriorityQuery);

                }

            }
        }, isBookAvailableQuery);

    }, function () {

        res.status(202).json(
            resArray
        );
    });
    // for (var bookId in bookIdArray) {
    //
    //
    //
    // }

});


router.post('/returnbook', function (req, res, next) {

    var count=0;
    var email = req.body.email;
    var bookIdArray = req.body.bookIdArray;

    var dateOfIssue = new Date();
    var dateOfReturn = new Date();
    dateOfReturn.setDate(dateOfIssue.getDate() + 30);

    function timeConverter(UNIX_timestamp) {
        var a = new Date(UNIX_timestamp);
        var months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
        var year = a.getFullYear();
        var month = months[a.getMonth()];
        var date = a.getDate();
        var hour = a.getHours();
        var min = a.getMinutes();
        var sec = a.getSeconds();
        //YYYY-MM-DD HH:MM:SS
        //var time = date + ' ' + month + ' ' + year + ' ' + hour + ':' + min + ':' + sec ;
        var time = year + '-' + month + '-' + date + ' ' + hour + ':' + min + ':' + sec;
        return time;
    }


    var resArray=[];
    async.forEach(bookIdArray,function (bookId ,callback) {

        var waitListPriorityQuery = "Select ifnull(min(Priority),0) as Priority from WaitListTransaction WLT where WLT.BookId = '" + bookId.bookId + "'";

        db.executeQuery(function (err, results1) {
            if (err) {
                console.log("ERROR: " + error.message);
                throw err;
            } else {


                var returnBookQuery = "Delete from PatronTransaction where ActionType='ISSUE'  and BookId='" + bookId.bookId + "'";

                db.executeQuery(function (err, results2) {
                    if (err) {
                        console.log("ERROR: " + error.message);
                        throw err;
                    } else {

                        console.log("===========================================")
                        console.log(results1[0].Priority);
                        console.log("===========================================")

                        if (results1[0].Priority > 0) {


                            var issueBookQuery = "INSERT INTO PatronTransaction (Email,DateOfIssue,DateOfReturn,ActionType,BookId)VALUES('" +
                                email +
                                "','" +
                                timeConverter(dateOfIssue.getTime()) +
                                "','" +
                                timeConverter(dateOfReturn.getTime()) +
                                "','" +
                                "ISSUE" +
                                "','" +
                                bookId.bookId + "')";

                            db.executeQuery(function (err, results3) {
                                if (err) {
                                    console.log("ERROR: " + error.message);
                                    throw err;
                                } else {


                                    var deleteWaitListQuery = "Delete from WaitListTransaction where BookId='" + bookId.bookId + "'";

                                    db.executeQuery(function (err, results4) {
                                        if (err) {
                                            console.log("ERROR: " + error.message);
                                            throw err;
                                        } else {

                                            var selectPatronQuery = "Select * from  Patron where email ='" + email + "'";

                                            db.executeQuery(function (err, results5) {
                                                if (err) {
                                                    console.log("ERROR: " + error.message);
                                                    throw err;
                                                } else {


                                                    var updatePatronQuery = "update Patron set IssuedBooksCount =" + (results5[0].IssuedBooksCount - 1) + "";

                                                    db.executeQuery(function (err, results6) {
                                                        if (err) {
                                                            console.log("ERROR: " + error.message);
                                                            throw err;
                                                        } else {


                                                            var updateBookStatusQuery = "update Books set CurrentStatus = 'CHECKED_OUT' where BookId='" + bookId.bookId + "'";
                                                            db.executeQuery(function (err, results4) {
                                                                if (err) {
                                                                    console.log("ERROR: " + error.message);
                                                                    throw err;
                                                                } else {


                                                                    var res1Json={
                                                                        result: "Your Book has been successfully returned and has been assigned to the next waiting list candidate.",
                                                                        status: 202
                                                                    }
                                                                    resArray.push(res1Json);

                                                                    count++;
                                                                    if(count===bookIdArray.length){
                                                                        callback(resArray);
                                                                    }

                                                                }
                                                            }, updateBookStatusQuery);


                                                        }
                                                    }, updatePatronQuery);

                                                }
                                            }, selectPatronQuery);

                                        }
                                    }, deleteWaitListQuery);

                                }
                            }, issueBookQuery);

                        } else {

                            var selectPatronQuery = "Select * from  Patron where email ='" + email + "'";

                            db.executeQuery(function (err, results5) {
                                if (err) {
                                    console.log("ERROR: " + error.message);
                                    throw err;
                                } else {


                                    var updatePatronQuery = "update Patron set IssuedBooksCount =" + (results5[0].IssuedBooksCount - 1) + "";

                                    db.executeQuery(function (err, results6) {
                                        if (err) {
                                            console.log("ERROR: " + error.message);
                                            throw err;
                                        } else {


                                            var updateBookStatusQuery = "update Books set CurrentStatus = 'AVAILABLE' where BookId='" + bookId.bookId + "'";
                                            db.executeQuery(function (err, results4) {
                                                if (err) {
                                                    console.log("ERROR: " + error.message);
                                                    throw err;
                                                } else {

                                                    var mailIndex=3;
                                                    mail.sendMail(req, res,mailIndex,bookId.title,mailIndex, function (err) {
                                                        if (err) {
                                                            console.log("Error while sending email")
                                                        }
                                                        else {
                                                            console.log("Mail successfully sent")
                                                        }
                                                    });

                                                    var res2Json={
                                                        result: "Your Book has been successfully returned...!!!",
                                                        status: 202
                                                    };


                                                    resArray.push(res2Json);

                                                    count++;
                                                    if(count===bookIdArray.length){
                                                        callback(resArray);
                                                    }

                                                }
                                            }, updateBookStatusQuery);

                                        }
                                    }, updatePatronQuery);

                                }
                            }, selectPatronQuery);

                        }

                    }
                }, returnBookQuery);

            }
        }, waitListPriorityQuery);

    }, function () {

        res.status(202).json(
            resArray
        );

    });


});



router.post('/search', function (req, res, next) {

    var email = req.body.email;

    var searchstring = req.body.searchstring;

    var resultJsonArray =[];

    var searchQuery = "SELECT * FROM Books b where b.Title like '%" + searchstring + "%'";

    console.log("searchQuery" + searchQuery);


    db.executeQuery(function (err, results) {
        if (err) {
            console.log("ERROR: " + error.message);
            throw err;
        } else {
            console.log(results);

            if(results.length>0) {


                for (var i in results) {

                    var resultJson = {};
                    resultJson.title = results[i].Title;
                    resultJson.author = results[i].Author;
                    resultJson.callnumber = results[i].CallNumber;
                    resultJson.publisher = results[i].Publisher;
                    resultJson.yop = results[i].YearOfPublication;
                    resultJson.status = results[i].CurrentStatus;
                    resultJson.keywords = results[i].Keywords;
                    resultJson.image = results[i].CoverageImage;
                    resultJson.location = results[i].LocationInTheLibrary;
                    resultJson.copies = results[i].NumberOfCopies;
                    resultJson.bookId = results[i].BookId;
                    resultJsonArray.push(resultJson);
                    //res.status(200).json(resultJson);
                }
                res.status(201).json(resultJsonArray
                );
            } else {

                res.status(202).json(
                     resultJsonArray
                );
            }
        }
    }, searchQuery);


});

module.exports = router;
