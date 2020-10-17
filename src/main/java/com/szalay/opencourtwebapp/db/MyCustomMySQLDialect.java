package com.szalay.opencourtwebapp.db;

import org.hibernate.dialect.MySQL8Dialect;

public class MyCustomMySQLDialect extends MySQL8Dialect {

    public String getTableTypeString() {
        return " ENGINE=InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_hungarian_ci";
//                "ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci";

    }
}
