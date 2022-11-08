package priv.shiroko.amis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @TableName client
 */
@Data
public class Client implements Serializable, BasicEntity {
    private Integer id;

    private String name;

    private String icNum;

    private String mobile;

    private Double incomePerYear;

    private Double asset;

    private Double debt;

    private String company;

    private String address;

    private String business;

    private Integer managerId;

    private String managerName;

    private static final long serialVersionUID = 1L;
}