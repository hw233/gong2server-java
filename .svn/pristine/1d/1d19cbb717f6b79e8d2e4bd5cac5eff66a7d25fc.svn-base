package com.gamejelly.gong2.gas.entity;

import com.gamejelly.gong2.meta.share.SharedModel;
import com.gamejelly.gong2.meta.share.AvatarTempValueModel;
import com.hadoit.game.engine.guardian.gas.entity.Entity;
import com.hadoit.game.engine.guardian.gas.entity.EntityCallback;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;
import org.apache.commons.lang.StringUtils;
import org.jboss.netty.util.internal.StringUtil;

import java.util.Map;

public class SharedEntity extends Entity {

    @Override
    public String getId() {
        return getSharedModel().getId();
    }

    @Override
    public String getEntityType() {
        return "SharedEntity";
    }

    private Map<String, AvatarTempValueModel> avatarTempValueMap;


    public SharedModel getSharedModel() {
        return (SharedModel) super.getDbModel();
    }


    @Override
    public void writeToDB(EntityCallback<Entity> callback) {
        super.writeToDB(callback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onInit() {
        initData();
    }


    protected void initData() {

    }

    public AvatarTempValueModel getAvatarTempVauleModel(String avatarId, boolean needCreateIfNull) {
        if (avatarTempValueMap == null || !avatarTempValueMap.containsKey(avatarId)) {
            GuardianLogger.info("getAvatarTempVauleModel null avatarId = ", avatarId);
            return addDefaultAvatarTempValueModel(avatarId, needCreateIfNull);
        } else {
            return avatarTempValueMap.get(avatarId);
        }
    }
    public AvatarTempValueModel addDefaultAvatarTempValueModel(String avatarId, boolean needCreateIfNull) {
        if (!needCreateIfNull) {
            return null;
        }
        GuardianLogger.info("addDefaultAvatarTempValueModel avatarId = ", avatarId);
        if (StringUtils.isEmpty(avatarId)) {
            return null;
        }

        if (avatarTempValueMap.containsKey(avatarId)) {
            AvatarTempValueModel xm = avatarTempValueMap.remove(avatarId);
            getSharedModel().getAvatarTempValueList().remove(xm);
        }

        AvatarTempValueModel avatarTempValueModel = new AvatarTempValueModel();
        avatarTempValueModel.setAvatarId(avatarId);
        avatarTempValueMap.put(avatarId, avatarTempValueModel);
        getSharedModel().getAvatarTempValueList().add(avatarTempValueModel);
        return avatarTempValueModel;
    }
}
