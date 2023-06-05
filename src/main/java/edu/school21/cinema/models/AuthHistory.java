package edu.school21.cinema.models;

import lombok.Data;

@Data
public class AuthHistory {
    private Long user_id;
    private String time;
    private String type;
    private String address;
}
