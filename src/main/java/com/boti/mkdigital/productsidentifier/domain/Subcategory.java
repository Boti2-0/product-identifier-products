package com.boti.mkdigital.productsidentifier.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@Entity
@Table(name = "sub_category")
@SequenceGenerator(name = "subcategory_seq_gen", sequenceName = "subcategory_id", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {
    @Id
    @GeneratedValue(strategy = IDENTITY, generator = "subcategory_seq_gen")
    private Long id;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @Column(name = "sub_category")
    private String subCategory;
}
