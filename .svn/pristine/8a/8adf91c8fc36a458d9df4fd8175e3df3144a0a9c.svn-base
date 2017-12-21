package com.gamejelly.gong2.gas.entity;


import com.gamejelly.gong2.config.data.PropData;
import com.gamejelly.gong2.config.data.ServantAdvanceData;
import com.gamejelly.gong2.config.data.ServantData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;
import com.gamejelly.gong2.gas.entity.fight.BaseFightProperty;
import com.gamejelly.gong2.gas.entity.fight.Fightable;
import com.gamejelly.gong2.meta.ServantModel;
import com.gamejelly.gong2.utils.FormulaHelper;
import com.gamejelly.gong2.utils.GongConstants;
import com.gamejelly.gong2.utils.GongUtils;
import com.gamejelly.gong2.utils.gen.FightablePropHelper;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;


public class ServantEntity extends BaseFightProperty implements Fightable {

    private AvatarEntity entity;
    private ServantModel model;

    private int zhanli;

    public ServantEntity() {

    }

    public ServantEntity(AvatarEntity entity, ServantModel m) {
        this.entity = entity;
        this.model = m;
    }

    @Override
    public void calcAll() {
        if (getTemplate() == null) {
            GuardianLogger.warn("Mentu not exists on Mentu calcAll! templateId=", getTemplateId());
            return;
        }

        this.reset();

        servantCommonUse();

    }

    // 通用属性加成
    private void servantCommonUse() {


        //进阶
        if (this.getModel().getAdvanceLv() > 0) {
            LMap servantAdvanceData = ServantAdvanceData.data.getMap(this.getModel().getAdvanceLv());
            if (servantAdvanceData != null) {
                LList propKindList = servantAdvanceData.getList("propkind");
                LList propKindNum =servantAdvanceData.getList("propnum");
                if(GongUtils.twoListNotEmptyAndHaveEqualSize(propKindList,propKindNum)){
                    for (int i = 0; i < propKindList.size(); i++) {
                        FightablePropHelper.incrNumProp(this,
                                PropData.data.getMap(propKindList.getInt(i)).getString("funName"),
                                propKindNum.getFloat(i));
                    }
                }
            } else {
                GuardianLogger.error(" ServantAdvanceData id not exist id = ", this.getModel().getAdvanceLv());
            }

        }


        // 汇总运算
        FormulaHelper.calcFightObjectProps(this);

        zhanli = FormulaHelper
                .calcSFormulaNumber(GongConstants.FORMULA_TOTAL_SERVANT_ZL, GongUtils.createHashMap("o", this))
                .intValue();

//        GuardianLogger.info(this.toString());
    }

    @Override
    public int getTemplateId() {
        return model.getTemplateId();
    }

    public LMap getTemplate() {
        LMap ret = ServantData.data.getMap(model.getTemplateId());
        return ret;
    }

    public AvatarEntity getEntity() {
        return entity;
    }

    public void setEntity(AvatarEntity entity) {
        this.entity = entity;
    }

    public ServantModel getModel() {
        return model;
    }

    public void setModel(ServantModel model) {
        this.model = model;
    }

    public String getId() {
        return model.getId();
    }

    @Override
    public int getLv() {
        return model.getLv();
    }

    // basAtk
    // basPreAtk
    // basDef
    // basPreDef
    // basDpower
    // basPreDpower
    // basHp
    // basPreHp
    // basCp
    // basCri
    // basDcri
    // basFocus
    // basDparry
    // basParry
    // basTen
    // basPen
    // basDeHIT
    // basEahit
    // basPreDeepen
    // basPreEase

    public int getAvatarLv() {
        return entity.getAvatarModel().getLv();
    }

    public int getBasAtk() {
        return getTemplate().getInt("basAtk", 0);
    }

    public float getBasPreAtk() {
        return getTemplate().getFloat("basPreAtk", 0f);
    }

    public int getBasDef() {
        return getTemplate().getInt("basDef", 0);
    }

    public float getBasPreDef() {
        return getTemplate().getFloat("basPreDef", 0f);
    }

    public int getBasDpower() {
        return getTemplate().getInt("basDpower", 0);
    }

