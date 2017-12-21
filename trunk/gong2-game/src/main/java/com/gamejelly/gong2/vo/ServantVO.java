package com.gamejelly.gong2.vo;

import java.util.List;

import com.gamejelly.gong2.config.data.ServantAdvanceData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.gas.entity.ServantEntity;
import com.gamejelly.gong2.meta.ServantModel;

@SuppressWarnings("unused")
public class ServantVO {
    private ServantModel model;

    private int zhanli;

    private List<Integer> dress;

    private ServantNextVO nextLvServantVo;

    private ServantNextVO nextAdvanceLvServantVo;
    /**
     * 面板属性
     */
    public float disAtk;
    public float disDef;
    public float disDpower;
    public float disHp;
    public float disCri;
    public float disDcri;
    public float disFocus;
    public float disDparry;
    public float disParry;
    public float disTen;
    public float disPen;
    public float disDeHIT;
    public float disEahit;
    public float disPreDeepen;
    public float disPreEase;
    public int disCp;

    public ServantVO() {

    }

    public ServantVO(ServantEntity se) {
        this.model = se.getModel();
        this.zhanli = se.getZhanli();
        this.disAtk = se.getDisAtk();
        this.disDef = se.getDisDef();
        this.disDpower = se.getDisDpower();
        this.disHp = se.getDisHp();
        this.disCri = se.getDisCri();
        this.disDcri = se.getDisDcri();
        this.disFocus = se.getDisFocus();
        this.disDparry = se.getDisDpower();
        this.disParry = se.getDisParry();
        this.disTen = se.getDisPen();
        this.disPen = se.getDisPen();
        this.disDeHIT = se.getDisDeHIT();
        this.disEahit = se.getDisEahit();
        this.disPreDeepen = se.getDisPreDeepen();
        this.disPreEase = se.getDisPreEase();
        this.disCp = se.getBasCp();

        setNextLvServantVo(se);
        setNextAdvanceLvServantVo(se);
    }

    private void setNextLvServantVo(ServantEntity se) {

        int maxLv = SysConstData.data.getInt("MAX_LV", 0);
        if (se.getModel().getLv() >= maxLv) {
            return;
        }
        se.getModel().incrLv(1);
        se.calcAll();
        this.nextLvServantVo = new ServantNextVO(se);
        se.getModel().incrLv(-1);
        se.calcAll();
    }

    private void setNextAdvanceLvServantVo(ServantEntity se) {

        int maxAdvanceLv = ServantAdvanceData.data.size();
        if (se.getModel().getAdvanceLv() >= maxAdvanceLv) {
            return;
        }
        se.getModel().incrAdvanceLv(1);
        se.calcAll();
        this.nextAdvanceLvServantVo = new ServantNextVO(se);
        se.getModel().incrAdvanceLv(-1);
        se.calcAll();
    }
}
