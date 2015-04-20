package com.hcwins.vehicle.ta.evs.apidao;

/**
 * Created by xiangzhai on 10/04/15.
 */

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    protected Long licensePic;
    protected String mail;
    protected String nature;
    protected String representative;
    protected String scale;
    protected String status;
    protected String telephone;
    protected String website;
    protected long cityId;

    public EVSEnterprise(long id, Date createTime, Date updateTime, String detailAddress, String enterpriseName, String licenseNumber, long licensePic, String mail, String nature, String representative, String scale, String status, String telephone, String website, Long cityId) {
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

    public Long getLicensePic() {
        return licensePic;
    }

    public void setLicensePic(Long licensePic) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
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
                    r.getLong("licensePic"),
                    r.getString("mail"),
                    r.getString("nature"),
                    r.getString("representative"),
                    r.getString("scale"),
                    r.getString("status"),
                    r.getString("telephone"),
                    r.getString("website"),
                    r.getLong("cityId")
            );
        }
    }

    @Override
    public String toString() {
        return "EVSEnterprise{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", detailAddress=" + detailAddress +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", licenseNumber=" + licenseNumber +
                ", licensePic=" + licensePic +
                ", mail=" + mail +
                ", nature=" + nature +
                ", representative=" + representative +
                ", scale=" + scale +
                ", status=" + status +
                ", telephone=" + telephone +
                ", website=" + website +
                ", cityId=" + cityId +
                "}";
    }

    public static EVSEnterpriseDao dao = EVSUtil.getDAO(EVSEnterpriseDao.class);

}
