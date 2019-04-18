package com.k.menu.common;

import com.k.menu.model.User;

/**
 * Created by Karthik on 30-12-2017.
 */

public class common {
    public static User currentuser;

    public static String converCodeToStatus(String status) {
        if (status.equals("0"))
            return "ORDER PLACED";
        else if (status.equals("1"))
            return "ORDER ON THE WAY";
        else
            return "ORDER SHIPPED";
    }
}
