package com.boti.mkdigital.productsidentifier.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "category")
@SequenceGenerator(name = "category_seq_gen", sequenceName = "category_id", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "category_seq_gen")
    private Integer id;
    private String category;
}
