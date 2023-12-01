package com.example.scoket;

import jakarta.persistence.*;

@Entity
public class Socket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Lob
    String img;
}
