package com.dong.shop.Notice;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@ToString
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;
    public String date;

}
