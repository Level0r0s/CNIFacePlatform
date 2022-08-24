package cn.abellee.cniface.platform.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author abel
 * @date 2022/8/20 9:27 PM
 */
@Data
@Entity
@Table(name = "repositories", indexes = {@Index(name = "name", columnList = "create_time")})
@ToString
@EqualsAndHashCode(callSuper = true)
public class RepositoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

}
