

var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'library.cmpe277@gmail.com',
        pass: 'library_cmpe277'
    }
});



function sendMail(req,res,messageIndex,message,code) {
    console.log("inside send mail part");

    var mailOptions;

    switch (messageIndex){
        case 1:
            mailOptions = {
                from: 'library.cmpe277@gmail.com',
                to: req.body.email,
                subject: 'You have registered for cmpe277 Library',
                html: '<h1>Welcome : </h1><p>you have registered to the cmpe277 Library application : CMPE277 rocks!</p>' +
                '<p>Please use the below code to activate your account!</p>'+
                '<code>'+code+'</code>'
            };
            break;


        case 2:
            mailOptions = {
                from: 'library.cmpe277@gmail.com',
                to: req.body.email,
                subject: 'You have issued book from cmpe277 Library',
                html: '<p>You have issued books from the cmpe277 Library</p>' +
                '<p>Here are the book titles that you issued.</p>'+
                '<b>'+message+'</b>'
            };
            break;

        case 3:
            mailOptions = {
                from: 'library.cmpe277@gmail.com',
                to: req.body.email,
                subject: 'You have returned book to cmpe277 Library',
                html: '<p>You have returned books to the cmpe277 Library</p>' +
                '<p>Here are the book titles that you returned.</p>'+
                '<b>'+message+'</b>'
            };
            break;
    }


    transporter.sendMail(mailOptions, function (error, info) {
        if (error) {
            console.log(error);
        } else {
            console.log('Email sent: ' + info.response);

        }
    });
}


exports.sendMail = sendMail;