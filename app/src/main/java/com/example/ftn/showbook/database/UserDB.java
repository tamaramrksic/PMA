package com.example.ftn.showbook.database;

public class UserDB {
    public static final String TABLE_NAME = "users";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_MAXDISTANCE = "max_distance";
    public static final String COLUMN_FACILITYTYPE = "facility_type";
    public static final String COLUMN_COMMENTS = "comments";


    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private Integer maxDistance;
    private String facilityType;
    private boolean comments;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " LONG PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_FIRSTNAME + " TEXT,"
                    + COLUMN_LASTNAME + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_MAXDISTANCE + " INTEGER,"
                    + COLUMN_FACILITYTYPE + " TEXT,"
                    + COLUMN_COMMENTS + " BOOLEAN,"
                    + ")";

    public UserDB() {
    }

    public UserDB(Long id, String username, String firstName, String lastName, String address,
                  Integer maxDistance, String facilityType, boolean comments) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.maxDistance = maxDistance;
        this.facilityType = facilityType;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Integer maxDistance) {
        this.maxDistance = maxDistance;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public boolean isComments() {
        return comments;
    }

    public void setComments(boolean comments) {
        this.comments = comments;
    }
}
