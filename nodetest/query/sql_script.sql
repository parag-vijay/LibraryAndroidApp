create database cmpe277;

use cmpe277;

create table Librarian (Name varchar(256),Email varchar(256),Password varchar(256),UniversityID varchar(256),Books varchar(256),CreationDate datetime DEFAULT CURRENT_TIMESTAMP);
create table Patron (Name varchar(256),Email varchar(256),Password varchar(256),UniversityID varchar(256),Books varchar(256),TotalBooksCount varchar(256),IssuedBooksCount varchar(256),Fine varchar(256),CreationDate datetime DEFAULT CURRENT_TIMESTAMP);

create table LibrarianTransaction (Email varchar(256),DateOfAddition datetime,DateOfRemoval datetime,ActionType  enum ('ADD','REMOVE'));
create table PatronTransaction (Email varchar(256),DateOfIssue datetime,DateOfReturn datetime,ActionType  enum ('ISSUE','RETURN','WAIT'));

create table Books (Author varchar(256),Title varchar(256),CallNumber varchar(256),Publisher varchar(256),YearOfPublication varchar(256),LocationInTheLibrary varchar(256),NumberOfCopies varchar(256),CurrentStatus varchar(256),Keywords varchar(256),CoverageImage varchar(256));




SELECT B1.*
FROM
    cmpe277.LibrarianTransaction  T1
    LEFT JOIN cmpe277.LibrarianTransaction T2 ON T1.BookId=T2.BookId and T1.ActionType="ADD"  and T2.ActionType="REMOVE"  and T1.Email=T2.Email
    INNER JOIN cmpe277.Books  B1 ON B1.BookId = T1.BookId
WHERE
    T2.BookId IS NULL and
    T1.Email="";


    SELECT B1.*
FROM
    cmpe277.PatronTransaction T1
    LEFT JOIN cmpe277.PatronTransaction T2 ON T1.BookId=T2.BookId and T1.ActionType="ADD"  and T2.ActionType="REMOVE"  and T1.Email=T2.Email
    INNER JOIN cmpe277.Books  B1 ON B1.BookId = T1.BookId
WHERE
    T2.BookId IS NULL and
    T1.Email=""