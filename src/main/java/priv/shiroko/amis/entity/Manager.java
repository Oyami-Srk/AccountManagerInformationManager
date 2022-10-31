package priv.shiroko.amis.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName manager
 */
public class Manager implements Serializable {
    private Integer id;

    private Integer userId;

    private String name;

    private Object sex;

    private Date birthday;

    private Object ethnic;

    private Object politicalStatus;

    private String nativePlace;

    private String photo;

    private Object education;

    private Object graduated;

    private Object professionalTitle;

    private Object managerLevel;

    private String unit;

    private String dept;

    private Object businessLine;

    private String job;

    private Date hiredDate;

    private Date enteredDate;

    private Integer financialAgeLimit;

    private Integer managerAgeLimit;

    private Double totalCredits;

    private Double yearCredits;

    private Date exitDate;

    private String lastYearAssessment;

    private String qualificationCertId;

    private Date qualificationCertDate;

    private String jobCertId;

    private Date jobCertDate;

    private String mobile;

    private String officeTel;

    private Object managerStatus;

    private Date lastUpdate;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Object getEthnic() {
        return ethnic;
    }

    public void setEthnic(Object ethnic) {
        this.ethnic = ethnic;
    }

    public Object getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(Object politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Object getEducation() {
        return education;
    }

    public void setEducation(Object education) {
        this.education = education;
    }

    public Object getGraduated() {
        return graduated;
    }

    public void setGraduated(Object graduated) {
        this.graduated = graduated;
    }

    public Object getProfessionalTitle() {
        return professionalTitle;
    }

    public void setProfessionalTitle(Object professionalTitle) {
        this.professionalTitle = professionalTitle;
    }

    public Object getManagerLevel() {
        return managerLevel;
    }

    public void setManagerLevel(Object managerLevel) {
        this.managerLevel = managerLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Object getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(Object businessLine) {
        this.businessLine = businessLine;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Date getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(Date enteredDate) {
        this.enteredDate = enteredDate;
    }

    public Integer getFinancialAgeLimit() {
        return financialAgeLimit;
    }

    public void setFinancialAgeLimit(Integer financialAgeLimit) {
        this.financialAgeLimit = financialAgeLimit;
    }

    public Integer getManagerAgeLimit() {
        return managerAgeLimit;
    }

    public void setManagerAgeLimit(Integer managerAgeLimit) {
        this.managerAgeLimit = managerAgeLimit;
    }

    public Double getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(Double totalCredits) {
        this.totalCredits = totalCredits;
    }

    public Double getYearCredits() {
        return yearCredits;
    }

    public void setYearCredits(Double yearCredits) {
        this.yearCredits = yearCredits;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getLastYearAssessment() {
        return lastYearAssessment;
    }

    public void setLastYearAssessment(String lastYearAssessment) {
        this.lastYearAssessment = lastYearAssessment;
    }

    public String getQualificationCertId() {
        return qualificationCertId;
    }

    public void setQualificationCertId(String qualificationCertId) {
        this.qualificationCertId = qualificationCertId;
    }

    public Date getQualificationCertDate() {
        return qualificationCertDate;
    }

    public void setQualificationCertDate(Date qualificationCertDate) {
        this.qualificationCertDate = qualificationCertDate;
    }

    public String getJobCertId() {
        return jobCertId;
    }

    public void setJobCertId(String jobCertId) {
        this.jobCertId = jobCertId;
    }

    public Date getJobCertDate() {
        return jobCertDate;
    }

    public void setJobCertDate(Date jobCertDate) {
        this.jobCertDate = jobCertDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public Object getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(Object managerStatus) {
        this.managerStatus = managerStatus;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Manager other = (Manager) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getEthnic() == null ? other.getEthnic() == null : this.getEthnic().equals(other.getEthnic()))
            && (this.getPoliticalStatus() == null ? other.getPoliticalStatus() == null : this.getPoliticalStatus().equals(other.getPoliticalStatus()))
            && (this.getNativePlace() == null ? other.getNativePlace() == null : this.getNativePlace().equals(other.getNativePlace()))
            && (this.getPhoto() == null ? other.getPhoto() == null : this.getPhoto().equals(other.getPhoto()))
            && (this.getEducation() == null ? other.getEducation() == null : this.getEducation().equals(other.getEducation()))
            && (this.getGraduated() == null ? other.getGraduated() == null : this.getGraduated().equals(other.getGraduated()))
            && (this.getProfessionalTitle() == null ? other.getProfessionalTitle() == null : this.getProfessionalTitle().equals(other.getProfessionalTitle()))
            && (this.getManagerLevel() == null ? other.getManagerLevel() == null : this.getManagerLevel().equals(other.getManagerLevel()))
            && (this.getUnit() == null ? other.getUnit() == null : this.getUnit().equals(other.getUnit()))
            && (this.getDept() == null ? other.getDept() == null : this.getDept().equals(other.getDept()))
            && (this.getBusinessLine() == null ? other.getBusinessLine() == null : this.getBusinessLine().equals(other.getBusinessLine()))
            && (this.getJob() == null ? other.getJob() == null : this.getJob().equals(other.getJob()))
            && (this.getHiredDate() == null ? other.getHiredDate() == null : this.getHiredDate().equals(other.getHiredDate()))
            && (this.getEnteredDate() == null ? other.getEnteredDate() == null : this.getEnteredDate().equals(other.getEnteredDate()))
            && (this.getFinancialAgeLimit() == null ? other.getFinancialAgeLimit() == null : this.getFinancialAgeLimit().equals(other.getFinancialAgeLimit()))
            && (this.getManagerAgeLimit() == null ? other.getManagerAgeLimit() == null : this.getManagerAgeLimit().equals(other.getManagerAgeLimit()))
            && (this.getTotalCredits() == null ? other.getTotalCredits() == null : this.getTotalCredits().equals(other.getTotalCredits()))
            && (this.getYearCredits() == null ? other.getYearCredits() == null : this.getYearCredits().equals(other.getYearCredits()))
            && (this.getExitDate() == null ? other.getExitDate() == null : this.getExitDate().equals(other.getExitDate()))
            && (this.getLastYearAssessment() == null ? other.getLastYearAssessment() == null : this.getLastYearAssessment().equals(other.getLastYearAssessment()))
            && (this.getQualificationCertId() == null ? other.getQualificationCertId() == null : this.getQualificationCertId().equals(other.getQualificationCertId()))
            && (this.getQualificationCertDate() == null ? other.getQualificationCertDate() == null : this.getQualificationCertDate().equals(other.getQualificationCertDate()))
            && (this.getJobCertId() == null ? other.getJobCertId() == null : this.getJobCertId().equals(other.getJobCertId()))
            && (this.getJobCertDate() == null ? other.getJobCertDate() == null : this.getJobCertDate().equals(other.getJobCertDate()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getOfficeTel() == null ? other.getOfficeTel() == null : this.getOfficeTel().equals(other.getOfficeTel()))
            && (this.getManagerStatus() == null ? other.getManagerStatus() == null : this.getManagerStatus().equals(other.getManagerStatus()))
            && (this.getLastUpdate() == null ? other.getLastUpdate() == null : this.getLastUpdate().equals(other.getLastUpdate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getEthnic() == null) ? 0 : getEthnic().hashCode());
        result = prime * result + ((getPoliticalStatus() == null) ? 0 : getPoliticalStatus().hashCode());
        result = prime * result + ((getNativePlace() == null) ? 0 : getNativePlace().hashCode());
        result = prime * result + ((getPhoto() == null) ? 0 : getPhoto().hashCode());
        result = prime * result + ((getEducation() == null) ? 0 : getEducation().hashCode());
        result = prime * result + ((getGraduated() == null) ? 0 : getGraduated().hashCode());
        result = prime * result + ((getProfessionalTitle() == null) ? 0 : getProfessionalTitle().hashCode());
        result = prime * result + ((getManagerLevel() == null) ? 0 : getManagerLevel().hashCode());
        result = prime * result + ((getUnit() == null) ? 0 : getUnit().hashCode());
        result = prime * result + ((getDept() == null) ? 0 : getDept().hashCode());
        result = prime * result + ((getBusinessLine() == null) ? 0 : getBusinessLine().hashCode());
        result = prime * result + ((getJob() == null) ? 0 : getJob().hashCode());
        result = prime * result + ((getHiredDate() == null) ? 0 : getHiredDate().hashCode());
        result = prime * result + ((getEnteredDate() == null) ? 0 : getEnteredDate().hashCode());
        result = prime * result + ((getFinancialAgeLimit() == null) ? 0 : getFinancialAgeLimit().hashCode());
        result = prime * result + ((getManagerAgeLimit() == null) ? 0 : getManagerAgeLimit().hashCode());
        result = prime * result + ((getTotalCredits() == null) ? 0 : getTotalCredits().hashCode());
        result = prime * result + ((getYearCredits() == null) ? 0 : getYearCredits().hashCode());
        result = prime * result + ((getExitDate() == null) ? 0 : getExitDate().hashCode());
        result = prime * result + ((getLastYearAssessment() == null) ? 0 : getLastYearAssessment().hashCode());
        result = prime * result + ((getQualificationCertId() == null) ? 0 : getQualificationCertId().hashCode());
        result = prime * result + ((getQualificationCertDate() == null) ? 0 : getQualificationCertDate().hashCode());
        result = prime * result + ((getJobCertId() == null) ? 0 : getJobCertId().hashCode());
        result = prime * result + ((getJobCertDate() == null) ? 0 : getJobCertDate().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getOfficeTel() == null) ? 0 : getOfficeTel().hashCode());
        result = prime * result + ((getManagerStatus() == null) ? 0 : getManagerStatus().hashCode());
        result = prime * result + ((getLastUpdate() == null) ? 0 : getLastUpdate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", ethnic=").append(ethnic);
        sb.append(", politicalStatus=").append(politicalStatus);
        sb.append(", nativePlace=").append(nativePlace);
        sb.append(", photo=").append(photo);
        sb.append(", education=").append(education);
        sb.append(", graduated=").append(graduated);
        sb.append(", professionalTitle=").append(professionalTitle);
        sb.append(", managerLevel=").append(managerLevel);
        sb.append(", unit=").append(unit);
        sb.append(", dept=").append(dept);
        sb.append(", businessLine=").append(businessLine);
        sb.append(", job=").append(job);
        sb.append(", hiredDate=").append(hiredDate);
        sb.append(", enteredDate=").append(enteredDate);
        sb.append(", financialAgeLimit=").append(financialAgeLimit);
        sb.append(", managerAgeLimit=").append(managerAgeLimit);
        sb.append(", totalCredits=").append(totalCredits);
        sb.append(", yearCredits=").append(yearCredits);
        sb.append(", exitDate=").append(exitDate);
        sb.append(", lastYearAssessment=").append(lastYearAssessment);
        sb.append(", qualificationCertId=").append(qualificationCertId);
        sb.append(", qualificationCertDate=").append(qualificationCertDate);
        sb.append(", jobCertId=").append(jobCertId);
        sb.append(", jobCertDate=").append(jobCertDate);
        sb.append(", mobile=").append(mobile);
        sb.append(", officeTel=").append(officeTel);
        sb.append(", managerStatus=").append(managerStatus);
        sb.append(", lastUpdate=").append(lastUpdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}