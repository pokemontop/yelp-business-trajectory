drop table if exists foodtype, elite, visit, business;

create table business (
	bid				varchar(25),
    bname			varchar(100),
    baddr			varchar(250),
    city			varchar(50),
    state			varchar(20),
    longitude		double,
    latitude		double,
    stars			float,
    review_count	int,
    open			boolean,
    primary key (bid)
);
    
create table foodtype (
	bid		varchar(25),
    ftype	varchar(25),
    primary key (bid, ftype),
    foreign key (bid) references business(bid)
);
    
create table elite (
	uid		varchar(25),
    bid		varchar(25),
    vdate	date,
    rtext	varchar(5000),
    rstar	float,
    primary key (uid, bid, vdate),
    foreign key (bid) references business(bid)
);

create table visit (
	bid		varchar(25),
    bdate	date,
    vcount	int,
    primary key (bid, bdate),
    foreign key (bid) references business(bid)
);