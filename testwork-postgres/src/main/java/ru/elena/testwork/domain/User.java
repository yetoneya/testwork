package ru.elena.testwork.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final int LEN = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column
    private String ssoid;
    @Column
    private String ts;
    @Column
    private String grp;
    @Column
    private String type;
    @Column
    private String subtype;
    @Column
    private String url;
    @Column
    private String orgid;
    @Column
    private String formid="";
    @Column
    private String code;
    @Column
    private String ltpa;
    @Column
    private String sudirresponse;
    @Column
    private String ymdh;

    public static User createNewUser(String[] values) {
        if (values.length != LEN) {
            return null;
        } else {
            return new User(0, values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7], values[8], values[9], values[10], values[11]);
        }
    }

}
