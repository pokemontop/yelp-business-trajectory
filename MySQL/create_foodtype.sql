create table foodtype (
	bid		varchar(25),
    ftype	varchar(25),
    primary key (bid, ftype),
    foreign key (bid) references business(bid)
    );