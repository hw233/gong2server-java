package com.gamejelly.gong2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gamejelly.gong2.config.data.EquipData;
import com.gamejelly.gong2.meta.*;
import com.hadoit.game.engine.core.protocol.json.GsonFactory;
import org.apache.commons.collections.CollectionUtils;

import com.gamejelly.gong2.config.data.ItemData;
import com.gamejelly.gong2.config.data.ServantData;
import com.gamejelly.gong2.config.data.SysConstData;
import com.gamejelly.gong2.config.data.base.LList;
import com.gamejelly.gong2.config.data.base.LMap;

import com.gamejelly.gong2.gas.service.shared.SharedDataService;
import com.gamejelly.gong2.meta.AvatarModel;
import com.gamejelly.gong2.meta.EquipModel;
import com.gamejelly.gong2.meta.ItemModel;
import com.gamejelly.gong2.meta.RelationModel;
import com.gamejelly.gong2.meta.ServantModel;

import com.gamejelly.gong2.vo.SimplePlayerInfo;
import com.hadoit.game.common.lang.Pair;
import com.hadoit.game.engine.guardian.utils.GuardianLogger;

/**
 * 有些逻辑dbs也需要用到, 但是gas.service包被spring隔离了, 需要这个class中转一下
 *
 * @author wacenn
 */
public class LogicUtils {

    private static ItemModel newItemModel(int templateId, int count) {
        ItemModel im = new ItemModel();
        im.setCount(count);
        im.setTemplateId(templateId);
        im.setId(IdProvider.genId(GongConstants.ID_TYPE_ITEM));
        im.setCreateTime(System.currentTimeMillis());
        return im;
    }

    public static EquipModel initEquipProp(int equipTemplateId) {
        EquipModel em = new EquipModel();

        LMap equipTemp = EquipData.data.getMap(equipTemplateId);
        if (equipTemp == null) {
            GuardianLogger.error("Equip not exist id =",equipTemplateId);
            return  null;
        }


        return em;
    }

    public static List<ItemModel> createItem(int templateId, int count) {
        LMap template = ItemData.data.getMap(templateId);
        if (template == null) {
            GuardianLogger.error("createItem ItemData not exist! templateId=" + templateId);
            return null;
        }
        if (count < 1) {
            return null;
        }
        int kind = template.getInt("kind");
        List<ItemModel> ret = new ArrayList<ItemModel>();
        boolean stack = template.getBool("stack");
        int loopCnt = stack ? 1 : count;
        int perCnt = stack ? count : 1;
        for (int i = 0, len = loopCnt; i < len; i++) {
            ItemModel im = newItemModel(templateId, perCnt);
            if (kind == SysConstData.data.getInt("ITEM_KIND_1")) {
                // 装备
                EquipModel em = initEquipProp(templateId);
                im.setEquip(em);
                im.setInBag(true);
            } else if (kind == SysConstData.data.getInt("ITEM_KIND_4")) {
                // 阵法
                im.setLv(1);
                im.setInBag(false);
            } else if (kind == SysConstData.data.getInt("ITEM_KIND_7")) {
                // 技能
                im.setLv(1);
                im.setInBag(false);
            } else {
                im.setInBag(true);
            }

            ret.add(im);
        }

        return ret;
    }

    public static Pair<ServantModel, List<ItemModel>> createServantModel(int templateId, int lv) {
        Pair<ServantModel, List<ItemModel>> ret = new Pair<ServantModel, List<ItemModel>>(null, null);

        ServantModel sm = new ServantModel();
        sm.setId(IdProvider.genId(GongConstants.ID_TYPE_SERVATE));
        sm.setTemplateId(templateId);
        sm.setLv(lv);
        sm.setPos(0);
        sm.setCreateTime(System.currentTimeMillis());
        ret.setFirst(sm);

        List<ItemModel> items = initServantFb(sm);
        List<ItemModel> itemsEquip =initServantEquip(sm);
        items.addAll(itemsEquip);
        ret.setSecond(items);
        return ret;
    }


