package com.example.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class JdbcService {

    private static Connection connection = null;
    private static String url = "jdbc:postgresql://localhost/dcsa_openapi?currentSchema=dcsa_im_v3_0";
    private static String user = "dcsa_db_owner";
    private static String pass = "9c072fe8-c59c-11ea-b8d1-7b6577e9f3f5";

    private final static String ROOM_ID = "ROOM_ID";
    private static long masterKey = -1l;
    private static PreparedStatement prepareInsertInteriorMaster = null;
    private static PreparedStatement prepareInsertInteriorFeature = null;

    private static final String INSERT_INTERIOR_ROOMS_MASTER = "INSERT INTO INTERIOR_ROOMS_MASTER"
            + "(PROGRAM_MARKET, STR_WEEK_FROM, STR_WEEK_TO, PNO12, COLOR, UPHOLSTERY, MODIFIED_DATE, MODIFIED_BY ) "
            + "VALUES(?, ?, ?, ?, ?, ?, SYSDATE,\'CPAMIMPORT\')";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String POSTGRESQL = "INSERT INTO dcsa_im_v3_0.booking (\n" +
            "    carrier_booking_reference,\n" +
            "    receipt_delivery_type_at_origin,\n" +
            "    receipt_delivery_type_at_destination,\n" +
            "    cargo_movement_type_at_origin,\n" +
            "    cargo_movement_type_at_destination,\n" +
            "    booking_request_datetime,\n" +
            "    service_contract,\n" +
            "    cargo_gross_weight,\n" +
            "    cargo_gross_weight_unit,\n" +
            "    commodity_type\n" +
            ") VALUES (\n" +
            "    'BR1239719977',\n" +
            "    'CY',\n" +
            "    'CFS',\n" +
            "    'FCL',\n" +
            "    'LCL',\n" +
            "    DATE '2020-07-07',\n" +
            "    'You will surrender',\n" +
            "    13233.4,\n" +
            "    'KGM',\n" +
            "    'Donno'\n" +
            ")";

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    static PreparedStatement prepareInsertInteriorMaster(Connection connection) {

        String key[] = { ROOM_ID };
        if (prepareInsertInteriorMaster == null) {
            try {
                prepareInsertInteriorMaster = connection.prepareStatement(INSERT_INTERIOR_ROOMS_MASTER, key);
            } catch (SQLException e) {
                String errorMsg = String.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
                System.out.println(errorMsg);
            } catch (Exception ex) {
                System.out.println("Error when insert in master. Handle error " + ex.getMessage());
            }
        }
        return prepareInsertInteriorMaster;
    }

    void insertIntoPostgresql(){

        int rows = jdbcTemplate.update(POSTGRESQL);
        if (rows > 0) {
            System.out.println("A new row has been inserted.");
        }

    }
}
