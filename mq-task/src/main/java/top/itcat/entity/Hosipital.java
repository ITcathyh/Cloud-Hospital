package top.itcat.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;

import java.io.Serializable;

@Data
@Document(indexName = "hosipitals", type = "hosipital")
public class Hosipital implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private long id;
    private String name;
    private String country;
    private String province;
    private String city;
    private String county;

    @Field(index = FieldIndex.no)
    private String phonenum;
    @Field(index = FieldIndex.no)
    private String email;

    private HosiptitalLevel level;
    private String goodat;
}
