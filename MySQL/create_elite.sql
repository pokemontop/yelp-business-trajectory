create table elite (
	uid		varchar(25),
    bid		varchar(25),
    vdate	date,
    rtext	varchar(5000),
    rstar	float,
    primary key (uid, bid, vdate),
    foreign key (bid) references business(bid)
    );