package com.boti.mkdigital.productsidentifier.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

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
