create table business (
	bid				varchar(25),
    bname			varchar(50),
    baddr			varchar(100),
    city			varchar(20),
    state			varchar(20),
    longitude		double,
    latitude		double,
    stars			float,
    review_count	int,
    open			boolean,
    primary key (bid)
    );