    public float getBasPreDpower() {
        return getTemplate().getFloat("basPreDpower", 0f);
    }

    public int getBasHp() {
        return getTemplate().getInt("basHp", 0);
    }

    public float getBasPreHp() {
        return getTemplate().getFloat("basPreHp", 0f);
    }

    public int getBasCp() {
        return getTemplate().getInt("basCp", 0);
    }

    public float getBasCri() {
        return getTemplate().getFloat("basCri", 0f);
    }

    public float getBasDcri() {
        return getTemplate().getFloat("basDcri", 0f);
    }

    public int getBasFocus() {
        return getTemplate().getInt("basFocus", 0);
    }

    public float getBasDparry() {
        return getTemplate().getFloat("basDparry", 0f);
    }

    public float getBasParry() {
        return getTemplate().getFloat("basParry", 0f);
    }

    public int getBasTen() {
        return getTemplate().getInt("basTen", 0);
    }

    public int getBasPen() {
        return getTemplate().getInt("basPen", 0);
    }

    public int getBasDeHIT() {
        return getTemplate().getInt("basDeHIT", 0);
    }

    public int getBasEahit() {
        return getTemplate().getInt("basEahit", 0);
    }

    public float getBasPreDeepen() {
        return getTemplate().getFloat("basPreDeepen", 0f);
    }

    public float getBasPreEase() {
        return getTemplate().getFloat("basPreEase", 0f);
    }

    public float getAtkLv() {
        return getTemplate().getFloat("atkLv", 0f);
    }

    public float getDefLv() {
        return getTemplate().getFloat("defLv", 0f);
    }

    public float getDpowerLv() {
        return getTemplate().getFloat("dpowerLv", 0f);
    }

    public float getHpLv() {
        return getTemplate().getFloat("hpLv", 0f);
    }

    public int getPropType() {
        return getTemplate().getInt("propType", 0);
    }


    public float getAtkPat() {
        return 1;
    }

    public float getDefPat() {
        return 1;
    }

    public float getHpPat() {
        return 1;
    }

    public float getSpdPat() {
        return 1;
    }

    public float getPowerPat() {
        return 1;
    }

    public float getDpowerPat() {
        return 1;
    }

    public float getMpPat() {
        return 1;
    }

    public float getHitPat() {
        return 1;
    }

    public float getDgePat() {
        return 1;
    }

    public float getCriPat() {
        return 1;
    }

    public float getDcriPat() {
        return 1;
    }

    public float getFocusPat() {
        return 1;
    }

    public float getDatkPat() {
        return 1;
    }

    public int getZhanli() {
        return zhanli;
    }

    public void setZhanli(int zhanli) {
        this.zhanli = zhanli;
    }

    public String getName() {
        if (model.isAvatar()) {
            return entity.getAvatarModel().getRoleName();
        }
        return getTemplate().getString("name", "");
    }

