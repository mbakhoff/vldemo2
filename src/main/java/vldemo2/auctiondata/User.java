package vldemo2.auctiondata;

import java.util.Date;

public class User {
    public final int id;
    public final String name;
    public final Date memberSince;

    public User(int id, String name, Date memberSince) {
        this.id = id;
        this.name = name;
        this.memberSince = memberSince;
    }
}
