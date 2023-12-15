package com.finalproject.bttd.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="matching_post")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int post_id;

    private String post_title;
    private String post_context;

    @Temporal(TemporalType.TIMESTAMP)
    private Date post_date;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user_id;


    private String away_id;
    @Column(name = "score")
    private boolean score;

    public Board(String postId) {
    }
}
