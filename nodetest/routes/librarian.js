var express = require('express');
var moment = require('moment');
var router = express.Router();
var db = require('../database/database');
var validator = require('../services/validation');


/* add a new book to DB */
router.post('/add', function (req, res, next) {



    var title = req.body.title;
    var author = req.body.author;
    var callnumber = req.body.callnumber;
    var publisher = req.body.publisher;
    var yop = req.body.yop;
    var location = req.body.location;
    var copy = req.body.copies;
    var status = req.body.status;
    var keywords = req.body.keywords;
    var image = req.body.image;
    var email = req.body.email;
    var actionType = 'ADD';

    var addBookQuery;
    var addLibTxnQuery;
    var updateBookQuery;


    console.log("title is : " + title);
    console.log("author is : " + author);
    console.log("callnumber is : " + callnumber);
    console.log("publisher is : " + publisher);
    console.log("yop is : " + yop);
    console.log("location is : " + location);
    console.log("copies is : " + copy);
    console.log("status is : " + status);
    console.log("keywords is : " + keywords);
    console.log("image is : " + image);

    validator.isBookPresent(function (bookStatus, results) {


        console.log(bookStatus);


        if (bookStatus) {

            var copies1 = results[0].NumberOfCopies+1;

            updateBookQuery = "UPDATE Books SET NumberOfCopies = '"+ copies1 +"' WHERE Author='" + author + "' AND Title='" + title + "'";

            console.log(updateBookQuery);

            db.executeQuery(function (err, results) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {
                    res.status(201).json({
                        result: "Book Updated Successfully",
                        status: 201
                    });

                }
            }, updateBookQuery);


            actionType = 'UPDATE';

            var bookId = results[0].BookId;

            // var now = new Date();

            var mysqlTimestamp = moment(Date.now()).format('YYYY-MM-DD HH:mm:ss');
            console.log("mysqlTimestamp: " + mysqlTimestamp);



            var then = '0000-00-00 00:00:00';

            addLibTxnQuery = "INSERT INTO LibrarianTransaction (Email,DateOfAddition,DateOfRemoval,ActionType,BookId)VALUES('" +
                email +
                "','" +
                mysqlTimestamp +
                "','" +
                then +
                "','" +
                actionType +
                "','" +
                bookId + "')";

            console.log("addLibTxnQuery is:" + addLibTxnQuery);

            db.executeQuery(function (err, results) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {
                    // res.status(201).json({
                    //     result: "Txn Added Successfully",
                    //     status: 201
                    // });

                }
            }, addLibTxnQuery);

        } else {
            //insert thr record

            addBookQuery = "INSERT INTO Books (Author,Title,CallNumber,Publisher,YearOfPublication,LocationInTheLibrary,NumberOfCopies,CurrentStatus,Keywords,CoverageImage)VALUES('" +
                author +
                "','" +
                title +
                "','" +
                callnumber +
                "','" +
                publisher +
                "','" +
                yop +
                "','" +
                location +
                "','" +
                copy +
                "','" +
                status +
                "','" +
                keywords +
                "','" +
                image + "')";

            console.log("addBookQuery is:" + addBookQuery);

            db.executeQuery(function (err, results) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {


                    validator.isBookPresent(function (bookStatus, results) {

                        console.log("boooooooooooooook added: ", results[0].BookId);

                        var bookId = results[0].BookId;

                        // var now = new Date();

                        var mysqlTimestamp = moment(Date.now()).format('YYYY-MM-DD HH:mm:ss');
                        console.log("mysqlTimestamp: " + mysqlTimestamp);



                        var then = '0000-00-00 00:00:00';

                        addLibTxnQuery = "INSERT INTO LibrarianTransaction (Email,DateOfAddition,DateOfRemoval,ActionType,BookId)VALUES('" +
                            email +
                            "','" +
                            mysqlTimestamp +
                            "','" +
                            then +
                            "','" +
                            actionType +
                            "','" +
                            bookId + "')";

                        console.log("addLibTxnQuery is:" + addLibTxnQuery);

                        db.executeQuery(function (err, results) {
                            if (err) {
                                console.log("ERROR: " + error.message);
                                throw err;
                            } else {
                                // res.status(201).json({
                                //     result: "Txn Added Successfully",
                                //     status: 201
                                // });

                            }
                        }, addLibTxnQuery);


                    }, title, author);



                    res.status(201).json({
                        result: "Book Added Successfully",
                        status: 201
                    });

                }
            }, addBookQuery);
        }

    }, title, author);

});

/* update a book entry*/

