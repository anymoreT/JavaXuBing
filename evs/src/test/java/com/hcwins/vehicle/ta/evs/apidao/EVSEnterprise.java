package com.hcwins.vehicle.ta.evs.apidao;

/**
 * Created by xiangzhai on 10/04/15.
 */

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

@Annotations.Entity(table = "EVS_Enterprise", mapper = EVSEnterprise.Mapper.class)
public class EVSEnterprise extends BaseEntity {
    /*
Table: EVS_Enterprise
Columns:
id bigint(20) AI PK
createTime datetime
updateTime datetime
detailAddress varchar(200)
enterpriseName varchar(200)
licenseNumber varchar(10)
licensePic longblob
mail varchar(255)
nature varchar(10)
representative varchar(100)
scale varchar(20)
status varchar(255)
telephone varchar(20)
website varchar(255)
cityId bigint
    */

    protected String detailAddress;
    protected String enterpriseName;
    protected String licenseNumber;
    protected byte[] licensePic;
    protected String mail;
    protected String nature;
    protected String representative;
    protected String scale;
    protected Status status;
    protected String telephone;
    protected String website;
    protected Long cityId;

    public static enum Status {
        UNAUDITED,
        REFUSED,
        AVAILABLE,
        UNAVAILABLE;
    }

    public EVSEnterprise(Long id, Date createTime, Date updateTime, String detailAddress, String enterpriseName, String licenseNumber, byte[] licensePic, String mail, String nature, String representative, String scale, Status status, String telephone, String website, Long cityId) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.detailAddress = detailAddress;
        this.enterpriseName = enterpriseName;
        this.licenseNumber = licenseNumber;
        this.licensePic = licensePic;
        this.mail = mail;
        this.nature = nature;
        this.representative = representative;
        this.scale = scale;
        this.status = status;
        this.telephone = telephone;
        this.website = website;
        this.cityId = cityId;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public byte[] getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(byte[] licensePic) {
        this.licensePic = licensePic;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public static class Mapper implements ResultSetMapper<EVSEnterprise> {
        public EVSEnterprise map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new EVSEnterprise(
                    r.getLong("id"),
                    r.getTimestamp("createTime"),
                    r.getTimestamp("updateTime"),
                    r.getString("detailAddress"),
                    r.getString("enterpriseName"),
                    r.getString("licenseNumber"),
                    r.getBytes("licensePic"),
                    r.getString("mail"),
                    r.getString("nature"),
                    r.getString("representative"),
                    r.getString("scale"),
                    Status.valueOf(r.getString("status")),
                    r.getString("telephone"),
                    r.getString("website"),
                    r.getLong("cityId")
            );
        }
    }

    @Override
    public String toString() {
        return "EVSEnterprise{" +
                "detailAddress='" + detailAddress + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", licensePic=" + Arrays.toString(licensePic) +
                ", mail='" + mail + '\'' +
                ", nature='" + nature + '\'' +
                ", representative='" + representative + '\'' +
                ", scale='" + scale + '\'' +
                ", status=" + status +
                ", telephone='" + telephone + '\'' +
                ", website='" + website + '\'' +
                ", cityId=" + cityId +
                "} " + super.toString();
    }

    public static EVSEnterpriseDao dao = EVSUtil.getDAO(EVSEnterpriseDao.class);
}
