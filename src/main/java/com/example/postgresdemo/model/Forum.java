package com.example.postgresdemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "forums")
public class Forum extends AuditModel {
    @Id
    @GeneratedValue(generator = "forum_generator")
    @SequenceGenerator(
            name = "forum_generator",
            sequenceName = "forum_sequence",
            initialValue = 1000
    )
    private Long id;

    @NotBlank
    @Size(min = 3, max = 400)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "forum_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Forum forum;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}