    @Override
    public String toString() {
        return "ServantEntity [entity=" + entity + ", model=" + model + ", zhanli=" + zhanli + ", totAtk=" + totAtk
                + ", totPreAtk=" + totPreAtk + ", totDef=" + totDef + ", totPreDef=" + totPreDef + ", totDpower="
                + totDpower + ", totPreDpower=" + totPreDpower + ", totHp=" + totHp + ", totPreHp=" + totPreHp
                + ", totCp=" + totCp + ", totCri=" + totCri + ", totDcri=" + totDcri + ", totFocus=" + totFocus
                + ", totDparry=" + totDparry + ", totParry=" + totParry + ", totTen=" + totTen + ", totPen=" + totPen
                + ", totDeHIT=" + totDeHIT + ", totEahit=" + totEahit + ", totPreDeepen=" + totPreDeepen
                + ", totPreEase=" + totPreEase + ", criProb=" + criProb + ", parryProb=" + parryProb + ", finalAtk="
                + finalAtk + ", finalDef=" + finalDef + ", finalDpower=" + finalDpower + ", finalHp=" + finalHp
                + ", finalCp=" + finalCp + ", finalPen=" + finalPen + ", finalDeHIT=" + finalDeHIT + ", finalEahit="
                + finalEahit + ", finalPreDeepen=" + finalPreDeepen + ", finalPreEase=" + finalPreEase + ", bufAtk="
                + bufAtk + ", bufPreAtk=" + bufPreAtk + ", bufDef=" + bufDef + ", bufPreDef=" + bufPreDef
                + ", bufDpower=" + bufDpower + ", bufPreDpower=" + bufPreDpower + ", bufHp=" + bufHp + ", bufPreHp="
                + bufPreHp + ", bufCp=" + bufCp + ", bufCri=" + bufCri + ", bufDcri=" + bufDcri + ", bufFocus="
                + bufFocus + ", bufDparry=" + bufDparry + ", bufParry=" + bufParry + ", bufTen=" + bufTen + ", bufPen="
                + bufPen + ", bufDeHIT=" + bufDeHIT + ", bufEahit=" + bufEahit + ", bufPreDeepen=" + bufPreDeepen
                + ", bufPreEase=" + bufPreEase + ", rankAtk=" + rankAtk + ", rankDef=" + rankDef + ", rankDpower="
                + rankDpower + ", rankHp=" + rankHp + ", equAtk=" + equAtk + ", equDef=" + equDef + ", equDpower="
                + equDpower + ", equHp=" + equHp + ", skillDamegeAdd=" + skillDamegeAdd + ", skillDamegeProb="
                + skillDamegeProb + ", disAtk=" + disAtk + ", disDef=" + disDef + ", disDpower=" + disDpower
                + ", disHp=" + disHp + ", disCri=" + disCri + ", disDcri=" + disDcri + ", disFocus=" + disFocus
                + ", disDparry=" + disDparry + ", disParry=" + disParry + ", disTen=" + disTen + ", disPen=" + disPen
                + ", disDeHIT=" + disDeHIT + ", disEahit=" + disEahit + ", disPreDeepen=" + disPreDeepen
                + ", disPreEase=" + disPreEase + ", getTemplateId()=" + getTemplateId() + ", getTemplate()="
                + getTemplate() + ", getEntity()=" + getEntity() + ", getModel()=" + getModel() + ", getId()=" + getId()
                + ", getLv()=" + getLv() + ", getAvatarLv()=" + getAvatarLv() + ", getBasAtk()=" + getBasAtk()
                + ", getBasPreAtk()=" + getBasPreAtk() + ", getBasDef()=" + getBasDef() + ", getBasPreDef()="
                + getBasPreDef() + ", getBasDpower()=" + getBasDpower() + ", getBasPreDpower()=" + getBasPreDpower()
                + ", getBasHp()=" + getBasHp() + ", getBasPreHp()=" + getBasPreHp() + ", getBasCp()=" + getBasCp()
                + ", getBasCri()=" + getBasCri() + ", getBasDcri()=" + getBasDcri() + ", getBasFocus()=" + getBasFocus()
                + ", getBasDparry()=" + getBasDparry() + ", getBasParry()=" + getBasParry() + ", getBasTen()="
                + getBasTen() + ", getBasPen()=" + getBasPen() + ", getBasDeHIT()=" + getBasDeHIT() + ", getBasEahit()="
                + getBasEahit() + ", getBasPreDeepen()=" + getBasPreDeepen() + ", getBasPreEase()=" + getBasPreEase()
                + ", getAtkLv()=" + getAtkLv() + ", getDefLv()=" + getDefLv() + ", getDpowerLv()=" + getDpowerLv()
                + ", getHpLv()=" + getHpLv() + ", getPropType()=" + getPropType() + ", getAtkPat()=" + getAtkPat()
                + ", getDefPat()=" + getDefPat() + ", getHpPat()=" + getHpPat() + ", getSpdPat()=" + getSpdPat()
                + ", getPowerPat()=" + getPowerPat() + ", getDpowerPat()=" + getDpowerPat() + ", getMpPat()="
                + getMpPat() + ", getHitPat()=" + getHitPat() + ", getDgePat()=" + getDgePat() + ", getCriPat()="
                + getCriPat() + ", getDcriPat()=" + getDcriPat() + ", getFocusPat()=" + getFocusPat()
                + ", getDatkPat()=" + getDatkPat() + ", getZhanli()=" + getZhanli() + ", getName()=" + getName() + "]";
    }

}
