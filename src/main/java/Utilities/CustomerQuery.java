package Utilities;


import Model.Customer;
import Model.User;
import java.sql.*;

import Model.smsValidatorModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.Instant;

import static Model.Customer.customers;

import static Model.User.users;
import static Model.smsValidatorModel.smsValidatorModelObservableList;
import static View_Controller.LoginController.currentUser;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CustomerQuery {
    //customer View String
    public static final String customerView = "CREATE OR REPLACE VIEW CustomerView AS SELECT c.customerId, c.customerName, c.active as smsStatus,c.lastUpdate,c.lastUpdateBy, a.address, a.address2, a.phone, a.postalCode, a.addressId, ci.cityId, ci.city, co.countryId, co.country from customer c INNER JOIN address a ON c.addressId = a.addressId INNER JOIN city ci ON a.cityId = ci.cityId INNER JOIN country co ON ci.countryId = co.countryId";
    ;

    public static final String messageView = "CREATE OR REPLACE VIEW messageView AS SELECT c.customerName, c.customerId, c.active as smsRemind, c.lastUpdateBy, a.phone, ap.appointmentId, ap.start,ap.title,ap.contact from customer c LEFT OUTER JOIN address a ON c.addressId = a.addressId LEFT OUTER JOIN appointment ap ON c.customerId = ap.customerId";


    public static final String messageView2 = "CREATE OR REPLACE VIEW smsView AS SELECT c.customerId, c.customerName, c.active as smsStatus,c.lastUpdate,c.lastUpdateBy, a.address, a.address2, a.phone, a.postalCode, a.addressId, ci.cityId, ci.city, co.countryId, co.country, ap.appointmentId, ap.createDate from customer c INNER JOIN address a ON c.addressId = a.addressId INNER JOIN city ci ON a.cityId = ci.cityId INNER JOIN country co ON ci.countryId = co.countryId Right OUTER JOIN appointment ap ON c.customerId = ap.customerId";


    static int oldCountryId;

    //creates view for customers
    public static void createCustomerView() throws SQLException {
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(customerView)) {
            statement.execute();
        }

    }

    public static void createMessageView() throws SQLException {
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(messageView)) {
            statement.execute();
        }

    }
    public static void createMessageView2() throws SQLException {
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(    messageView2
        )) {
            statement.execute();
        }

    }

    /*    public static ObservableList<smsValidatorModel> getCustomersForSMS() throws SQLException {
            String query13 = "select * from messageView where customerName =? and appointmentId =?";
            try (PreparedStatement statement = DBConnection.connection.prepareStatement(query13)) {
                createMessageView();
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        smsValidatorModel customer = new smsValidatorModel();
                        customer.setCustName(rs.getString("customerName"));
                        customer.setAppointmentId(rs.getInt("appointmentId"));
                        customer.setPhone(rs.getString("phone"));
                        customer.setStartFull(rs.getString("start"));
                        smsValidatorModelObservableList.add(customer);
                    }
                    return smsValidatorModelObservableList;
                }
            }
        }*/
    public static ObservableList<smsValidatorModel> getCustomersForSMS(String customerName, Integer customerID) throws SQLException {
        String query13 = "select * from messageView where customerName = ? and customerId = ?";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query13)) {
            createMessageView();
            statement.setString(1, customerName);
            statement.setInt(2, customerID);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    smsValidatorModel customer = new smsValidatorModel();
                    customer.setCustName(rs.getString("customerName"));
                    customer.setAppointmentId(rs.getInt("appointmentId"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setStartFull(rs.getString("start"));
                    customer.setSmsActive(rs.getInt("smsRemind"));
                    customer.setCustomerId(rs.getInt("customerId"));
                    customer.setReplyBody(rs.getString("title"));
                    customer.setReplyDate(rs.getString("contact"));
                    customer.setCustomerId(rs.getInt("customerId"));
                    customer.setSmsId(rs.getString("lastUpdateBy"));
                    smsValidatorModelObservableList.add(customer);

                }
                return smsValidatorModelObservableList;
            }

        }

    }

    public static ObservableList<smsValidatorModel> getAllSMSValidatorModelCust() throws SQLException {
        String query13 = "select * from messageView";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query13)) {
            createMessageView();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    smsValidatorModel customer = new smsValidatorModel();
                    customer.setCustName(rs.getString("customerName"));
                    customer.setAppointmentId(rs.getInt("appointmentId"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setStartFull(rs.getString("start"));
                    customer.setSmsActive(rs.getInt("smsRemind"));
                    customer.setCustomerId(rs.getInt("customerId"));
                    customer.setReplyBody(rs.getString("title"));
                    customer.setReplyDate(rs.getString("contact"));
                    customer.setCustomerId(rs.getInt("customerId"));
                    customer.setSmsId(rs.getString("lastUpdateBy"));
                    smsValidatorModelObservableList.add(customer);

                }
                return smsValidatorModelObservableList;
            }

        }

    }


    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String query13 = "select customerId,customerName,address,address2,phone,addressId,postalCode,cityId,city,countryId,country,smsStatus from CustomerView";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query13)) {
            createCustomerView();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(rs.getInt("customerId"));
                    customer.setCustomerName(rs.getString("customerName"));
                    customer.setAddressId(rs.getInt("addressId"));
                    customer.setAddress(rs.getString("address"));
                    customer.setAddress2(rs.getString("address2"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setPostalCode(rs.getString("postalCode"));
                    customer.setCityId(rs.getInt("cityId"));
                    customer.setCity(rs.getString("city"));
                    customer.setCountryId(rs.getInt("countryId"));
                    customer.setCountry(rs.getString("country"));
                    customer.setSmsActive(rs.getInt("smsStatus"));
                    customers.add(customer);
                }
                return customers;
            }
        }
    }

    public static ObservableList<Customer> getAllCustomersNameId() throws SQLException {
        String query13 = "select customerId,customerName from CustomerView";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query13)) {
            createCustomerView();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setCustomerId(rs.getInt("customerId"));
                    customer.setCustomerName(rs.getString("customerName"));
                    customers.add(customer);
                }
                return customers;
            }
        }
    }


    public static ObservableList<User> getAllUsers() throws SQLException {
        String query7 = "select * from user";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query7);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("userId"));
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            return users;
        }
    }

    public static ObservableList<String> consultantList() throws SQLException {
        ObservableList<String> consultantList = FXCollections.observableArrayList();
        String query8 = "select * from user";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query8);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                String consultantName = rs.getString("userName");
                consultantList.add(consultantName);
            }
            return consultantList;
        }
    }

    public static Customer addCountry(Customer customer) throws SQLException {
        oldCountryId = customer.getCountryId();
        String query9 = "Select * from country where country=?";
        String query10 = "INSERT INTO country (country, createDate,createdBy,lastUpdate,lastUpdateBy) VALUES(?,?,?,?,?)";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query9, RETURN_GENERATED_KEYS);
             PreparedStatement statement2 = (PreparedStatement) DBConnection.connection.prepareStatement(query10, RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getCountry());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int countryId = rs.getInt("countryId");
                    customer.setCountryId(countryId);
                    return customer;
                } else {
                    statement2.setString(1, customer.getCountry());
                    statement2.setTimestamp(2, Timestamp.from(Instant.now()));
                    statement2.setString(3, currentUser);
                    statement2.setTimestamp(4, Timestamp.from(Instant.now()));
                    statement2.setString(5, currentUser);
                    statement2.executeUpdate();
                    addCountry(customer);

                }
                return customer;
            }
        }

    }


    public static Customer addCity(Customer customer) throws SQLException {
        String query11 = "Select * from city where city=? AND countryId=?";
        String query12 = "INSERT INTO city (city,countryId,createDate,createdBy,lastUpdate,lastUpdateBy) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query11, RETURN_GENERATED_KEYS);
             PreparedStatement statement2 = (PreparedStatement) DBConnection.connection.prepareStatement(query12, RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getCity());
            statement.setString(2, Integer.toString(customer.getCountryId()));

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int cityId = rs.getInt(1);
                    customer.setCityId(cityId);
                    return customer;
                } else {
                    statement2.setString(1, customer.getCity());
                    statement2.setInt(2, customer.getCountryId());
                    statement2.setTimestamp(3, Timestamp.from(Instant.now()));
                    statement2.setString(4, currentUser);
                    statement2.setTimestamp(5, Timestamp.from(Instant.now()));
                    statement2.setString(6, currentUser);
                    statement2.executeUpdate();
                    addCity(customer);

                }
                return customer;
            }
        }

    }

    public static Customer addAddress(Customer customer) throws SQLException {
        String query15 = "SELECT* FROM address WHERE address=? AND address2=? AND postalCode=? AND cityId=?";
        String query16 = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query15, RETURN_GENERATED_KEYS);
             PreparedStatement statement2 = (PreparedStatement) DBConnection.connection.prepareStatement(query16, RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getAddress());
            statement.setString(2, customer.getAddress2());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, Integer.toString(customer.getCityId()));
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int addressId = rs.getInt(1);
                    customer.setAddressId(addressId);
                    return customer;
                } else {
                    statement2.setString(1, customer.getAddress());
                    statement2.setString(2, customer.getAddress2());
                    statement2.setInt(3, customer.getCityId());
                    statement2.setString(4, customer.getPostalCode());
                    statement2.setString(5, customer.getPhone());
                    statement2.setTimestamp(6, Timestamp.from(Instant.now()));
                    statement2.setString(7, currentUser);
                    statement2.setString(8, currentUser);
                    statement2.executeUpdate();
                    addAddress(customer);

                }
                return customer;
            }
        }

    }

    public static void addCustomer(Customer customer) throws SQLException {
        String query17 = "UPDATE country SET country=?, createDate=?, createdBy=?, lastUpdate=?, lastUpdateBy=? WHERE countryId=?";
        String query18 = "UPDATE city SET city=?, countryId=?, createDate=?, createdBy=?, lastUpdate=?, lastUpdateBy=? WHERE cityId=? AND countryId=?";
        String query19 = "UPDATE address SET address=?, address2=?, cityId=?, postalCode=?,phone=?, createDate=?, createdBy=?, lastUpdate=?, lastUpdateBy=? WHERE addressId=?";
        String query20 = "UPDATE customer SET customerName=?, addressId=?,`active`=?, lastUpdate=?,lastUpdateBy=? WHERE customerId=?";
        String query21 = "INSERT INTO customer  (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement statement1 = DBConnection.connection.prepareStatement(query17);
             PreparedStatement statement2 = DBConnection.connection.prepareStatement(query18);
             PreparedStatement statement3 = DBConnection.connection.prepareStatement(query19);
             PreparedStatement statement4 = DBConnection.connection.prepareStatement(query20);
             PreparedStatement statement5 = DBConnection.connection.prepareStatement(query21)) {
            if (customer.getCustomerId() > 0) {
                addCountry(customer);
                addCity(customer);
                addAddress(customer);

                statement1.setString(1, customer.getCountry());
                statement1.setTimestamp(2, Timestamp.from(Instant.now()));
                statement1.setString(3, currentUser);
                statement1.setTimestamp(4, Timestamp.from(Instant.now()));
                statement1.setString(5, currentUser);
                statement1.setInt(6, customer.getCountryId());
                statement1.executeUpdate();

                statement2.setString(1, customer.getCity());
                statement2.setInt(2, customer.getCountryId());
                statement2.setTimestamp(3, Timestamp.from(Instant.now()));
                statement2.setString(4, currentUser);
                statement2.setTimestamp(5, Timestamp.from(Instant.now()));
                statement2.setString(6, currentUser);
                statement2.setInt(7, customer.getCityId());
                statement2.setInt(8, oldCountryId);
                statement2.executeUpdate();


                statement3.setString(1, customer.getAddress());
                statement3.setString(2, customer.getAddress2());
                statement3.setInt(3, customer.getCityId());
                statement3.setString(4, customer.getPostalCode());
                statement3.setString(5, customer.getPhone());
                statement3.setTimestamp(6, Timestamp.from(Instant.now()));
                statement3.setString(7, currentUser);
                statement3.setTimestamp(8, Timestamp.from(Instant.now()));
                statement3.setString(9, currentUser);
                statement3.setInt(10, customer.getAddressId());
                statement3.executeUpdate();


                statement4.setString(1, customer.getCustomerName());
                statement4.setInt(2, customer.getAddressId());
                statement4.setInt(3, customer.getSmsActive());
                statement4.setTimestamp(4, Timestamp.from(Instant.now()));
                statement4.setString(5, "");
                statement4.setInt(6, customer.getCustomerId());

                statement4.executeUpdate();


            }
            if (customer.getCustomerId() == 0 || customer.getCustomerId() < 0) {
                addCountry(customer);
                addCity(customer);
                addAddress(customer);
                statement5.setString(1, customer.getCustomerName());
                statement5.setInt(2, customer.getAddressId());
                statement5.setInt(3, customer.getSmsActive());
                statement5.setTimestamp(4, Timestamp.from(Instant.now())
                );
                statement5.setString(5, currentUser);
                statement5.setTimestamp(6, Timestamp.from(Instant.now())
                );
                statement5.setString(7, "message id");
                statement5.executeUpdate();
            } else {
                customer.getCustomerId();
            }
        }
    }

    public static ObservableList<String> customerList() throws SQLException {
        ObservableList<String> custNameList = FXCollections.observableArrayList();
        String query22 = "select customerName,customerId from customer";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query22);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                String customerName = rs.getString("customerName");
                Integer customerId = rs.getInt("customerId");
                custNameList.add(customerName);
            }
            return custNameList;
        }
    }

    public static String custPhone(int addressId) throws SQLException {
        String customerPhone = null;
        String query22 = "select phone from address where addressId =?";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query22);
             ResultSet rs = statement.executeQuery()) {
            statement.setInt(1, addressId);
            while (rs.next()) {
                customerPhone = rs.getString("phone").toString();
            }

            return customerPhone;
        }
    }

    public static int customerAddressId(String custName) throws SQLException {
        int customerId = 0;
        String query22 = "select addressId from customer where customerName =?";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query22);
             ResultSet rs = statement.executeQuery()) {
            statement.setString(1, custName);
            while (rs.next()) {
                customerId = rs.getInt("addressId");
            }

            return customerId;
        }
    }

    public static void deleteCustomer(Customer customer) throws SQLException {
        String query23 = "DELETE customer.* FROM customer WHERE customer.customerId = ?";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query23)) {
            statement.setInt(1, customer.getCustomerId());
            statement.execute();
        }
    }

    public static void addMessageId(String messageId, Integer customerId) throws SQLException {
        String query23 = "UPDATE customer SET lastUpdateBy =? WHERE customer.customerId = ?";
        try (PreparedStatement statement = (PreparedStatement) DBConnection.connection.prepareStatement(query23)) {
            statement.setString(1, messageId);
            statement.setInt(2, customerId);
            statement.execute();
        }
    }

    public static int matchMessageIdAndCust(String phone) throws SQLException {
        int appointmentId = 0;
        createMessageView2();
        String query23 = "SELECT appointmentId from smsView WHERE phone=? Order By createDate DESC Limit 1";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query23)) {
            statement.setString(1, phone);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    appointmentId = rs.getInt("appointmentId");
                }
                return appointmentId;
            }
        }




    }
    public static int matchCustomerIdToAddressId(int customerId ) throws SQLException {
        int addressId = 0;
        createCustomerView();
        String query23 = "SELECT addressId from customer WHERE customerId=?";
        try (PreparedStatement statement = DBConnection.connection.prepareStatement(query23)) {
            statement.setInt(1, customerId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    addressId = rs.getInt("addressId");
                }
                return addressId;
            }
        }
    }


        public static void getReplyCust (String replyBody, String replyDate,int appointmentId) throws SQLException {
            String query13 = "update appointment set title =?, contact =? where appointmentId=?";
            try (PreparedStatement statement = DBConnection.connection.prepareStatement(query13)) {
                statement.setString(1, replyBody);
                statement.setString(2, replyDate);
                statement.setInt(3, appointmentId);
                statement.execute();
            }

        }
    }