    public static List<ItemModel> initServantEquip(ServantModel mtm) {
        LMap mtData = ServantData.data.getMap(mtm.getTemplateId());
        LList equipIdList = mtData.getList("equip", true);

        List<ItemModel> ebs = new ArrayList<ItemModel>();
        if (CollectionUtils.isNotEmpty(equipIdList)) {
            for (int i = 0; i < equipIdList.size(); i++) {
                int equipTemplateId = equipIdList.getInt(i, 0);
                LMap equipData = EquipData.data.getMap(equipTemplateId);
                if (equipData == null) {
                    GuardianLogger.error("EquipData not exit id =", equipTemplateId);
                    continue;
                }

                if (equipTemplateId > 0) {
                    ItemModel equipItem = LogicUtils.createItem(equipTemplateId, 1).get(0);
                    equipItem.setOwnerId(mtm.getId());
                    equipItem.setInBag(false);
                    ebs.add(equipItem);
                }
            }
        }
        return ebs;
    }


    public static List<ItemModel> initServantFb(ServantModel mtm) {
        LMap mtData = ServantData.data.getMap(mtm.getTemplateId());
        LList fbIdList = mtData.getList("skill", true);
        List<ItemModel> fbs = null;
        fbs = new ArrayList<ItemModel>();
        if (CollectionUtils.isNotEmpty(fbIdList)) {
            for (int i = 0; i < fbIdList.size(); i++) {
                int tffbId = fbIdList.getInt(i, 0);
                if (tffbId > 0) {
                    ItemModel fbItme = LogicUtils.createItem(tffbId, 1).get(0);
                    fbItme.setOwnerId(mtm.getId());
                    fbs.add(fbItme);
                }
            }
        }
        return fbs;
    }
    
    public static SimplePlayerInfo doConvertToSimplePlayerInfo(AvatarModel am, RelationModel rm) {
		SimplePlayerInfo spi = new SimplePlayerInfo();
		spi.setId(am.getId());
		spi.setLv(am.getLv());
		spi.setName(am.getRoleName());
		spi.setVipLv(am.getVipLv());
		spi.setIconIndex(am.getIconIndex());
		spi.setJob(am.getJob());
//		spi.setGrade(am.getGrade());
//		spi.setGongHuiName(am.getGongHuiName());
//		spi.setRobotScope(am.getRobotScope());
//		spi.setMeiLi(am.getMeiLi());
//		spi.setLastCustomPhotoTime(am.getLastCustomPhotoTime());
		spi.setSex(am.getSex());
//		spi.setMapPhoto(am.getMapPhoto());
		spi.setServerId(am.getServerId());
		spi.setLogoutTime(am.getLogoutTime());


		if (rm != null) {
			spi.setRelationType(rm.getRelationType());
			spi.setQinMiValue(rm.getQinMiValue());
			spi.setMsgType(rm.getMsgType());
			spi.setMsgWindowStatus(rm.getMsgWindowStatus());
		}
		return spi;
    }


    public static UserMessageModel createSystemRewardMessage(String avatarId, String content,
                                                             SystemRewardInfo systemRewardInfo, long currTime) {
        UserMessageModel umm = new UserMessageModel();
        umm.setMajorType(GongConstants.MSG_MAJOR_TYPE_SYSTEM);
        umm.setChildType(GongConstants.MSG_CHILD_TYPE_SYSTEM_REWARD);
        umm.setContent(content);
        umm.setReceiverId(avatarId);
        umm.setCreateTime(currTime);

        List<MessageAction> actionList = new ArrayList<MessageAction>(2);
        HashMap<String, String> map = new HashMap<String, String>(2);
        map.put("rewardInfo", GsonFactory.getDefault().toJson(systemRewardInfo));
        MessageAction ac = new MessageAction(1, GongConstants.MSG_OP_LINGQU, map);
        actionList.add(ac);
        umm.setActionList(actionList);
        return umm;
    }
    public static UserMessageModel createSystemCommonMsg(String avatarId, String content, long currTime) {
        UserMessageModel umm = new UserMessageModel();
        umm.setReceiverId(avatarId);
        umm.setMajorType(GongConstants.MSG_MAJOR_TYPE_SYSTEM);
        umm.setChildType(GongConstants.MSG_CHILD_TYPE_SYSTEM_COMMON);
        umm.setContent(content);
        umm.setCreateTime(currTime);
        return umm;
    }


}
