create table visit (
	bid		varchar(25),
    bdate	date,
    vcount	int,
    primary key (bid, bdate),
    foreign key (bid) references business(bid)
    );