router.post('/update', function (req, res, next) {


    var title = req.body.title;
    var author = req.body.author;
    var callnumber = req.body.callnumber;
    var publisher = req.body.publisher;
    var yop = req.body.yop;
    var location = req.body.location;
    var copy = req.body.copies;
    var status = req.body.status;
    var keywords = req.body.keywords;
    var image = req.body.image;
    var email = req.body.email;
    var bookId = req.body.bookId;
    var actionType = req.body.actionType;


    console.log("Hi this is my body ::::::::::::::::::::::::::",req.body);

    var updateBookQuery;

    console.log("title is : " + title);
    console.log("author is : " + author);
    console.log("callnumber is : " + callnumber);
    console.log("publisher is : " + publisher);
    console.log("yop is : " + yop);
    console.log("location is : " + location);
    console.log("copies is : " + copy);
    console.log("status is : " + status);
    console.log("keywords is : " + keywords);
    console.log("image is : " + image);

    updateBookQuery = "UPDATE Books SET Author='"+author+"', Title='"+title+"'," +
        "CallNumber='"+callnumber+"'," +
        "Publisher='"+publisher+"', " +
        "YearOfPublication='"+ yop+ "'," +
        "NumberOfCopies = '"+ copy +"'," +
        "CurrentStatus = '"+ status +"'," +
        "Keywords = '"+ keywords +"'," +
        "CoverageImage = '"+ image +"' WHERE BookId='" + bookId + "'";

    console.log("updateBookQuery: " +updateBookQuery);

    db.executeQuery(function (err, results) {
        if (err) {
            console.log("ERROR: " + error.message);
            throw err;
        } else {


            validator.isBookPresent(function (bookStatus, results) {

                console.log("results added: ", results[0].BookId);

                var bookId = results[0].BookId;

                // var now = new Date();

                console.log("booooook ID" +bookId);


                var mysqlTimestamp = moment(Date.now()).format('YYYY-MM-DD HH:mm:ss');
                console.log("mysqlTimestamp: " + mysqlTimestamp);

                var actionType = 'UPDATE';

                var then = '0000-00-00 00:00:00';

                addLibTxnQuery = "UPDATE LibrarianTransaction set ActionType = '" + actionType + "' where BookId='"+ bookId+"'";

                console.log("addLibTxnQuery is:" + addLibTxnQuery);

                db.executeQuery(function (err, results) {
                    if (err) {
                        console.log("ERROR: " + error.message);
                        throw err;
                    } else {
                        console.log("update book successful in librarian table");

                    }
                }, addLibTxnQuery);



            }, title, author);


            res.status(201).json({
                result: "Book updated Successfully",
                status: 201
            });

        }
    }, updateBookQuery);









});

router.post('/delete', function (req, res, next) {

    var email = req.body.email;

    var bookId = req.body.bookId;
    var issueStatus = 'ISSUE';

    validator.isBookIssued(function (bookingStatus, results) {

        if(bookingStatus){
            res.status(201).json({
                result: "Book issued cannot be deleted!",
                status: 201
            });
        }
        else{

            var deleteBookQuery;

            deleteBookQuery= "DELETE FROM Books WHERE BookId='" + bookId + "'";

            db.executeQuery(function (err, results) {
                if (err) {
                    console.log("ERROR: " + error.message);
                    throw err;
                } else {

                    var mysqlTimestamp = moment(Date.now()).format('YYYY-MM-DD HH:mm:ss');
                    console.log("mysqlTimestamp: " + mysqlTimestamp);

                    var actionType = 'REMOVE';

                    var then = '0000-00-00 00:00:00';

                    addLibTxnQuery = "INSERT INTO LibrarianTransaction (Email,DateOfAddition,DateOfRemoval,ActionType,BookId)VALUES('" +
                        email +
                        "','" +
                        mysqlTimestamp +
                        "','" +
                        then +
                        "','" +
                        actionType +
                        "','" +
                        bookId + "')";

                    console.log("addLibTxnQuery is:" + addLibTxnQuery);

                    db.executeQuery(function (err, results) {
                        if (err) {
                            console.log("ERROR: " + error.message);
                            throw err;
                        } else {
                            console.log("delete book successful in librarian table");

                        }
                    }, addLibTxnQuery);













                    res.status(201).json({
                        result: "Book deleted Successfully",
                        status: 201
                    });

                }
            }, deleteBookQuery);
        }

    }, bookId, issueStatus);


});

router.post('/search', function (req, res, next) {

    var email = req.body.email;

    var searchstring = req.body.searchstring;

    var resultJsonArray =[];

    var searchQuery = "SELECT * FROM Books b,LibrarianTransaction l where b.Title like '%" + searchstring + "%' and l.BookId=b.BookId and l.email = '" + email + "' and l.ActionType <> 'REMOVE'";

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
                res.status(201).json(resultJsonArray);
            } else {

                res.status(202).json(resultJsonArray);
            }
        }
    }, searchQuery);


});
module.exports = router;
