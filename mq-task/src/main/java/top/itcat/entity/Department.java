package top.itcat.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-23
 */
@Data
@Accessors(chain = true)
@Document(indexName = "departments", type = "department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String code;
    private String name;
    /**
     *
     */
    private Integer classification;
    /**
     *
     */
    private String catalog;
}
