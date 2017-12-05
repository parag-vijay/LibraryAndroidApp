

var nodemailer = require('nodemailer');

var transporter = nodemailer.createTransport({
    service: 'gmail',
    auth: {
        user: 'library.cmpe277@gmail.com',
        pass: 'library_cmpe277'
    }
});



function sendMail(req,res) {
    console.log("inside send mail part");

    var mailOptions = {
        from: 'library.cmpe277@gmail.com',
        to: req.body.email,
        subject: 'You have registered for cmpe277 Library',
        html: '<h1>Welcome :</h1><p>you have registered to the cmpe277 Library application : CMPE277 rocks!</p>' +
        '<p>Please use the below code to activate your account!</p>'+
        '<code>1234</code>'
    };

    transporter.sendMail(mailOptions, function (error, info) {
        if (error) {
            console.log(error);
        } else {
            console.log('Email sent: ' + info.response);

        }
    });
}


exports.sendMail = sendMail;