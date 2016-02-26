package kr.pe.jady.wordict.user.vo;

import kr.pe.jady.wordict.domain.model.User;

import java.util.Date;

public class UserSearchVo extends User {
    private Date startDt;
    private Date endDt;

    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    public Date getEndDt() {
        return endDt;
    }

    public void setEndDt(Date endDt) {
        this.endDt = endDt;
    }
}
