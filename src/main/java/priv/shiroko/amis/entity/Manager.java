package priv.shiroko.amis.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import priv.shiroko.amis.utils.BasicEnum;
import priv.shiroko.amis.utils.ExcelEnumNameConverter;
import priv.shiroko.amis.utils.JsonEnumNameSerializer;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @TableName manager
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Manager implements Serializable, BasicEntity {
    @ExcelProperty("编号")
    private Integer id;

    @ExcelIgnore
    private Integer userId;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty(value = "性别", converter = ExcelEnumNameConverter.class)
    @JsonSerialize(using = JsonEnumNameSerializer.class)
    private Sex sex;

    @ExcelProperty("出生日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date birthday;

    @ExcelProperty("年龄")
    private Integer age;

    @ExcelProperty("民族")
    private String ethnic;

    @ExcelProperty("身份证号")
    private String icNum;

    @ExcelProperty("政治面貌")
    private String politicalStatus;

    @ExcelProperty("籍贯")
    private String nativePlace;

    @ExcelIgnore
    private byte[] photo;

    @ExcelProperty("学历")
    private String education;

    @ExcelProperty("学位")
    private String graduated;

    @ExcelProperty("毕业院校")
    private String school;

    @ExcelProperty("职称")
    private String professionalTitle;

    @ExcelProperty("客户经理等级")
    private String managerLevel;

    @ExcelProperty("单位")
    private String unit;

    @ExcelProperty("部门")
    private String dept;

    @ExcelProperty(value = "业务条线", converter = ExcelEnumNameConverter.class)
    @JsonSerialize(using = JsonEnumNameSerializer.class)
    private BusinessLine businessLine;

    @ExcelProperty("职务")
    private String job;

    @ExcelProperty("参加工作时间")
    @DateTimeFormat("yyyy-MM-dd")
    private Date hiredDate;

    @ExcelProperty("入行时间")
    @DateTimeFormat("yyyy-MM-dd")
    private Date enteredDate;

    @ExcelProperty("金融工作年限")
    private Integer financialAgeLimit;

    @ExcelProperty("客户经理从业年限")
    private Integer managerAgeLimit;

    @ExcelProperty("累计学分")
    private Double totalCredits;

    @ExcelProperty("当年学分")
    private Double yearCredits;

    @ExcelProperty("退出时间")
    @DateTimeFormat("yyyy-MM-dd")
    private Date exitDate;

    @ExcelProperty("上年度考核结果")
    private String lastYearAssessment;

    @ExcelProperty("从业资格证编号")
    private String qualificationCertId;

    @ExcelProperty("从业资格证发放日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date qualificationCertDate;

    @ExcelProperty("岗位证书编号")
    private String jobCertId;

    @ExcelProperty("岗位证书日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date jobCertDate;

    @ExcelProperty("手机")
    private String mobile;

    @ExcelProperty("办公电话")
    private String officeTel;

    @ExcelProperty(value = "客户经理状态", converter = ExcelEnumNameConverter.class)
    @JsonSerialize(using = JsonEnumNameSerializer.class)
    private Status managerStatus;

    @ExcelProperty("上次更新时间")
    @DateTimeFormat("yyyy-MM-dd HH:MM:SS")
    private Date lastUpdate;


    @ExcelIgnore
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    public void setBirthday(Date birthday) {
        this.birthday = birthday;

        Calendar now = Calendar.getInstance();
        Calendar born = Calendar.getInstance();
        now.setTime(new Date());
        born.setTime(birthday);
        if (born.after(now)) {
            this.age = null;
        }
        int age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR))
            age--;
        this.age = age;
    }

    @Getter
    public enum Sex implements BasicEnum {
        MALE("male", "男"),
        FEMALE("female", "女");

        private final String value;
        private final String name;

        Sex(String value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    @Getter
    public enum BusinessLine implements BasicEnum {
        BUSINESS("business", "对公"),
        PERSONAL("personal", "个人");

        private final String value;
        private final String name;

        BusinessLine(String value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    @Getter
    public enum Status implements BasicEnum {
        IN_SERVICE("in-service", "在职"),
        OUT_OF_SERVICE("out-of-service", "退出");

        private final String value;
        private final String name;

        Status(String value, String name) {
            this.value = value;
            this.name = name;
        }
    }

}