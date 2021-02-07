package com.swida.documetation.data.entity.storages;

import com.swida.documetation.data.entity.UserCompany;
import com.swida.documetation.data.entity.subObjects.BreedOfTree;
import com.swida.documetation.data.enums.StatusOfEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class DryStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String codeOfProduct;
    @ManyToOne
    private BreedOfTree breedOfTree;
    private String breedDescription="";

    private String sizeOfHeight;
    private String sizeOfWidth;
    private String sizeOfLong;

    private int countOfDesk;

    private String extent;
    private String description;
    private String date;

    private String qualityOfPack;
    private String longOfPack;


    @OneToMany(mappedBy = "dryStorage")
    private List<DescriptionDeskOak> deskOakList;

    @OneToMany(mappedBy = "dryStorage")
    private List<PackagedProduct> packagedProductList;
    @ManyToOne
    private UserCompany userCompany;
    @ManyToOne
    private DryingStorage dryingStorage;
    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;
}
